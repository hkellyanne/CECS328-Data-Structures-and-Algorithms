import java.util.Random;
import java.util.Scanner;

public class runforyourlife {
	//initiate variables
		static Scanner reader = new Scanner(System.in);
		
		//initialize testing arrays
		static int[] myArray;
		static int[] nullArray = new int[0];
		static int[] negArray = {-1,-2,-80,-4,-54,-6,-7,-8,-9,-10};
		static int[] zeroArray = {0,0,0,0,0,0,0,0,0,0};
		static int[] posArray = {1,2,3,4,5,6,7,8,9,10};
		
		static int s;
		static int n;
		static Random rand = new Random();
		static int myRand;
		static int myNum;
		static boolean check = true;
		
		public static void main(String args[]){
			int choice;
			
			//display menu options
			System.out.println("1. Quit the program\n"
					+ "2. Time Freddy's algorithm\n"
					+ "3. Time Susie's algorithm\n"
					+ "4. Time Johnny's algorithm\n"
					+ "5. Time Sally's algorithm\n");
			//initialize array to be used by all functions
			myArray = getValues();
			System.out.print("[");
			for (int i = 0; i < myArray.length - 1; i++){
				System.out.print(myArray[i] + " ");
			}
			//prompt user for menu choice
			System.out.print("]\n Menu Choice: \n");
			
			while(check){
			//store menu choice 
			choice = reader.nextInt();
			
			//if user decides to quit the program
			if (choice == 1){
				System.out.println("\nquit");
				System.exit(0);
			}
			
			//else cycle through choice options
			else{
			
			switch (choice){
			case 1: System.out.println("\nexiting program");
					check = false;
					System.exit(0);
			case 2: System.out.println("\nrunning freddy's algorithm");
					final long freshmanstart = System.currentTimeMillis();
					System.out.println("Max: " + freddy(myArray));
					final long freshmanend = System.currentTimeMillis();
					System.out.println("Total execution time: " + (freshmanend - freshmanstart) + " milliseconds");
					break;
			case 3: System.out.println("\nrunning susie's algorithm");
					final long sophomorestart = System.currentTimeMillis();
					System.out.println("Max: " + susie(myArray));
					final long sophomoreend = System.currentTimeMillis();
					System.out.println("Total execution time: " + (sophomoreend - sophomorestart) + " milliseconds");
					break;
			case 4: System.out.println("\nrunning johnny's algorithm");
					final long juniorstart = System.currentTimeMillis();
					System.out.println("Max: " + johnny(myArray));
					final long juniorend = System.currentTimeMillis();
					System.out.println("Total execution time: " + (juniorend - juniorstart) + " milliseconds");
					break;
			case 5: System.out.println("\nrunning sally's algorithm");
					final long seniorstart = System.currentTimeMillis();
					System.out.println("Max: " + sally(myArray));
					final long seniorend = System.currentTimeMillis();
					System.out.println("Total execution time: " + (seniorend - seniorstart) + " milliseconds");
					break;
			default: System.out.println("\ninvalid input");
					break;
			
			}
			
			//display menu again
			System.out.println("\n\n1. Quit the program\n"
					+ "2. Time Freddy's algorithm\n"
					+ "3. Time Susie's algorithm\n"
					+ "4. Time Johnny's algorithm\n"
					+ "5. Time Sally's algorithm\n");
			}
		}
			
			
			
		}
		
		//prompt user for a seed and input size in order to initialize array
		public static int[] getValues(){
			System.out.println("Seed Value:");
			s = reader.nextInt();
			System.out.println("Input Size: ");
			n = reader.nextInt();
			myRand = rand.nextInt(s)+1;
			myArray = new int[n + 1];
			for (int i = 0; i < myArray.length; i++){
				myArray[i] = rand.nextInt(100-(-100)) + (-100);
			}
			return myArray;
		}
		
		//freddy the freshman
		public static int freddy(int[] a){
			int[] myArray = a;
			int max = 0;
			
			for(int i = 0; i < a.length+1; i++){
				for (int j = i; j < a.length+1; j++){
					int thisSum=0;
					for (int k = i; k < j; k++){
						thisSum = thisSum + myArray[k];
						if (thisSum > max){
							max = thisSum;
						}
					}
				}
			}
			return max;
			
			
		}
		
		//susie the sophomore
		public static int susie(int[] a){
			int[] myArray = a;
			int max = 0;
			int thisSum;
			
			for(int i = 0; i < a.length; i++){
				thisSum = 0;
				for(int j = i; j < a.length; j++){
					thisSum = thisSum + myArray[j];
					if (thisSum > max){
						max = thisSum;
					}
				}
			}
			return max;
			
		}
		
		//johnny the junior
		public static int johnny(int[] a){
			int right = a.length -1;
			if (right < 0){
				right = 0;
			}
			return max3(a, 0, right);
		}

		
		public static int max3(int[] a, int left, int right){
			int[] myArray = a;
			
			if (left == right){
				//check if an array is not empty so that it will not crash if array size is 0
//				if(myArray.length >= 1){
				if(myArray[left] > 0){
					return myArray[left]; //base case; return the one item in the subarray if it is positive
//				}
				}
				return 0;
			}
			
			//split the array in 2 and find each half's max sum
			int center = (int) Math.floor((left+right)/2);

			int maxLeftSum = max3(myArray, left, center);
			int maxRightSum = max3(myArray, center+1, right);
			
			
			//find the max sum starting at center and moving left
			int maxLeftBorder = 0;
			int leftBorder = 0;
			
			for (int i = center; i >= 0; i--){
				leftBorder = leftBorder + myArray[i];
				if (leftBorder > maxLeftBorder){
					maxLeftBorder = leftBorder;
				}
			}
			
			//find the max starting at center and moving right
			int maxRightBorder = 0;
			int rightBorder = 0;
			
			for (int i = center+1; i < (myArray.length); i++){
				rightBorder = rightBorder + myArray[i];
				if (rightBorder > maxRightBorder){
					maxRightBorder = rightBorder;
				}
			}
			
			
			//System.out.println(maxLeftSum + " " + maxRightSum + " " + maxLeftBorder + " " + maxRightBorder);
			//the max sum is the largest of the three sums found
			return Math.max(Math.max(maxLeftSum, maxRightSum), maxLeftBorder+maxRightBorder);
			
		}
		
		//sally the senior
		public static int sally(int[] a){
			int[] myArray = a;
			int max = 0;
			int thisSum = 0;
			for (int i = 0; i < (a.length); i++){
				thisSum = thisSum + a[i];
				if (thisSum > max){
					max = thisSum;
				}
				else if (thisSum < 0){
					thisSum = 0;
				}
			}
			return max;
		}


}
