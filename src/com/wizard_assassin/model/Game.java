package com.wizard_assassin.model;

import com.apps.util.Console;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Game implements Verbs {

    private Data obj;

    {
        try {
            obj = makeObj();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Location inventory = obj.getLocations().get(13);
    private List<String> inventoryItems = new ArrayList<String>(Arrays.asList(inventory.getItem()));
    private Scanner inputScanner = new Scanner(System.in);
    private int count = 0;
    private Location locationState = obj.getLocations().get(14);
    List<String> npcNames = new ArrayList<>();
    private String oldLocation = "";

    private String location = locationState.getName();
    private String description = locationState.getDescription();
    private ArrayList<String> verbNoun = new ArrayList<>(List.of("verb", "noun"));
    Scanner scanner = new Scanner(System.in);
    private String userInput;
    boolean loopCondition = true;


    public Game() {
        gameLoop();
    }

    public Data makeObj() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream locationRecFile = classLoader.getResourceAsStream("Location.json");
            obj = mapper.readValue(locationRecFile, Data.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }


    public void getAnItem(String itemInput, Location currentLocation, String verb) throws IOException {

        List<String> roomItems = new ArrayList<String>(Arrays.asList(currentLocation.getItem()));

        if (verb.equals("get") && !inventoryItems.contains(itemInput)) {
            roomItems.remove(itemInput);
            inventoryItems.add(itemInput);
            System.out.println("\n");
            System.out.printf("You picked up a \033[32m%s\033[0m and added it to your inventory.\n", itemInput);
        } else if (verb.equals("get") && inventoryItems.contains(itemInput)) {
            System.out.println("\nCan not \033[92m" + verb.toUpperCase() + "\033[0m \u001B[31m" + itemInput.toUpperCase() + "\u001B[0m. It's already in your inventory. Choose again...");
        } else if (verb.equals("drop") && inventoryItems.contains(itemInput)) {
            inventoryItems.remove(itemInput);
            roomItems.add(itemInput);
            System.out.println("\n");
            System.out.printf("You dropped the \033[32m%s\033[0m and removed it from your inventory.\n", itemInput);
        } else if (verb.equals("drop") && !inventoryItems.contains(itemInput)) {
            System.out.println("\nCan not \033[92m" + verb.toUpperCase() + "\033[0m \u001B[31m" + itemInput.toUpperCase() + "\u001B[0m. It is not in your inventory. Choose again...");
        }

        // Pick up item step 2, Put item in inventory
        String[] toInventory = new String[inventoryItems.size()];
        toInventory = inventoryItems.toArray(toInventory);
        inventory.setItem(toInventory);

        // INVENTORY PRINT OUT
        checkInventory();
        System.out.printf("You now see these items in the room: \033[32m%s\033[0m", roomItems);
        // END of INVENTORY

        // NOTE convert roomItems List to array. Update masterObj with changes
        String[] updatedRoomItems = roomItems.toArray(new String[0]);
        currentLocation.setItem(updatedRoomItems);
    }

    public void checkInventory() {
        System.out.println();
        System.out.println("*** Inventory ***");
        System.out.printf("Your inventory: \033[92m%s\033[0m", inventoryItems);
        System.out.println();
    }

    void gameLoop() {

        //Generate random int value from 0 to 2 for random sayings
        int num = (int) (Math.random() * (3));

        //Map of characters and quotes
        ObjectMapper mapper = new ObjectMapper();
        Characters object = null;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream charactersRecFile = classLoader.getResourceAsStream("characters.json");

        try {
            object = mapper.readValue(charactersRecFile, Characters.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, String> characterQuotes = new HashMap<>();

        for (ExtraCharacters extraCharacters : object.getCharacters())
            characterQuotes.put(extraCharacters.getName().toLowerCase(), extraCharacters.getQuote().get(num));


        //game loop
        while (isLoopCondition()) {

            //win condition
            winConditionCheck();

            //new location text prompt()
            prompt(getLocation(), getDescription(), characterQuotes, object);

            //User input  userInput()
            try {
                userInputPrompt(getLocation());
            } catch (IOException e) {
                e.printStackTrace();
            }

            //set verb noun variables
            String verb = getVerbNoun().get(0);
            String noun = getVerbNoun().get(1);

            /*
            //Troubleshooting
            System.out.println("verb: "+verb+" noun: "+noun);
            System.out.println("move actions: "+Verbs.getMoveActions());
            System.out.println("item actions: "+Verbs.getItemActions());
            System.out.println("character actions: "+Verbs.getCharacterActions());
            */

            //Actions
            if ("go".equals(verb) && Verbs.getMoveActions().contains(verb)) {
                //move
                action(verb, noun);
            }
            //use items
            else if (Verbs.getItemActions().contains(verb)) {
                useItem(verb, noun);
            }
            //character actions
            else if (Verbs.getCharacterActions().contains(verb)) {

                if (npcNames.contains(noun)) {
                    if (verb.equals("speak")) {
                        String characterQuote = characterQuotes.get(noun);
                        talk(noun, characterQuote);
                    } else if (verb.equals("fight")) {
                        fight(noun, object);
                    } else {
                        System.out.printf("There is no \u001B[93m%s\u001B[0m here... You must be seeing ghosts.%n", noun.toUpperCase());
                    }
                } else if (Verbs.getAreaActions().contains(verb)) {
                    System.out.println("This VERB is for area interactions");
                } else {
                    System.out.println("I do not understand " + userInput.toUpperCase() + ". Format command as 'VERB<space>NOUN' or 'quit' or 'view' or 'help' or 'inventory'");
                }

                //reset location
                setLocation(locationState.getName());

            }
        }
    }


    private void talk(String noun, Object characterQuote) {
        if (!noun.equals("queen")) {
            System.out.printf("\u001B[93m%s\u001B[0m: '%s'%n", noun.toUpperCase(), characterQuote);
        } else {
            System.out.printf("\u001B[93m%s\u001B[0m: '\033[95m%s\033[0m'%n", noun.toUpperCase(), characterQuote);
            while (true) {
                System.out.println();
                System.out.println("Press Enter to Continue");
                String input = inputScanner.nextLine().trim().toLowerCase();
                if (input.equals("")) {
                    locationState = obj.getPickedLocation("Church");
                    break;
                }
            }
        }
    }

    private void useItem(String verb, String noun) {
        if (verb.equals("use") && noun.equals("diamond key") && locationState.getName().equals("Great Hall")) {
            System.out.println("That \033[92mDIAMOND KEY\033[0m did the trick. You're in...");
            System.out.println();
            while (true) {
                System.out.println("Hit 'enter' to continue");
                String progress = inputScanner.nextLine();
                if (progress.equals("")) {
                    break;
                }
            }
            count++;
            locationState = obj.getPickedLocation("Wizard's Foyer");
        } else if (Arrays.asList(locationState.getItem()).contains(noun) || inventoryItems.contains(noun)) {
            try {
                getAnItem(noun, locationState, verb);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("\nCan not \033[92m" + verb.toUpperCase() + "\033[0m \u001B[31m" + noun.toUpperCase() + "\u001B[0m. Choose again...");
        }
    }

    private void action(String verb, String noun) {
        //move
        if (locationState.directions.get(noun) != null) {
            String locationInput = locationState.directions.get(noun);
            if (locationInput.equals("Courtyard") && getLocation().equals("Church")) {
                if (npcNames.isEmpty()) {
                    locationState = obj.getPickedLocation(locationInput);
                    setLocation(locationState.getName());
                } else {
                    System.out.printf("The \033[31m%s\033[0m blocks your path. You must fight it.\nUse the stick%n", npcNames.get(0).toUpperCase());
                }
            } else if (locationInput.equals("Great Hall") && getLocation().equals("Courtyard") && count == 0) {
                if (inventoryItems.contains("password")) {
                    System.out.println("\033[31mGuard:\033[0m That's the right \033[92mPASSWORD\033[0m. Go ahead and pass.");
                    System.out.println();
                    while (true) {
                        System.out.println("Hit 'enter' to continue");
                        String progress = inputScanner.nextLine();
                        if (progress.equals("")) {
                            break;
                        }
                    }
                    count++;
                    locationState = obj.getPickedLocation(locationInput);
                } else {
                    System.out.println("\033[31mGuard:\033[0m Wrong \033[92mPASSWORD\033[0m! Get outta here, ya scum!");
                }
            } else if (locationInput.equals("Royal Lounge") && getLocation().equals("Great Hall") && count == 1) {
                if (inventoryItems.contains("tunic") && inventoryItems.contains("sword")) {
                    System.out.println("\033[31mGuard:\033[0m I don't know you... but you have the Kingdom's \033[92mTUNIC\033[0m... and that \033[92mSWORD\033[0m... You must be new... go ahead and pass.");
                    System.out.println();
                    while (true) {
                        System.out.println("Hit 'enter' to continue");
                        String progress = inputScanner.nextLine();
                        if (progress.equals("")) {
                            break;
                        }
                    }
                    count++;
                    locationState = obj.getPickedLocation(locationInput);
                } else {
                    System.out.println("\033[31mGuard:\033[0m Where do you think you're goin? Only knights can pass through here.\nAnd not just any bloak with a Kingdom's \033[92mTUNIC\033[0m.\nYou need a \033[92mSWORD\033[0m too.");
                }
            } else if (locationInput.equals("Wizard's Foyer") && locationState.getName().equals("Great Hall") && count <= 2) {
                if (inventoryItems.contains("diamond key")) {
                    System.out.println("Maybe I can \033[31mUSE\033[0m that \033[92mDIAMOND KEY\033[0m on this door.");
                } else {
                    System.out.println("Hmm, it's locked. There's an emblem in the shape of a \033[92mDIAMOND\033[0m on the door");
                    System.out.println("\n\u001B[91m                         *********  You remain in the " + locationState.getName() + ". *********\u001B[0m\n\n");
                }
            } else {
                locationState = obj.getPickedLocation(locationInput);
            }
        } else {
            System.out.println("\n\u001B[31m" + noun.toUpperCase() + "\u001B[0m is not a valid direction. Choose again...");
        }

    }

    //fight method
    void fight(String noun, Characters object) {
        List<String> inventoryItems = getInventoryItems();
        if(noun.equals("evil wizard")) {
            if(inventoryItems.contains("knife")) {
                System.out.println("\033[91mThe Wizard suddenly blasts your head off with a thunder bolt... and you die!\033[0m");
                System.out.println("\033[91mG\033[0m\033[30mA\033[0m\033[91mM\033[0m\033[30mE\033[0m \033[91mO\033[0m\033[30mV\033[0m\033[91mE\033[0m\033[30mR\033[0m!");
                setLoopCondition(false);
            }
            else if(inventoryItems.contains("knife")){
                System.out.println("\033[36mThe Wizard suddenly attacks you with a thunder bolt but you matrix dodge it.\n You shank him with the\033[0m \033[92mKNIFE\033[0m \033[36mand he dies!\033[0m");
                System.out.println("You have shanked the wizard to death. You return home as a hero who saved your kingdom!");
                setLoopCondition(false);
            }
        }
        else if(inventoryItems.contains("sword")){
            int characterIndex= npcNames.indexOf(noun);
            object.getCharacters().remove(characterIndex);
            npcNames.remove(noun);
            if(!npcNames.isEmpty() || noun.equals("rat")) {
                System.out.printf("You stab \033[91m%s\033[0m in the heart and they die.\n Miraculously, no one notices.%n", noun.toUpperCase());
            }
            else {
                System.out.println("You've been found out!");
                System.out.println("Should've listened to the Queen and not gone on that killing spree... You lose");
                System.out.println("\033[91mG\033[0m\033[30mA\033[0m\033[91mM\033[0m\033[30mE\033[0m \033[91mO\033[0m\033[30mV\033[0m\033[91mE\033[0m\033[30mR\033[0m!");
                setLoopCondition(false);

            }
        }
        else if(inventoryItems.contains("stick") && noun.equals("rat")) {
            System.out.printf("You beat the \033[91m%s\033[0m to death with the \033[92mSTICK\033[0m", noun.toUpperCase());
            int characterIndex= npcNames.indexOf(noun);
            object.getCharacters().remove(characterIndex);

            npcNames.remove(noun);
        }
        else {
            System.out.println("I'm going to advise against that.");
        }
    }

    //win condition
    void winConditionCheck() {
        if (locationState.getName().equals("Laboratory") && (inventoryItems.contains("poison"))) {
            System.out.println("You have poisoned the wizard. You return home as a hero who saved your kingdom.");
            setLoopCondition(false);
        }
    }

    void prompt(String location, String description, Map<String, String> quotes, Characters object) {

        //location
        System.out.println("\n\u001B[35m                                              *********  You are in the " + location + ". *********\u001B[0m\n\n");

        //NPCs
        System.out.println(locationState.getDescription() + "\n");
        System.out.println("You see the following characters: ");
        npcNames.clear();
        for (ExtraCharacters extraCharacters : object.getCharacters()) {
            if ((location.equals(extraCharacters.getRoom()))) {
                System.out.printf("             \u001B[93m%s\u001B[0m%n", extraCharacters.getName().toUpperCase());
                npcNames.add(extraCharacters.getName().toLowerCase());
                quotes.put(extraCharacters.getName(), extraCharacters.getQuote().get(0));
            }
        }
        System.out.println();

        //death condition
        if (location.equals("Wizard's Foyer") && !inventoryItems.contains("wizard robes")) {
            System.out.println("\033[91mThe monster bites your head off and you die!\033[0m");
            System.out.println("\033[91mG\033[0m\033[30mA\033[0m\033[91mM\033[0m\033[30mE\033[0m \033[91mO\033[0m\033[30mV\033[0m\033[91mE\033[0m\033[30mR\033[0m!");
            setLoopCondition(false);
        }

        //Items
        if (locationState.getItem().length > 0) {
            System.out.printf("You see these items: \u001B[32m %s \u001B[0m%n", Arrays.deepToString(locationState.getItem()));
        }

        //Directions
        if (!locationState.getDirections().isEmpty()) {
            System.out.println("From the " + locationState.getName() + " you can go to the:");
            for (Map.Entry<String, String> direction : locationState.getDirections().entrySet()) {
                System.out.printf("       \u001B[31m %s: %s \u001B[0m%n", direction.getKey(), direction.getValue());
            }
        }
        System.out.println("\033[36m What would you like to do now?\033[0m\n\033[90mEnter 'quit' to exit game.\nEnter 'view' to see the map.\nEnter 'help' for list of valid commands.\nEnter 'inventory' to list all your items.\033[0m");
        System.out.println();

    }


    //user input processor, sets verbNoun attribute array
    private void userInputPrompt(String location) throws IOException {
        boolean validInput = false;
        while (!validInput) {

            userInput = scanner.nextLine().trim().toLowerCase();
            TextParser parser = new TextParser();
            //verb-noun pair array using text parser
            ArrayList<String> result = parser.textParser(userInput);

            //checks verbs and nouns for validity
            if (!"verb".equals(result.get(0))) {
                if (actionChecker(location, result.get(0))) {
                    validInput = true;
                    if ("inventory".equals(result.get(0))){
                        Console.clear();
                        checkInventory();
                        System.out.println("\nEnter back to exit");
                        boolean condition = false;
                        while (!condition) {
                            Scanner scanner = new Scanner(System.in);
                            String playerChoice = scanner.nextLine().trim().toLowerCase();
                            if (playerChoice.equals("back")) {
                                condition = true;
                            }
                        }
                    }
                    //sends to event handler if a global command
                    EventHandler.eventHandler(userInput, location);
                    setVerbNoun(result);
                } else {
                    System.out.println("Invalid Input: Enter as Prompted (verb and noun)");
                }
            }
        }
    }

    //checks if action is allowed for given location
    private boolean actionChecker(String location, String inputAction) {
        boolean result = false;
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode rootArray = mapper.readTree(new File("resources/locations.json"));
            //Always-allowed actions are hard coded
            ArrayList<String> actionsList = new ArrayList<>(List.of("help", "quit", "view", "inventory", "drop"));
            for (JsonNode root : rootArray) {
                // Get Name
                JsonNode nameNode = root.path(location);

                if (!nameNode.isMissingNode()) {  // if "name" node is not missing

                    for (JsonNode node : nameNode) {
                        // Get node names
                        JsonNode actionsNode = nameNode.path("actions");

                        if (actionsNode.equals(node)) {
                            for (JsonNode item : actionsNode) {
                                actionsList.add(item.asText());
                            }
                        }
                    }
                }
            }
            for (String action : actionsList) {
                if (inputAction.equals(action)) {
                    result = true;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        location = location;
    }


    public boolean isLoopCondition() {
        return loopCondition;
    }

    public void setLoopCondition(boolean loopCondition) {
        loopCondition = loopCondition;
    }

    public ArrayList<String> getVerbNoun() {
        return verbNoun;
    }

    public void setVerbNoun(ArrayList<String> verbNoun) {
        verbNoun = verbNoun;
    }

    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
        userInput = userInput;
    }

    public List<String> getInventoryItems() {
        return inventoryItems;
    }
}