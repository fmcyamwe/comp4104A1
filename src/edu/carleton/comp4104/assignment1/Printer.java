package edu.carleton.comp4104.assignment1;

import java.util.Random;

public class Printer implements Runnable{
	static boolean [] print;  // need to initialize array by using using size parameter
	char character;
	public Printer(char character, int size){
		this.character = character;
		print[0] = true;
	}
	
	public void run(){
		Random random = new Random();
		try {
			Thread.sleep(random.nextInt(101));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		while(true){
			for(int i = 0; i< print.length; i++){
				if(Thread.currentThread().getName() == "Thread"+i && print[i] == true){   // need to thread.getname right.
					System.out.print(character);
					print[i] = false;   // since forloop is inside infinite while loop, cant have same character printing multiple times. so made it false.
					print[i+1] = true;  // set the next spot  ready for print by making it true.
				}	
			}
		}
		
		
		
	}

}
