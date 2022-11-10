package com.wizard_assassin.graphics;

import com.wizard_assassin.controller.Controller;
import com.wizard_assassin.inputs.KeyboardInputs;
import com.wizard_assassin.model.Game;
import com.wizard_assassin.model.Music;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class GamePanel extends JPanel {
    Music music = new Music();

    // ATTRIBUTES
    // private JPanel mainPanel;
    private GamePanel gamePanel;
    private BufferedImage backgroundImage, myPicture, invBackground, gameVisualBackground, prefaceBG;
    private KeyboardInputs keyboardInputs;
    private Controller controller = new Controller();
    private String playerName, locationImg;


    //  GRAPHIC OBJECTS
    JPanel splashPanel, titlePanel, namePanel, wireFrame, textBox, directionBox, showHUDBox, showGameVisual;
    JButton backButton, nameButton, startButton, northButton, eastButton, southButton, westButton, selectButton, continueButton, getButton, upButton, downButton;
    JLabel titleBlock, inventoryBlock, locationBlock, picLabel, titleLabel, preLabel, invLabel;;
    JTextField nameField, gameTextField;
    JTextArea promptField;
    JScrollPane scrollPane;
    GIFPlayer gifPlayer;

    // CONSTRUCTOR
    public GamePanel() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        //setBackground(Color.blue);
        setPanelSize();
        setLayout(null);
        //this.add(new SplashPanel());
        splashPanel();
    }

    private void setPanelSize() {
        Dimension size = new Dimension(1280, 800);
        setPreferredSize(size);
    }

    //----------------------------------------------------------------------------------------------------------

    //     SPLASH SCREEN
    public void splashPanel() {
        gifPlayer = new GIFPlayer();
        ImageIcon display = new ImageIcon(gifPlayer.gifGetter("TitleScreenResources/sbg.gif"));
        JLabel bg = new JLabel(display);
        bg.setSize(1280,800);
        this.add(bg);

        BufferedImage titlePic = showPicture("TitleScreenResources/title.png");
        ImageIcon title = new ImageIcon(titlePic);

        splashPanel = new JPanel();
        startButton = new JButton("START");
        splashPanel.setLayout(null);
        splashPanel.setBounds(600, 400, 200, 50);
        splashPanel.setVisible(true);
        startButton.setBounds(600, 400, 200, 50);
        splashPanel.add(startButton);
        titleLabel = new JLabel(title);
        titleLabel.setVisible(true);
        titleLabel.setBounds(100, 30, 1200, 150);
        bg.add(titleLabel);
        bg.add(startButton);
//        while (titleCondition) {
//            this.add(titleLabel);
//        }
        add(splashPanel);
        startButton.addActionListener(e -> {
            bg.setVisible(false);
            splashPanel.setVisible(false);
            titleLabel.setVisible(false);
            namePanel();
        });
        importImg("TitleScreenResources/game_background_1.png");
    }


    //----------------------------------------------------------------------------------------------------------
    // Enter name screen
    private void namePanel() {
        JTextArea intro = new JTextArea();
        intro.setLineWrap(true);
        intro.append(" Wizard Assassin is a single-player game in which the objective is to defeat the evil wizard and save the king.");
        intro.append(" \n \n Wizard Assassin is a single-player game in which the objective is to defeat the evil wizard and save the king.");
        intro.append(" \n \n Once the Wizard Assassin reaches the Laboratory and defeat the evil wizard the player wins!!!!.");
        intro.setBounds(225,145, 750, 550);
        intro.setOpaque(false);
        //        JLabel introLabel1 = new JLabel("      Wizard Assassin is a single-player game in which the objective is to \n defeat the evil wizard and save the king.");
//        JLabel introLabel2 = new JLabel("      The player needs to explore different rooms in the castle as well as \n collect all items necessary to defeat the evil wizard.");
//        JLabel introLabel3 = new JLabel("      Once the Wizard Assassin reaches the Laboratory and defeat the evil \n wizard the player wins!!!!.");
//        introLabel1.setBounds(250, 150, 800, 150);
//        introLabel2.setBounds(250, 350, 800, 150);
//        introLabel3.setBounds(250, 550, 800, 50);
        Font font = new Font("Verdana", Font.BOLD, 18);
        intro.setFont(font);
//        introLabel1.setFont(font);
//        introLabel2.setFont(font);
//        introLabel3.setFont(font);
        continueButton = new JButton("CONTINUE");
        continueButton.setBounds(600, 700, 100, 100);
        namePanel = new JPanel();
        //namePanel.setLayout();
        namePanel.setBounds(500, 30, 300, 100);
        JLabel directions = new JLabel("Enter name below");
        nameButton = new JButton("ENTER");
        nameField = new JTextField(20);

        prefaceBG = showPicture("TitleScreenResources/par.jpg");
        ImageIcon preBG = new ImageIcon(prefaceBG);
        JLabel preLabel = new JLabel(preBG);
        preLabel.setBounds(220, 140, 840, 550);

        namePanel.add(directions);
        namePanel.add(nameField);
        namePanel.add(nameButton);
        namePanel.setVisible(true);

        add(namePanel);
        nameButton.addActionListener(e -> {
            if (nameField.getText().isEmpty()) {
                setPlayerName("Rennie");
            } else {
                setPlayerName(nameField.getText());
            }
//            this.add(introLabel1);
//            this.add(introLabel2);
//            this.add(introLabel3);
            this.add(intro);
            this.add(preLabel);
            this.add(continueButton);
            repaint();
            //add(namePanel);
        });
        continueButton.addActionListener(e -> {
            preLabel.setVisible(false);
            intro.setVisible(false);
//            introLabel1.setVisible(false);
//            introLabel2.setVisible(false);
//            introLabel3.setVisible(false);
            continueButton.setVisible(false);
            namePanel.setVisible(false);
            prefacePage();
            //wireFrame();
        });
        importImg("TitleScreenResources/StoneWall.jpeg");
    }

    //----------------------------------------------------------------------------------------------------------
    // Preface Page

    public void prefacePage() {
        JPanel prefacePanel = new JPanel();

        Font font = new Font("Verdana", Font.BOLD, 18);
        prefaceBG = showPicture("TitleScreenResources/par.jpg");
        ImageIcon preBG = new ImageIcon(prefaceBG);
        JLabel preLabel = new JLabel(preBG);
        preLabel.setBounds(220, 100, 810, 600);
        preLabel.setBackground(Color.lightGray);

        //prefacePanel.setBounds(450, 200, 500, 500);
        // prefacePanel.setOpaque(false);
        JTextArea prefaceText = new JTextArea();
        prefaceText.setFont(font);
        prefaceText.setOpaque(false);
        prefaceText.setVisible(true);
        prefaceText.setBounds(250,160, 750, 450);
        JButton nextButton = new JButton("NEXT");
        nextButton.setBounds(600, 730, 100, 40);
        nextButton.setVisible(true);
        prefaceText.setLineWrap(true);

        titleLabel.setVisible(true);
        titleLabel.setBounds(250,100, 800, 50);
        this.add(titleLabel);
        this.add(prefaceText);
        this.add(nextButton);

        //prefacePanel.add(nextButton);

        prefaceText.setText("\n"+getPlayerName() + " is in the " + Game.getViewLocation());
        prefaceText.append(" \n \n ");
        prefaceText.append("\n" + getPlayerName() + " spots the queen roaming about her garden. You decide to speak to her majesty. ");
        prefacePanel.setVisible(true);
        this.add(preLabel);
        //add(prefacePanel);
        nextButton.addActionListener(e -> {
            prefaceText.setText("The Queen says to you,\"Hello warrior, I've called you here because I have a special mission for you. This mission is sooooo special.... As you are well aware the Kingdom to the South has been mercilessly invading neighboring Kingdom's, slaughtering countless innocents, and it appears that we may be their next target. What you may not know is that we have gathered intel from a spy that this ruthless bloodshed is only occurring due to the influence of a powerful spell being placed on the entire Kingdom by an Evil Wizard. My plea for you and that of our people is for you to infiltrate the Kingdom to the South and ASSASSINATE this Evil Wizard. We know that this Wizard is guarded by a vicious monster but our inside source tells us that this beast is loyal only to the Evil Wizard. It relies mostly on scent, so you should be able to find something of the WIZARD's to trick the monster, ROBES, perhaps. Please spare as many lives as you can, since we don't believe any folk in the Kingdom to be acting of their own volition. When you're ready to go, I'll use this scroll to transport you to the Kingdom to the South. Are you ready?\"");
            nextButton.addActionListener(e1 -> {
                titleLabel.setVisible(false);
                prefaceText.setVisible(false);
                nextButton.setVisible(false);
                preLabel.setVisible(false);
                prefacePanel.setVisible(false);
                wireFrame();
            });
        });
    }

    //----------------------------------------------------------------------------------------------------------
    // WIRE FRAME WINDOW
    public void wireFrame() {
        importImg("TitleScreenResources/StoneWall.jpeg");
        textBox();
        directionBox();
        showHUDBox();
        showGameVisual();
        showPic();
    }

    //UPDATES WIREFRAME
    private void updateGame() {
        inventoryBlock.setText("Inventory: " + Game.getViewInventory().toString());
        locationBlock.setText("Location: " + Game.getViewLocation());
        promptField.setText(Game.getReturnPrompt());
        promptField.append(Game.getResponse());
        picLabel.setVisible(false);
        invLabel.setVisible(false);
        showPic();
        showHUDBox();
    }

    // TEXT BOX (BOTTOM LEFT)
    public void textBox() {
        textBox = new JPanel();
        promptField = new JTextArea();
        scrollPane = new JScrollPane(promptField);
        scrollPane.setBounds(0, 0, 800, 150);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        gameTextField = new JTextField(60);
        gameTextField.setBounds(10, 755, 800, 20);
        gameTextField.setVisible(true);
        textBox.setBounds(10, 630, 800, 150);
        textBox.setBackground(Color.GRAY);
        textBox.setLayout(null);
        textBox.setVisible(true);
        promptField.setBounds(5, 5, 600, 125);
        promptField.setVisible(true);
        promptField.setLineWrap(true);
        promptField.setText(Game.getReturnPrompt());

        textBox.add(scrollPane);
        //this.add(gameTextField);
        add(textBox);
    }

    // DIRECTION BOX (BOTTOM RIGHT)
    public void directionBox() {
        directionBox = new JPanel();
        directionBox.setOpaque(false);
        JButton b1 = new JButton("1");
        JButton b2 = new JButton("2");
        JButton b3 = new JButton("3");
        JButton b4 = new JButton("4");
        JButton b5 = new JButton("TALK");
        b1.setBounds(915, 580, 50, 50);
        b2.setBounds(965, 580, 50, 50);
        b3.setBounds(1015, 580, 50, 50);
        b4.setBounds(1065, 580, 50, 50);
        b5.setBounds(1140, 630, 70, 50);
        northButton = new JButton("N");
        northButton.setBounds(1015, 630, 50, 50);
        eastButton = new JButton("E");
        eastButton.setBounds(1070, 680, 50, 50);
        southButton = new JButton("S");
        southButton.setBounds(1015, 730, 50, 50);
        westButton = new JButton("W");
        westButton.setBounds(960, 680, 50, 50);
        upButton = new JButton("UP");
        upButton.setBounds(850, 630, 80, 50);
        downButton = new JButton("DOWN");
        downButton.setBounds(850, 730, 80, 50);
        selectButton = new JButton("FIGHT");
        selectButton.setBounds(1140, 730, 70, 50);
        directionBox.setBounds(840, 480, 400, 150);
        directionBox.setVisible(true);
        eastButton.setVisible(true);
        //getButton = new JButton("GET");
        //getButton.setBounds(1140, 680, 70, 50);
        this.add(b1);
        this.add(b2);
        this.add(b3);
        this.add(b4);
        this.add(b5);
        this.add(eastButton);
        this.add(northButton);
        this.add(southButton);
        this.add(westButton);
        this.add(upButton);
        this.add(downButton);
        this.add(selectButton);
        //this.add(getButton);
        northButton.addActionListener(e -> {
            System.out.println("North");
            controller.input("n");
            updateGame();
        });
        eastButton.addActionListener(e -> {
            System.out.println("East");
            controller.input("e");
            updateGame();
        });
        southButton.addActionListener(e -> {
            System.out.println("South");
            controller.input("s");
            updateGame();
        });
        westButton.addActionListener(e -> {
            System.out.println("West");
            controller.input("w");
            updateGame();
        });
        upButton.addActionListener(e -> {
            controller.input("u");
            updateGame();
        });
        downButton.addActionListener(e -> {
            controller.input("d");
            updateGame();
        });
        selectButton.addActionListener(e -> {
            System.out.println("Select");
            controller.input("f");
            updateGame();
        });
        /*getButton.addActionListener(e -> {
            System.out.println("Get");
            controller.input("g");
            updateGame();

        });*/
        b1.addActionListener(e -> {

        });

        b2.addActionListener(e -> {

        });
        b3.addActionListener(e -> {

        });
        b4.addActionListener(e -> {
            controller.input("use diamond key");
            updateGame();
        });
        b5.addActionListener(e -> {
            System.out.println("Talk");
            controller.input("t");
            updateGame();
        });
        add(directionBox);
    }

    // HUD BOX (TOP RIGHT)
    public void showHUDBox() {
        BufferedImage invBackground = showPicture("PanelAssets/inv2.png");
        ImageIcon invBG = new ImageIcon(invBackground);
        invLabel = new JLabel(invBG);
        invLabel.setVisible(true);
        invLabel.setBounds(840, 35, 400, 545);

        //showHUDBox = new JPanel();
        inventoryBlock = new JLabel();
        inventoryBlock.setText("Inventory: " + Game.getViewInventory().toString());
        inventoryBlock.setBounds(840, 30, 400, 100);

        locationBlock = new JLabel();
        locationBlock.setText(" Location: " + Game.getViewLocation());
        locationBlock.setBounds(10, 5, 160, 20);
        locationBlock.setOpaque(true);
        locationBlock.setBackground(Color.lightGray);
        //showHUDBox.setBackground(Color.cyan);
        //showHUDBox.setBounds(840, 40, 400, 450);
        //showHUDBox.setVisible(true);
        playerInventory();
        this.add(invLabel);
        this.add(locationBlock);
        invLabel.add(inventoryBlock);

        //add(showHUDBox);
    }


    public void playerInventory() {
        InventoryUI inventoryUI = new InventoryUI();
        ButtonGroup group = new ButtonGroup();
        JRadioButton use = new JRadioButton("USE", true);
        JRadioButton drop = new JRadioButton("DROP");
        JRadioButton examine = new JRadioButton("EXAMINE");
        use.setBounds(40, 250, 100, 40);
        drop.setBounds(155, 250, 100, 40);
        examine.setBounds(270, 250, 100, 40);

        List<String> inventory = Game.getViewInventory();

        Icon iconDefault = new ImageIcon(showPicture("ObjectsAssets/Layer_17.png"));

        JButton item1 = new JButton(iconDefault);
        JButton item2 = new JButton(iconDefault);
        JButton item3 = new JButton(iconDefault);
        JButton item4 = new JButton(iconDefault);
        JButton item5 = new JButton(iconDefault);
        JButton item6 = new JButton(iconDefault);
        item1.setBounds(40, 300, 80, 80);
        item2.setBounds(40, 400, 80, 80);
        item3.setBounds(155, 300, 80, 80);
        item4.setBounds(155, 400, 80, 80);
        item5.setBounds(270, 300, 80, 80);
        item6.setBounds(270, 400, 80, 80);

        if (inventory.size() >= 1) {
            Icon icon1 = new ImageIcon(showPicture(inventoryUI.inventorySetter(inventory.get(0))));
            item1.setIcon(icon1);
            item1.addActionListener(e -> {
                String action = "";

                if (use.isSelected()) {
                    action = "use";

                } else if (drop.isSelected()) {
                    action = "drop";

                } else {
                    action = "examine";
                }
                controller.input(action + " " + inventory.get(0));
                updateGame();
            });
        }
        if (inventory.size() >= 2) {
            Icon icon2 = new ImageIcon(showPicture(inventoryUI.inventorySetter(inventory.get(0))));
            item2.setIcon(icon2);
            item2.addActionListener(e -> {
                String action = "";

                if (use.isSelected()) {
                    action = "use";

                } else if (drop.isSelected()) {
                    action = "drop";

                } else {
                    action = "examine";
                }
                controller.input(action + " " + inventory.get(1));
                updateGame();
            });
        }
        if (inventory.size() >= 3) {
            Icon icon3 = new ImageIcon(showPicture(inventoryUI.inventorySetter(inventory.get(0))));
            item3.setIcon(icon3);
            item3.addActionListener(e -> {
                String action = "";

                if (use.isSelected()) {
                    action = "use";

                } else if (drop.isSelected()) {
                    action = "drop";

                } else {
                    action = "examine";
                }

                controller.input(action + " " + inventory.get(2));
                updateGame();
            });
        }
        if (inventory.size() >= 4) {
            Icon icon4 = new ImageIcon(showPicture(inventoryUI.inventorySetter(inventory.get(0))));
            item4.setIcon(icon4);
            item4.addActionListener(e -> {
                String action = "";

                if (use.isSelected()) {
                    action = "use";

                } else if (drop.isSelected()) {
                    action = "drop";

                } else {
                    action = "examine";
                }

                controller.input(action + " " + inventory.get(5));
                updateGame();
            });
        }
        if (inventory.size() >= 5) {
            Icon icon5 = new ImageIcon(showPicture(inventoryUI.inventorySetter(inventory.get(0))));
            item5.setIcon(icon5);
            item5.addActionListener(e -> {
                String action = "";

                if (use.isSelected()) {
                    action = "use";

                } else if (drop.isSelected()) {
                    action = "drop";

                } else {
                    action = "examine";
                }

                controller.input(action + " " + inventory.get(4));
                updateGame();
            });
        }
        if (inventory.size() >= 6) {
            Icon icon6 = new ImageIcon(showPicture(inventoryUI.inventorySetter(inventory.get(0))));
            item6.setIcon(icon6);
            item6.addActionListener(e -> {
                String action = "";

                if (use.isSelected()) {
                    action = "use";

                } else if (drop.isSelected()) {
                    action = "drop";

                } else {
                    action = "examine";
                }

                controller.input(action + " " + inventory.get(5));
                updateGame();
            });
        }
        group.add(use);
        group.add(drop);
        group.add(examine);

        invLabel.add(use);
        invLabel.add(drop);
        invLabel.add(examine);

        invLabel.add(item1);
        invLabel.add(item2);
        invLabel.add(item3);
        invLabel.add(item4);
        invLabel.add(item5);
        invLabel.add(item6);

    }


    public BufferedImage showPicture(String file) {
        InputStream is = getClass().getClassLoader().getResourceAsStream(file);
        try {
            myPicture = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return myPicture;
    }

    public void showPic() {
        String imgFile = Game.getViewLocation();
        BufferedImage currentImg = null;
        switch (imgFile) {
            case ("Queen's Garden"):
                currentImg = showPicture("TitleScreenResources/magic_garden.jpg");
                break;
            case ("Church"):
                currentImg = showPicture("TitleScreenResources/church.jpg");
                break;
            case ("Courtyard"):
                currentImg = showPicture("TitleScreenResources/courtyard1.png");
                break;
            case ("Watchtower"):
                currentImg = showPicture("TitleScreenResources/watchtower.jpg");
                break;
            case ("Armory"):
                currentImg = showPicture("TitleScreenResources/armory.jpg");
                break;
            case ("Dungeon"):
                currentImg = showPicture("TitleScreenResources/dungeon.jpg");
                break;
            case ("Great Hall"):
                currentImg = showPicture("TitleScreenResources/great_hall.jpg");
                break;
            case ("kitchen"):
                currentImg = showPicture("TitleScreenResources/kitchen.jpg");
                break;
            case ("Royal Lounge"):
                currentImg = showPicture("TitleScreenResources/royal_lounge.jpg");
                break;
            case ("Royal Library"):
                currentImg = showPicture("TitleScreenResources/library.jpg");
                break;
            case ("King's Chambers"):
                currentImg = showPicture("TitleScreenResources/king_chamber.jpg");
                break;
            case ("Wizard's Foyer"):
                currentImg = showPicture("TitleScreenResources/wizard_foyer.jpg");
                break;
            case ("Wizard's Chambers"):
                currentImg = showPicture("TitleScreenResources/wizard_room.jpg");
                break;
            case ("Laboratory"):
                currentImg = showPicture("TitleScreenResources/lab.jpg");
                break;
            default:
                currentImg = showPicture("TitleScreenResources/placeholder.jpg");
                break;
        }
        ImageIcon img = new ImageIcon(currentImg);
        picLabel = new JLabel(img);
        picLabel.setVisible(true);
        picLabel.setBounds(10, 30, 800, 550);
        roomItems();
        this.add(picLabel);
    }


    private void roomItems() {
        List<String> viewRoomItems;
        viewRoomItems = Game.getViewRoomItems();

        JButton item1 = new JButton();
        JButton item2 = new JButton();
        JButton item3 = new JButton();
        item1.setBounds(340, 470, 80, 80);
        item2.setBounds(440, 470, 80, 80);
        item3.setBounds(540, 470, 80, 80);

        switch (viewRoomItems.size()) {
            case (1):
                item1.setText(viewRoomItems.get(0).toUpperCase());
                picLabel.add(item1);
                item1.addActionListener(e -> {
                    controller.input("get " + viewRoomItems.get(0));
                    updateGame();
                });
                break;
            case (2):
                item1.setText(viewRoomItems.get(0).toUpperCase());
                item2.setText(viewRoomItems.get(1).toUpperCase());
                item1.addActionListener(e -> {
                    controller.input("get " + viewRoomItems.get(0));
                    updateGame();
                });
                item2.addActionListener(e -> {
                    controller.input("get " + viewRoomItems.get(1));
                    updateGame();
                });
                picLabel.add(item1);
                picLabel.add(item2);
                break;
            case (3):
                item1.setText(viewRoomItems.get(0).toUpperCase());
                item2.setText(viewRoomItems.get(1).toUpperCase());
                item3.setText(viewRoomItems.get(2).toUpperCase());
                item1.addActionListener(e -> {
                    controller.input("get " + viewRoomItems.get(0));
                    updateGame();
                });
                item2.addActionListener(e -> {
                    controller.input("get " + viewRoomItems.get(1));
                    updateGame();
                });
                item3.addActionListener(e -> {
                    controller.input("get " + viewRoomItems.get(2));
                    updateGame();
                });
                picLabel.add(item1);
                picLabel.add(item2);
                picLabel.add(item3);
                break;
            default:
                break;
        }
    }


    // GAME VISUAL (TOP LEFT)
    public void showGameVisual() {
        showGameVisual = new JPanel();
        showGameVisual.setBounds(10, 30, 800, 600);
        JButton quitButton = new JButton("QUIT");
        quitButton.setBounds(1180, 10, 70, 20);
        JButton settingsButton = new JButton("SETTINGS");
        settingsButton.setBounds(1000,10,80,20);
        this.add(quitButton);
        this.add(settingsButton);
        quitButton.addActionListener(e -> {
            System.exit(0);
        });

        settingsButton.addActionListener(e -> {
            music.initialize();
        });
    }

    //----------------------------------------------------------------------------------------------------------
    private BufferedImage importImg(String file) {
        InputStream is = getClass().getClassLoader().getResourceAsStream(file);

        try {
            backgroundImage = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        repaint();
        return null;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, 1280, 800, null);
    }

    // ACCESSOR METHODS


    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getLocationImg() {
        return locationImg;
    }

    public void setLocationImg(String locationImg) {
        this.locationImg = locationImg;
    }
}