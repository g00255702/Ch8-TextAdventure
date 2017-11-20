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
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.08.10
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
        
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
        Room basement, lab1, lab2, kitchen1, kitchen2, backyard, secret, bathroom, ditch, otherside, theMoon, portal;
      
        // create the rooms
        basement = new Room("You are currently in your happy place");
        lab1 = new Room("You have entered your lab. You are surrounded by creatures not from earth");
        lab2 = new Room("You enter a pitch black room with portal facing you");
        kitchen1 = new Room("Entered the kitchen. Why not grab a bite to eat");
        kitchen2 = new Room("Found yourself in another kitchen, but nothing edible to eat");
        backyard = new Room("Find yourself 1,000 feet above ground level. Why don't you head back inside");
        secret = new Room("You die instantly without knowing what eat you");
        bathroom = new Room("Entered the bathroom. You know what to do from there");
        ditch = new Room("You fell into a ditch");
        otherside = new Room("You find yourself not fimilar to earth");
        theMoon = new Room("Welcome to the Moon. You can't go back and have no oxygen to inhale. bye bye.");
        portal = new Room("You stepped into a portal and feel nauseous. Where would you like to go from here?");
        
        
        
        // initialise room exits
        bathroom.setExit("west", basement);
        
        kitchen1.setExit("north", basement);
        
        basement.setExit("east", bathroom);
        basement.setExit("west", lab1);
        basement.setExit("south", kitchen1);
        
        lab1.setExit("east", basement);
        lab1.setExit("west", kitchen2);
        lab1.setExit("north", lab2);
        
        lab2.setExit("south", lab1);
        lab2.setExit("east", portal);
        
        portal.setExit("west", lab2);
        portal.setExit("north", theMoon);
        
        kitchen2.setExit("east", lab1);
        kitchen2.setExit("west", backyard);
        
        backyard.setExit("north", ditch);
        backyard.setExit("east", kitchen2);
        
        ditch.setExit("north", otherside);
        
        otherside.setExit("north", secret);
        otherside.setExit("south", ditch);

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
            currentRoom = nextRoom;
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
