import java.util.concurrent.*; 
import java.util.*; 
import java.util.HashMap;
import java.io.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

public class CoffeMachine
{
    public static int outlets ;
    public static HashMap<String, Integer> items_quantity = new HashMap<>();
    public static List<Beverage> Beverages = new ArrayList<Beverage>();
    
    public static void main(String args[]) 
    {
        
     
      
      
      /*HashMap<String, Integer> Tea_Ingredients = new HashMap<String,Integer>();
      Tea_Ingredients.put("hot_water", 200);
      Tea_Ingredients.put("hot_milk", 100);
      Tea_Ingredients.put("ginger_syrup", 10);
      Tea_Ingredients.put("sugar_syrup", 10);
      Tea_Ingredients.put("tea_leaves_syrup", 30);
       
      HashMap<String, Integer> coffee_Ingredients = new HashMap<String,Integer>();
      coffee_Ingredients.put("hot_water", 100);
      coffee_Ingredients.put("hot_milk", 400);
      coffee_Ingredients.put("ginger_syrup", 30);
      coffee_Ingredients.put("sugar_syrup", 50);
      coffee_Ingredients.put("tea_leaves_syrup", 30);
     
     Beverage b;
     b= new Beverage("hot_tea",Tea_Ingredients);
       Beverages.add(b);
        b= new Beverage("hot_coffee",coffee_Ingredients);
       Beverages.add(b);
       b= new Beverage("hot_tea",Tea_Ingredients);
       Beverages.add(b);
        b= new Beverage("hot_coffee",coffee_Ingredients);
       Beverages.add(b);

       ProducerConsumer ps =  new ProducerConsumer(Beverages,2);
       ps.Start();
       
      */
      readFile();
      PrepareBeverage ps =  new PrepareBeverage(items_quantity,Beverages,2);
      ps.Start();
      int x=10;
      int y=25;
      int z=x+y;

      System.out.println("Sum of x+y = " + z);
    }
    
    private static void readFile() 
    {
        Object obj = new JSONParser().parse(new FileReader("/uploads/Inputtest1.json"));
        JSONObject jo = (JSONObject) obj;
 
        outlets = (int) jo.get("machine").get("outlets").get("count_n");
        items_quantity = ((HashMap)jo.get("total_items_quantity"));
        JSONObject BeverageInfo = jo.get("beverages");
        
        Iterator<String> keys = BeverageInfo.keys();

        while(keys.hasNext()) 
        {
          Beverage b ;
          String key = keys.next();
          HashMap<String, Integer> Ingredients = new HashMap<String,Integer>();
          Ingredients = (HashMap)jo.get("beverages");
          b= new Beverage(key,Ingredients);
          Beverages.add(b);
        }
       
    }
}