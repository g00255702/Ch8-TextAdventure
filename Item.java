
/**
 * * This class is part of the "Trapped Genius" application. 
 * "Trapped Genius" is a very simple, text based adventure game.
 * 
 * An "Item" represents one location in the scenery of the game. It exits in every room
 * and is used to help the user throughout the game.
 *
 * @author Emeka Okonkwo
 * @version 2017.11.20
 */
public class Item
{
    // instance variables - replace the example below with your own
    private String itemDescription;
    private int itemWeight;
    
    /**
     * Sets the description and weight
     * 
     * @param description
     * @param weight
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
