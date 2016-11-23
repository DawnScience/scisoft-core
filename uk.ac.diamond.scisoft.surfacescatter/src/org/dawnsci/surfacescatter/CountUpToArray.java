package org.dawnsci.surfacescatter;

public class CountUpToArray {

	public static int[] CountUpToArray1(int[] input){
		
		int[] output = new int[input.length];
		
		for(int i = 0; i<input.length; i++){
			
			int probe = input[i];
			int countUpTo =0;
			
			for(int j = 0; j<i; j++){ 
				
				if(probe == input[j]){
					countUpTo++;
				}
			}	
			
			output[i] = countUpTo;
				
		}
		
		return output;
	}
	
	
}
