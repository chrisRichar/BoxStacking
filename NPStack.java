///Christian Richardson 1312908
///Sivaram Manoharan 1299026

import java.util.*;
import java.io.*;

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
			
			//read all boxes into memory
			String line;
			while((line = br.readLine()) != null){	//while we have lines to read
				String[] lArray = line.split(" ");	//load lines into arrays
				int[] iArray = {Integer.parseInt(lArray[0]), Integer.parseInt(lArray[1]), Integer.parseInt(lArray[2])};
				boolean positiveInts = true;
				for(int i = 0; i < iArray.length; i++){	//check for negative integer values
					if(iArray[i] < 0)
						positiveInts = false;
				}
				
				if(positiveInts){					//only make a box if all its dimensions are positive
					//set boxes so their largest surface area is at the bottom (sort dimensionsin descending order)
					Arrays.sort(iArray);
					int temp = iArray[2];
					iArray[2] = iArray[0];
					iArray[0] = temp;
					boxList.add(new Box(iArray[0], iArray[1], iArray[2]));
					
					//System.out.println(iArray[0] + "," + iArray[1]  + "," + iArray[2]);
				}
			}
			br.close();
			isUsed = new boolean[boxList.size()];
			
			//find largest area box
			int bestSolutionHeight = 0;
			int prevStackHeight = 0;
			
			//for the number of attempts(given by args) generate a solution
			while(solutionsNum > 0){
				prevStackHeight = stackTower();	//stack tower with the best-fitting boxes
				
				if(prevStackHeight > bestSolutionHeight){	//store the height of the most successful solution
					bestSolutionHeight = prevStackHeight;
					//bestList = new ArrayList<Box>(boxStack);
				}
				
				//re-orient boxes by maximum height (Systematic approach)
				//for(int i = 0; i < boxList.size(); i++){
				//	boxList.get(i).rearrangeTall();
				//}
				
				int Temperature = boxList.size() / 10;
				for(int i = 0;i++;i < temperature){
				 int change = Math.randInt(0,temperature)
				 boxList.get(change).rearrangeTall();
				}
						
				foundValid = true;
				prevStackHeight = stackTower();	//continue stacking tower with best-fitting boxes
				
				solutionsNum--;
			}
			
			
			//print bottom-up stack of boxes and the total height
			printBest(boxStack);
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
				if(!isUsed[i] && 
				(current.width < prevBox.width && current.depth < prevBox.depth) && 
				(current.width >= maxWidth && current.depth >= maxDepth)){
					
					foundValid = true;
				
					maxWidth = current.width;
					maxDepth = current.depth;
					maxHeight = current.height;
					maxIndex = i;
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
