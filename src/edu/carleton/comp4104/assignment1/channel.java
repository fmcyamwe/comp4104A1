package edu.carleton.comp4104.assignment1;

import java.io.IOException;
import java.nio.channels.Channel;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class channel implements Channel {
	
	int size ;
	static int currentSize;
	Node node1,node2;
	ArrayBlockingQueue<Message> bufferNode1;
	ArrayBlockingQueue<Message> bufferNode2;
	
	public channel(int s, Node node1, Node node2 ){
		this.node1=node1;
		this.node2=node2;
		this.size=s;
		bufferNode1= new ArrayBlockingQueue<Message>(size);
		bufferNode2= new ArrayBlockingQueue<Message>(size);
		
		if(!(node1.chan.contains(this))){node1.chan.add(this);}
		if(!(node2.chan.contains(this))){node2.chan.add(this);}
		
	}
	 
	public void put(Message mes, Node SendingNode){
		
		/*while(currentSize >= size){
			try {
				System.out.println("The channel between Node "+node1.label+" and Node "+ node2.label+ " is full! waiting...");
				Thread.currentThread().sleep(new Random().nextInt(100));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		*/
		if(SendingNode == node1){
			try {
				synchronized(bufferNode1){
					bufferNode1.put(mes);
					currentSize++;
					bufferNode1.notifyAll();
				}				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{ //its the second node sending so you put it in the second buffer
			try {
				synchronized(bufferNode2){
					bufferNode2.put(mes);
					currentSize++;
					bufferNode2.notifyAll();
				}				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public  Message take(Node receivingNode){
		Message message = null;	
		
		/*while(currentSize <= 0){
			try {
				System.out.println("The channel between Node "+node1.label+" and Node "+ node2.label+ " is empty! waiting...");
				Thread.currentThread().sleep(new Random().nextInt(100));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		*/
		if(receivingNode == node1){
			try {
				synchronized(bufferNode2){
					while(bufferNode2.size()<=0){
						bufferNode2.wait();
					}
					
					message=bufferNode2.take();
					currentSize--;
					bufferNode2.notifyAll();
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			try {
				synchronized(bufferNode1){
					while(bufferNode1.size()<=0){
						bufferNode1.wait();
					}
					
					message=bufferNode1.take();
					currentSize--;
					bufferNode1.notifyAll();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return message;
		
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isOpen() {
		// TODO Auto-generated method stub
		return false;
	}

}
