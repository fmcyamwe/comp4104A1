package edu.carleton.comp4104.assignment1;

import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Worker implements Runnable {
	CyclicBarrier barrier;
	char character;
	ArrayList<ArrayList<Character>> dependents = new ArrayList<ArrayList<Character>>();
	Stack <String> stack = new Stack<String> ();
	boolean [] check = new boolean[dependents.size()];
	
	public Worker(char c, CyclicBarrier b, ArrayList<ArrayList<Character>> dependents ){
		System.out.println(c);
		this.barrier=b;
		this.character=c;
		this.dependents = dependents;
	}
	

	public void run() {
		for(int j = 0; j < check.length; j++){
			if(check[j] == false){
				for(int index = 0; index < dependents.size(); index++){
					for(char c: dependents.get(index)){
						if(c == character){
							for(int i = 0; i< dependents.get(index).size(); i++){
								System.out.println("i am here");
								Thread thread = new Thread(new Worker(dependents.get(index).get(i), new CyclicBarrier(dependents.get(i+1).size()), dependents));
								thread.start();
							}
						}
					}
				check[index] = true;
				}
			}
		}
		
		stack.push("build "+ character);
		try {
			barrier.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
		
		System.out.println(stack.pop());
		
	}

}