import java.util.Scanner;

// A program that displays my local movie theaters movie offerings as of December 14, 2024
public class LandmarkBolton_TommyTang {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Array of movie titles playing at my theater
        String[] movies = {
                "Kraven the Hunter",
                "The Lord of the Rings: The War of the Rohirrim",
                "Pushpa 2: The Rule (Hindi w EST)",
                "Moana 2",
                "Gladiator II",
                "Wicked",
                "Red One",
                "The Wild Robot"
        };

        // Array of corresponding movie ratings
        String[] ratings = {
                "14A",
                "PG",
                "14A",
                "G",
                "14A",
                "PG",
                "PG",
                "G"
        };

        // 2D array representing showtimes for each movie
        String[][] times = {
                { "1:00 pm", "4:00 pm", "7:20 pm", "10:20 pm" },
                { "12:10 pm", "3:20 pm", "6:40 pm", "10:05 pm" },
                { "9:15 pm" },
                { "10:15 am", "12:30 pm", "12:40 pm", "3:10 pm", "3:30 pm", "6:10 pm", "6:30 pm", "9:45 pm",
                        "9:55 pm" },
                { "12:20 pm", "3:40 pm", "7:10 pm", "9:35 pm" },
                { "12:00 pm", "3:00 pm", "6:20 pm", "9:25 pm" },
                { "12:50 pm", "3:50 pm", "6:50 pm" },
                { "10:25 am" }
        };

        // Control variables for program flow
        boolean pageIsActive = true; // Keeps track of whether the program is running
        States page = States.GREETING; // Tracks the current page or state in the program

        int selectedMovie = 0; // Index of the movie selected by the user
        String userName; // Stores the user's name
        int age = 0; // Stores the user's age

        // Main loop to keep the program active until pageIsActive is false
        while (pageIsActive) {

            switch (page) {
                // State 1: Greeting page to collect user info and display initial options
                case GREETING:
                    System.out.println("What is your name?");
                    userName = scanner.nextLine(); // Collect user's name

                    System.out.println("How old are you?");
                    age = scanner.nextInt(); // Collect user's age

                    // Display a greeting and options
                    System.out.println(
                            "Hi " + userName
                                    + "! Welcome to Landmark Cinemas Bolton. What do you need today? \n Type in the corresponding number to perform action \n");
                    System.out.println("1 - \t View movies playing on Saturday, December 14, 2024");
                    System.out.println("2 - \t View theater details");

                    int choice = scanner.nextInt(); // User chooses an option

                    // Navigate to the appropriate state based on user's choice
                    if (choice == 1) {
                        page = States.DISPLAYING_MOVIES; // Go to movie listing
                    } else if (choice == 2) {
                        page = States.LOCATION_DETAILS; // Go to theater details
                    }

                    break;

                // State 2: Display the list of movies and allow selection for details
                case DISPLAYING_MOVIES:
                    selectedMovie = 0; // Reset selected movie
                    System.out.println("On Saturday, December 14, 2024, movies include: \n");

                    // Loop to display all movies with their ratings
                    for (int i = 0; i < movies.length; i++) {
                        System.out.println((i + 1) + " - \t" + movies[i] + ", " + ratings[i] + "\n");
                    }

                    System.out.println("Type a number to view movies in detail");
                    selectedMovie = scanner.nextInt(); // User selects a movie

                    // Validate input and navigate to movie details page
                    if (selectedMovie >= 1 && selectedMovie <= 8) {
                        page = States.MOVIE_DETAILS;
                    }

                    break;

                // State 3: Display detailed information about the selected movie
                case MOVIE_DETAILS:
                    selectedMovie--; // Adjust to zero-based index

                    // Check if the user's age is below the movie's rating requirements
                    if (age < 14 && ratings[selectedMovie].equals("14A")) {
                        System.out.println("\t WARNING");
                        System.out.println("You are currently under 14 and this movie is rated 14A \n");
                    } else if (age < 13 && ratings[selectedMovie].equals("PG")) {
                        System.out.println("\t WARNING");
                        System.out.println("You are currently under 13 and this movie is rated PG \n");
                    }

                    // Display movie details (title, rating, and showtimes)
                    System.out.println("You have selected " + movies[selectedMovie]);
                    System.out.println("Rating: " + ratings[selectedMovie]);
                    System.out.println("Times:");

                    // Loop to display all showtimes for the selected movie
                    for (String time : times[selectedMovie]) {
                        System.out.println(time);
                    }

                    pageIsActive = false; // Exit program after displaying movie details
                    break;

                // State 4: Display the theater location details
                case LOCATION_DETAILS:
                    System.out.println("Address: 194 McEwan Drive East, Caledon, ON, L7E 4E5");
                    pageIsActive = false; // Exit program after displaying location
                    break;
            }
        }

        scanner.close(); // Close the scanner to avoid resource leaks
    }

    // Enum to represent the different states/pages in the program
    enum States {
        GREETING, // Initial greeting and menu options
        DISPLAYING_MOVIES, // Display list of movies
        MOVIE_DETAILS, // Show details for a selected movie
        LOCATION_DETAILS // Show theater location details
    }
}
