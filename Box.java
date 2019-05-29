///Christian Richardson 1312908
///Denzel Dacones 1319987
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
	
	//returns true if this box's dimensions can fit on the given parameters dimensions
	public boolean canFit(int w, int d){
		return (width < w) && (depth < d);
	}
	
	//returns the differences between this instance's width/depth/height and the given parameter
	public int heightDiff(int h){
		return height - h;
	}
	public int widthDiff(int w){
		return width - w;
	}
	public int depthDiff(int d){
		return depth - d;
	}
}
