package com.ioteg;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import com.ioteg.resultmodel.ResultEvent;
import com.ioteg.resultmodel.csvserializers.CSVUtil;

public class CSVResultEventHttpMessageConverter extends AbstractHttpMessageConverter<ResultEvent> {

	public CSVResultEventHttpMessageConverter() {
         super(new MediaType("application", "csv", StandardCharsets.UTF_8));
	}

	@Override
	protected boolean supports(Class<?> clazz) {		
		return clazz.getSimpleName().equalsIgnoreCase(ResultEvent.class.getSimpleName());
	}

	@Override
	protected ResultEvent readInternal(Class<? extends ResultEvent> clazz, HttpInputMessage inputMessage)
			throws IOException {

		return null;
	}

	@Override
	protected void writeInternal(ResultEvent t, HttpOutputMessage outputMessage)
			throws IOException {
		outputMessage.getBody().write(CSVUtil.serializeResultEvent(t.getModel(), t).getBytes());
	}

}
