package parallelSorting;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParallelSorting {
	private List<Integer> l;
	private SortThread[] threads;
	private int timeOut;
	
	public ParallelSorting(int numThread, int seed, int size, int timeOut) {
		this.timeOut = timeOut;
		
		//Initializing the List
		l = new ArrayList<Integer>(size);
		Random generator = new Random(seed);
		for(int i=0;i<size;i++) {
			l.add(generator.nextInt(100000)); 
		}
		
		//Initializing threads
		threads = new SortThread[numThread];
		//Distributing subLists to threads
		int stride = size/numThread;
		for (int i=0;i<numThread-1;i++) {
			int start = i*stride;
			int end = (i+1)*stride;
			System.out.println("Start: " + start + "; End: " + end);
			threads[i] = new SortThread(i, l.subList(start, end));
		}
		threads[numThread-1] = new SortThread((numThread-1), l.subList((numThread-1)*stride, size));
		System.out.println("Start: " + (numThread-1)*stride + "; End: " + size);
	}
	
	public List<Integer> parallelMergeSort() {
		
		// Executing sort() parallel
		for(Thread t : threads) {
			t.start();
		}
		for(Thread t : threads) {
			try {
				t.join(timeOut);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/* Merge Phase */
		
		//Initialize pointers to 0
		int[] pointers = new int[threads.length];
		for (int i=0;i<threads.length;i++) {
			pointers[i] = 0;
		}
		//Merge by 2 iterations
		List<Integer> newL = new ArrayList<Integer>(l.size());
		while(newL.size() != l.size()) {
			int nextNum = 999999999;
			int smallestIndex = 0;
			//Find the smallest element in different pointers
			for(int i=0;i<threads.length;i++) {
				// if exceeded
				if(pointers[i] >= threads[i].l.size()) {
					continue;
				}
				if(threads[i].l.get(pointers[i]) < nextNum) {
					nextNum = threads[i].l.get(pointers[i]);
					smallestIndex = i;
				}
			}
			newL.add(threads[smallestIndex].l.get(pointers[smallestIndex]));
			pointers[smallestIndex]++;
		}
		return newL;
	}
	
	public List<Integer> getL(){
		return l;
	}
	
	public static boolean seqCheck(List<Integer> l) {
		boolean flag = true;
		int p, q;
		p = l.get(0);
		for(int i=1;i<l.size();i++) {
			q = l.get(i);
			if(p <= q) {
				p = q;
			} else {
				flag = false;
				break;
			}
		}
		return flag;
	}
	
	public static void main(String[] args) {
		if (args.length != 4) {
			System.out.println("Hint: ParallelSorting <numThreads> <seed> <sizeArray> <timeOut>\nExiting...");
			System.exit(-1);
		}
		int numThreads = Integer.parseInt(args[0]);
		int seed = Integer.parseInt(args[1]);
		int sizeArray = Integer.parseInt(args[2]);
		int timeOut = Integer.parseInt(args[3]);
		ParallelSorting ps = new ParallelSorting(numThreads, seed, sizeArray, timeOut);
		
		// Time cost
		long start = System.nanoTime();
		List<Integer> newL = ps.parallelMergeSort();
		long end = System.nanoTime();

		
		boolean flag = ParallelSorting.seqCheck(newL);
		System.out.println("If the final list is in order: " + flag);
		System.out.println("Time used: " + (end-start) + "ns");
	}

}
