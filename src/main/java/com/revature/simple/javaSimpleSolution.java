package com.revature.simple;

import java.util.Arrays;
import java.util.Stack;

import org.apache.log4j.Logger;

public class javaSimpleSolution implements JavaSimple{

	private static final Logger LOGGER = Logger.getLogger(javaSimpleSolution.class);
	
	@Override
	//Casts a given double into an int
	public int castToInt(double n) {
		return (int) n;
	}

	@Override
	//Casts a given short into a byte
	public byte castToByte(short n) {
		return (byte) n;
	}

	@Override
	//Divides two doubles
	public double divide(double operandOne, double operandTwo) throws IllegalArgumentException {
		//we make sure the denominator isn't zero and throw an exception if it is.
		try {
			if(operandTwo==0.0) {
				throw new IllegalArgumentException();
			}
			
		}catch (IllegalArgumentException iae) {
			LOGGER.error("Cannot divide by zero!");
		}
		
		return operandOne/operandTwo;
	}

	@Override
	//Checks if a given number is even or not.
	public boolean isEven(int n) {
		//Divide the given number by 2. If it's odd, it should end up with a '.5' at the end.
		double decNum = ((double) n) / 2.0;
		
		//converting a double to an int always truncates any decimal. So (double) 3.5 would become (int) 3 .
		int wholeNum = castToInt(decNum);
		
		//if the decNum ends in '.5', this would make 5 part of the whole number (3.5 -> 35.0).
		decNum *= 10.0;
		//makes the wholeNum have the same amount of whole number digits, for proper comparison.
		wholeNum *= 10;
		
		//if n is even, wholeNum and decNum would be the same number and end in 0.
		//if n is odd, decNum would have a 5 at the end instead of a 0.
		if(wholeNum==castToInt(decNum)) {
			return true;
		}
		return false;
	}

	@Override
	//Returns whether all of a given array's int elements are even or not.
	public boolean isAllEven(int[] array) {
		//for each value in the array...
		for(int i: array) {
			//...check if it's even. If even one value is not even, return false.
			if(!isEven(i))
				return false;
		}
		return true;
	}

	@Override
	//Returns the average of the values within a given array
	public double average(int[] array) throws IllegalArgumentException {
		//check to make sure the array isn't empty
		try {
			if(array.length==0) {
				throw new IllegalArgumentException();
			}
		}catch (IllegalArgumentException iae) {
			LOGGER.error("Cannot average an empty array!");
		}
		
		//gets the sum of all values and divides it by the number of values to get the average
		double sum = 0.0;
		for(int i:array) {
			sum += (double) i;
		}
		double avg = sum/((double)array.length);
		
		return avg;
	}

	@Override
	public int max(int[] array) throws IllegalArgumentException {
		//check to make sure the array isn't empty
		try {
			if(array.length==0) {
				throw new IllegalArgumentException();
			}
		}catch (IllegalArgumentException iae) {
			LOGGER.error("Cannot find the max value of an empty array!");
		}
		
		//have the first value be the current maximum value and compare it to others
		//if another value is found to be greater, make that the new max value
		int currentMax = array[0];
		for(int i:array) {
			if(i>currentMax) {
				currentMax = i;
			}
		}
		
		return currentMax;
	}

	@Override
	public int fibonacci(int n) throws IllegalArgumentException {
		//make sure they didn't request a negative value
		try {
			if(n<0) {
				throw new IllegalArgumentException();
			}
		}catch (IllegalArgumentException iae) {
			LOGGER.error("The fibonacci sequence doesn't go less than zero!");
		}
		
		//the nth value is calculated and returned
		if(n>=1) {
			//sum1 and sum2 represent the current two values being added for the sequence
			int sum1 = 1;
			int sum2 = 1; 
			//since we calculate two values from the sequence at a time, i increases by 2 for each step.
			for(int i=1; i<=n; i+=2) {
				if(i==n) {
					return sum1;
				}else if(n==(i+1)) {
					return sum2;
				}
				
				sum1 += sum2;
				sum2 += sum1;
			}
		}
		
		//the default return is zero, which should be called if n<1
		return 0;
	}

	@Override
	//returns a sorted version of the given array
	public int[] sort(int[] array) throws IllegalArgumentException {
		//check to make sure the array isn't empty
		try {
			if(array.length==0) {
				throw new IllegalArgumentException();
			}
		}catch (IllegalArgumentException iae) {
			LOGGER.error("Cannot sort an empty array!");
		}
		
		int[] sorted = new int[array.length];
		int currentMax;
		int maxIndex = 0;
		
		//takes one value out at a time and puts them in increasing order in the sorted array.
		for(int i=(array.length-1); i>=0; i--) {
			
			currentMax = Integer.MIN_VALUE;
			for(int m=0; m<array.length; m++) {
				if(array[m]>currentMax) {
					currentMax = array[m];
					maxIndex = m;
				}
			}
			
			sorted[i] = currentMax;
			array[maxIndex] = Integer.MIN_VALUE;
		}
		
		return sorted;
	}

	@Override
	//calculates and returns the factorial of the given number
	public int factorial(int n) throws IllegalArgumentException {
		//make sure they didn't request a negative value
		try {
			if(n<0) {
				throw new IllegalArgumentException();
			}
		}catch (IllegalArgumentException iae) {
			LOGGER.error("Cannot give the factorial of a number less than 0!");
		}
		
		//since n=0 would render the following loop useless, we check for that first.
		if(n==0) {
			return 1;
		}
		
		//a loop calculates the factorial, starting with 1.
		int fac = 1;
		for(int i=1; i<=n; i++) {
			fac *= i;
		}
		
		return fac;
	}

	@Override
	public int[] rotateLeft(int[] array, int n) throws IllegalArgumentException {
		//make sure they didn't request a negative value or give an empty array
		try {
			if((n<0)||(array.length==0)) {
				throw new IllegalArgumentException();
			}
		}catch (IllegalArgumentException iae) {
			LOGGER.error("Cannot shift a negative amount! Cannot shift an empty array!");
		}
		
		//there are only 'array.length' amount of possible rotated sequences, 
		//	so we shrink n represent one of the initial 'array.length' number of sequences.
		n %= array.length;
		
		//if n=0, no rotation needs to take place, as the elements end up in their initial spots.
		if(n==0) {
			return array;
		}
		
		//for each element, calculate it's new index using its original index and n.
		int newIndex;
		int[] shifted = new int[array.length];
		for(int index=0; index<array.length; index++) {
			newIndex = index-n;
			//if the new index falls out of bounds, add array.length to the number,
			//	automatically "bringing it around" to the other end of the array.
			if(newIndex<0) {
				newIndex += array.length;
			}
			//place the element in its new location
			shifted[newIndex] = array[index];
		}
		return shifted;
	}

	@Override
	//checks if the given number is prime
	public String isPrime(int n) throws IllegalArgumentException {
		/*
		 * Readable version of the code:
		 * 
		 * if(n<=1)
		 * 		return "false";
		 * if(n<=3)
		 * 		return "true";
		 * if((n%2==0)||(n%3==0))
		 * 		return "false";
		 * 
		 * int i = 5;
		 * while((i*i)<=n){
		 * 		if((n%i==0)||(n%(i+2)==0))
		 * 			return "false";
		 * 		i += 6;
		 * }
		 * return "true";
		 * 
		 */
		
		//make sure they didn't request a negative value
		try {
			if(n<0) {
				throw new IllegalArgumentException();
			}
		}catch (IllegalArgumentException iae) {
			LOGGER.error("Cannot check if a number less than 0 is prime!");
		}
		
		//0 and 1 aren't prime, but 2 and 3 are. 		...(man, this looks ugly.)
		return ((n<=1) ? "false":
					((n<=3) ? "true":
						(((n%2==0)||(n%3==0)) ? "false":
							(isPrimeHelper(n, 5)
							)
						)
					)
				);
	}
	//since while loops can't be typed within a ternary, we resort to recursion.
	public String isPrimeHelper(int n, int i) {
		return (((i*i)>n) ? "true":
					(((n%i==0)||(n%(i+2)==0)) ? "false":
						(isPrimeHelper(n, (i+6))
						)
					)
				);
	}

	@Override
	//checks whether the brackets in a given string are balanced or not.
	public boolean balancedBrackets(String brackets) throws IllegalArgumentException {
		/*
		 * Using Stack class.
		 * methods are:
		 * Stack.push() ~ pushes an element to top of stack
		 * Stack.pop() ~ pops and returns the element on top
		 * Stack.peek() ~ returns the element on top without popping it
		 * Stack.empty() ~ checks if the stack is empty
		 * 
		 * more info: https://docs.oracle.com/javase/7/docs/api/java/util/Stack.html
		 * 
		 */
		
		//make sure they didn't send an empty or null string
		try {
			if((brackets==null)||(brackets.isEmpty())){
				throw new IllegalArgumentException();
			}
		}catch (IllegalArgumentException iae) {
			LOGGER.error("Cannot check an empty string!");
		}
		//converts the sting into a character array so brackets are easier to grab individually
		char[] stringArr = brackets.toCharArray();
		//an uneven number of brackets is automatically unbalanced.
		if(stringArr.length%2!=0) {
			return false;
		}
		
		//we import java.util.Stack and make a new Stack object
		//	Like ArrayList, you should parameterize stack with the type of elements it'll be handling.
		Stack<Character> st = new Stack<Character>();
		 
		
		for(char c : stringArr) {
			//first check if the character is an open bracket
			if((c=='(')||(c=='[')||(c=='{')) {
				//if it is, push it into the stack
				st.push(c);
			} 
			//else, if it's a closed bracket...
			else {
				//...pop the most recent open bracket from the stack 
				//		and compare it to the current closed bracket, c.
				
				char open = st.pop();
				
				//compare the open and close brackets
				switch(open) {
					case '(':
						if(c!=')') {
							return false;
						}
						break;
					case '[':
						if(c!=']') {
							return false;
						}
						break;
					case '{':
						if(c!='}') {
							return false;
						}
						break;
		}	}	}
		
		//finally, check for any open brackets left in the stack, making the given string unbalanced.
		if(!st.empty()) {
			return false;
		}
		//if it passes all the tests, then it's balanced!
		return true;
	}
	
	public static void main(String args[]) {
		javaSimpleSolution jss = new javaSimpleSolution();
		
		LOGGER.info(jss.castToInt(0.0)); //= 0
		LOGGER.info(jss.castToInt(3.1)); //= 3
		
		LOGGER.info(jss.castToByte((short)2)); //= 2
		LOGGER.info(jss.castToByte((short)128)); //= -128
		
		LOGGER.info(jss.divide(10,2)); //= 5.0
		LOGGER.info(jss.divide(3,2)); //= 1.5
		
		LOGGER.info(jss.isEven(2)); //= true
		LOGGER.info(jss.isEven(3)); //= false
		
		LOGGER.info(jss.isAllEven(new int[] {2})); //= true
		LOGGER.info(jss.isAllEven(new int[] {2,4,6,8,10}));// = true
		LOGGER.info(jss.isAllEven(new int[] {3})); //= false
		LOGGER.info(jss.isAllEven(new int[] {2,4,6,8,11})); //= false
		
		LOGGER.info(jss.average(new int[] {2})); //= 2.0
		LOGGER.info(jss.average(new int[] {2,3})); //= 2.5
		
		LOGGER.info(jss.max(new int[] {10,2,4,7,9})); //= 10
		LOGGER.info(jss.max(new int[] {10,10,13,13,14,15,17,17})); //= 17
		
		LOGGER.info(jss.fibonacci(0)); //= 0
		LOGGER.info(jss.fibonacci(1)); //= 1
		LOGGER.info(jss.fibonacci(9)); //= 34
		
		LOGGER.info(Arrays.toString(jss.sort(new int[] {2,4,5,1,3,1}))); //= [1,1,2,3,4,5] @@@@@@@@
		
		LOGGER.info(jss.factorial(0)); //= 1
		LOGGER.info(jss.factorial(1)); //= 1
		LOGGER.info(jss.factorial(3)); //= 6
		
		LOGGER.info(Arrays.toString(jss.rotateLeft((new int[] {1,2,3,4,5}), 1))); //= [2,3,4,5,1]
		LOGGER.info(Arrays.toString(jss.rotateLeft((new int[] {1,2,3,4,5}), 6))); //= [2,3,4,5,1]
		LOGGER.info(Arrays.toString(jss.rotateLeft((new int[] {1,2,3,4,5}), 3))); //= [4,5,1,2,3]
		
		LOGGER.info(jss.isPrime(7)); //= true
		LOGGER.info(jss.isPrime(157)); //= true
		
		LOGGER.info(jss.balancedBrackets("({[]})")); //= true
		LOGGER.info(jss.balancedBrackets("([)]")); //= false
	}

}
