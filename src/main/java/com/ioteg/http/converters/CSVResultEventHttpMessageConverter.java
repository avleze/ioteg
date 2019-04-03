package com.ioteg.http.converters;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import com.ioteg.resultmodel.ResultEvent;
import com.ioteg.serializers.csv.CSVUtil;

/**
 * <p>CSVResultEventHttpMessageConverter class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class CSVResultEventHttpMessageConverter extends AbstractHttpMessageConverter<ResultEvent> {

	/**
	 * <p>Constructor for CSVResultEventHttpMessageConverter.</p>
	 */
	public CSVResultEventHttpMessageConverter() {
         super(new MediaType("application", "csv", StandardCharsets.UTF_8));
	}

	/** {@inheritDoc} */
	@Override
	protected boolean supports(Class<?> clazz) {		
		return clazz.getSimpleName().equalsIgnoreCase(ResultEvent.class.getSimpleName());
	}

	/** {@inheritDoc} */
	@Override
	protected ResultEvent readInternal(Class<? extends ResultEvent> clazz, HttpInputMessage inputMessage)
			throws IOException {

		return null;
	}

	/** {@inheritDoc} */
	@Override
	protected void writeInternal(ResultEvent t, HttpOutputMessage outputMessage)
			throws IOException {
		outputMessage.getBody().write(CSVUtil.serializeResultEvent(t.getModel(), t).getBytes());
	}

}
