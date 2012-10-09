/**
 * Anthony D'Angelo 100773125
 * Tsering Chopel 100649290
 * Florent Muyango 100709054 
 */

package edu.carleton.comp4104.assignment1;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Node implements Runnable {
	int label, messageCounter = 1;
	Random random = new Random();
	ArrayList<Channel> chan = new ArrayList<Channel>();
	ArrayList<Message> messages = new ArrayList<Message>(100);
	CountDownLatch signal;
	int timer = Question2.timer ; //better way to time each node thread has to be implemented...
	boolean run = true;

	Node(int i, CountDownLatch startsignal) { 
		this.label = i;
		this.signal = startsignal;

	}

	public void addChannel(Channel ch) {
		chan.add(ch); 
	}

	@Override
	public void run() {
		
		try {
			signal.await(); //latch that starts all the node threads
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while (run) {
			// decrement the timer till it hits zero and u change the boolean
			// run
			if (timer <= 0) {
				run = false;
			}
			
			Random r = new Random();
			try {
				Thread.sleep(r.nextInt(101));

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
				Message temp = createMessage(); 
		//attempts to send the message on the first channel...else it sends it to all the channels
				if(!this.chan.isEmpty()){
					if(chan.get(0).node1.label == temp.source)						
						send(temp, chan.get(0)); 
					else{
						for(Channel ch:chan){							
							send(temp,ch);
							}
						}				
				}
				
			
			Message tem;
			for (Channel ch : chan) {				
					tem = receive(ch);			
				
				if (tem.destination == this.label) {
					System.out.println("Node " + this.label
							+ " received message from node " + tem.source
							+ " at " + tem.time + ": " + tem.msg);
				}else{
					//add it the list of message received that are not intended for you
					if(!messages.contains(tem)){
						messages.add(tem);
					}
					//then broadcast it to all the channels except the one we are in.
					for(Channel h : chan){
						if(h.equals(ch)) return;
						else send(temp,ch); System.out.println("Message not intend for me..redirecting..");
					}					
				}
			}
		

			timer--;  
			
		}
		
		System.out.println("Thread done");

		// TODO Auto-generated method stub
	}

	public Message createMessage() {
		//picks a node number randomly from the system nodes
		int destination = random.nextInt(Question2.system.size());
		String message = "message number " + messageCounter;

		Message temp = new Message(this.label, destination, message, timer);
		messageCounter++;

		return temp;
	}

	public void send(Message e, Channel h) {
		for (Channel ch : chan) {
			if (ch == h) {
				ch.put(e, this);
			}
		}

	}

	//return the message
	public Message receive(Channel recevingChannel) {

		return recevingChannel.take(this);
	}

}
