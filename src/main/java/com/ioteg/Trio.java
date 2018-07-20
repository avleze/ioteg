package com.ioteg;

public class Trio<A, B, C> {

	public A first;
	public B second;
	public C third;
	
	public Trio() {};
	
	public Trio (A first, B second, C third){
		this.first = first;
		this.second = second;
		this.third = third;
	}
	
	public A getFirst() { return first;}
	public B getSecond() { return second;}
	public C getThird() { return third;}
	
	public void setFirst(A v) {first = v;}
	public void setSecond(B v) {second = v;}
	public void setThird(C v) {third = v;}
}
