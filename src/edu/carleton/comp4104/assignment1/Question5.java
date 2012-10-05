/**
 * Anthony D'Angelo 100773125
 * Tsering Chopel 100649290
 * Florent Muyango 100709054 
 */

package edu.carleton.comp4104.assignment1;

import java.util.Timer;

public class Question5 {
	
	private final static int millisecondsInSeconds = 1000;
	
	private static void printHelpMessage(){
		System.out.println("This program expects the following arguments:\n"
				+ "-C <Number of Customers> -B <Number of Barbers> -g <Max Hair Growing Time in seconds> -c <Max Hair Cutting Time in seconds>" +
				" -W <Number of Waiting Room Chairs> -R <program runtime in seconds>\n"
				+ "where everything between '<>' (including the '<>') is a positive integer (E.G. -MyFlag 7). " +
				"Please ensure your arguments " +
				"conform to these expectations and try again.");
		return;
	}
	
	private static void printNegativeValueArguments(){
		System.out.println("One of the passed-in arguments was a negative integer. Try again with a positive integer.");
		return;
	}
	
	private static void printIntegerRequired(){
		System.out.println("One of the passed-in arguments wasn't an integer. Try again ensuring all arguments " +
				"after the various flags are positive integers.");
		return;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int argNum = 0;
		boolean seenNumCust = false, seenNumBarbs = false, seenGrowTime = false, seenCuttingTime = false, seenNumChairs = false, seenRuntime = false;		
		final int requiredNumArgs = 12;
		int numCustomers = 0;
		int numBarbers = 0;
		int maxGrowTime = 0;
		int maxCutTime = 0;
		int numChairs = 0;
		int runtime = 0;
		Timer runtimeTimer = new Timer("Salon Close Timer");
		Salon mySalon;
		Customer[] myCustomers;
		Barber[] myBarbers;
		Thread[] myThreads;
		
		//Parse and ensure proper arguments
		if(args.length != requiredNumArgs){
			System.out.println("Insufficient number of arguments.");
			printHelpMessage();
			return;
		}
		
		while(argNum < requiredNumArgs){
			switch(args[argNum++]){
				case("-C"):
					try{
						numCustomers = Integer.parseInt(args[argNum++]);
						if(0 > numCustomers){
							printNegativeValueArguments();
							printHelpMessage();
							return;
						}
						seenNumCust = true;
					} catch(NumberFormatException e) {
						printIntegerRequired();
						printHelpMessage();
						return;
					}
					break;
				
				case("-B"):
					try{
						numBarbers = Integer.parseInt(args[argNum++]);
						if(0 > numBarbers){
							printNegativeValueArguments();
							printHelpMessage();
							return;
						}	
						seenNumBarbs = true;
					} catch(NumberFormatException e) {
						printIntegerRequired();
						printHelpMessage();
						return;
					}					
					break;
				
				case("-g"):
					try{
						maxGrowTime = Integer.parseInt(args[argNum++]);
						if(0 > maxGrowTime){
							printNegativeValueArguments();
							printHelpMessage();
							return;
						}					
						seenGrowTime = true;
					} catch(NumberFormatException e) {
						printIntegerRequired();
						printHelpMessage();
						return;
					}					
					break;
				
				case("-c"):
					try{
						maxCutTime = Integer.parseInt(args[argNum++]);
						if(0 > maxCutTime){
							printNegativeValueArguments();
							printHelpMessage();
							return;
						}		
						seenCuttingTime = true;
					} catch(NumberFormatException e) {
						printIntegerRequired();
						printHelpMessage();
						return;
					}					
					break;
				
				case("-W"):
					try{
						numChairs = Integer.parseInt(args[argNum++]);
						if(0 > numChairs){
							printNegativeValueArguments();
							printHelpMessage();
							return;
						}		
						seenNumChairs = true;
					} catch(NumberFormatException e) {
						printIntegerRequired();
						printHelpMessage();
						return;
					}						
					break;
				
				case("-R"):
					try{
						runtime = Integer.parseInt(args[argNum++]);
						if(0 > runtime){
							printNegativeValueArguments();
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
			
		}
		
		//Ensure they didn't just pass in six of the same valid argument.
		if(!seenNumCust || !seenNumBarbs || !seenGrowTime || !seenCuttingTime || !seenNumChairs || !seenRuntime){
			System.out.println("You were missing one or more of the required arguments.");
			printHelpMessage();
			return;
		}
		
		System.out.println("customers " + numCustomers + ", barber " + numBarbers + ",grow time " + maxGrowTime + 
				", cut time " + maxCutTime + ", chairs " + numChairs + ", runtime " + runtime);

		myBarbers = new Barber[numBarbers];
		myCustomers = new Customer[numCustomers];
		myThreads = new Thread[numBarbers + numCustomers];
		
		mySalon = new Salon(numChairs, numBarbers, runtime);
		
		for(int i = 0; i < numBarbers; ++i){
			myBarbers[i] = new Barber(mySalon, maxCutTime);
			myThreads[i] = new Thread(myBarbers[i], "Barber " + (i + 1));
			myThreads[i].start();
		}
		
		for(int i = 0; i < numCustomers; ++i){
			myCustomers[i] = new Customer(mySalon, maxGrowTime);
			myThreads[i + numBarbers] = new Thread(myCustomers[i], myCustomers[i].toString());
			myThreads[i + numBarbers].start();			
		}
		
		//Schedule our task to close the salon after 'runtime' seconds has elapsed.
		runtimeTimer.schedule(new SalonCloser(mySalon), (long) (runtime * millisecondsInSeconds));
		
		mySalon.startDay();
		
		//Wait for all of the Barbers and Customers to finish.
		for(Thread t:myThreads){
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		mySalon.closingPerimiterCheck();
	}

}
