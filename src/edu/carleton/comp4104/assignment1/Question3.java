package edu.carleton.comp4104.assignment1;

public class Question3 {

	private static void printHelpMessage(){
		System.out.println("This program expects the following arguments:\n"
				+ "-t <simulation time in seconds, at least 1> " +
				"-r <Max time taken to eat food in seconds, at least 1>" +
				"Please ensure your arguments " +
				"conform to these expectations and try again.");
		return;
	}
	
	private static void printInvalidValue(){
		System.out.println("One of the passed-in arguments was an integer less than 1. Try again with a positive integer greater than or " +
				"equal to one.");
		return;
	}
	
	private static void printIntegerRequired(){
		System.out.println("One of the passed-in arguments wasn't an integer. Try again ensuring all arguments " +
				"after the various flags are positive integers, greater than or equal to 1.");
		return;
	}
	
	private static final String ingredientOne = "Potato";
	private static final String ingredientTwo = "Water";
	private static final String ingredientThree = "Butter";
	
	/**
	 * @param args See printHelpMessage.
	 */
	public static void main(String[] args) {
		int argNum = 0;
		boolean seenMaxEatTime = false, seenRuntime = false;		
		final int requiredNumArgs = 4;
		int maxEatTime = 0;
		int runtime = 0;
		Control myControl;
		Agent myAgent;
		Eater[] myEaters;
		Thread[] myThreads;
		
		//Parse and ensure proper arguments
		if(args.length != requiredNumArgs){
			System.out.println("Unsuitable number of arguments.");
			printHelpMessage();
			return;
		}
		
		while(argNum < requiredNumArgs){
			switch(args[argNum++]){
				case("-t"):
					try{
						maxEatTime = Integer.parseInt(args[argNum++]);
						if(1 > maxEatTime){
							printInvalidValue();
							printHelpMessage();
							return;
						}					
						seenMaxEatTime = true;
					} catch(NumberFormatException e) {
						printIntegerRequired();
						printHelpMessage();
						return;
					}					
					break;
				
				case("-r"):
					try{
						runtime = Integer.parseInt(args[argNum++]);
						if(0 > runtime){
							printInvalidValue();
							printHelpMessage();
							return;
						}	
						seenRuntime = true;
					} catch(NumberFormatException e) {
						printIntegerRequired();
						printHelpMessage();
						return;
					}						
					break;
				
				default:
					printHelpMessage();
					return;
			}
			
		} /* while(argNum < requiredNumArgs) */		

		//Ensure they didn't just pass in 4 of the same valid argument.
		if(!seenMaxEatTime || !seenRuntime){
			System.out.println("You were missing one or more of the required arguments.");
			printHelpMessage();
			return;
		}
		
		System.out.println("max eat time " + maxEatTime + ", runtime " + runtime);
		
		//Now that we know suitable arguments have been passed in, instantiate the required variables
		myControl = new Control(runtime);
		
		myAgent = new Agent(ingredientOne, ingredientTwo, ingredientThree, myControl);
		
		myEaters = new Eater[3];
		
		myEaters[0] = new Eater(ingredientOne, maxEatTime, myControl);
		myEaters[1] = new Eater(ingredientTwo, maxEatTime, myControl);
		myEaters[2] = new Eater(ingredientThree, maxEatTime, myControl);
		
		myThreads = new Thread[4];
		for(int i = 0; i < 3; ++i){
			myThreads[i] = new Thread(myEaters[i], "Eater " + i);
			myThreads[i].start();			
		}
		myThreads[3] = new Thread(myAgent, "Agent");
		
		myControl.startDay();
		
		//Wait for everyone to finish.
		for(Thread t:myThreads){
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return;
	}

}
