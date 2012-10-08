/**
 * Anthony D'Angelo 100773125
 * Tsering Chopel 100649290
 * Florent Muyango 100709054 
 */

package edu.carleton.comp4104.assignment1;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Collections;

public class Question1{
	
	public static void main(String[] args) {
		String st = args[0];
		System.out.println(st);
		
		ArrayList<Character> theList = manageString(st);
		System.out.println(theList);
		int length = theList.size();
		
		char [] characters = new char[length];
		
		for(int i = 0;i<length; i++	){
			characters[i] = theList.get(i);
		}
		
		Printer.print = new ArrayList<Boolean>(length);
		for(int i = 0;i<length; i++	){
			Printer.print.add(i, false); // initializes the new boolean inside Arraylist 
		}
		
		Printer.print.add(0,true);
		
		for(int i = 0;i<length; i++	){
			Thread t = new Thread(new Printer(characters[i], i), "Thread "+i);
			t.start();
		}
		
		
		
	}//    ab{123[ABC]456}cd   ab[123{ABC}456]cd

	public static ArrayList<Character> manageString(String s){
		ArrayList <Character> list = new ArrayList<Character>();
		Stack <Integer> temp = new Stack<Integer>();
		for(char c: s.toCharArray()){
			list.add(c);
		}
		
		for(int index = 0; index<list.size(); index++){		
			if(list.get(index)=='{'  || list.get(index)=='['){
				temp.push(index);
			}

			if(list.get(index) == '}'){
				int pop = temp.pop();
				Collections.shuffle(list.subList(pop+1, index));
				list.remove(pop);
				index--;
				list.remove(index);
				index--;
				for(int i = pop+1; i <=index;){
					list.remove(i);
					index--;
				}
			}
			
			if(list.get(index) == ']'){
				int pop = temp.pop();
				Collections.shuffle(list.subList(pop+1, index));
				list.remove(pop);
				index--;
				list.remove(index);
				index --;
			}
		}
		return list;
	}
}
