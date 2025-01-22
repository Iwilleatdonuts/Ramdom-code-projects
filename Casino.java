import java.util.Scanner;

//This is a program that simulates a casino that allows the user to interact with different games and gamble currency
public class Casino {

    // Arrays to easily index the cards and suits
    static String[] cards = { "Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack",
            "Queen", "King", "Joker" };
    static String[] suits = { "Clubs", "Diamonds", "Hearts", "Spades" };

    // Control variables to run the program
    static casinoGames state; // Tracks the current state of the program
    static boolean casinoIsRunning; // Keeps track of whether the program is currently running

    static String name; // Stores the user's name
    static int age; // Stores the age of the user, used for legal age requirements
    static int currency; // Stores the user's currency

    public static void main(String[] args) throws Exception {

        // initiallize the scanner object for user input in the program
        Scanner scanner = new Scanner(System.in);

        // Sets the initial state to HOME
        state = casinoGames.HOME;

        // Begins the program
        casinoIsRunning = true;

        // Main loop to run the proram until the tracking variable is false
        while (casinoIsRunning) {

            // Initialize the state machine
            switch (state) {

                // Home state: Acts as the home page of this program, allows the user to switch
                // to
                // different pages
                case HOME:

                    // Runs the updatePlayerInfo method to get the players information, and loops to
                    // make sure the player is above the legal gambling age in Ontario
                    while (age < 19) {

                        updatePlayerInfo(scanner, true, 3000);
                        pauseEffect();// Pause effect to give the user an easier time reading lines before the page
                                      // moves on

                    }

                    // Option menu for player to navigate the program
                    System.out.println("\nYou currently have $" + currency + ", what would you like to do?\n");
                    System.out.println("\t1 - Play games");
                    System.out.println("\t2 - Update player information");
                    System.out.println("\t3 - Leave casino");

                    // Scan the user input
                    int homeOptions = scanner.nextInt();
                    scanner.nextLine();

                    // Take the user input to do appropriate task
                    if (homeOptions == 1) {

                        state = casinoGames.GAME_SELECTOR; // Switching to the game selector page

                    } else if (homeOptions == 2) {

                        state = casinoGames.RESETTING; // Switches to the player resetting page

                    } else {

                        System.out.println("\nYou are leaving the casino with $" + currency + ", have a great day!");
                        casinoIsRunning = false; // Gives the user a goodbye message, then turns off the program
                    }

                    break;

                // Restting state: gives the user the option to reset their game info
                case RESETTING:

                    // Option menu to allow to user to reset custom parameters and reads values
                    System.out
                            .println("\nWould you like to reset your currency back to $3000, or to a custom amount?\n");
                    System.out.println("\t1 - Yes, reset to $3000");
                    System.out.println("\t2 - Yes, reset to custom amount");
                    System.out.println("\t3 - No, reset only name and age");

                    int resetOptions = scanner.nextInt();
                    scanner.nextLine();

                    // Resets the users information and sets the currency back to default of 3000
                    if (resetOptions == 1) {

                        updatePlayerInfo(scanner, true, 3000);
                        pauseEffect();

                        // Resets the user information and allows the user to input a custom amount of
                        // currency
                    } else if (resetOptions == 2) {

                        System.out.println("How much would you like to recieve?");

                        int newAmount = scanner.nextInt();
                        scanner.nextLine();

                        updatePlayerInfo(scanner, true, newAmount);
                        pauseEffect();

                        // Resets the users name and age, but leaves their currency
                    } else {

                        updatePlayerInfo(scanner, false, 0);
                        pauseEffect();

                    }

                    // Switches back to the home state after the resetting is finished
                    state = casinoGames.HOME;

                    break;

                // Option state: Allows the user to choose different casino games to play
                case GAME_SELECTOR:

                    // Option menu for the user to select the game they want to play
                    System.out.println("\nWhat game would you like to play?\n");
                    System.out.println("\t1 - Blackjack");
                    System.out.println("\t2 - War");
                    System.out.println("\t3 - Suit-bet");
                    System.out.println("\t4 - Return to home");

                    int gameOptions = scanner.nextInt();
                    scanner.nextLine();

                    // Switches the state to the blackjack game state
                    if (gameOptions == 1) {

                        System.out.println("\nWelcome to Blackjack!");
                        state = casinoGames.BLACKJACK;

                        // Switches the state to the war game state
                    } else if (gameOptions == 2) {

                        System.out.println("\nWelcome to War!");
                        state = casinoGames.WAR;

                        // Switches the state to the suit-bet game state
                    } else if (gameOptions == 3) {

                        System.out.println("\nWelcome to Suit-bet!");
                        state = casinoGames.SUITBET;

                        // Switches the state back to the home page
                    } else {

                        pauseEffect();
                        state = casinoGames.HOME;

                    }

                    break;

                // Game state: allows the user to play black jack against the dealer/program
                case BLACKJACK:

                    // Option menu for the user to choose what to do
                    System.out.println("\nWhat would you like to do?");
                    System.out.println("\t1 - Play");
                    System.out.println("\t2 - Leave");

                    int blackJackOptions = scanner.nextInt();
                    scanner.nextLine();

                    // Runs a round of blackjack if the user selects the play option
                    if (blackJackOptions == 1) {

                        // Tracking variable to keep track of whether or not the game is running
                        boolean gameIsRunning = true;
                        // Initializes and sets the player score to zero
                        int playerScore = 0;

                        // Draws two seperate cards, excluding jokers
                        int initCard1 = drawCard(false);
                        int initCard2 = drawCard(false);

                        // Caps the value of the cards because anything above a 10 in blackjack is
                        // considered 10
                        int card1Value = Math.min(initCard1, 10);
                        int card2Value = Math.min(initCard2, 10);

                        // Aces are considered 11 in blackjack, as long as they do not make the player
                        // go bust
                        if (card1Value == 1) {
                            card1Value = 11;
                        }
                        if (card2Value == 1) {
                            card2Value = 11;
                        }

                        // Calculates the actual score of the first two cards
                        playerScore = card1Value + card2Value;

                        // Prints out the individual cards that the player drew, seperate from the
                        // values of the cards
                        System.out.println(
                                "\nYour starting hand is " + cards[initCard1 - 1] + " and " + cards[initCard2 - 1]);
                        pauseEffect();

                        // Main loop of the game where the user continually draws cards until they
                        // decide to stand
                        while (gameIsRunning) {

                            // If the score is 21, the player automatically wins the game
                            if (playerScore == 21) {

                                System.out.println("\nCongratulations! You achieved a hand-total of 21");
                                System.out.println("\n$500 will be added to your balance");
                                pauseEffect();

                                // Adds $500 to the current balance
                                currency += 500;

                                // Turns off the main loop of the game to allow the player to leave or play a
                                // second round
                                gameIsRunning = false;
                                break;

                                // Checks if the player goes above 21, or bust
                            } else if (playerScore > 21) {

                                System.out.println("\nYou went above 21, womp womp");
                                System.out.println("\n$500 will be taken from your account");
                                pauseEffect();

                                // Removes $500 from the player balance
                                currency -= 500;

                                // Turns off the main loop of the game to allow the player to leave or play a
                                // second round
                                gameIsRunning = false;
                                break;

                                // If neither situation happened, reads out the current score and allows the
                                // program to continue
                            } else {

                                System.out.println("\nYour current score is " + playerScore);
                                pauseEffect();

                            }

                            // Gives the user the option to either draw another card or to stand
                            System.out.println("\nWhat will you do?");
                            System.out.println("\t1 - Hit");
                            System.out.println("\t2 - Stand");

                            int hitOrStand = scanner.nextInt();

                            // Draws another card if the user selects this option
                            if (hitOrStand == 1) {

                                int drawCard = drawCard(false);
                                int drawValue = Math.min(drawCard, 10);// Caps the value at 10 if the card drawn is
                                                                       // royal

                                // checks if the player has an ace and if they can use the ace as 11 without
                                // going bust
                                if (drawCard == 1 && playerScore < 11) {
                                    drawValue = 11;
                                }

                                // Adds the updated values to the player score
                                playerScore += drawValue;

                                // Outputs what cards the player drew
                                System.out.println("\nYou drew a(n) " + cards[drawCard - 1]);
                                pauseEffect();

                                // Runs if the player decides to stand instead of drawing another card
                            } else {

                                // Signals the start of the dealers turn
                                System.out.println("\nYou are now standing with " + playerScore);
                                System.out.println("\nIt is now the dealer's turn");
                                pauseEffect();

                                // Draws the initial two cards for the dealer
                                int dealerScore = drawCard(false);
                                System.out.println("\n" + cards[dealerScore - 1]);
                                pauseEffect();

                                // Raises the value of aces and decreases the value of roayals in accordance to
                                // blackjack rules
                                if (dealerScore == 1) {

                                    dealerScore = 11;

                                } else if (dealerScore > 10) {

                                    dealerScore = 10;

                                }

                                // Performs the same actions on the second card
                                int secondDraw = drawCard(false);
                                System.out.println("\n" + cards[secondDraw - 1]);
                                pauseEffect();

                                if (secondDraw == 1 && dealerScore <= 10) {

                                    secondDraw = 11;

                                } else if (secondDraw > 10) {

                                    secondDraw = 10;

                                }

                                // Calculates the dealers score after the first two draws
                                dealerScore += secondDraw;

                                // Runs the code to draw more cards while the dealer has a lower score than the
                                // player
                                while (dealerScore <= playerScore) {

                                    // Draws a card
                                    int dealerDraw = drawCard(false);
                                    System.out.println("\n" + cards[dealerDraw - 1]);
                                    pauseEffect();

                                    if (dealerDraw == 1 && dealerScore <= 10) {

                                        dealerDraw = 11;

                                    } else if (dealerDraw > 10) {

                                        dealerDraw = 10;

                                    }

                                    // Adds the filtered score to the total score for the dealer
                                    dealerScore += dealerDraw;

                                }

                                // Cheks if the dealer went above 21
                                if (dealerScore > 21) {

                                    // Prints out a victory message for the player if they beat the dealer
                                    System.out.println(
                                            "\nThe dealer went bust with " + dealerScore
                                                    + ", congratulations! $500 will now be added to your account");
                                    pauseEffect();

                                    // Adds $500 to the players current balanace
                                    currency += 500;

                                    // Turns off the loop to allow the player to leave the game or play another
                                    // round
                                    gameIsRunning = false;

                                    // Once the code reaches this point, the dealer can only have less than 21 but
                                    // more than the player, meaning they win
                                } else {

                                    System.out.println("\nThe dealer achieved a higher hand of " + dealerScore
                                            + ", $500 will now be taken from your account");
                                    pauseEffect();

                                    // Subtracts $500 from the user
                                    currency -= 500;

                                    // Allows the palyer to leave or paly another round
                                    gameIsRunning = false;

                                }

                            }

                        }

                        // Runs this code if the user selects to leave blackjack
                    } else {

                        System.out.println("See you soon, " + name);
                        pauseEffect();
                        state = casinoGames.GAME_SELECTOR; // Switches the state back to the game menu

                    }

                    break;

                // Game state: allows the user to play war against the dealer/program
                case WAR:

                    // Option menu for the player
                    System.out.println("\nWhat would you like to do?");
                    System.out.println("\t1 - Play");
                    System.out.println("\t2 - Leave");

                    // Initializes a variable to keep track of whether there is a current war
                    boolean isWar = false;

                    int warOptions = scanner.nextInt();
                    scanner.nextLine();

                    // Runs if the user decides to play
                    if (warOptions == 1) {

                        // Draws a card for the player, prints it to the terminal
                        int playerDraw = drawCard(true);
                        System.out.println("\nYou have drawn a(n) " + cards[playerDraw - 1]);
                        pauseEffect();

                        // Draws a card for the dealer, prints it to the terminal
                        int dealerDraw = drawCard(true);
                        System.out.println("\nThe dealer has drawn a(n) " + cards[dealerDraw - 1]);
                        pauseEffect();

                        // Checks if the players card out-values the dealers
                        if (playerDraw > dealerDraw) {

                            System.out.println(
                                    "\nYou have drawn a higher value card, $100 will be added to your account");
                            pauseEffect();

                            // Victory money is added
                            currency += 100;

                            // Checks if the player's card is out-valued by the dealer's
                        } else if (playerDraw < dealerDraw) {

                            System.out.println(
                                    "\nYou have drawn a lower value card, $100 will be taken from your account");
                            pauseEffect();

                            // Subtracts money from the users account
                            currency -= 100;

                            // Runs if the dealer and the player both drew the same card
                        } else {

                            System.out.println("\nYou have drawn the same value as the dealer, it is war...");
                            pauseEffect();

                            // Turns the war variable to true, enabling the block to run the tiebreaker code
                            isWar = true;

                        }

                        // Loops that runs if a tiebreaker/war is required
                        while (isWar) {

                            // draws three cards for the player, and sums up the three values
                            System.out.println("\nYou have drawn a(n)");

                            int playerWarDraw1 = drawCard(true);
                            System.out.println("\n" + cards[playerWarDraw1 - 1]);
                            pauseEffect();

                            int playerWarDraw2 = drawCard(true);
                            System.out.println("\n" + cards[playerWarDraw2 - 1]);
                            pauseEffect();

                            int playerWarDraw3 = drawCard(true);
                            System.out.println("\n" + cards[playerWarDraw3 - 1]);
                            pauseEffect();

                            int playerTotalWarValue = playerWarDraw1 + playerWarDraw2 + playerWarDraw3;

                            System.out.println("\nYour total value is " + playerTotalWarValue);
                            pauseEffect();

                            // Draws three cards for the dealer and sums up the three values
                            System.out.println("\nThe dealer has drawn a(n)");

                            int dealerWarDraw1 = drawCard(true);
                            System.out.println("\n" + cards[dealerWarDraw1 - 1]);
                            pauseEffect();

                            int dealerWarDraw2 = drawCard(true);
                            System.out.println("\n" + cards[dealerWarDraw2 - 1]);
                            pauseEffect();

                            int dealerWarDraw3 = drawCard(true);
                            System.out.println("\n" + cards[dealerWarDraw3 - 1]);
                            pauseEffect();

                            int dealerTotalWarValue = dealerWarDraw1 + dealerWarDraw2 + dealerWarDraw3;

                            System.out.println("\nThe dealer's total value is " + dealerTotalWarValue);
                            pauseEffect();

                            // Checks if the player has a higher total score than the dealer
                            if (playerTotalWarValue > dealerTotalWarValue) {

                                System.out.println("\nYou have won the war, $400 will be added to your account");
                                pauseEffect();

                                // Adds $400 if the player wins
                                currency += 400;
                                // Turns off the war variable to close the war loop and end the round
                                isWar = false;

                                // Checks if the player has a lower total score than the dealer
                            } else if (playerTotalWarValue < dealerTotalWarValue) {

                                System.out.println("\nYou have lost the war, $400 will be taken from your account");
                                pauseEffect();

                                // Subtracts $400
                                currency -= 400;

                                // Closes the war loop to end the roud
                                isWar = false;

                                // Runs the code again if the user and dealer have the same values, meaning the
                                // tiebreaker must keep going
                            } else {

                                System.out.println("War is still among us");
                                pauseEffect();

                            }

                        }

                        // Leaves the war game and goes back to the game selector page if the user
                        // selected the return option
                    } else {

                        System.out.println("See you soon, " + name);
                        pauseEffect();
                        state = casinoGames.GAME_SELECTOR;

                    }

                    break;

                // Game state: allows the player to bet money against the dealer
                case SUITBET:

                    // Option menu for the player to play or leave
                    System.out.println("\nWhat would you like to do?");
                    System.out.println("\t1 - Play");
                    System.out.println("\t2 - Leave");

                    int suitBetOptions = scanner.nextInt();
                    scanner.nextLine();

                    // Runs if the user decides to play
                    if (suitBetOptions == 1) {

                        // Option menu to give the user a choice of which suit to bet on
                        System.out.println("\nBet on one of the following suits");
                        System.out.println("\t1 - Clubs");
                        System.out.println("\t2 - Diamonds");
                        System.out.println("\t3 - Hearts");
                        System.out.println("\t4 - Spades");

                        int playerSuitBet = scanner.nextInt();

                        // Asks the player to input a certain amount of money to bet
                        System.out.println("\nHow much money will you bet?");
                        int playerBet = scanner.nextInt();
                        scanner.nextLine();

                        // Reads out the player info for clarification
                        System.out.println("\nYou are betting $" + playerBet + " on " + suits[playerSuitBet - 1]);
                        pauseEffect();

                        // Draws a random suit for the dealer
                        int dealerBet = drawSuit();

                        // Forces the loop to infinitely draw suits until the dealer has a different one
                        // from the user
                        while (dealerBet == playerSuitBet) {

                            dealerBet = drawSuit();

                        }

                        // Prints out what suit the dealer is betting on
                        System.out.println("\nThe dealer is betting on " + suits[dealerBet - 1]);
                        pauseEffect();

                        // Enables a loop that draws suits until one person is correct
                        boolean drawingSuits = true;

                        // Loops through the draw code until one player is correct
                        while (drawingSuits) {

                            int suitDraw = drawSuit();

                            System.out.println("\nThe dealer has drawn " + suits[suitDraw - 1]);
                            pauseEffect();

                            // Checks if the player had the correct bet
                            if (suitDraw == playerSuitBet) {

                                System.out.println(
                                        "\nCongratulations! $" + playerBet + " will be added to your account");
                                pauseEffect();

                                // Adds the amount that the player bet into their account
                                currency += playerBet;
                                drawingSuits = false; // Disables the loop for the drawing code to run and ends the loop

                                // Checks if the dealer had the correct bet
                            } else if (suitDraw == dealerBet) {

                                System.out.println(
                                        "\nWomp womp, $" + playerBet + " will be removed from your account");
                                pauseEffect();

                                // Removes the amount that the player betted from their account
                                currency -= playerBet;
                                drawingSuits = false;// Disables the loop to allow the user to leave or play again

                                // Runs the loop again if neither bet was the same as the drawn suit
                            } else {

                                System.out.println("\nNo bets were correct, drawing again");
                                pauseEffect();

                            }

                        }

                        // Brings the user back to the game selector page if they choose to leave
                    } else {

                        System.out.println("See you soon, " + name);
                        pauseEffect();
                        state = casinoGames.GAME_SELECTOR;

                    }

                    break;

            }

        }

        scanner.close();

    }

    /*
     * Method to handle updating player information as this can be called in many
     * ways
     * 
     * @param scan the scanner object used to centralize the scanner
     * 
     * @param resetCurrenct the boolean to decide if the user is going to reset
     * their currency or not
     * 
     * @param resetAmount will only be used if resetCurrency is true, amount to
     * reset the currency to
     */
    static void updatePlayerInfo(Scanner scan, boolean resetCurrency, int resetAmount) {

        // Takes the player's information
        System.out.println("\nPlease enter name");
        name = scan.nextLine();

        System.out.println("\nPlease enter Age");
        age = scan.nextInt();
        scan.nextLine();

        // Resets the currency if necessary
        if (resetCurrency) {

            currency = resetAmount;

        }

        // Prints out a welcome message if the player is of legal gambling age in
        // Ontario (19)
        if (age < 19) {

            System.out.println("\nYou are below the legal age for gambling in Ontario, please leave and try again \n");

        } else {

            System.out.println("\nWelcome to the casino, " + name);

        }

    }

    /*
     * Method to handle the logic of drawing a card from ace to joker
     * 
     * @param includeJokers changes whether the deck draws 1-13 or 1-14 depending on
     * if jokers should be in the deck
     */
    static int drawCard(boolean includeJokers) {

        // sets the range of the random number generator to 13 by default
        int range = 13;

        // Incraeses the range to 14 if jokers are included
        if (includeJokers) {

            range = 14;

        }

        // Math.random returns a double from 0-1, meaning that it must be multiplied by
        // the scale needed, range, and then typecasted into an integer to be used in
        // the program
        int card = (int) (Math.random() * range) + 1;

        // returns the randomized card
        return card;

    }

    /*
     * Method to handle the logic of drawing a different suit of cards
     */
    static int drawSuit() {

        // Type casts the double so that it returns a random integer between 1-4
        // inclusive
        int suitIndex = (int) (Math.random() * 4) + 1;

        return suitIndex;

    }

    // Method to delay the code with a small ... visual to make the output easier to
    // process
    static void pauseEffect() throws InterruptedException {

        System.out.print("\n");
        Thread.sleep(500);
        System.out.print(" .");
        Thread.sleep(500);
        System.out.print(" .");
        Thread.sleep(500);
        System.out.println(" .");
        Thread.sleep(500);

    }

    // Enums are used in this code to represent the different stages of being in the
    // casino
    public enum casinoGames {

        HOME,
        RESETTING,
        GAME_SELECTOR,
        BLACKJACK,
        WAR,
        SUITBET

    }
}