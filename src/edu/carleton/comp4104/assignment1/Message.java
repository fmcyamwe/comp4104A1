/**
 * Anthony D'Angelo 100773125
 * Tsering Chopel 100649290
 * Florent Muyango 100709054 
 */

package edu.carleton.comp4104.assignment1;

	public class Message {
		int source;
		int destination;
		String msg;
		int time;
	
	Message(int s,int d,String mes,int t){
		setSource(s);
		setDestination(d);
		setMsg(mes);
		setTime(t);
	}

	public int getSource() {
		return source;
	}
	
	public void setSource(int source) {
		this.source = source;
	}
	
	public int getDestination() {
		return destination;
	}
	
	public void setDestination(int destination) {
		this.destination = destination;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public int getTime() {
		return time;
	}
	
	public void setTime(int t) {
		this.time = t;
	}
}
