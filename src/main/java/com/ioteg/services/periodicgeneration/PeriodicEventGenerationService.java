package com.ioteg.services.periodicgeneration;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.ioteg.communications.MqttService;
import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generation.EventTypeGenerator;
import com.ioteg.generation.GenerationContext;
import com.ioteg.generation.GeneratorsFactory;
import com.ioteg.generation.NotExistingGeneratorException;
import com.ioteg.model.ConfigurableEventType;
import com.ioteg.model.EventType;
import com.ioteg.resultmodel.ResultField;
import com.ioteg.services.EntityNotFoundException;
import com.ioteg.services.GenerationService;
import com.ioteg.services.UserService;

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

	private MqttService mqttService;
	private GenerationService generationService;
	private UserService userService;
	
	/**
	 * @param mqttService
	 * @param generationService
	 * @param userService
	 */
	@Autowired
	public PeriodicEventGenerationService(MqttService mqttService, GenerationService generationService,
			UserService userService) {
		super();
		this.mqttService = mqttService;
		this.generationService = generationService;
		this.userService = userService;
		this.tasksByConfigurableEventTypeId = new HashMap<>();
		this.generationContextByChannelId = new HashMap<>();
	}

	private Map<Long, ScheduledFuture<?>> tasksByConfigurableEventTypeId;
	private Map<Long, ConcurrentMap<String, ResultField>> generationContextByChannelId;

	public void executeAsyncEvent(Long channelId, Long configurableEventTypeId)
			throws EntityNotFoundException, NotExistingGeneratorException, ExprLangParsingException, ParseException {
		ConfigurableEventType cEventType = generationService.loadConfigurableEventTypeDeeply(configurableEventTypeId);

		EventType eventType = cEventType.getEventType();

		EventTypeGenerator eTG = GeneratorsFactory.makeEventTypeGenerator(eventType, getGenerationContext(channelId));

		PeriodicEventGenerator pEG = new PeriodicEventGenerator(eTG, mqttService,
				getTopic(channelId, configurableEventTypeId), userService.loadLoggedUser().getMqttApiKey());

		ScheduledFuture<?> task = scheduledPool.scheduleAtFixedRate(pEG, cEventType.getDelay(), cEventType.getPeriod(),
				cEventType.getUnit());
		
		ScheduledFuture<?> possiblePreviousTask = tasksByConfigurableEventTypeId.get(configurableEventTypeId);
		if(possiblePreviousTask != null)
			possiblePreviousTask.cancel(true);

		tasksByConfigurableEventTypeId.put(cEventType.getId(), task);
	}
	
	public void stopAsyncEvent(Long channelId, Long configurableEventTypeId) {
		ScheduledFuture<?> task = tasksByConfigurableEventTypeId.get(configurableEventTypeId);
		if(task != null)
		{
			task.cancel(false);
			tasksByConfigurableEventTypeId.remove(configurableEventTypeId);
		}
	}

	private GenerationContext getGenerationContext(Long channelId) {
		ConcurrentMap<String, ResultField> sharedMap = generationContextByChannelId.get(channelId);
		if (sharedMap == null)
		{
			sharedMap = new ConcurrentHashMap<>();
			generationContextByChannelId.put(channelId, sharedMap);
		}
		return new GenerationContext(sharedMap);
	}

	private String getTopic(Long channelId, Long configurableEventTypeId) {
		return String.format("/channel/%s/event/%s", channelId, configurableEventTypeId);
	}

}
