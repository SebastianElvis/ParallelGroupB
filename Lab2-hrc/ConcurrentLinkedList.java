package part3;

import java.util.concurrent.atomic.AtomicReference;

public class ConcurrentLinkedList<T> {
	private T elem;
	private AtomicReference<ConcurrentLinkedList<T>> next = new AtomicReference<ConcurrentLinkedList<T>>();
		
	public T getElem() {
		return elem;
	}
	
	public AtomicReference<ConcurrentLinkedList<T>> getNext(){
		return next;
	}
	
	public <T> ConcurrentLinkedList(){
		elem = null;
		next.set(null);
	}
	
	public static <T> ConcurrentLinkedList<T> createEmpty() {
		return new ConcurrentLinkedList<>();
	}
	
	public boolean isEmpty() {
		if(elem == null && (next == null || next.get() == null) ) {
			return true;
		} else {
			return false;
		}
	}
	
	public void addElement(T t) {
		if(elem == null) {
			elem = t;
		} else {
			ConcurrentLinkedList<T> n = this;
			while( n.next.get() != null) {
				n = n.next.get();
			}
			ConcurrentLinkedList<T> nextT = new ConcurrentLinkedList<T>();
			nextT.elem = t;
			n.next.set(nextT);
		}
	}
	
	public void removeFirstElement() {
		if(elem == null && next.get() == null) {
			System.out.println("ERROR IN removeFirstElement()");
			System.out.println("The list has already been empty!");
			System.exit(-1);
		} else if (elem != null && next.get() == null) {
			elem = null;
		} else {
			elem = next.get().elem;
			next.set(next.get().next.get());
		}
	}
	
	public void removeLastElement() {
		if(elem == null || next.get() == null) {
			System.out.println("ERROR IN removeLastElement()");
			System.out.println("The list has already been empty!");
			System.exit(-1);
		} else if(elem != null && next.get() == null) {
			elem = null;
		} else {
			ConcurrentLinkedList<T> n = this;
			while(n.next.get().next.get() != null) {
				n = n.next.get();
			}
			n.next.set(null);
		}
	}
	
	@Override
	public String toString() {
		String s = "";
		if(elem == null) {
			return s;
		} else {
			ConcurrentLinkedList<T> n = this;
			while(n != null && n.elem != null) {
				s += n.elem + " ";
				n = n.next.get();
			}
			return s;
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ConcurrentLinkedList<Integer> l = new ConcurrentLinkedList<Integer>();
		for(int i=0;i<10;i++) {
			l.addElement(i);
		}
		System.out.println("fuck");
		System.out.println(l);
		l.removeLastElement();
		System.out.println(l);
		l.removeFirstElement();
		System.out.println(l);
	}

}
