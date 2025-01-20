import java.util.Scanner;

public class Casino {

    private static String name;
    private static int age;
    private static int currency;
    private static casinoGames state;

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        state = casinoGames.HOME;

        updatePlayerInfo(true);

        switch (state) {

            case HOME:

                System.out.println("You currently have $" + currency + "\nWhat would you like to do?");
                System.out.println("\t1 - Play games");
                System.out.println("\t2 - Update player information");
                System.out.println("\t3 - Leave casino");

                //int option = scanner.nextInt();

                break;

            case BLACKJACK:

                break;

        }

    }

    static boolean updatePlayerInfo(boolean resetCurrency) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter name");
        name = scanner.nextLine();

        System.out.println("Please enter Age");
        age = scanner.nextInt();

        scanner.close();

        if (resetCurrency) {
            currency = 3000;
        }

        if (age < 19) {
            System.out.println("You are below the legal age for gambling in Ontario, please leave");
            return false;
        } else {
            System.out.println("Welcome to the casino, " + name);
            return true;
        }

    }

    /*
     * ill make proper comments at some point, but for now minCard should only ever
     * be as low as 1(ace), and maxCard should only ever be as high as 14(joker)
     */
    static int drawCard(int minCard, int maxCard) {

        int range = (maxCard - minCard) + 1;
        return (int) (Math.random() * range) + minCard;

    }

    public enum casinoGames {

        HOME,
        BLACKJACK

    }
}