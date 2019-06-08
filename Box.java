///Christian Richardson 1312908
///Sivaram Manoharan 1299026
import java.lang.Math;
import java.util.*;

public class Box{
	public int width, depth, height;
	
	public Box(int w, int d, int h){
		width = w;
		depth = d;
		height = h;
	}
	
	public void rearrangeTall(){
		int[] dimensions = {width, depth, height};
		Arrays.sort(dimensions);	//sort ascending order
		int temp = dimensions[0];
		dimensions[0] = dimensions[1];
		dimensions[1] = temp;
		
		width = dimensions[0];
		depth = dimensions[1];
		height = dimensions[2]; // sort by descending
	}
	
		public void flipBox(){
		int oldDepth = depth;
		depth = width;
		width = height;
		height = oldDepth;
		
	}
	
	public void reflipBox(){
		int oldDepth = depth;
		depth = height;
		height = width;
		width = oldDepth;
	}
}
