/**
 * Anthony D'Angelo 100773125
 * Tsering Chopel 100649290
 * Florent Muyango 100709054 
 */

package edu.carleton.comp4104.assignment1;

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

			//We want to sleep using ms, so if we can get a random time in ms, convert and do it. Otherwise, get a random number
			//of seconds (opposed to ms) and convert that to ms.
			if(((maxGrowTime + 1) * numMSsInSecs) < Integer.MAX_VALUE){
				timeToGrow = random.nextInt((maxGrowTime + 1) * numMSsInSecs); 
			} else {
				timeToGrow = random.nextInt(maxGrowTime + 1) * numMSsInSecs; 
			}	
			
			mySalon.postOnSalonMessageBoard(this + " growing hair for " + timeToGrow + " ms");
			
			try {
				Thread.sleep(timeToGrow);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
			mySalon.postOnSalonMessageBoard(this + " needs a haircut");
			
			//Blocking call
			mySalon.waitForHaircut(this);
			//We'll have left that call only if we got our hair cut or if the salon is closed. 
			//Either way, the loop will handle the next step appropriately.
			
		}
		
		return;
	}

}
