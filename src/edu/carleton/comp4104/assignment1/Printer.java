/**
 * Anthony D'Angelo 100773125
 * Tsering Chopel 100649290
 * Florent Muyango 100709054 
 */



package edu.carleton.comp4104.assignment1;

import java.util.ArrayList;
import java.util.Random;

public class Printer implements Runnable{
	static ArrayList <Boolean> print; //probably should pass the arraylist as argument inside constructor. from design point of view?
	char character;
	int order;
	
	
	public Printer(char character, int order){
		this.character = character;
		this.order = order;
	}
	
	public void run(){
		Random random = new Random();
		try {
			Thread.sleep(random.nextInt(101));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		
		synchronized (print){
			while(!(print.get(order) == true)){
				try {
					print.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
				System.out.print(character+", ");
				print.set(order, false);
				print.set(order+1, true);
				print.notifyAll();
			
		}
	}
	


}
