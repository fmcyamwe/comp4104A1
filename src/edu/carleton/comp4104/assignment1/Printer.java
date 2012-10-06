/**
 * Anthony D'Angelo 100773125
 * Tsering Chopel 100649290
 * Florent Muyango 100709054 
 */



package edu.carleton.comp4104.assignment1;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Printer implements Runnable{
	CountDownLatch latch = null;
	static ArrayList <Boolean> print; 
	char character;
	int order;
	
	
	public Printer(char character, CountDownLatch latch, int order){
		this.latch = latch;
		this.character = character;
		this.order = order;
		//print[0] = true;
	}
	
	public void run(){
		//System.out.println("am i here"+ Thread.currentThread().getName());
		Random random = new Random();
		try {
			Thread.sleep(random.nextInt(101));
			//latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		
		synchronized (print){ // am i synchronizing on arraylist object? but its also a variable and that too a static. 
			while(!(print.get(order) == true)){
				try {
					print.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
				System.out.println(character);
				print.set(order, false);
				print.set(order+1, true);
				print.notifyAll();
			
		}
	}
	


}
