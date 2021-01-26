import java.util.HashMap;
public class Beverage
{
   public String Name;
   HashMap<String, Integer> Ingredients ;
   
   public Beverage(String name, HashMap<String, Integer> ingredients)
   {
       Name =  name;
       Ingredients = new HashMap<String, Integer>(ingredients);
   }
}