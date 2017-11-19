package parallelSorting;

import java.util.Collections;
import java.util.List;

public class SortThread extends Thread{
	private int id;
	public List<Integer> l;
	
	public SortThread(int id, List<Integer> l) {
		super();
		this.id = id;
		this.l = l;
	}

	@Override
	public void run() {
		long start = System.nanoTime();
		Collections.sort(l);
		long end = System.nanoTime();
		System.out.println("Thread ID: " + id + "; Time used: " + (end-start) + " nanoseconds");
		System.out.println("Thread ID: " + id + "; If sorted: " + ParallelSorting.seqCheck(l));
	}
}
