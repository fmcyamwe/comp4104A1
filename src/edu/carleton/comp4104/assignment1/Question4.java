package edu.carleton.comp4104.assignment1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;



public class Question4 {

	/**
	 * @param args
	 */
	static String order;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File inputFile= null;
		ArrayList<String>  products=new ArrayList<String>(); 
		
		for(int i =0;i<=4; i=i+2){
			
			switch(args[i]){
			case "-c":
				inputFile= new File("C:\\Users\\Temp\\workspace\\q2\\src\\edu\\carleton\\comp4104\\assignment1\\" + args[i+1]);
				break;
			case"-b":
				for(int e = i+1 ; e < args.length; e++ ){
					products.add(args[e]);
				}
				break;
			}
			
		}
		
		getContents(inputFile);

	}

	public static void getContents(File inputFile) {
		
		  String line = null; 
		String REGEX = "\\s*(\\s|,)\\s*";
	    Pattern p = Pattern.compile(REGEX);
	    
	    try {
		      //use buffering, reading one line at a time
		      
		      BufferedReader input =  new BufferedReader(new FileReader(inputFile));
		      try{ 
		    	  
		        while (( line = input.readLine()) != null){
	        		
		        		String[] items = p.split(line);
		        		
		        		for(int i =0 ; i< items.length;i++){
		        			System.out.println(items[i]);
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

}
