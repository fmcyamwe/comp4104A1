/**
 * Anthony D'Angelo 100773125
 * Tsering Chopel 100649290
 * Florent Muyango 100709054 
 */

package edu.carleton.comp4104.assignment1;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Customer implements Runnable{

	private static int numInstances = 0;
	private int myInstance;
	private Salon mySalon;
	private int maxGrowTime;
	private java.util.Random random;
	private final int numMSsInSecs = 1000;	
	
	public Customer(Salon s, int growTime) {
		myInstance = ++numInstances;
		mySalon = s;
		maxGrowTime = growTime;
		random = new java.util.Random();
	}
	
	public String toString(){
		return "Customer " + myInstance;
	}
	
	public int getInstanceNumber(){
		return myInstance;
	}
	
	@Override
	public boolean equals(Object o){
		//'http://stackoverflow.com/questions/185937/overriding-the-java-equals-method-quirk' for a reminder of how to do this.
		if(o == null){
			return false;
		}
		
		if( !(o instanceof Customer) ){
			return false;
		}
		
		if(o == this){
			return true;
		}
		
		Customer other = (Customer) o;
		
		return (other.getInstanceNumber() == myInstance);
	}
	
	@Override
	public void run(){

		try {
			mySalon.waitForDayToStart();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(mySalon.salonOpen()){
			long timeToGrow;
			//This barrier is returned by the salon. We wait on it to know when we're done getting our hair cut.
			CyclicBarrier haircutDone;

			//We want to sleep using ms, so if we can get a random time in ms, convert and do it. Otherwise, get a random number
			//of seconds (opposed to ms) and convert that to ms.
			if(((maxGrowTime * numMSsInSecs) + 1) < Integer.MAX_VALUE){
				timeToGrow = random.nextInt((maxGrowTime * numMSsInSecs) + 1); 
			} else {
				timeToGrow = random.nextInt(maxGrowTime + 1) * numMSsInSecs; 
			}	
			
			mySalon.postOnSalonMessageBoard(this + " growing hair for " + timeToGrow + " ms");
			
			//Make sure we grow hair for at least the time we say we will
			try {
				long tempStart, tempEnd, tempWait;
				tempStart = System.currentTimeMillis();				
				Thread.sleep(timeToGrow);
				tempEnd = System.currentTimeMillis();	
				tempWait = timeToGrow - (tempEnd - tempStart);
				
				while(0 < tempWait){
					//There's still some time left to grow
					tempStart = System.currentTimeMillis();
					Thread.sleep(tempWait);
					tempEnd = System.currentTimeMillis();
					
					tempWait = tempWait - (tempEnd - tempStart);
				}				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
			mySalon.postOnSalonMessageBoard(this + " needs a haircut");
			
			//Blocking call
			haircutDone = mySalon.waitForHaircut(this);
			//We'll have left that call only if we got into the waiting room or if the salon is closed.
			//If we got back a non-null value for the Barrier, we're awaiting a haircut.
			
			if(haircutDone != null){
				try {
					//Now that we're in the waiting room, wait until our hair is cut.
					haircutDone.await();
					mySalon.postOnSalonMessageBoard(this + " has finished getting their hair cut");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
			} else {
				//If we're returned a null Barrier, the salon is about to close. Sleep for a little bit so our loop
				//condition works properly.
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		} /* while(mySalon.salonOpen()) */
		
		mySalon.postOnSalonMessageBoard(this + " went home because the salon was closed.");
		
		return;
	}

}
