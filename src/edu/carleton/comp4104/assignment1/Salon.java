/**
 * Anthony D'Angelo 100773125
 * Tsering Chopel 100649290
 * Florent Muyango 100709054 
 */

package edu.carleton.comp4104.assignment1;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class Salon {

	private boolean salonOpen = true;
	private long startTime = 0;
	private long closingTime;
	private ArrayBlockingQueue<Customer> waitingChairs;
	private ArrayBlockingQueue<Customer> barberChairs;
	private HashMap<Customer, CyclicBarrier> paymentBarriers;
	private final int numMSsInSecs = 1000;
	private boolean dayStarted = false;
	
	public Salon(int numChairs, int numBarbers, int timeToStayOpen) {
		waitingChairs = new ArrayBlockingQueue<Customer>(numChairs);
		barberChairs = new ArrayBlockingQueue<Customer>(numBarbers); 
		paymentBarriers = new HashMap<Customer, CyclicBarrier>(numChairs);
		closingTime = (timeToStayOpen * numMSsInSecs);
	}
	
	/**
	 * Used as a latch to synchronize all of the Customers and Barbers at startup.
	 * @throws InterruptedException
	 */
	public synchronized void waitForDayToStart() throws InterruptedException{
		while(!dayStarted){
			wait();
		}
		return;
	}
	
	/**
	 * Releases the latch to start all of the Customers and Barbers.
	 * Also sets the startTime.
	 */
	public synchronized void startDay(){
		dayStarted = true;
		startTime = System.currentTimeMillis();
		notifyAll();
		return;
	}
	
	/**
	 * Use this to make sure messages are printed out in an orderly manner as well as to allow us to keep 
	 * the Customer and Barber ignorant to the time since we opened.
	 * 
	 * @param msg to print. 'time = <current time in execution> secs,' will be prepended.
	 */
	public synchronized void postOnSalonMessageBoard(String msg){
		System.out.println("time=" + ((System.currentTimeMillis() - startTime)/ numMSsInSecs) + " secs, " + msg);
		return;
	}
	
	/**
	 * This function is used by Customers and Barbers to see if they should continue their run loops.
	 * @return True if the salon is open and they should still run their loops, false if the salon is closed
	 * (indicates the runtime has elapsed) and the objects should exit their run threads.
	 */
	public synchronized boolean salonOpen(){
		if(salonOpen){
			return true;
		}
		
		return false;
	}
	
	/**
	 * The SalonCloser TimerTask will call this function to close the salon when the runtime has elapsed.
	 */
	public synchronized void closeSalon(){
		salonOpen = false;
		System.out.println("Sorry, salon is closing. Everyone inside will get a cut but we won't be accepting new customers today.");
		return;
	}
	
	/**
	 * This function is used by Customers to get themselves into the Salon waiting room. This is a blocking call.
	 * The Customer will only wait as long as the salon will remain open. That way whoever's not
	 * in the waiting room when the timer goes off won't be stuck.
	 * Once in the waiting room, the Customer waits until their hair is cut.
	 * @param c The Customer that is trying to get into the waiting room to cut their hair.
	 */
	public synchronized void waitForHaircut(Customer c){
		long timeRemaining = closingTime - (System.currentTimeMillis() - startTime);
		
		if(timeRemaining > 0){
			try {				
				//Try to get into the waiting room, only wait max as long as the salon will remain open, that way whoever's not
				//in the waiting room when the timer goes off won't be stuck
				if(waitingChairs.offer(c, timeRemaining, TimeUnit.MILLISECONDS)){
					
					postOnSalonMessageBoard(c + " in room, waiting=" + waitingChairs.size());
					if(!paymentBarriers.containsKey(c)){
						paymentBarriers.put(c, new CyclicBarrier(2));
					}
					//Now that we're in the waiting room, wait until our hair is cut.
					CyclicBarrier haircutDone = paymentBarriers.get(c);
					if(haircutDone != null){
						try {
							haircutDone.await();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (BrokenBarrierException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}			
					}					
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return;
	}
	
	/**
	 * This function is used by Barbers.
	 * It does two things: it finishes processing on the Customer whose hair they just finished cutting and
	 * it grabs the next waiting Customer. This is a blocking call. 
	 * If no Customers arrive by the time the salon closes, we return null.
	 * We also return null if we had an InterruptedException while waiting.  
	 * @param oldCustomer The Customer whose hair we just finished cutting.
	 * @return The next Customer in line to have their hair cut or null if we timed out or had an exception while waiting for 
	 * our next Customer.
	 */
	public synchronized Customer nextCustomer(Customer oldCustomer){
		Customer c = null;
		long timeRemaining = closingTime - (System.currentTimeMillis() - startTime);

		//On the first run, the oldCustomer will be null.
		//If we had a Customer, remove them from processing by taking them out of the barberChairs and signaling their barrier.
		if(oldCustomer != null){
			barberChairs.remove(oldCustomer);
			if(paymentBarriers.containsKey(oldCustomer)){
				try {
					//This will trip the barrier that the Customer's waiting on.
					CyclicBarrier temp = paymentBarriers.get(oldCustomer);
					if(temp != null){
						temp.await();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				paymentBarriers.remove(oldCustomer);
			} else {
				System.out.println("Error: A Barber had a Customer that wasn't waiting for their hair to be cut.");
			}
		}
		
		//We only print part of the potential string here, then the rest if necessary later so don't use postOnSalonMessageBoard.
		System.out.print("time=" + ((System.currentTimeMillis() - startTime)/ numMSsInSecs) + " secs, " +
		"Barber free");
		
		if((timeRemaining > 0) || (customerWaiting())){
			try {
				System.out.println(", waiting for a customer");
				//Try to grab the next waiting customer, only wait max as long as the salon will remain open, that way whoever's not
				//cutting hair when the timer goes off won't be stuck
				c = waitingChairs.poll(timeRemaining, TimeUnit.MILLISECONDS);
				
				//If there is a Customer whose hair we must cut, put them in a Barber's chair.
				if(c != null){
					barberChairs.put(c);
					postOnSalonMessageBoard("Barber has customer, waiting=" + waitingChairs.size());
					postOnSalonMessageBoard("Barber cutting hair");			
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			if(!salonOpen){
				System.out.println(", no more customers, going home");
			}
			System.out.print("\n");
		}
		
		return c;
	}
	
	/**
	 * This function is used by Barbers to make sure they don't leave until all the Customers are gone.
	 * @return True if any Customer is waiting for a haircut, else false
	 */
	public synchronized boolean customerWaiting(){
		return !waitingChairs.isEmpty();
	}
	
	/**
	 * This function is used by Customers to see if they're in the salon either waiting for a cut or being cut.
	 * @param c The Customer to check for in the salon.
	 * @return True if the Customer is waiting for a cut or being cut, false if the Customer's not in the salon.
	 */
	public synchronized boolean inSalon(Customer c){
		//paymentBarriers will only contain the key if (waitingChairs.contains(c) || barberChairs.contains(c))) is true
		return paymentBarriers.containsKey(c);
	}
	
	public synchronized void closingPerimiterCheck(){
		if(!salonOpen && (!barberChairs.isEmpty() || !waitingChairs.isEmpty())){
			System.out.println("\nUh-oh! The salon's closed, the barbers have gone home but there are still customers in here!");
		}
	}

}
