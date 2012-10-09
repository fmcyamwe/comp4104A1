/**
 * Anthony D'Angelo 100773125
 * Tsering Chopel 100649290
 * Florent Muyango 100709054 
 */

package edu.carleton.comp4104.assignment1;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Pattern;



public class Question2 {


	
	static int nodenum = 0, channelsize = 0 , timer = 0 ;
	public static ArrayList<Node> system; 
	static CountDownLatch startSignal;
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Paths path = null;  //TO BE USED 
		String inputFile= null;
			
		startSignal= new CountDownLatch(1);
		
		for(int i =0;i<=6; i=i+2){
			
			switch(args[i]){
			case "-c":
				inputFile= new java.io.File("").getAbsolutePath() + File.separator  + args[i+1];
				break;
			case "-t":
				timer= Integer.parseInt(args[i+1]);
				break;
			case"-s":
				channelsize= Integer.parseInt(args[i+1]);
				break;
			}
			
		}
		
			FileReader r = null;
			try {
				r = new FileReader(inputFile);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			getContents(r);
		
		System.out.println("Starting the threads...");
		
		startSignal.countDown();// start the threads
	
		
		
		
		

	}
	
	//read the file and create the number of nodes
	//then go throught the lines and for each line create 
	// the two nodes and assign them OR create the channel 
	public static void getContents(FileReader aFile) {
	    
	    //StringBuilder contents = new StringBuilder();
	    int n=1;
	    String line = null; 
	      
	    String REGEX = "\\s*(\\s|,)\\s*";
	    Pattern p = Pattern.compile(REGEX);
	    try {
	      //use buffering, reading one line at a time
	    
	      BufferedReader input =  new BufferedReader(aFile);
	      try{ 
	    	  
	        while (( line = input.readLine()) != null){
	        	if(nodenum == 0){
	        		nodenum = Integer.parseInt(line); 
	        		 
	        		system = new ArrayList<Node>(nodenum);
	        		
	        		
	        	}
	        	else{	        		
	        		String[] items = p.split(line);	        		
	        	
	        		int i = Integer.parseInt(items[0]);
	        		int e = Integer.parseInt(items[1]);
	        		makeThread(i,e);//	        		
        		
	        	}
	        	
	        
	        }
	      }
	      finally {
	    	  
	        input.close();
	      }
	    }
	    catch (IOException ex){
	      ex.printStackTrace();
	    }
	    
	   
	  }

	public static void makeThread(int sender, int receiver) {
		
		//checks if one of the nodes is already present in the system
		for(Node i: system){ 
			if(i.label==sender || i.label== receiver ){
				if(i.label==sender){//the sender is present so add the channel
					i.addChannel(new Channel(channelsize,i,new Node(receiver,startSignal)));
				}else if(i.label==receiver) { 
					i.addChannel(new Channel(channelsize,new Node(sender,startSignal),i));					
				}
				return; //dont continue and just get out
			}			
		}
		Node tempNode1=new Node(sender,startSignal);
		Node tempNode2= new Node(receiver,startSignal);
		system.add(tempNode1);
		system.add(tempNode2);
		
		new Channel(channelsize,tempNode1,tempNode2);
		
		new Thread(tempNode1).start();
		new Thread(tempNode2).start();
		
		
	}

}
