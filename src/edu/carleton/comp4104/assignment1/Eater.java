package edu.carleton.comp4104.assignment1;

public class Eater implements Runnable {

	private String myIngredient;
	private java.util.Random randomizer;
	private int maxTimeToEat;
	Control myKitchen;	
	private final int numMSsInSecs = 1000;
	
	public Eater(String ingr, int eatTime, Control myControl) {
		myIngredient = ingr;
		randomizer = new java.util.Random();
		maxTimeToEat = eatTime;
		myKitchen = myControl;		
	}

	@Override
	public void run() {

		try {
			myKitchen.waitForDayToStart();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(myKitchen.kitchenOpen()){
			if(myKitchen.waitForIngredients()){
				if(myKitchen.checkTableForMissingIngredient(myIngredient)){
					//This Eater has the missing ingredient, he can make himself food.
					String first, second;
					first = myKitchen.grabNextIngredientOnTable();
					second = myKitchen.grabNextIngredientOnTable();
					
					if((first == null) || (second == null)){
						myKitchen.postOnKitchenChalkboard("Eater tried to take from an empty table! We're going to skip his action, " +
								"hope the next round fares better.");
					} else {
						//By adding one, we allow for the max time value to be chosen as well.
						int timeToEat = randomizer.nextInt(maxTimeToEat + 1);
						
						try {
							Thread.sleep(timeToEat * numMSsInSecs);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
						
						myKitchen.postOnKitchenChalkboard("Eater had " + myIngredient + ", picked up " + first + " and "
								+ second +" and ate food in " + timeToEat +" seconds");
					}
					
					myKitchen.clearTable();
				} /* if(myKitchen.checkTableForMissingIngredient(myIngredient)) */
			} else {
				//If the if's condition returned false, the kitchen is about to close. Sleep for a little bit so our loop
				//condition works properly.
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			} /* if(myKitchen.waitForIngredients()) */
		} /* while(myKitchen.kitchenOpen()) */

		return;
	}

}
