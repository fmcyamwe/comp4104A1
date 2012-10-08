package edu.carleton.comp4104.assignment1;

public class Agent implements Runnable {

	private String[] ingredients;
	private java.util.Random randomizer;
	Control myKitchen;
	
	public Agent(String ingredientOne, String ingredientTwo, String ingredientThree, Control myControl) {
		ingredients = new String[3];
		ingredients[0] = ingredientOne;
		ingredients[1] = ingredientTwo;
		ingredients[2] = ingredientThree;
		randomizer = new java.util.Random();
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
		
		//Introduce an initial delay that will hopefully allow the three Eaters to get to their blocking state
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		
		while(myKitchen.kitchenOpen()){
			
			int firstIngrIndex, secondIngrIndex;
			
			//Randomly choose two different ingredients to add to the table.
			firstIngrIndex = randomizer.nextInt(3);
			do{
				secondIngrIndex = randomizer.nextInt(3);
			} while(firstIngrIndex == secondIngrIndex);
			
			if(!myKitchen.putIngredientOnTable(ingredients[firstIngrIndex]) || !myKitchen.putIngredientOnTable(ingredients[secondIngrIndex])){
				myKitchen.postOnKitchenChalkboard("Uh-oh! We tried to put more than two ingredients on the table!");
			} else {
				myKitchen.postOnKitchenChalkboard("Agent places " + ingredients[firstIngrIndex] + 
						" and " + ingredients[secondIngrIndex] + " on the table.");
			}
			
			//Blocking call!
			if(myKitchen.waitForClearTable()){
				//If the condition's call returns false, the kitchen's about to close.
				//Still, sleep little bit so our loop condition works properly.
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
			
		} /* while(myKitchen.kitchenOpen()) */
		
		return;
	}

}
