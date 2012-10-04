/**
 * Anthony D'Angelo 100773125
 * Tsering Chopel 100649290
 * Florent Muyango 100709054 
 */

package edu.carleton.comp4104.assignment1;

public class Question5 {

	private final static int numArgsBarber = 12;
	private static int numCustomers = 0;
	
	public Question5() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for(String s:args){
			System.out.println(s);
		}
		// TODO Auto-generated method stub
		if(args.length != numArgsBarber){
			//TODO output error
			return;
		}
		
		int argNum = 0;
		boolean seenCustArg = false, seenNumBarbs = false, seenGrowTime = false, seenCuttingTime = false, seenNumChairs = false, seenRuntime = false;
		
		while(argNum < numArgsBarber){
			//TODO fill this in
			switch(args[argNum++]){
				case("-C"):
					numCustomers = Integer.parseInt(args[argNum++]);
					break;
				
				case("-B"):
					break;
				
				case("-g"):
					break;
				
				case("-c"):
					break;
				
				case("-W"):
					break;
				
				case("-R"):
					break;
				
				default:
					//TODO Output Error
					break;
			}
			
		}
		
		if(!seenCustArg || !seenNumBarbs || !seenGrowTime || !seenCuttingTime || !seenNumChairs || !seenRuntime){
			//TODO output error
			return;
		}

	}

}
