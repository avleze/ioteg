package com.ioteg.serializers.xml;

import java.io.IOException;
import java.util.List;
import com.ioteg.resultmodel.ResultEvent;

/**
 * <p>XMLResultEventListSerializer class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class XMLResultEventListSerializer implements XMLSerializer<List<ResultEvent>> {

	private static XMLResultEventSerializer xmlResultEventSerializer;

	static {
		xmlResultEventSerializer = new XMLResultEventSerializer();
	}

	/** {@inheritDoc} */
	@Override
	public void serialize(List<ResultEvent> value, XMLGenerator xmlGen) throws IOException {

		xmlGen.writeStartField("eventTypes", true);

		for (ResultEvent resultEvent : value)
			xmlResultEventSerializer.serialize(resultEvent, xmlGen);

		xmlGen.writeEndField("eventTypes", true);
	}

}
