/**
 * Anthony D'Angelo 100773125
 * Tsering Chopel 100649290
 * Florent Muyango 100709054 
 */

package edu.carleton.comp4104.assignment1;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Question1{
	
	public static void main(String[] args) {
		
		CountDownLatch latch = new CountDownLatch(1);
		String s = args[0];
		int length = s.length();
		char [] characters = new char[length]; // fill it up with characters in order of first character at zero index
		
		for(int i = 0;i<length; i++	){
			characters[i] = s.charAt(i);
		}
		
		Printer.print = new ArrayList<Boolean>(length);
		for(int i = 0;i<length; i++	){
			Printer.print.add(i, false); // initializes the new boolean inside Arraylist 
		}
		
		Printer.print.add(0,true);
		//Printer.print.get(0) = true;
		
		for(int i = 0;i<length; i++	){
			Thread t = new Thread(new Printer(characters[i], latch, i), "Thread "+i);
			t.start();
		}
		
		try {
			Thread.sleep(101);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public String randomize(String s){
		if(s.length() == 1)
			return s;
		
		for(char c: s.toCharArray()){
			if(c=='['){
				
			}
		}
		return s;
	}
	
	public String onlyOnechar(String s){
		Random random = new Random();
		int index = random.nextInt(s.length());
		char [] c = s.toCharArray();
		if(c[index] == '[')
			randomize(s.substring(index));
		else if(c[index]=='{')
			onlyOnechar(s.substring(index));
		else
			return c[index].;
		return s;
	}

}
