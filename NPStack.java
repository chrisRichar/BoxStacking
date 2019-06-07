///Christian Richardson 1312908
///Sivaram Manoharan 1299026

import java.util.*;
import java.io.*;
import java.util.Random;
 

public class NPStack{
	static boolean[] isUsed;
	static ArrayList<Box> boxList;
	static ArrayList<Box> boxStack;
	
	
	static boolean foundValid = true;
	static int maxIndex, maxWidth, maxDepth, maxHeight;
	
	static Box current;
	static Box prevBox = new Box(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
	
	public static void main(String[] args){
		try{
			if(args.length != 2){
				System.out.println("Usage: string<filename>, integer<num of candidate solutions>");
				return;
			}
			
			//declare readers to load the boxes into memory
			int solutionsNum = Integer.parseInt(args[1]);
			File file = new File(args[0]);
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			//declare list for stacking boxes
			boxList = new ArrayList<Box>();
			boxStack = new ArrayList<Box>();
			ArrayList<Box> bestStack = new ArrayList<Box>();
			
			//read all boxes into memory
			String line;
			while((line = br.readLine()) != null){	//while we have lines to read
				String[] lArray = line.split(" ");	//load lines into arrays
				int[] iArray = {Integer.parseInt(lArray[0]), Integer.parseInt(lArray[1]), Integer.parseInt(lArray[2])};
				boolean positiveInts = true;
				for(int i = 0; i < iArray.length; i++){	//check for negative integer values
					if(iArray[i] <= 0)
						positiveInts = false;
				}
				
				if(positiveInts){					//only make a box if all its dimensions are positive
					//set boxes so their largest surface area is at the bottom (sort dimensionsin descending order)
					Arrays.sort(iArray);//ascending order
					boxList.add(new Box(iArray[0], iArray[1], iArray[2]));
					
					//System.out.println(iArray[0] + "," + iArray[1]  + "," + iArray[2]);
				}
			}
			br.close();
			isUsed = new boolean[boxList.size()];
			
			//find largest area box
			int bestSolutionHeight = 0;
			int prevStackHeight = 0;
			double temperature = boxList.size() / 2;
			double cooling = 0.5;
			
			// Generate the initial solution
			bestSolutionHeight = stackTower();
			bestStack = new ArrayList<Box>(boxStack);
			
			//for the number of attempts(given by args) generate a solution
			while(solutionsNum > 1){
				temperature = temperature * cooling;
				Random randomGenerator = new Random();
				int randomInt = randomGenerator.nextInt(boxList.size());
				int probability = randomGenerator.nextInt(100);
				boxList.get(randomInt).flipBox();
				currStackHeight = stackTower();	//stack tower with the best-fitting boxes
				
				if(currStackHeight > bestSolutionHeight){	//store the height of the most successful solution
					bestSolutionHeight = currStackHeight;
					bestStack = new ArrayList<Box>(boxStack);
				}
				else if(probability < (temperature * 100)) {
					 boxList.get(randomInt).flipBox(); // Flips the box back to its initial position so the current stack is the same as the previous
				}
				isUsed = new boolean[boxList.size()];
				prevBox = new Box(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
				boxStack = new ArrayList<Box>();	//reset box stack (to make a new solution)
				foundValid = true;
				solutionsNum--;
			}
		
	
			
			
			//print bottom-up stack of boxes and the total height
			printBest(bestStack);
			System.out.println("height: " + bestSolutionHeight);
		
			
		}catch(Exception e){
			System.out.println(e);
		}
		
//		int Temperature = boxList.size();
//		int[] boxChanges = new int[temperature]
//		for(int i = 0;i < temperature, i++) {
//			int change = math.random(0,temperature)
//			boxChanges[i] = change;
//		}
//		// change boxes at those positions
//		// check if solution improved
//		// decrease number of solutions and 
	}
	
	
	
	
	
	///method that takes a list of boxes and prints the specified output of <width depth height totalHeight>
	static void printBest(ArrayList<Box> bestStack){
		Box current;
		int totalHeight = 0;
		for(int i = 0; i < bestStack.size(); i++){
			current = bestStack.get(i);
			totalHeight += current.height;
			System.out.println(current.width + " " + current.depth + " " + current.height + " " + totalHeight);
		}
		System.out.println("Boxes used: " + bestStack.size());
	}
	
	///stack the tower with the most fitting boxes
	static int stackTower(){
		int totalStackHeight = 0;
		while(foundValid){
			foundValid = false;
			maxWidth = 0;
			maxDepth = 0;
			maxHeight = 0;
			maxIndex = 0;
		
			for(int i = 0; i < boxList.size(); i++){
				current = boxList.get(i);
				
				//Find largest unused box that can still fit within the confines of the previous box
				if(!isUsed[i] && 														//single use condition
				(current.width < prevBox.width && current.depth < prevBox.depth) && 	//touching face condition
				(current.width >= maxWidth && current.depth >= maxDepth)){				//find largest base
					
					if(current.width == maxWidth && current.depth == maxDepth){
						if(current.height > maxHeight){
							maxWidth = current.width;
							maxDepth = current.depth;
							maxHeight = current.height;
							maxIndex = i;
						}
						
					}
					else{
						maxWidth = current.width;
						maxDepth = current.depth;
						maxHeight = current.height;
						maxIndex = i;
					}
					foundValid = true;
				
					
				}
			
			}
			if(foundValid){
				prevBox = boxList.get(maxIndex);
				boxStack.add(new Box(maxWidth, maxDepth, maxHeight));	//after every scan of the list, stack the largest box
				totalStackHeight += maxHeight;
				isUsed[maxIndex] = true;				//flag that the box is in use (cannot be stacked twice)
			}
		}
		return totalStackHeight;
	}
}
