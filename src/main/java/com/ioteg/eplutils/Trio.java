package com.ioteg.eplutils;

/**
 * <p>Trio class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class Trio<A, B, C> {

	private A first;
	private B second;
	private C third;

	/**
	 * <p>Constructor for Trio.</p>
	 */
	public Trio() {
	}

	/**
	 * <p>Constructor for Trio.</p>
	 *
	 * @param first a A object.
	 * @param second a B object.
	 * @param third a C object.
	 */
	public Trio(A first, B second, C third) {
		this.first = first;
		this.second = second;
		this.third = third;
	}

	/**
	 * <p>Getter for the field <code>first</code>.</p>
	 *
	 * @return a A object.
	 */
	public A getFirst() {
		return first;
	}

	/**
	 * <p>Getter for the field <code>second</code>.</p>
	 *
	 * @return a B object.
	 */
	public B getSecond() {
		return second;
	}

	/**
	 * <p>Getter for the field <code>third</code>.</p>
	 *
	 * @return a C object.
	 */
	public C getThird() {
		return third;
	}

	/**
	 * <p>Setter for the field <code>first</code>.</p>
	 *
	 * @param v a A object.
	 */
	public void setFirst(A v) {
		first = v;
	}

	/**
	 * <p>Setter for the field <code>second</code>.</p>
	 *
	 * @param v a B object.
	 */
	public void setSecond(B v) {
		second = v;
	}

	/**
	 * <p>Setter for the field <code>third</code>.</p>
	 *
	 * @param v a C object.
	 */
	public void setThird(C v) {
		third = v;
	}
}
