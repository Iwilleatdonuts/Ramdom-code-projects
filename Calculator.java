import java.util.Scanner; // Import the Scanner class for user input

public class Calculator {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in); // Create a Scanner object to read input

        int firstNum;  // Variable to store the first number
        int secondNum; // Variable to store the second number
        int operation; // Variable to store the users chosen operation

        String operator; // String to store the operator symbol for display purposes

        // Prompt the user to enter the first number
        System.out.println("Please type the first number below");
        firstNum = scanner.nextInt();

        // Prompt the user to enter the second number
        System.out.println("Please type the second number below");
        secondNum = scanner.nextInt();

        // Call the operationChecker method to get the operation choice from the user
        operation = operationChecker(scanner);

        // Sets the operator symbol based on the users choice
        if (operation == 1) {
            operator = "+";
        } else if (operation == 2) {
            operator = "-";
        } else if (operation == 3) {
            operator = "*";
        } else {
            operator = "/";
        }

        // Perform the calculation and display the result in formatted output
        System.out.printf("%d %s %d = %.2f%n", firstNum, operator, secondNum, calculate(operation, firstNum, secondNum));
        
        // Close the scanner to prevent resource leaks
        scanner.close();
    }

    // Method to perform the calculation based on the selected operation
    static double calculate(int operation, double firstNum, double secondNum) {
        double product; // Variable to store the result
        if (operation == 1) { // Addition
            product = firstNum + secondNum;
        } else if (operation == 2) { // Subtraction
            product = firstNum - secondNum;
        } else if (operation == 3) { // Multiplication
            product = firstNum * secondNum;
        } else { // Division
            product = firstNum / secondNum;
        }
        return product; // Return the result of the operation
    }

    // Method to check the operation choice from the user
    static int operationChecker(Scanner scanner) {
        boolean checker = false; // Used to indicate if a valid input has been provided
        int newOperator = 0;     // Stores the user's choice

        // Loop until a proper operation is entered
        while (!checker) {
            // Display the available operations
            System.out.println("Type: \n 1 - Add \n 2 - Subtract \n 3 - Multiply \n 4 - Divide \n");
            
            // Get the user's input
            newOperator = scanner.nextInt();

            // Check if the input is between 1 to 4
            if (newOperator >= 1 && newOperator <= 4) {
                checker = true; // Exit the loop if valid input is entered
            }
        }
        return newOperator; // Return the checked operation choice
    }
}
