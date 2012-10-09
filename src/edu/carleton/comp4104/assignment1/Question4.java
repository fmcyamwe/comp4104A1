package edu.carleton.comp4104.assignment1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;
import java.util.regex.Pattern;

public class Question4 {

	/**
	 * @param args
	 */
	static String order;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String inputFile = null;
		ArrayList<String> products = new ArrayList<String>();

		for (int i = 0; i <= 4; i = i + 2) {

			switch (args[i]) {
			case "-c":
				inputFile = new java.io.File("").getAbsolutePath()
						+ File.separator + args[i + 1];
				break;
			case "-b":
				for (int e = i + 1; e < args.length; e++) {
					products.add(args[e]);
				}
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

		ArrayList<ArrayList<Character>> dependents = getContents(r);
		
		for(int i = 0; i< products.size()-1; i++){
			Thread thread = new Thread(new Worker(products.get(i).charAt(i), new CyclicBarrier(dependents.get(i+1).size()), dependents));
			thread.start();
		} 

	}

	public static ArrayList<ArrayList<Character>> getContents(FileReader inputFile) {

		String line = null;
		String REGEX = "\\s*(\\s|,)\\s*";
		Pattern p = Pattern.compile(REGEX);
		ArrayList<ArrayList<Character>> myReturn = new ArrayList<ArrayList<Character>>();
		
		try {
			// use buffering, reading one line at a time

			BufferedReader input = new BufferedReader(inputFile);

			try {

				while ((line = input.readLine()) != null) {

					String[] items = p.split(line);
					switch(items[1]){
						case "has-parts":
							myReturn.add(new ArrayList<Character>());
							ArrayList<Character> temp = myReturn.get(myReturn.size() - 1);
							temp.add(items[0].charAt(0));
							for(int i = 2; i < items.length; ++i){
								temp.add(items[i].charAt(0));
							}
							break;
							
						case "before":
							myReturn.add(new ArrayList<Character>());
							ArrayList<Character> tempBefore = myReturn.get(myReturn.size() - 1);
							//Swap them so that the X before Y becomes Y has-parts X. Not that it has those parts, but it must be built in that order.
							//Assume there is only one letter after 'before'.
							tempBefore.add(items[2].charAt(0));
							tempBefore.add(items[0].charAt(0));
							break;	
							
						default:
							System.out.println("Error: the second token on every line of the input file should be either 'has-parts' or 'before'.");
							break;
					}

				}
			} finally {

				input.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		return myReturn;
	}

}
