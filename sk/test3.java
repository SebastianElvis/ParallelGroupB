import java.lang.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class test3
{
    public static void main(String args[]) 
    {
        int i;
        Random rand; 
        int num=0,size=0,timeOut=0;
        long seed = 0;
        int mode = 0;
        int nx,modx;
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
        if(num < 1)
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
        List <Integer> b;
        List <Integer> a = new ArrayList<>();
        rand = new Random(seed);
        
        for(i = 0; i < size; i++)
        { 
            a.add(rand.nextInt(65535));
        }
        //------------------------------------------------
        
        //System.out.println("--array:"+a);
        mergeSortTest1 mst = new mergeSortTest1();
        long startTime=System.nanoTime();
        long mstime = System.currentTimeMillis();
        //if(num == 1)
        //{
        //    a = mst.sort(a);
        //    System.out.println("--sorted:"+a);
        //}
        //else
        //{
        //List <Integer> temp ;
        if(num > size){num = size;}
        nx = (size + num - 1)/num; 
        List<Integer> temp[] = new List[num];
        testThread1 []thrd = new testThread1[num];
        for(i = 0; i < num -1 ; i ++)
        {
            //temp = a.subList(i*nx,i*nx+nx);
            thrd[i] = new testThread1(i,a.subList(i*nx,i*nx+nx));
            thrd[i].start();
            
        }
        thrd[num-1] = new testThread1(num-1,a.subList((num-1)*nx,a.size())) ;
        thrd[num-1].start();
        for(i = 0; i < num ; i ++)
        {
            try
            {
                thrd[i].join(); 
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        for(i = 0; i < num  ; i ++)
        {
            temp[i] = thrd[i].a;
        }
        System.out.println("--sorted:");
        b = mst.merge_lis(temp,0,num);
        //}
        startTime = System.nanoTime()-startTime;
        mstime = System.currentTimeMillis()-mstime;
        System.out.println("Time cost:"+startTime+"ns");
        System.out.println("Time cost:"+mstime+"ms");
        //-----------------------------------------------
    }
    
}

class mergeSortTest1
{
    public List merge_lis(List a[], int index, int block_size)
    {
        //List <Integer> x,y;
        if(block_size == 1)
        {
            //x = a[index].a;
            //System.out.println(a[index]);
            return a[index];
        }
        else if(block_size > 1)
        {
             return merge(merge_lis(a,index, block_size/2),merge_lis(a, (index+block_size/2),  (block_size-block_size/2)));
        }
        else{return null;}
    }
    public List merge(List a,List b)
    {
        int sza,szb,pa=0, pb=0;
        int i;
        sza = a.size();
        szb = b.size();
        List <Integer> x = new ArrayList<>();
        while( pa< a.size() || pb< b.size() )
        {
            if(a.size() == pa)
            {
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
        //System.out.println(x); 
        return x;
    }
}

class testThread1 extends Thread
{
    private int threadID;
    public List <Integer> a;
    public testThread1(int in, List a)
    {
        this.threadID = in;
        this.a = a;
    }
    public void run()
    {
        
        //System.out.println("run");
        mergeSortTest1 mst = new mergeSortTest1();
        a = mst.sort(a);
        //System.out.println(threadID+":"+a);
    }

}
