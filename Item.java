
/**
 * Write a description of class Item here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Item
{
    // instance variables - replace the example below with your own
    private String itemDescription;
    private int itemWeight;
    
    /**
     * Sets the description and weight
     * 
     * @set description
     * @set weight
     */
    public Item(String description, int weight)
    {
        itemDescription = description;
        itemWeight = weight;
    }
    
    /**
     * Returns the description of the item
     * 
     * @return the description
     */
    public String getDescription()
    {
        return itemDescription;
    }
    
    /**
     * Returns the weight of the item
     * 
     * @return the weight
     */
    public int getWeight()
    {
        return itemWeight;
    }
}
