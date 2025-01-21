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

                    } else if (resetOptions == 2) {

                        System.out.println("How much would you like to recieve?");

                        int newAmount = scanner.nextInt();
                        scanner.nextLine();

                        updatePlayerInfo(scanner, true, newAmount);

                    } else {

                        updatePlayerInfo(scanner, false, 0);

                    }

                    state = casinoGames.HOME;

                    break;

                case GAME_SELECTOR:

                    System.out.println("\nWhat game would you like to play?\n");
                    System.out.println("\t1 - Blackjack");
                    System.out.println("\t2 - what the sigma");
                    System.out.println("\t5 - Return to home");

                    int gameOptions = scanner.nextInt();
                    scanner.nextLine();

                    if (gameOptions == 1) {

                        System.out.println("\nWelcome to Blackjack!");
                        state = casinoGames.BLACKJACK;

                    } else {

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

                        while (gameIsRunning) {

                            if (playerScore == 21) {

                                System.out.println("\nCongratulations! You achieved a hand-total of 21");
                                System.out.println("\n$500 will be added to your balance...");

                                currency += 500;

                                gameIsRunning = false;
                                state = casinoGames.GAME_SELECTOR;

                            } else if (playerScore > 21) {

                                System.out.println("\nYou went above 21, womp womp");
                                System.out.println("\n$500 will be taken from your account...");

                                currency -= 500;

                                gameIsRunning = false;
                                state = casinoGames.GAME_SELECTOR;
                                break;

                            } else {

                                System.out.println("Your current score is " + playerScore);

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

                                System.out.println("\nYou drew a " + cards[drawCard - 1]);

                            } else {

                                System.out.println("Dealer is now hiogh on larte night deluelues");

                                gameIsRunning = false;
                                state = casinoGames.GAME_SELECTOR;
                            }

                        }

                    } else {

                        System.out.println("Good job, " + name);
                        state = casinoGames.HOME;

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

    public enum casinoGames {

        HOME,
        RESETTING,
        GAME_SELECTOR,
        BLACKJACK

    }
}