package org.dawnsci.surfacescatter;

public class SortingArrayMaxima {
     
    private static ArrayMaximForSorting[] array;
    private static int length;
 
    public static ArrayMaximForSorting[] getArrayOfMaximums(double[] input){
    	
    	ArrayMaximForSorting[] output =new ArrayMaximForSorting[input.length];
    	
    	for(int i=0 ; i<input.length; i++){
    		output[i] = new ArrayMaximForSorting(i, input[i]);
    	}
    	
    	return output;
    }
    
    public static void sort(ArrayMaximForSorting[] inputArr) {
         
        if (inputArr == null || inputArr.length == 0) {
            return;
        }
        
        array = inputArr;
        length = inputArr.length;
        quickSort(0, length - 1);
    }
 
    private static void quickSort(int lowerIndex, int higherIndex) {
         
        int i = lowerIndex;
        int j = higherIndex;
        // calculate pivot number, I am taking pivot as middle index number
        ArrayMaximForSorting pivot = array[lowerIndex+(higherIndex-lowerIndex)/2];
        // Divide into two arrays
        while (i <= j) {
            /**
             * In each iteration, we will identify a number from left side which 
             * is greater then the pivot value, and also we will identify a number 
             * from right side which is less then the pivot value. Once the search 
             * is done, then we exchange both numbers.
             */
            while (array[i].getValue() < pivot.getValue()) {
                i++;
            }
            while (array[j].getValue() > pivot.getValue()) {
                j--;
            }
            if (i <= j) {
                exchangeNumbers(i, j);
                //move index to next position on both sides
                i++;
                j--;
            }
        }
        // call quickSort() method recursively
        if (lowerIndex < j)
            quickSort(lowerIndex, j);
        if (i < higherIndex)
            quickSort(i, higherIndex);
    }
 
    private static void exchangeNumbers(int i, int j) {
        ArrayMaximForSorting temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
     
//    public static void main(String a[]){
//         
//        SortingArrayMaxima sorter = new SortingArrayMaxima();
//        int[] input = {24,2,45,20,56,75,2,56,99,53,12};
//        sorter.sort(input);
//        for(int i:input){
//            System.out.print(i);
//            System.out.print(" ");
//        }
//    }
}