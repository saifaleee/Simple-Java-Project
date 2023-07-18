import java.util.Random; // for random number generation
import java.util.Scanner; // include Scanner library
import java.util.Calendar; // for current time


public class AirplaneTicketBooking {
    
    static final String FROM = "UAE";
    static String seat_no;
    static int luggage_weight = 0;
    static String passportNumber ;
    static final String[] COUNTRIES = {"USA", "UK", "Australia", "Japan", "China", "India"};
    static final int[] DISTANCES = {12000, 6000, 11000, 7000, 5000, 2000}; // in km
    static final String[] AIRLINES = {"Emirates", "Etihad", "Qatar Airways", "Air Arabia"};
    static final double[] PRICES = {1.29, 1.10, 0.92, 0.73}; // in AED per km


    //------------------------ MAIN FUNC------------------------------


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Create an object of Scanner, that will be used for input from terminal

        int choice;

        do {
            System.out.println("Welcome to Airplane Ticket Booking System!");
            System.out.println("Where would you like to travel to from " + FROM + "?");
            for (int i = 0; i < COUNTRIES.length; i++) {
                System.out.println((i+1) + ". " + COUNTRIES[i] + " (" + DISTANCES[i] + " km)");
            }
            System.out.print("Enter your choice (1-" + COUNTRIES.length + "): ");
            choice = scanner.nextInt(); // READ FROM USER 
            
        } while (choice < 1 || choice > COUNTRIES.length); // checking input range

        String to = COUNTRIES[choice-1];
        int distance = DISTANCES[choice-1];
        System.out.println("You have selected to travel from " + FROM + " to " + to + ".");
        System.out.println("The distance between " + FROM + " and " + to + " is " + distance + " km.");
        System.out.println("Please choose your airline:");
        for (int i = 0; i < AIRLINES.length; i++) {
            System.out.println((i+1) + ". " + AIRLINES[i] + " (" + PRICES[i] + " AED per km)");
        }
        System.out.print("Enter your choice (1-" + AIRLINES.length + "): ");
        choice = scanner.nextInt(); // CHOSING AIRLINE

        String airline = AIRLINES[choice-1]; // store user selected airpline
        double priceRate = PRICES[choice-1]; // store price_rate based on airline

        System.out.println("You have selected " + airline + " with price rate " + priceRate + " AED per km.");
        System.out.println("Please choose your class:");
        System.out.println("1. Economy");
        System.out.println("2. Business");
        System.out.print("Enter your choice (1-2): ");
        choice = scanner.nextInt(); // TICKET TYPE

        System.out.println("Please Enter Your Luggage Weight:");
        luggage_weight = scanner.nextInt(); // input weight
        int luggage_fee = calculateLuggageFee(luggage_weight);

        System.out.println("Please Enter Your Passport Number: (it should be 8-characters long)");
        scanner.nextLine();
        passportNumber = scanner.nextLine(); // input passport Number
        
        System.out.println("Please Enter Your Passport Expiry Year:");
        int passport_expiry_year = scanner.nextInt(); // input passport expiry

        //--------------------------- GENERATE RANDOM SEAT NUMBER----------------

        int row;
        char column;

        // create a new random number generator
        Random random = new Random();

        // generate a random integer between 0 and 99
        row = random.nextInt(100);
        
        column = (char) ((random.nextInt(27) + 65));

        seat_no = generateSeatNumber(row,column);

        if( isPassportValid(passportNumber,passport_expiry_year) ){ // PASSPORT IS VALID

        boolean isBusiness = (choice == 2); // CHECKING if user has buisness class or not and storing it in bool var
        double basePrice = distance * priceRate; // distanceX price 
        double taxRate = calculateTax(isBusiness); // calculating tax of passenger which depends on his class type
        double tax = basePrice * taxRate;
        double discount = calculateDiscount(airline);
        double total = (basePrice + tax + luggage_fee) * (1 - discount); 
        System.out.println("------------------------------------------------------------");
        System.out.println("The base price for your ticket is " + basePrice + " AED.");
        System.out.println("The Luggage Fee for your items is " + luggage_fee + " AED.");
        System.out.println("The tax rate for your ticket is " + (taxRate*100) + "%, and the tax amount is " + tax + " AED.");
        System.out.println("The discount rate for your ticket is " + (discount*100) + "%.");
        System.out.println("The total price for your ticket is " + total + " AED.");

        System.out.println("------------------------------------------------------------");
        System.out.println("Your Seat Number is : "+ seat_no);


        }
        
        else{ // Passport is not valid
             System.out.println("Your Passport length or expiry date is invalid ");
             scanner.close(); // close the scanner to avoid resource leak
             
                
            }


        
    }
       
        // ######################## FUNCTIONS ################################
        static double calculateTax(boolean isBusiness) {
        double taxRate;
        if (isBusiness) { // is passenger is buisness class he will have higher rate of tax
            taxRate = 0.1;
        } else {
            taxRate = 0.05;
        }
        return taxRate;
    }

        static double calculateDiscount(String airline) {
        double discount;
        switch(airline) {
            case "Emirates":
                discount = 0.2;
                break;
            case "Etihad":
                discount = 0.15;
                break;
            case "Qatar Airways":
                discount = 0.1;
                break;
            default: // same for air arabia
                discount = 0.05;
                break;
        }
        return discount;
    }

        static int calculateLuggageFee(int weight) {
        int fee = 0;
        if (weight > 20) { // if weight is greater than 20kg, than for every kg , passenger must pay 10 AED
            fee = (weight - 20) * 10;
        }
        return fee;
    }

        static boolean isPassportValid(String passportNumber, int expiryYear) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        return (passportNumber.length() == 8 && expiryYear >= currentYear); // check if passport is length of 8 and expiry year is not less than current year
    }


        static String generateSeatNumber(int row, char seat) {
        return row + String.valueOf(seat).toUpperCase();
    }



}

