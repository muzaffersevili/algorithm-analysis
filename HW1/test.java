import java.util.Random;

public class test {

	public static void main(String[] args) {

		int n = 10000; // Array Size = 1000
		//int n = 10000; // Array Size = 10000
		//int n = 100000; // Array Size = 100000
		
		int[] arrayToSort = new int[n];
		
		/*
		// Equal Integers
		for (int i = 0; i < n; i++) {
			arrayToSort[i] = 70;
		}
		*/
		
		
		// Random Integers
		Random rand = new Random();
		for (int i = 0; i < n; i++) {
			arrayToSort[i] = rand.nextInt();
		}
		
		
		/*
		// Increasing Integers
		for (int i = 0; i < n; i++) {
			arrayToSort[i] = i;
		}
		*/

		/*
		// Decreasing Integers
		for (int i = n-1; i > 0; i--) {
			arrayToSort[i] = i;
		}
		*/
		
		
		
		//HeapSort
		HeapSort heapsort=new HeapSort();
		long heap_startTime = System. currentTimeMillis();
        heapsort.sort(arrayToSort);
        long heap_estimatedTime = System. currentTimeMillis() - heap_startTime;
        System.out.println("Heap Short Time: "+heap_estimatedTime);
        //heapsort.printArray(arrayToSort);
        
        
	
		
		//ShellSort
        ShellSort shellsort = new ShellSort();
        long shell_startTime = System. currentTimeMillis();
        shellsort.sort(arrayToSort);
        long shell_estimatedTime = System. currentTimeMillis() - shell_startTime;
        System.out.println("Shell Sort Time: "+shell_estimatedTime);
        //shellsort.printArray(arrayToSort);
        
		
		
		
		//IntroSort
		Introsort introsort = new Introsort(n);
		
		for (int i = 0; i < n; i++) {
            introsort.dataAppend(arrayToSort[i]);
        }
		
		long intro_startTime = System. currentTimeMillis();
        introsort.sortData();
        long intro_estimatedTime = System. currentTimeMillis() - intro_startTime;
        
        System.out.println("Intro Sort Time: "+intro_estimatedTime);
        //introsort.printData();
        
	}

}
