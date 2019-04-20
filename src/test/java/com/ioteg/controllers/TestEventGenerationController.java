package com.ioteg.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.not;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
public class TestEventGenerationController {

	@Autowired
	private GenerationController controller;

	@Test
	public void testGenerateEvents() throws Exception {
		assertThat(controller, not(nullValue()));
		/*Field field = new Field(null, "Integer", "50", null, null, null, null, null, null, null, null, null, null, null,
				null, "sensor", null, null, null, null, null, null, null);

		Block block = new Block(null, "bloque", null, 10, Arrays.asList(field), null, null);
		EventType eventType = new EventType(null, "eventoPrueba", Arrays.asList(block));

		List<ResultEvent> resultEvents = controller.generateEvents(new EventTypeList(Arrays.asList(eventType)));

		ResultEvent resultEvent = resultEvents.get(0);

		assertThat(resultEvent.getName(), equalTo("eventoPrueba"));
		assertThat(resultEvent.getArrayResultBlocks().size(), equalTo(1));
		assertThat(resultEvent.getArrayResultBlocks().get(0).getResultBlocks().size(), equalTo(10));

		resultEvent.getArrayResultBlocks().get(0).getResultBlocks().forEach(resultBlock -> {
			assertThat(resultBlock.getName(), equalTo("bloque"));
			assertThat(resultBlock.getResultFields().size(), equalTo(1));

			ResultField resultField = resultBlock.getResultFields().get(0);
			assertThat(resultField.getName(), equalTo("sensor"));
			assertThat(resultField.getQuotes(), equalTo(false));
			assertThat(resultField.getType(), equalTo("Integer"));

			ResultSimpleField resultSimpleField = (ResultSimpleField) resultField;
			assertThat(resultSimpleField.getValue(), equalTo("50"));
		});*/

	}
}