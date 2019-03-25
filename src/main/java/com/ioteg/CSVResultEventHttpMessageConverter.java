package com.ioteg;

import java.io.IOException;
import java.nio.charset.Charset;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.ioteg.resultmodel.ResultEvent;
import com.ioteg.resultmodel.csvserializers.CSVUtil;

public class CSVResultEventHttpMessageConverter extends AbstractHttpMessageConverter<ResultEvent> {

	public CSVResultEventHttpMessageConverter() {
         super(new MediaType("application", "csv", Charset.forName("UTF-8")));
	}

	@Override
	protected boolean supports(Class<?> clazz) {		
		return clazz.getSimpleName().equalsIgnoreCase(ResultEvent.class.getSimpleName());
	}

	@Override
	protected ResultEvent readInternal(Class<? extends ResultEvent> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {

		return null;
	}

	@Override
	protected void writeInternal(ResultEvent t, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		outputMessage.getBody().write(CSVUtil.serializeResultEvent(t.getModel(), t).getBytes());
	}

}
