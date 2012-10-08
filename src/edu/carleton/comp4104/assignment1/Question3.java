package edu.carleton.comp4104.assignment1;
//driver class
public class Question3 {

	public Question3(){
		
	}
	
	public static void main(String args[]){
		
		String simulationTime = args[1];
		String maxEatTime = args[3];
		
		Control monitor = new Control();
		
		Thread eater1 = new Thread(new Eater("potato",monitor));
		eater1.start();
		Thread eater2 = new Thread(new Eater("water",monitor));
		eater2.start();
		Thread eater3 = new Thread(new Eater("butter",monitor));
		eater3.start();
		
		Thread agent = new Thread(new Agent(monitor));
		agent.start();
		
	}
}
