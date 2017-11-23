package part3;

public class TestConcurrency {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ConcurrentLinkedList<Integer> l = new ConcurrentLinkedList<Integer>();
		Runnable r = new AddToList(l);
		for(int i=0;i<20;i++) {
			Thread t = new Thread(r);
			t.start();
			try {
				t.join(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(l.size());
	}

}

class AddToList implements Runnable{
	private ConcurrentLinkedList l;
	
	public AddToList(ConcurrentLinkedList l) {
		this.l = l;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int i=0;i<10;i++) {
			l.addElement(1);
		}
	}
	
}
