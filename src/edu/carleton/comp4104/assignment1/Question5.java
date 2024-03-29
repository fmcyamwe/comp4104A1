/**
 * Anthony D'Angelo 100773125
 * Tsering Chopel 100649290
 * Florent Muyango 100709054 
 */

package edu.carleton.comp4104.assignment1;

public class Question5 {
	
	private static void printHelpMessage(){
		System.out.println("This program expects the following arguments:\n"
				+ "-C <Number of Customers> -B <Number of Barbers, at least 1> " +
				"-g <Max Hair Growing Time in seconds> -c <Max Hair Cutting Time in seconds>" +
				" -W <Number of Waiting Room Chairs, at least 1> -R <program runtime in seconds>\n"
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
	 * @param args See printHelpMessage.
	 */
	public static void main(String[] args) {
		int argNum = 0;
		boolean seenNumCust = false, seenNumBarbs = false, seenGrowTime = false, seenCuttingTime = false, seenNumChairs = false, seenRuntime = false;		
		final int requiredNumArgs = 12;
		int numCustomers = 0;
		int numBarbers = 1;
		int maxGrowTime = 0;
		int maxCutTime = 0;
		int numChairs = 1;
		int runtime = 0;
		Salon mySalon;
		Customer[] myCustomers;
		Barber[] myBarbers;
		Thread[] myThreads;
		
		//Parse and ensure proper arguments
		if(args.length != requiredNumArgs){
			System.out.println("Unsuitable number of arguments.");
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
						if(1 > numBarbers){
							System.out.println("There must be at least 1 Barber. Try again.");
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
						if(1 > numChairs){
							System.out.println("There must be at least 1 chair in " +
									"the waiting room. Try again.");
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
			} /* while(argNum < requiredNumArgs) */
			
		}
		
		//Ensure they didn't just pass in six of the same valid argument.
		if(!seenNumCust || !seenNumBarbs || !seenGrowTime || !seenCuttingTime || !seenNumChairs || !seenRuntime){
			System.out.println("You were missing one or more of the required arguments.");
			printHelpMessage();
			return;
		}
		
		System.out.println("customers " + numCustomers + ", barber " + numBarbers + ", max grow time " + maxGrowTime + 
				", max cut time " + maxCutTime + ", chairs " + numChairs + ", runtime " + runtime);

		//Now that we know suitable arguments have been passed in, instantiate the required variables
		myBarbers = new Barber[numBarbers];
		myThreads = new Thread[numBarbers + numCustomers];
		//Customer array conditionally initialized below
		mySalon = new Salon(numChairs, numBarbers, runtime);
		
		//We can start these threads right away because they wait on a latch in the salon object
		for(int i = 0; i < numBarbers; ++i){
			myBarbers[i] = new Barber(mySalon, maxCutTime);
			myThreads[i] = new Thread(myBarbers[i], "Barber " + (i + 1));
			myThreads[i].start();
		}
		
		if(numCustomers > 0){
			myCustomers = new Customer[numCustomers];
			for(int i = 0; i < numCustomers; ++i){
				myCustomers[i] = new Customer(mySalon, maxGrowTime);
				myThreads[i + numBarbers] = new Thread(myCustomers[i], myCustomers[i].toString());
				myThreads[i + numBarbers].start();			
			}
		}
		
		//Releases the latch, lets our threads start at the same time. Also sets up the program timer.
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
		
		//Check if we missed any Customers in the salon.
		mySalon.closingPerimiterCheck();
		
		return;
	}

}
