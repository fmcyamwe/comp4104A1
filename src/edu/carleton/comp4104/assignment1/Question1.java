/**
 * Anthony D'Angelo 100773125
 * Tsering Chopel 100649290
 * Florent Muyango 100709054 
 */

package edu.carleton.comp4104.assignment1;

public class Question1{
	
	public static void main(String[] args) {
		// figure out the string order
		char [] characters = null; // fill it up with characters in order of first character at zero index
		int length = args.length;
		for(int i = 0;i<length; i++	){
			Thread t = new Thread(new Printer(characters[i], length), ""+i);
			t.start();
		}
		
	
		
	}

}
