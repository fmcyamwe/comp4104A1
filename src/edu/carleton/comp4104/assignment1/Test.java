package edu.carleton.comp4104.assignment1;

import java.io.*;
import java.util.ArrayList;

import java.util.concurrent.CountDownLatch;
import java.util.regex.Pattern;


public class Test {
   
	//A STATIC ARRAY TO KEEP TRACK OF ALL THE NODES ?!? 
	
	static int nodenum = 0, channelsize = 0 , timer = 0 ;
	public static ArrayList<Node> system; 
	static CountDownLatch startSignal;
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Paths path;  //TO BE USED 
		File inputFile= null;
			
		startSignal= new CountDownLatch(1);
		
		for(int i =0;i<=6; i=i+2){
			
			switch(args[i]){
			case "-c":
				inputFile= new File("C:\\Users\\Temp\\workspace\\q2\\src\\edu\\carleton\\comp4104\\assignment1\\" + args[i+1]);
				break;
			case "-t":
				timer= Integer.parseInt(args[i+1]);
				break;
			case"-s":
				channelsize= Integer.parseInt(args[i+1]);
				break;
			}
			
		}
		
		//File inputFile = new File("C:\\Users\\Temp\\workspace\\q2\\src\\edu\\carleton\\comp4104\\assignment1\\input.txt");
	
		getContents(inputFile);
		
		System.out.println("Starting");
		startSignal.countDown();// start the threads
	
		//read the file and create the number of nodes
		//then go throught the lines and for each line create 
		// the two nodes and assign them OR create the channel 
		
		

	}
	
	public static void getContents(File aFile) {
	    
	    //StringBuilder contents = new StringBuilder();
	    int n=1;
	    String line = null; 
	      
	    String REGEX = "\\s*(\\s|,)\\s*";
	    Pattern p = Pattern.compile(REGEX);
	    try {
	      //use buffering, reading one line at a time
	      //FileReader always assumes default encoding is OK!
	      BufferedReader input =  new BufferedReader(new FileReader(aFile));
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
	        		makeThread(i,e);//prolly wil need somethin else		        		
	        			//System.out.println(i);
	        			//System.out.println(e);
	        		
	        	}
	        	
	        	//System.out.println(line); 
	        }
	      }
	      finally {
	    	  System.out.println("bonbon: " + n);  
	        input.close();
	      }
	    }
	    catch (IOException ex){
	      ex.printStackTrace();
	    }
	    
	   
	  }

	public static void makeThread(int sender, int receiver) {		
		
		for(Node i: system){ //one of the node is already present in the system
			if(i.label==sender || i.label== receiver ){
				if(i.label==sender){//the sender is present so add the channel
					i.addChannel(new channel(channelsize,i,new Node(receiver,startSignal)));
				}else if(i.label==receiver) {//the receiver is the one present........may create a weird behavior of not knowing who is the sender or receiver.
					i.addChannel(new channel(channelsize,i,new Node(sender,startSignal)));					
				}
				return; //dont continue and just get out
			}			
		}
		Node tempNode1=new Node(sender,startSignal);
		Node tempNode2= new Node(receiver,startSignal);
		system.add(tempNode1);
		system.add(tempNode2);
		
		channel tempchannel= new channel(channelsize,tempNode1,tempNode2);
		
		new Thread(tempNode1).start();
		new Thread(tempNode2).start();
		
	}

}
