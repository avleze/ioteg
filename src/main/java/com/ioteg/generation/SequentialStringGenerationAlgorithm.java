package com.ioteg.generation;

import com.ioteg.model.Field;
import static java.lang.Math.*;

/**
 * <p>SequentialStringGenerationAlgorithm class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class SequentialStringGenerationAlgorithm extends GenerationAlgorithm<String> {

	protected String possibleChars;
	private int index;
	private int beginIndex;
	private int endIndex;
	private int step;

	/**
	 * <p>Constructor for SequentialStringGenerationAlgorithm.</p>
	 *
	 * @param field a {@link com.ioteg.model.Field} object.
	 * @param possibleChars a {@link java.lang.String} object.
	 * @param generationContext a {@link com.ioteg.generation.GenerationContext} object.
	 */
	public SequentialStringGenerationAlgorithm(Field field, String possibleChars, GenerationContext generationContext) {
		super(field, generationContext);
		this.possibleChars = possibleChars;
		this.step = Integer.valueOf(field.getStep());
		this.beginIndex = calculateIndex(field.getBegin());
		this.endIndex = calculateIndex(field.getEnd());
		this.index = beginIndex;
	}

	private int calculateIndex(String str) {
		int computedIndex = 0;
		int count = 0;
		for (char c : new StringBuilder(str).reverse().toString().toCharArray())
			computedIndex += (possibleChars.indexOf(c) + 1) * pow(possibleChars.length(), count++);

		return computedIndex;
	}

	/** {@inheritDoc} */
	@Override
	public String generate() {

		String value = getString(index);
		if (step > 0 && (index + step <= endIndex))
			index += step;
		else if (step < 0 && (index + step >= endIndex))
			index += step;
		else
			index = beginIndex;

		return value;
	}

	private String getString(int n) {
		char[] buf = new char[(int) floor(
				log((double) (possibleChars.length() - 1) * (n + 1)) / log(possibleChars.length()))];
		for (int i = buf.length - 1; i >= 0; i--) {
			n--;
			buf[i] = possibleChars.charAt(n % possibleChars.length());
			n /= possibleChars.length();
		}
		return new String(buf);
	}

}
