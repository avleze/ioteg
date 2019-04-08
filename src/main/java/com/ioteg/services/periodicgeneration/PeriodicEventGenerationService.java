package com.ioteg.services.periodicgeneration;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.ioteg.communications.MqttService;
import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generation.GenerationContext;
import com.ioteg.generation.NotExistingGeneratorException;
import com.ioteg.model.ChannelType;
import com.ioteg.model.ConfigurableEventType;
import com.ioteg.resultmodel.ResultField;

/**
 * <p>
 * PeriodicEventGenerationService class.
 * </p>
 *
 * @author antonio
 * @version $Id: $Id
 */
@Service
@Profile({ "production", "development", "default" })
public class PeriodicEventGenerationService {

	private ScheduledThreadPoolExecutor scheduledPool = new ScheduledThreadPoolExecutor(4);

	@Autowired
	private MqttService mqttService;

	private Map<UUID, ScheduledFuture<?>> futureTasks;
	private Map<UUID, PeriodicEventGenerator> periodicEventGeneratorTasks;

	/**
	 * <p>
	 * executeConfigurableEventTypes.
	 * </p>
	 * 
	 * @param userApiKey
	 *
	 * @param configurableEventTypes a {@link java.util.List} object.
	 * @throws com.ioteg.generation.NotExistingGeneratorException     if any.
	 * @throws com.ioteg.exprlang.ExprParser.ExprLangParsingException if any.
	 * @throws java.text.ParseException                               if any.
	 */
	public List<String> executeAsyncChannel(ChannelType channelType, String userApiKey)
			throws NotExistingGeneratorException, ExprLangParsingException, ParseException {

		List<String> topicsCreated = new ArrayList<>();
		ConcurrentMap<String, ResultField> sharedResults = new ConcurrentHashMap<>();

		for (ConfigurableEventType configurableEventType : channelType.getConfigurableEventTypes()) {
			String topic = formatTopic(channelType.getId(), configurableEventType.getEventType().getName(), userApiKey);

			PeriodicEventGenerator periodicEventGenerator = new PeriodicEventGenerator(topic, configurableEventType,
					new GenerationContext(sharedResults), mqttService);
			scheduleTask(periodicEventGenerator);
			topicsCreated.add(topic);
		}

		return topicsCreated;
	}

	public boolean stopConfigurableEventType(UUID id) {
		return futureTasks.get(id).cancel(false);
	}

	public void resumeConfigurableEventType(UUID id) {
		PeriodicEventGenerator periodicEventGenerator = periodicEventGeneratorTasks.get(id);
		scheduleTask(periodicEventGenerator);
	}

	private ScheduledFuture<?> scheduleTask(PeriodicEventGenerator task) {
		ConfigurableEventType configurableEventType = task.getConfigurableEventType();
		ScheduledFuture<?> futureTask = scheduledPool.scheduleAtFixedRate(task, configurableEventType.getDelay(),
				configurableEventType.getPeriod(), configurableEventType.getUnit());
		periodicEventGeneratorTasks.put(configurableEventType.getId(), task);
		futureTasks.put(configurableEventType.getId(), futureTask);

		return futureTask;
	}

	@PostConstruct
	public void initialize() {
		futureTasks = new HashMap<>();
		periodicEventGeneratorTasks = new HashMap<>();
	}

	private String formatTopic(UUID channelId, String eventName, String apiKey) {
		return String.format("/channel/%s/%s/%s", channelId, eventName, apiKey);
	}
}
