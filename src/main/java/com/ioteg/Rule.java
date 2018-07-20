package com.ioteg;

public class Rule<weight, value, min, max, sequence> {

	public weight first;
	public value second;
	public min third;
	public max fourth;
	public sequence fifth;
	
	public Rule() {};
	
	public Rule (weight first, value second, min third, max fourth, sequence fifth){
		this.first = first;
		this.second = second;
		this.third = third;
		this.fourth = fourth;
		this.fifth = fifth;
	}
	
	public weight getWeight() { return first;}
	public value getValue() { return second;}
	public min getMin() { return third;}
	public max getMax() { return fourth;}
	public sequence getSequence() { return fifth;}
	
	public void setWeight(weight v) {first = v;}
	public void setValue(value v) {second = v;}
	public void setMin(min v) {third = v;}
	public void setMax(max v) {fourth = v;}
	public void setSequence(sequence v) {fifth = v;}

}
