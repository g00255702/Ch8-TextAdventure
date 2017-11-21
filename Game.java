import java.util.Stack;

/**
 *  This class is the main class of the "Trapped Genius" application. 
 *  "Trapped Genius" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery and hope nothing bad happens during their travels.
 *  That's all.
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author Emeka Okonkwo
 * @version 2017.11.17
 * 
 * This version of the game has 12 Rooms and 12 Items
 * Added a back command
 * Added a look command
 * Added a eat command
 * Added a stack to go back multiple rooms
 * Added multiple unique Rooms that won't allow user to go back
 * 
 * 
 * 
 * 
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.08.10
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Room priorRoom;
    private Stack priorRooms = new Stack(); //used to implement the back command to take several rooms back
    private Room theMoon; // user is unable to go back to prior room once entered
    private Room ditch;
    private Room secret;
    private Room trap; // special room designed to only allow the user to enter this room over and over
    
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room basement, lab1, lab2, kitchen1, kitchen2, backyard, bathroom, otherside,portal;
      
        // create the rooms
        basement = new Room("currently in your happy place");
        lab1 = new Room("in your lab. You are surrounded by creatures not from earth");
        lab2 = new Room("in pitch black room with portal facing you");
        kitchen1 = new Room("in the kitchen. Why not grab a bite to eat");
        kitchen2 = new Room("in another kitchen, but nothing edible to eat");
        backyard = new Room("1,000 feet above ground level. Why don't you head back inside");
        secret = new Room("instantly dead without knowing what eat you");
        bathroom = new Room("Entered the bathroom. You know what to do from there");
        ditch = new Room("in a ditch.");
        otherside = new Room("somewhere not fimilar to earth");
        theMoon = new Room("on the Moon. You can't go back and have no oxygen to inhale. There is a space suit, but good luck putting that on in time. bye bye.");
        portal = new Room("inside a portal and feel nauseous. Where would you like to go from here?");
        trap = new Room("Idk. Lets go back");
        
        //creat items
        Item gloves = new Item("pair of gloves", 4);
        Item helmet = new Item("sturdy helment", 10);
        Item knife = new Item("sharp knife", 13);
        Item letter = new Item("Found a note saying: beware of south and west",1);
        Item skeleton = new Item("Dead corpse",15);
        Item giantPillow = new Item("giant pillow", 500);
        Item toiletPaper = new Item("toilet paper", 3);
        Item spaceSuit = new Item("Space suit", 30);
        Item headphones = new Item("headphones but no phone D:", 7);
        Item book = new Item("a book with a title named: research or death", 20);
        Item deadRat = new Item("a dead rat", 5);
        Item door = new Item("nothing but a door",0);
        Item note = new Item("Note: Don't let the pathway deceive you. You'll eventually get out", 1);
        
        //place items in rooms
        basement.setItem(knife);
        bathroom.setItem(toiletPaper);
        kitchen1.setItem(book);
        kitchen2.setItem(deadRat);
        lab1.setItem(gloves);
        lab2.setItem(helmet);
        ditch.setItem(skeleton);
        otherside.setItem(giantPillow);
        theMoon.setItem(spaceSuit);
        secret.setItem(door);
        portal.setItem(headphones);
        backyard.setItem(letter);
        trap.setItem(note);

        
        // initialise room exits
        bathroom.setExit("east", basement);
        
        kitchen1.setExit("south", basement);
        
        basement.setExit("west", bathroom);
        basement.setExit("east", lab1);
        basement.setExit("north", kitchen1);
        basement.setExit("south", trap);
        
        trap.setExit("north", trap);
        trap.setExit("south", trap);
        trap.setExit("east", trap);
        trap.setExit("west", trap);
        
        lab1.setExit("west", basement);
        lab1.setExit("east", kitchen2);
        lab1.setExit("south", lab2);
        
        lab2.setExit("north", lab1);
        lab2.setExit("west", portal);
        
        portal.setExit("east", lab2);
        portal.setExit("south", theMoon);
        portal.setExit("north", backyard);
        portal.setExit("west", secret);
        
        kitchen2.setExit("west", lab1);
        kitchen2.setExit("east", backyard);
        
        backyard.setExit("south", ditch);
        backyard.setExit("", kitchen2);
        
        
        ditch.setExit("south", otherside);
        
        otherside.setExit("south", secret);
        otherside.setExit("north", ditch);

        currentRoom = basement;  // start game outside
    }
    
    /**
     * Gets the long description of the player's room
     */
    private void look()
    {
        System.out.println(currentRoom.getLongDescription());
    }
    
    /**
     * Eat command
     */
    private void eat()
    {
        System.out.println("You have eaten now and you are not hungry anymore.");
    }


    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome Trapped Genius!");
        System.out.println("Trapped Genius is a new, incredibly simple text adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;
                
            case BACK:
                goback();
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
                
            case LOOK:
                look();
                break;
                
            case EAT:
                eat();
                break;
                
            
                
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            priorRoom = currentRoom;
            priorRooms.push(priorRoom);
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }
    
    /**
     * This methods gets the previous Room that you were currently in and goes back to that room
     * Modified to go back multiple rooms
     */
    private void goback()
    {
        if(priorRoom == null)
        {
            System.out.println("You haven't moved anywhere. Start walking!");
        }
        else if(currentRoom == ditch)
        {
            System.out.println("Good luck climbing that after falling so far down");
        }
        else if(currentRoom == secret)
        {
            System.out.println("Can't go back when your already dead");
        }
        else if(currentRoom == theMoon)
        {
            System.out.println("Sorry the portal just closed. Good luck");
        }
        else if(currentRoom == trap)
        {
            System.out.println("Sorry your stuck!");
        }
        else if(priorRooms.empty())
        {
            System.out.println("Well there isn't any need to retrace your steps unless your lost");
        }
        else{
            currentRoom = (Room) priorRooms.pop();
            System.out.println(currentRoom.getLongDescription());
        }
    }
    


    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
