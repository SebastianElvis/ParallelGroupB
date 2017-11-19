import java.lang.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class test3
{
    public static void main(String args[]) 
    {
        int i;
        Random rand; 
        int num=0,size=0,timeOut=0;
        long seed = 0;
        if(args.length == 4)
        {
            num = Integer.parseInt(args[0]);
            seed = Long.parseLong(args[1]);
            size = Integer.parseInt(args[2]);
            timeOut = Integer.parseInt(args[3]);
            System.out.println("Create "+num + " thread(s) seed: "+seed+" size: "+size+" timeout: "+timeOut);
        }
        else
        {
            System.out.println("Wrong input!");
            System.exit(1);
        }
        if(size < 1)
        {
            System.out.println("Wrong size!");
            System.exit(1); 
        }
        if(num < 0)
        {
            System.out.println("Wrong numbers of threads!");
            System.exit(1); 
        }
        if(timeOut <= 0)
        {
            System.out.println("Wrong numbers of timeOut!");
            System.exit(1); 
            
        }
        //int[] ta = new int[size];
        List <Integer> a = new ArrayList<>();
        rand = new Random(seed);
        
        for(i = 0; i < size; i++)
        { 
            a.add(rand.nextInt(65535));
        }
        System.out.println("--array:"+a);
        mergeSortTest1 mst = new mergeSortTest1();
        a = mst.sort(a);
        System.out.println("--sorted:"+a);
        //System.out.println(a.size()); 
        //System.out.println("array"+a);
        //int mid = size/2;
        //List <Integer> x,y;
        //x = a.subList(0,mid);
        //y = a.subList(mid,(a.size()));
        //for(i = 0; i < x.size(); i++)
        //{
        //    System.out.println(i+":"+x.get(i));
        //} 
        //for(i = 0; i < y.size(); i++)
        //{
        //    System.out.println(i+":"+y.get(i));
        //} 
        
    }
    
}

class mergeSortTest1
{
    public List merge(List a,List b)
    {
        int sza,szb,pa=0, pb=0;
        int i;
        sza = a.size();
        szb = b.size();
        List <Integer> x = new ArrayList<>();
        while( pa< a.size() || pb< b.size() )
        {
            //System.out.println("loop-----");
            //System.out.println(a.size());
            //System.out.println(b.size());
            //System.out.println("-----1oop");
            //System.out.println(b);
            if(a.size() == pa)
            {
                
                //System.out.println("1111");
                while(b.size() != pb)
                {
                    x.add((int)b.get(pb));
                    pb++;
                }
                break;
            }
            if(b.size() == pb)
            {
                while(a.size() != pa)
                {
                    x.add((int)a.get(pa));
                    pa++;
                }
                break;
            }
            if((int)a.get(pa) < (int)b.get(pb))
            {
                x.add((int)a.get(pa));
                pa++;
            }
            else
            {
                x.add((int)b.get(pb));
                pb++;
            }
        }
        //a = null;
        //b = null;
        return x;
    }
    public List sort(List a)
    {
        if(a.size() < 2)
        {
            return a;
        } 
        List <Integer> h1,h2,x;
        int mid;
        mid = a.size()/2;
        h1 = a.subList(0,mid);
        h2 = a.subList(mid,(a.size()));
        h1 = this.sort(h1);
        h2 = this.sort(h2);
        x = this.merge(h1,h2);
        System.out.println(x); 
        return x;
    }
}

class mergeSortTest2
{
    public int merge(int a[],int index1, int index2, int size1, int size2)
    {
        int p1;
        int p2;
        p1 = index1;
        p2 = index2;
        return 0;
    }
    //public List sort(int a[])
    //{
    //    //System.out.println(a.size()); 
    //    if(a.size() < 2)
    //    {
    //        return a;
    //    } 
    //    List <Integer> h1,h2,x;
    //    int mid;
    //    mid = a.size()/2;
    //    h1 = a.subList(0,mid);
    //    h2 = a.subList(mid,(a.size()));
    //    h1 = this.sort(h1);
    //    h2 = this.sort(h2);
    //    //System.out.println(h1); 
    //    //System.out.println(h2); 
    //    x = this.merge(h1,h2);
    //    
    //    //System.out.println(x); 
    //    return x;
    //}
}
class testThread extends Thread
{
    private int threadID;
    List <Integer> a;
    public testThread(int in, List a)
    {
        this.threadID = in;
        this.a = a
    }
    public void run()
    {
        
        //System.out.println("Hello world, I am thread "+ this.threadID);
    }

}
