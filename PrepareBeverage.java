import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.Executors; 
import java.util.concurrent.locks.ReentrantLock; 

public class PrepareBeverage
{
    private  HashMap<String, Integer> items_quantity = new HashMap<>(); 
    private  Queue<Beverage> TaskQueue;
    private  ReentrantLock Queuelock = new ReentrantLock();
    private  ReentrantLock MapLock = new ReentrantLock();
    private  ReentrantLock PrintLock = new ReentrantLock();
    private int Outlets;
    
    public PrepareBeverage( HashMap<String, Integer>total_items_quantity,List<Beverage> beverages,int outlets) 
    {
      this.items_quantity = new HashMap<String,Integer>();
      this.TaskQueue = new LinkedList<Beverage>();
      this.items_quantity = total_items_quantity;
      /*
      items_quantity.put("hot_water",500);
      items_quantity.put("hot_milk",500);
      items_quantity.put("ginger_syrup",100);
      items_quantity.put("sugar_syrup",100);
      items_quantity.put("tea_leaves_syrup",100);*/
      
      this.Outlets = outlets;
      
     for (int i = 0; i < beverages.size(); i++)  
          TaskQueue.add((beverages.get(i))); 
      
    }
    
    public void Start()
    {
         System.out.println("Hello from PC");
         System.out.println(TaskQueue.size());
         
         Thread threads[] = new Thread[this.Outlets];
         for(int i=0;i<this.Outlets;i++)
         {
            threads[i] = new Thread(new Runnable() { 
            @Override
            public void run() 
            { 
                try
                { 
                   PrepareBeverage(); 
                } 
                catch (InterruptedException e) { 
                    e.printStackTrace(); 
                } 
            } 
        }); 
         }
          for(int i=0;i<this.Outlets;i++)
         threads[i].start();
    }
    
    private void PrepareBeverage() throws InterruptedException 
    {
      while(true)
      {
          
        Beverage b = null;
        Boolean shouldTerminate = false;
        List<String> InsufficientIngredients = new Vector<String>();
        
        Queuelock.lock();
        
        try
        {
         if(TaskQueue.size() == 0)
         {
             shouldTerminate = true;
         }
         else
         {
              b = TaskQueue.peek(); 
          this.TaskQueue.remove(b);
         }
         
        } 
        finally 
        {
            Queuelock.unlock();
        }
        
        if(shouldTerminate)
        {
            return;
        }
        MapLock.lock();
        try
        {
           Iterator IngredientIterator = b.Ingredients.entrySet().iterator(); 
            while (IngredientIterator.hasNext())
          { 
            Map.Entry pair = (Map.Entry)IngredientIterator.next(); 
            
            if(this.items_quantity.get(pair.getKey()) < (Integer)pair.getValue() )
            {
                InsufficientIngredients.add((String)pair.getKey());
            }
          } 
          
            if(InsufficientIngredients.size() ==0)
            {
                 IngredientIterator = b.Ingredients.entrySet().iterator(); 
                while (IngredientIterator.hasNext())
                { 
                  Map.Entry pair = (Map.Entry)IngredientIterator.next(); 
                  String key = pair.getKey().toString();
                  Integer newValue =  (Integer)this.items_quantity.get(key) - (Integer)pair.getValue();
                  this.items_quantity.put(key,newValue);
                } 
            }
          
          
        }
        finally
        {
             MapLock.unlock();
        }
        
         if(InsufficientIngredients.size()> 0)
         {
             System.out.println("Can not prepare beverage -" + b.Name + " , Please refill below ingredients");
             for (int i = 0; i < InsufficientIngredients.size(); i++)
             {
                  System.out.print(InsufficientIngredients.get(i) + " "); 
             }
             System.out.println();
             
         }
         else
         {
              System.out.println("Preparing beverage " + b.Name);
              Thread.sleep(1000);
              System.out.println("Beverage is ready " + b.Name);
             
         }
      }
    }
}