import java.util.Scanner;

public class Casino {

    static String[] cards = { "Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack",
            "Queen", "King", "Joker" };
    static String[] suits = { "Clubs", "Diamonds", "Hearts", "Spades" };

    static String name;
    static int age;
    static int currency;
    static casinoGames state;
    static boolean casinoIsRunning;

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        state = casinoGames.HOME;

        age = 0;

        casinoIsRunning = true;

        while (casinoIsRunning) {

            switch (state) {

                case HOME:

                    while (age < 19) {

                        updatePlayerInfo(scanner, true, 3000);
                        pauseEffect();

                    }

                    System.out.println("\nYou currently have $" + currency + ", what would you like to do?\n");
                    System.out.println("\t1 - Play games");
                    System.out.println("\t2 - Update player information");
                    System.out.println("\t3 - Leave casino");

                    int homeOptions = scanner.nextInt();
                    scanner.nextLine();

                    if (homeOptions == 1) {

                        state = casinoGames.GAME_SELECTOR;

                    } else if (homeOptions == 2) {

                        state = casinoGames.RESETTING;

                    } else {

                        System.out.println("\nYou are leaving the casino with $" + currency + ", have a great day!");
                        casinoIsRunning = false;
                    }

                    break;

                case RESETTING:

                    System.out
                            .println("\nWould you like to reset your currency back to $3000, or to a custom amount?\n");
                    System.out.println("\t1 - Yes, reset to $3000");
                    System.out.println("\t2 - Yes, reset to custom amount");
                    System.out.println("\t3 - No");

                    int resetOptions = scanner.nextInt();
                    scanner.nextLine();

                    if (resetOptions == 1) {

                        updatePlayerInfo(scanner, true, 3000);
                        pauseEffect();

                    } else if (resetOptions == 2) {

                        System.out.println("How much would you like to recieve?");

                        int newAmount = scanner.nextInt();
                        scanner.nextLine();

                        updatePlayerInfo(scanner, true, newAmount);
                        pauseEffect();

                    } else {

                        updatePlayerInfo(scanner, false, 0);
                        pauseEffect();

                    }

                    state = casinoGames.HOME;

                    break;

                case GAME_SELECTOR:

                    System.out.println("\nWhat game would you like to play?\n");
                    System.out.println("\t1 - Blackjack");
                    System.out.println("\t2 - War");
                    System.out.println("\t3 - Suit-bet");
                    System.out.println("\t4 - Return to home");

                    int gameOptions = scanner.nextInt();
                    scanner.nextLine();

                    if (gameOptions == 1) {

                        System.out.println("\nWelcome to Blackjack!");
                        state = casinoGames.BLACKJACK;

                    } else if (gameOptions == 2) {

                        System.out.println("\nWelcome to War!");
                        state = casinoGames.WAR;

                    } else if (gameOptions == 3) {

                        System.out.println("\nWelcome to Suit-bet!");
                        state = casinoGames.SUITBET;

                    } else {
                        
                        pauseEffect();
                        state = casinoGames.HOME;

                    }

                    break;

                case BLACKJACK:

                    System.out.println("\nWhat would you like to do?");
                    System.out.println("\t1 - Play");
                    System.out.println("\t2 - Leave");

                    int blackJackOptions = scanner.nextInt();
                    scanner.nextLine();

                    if (blackJackOptions == 1) {

                        boolean gameIsRunning = true;
                        int playerScore = 0;

                        int initCard1 = drawCard(false);
                        int initCard2 = drawCard(false);

                        int card1Value = Math.min(initCard1, 10);
                        int card2Value = Math.min(initCard2, 10);

                        if (card1Value == 1) {
                            card1Value = 11;
                        }
                        if (card2Value == 1) {
                            card2Value = 11;
                        }

                        playerScore = card1Value + card2Value;

                        System.out.println(
                                "\nYour starting hand is " + cards[initCard1 - 1] + " and " + cards[initCard2 - 1]);
                        pauseEffect();

                        while (gameIsRunning) {

                            if (playerScore == 21) {

                                System.out.println("\nCongratulations! You achieved a hand-total of 21");
                                System.out.println("\n$500 will be added to your balance");
                                pauseEffect();

                                currency += 500;

                                gameIsRunning = false;
                                break;

                            } else if (playerScore > 21) {

                                System.out.println("\nYou went above 21, womp womp");
                                System.out.println("\n$500 will be taken from your account");
                                pauseEffect();

                                currency -= 500;

                                gameIsRunning = false;
                                break;

                            } else {

                                System.out.println("\nYour current score is " + playerScore);
                                pauseEffect();

                            }

                            System.out.println("\nWhat will you do?");
                            System.out.println("\t1 - Hit");
                            System.out.println("\t2 - Stand");

                            int hitOrStand = scanner.nextInt();

                            if (hitOrStand == 1) {

                                int drawCard = drawCard(false);
                                int drawValue = Math.min(drawCard, 10);

                                if (drawCard == 1 && playerScore < 11) {
                                    drawValue = 11;
                                }

                                playerScore += drawValue;

                                System.out.println("\nYou drew a(n) " + cards[drawCard - 1]);
                                pauseEffect();

                            } else {

                                System.out.println("\nYou are now standing with " + playerScore);
                                System.out.println("\nIt is now the dealer's turn");
                                pauseEffect();

                                int dealerScore = drawCard(false);
                                System.out.println("\n" + cards[dealerScore - 1]);
                                pauseEffect();

                                if (dealerScore == 1) {

                                    dealerScore = 11;

                                } else if (dealerScore > 10) {

                                    dealerScore = 10;

                                }

                                int secondDraw = drawCard(false);
                                System.out.println("\n" + cards[secondDraw - 1]);
                                pauseEffect();

                                if (secondDraw == 1 && dealerScore <= 10) {

                                    secondDraw = 11;

                                } else if (secondDraw > 10) {

                                    secondDraw = 10;

                                }

                                dealerScore += secondDraw;

                                while (dealerScore <= playerScore) {

                                    int dealerDraw = drawCard(false);
                                    System.out.println("\n" + cards[dealerDraw - 1]);
                                    pauseEffect();

                                    if (dealerDraw == 1 && dealerScore <= 10) {

                                        dealerDraw = 11;

                                    } else if (dealerDraw > 10) {

                                        dealerDraw = 10;

                                    }

                                    dealerScore += dealerDraw;

                                }

                                if (dealerScore > 21) {

                                    System.out.println(
                                            "\nThe dealer went bust with " + dealerScore
                                                    + ", congratulations! $500 will now be added to your account");
                                    pauseEffect();

                                    currency += 500;

                                    gameIsRunning = false;

                                } else {

                                    System.out.println("\nThe dealer achieved a higher hand of " + dealerScore
                                            + ", $500 will now be taken from your account");
                                    pauseEffect();

                                    currency -= 500;

                                    gameIsRunning = false;

                                }

                                // gameIsRunning = false;
                                // state = casinoGames.GAME_SELECTOR;
                                // pauseEffect();

                            }

                        }

                    } else {

                        System.out.println("See you soon, " + name);
                        pauseEffect();
                        state = casinoGames.GAME_SELECTOR;

                    }

                    break;

                case WAR:

                    System.out.println("\nWhat would you like to do?");
                    System.out.println("\t1 - Play");
                    System.out.println("\t2 - Leave");

                    boolean itIsWarO_O = false;

                    int warOptions = scanner.nextInt();
                    scanner.nextLine();

                    if (warOptions == 1) {

                        int playerDraw = drawCard(true);
                        System.out.println("\nYou have drawn a(n) " + cards[playerDraw - 1]);
                        pauseEffect();

                        int dealerDraw = drawCard(true);
                        System.out.println("\nThe dealer has drawn a(n) " + cards[dealerDraw - 1]);
                        pauseEffect();

                        if (playerDraw > dealerDraw) {

                            System.out.println(
                                    "\nYou have drawn a higher value card, $100 will be added to your account");
                            pauseEffect();

                            currency += 100;

                        } else if (playerDraw < dealerDraw) {

                            System.out.println(
                                    "\nYou have drawn a lower value card, $100 will be taken from your account");
                            pauseEffect();

                            currency -= 100;

                        } else {

                            System.out.println("\nYou have drawn the same value as the dealer, it is war...");
                            pauseEffect();
                            itIsWarO_O = true;

                        }

                        while (itIsWarO_O) {

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

                            if (playerTotalWarValue > dealerTotalWarValue) {

                                System.out.println("\nYou have won the war, $400 will be added to your account");
                                pauseEffect();

                                currency += 400;
                                itIsWarO_O = false;

                            } else if (playerTotalWarValue < dealerTotalWarValue) {

                                System.out.println("\nYou have lost the war, $400 will be taken from your account");
                                pauseEffect();

                                currency -= 400;
                                itIsWarO_O = false;

                            } else {

                                System.out.println("War is still among us");
                                pauseEffect();

                            }

                        }

                    } else {

                        System.out.println("See you soon, " + name);
                        pauseEffect();
                        state = casinoGames.GAME_SELECTOR;

                    }

                    break;

                case SUITBET:

                    System.out.println("\nWhat would you like to do?");
                    System.out.println("\t1 - Play");
                    System.out.println("\t2 - Leave");

                    int suitBetOptions = scanner.nextInt();
                    scanner.nextLine();

                    if (suitBetOptions == 1) {

                        System.out.println("\nBet on one of the following suits");
                        System.out.println("\t1 - Clubs");
                        System.out.println("\t2 - Diamonds");
                        System.out.println("\t3 - Hearts");
                        System.out.println("\t4 - Spades");

                        int playerSuitBet = scanner.nextInt();

                        System.out.println("\nHow much money will you bet?");
                        int playerBet = scanner.nextInt();
                        scanner.nextLine();

                        System.out.println("\nYou are betting $" + playerBet + " on " + suits[playerSuitBet - 1]);
                        pauseEffect();

                        int dealerBet = drawSuit();

                        while (dealerBet == playerSuitBet) {

                            dealerBet = drawSuit();

                        }

                        System.out.println("\nThe dealer is betting on " + suits[dealerBet - 1]);
                        pauseEffect();

                        boolean drawingSuits = true;

                        while (drawingSuits) {

                            int suitDraw = drawSuit();

                            System.out.println("\nThe dealer has drawn " + suits[suitDraw - 1]);
                            pauseEffect();

                            if (suitDraw == playerSuitBet) {

                                System.out.println(
                                        "\nCongratulations! $" + playerBet + " will be added to your account");
                                pauseEffect();

                                currency += playerBet;
                                drawingSuits = false;

                            } else if (suitDraw == dealerBet) {

                                System.out.println(
                                        "\nWomp womp, $" + playerBet + " will be removed from your account");
                                pauseEffect();

                                currency -= playerBet;
                                drawingSuits = false;

                            } else {

                                System.out.println("\nNo bets were correct, drawing again");
                                pauseEffect();

                            }

                        }

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

    static void updatePlayerInfo(Scanner scan, boolean resetCurrency, int resetAmount) {

        System.out.println("\nPlease enter name");
        name = scan.nextLine();

        System.out.println("\nPlease enter Age");
        age = scan.nextInt();
        scan.nextLine();

        if (resetCurrency) {

            currency = resetAmount;

        }

        if (age < 19) {

            System.out.println("\nYou are below the legal age for gambling in Ontario, please leave and try again \n");

        } else {

            System.out.println("\nWelcome to the casino, " + name);

        }

    }

    static int drawCard(boolean includeJokers) {

        int range = 13;

        if (includeJokers) {

            range = 14;

        }

        int card = (int) (Math.random() * range) + 1;

        return card;

    }

    static int drawSuit() {

        int suitIndex = (int) (Math.random() * 4) + 1;

        return suitIndex;

    }

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

    public enum casinoGames {

        HOME,
        RESETTING,
        GAME_SELECTOR,
        BLACKJACK,
        WAR,
        SUITBET

    }
}