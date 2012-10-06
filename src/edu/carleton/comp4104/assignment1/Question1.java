/**
 * Anthony D'Angelo 100773125
 * Tsering Chopel 100649290
 * Florent Muyango 100709054 
 */

package edu.carleton.comp4104.assignment1;

import java.util.ArrayList;
import java.util.Random;
//import java.util.concurrent.CountDownLatch;

public class Question1{
	
	public static void main(String[] args) {
		
		//CountDownLatch latch = new CountDownLatch(1);
		String st = args[0];
		//String copy = st;
		int length = st.length();
		
		
		//System.out.println(ch);
		
		for(int i = 0; i< st.length(); i++){
			if(st.charAt(i)=='{'){  // need to take care of embedded curly brackets. {{}}, right now first and third curly bracket are considered one
				//System.out.println("{ was detected, so i can use == to compare");
				for(int j = 0; j< st.substring(i).length(); j++){
					if(st.substring(i).charAt(j)=='}'){
						char c = onlyOnechar(st.substring(i, j));
						System.out.println(c);
						i += j+1;
					}
				}	
			}
			System.out.println(st.charAt(i));
		}
		
//		char [] characters = new char[length]; // fill it up with characters in order of first character at zero index
//		
//		for(int i = 0;i<length; i++	){
//			characters[i] = s.charAt(i);
//		}
//		
//		Printer.print = new ArrayList<Boolean>(length);
//		for(int i = 0;i<length; i++	){
//			Printer.print.add(i, false); // initializes the new boolean inside Arraylist 
//		}
//		
//		Printer.print.add(0,true);
//		
//		for(int i = 0;i<length; i++	){
//			Thread t = new Thread(new Printer(characters[i], i), "Thread "+i);
//			t.start();
//		}
		
		
		
	}
	
	public static String randomize(String s){
		if(s.length() == 1)
			return s;
		
		for(char c: s.toCharArray()){
			if(c=='['){
				
			}
		}
		return s;
	}
	
	public static char onlyOnechar(String s){
		Random random = new Random();
		int index = random.nextInt(s.length());
		char [] c = s.toCharArray();
		
		if(c[index] == '[')
			randomize(s.substring(index));
		else if(c[index]=='{')
			 return onlyOnechar(s.substring(index+1));
		else
			return c[index];
		return 0;
	}

}
