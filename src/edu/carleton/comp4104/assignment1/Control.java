package edu.carleton.comp4104.assignment1;

import java.util.Timer;
import java.util.concurrent.ArrayBlockingQueue;

public class Control {

	private boolean kitchenOpen = true;
	private boolean dayStarted = false;
	private final int numMSsInSecs = 1000;
	private long closingTime;
	private long startTime = 0;
	private Timer runtimeTimer;
	private ArrayBlockingQueue<String> table;
	private boolean ingredientsBeingConsumed = false;
	private boolean tableClear = true;
	
	/**
	 * This Control class is used to synchronize the Agent and Eaters.
	 * @param timeToStayOpen The length of time to stay open (the time
	 * until we change the control variable signaling the threads to
	 * finish execution)
	 */
	public Control(int timeToStayOpen) {
		closingTime = (timeToStayOpen * numMSsInSecs);
		runtimeTimer = new Timer("Kitchen Close Timer");
		table = new ArrayBlockingQueue<String>(2);
		tableClear = true;
	}
	
	/**
	 * This function is called by the consuming Eater thread when they're done their meal. It lets everyone know
	 * that the Agent should start up again.
	 */
	public synchronized void clearTable(){
		tableClear = true;
		notifyAll();
		return;
	}
	
	/**
	 * This is used by Eater threads to make sure they don't try to grab off the table in between the time the table is cleared and
	 * ingredientsBeingConsumed is set back to false.
	 * @return True if tableClear is true, else false.
	 */
	public synchronized boolean tableIsClear(){
		return tableClear;
	}
	
	/**
	 * This function is used by Eaters to see if their ingredient is present on the table.
	 * If it is, they go back to sleep. If it isn't, they proceed to consume the ingredients on the table. 
	 * @param myIngredient The Eater's ingredient, one of potato, butter and water.
	 * @return True if the passed-in ingredient is missing from the table, else false if the 
	 * passed-in ingredient is on the table.
	 */
	public synchronized boolean checkTableForMissingIngredient(String myIngredient){
		if(table.contains(myIngredient) || (ingredientsBeingConsumed == true)){
			return false;
		} else {
			//This variable ensures that every round only one Eater tries to eat.
			ingredientsBeingConsumed = true;
			return true;
		}
	}
	
	/**
	 * This function is used by Eaters once they've determined there's food for them on the table.
	 * They call this twice to get the two ingredients off the table.
	 * @return If there are ingredients on the table, it returns the String representing the ingredient
	 * at the head of the list, else null to let the Eater know they tried to take from an empty table.
	 */
	public synchronized String grabNextIngredientOnTable(){
		if(!table.isEmpty()){
			try {
				return table.take();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * This function is used by the Agent to put the next ingredient on the table.
	 * @param nextIngredient The string representing the next ingredient.
	 * @return True if we could insert the ingredient, false is the table was full.
	 */
	public synchronized boolean putIngredientOnTable(String nextIngredient){
		return table.offer(nextIngredient);
	}
	
	/**
	 * This function is used by Eaters to wait until food is on the table (or until the runtime has elapsed)
	 * at which point they'll wake up and check if the food on the table is for them and check if the kitchen's closed. 
	 * 
	 * NOTE: I'd rather use barriers to signal the specific eater but the instructions make it sound like that's not allowed.
	 * The instructions in Q5 sound similar, but in Q5 without barriers I'd have to continually poll to see if the customer's
	 * still getting a haircut and that's terrible.
	 * Here, we can get away without polling if we don't use barriers, but we have to wake all Eaters up, not just the one. 
	 * The same kind of ugliness presents itself when Control has to switch back to the Agent; we have to wake everyone up
	 * and check our state.
	 * 
	 * @return True if the Eater should keep eating/waiting, false if the kitchen's about to close.
	 */
	public synchronized boolean waitForIngredients(){
		long timeRemaining = closingTime - (System.currentTimeMillis() - startTime);
		
		if(timeRemaining > 0){
			//Wait for the table to be prepared, but only wait as long as we have time left.
			try{
				long tempStart, tempEnd, tempWait;
				tempStart = System.currentTimeMillis();
				wait(((timeRemaining > 0) ? timeRemaining : 10));
				tempEnd = System.currentTimeMillis();
				tempWait = timeRemaining - (tempEnd - tempStart);
				
				while((tableClear) && (0 < tempWait)){
					//We're here if we broke out of the wait early.
					//tempWait must be positive here because that subtracted difference is always positive or in an unlikely case 0.
					//This will ensure that we never wait less than the time remaining.
					
					tempStart = System.currentTimeMillis();
					wait(tempWait);
					tempEnd = System.currentTimeMillis();
					
					tempWait = tempWait - (tempEnd - tempStart);
				}	

				//We'll only break out of that while if there is food to be consumed or if time is up.
				if(!tableClear){
					return true;
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
	/**
	 * This function is used by the Agent thread to let the Eaters know that there's food on the table and then
	 * the Agent waits here until the table is ready to be used to prepare food again.
	 * @return True if the Agent should keep producing, false if the kitchen's about to close.
	 */
	public synchronized boolean waitForClearTable(){
		long timeRemaining;
		
		//Tell the Eaters there's food on the table.
		tableClear = false;
		notifyAll();
		
		timeRemaining = closingTime - (System.currentTimeMillis() - startTime);
		
		if(timeRemaining > 0){
			//Wait for the table to be cleared. We won't be stuck because in the main, we join all other threads, clear the table, then join Agent's.
			try{
				
				while(!tableClear){
					wait();
				}	

				//We'll only break out of that while if food needs to be produced or if time is up.
				if(tableClear){
					ingredientsBeingConsumed = false;
					return true;
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return false;		
	}
	
	/**
	 * Used as a latch to synchronize all of the Eaters and the Agent at startup.
	 * @throws InterruptedException
	 */
	public synchronized void waitForDayToStart() throws InterruptedException{
		while(!dayStarted){
			wait();
		}
		return;
	}

	/**
	 * Releases the latch to start all of the Eaters and the Agent.
	 * Also starts the Timer that will eventually close the kitchen and sets the startTime.
	 */
	public synchronized void startDay(){
		dayStarted = true;
		//Schedule our task to close the kitchen after 'runtime' seconds has elapsed.
		runtimeTimer.schedule(new WatchMan(this), closingTime);
		startTime = System.currentTimeMillis();
		notifyAll();
		return;
	}
	
	/**
	 * This function is used by the Agent and Eaters to see if they should continue their run loops.
	 * @return True if the kitchen is open and they should still run their loops, false if the kitchen is closed
	 * (indicates the runtime has elapsed) and the objects should exit their run threads.
	 */
	public synchronized boolean kitchenOpen(){
		if(kitchenOpen){
			return true;
		}		
		return false;
	}	
	
	/**
	 * The WatchMan TimerTask will call this function to close the kitchen when the runtime has elapsed.
	 */
	public synchronized void closeKitchen(){
		kitchenOpen = false;
		//This function is called by the timer we're about to cancel. The currently executing task will finish.
		runtimeTimer.cancel();
		System.out.println("Sorry, kitchen is closing. Everyone finish up and get out!");
		return;
	}
	
	/**
	 * Use this to make sure messages are printed out in an orderly manner.
	 * 
	 * @param msg to print.
	 */
	public synchronized void postOnKitchenChalkboard(String msg){
		System.out.println(msg);
		return;
	}
	
}
