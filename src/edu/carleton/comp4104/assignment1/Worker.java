package edu.carleton.comp4104.assignment1;

import java.util.concurrent.CyclicBarrier;

public class Worker implements Runnable {
	int numOfWorkers;
	CyclicBarrier barrier;
	String character;
	
	public Worker(int i,String c, CyclicBarrier b){
		this.numOfWorkers=i;
		this.barrier=b;
		this.character=c;
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(numOfWorkers<2){
			System.out.println("Build"+ character);
			
		}else{
			CyclicBarrier b = new CyclicBarrier(numOfWorkers-1);
			//new Thread(new Worker()).start();
		}
		
	}

}
