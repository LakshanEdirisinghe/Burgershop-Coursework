import java.util.*;

class Main {
    public static final int PREPARING = 0;
    public static final int DELIVERED = 1;
    public static final int CANCEL = 2;

    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);

        
        String[] orderId = new String[70];
        String[] cudIdArray = new String[70];
        String[] names = new String[70];
        int[] quantityOfBurger = new int[70];
        int[] orderStatus = new int[70];
        int orderID = 0; 
        int turn = 0; // Index for storing orders

        // Main menu loop
        while (true) {
            // Home page (menu)
            System.out.println("-------------------------------------------------------------------------------");
            System.out.printf("%s%45s%33s\n", "|", "iHungry Burger", "|");
            System.out.println("-------------------------------------------------------------------------------");

            System.out.println();

            System.out.printf("%s%45s\n", "[1] Place Order ", "[2] Search Best Customer");
            System.out.printf("%s%39s\n", "[3] Search Order ", "[4] Search Customer");
            System.out.printf("%s%45s\n", "[5] View Orders ", "[6] Update Order Details");
            System.out.println("[7] Exit");

            System.out.println('\n');

            System.out.print("Enter an option to continue > ");
            int option = input.nextInt();
            input.nextLine(); // Clear the buffer after nextInt()
            System.out.println();

            switch (option) {
                case 1:
                    clearConsole();
                    boolean case1 = true;
                    while (case1) {
                        System.out.println("-------------------------------------------------------------------------------");
                        System.out.printf("%s%43s%35s\n", "|", "PLACE ORDER", "|");
                        System.out.println("-------------------------------------------------------------------------------");

                        
                        orderID++;
                        int count = 0;
                        int copyOfNum = orderID;
                        while (copyOfNum > 0) {
                            count++;
                            copyOfNum /= 10;
                        }
                        String stringNum = Integer.toString(orderID);
                        for (int x = 0; x < (4 - count); x++) {
                            stringNum = "0" + stringNum;
                        }
                        String fullOrderId = "B" + stringNum;
                        System.out.println("\nORDER ID - " + fullOrderId);
                        System.out.println("================");
                        System.out.println();

                        // Customer ID input with format validation
                        String cusId = custIdValidation();

                        // Check if Customer ID exists in cudIdArray
                        boolean isItExist = false;
                        String cusName = null;
                        for (int i = 0; i < turn; i++) {
                            if (cudIdArray[i] != null && cudIdArray[i].equalsIgnoreCase(cusId)) {
                                isItExist = true;
                                cusName = names[i]; // Get the name from the first matching order
                                System.out.println("Customer Name: " + cusName);
                                break;
                            }
                        }

                        if (!isItExist) {
                            System.out.print("Enter Customer Name: ");
                            cusName = input.nextLine();
                        }

                        // Proceed with quantity input
                        int quantity;
                        boolean check;
                        do {
                            System.out.print("Enter Burger Quantity - ");
                            quantity = input.nextInt();
                            input.nextLine(); 
                            check = quantity <= 0;
                            if (check) {
                                System.out.println("Invalid quantity.\n");
                            }
                        } while (check);

                        double total = quantity * 500.0;
                        System.out.printf("Total value - %.2f\n", total);

                        System.out.print("\tAre you confirm order (Y/N) - ");
                        char confamation = input.nextLine().charAt(0);
                        boolean isitok = confamation == 'y' || confamation == 'Y';

                        if (isitok) {
                            // Store order details
                            orderId[turn] = fullOrderId;
                            cudIdArray[turn] = cusId;
                            names[turn] = cusName; // Store the name (existing or newly entered)
                            quantityOfBurger[turn] = quantity;
                            orderStatus[turn] = PREPARING;
                            System.out.println("\tYour order is entered into the system successfully...");
                            turn++; 
                        } else {
                            case1 = false;
                        }

                        if (isitok) {
                            System.out.print("\nDo you want to place another order (Y/N): ");
                            char confamation2 = input.nextLine().charAt(0);
                            if (confamation2 == 'N' || confamation2 == 'n') {
                                case1 = false;
                            }
                        }
                        clearConsole();
                    }
                    break;

                case 2:
                    clearConsole();
                    boolean ok = true;

                    L1: while (ok) {
                        System.out.println("-------------------------------------------------------------------------------");
                        System.out.printf("%s%43s%35s\n", "|", "BEST CUSTOMER", "|");
                        System.out.println("-------------------------------------------------------------------------------");
                        System.out.println();

                        if (turn == 0) {
                            System.out.println("No customers found!");
                        } else {
                            // Step 1: Find unique customer IDs
                            String[] uniqueCudIds = new String[1];
                            int uniqueCount = 0;

                            for (int i = 0; i < turn; i++) {
                                String currentCudId = cudIdArray[i];
                                boolean isDuplicate = false;

                                for (int j = 0; j < uniqueCount; j++) {
                                    if (uniqueCudIds[j].equalsIgnoreCase(currentCudId)) {
                                        isDuplicate = true;
                                        break;
                                    }
                                }

                                if (!isDuplicate) {
                                    if (uniqueCount == uniqueCudIds.length) {
                                        // Resize the array
                                        String[] uniqueCudIdsCopy = new String[uniqueCudIds.length + 1];
                                        for (int v = 0; v < uniqueCudIds.length; v++) {
                                            uniqueCudIdsCopy[v] = uniqueCudIds[v];
                                        }
                                        uniqueCudIds = uniqueCudIdsCopy;
                                    }
                                    uniqueCudIds[uniqueCount] = currentCudId;
                                    uniqueCount++;
                                }
                            }

                            // Step 2: Create arrays for unique customers and calculate totals
                            String[] finalUniqueCudIds = new String[uniqueCount];
                            String[] finalUniqueNames = new String[uniqueCount];
                            double[] finalUniqueTotals = new double[uniqueCount];

                            for (int f = 0; f < uniqueCount; f++) {
                                String cudId = uniqueCudIds[f];
                                finalUniqueCudIds[f] = cudId;

                                // Find the name (take the first occurrence since it’s consistent)
                                String customerName = "";
                                for (int x = 0; x < turn; x++) {
                                    if (cudIdArray[x].equalsIgnoreCase(cudId)) {
                                        customerName = names[x];
                                        break;
                                    }
                                }
                                finalUniqueNames[f] = customerName;

                                // Calculate total for this customer
                                double tot = 0.0;
                                for (int x = 0; x < turn; x++) {
                                    if (cudIdArray[x].equalsIgnoreCase(cudId)) {
                                        tot += quantityOfBurger[x] * 500.0;
                                    }
                                }
                                finalUniqueTotals[f] = tot;
                            }

                            // Step 3: Sort by total in descending order (bubble sort)
                            for (int i = 0; i < uniqueCount - 1; i++) {
                                for (int j = 0; j < uniqueCount - i - 1; j++) {
                                    if (finalUniqueTotals[j] < finalUniqueTotals[j + 1]) {
                                        // Swap totals
                                        double tempTotal = finalUniqueTotals[j];
                                        finalUniqueTotals[j] = finalUniqueTotals[j + 1];
                                        finalUniqueTotals[j + 1] = tempTotal;

                                        // Swap customer IDs
                                        String tempId = finalUniqueCudIds[j];
                                        finalUniqueCudIds[j] = finalUniqueCudIds[j + 1];
                                        finalUniqueCudIds[j + 1] = tempId;

                                        // Swap names
                                        String tempName = finalUniqueNames[j];
                                        finalUniqueNames[j] = finalUniqueNames[j + 1];
                                        finalUniqueNames[j + 1] = tempName;
                                    }
                                }
                            }

                            // Step 4: Display the sorted list
                            System.out.println("--------------------------------------------------");
                            System.out.printf("%-15s%-20s%-10s\n", "CustomerID", "Name", "Total");
                            System.out.println("--------------------------------------------------");
                            for (int i = 0; i < uniqueCount; i++) {
                                System.out.printf("%-15s%-20s%.2f\n", finalUniqueCudIds[i], finalUniqueNames[i], finalUniqueTotals[i]);
                                System.out.println("--------------------------------------------------");
                            }
                        }

                        System.out.print("\nDo you want to go back to main menu? (Y/N)> ");
                        char choice = input.nextLine().charAt(0);
                        if (choice == 'Y' || choice == 'y') {
                            clearConsole();
                            ok = false;
                        } else {
                            clearConsole();
                            continue L1;
                        }
                    }
                    break;

                case 3:
                    clearConsole();
                    boolean searchAgain = true;
                    while (searchAgain) {
                        System.out.println("-------------------------------------------------------------------------------");
                        System.out.printf("%s%43s%35s\n", "|", "SEARCH ORDER", "|");
                        System.out.println("-------------------------------------------------------------------------------");
                        System.out.println();

                        System.out.print("Enter Order ID: ");
                        String searchId = input.nextLine().trim().toUpperCase(); // Convert input to uppercase

                        boolean found = false;
                        int foundIndex = -1;

                        // Search through existing orders
                        for (int i = 0; i < turn; i++) {
                            if (searchId.equals(orderId[i])) {
                                found = true;
                                foundIndex = i;
                                break;
                            }
                        }

                        if (found) {
                            
                            System.out.println("----------------------------------------------------------------------------------");
                            System.out.printf("%-15s%-15s%-20s%-10s%-12s%-15s\n", "OrderID", "CustomerID", "Name", "Quantity", "Value", "Status");
                            System.out.println("----------------------------------------------------------------------------------");

                            double totalValue = quantityOfBurger[foundIndex] * 500.0;
                            String status = "";

                            switch (orderStatus[foundIndex]) {
                                case 1:
                                    status = "DELIVERED";
                                    break;
                                case 2:
                                    status = "CANCEL";
                                    break;
                                default:
                                    status = "PREPARING";
                                    break;
                            }

                            System.out.printf("%-15s%-15s%-20s%-10d%-12.2f%-15s\n", orderId[foundIndex], cudIdArray[foundIndex], names[foundIndex], quantityOfBurger[foundIndex], totalValue, status);
                            System.out.println("----------------------------------------------------------------------------------");
                        } else {
                            System.out.println("\nInvalid Order ID!");
                        }

                        System.out.print("\nDo you want to search again? (Y/N): ");
                        char choice = input.nextLine().charAt(0);
                        if (choice == 'N' || choice == 'n') {
                            searchAgain = false;
                        }
                        clearConsole();
                    }
                    break;

                case 4:
                    clearConsole();
                    boolean searchCustomer = true;
                    while (searchCustomer) {
                        boolean found = false;
                        System.out.println("-------------------------------------------------------------------------------");
                        System.out.printf("%s%43s%35s\n", "|", "SEARCH CUSTOMER", "|");
                        System.out.println("-------------------------------------------------------------------------------");
                        System.out.println();

                        String searchCusId = custIdValidation();
                        String customerName = "";

                        for (int c = 0; c < cudIdArray.length; c++) {
                            if (searchCusId.equalsIgnoreCase(cudIdArray[c])) {
                                found = true;
                                customerName = names[c];
                                break;
                            }
                        }
                        if (found) {
                            System.out.println("\nCustomerID - " + searchCusId);
                            System.out.println("Name        - " + customerName);
                            System.out.println("\nCustomer Order Details");
                            System.out.println("==========================\n");
                            System.out.println("----------------------------------------------------");
                            System.out.printf("%-10s%-22s%-15s\n", "Order_ID", "Order_Quantity", "Total Value");
                            System.out.println("----------------------------------------------------");

                            for (int i = 0; i < cudIdArray.length; i++) {
                                if (searchCusId.equalsIgnoreCase(cudIdArray[i])) {
                                    double totalValue = quantityOfBurger[i] * 500.00;
                                    System.out.printf("%-15s%-17d%-15.2f\n", orderId[i], quantityOfBurger[i], totalValue);
                                    System.out.println("----------------------------------------------------");
                                }
                            }
                        } else {
                            System.out.println("Customer ID " + searchCusId + " not found in the system!");
                        }

                        System.out.print("\nDo you want to search another customer? (Y/N): ");
                        char choice = input.nextLine().charAt(0);
                        if (choice == 'N' || choice == 'n') {
                            searchCustomer = false;
                        }
                        clearConsole();
                    }
                    break;

                case 5:
                    clearConsole();
                    boolean continueAgain = true;
                    while (continueAgain) {
                        System.out.println("-------------------------------------------------------------------------------");
                        System.out.printf("%s%43s%35s\n", "|", "VIEW ORDER LIST", "|");
                        System.out.println("-------------------------------------------------------------------------------");
                        System.out.println("\n");

                        boolean found = false;

                        System.out.println("[1] Delivered Order");
                        System.out.println("[2] Preparing Order");
                        System.out.println("[3] Cancel Order");
                        System.out.print("\nEnter an option to continue > ");
                        int subOption = input.nextInt();
                        input.nextLine(); 

                        clearConsole();

                        switch (subOption) {
                            case 1:
                                System.out.println("-------------------------------------------------------------------------------");
                                System.out.printf("%s%43s%35s\n", "|", "DELIVERED ORDER", "|");
                                System.out.println("-------------------------------------------------------------------------------");
                                System.out.println("\n");

                                for (int x = 0; x < orderStatus.length; x++) {
                                    if (orderStatus[x] == DELIVERED) {
                                        found = true;
                                        break;
                                    }
                                }

                                if (found) {
                                    System.out.println("-----------------------------------------------------------------------");
                                    System.out.printf("%-10s%-12s%-10s%-12s%-12s\n", "OrderID", "CustomerID", "Name", "Quantity", "OrderValue");
                                    System.out.println("-----------------------------------------------------------------------");

                                    for (int i = 0; i < turn; i++) {
                                        if (orderStatus[i] == DELIVERED) {
                                            double totalValue = quantityOfBurger[i] * 500.0;
                                            System.out.printf("%-10s%-12s%-12s%-12d%-12.2f\n",
                                                    orderId[i], cudIdArray[i], names[i], quantityOfBurger[i], totalValue);
                                        }
                                    }
                                    System.out.println("-----------------------------------------------------------------------");
                                } else {
                                    System.out.println("No delivered orders found!");
                                }
                                break;

                            case 2:
                                System.out.println("-------------------------------------------------------------------------------");
                                System.out.printf("%s%43s%35s\n", "|", "PREPAING ORDER", "|");
                                System.out.println("-------------------------------------------------------------------------------");
                                System.out.println("\n");

                                for (int x = 0; x < orderStatus.length; x++) {
                                    if (orderStatus[x] == PREPARING) {
                                        found = true;
                                        break;
                                    }
                                }

                                if (found) {
                                    System.out.println("-----------------------------------------------------------------------");
                                    System.out.printf("%-10s%-12s%-10s%-12s%-12s\n", "OrderID", "CustomerID", "Name", "Quantity", "OrderValue");
                                    System.out.println("-----------------------------------------------------------------------");

                                    for (int i = 0; i < turn; i++) {
                                        if (orderStatus[i] == PREPARING) {
                                            double totalValue = quantityOfBurger[i] * 500.0;
                                            System.out.printf("%-10s%-12s%-12s%-12d%-12.2f\n",
                                                    orderId[i], cudIdArray[i], names[i], quantityOfBurger[i], totalValue);
                                        }
                                    }
                                    System.out.println("-----------------------------------------------------------------------");
                                } else {
                                    System.out.println("Preparing Orders feature not yet implemented.");
                                }
                                break;

                            case 3:
                                System.out.println("-------------------------------------------------------------------------------");
                                System.out.printf("%s%43s%35s\n", "|", "CANCEL ORDER", "|");
                                System.out.println("-------------------------------------------------------------------------------");
                                System.out.println("\n");

                                for (int x = 0; x < orderStatus.length; x++) {
                                    if (orderStatus[x] == CANCEL) {
                                        found = true;
                                        break;
                                    }
                                }

                                if (found) {
                                    System.out.println("-----------------------------------------------------------------------");
                                    System.out.printf("%-10s%-12s%-10s%-12s%-12s\n", "OrderID", "CustomerID", "Name", "Quantity", "OrderValue");
                                    System.out.println("-----------------------------------------------------------------------");

                                    for (int i = 0; i < turn; i++) {
                                        if (orderStatus[i] == CANCEL) {
                                            double totalValue = quantityOfBurger[i] * 500.0;
                                            System.out.printf("%-10s%-12s%-12s%-12d%-12.2f\n",
                                                    orderId[i], cudIdArray[i], names[i], quantityOfBurger[i], totalValue);
                                        }
                                    }
                                    System.out.println("-----------------------------------------------------------------------");
                                } else {
                                    System.out.println("Canceled Orders feature not yet implemented.");
                                }
                                break;

                            default:
                                System.out.println("Invalid sub-option!");
                        }

                        System.out.print("\nDo you want to go to home page? (Y/N): ");
                        char choice = input.nextLine().charAt(0);
                        if (choice == 'Y' || choice == 'y') {
                            continueAgain = false;
                        }
                        clearConsole();
                    }
                    break;

                case 6:
                    clearConsole();
                    boolean updateAgain = true;
                    while (updateAgain) {
                        System.out.println("-------------------------------------------------------------------------------");
                        System.out.printf("%s%43s%35s\n", "|", "UPDATE ORDER DETAILS", "|");
                        System.out.println("-------------------------------------------------------------------------------");
                        System.out.println();

                        System.out.print("Enter Order ID: ");
                        String searchId = input.nextLine().trim().toUpperCase();
                        System.out.println();

                        boolean found = false;
                        int foundIndex = -1;
                        for (int i = 0; i < turn; i++) {
                            if (searchId.equalsIgnoreCase(orderId[i])) {
                                found = true;
                                foundIndex = i;
                                break;
                            }
                        }

                        if (found) {
                            if (orderStatus[foundIndex] == PREPARING) {
                                
                                System.out.printf("%-13s%-3s%s\n", "Order ID", "-", orderId[foundIndex]);
                                System.out.printf("%-13s%-3s%s\n", "Customer ID", "-", cudIdArray[foundIndex]);
                                System.out.printf("%-13s%-3s%s\n", "Name", "-", names[foundIndex]);
                                System.out.printf("%-13s%-3s%d\n", "Quantity", "-", quantityOfBurger[foundIndex]);

                                double totalValue = quantityOfBurger[foundIndex] * 500.0;
                                System.out.printf("%-13s%-3s%.2f\n", "Order Value", "-", totalValue);
                                System.out.printf("%-13s%-3s%s\n", "Order Status", "-", "PREPARING");

                                System.out.println("\nWhat do you want to update?");
                                System.out.println("\t(1) Quantity");
                                System.out.println("\t(2) Status");
                                System.out.println();

                                System.out.print("Enter your option - ");
                                int subOption = input.nextInt();
                                input.nextLine(); 

                                switch (subOption) {
                                    case 1:
                                        System.out.println("\nQuantity Update");
                                        System.out.println("===============\n");
                                        System.out.printf("%-13s%-3s%s\n", "Order ID", "-", orderId[foundIndex]);
                                        System.out.printf("%-13s%-3s%s\n", "Customer ID", "-", cudIdArray[foundIndex]);
                                        System.out.printf("%-13s%-3s%s\n", "Name", "-", names[foundIndex]);
                                        System.out.println();

                                        System.out.print("Enter your quantity update value - ");
                                        int newQuantity = input.nextInt();
                                        input.nextLine(); 
                                        if (newQuantity > 0) {
                                            quantityOfBurger[foundIndex] = newQuantity;
                                            System.out.println("\tUpdate order quantity successfully...\n");
                                            System.out.println("New order quantity - " + quantityOfBurger[foundIndex]);
                                            totalValue = quantityOfBurger[foundIndex] * 500.0;
                                            System.out.printf("%s%.2f\n\n", "New order value - ", totalValue);
                                        } else {
                                            System.out.println("Invalid quantity. Please enter a positive integer.\n");
                                        }
                                        break;

                                    case 2:
                                        System.out.println("\nStatus Update");
                                        System.out.println("=============\n");
                                        System.out.printf("%-13s%-3s%s\n", "Order ID", "-", orderId[foundIndex]);
                                        System.out.printf("%-13s%-3s%s\n", "Customer ID", "-", cudIdArray[foundIndex]);
                                        System.out.printf("%-13s%-3s%s\n", "Name", "-", names[foundIndex]);
                                        System.out.println("\t(0) Preparing");
                                        System.out.println("\t(1) Delivered");
                                        System.out.println("\t(2) Cancel");

                                        System.out.print("Enter new order status - ");
                                        int status = input.nextInt();
                                        input.nextLine(); // Consume newline
                                        if (status == 0 || status == 1 || status == 2) {
                                            orderStatus[foundIndex] = status;
                                            System.out.println("\nUpdate order status successfully...\n");
                                            String statusStr = (status == 0) ? "PREPARING" : (status == 1) ? "DELIVERED" : "CANCEL";
                                            System.out.println("New order status - " + statusStr);
                                        } else {
                                            System.out.println("Invalid status. Please enter 0, 1, or 2.\n");
                                        }
                                        break;

                                    default:
                                        System.out.println("Invalid option! Please choose 1 or 2.\n");
                                        break;
                                }
                            } else if (orderStatus[foundIndex] == DELIVERED) {
                                System.out.println("This Order is already delivered...You can not update this order...");
                            } else {
                                System.out.println("This Order is already cancelled...You can not update this order...");
                            }
                        } else {
                            System.out.println("\nInvalid Order ID!");
                        }

                        System.out.print("\nDo you want to update another order details (Y/N): ");
                        char choice = input.nextLine().charAt(0);
                        if (choice == 'N' || choice == 'n') {
                            updateAgain = false;
                        }
                        clearConsole();
                    }
                    break;

                case 7:
                    clearConsole();
                    System.out.println("\n\t\tYou left the program...\n");
                    System.exit(0);
                    break;

                default:
                    clearConsole();
                    System.out.println("Invalid option\n");
            }
        }
    }

    // Console clear method
    public static void clearConsole() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }

    // Customer ID (Phone Number) Validation method
    public static String custIdValidation() {
        Scanner input = new Scanner(System.in);
        boolean validation;
        String phoneNumber;
        do {
            System.out.print("Enter Customer ID (phone no): ");
            phoneNumber = input.nextLine();
            validation = phoneNumber.length() == 10 && phoneNumber.startsWith("0");
            if (!validation) {
                System.out.println("Invalid Customer ID. Please enter a 10-digit number starting with '0'.\n");
            }
        } while (!validation);

        return phoneNumber;
    }
}