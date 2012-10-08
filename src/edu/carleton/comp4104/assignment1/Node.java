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
	int label, messageCounter = 0;
	Random random = new Random();
	ArrayList<Channel> chan = new ArrayList<Channel>();
	CountDownLatch signal;
	int timer = Question2.timer;
	boolean run = true;

	Node(int i, CountDownLatch startsignal) { // need the channel too ?!?
		this.label = i;
		this.signal = startsignal;

	}

	public void addChannel(Channel ch) {
		chan.add(ch); // dont i add the node instead?
	}

	@Override
	public void run() {

		try {
			signal.await();
			// create the messages?!?
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
			for (Channel ch : chan) {
				Message temp = createMessage();
				send(temp, ch);

			}
			for (Channel ch : chan) {
				Message temp = receive(ch);
				if (temp.destination == this.label) {
					System.out.println("Node " + this.label
							+ " received message from node " + temp.source
							+ " at " + temp.time + ": " + temp.msg);
				}
			}
			

			timer--;
		}

		// TODO Auto-generated method stub

		// create the random messages and send them or receive them...have to
		// make a way to
		// make a thread either send or receive only!

	}

	public Message createMessage() {
		int destination = random.nextInt(Question2.system.size());
		String message = "message number " + messageCounter;

		Message temp = new Message(this.label, destination, message, "TimeNow");
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

	// this is a weird one...y return the message?
	public Message receive(Channel recevingChannel) {

		return recevingChannel.take(this);

	}

}
