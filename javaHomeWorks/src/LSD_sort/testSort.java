package LSD_sort;
import java.util.Arrays;
import java.util.Random;

public class testSort {
	private static final int MAX = 1000000; //numbers of arrays
	private static final int MAX_INT = Integer.MAX_VALUE;
	private static final int SIZE_BYTE = 256;
	
	
	public static void main(String[] args) {
		int[] data = generateArr();//generate for Qs		
		System.out.println("Elapsed = " + timer(data, 1));
		
		data = generateArr();//generate for LSD 
		System.out.println("LSDsort = " + timer(data, 2));
		
	}
	
	
	private static int[] generateArr() {
		int[] data = new int[MAX];
		Random random = new Random();
		for (int i = 0; i < data.length; i++) {
			data[i] = random.nextInt(MAX_INT); // for max integer
		}
		return data;
	}
	
	
	private static long timer(int[] data, int type){
		long start;
		switch(type){
		case 1:
			start = System.currentTimeMillis();//start timer
			Arrays.sort(data);//Qsort
			break;
		case 2:		
			start = System.currentTimeMillis();//start timer
			int[] count  = new int[SIZE_BYTE + 1];
			int[] sort_arr = new int[data.length];
			/*	create help Arrays before start sorting, and 
			 *	set it ? to the methods, because it's so long to create it in the
			 *  methods. But we mast to add it to ours timer
			*/
			sortLSD(data, count, sort_arr);//LSDsort
			break;
		default: 
			start = System.currentTimeMillis();//start timer
			break;			
		}
		long stop = System.currentTimeMillis();//end timer
		
		return (stop - start);
	}
/*	
 * it's LSDsort, were data - is array to sort, and cout and sort_arr it's help arrays,
 * 
 */	
	private static void sortLSD(int[] data, int[] count,int[] sort_arr){
		int[] answer = new int[data.length];
		for (int i = 0; i < 1; i++) {
			answer = countingSort(data, i, count, sort_arr);	
		}
		for (int i = 0; i < answer.length; i++) {
			data[i] = answer[i];
		}
	}
	
	
	/*
	 * counting Sort, were date it's sort array, sort_arr it's it's output arrays, and 
	 * byteIndex it's arrays for counting
	 */
	private	static int[] countingSort(int[] data, int byteIndex,  int[] count,int[] sort_arr){

		for (int i = 0; i < data.length; i++) {
			count[intToByte(data[i], byteIndex) + 1]++;
		}
		for (int i = 0; i < count.length - 1; i++) {
			count[i + 1] += count[i];			
		}
		for (int i = 0; i < data.length; i++) {
			sort_arr[count[intToByte(data[i], byteIndex)]++] = data[i];	
		}		
		return sort_arr;
	}
	
	/*
	 * 1 - it's the 1-8 bit's 2 - it's 9-16 bit's and so on
	 */
	private static int intToByte(int a, int byteIndex){ 
		int answer;
		switch(byteIndex){
		case 0:
			answer = (SIZE_BYTE - 1) & a;//(SIZE_BYTE - 1) it's 11111111 in binary
			break;
		case 1:
			answer = (SIZE_BYTE - 1) & (a >>> 8);
			break;
		case 2:
			answer = (SIZE_BYTE - 1) & (a >>> 16);
			break;
		case 3:
			answer = (a >>> 23);
			break;
		default:
			answer = 0;
			break;			
		}		
		return(answer);
	}
}
