package com.ioteg.services.periodicgeneration;

import java.text.ParseException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generators.context.GenerationContext;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;
import com.ioteg.model.ConfigurableEventType;
import com.ioteg.resultmodel.ResultField;

/**
 * <p>PeriodicEventGenerationService class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
@Service
public class PeriodicEventGenerationService {

	private ScheduledThreadPoolExecutor scheduledPool = new ScheduledThreadPoolExecutor(4);

	@Autowired
	private ObjectMapper objectMapper;
	
	/**
	 * <p>executeConfigurableEventTypes.</p>
	 *
	 * @param configurableEventTypes a {@link java.util.List} object.
	 * @throws com.ioteg.generators.exceptions.NotExistingGeneratorException if any.
	 * @throws com.ioteg.exprlang.ExprParser.ExprLangParsingException if any.
	 * @throws java.text.ParseException if any.
	 */
	public void executeConfigurableEventTypes(List<ConfigurableEventType> configurableEventTypes)
			throws NotExistingGeneratorException, ExprLangParsingException, ParseException {

		ConcurrentMap<String, ResultField> sharedConcurrentMap = new ConcurrentHashMap<>();

		for (ConfigurableEventType configurableEventType : configurableEventTypes)
			scheduledPool.scheduleAtFixedRate(new PeriodicEventGenerator(configurableEventType.getEventType(),
					new GenerationContext(sharedConcurrentMap), objectMapper), configurableEventType.getDelay(), configurableEventType.getPeriod(), configurableEventType.getUnit());
	}

}
