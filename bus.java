import java.util.*;

class cat {
    public static final int PREPARING = 0;
    public static final int DELIVERED = 1;
    public static final int CANCEL = 2;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Array Object
        CustomerDetails[] details = new CustomerDetails[1];
        int orderID = 0; // generate order_id by incrementing
        int turn = 0; // Index for storing orders

        while (true) {
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
                    boolean placeAnother = true;
                    while (placeAnother) {
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

                        String cusId = custIdValidation();

                        boolean isItExist = false;
                        String cusName = null;
                        for (int y = 0; y < turn; y++) {
                            if (details[y] != null && cusId.equalsIgnoreCase(details[y].getCudId())) {
                                isItExist = true;
                                cusName = details[y].getNames();
                                System.out.println("Customer Name: " + cusName);
                                break;
                            }
                        }

                        if (!isItExist) {
                            System.out.print("Enter Customer Name: ");
                            cusName = input.nextLine();
                        }

                        int quantity;
                        do {
                            System.out.print("Enter Burger Quantity - ");
                            quantity = input.nextInt();
                            input.nextLine();
                            if (quantity <= 0) {
                                System.out.println("Invalid quantity.\n");
                            }
                        } while (quantity <= 0);

                        double total = quantity * 500.0;
                        System.out.printf("Total value - %.2f\n", total);

                        System.out.print("\tAre you confirm order (Y/N) - ");
                        char confamation = input.nextLine().trim().charAt(0);
                        boolean isitok = confamation == 'y' || confamation == 'Y';

                        if (isitok) {
                            details[turn] = new CustomerDetails(fullOrderId, cusId, cusName, quantity, PREPARING);
                            System.out.println("\tYour order is entered into the system successfully...");
                            turn++;

                            if (turn >= details.length) {
                                CustomerDetails[] tempArray = new CustomerDetails[details.length + 1];
                                for (int x = 0; x < details.length; x++) {
                                    tempArray[x] = details[x];
                                }
                                details = tempArray;
                            }
                        } else {
                            placeAnother = false;
                        }

                        if (isitok) {
                            System.out.print("\nDo you want to place another order (Y/N): ");
                            char confamation2 = input.nextLine().trim().charAt(0);
                            if (confamation2 == 'N' || confamation2 == 'n') {
                                placeAnother = false;
                            }
                        }
                        clearConsole();
                    }
                    break;

                case 2:
                    clearConsole();
                    boolean ok = true;
                    while (ok) {
                        System.out.println("-------------------------------------------------------------------------------");
                        System.out.printf("%s%43s%35s\n", "|", "BEST CUSTOMER", "|");
                        System.out.println("-------------------------------------------------------------------------------");
                        System.out.println();

                        if (turn == 0) {
                            System.out.println("No customers found!");
                        } else {
                            FindBestCustomer[] uniqueDetails = new FindBestCustomer[1];
                            int uniqueCount = 0;

                            for (int i = 0; i < turn; i++) {
                                String currentCudId = details[i].getCudId();
                                boolean isDuplicate = false;

                                for (int j = 0; j < uniqueCount; j++) {
                                    if (uniqueDetails[j].getCudId().equalsIgnoreCase(currentCudId)) {
                                        isDuplicate = true;
                                        break;
                                    }
                                }

                                if (!isDuplicate) {
                                    if (uniqueCount == uniqueDetails.length) {
                                        FindBestCustomer[] tempUniqueDetails = new FindBestCustomer[uniqueDetails.length + 1];
                                        for (int v = 0; v < uniqueDetails.length; v++) {
                                            tempUniqueDetails[v] = uniqueDetails[v];
                                        }
                                        uniqueDetails = tempUniqueDetails;
                                    }
                                    uniqueDetails[uniqueCount] = new FindBestCustomer(currentCudId);
                                    uniqueCount++;
                                }
                            }

                            for (int f = 0; f < uniqueCount; f++) {
                                String cudId = uniqueDetails[f].getCudId();
                                String customerName = "";
                                for (int x = 0; x < turn; x++) {
                                    if (details[x].getCudId().equalsIgnoreCase(cudId)) {
                                        customerName = details[x].getNames();
                                        break;
                                    }
                                }
                                uniqueDetails[f].setName(customerName);

                                double tot = 0.0;
                                for (int x = 0; x < turn; x++) {
                                    if (details[x].getCudId().equalsIgnoreCase(cudId)) {
                                        tot += details[x].getQuantityOfBurger() * 500.0;
                                    }
                                }
                                uniqueDetails[f].setTotal(tot);
                            }

                            for (int i = 0; i < uniqueCount - 1; i++) {
                                for (int j = 0; j < uniqueCount - i - 1; j++) {
                                    if (uniqueDetails[j].getTotal() < uniqueDetails[j + 1].getTotal()) {
                                        FindBestCustomer temp = uniqueDetails[j];
                                        uniqueDetails[j] = uniqueDetails[j + 1];
                                        uniqueDetails[j + 1] = temp;
                                    }
                                }
                            }

                            System.out.println("--------------------------------------------------");
                            System.out.printf("%-15s%-20s%-10s\n", "CustomerID", "Name", "Total");
                            System.out.println("--------------------------------------------------");
                            for (int i = 0; i < uniqueCount; i++) {
                                System.out.printf("%-15s%-20s%.2f\n",
                                        uniqueDetails[i].getCudId(),
                                        uniqueDetails[i].getName(),
                                        uniqueDetails[i].getTotal());
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
                        String searchId = input.nextLine().trim().toUpperCase();

                        boolean found = false;
                        int foundIndex = -1;

                        for (int i = 0; i < turn; i++) {
                            if (searchId.equalsIgnoreCase(details[i].getOrderId())) {
                                found = true;
                                foundIndex = i;
                                break;
                            }
                        }

                        if (found) {
                            System.out.println("----------------------------------------------------------------------------------");
                            System.out.printf("%-15s%-15s%-20s%-10s%-12s%-15s\n", "OrderID", "CustomerID", "Name", "Quantity", "Value", "Status");
                            System.out.println("----------------------------------------------------------------------------------");

                            double totalValue = details[foundIndex].getQuantityOfBurger() * 500.0;
                            String status = "";

                            switch (details[foundIndex].getOrderStatus()) {
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

                            System.out.printf("%-15s%-15s%-20s%-10d%-12.2f%-15s\n",
                                    details[foundIndex].getOrderId(),
                                    details[foundIndex].getCudId(),
                                    details[foundIndex].getNames(),
                                    details[foundIndex].getQuantityOfBurger(),
                                    totalValue,
                                    status);
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
                        System.out.println("-------------------------------------------------------------------------------");
                        System.out.printf("%s%43s%35s\n", "|", "SEARCH CUSTOMER", "|");
                        System.out.println("-------------------------------------------------------------------------------");
                        System.out.println();

                        String searchCusId = custIdValidation();
                        String customerName = "";
                        boolean found = false;

                        for (int c = 0; c < turn; c++) {
                            if (searchCusId.equalsIgnoreCase(details[c].getCudId())) {
                                found = true;
                                customerName = details[c].getNames();
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

                            for (int i = 0; i < turn; i++) {
                                if (searchCusId.equalsIgnoreCase(details[i].getCudId())) {
                                    double totalValue = details[i].getQuantityOfBurger() * 500.00;
                                    System.out.printf("%-15s%-17d%-15.2f\n",
                                            details[i].getOrderId(),
                                            details[i].getQuantityOfBurger(),
                                            totalValue);
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

                        int targetStatus = -1;
                        String statusTitle = "";
                        switch (subOption) {
                            case 1:
                                targetStatus = DELIVERED;
                                statusTitle = "DELIVERED ORDER";
                                break;
                            case 2:
                                targetStatus = PREPARING;
                                statusTitle = "PREPARING ORDER";
                                break;
                            case 3:
                                targetStatus = CANCEL;
                                statusTitle = "CANCEL ORDER";
                                break;
                            default:
                                System.out.println("Invalid sub-option!");
                                continue;
                        }

                        System.out.println("-------------------------------------------------------------------------------");
                        System.out.printf("%s%43s%35s\n", "|", statusTitle, "|");
                        System.out.println("-------------------------------------------------------------------------------");
                        System.out.println("\n");

                        for (int x = 0; x < turn; x++) {
                            if (details[x].getOrderStatus() == targetStatus) {
                                found = true;
                                break;
                            }
                        }

                        if (found) {
                            System.out.println("-----------------------------------------------------------------------");
                            System.out.printf("%-10s%-12s%-10s%-12s%-12s\n", "OrderID", "CustomerID", "Name", "Quantity", "OrderValue");
                            System.out.println("-----------------------------------------------------------------------");

                            for (int i = 0; i < turn; i++) {
                                if (details[i].getOrderStatus() == targetStatus) {
                                    double totalValue = details[i].getQuantityOfBurger() * 500.0;
                                    System.out.printf("%-10s%-12s%-12s%-12d%-12.2f\n",
                                            details[i].getOrderId(),
                                            details[i].getCudId(),
                                            details[i].getNames(),
                                            details[i].getQuantityOfBurger(),
                                            totalValue);
                                }
                            }
                            System.out.println("-----------------------------------------------------------------------");
                        } else {
                            System.out.println("No " + statusTitle.toLowerCase() + " orders found!");
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
                            if (searchId.equalsIgnoreCase(details[i].getOrderId())) {
                                found = true;
                                foundIndex = i;
                                break;
                            }
                        }

                        if (found) {
                            if (details[foundIndex].getOrderStatus() == PREPARING) {
                                System.out.printf("%-13s%-3s%s\n", "Order ID", "-", details[foundIndex].getOrderId());
                                System.out.printf("%-13s%-3s%s\n", "Customer ID", "-", details[foundIndex].getCudId());
                                System.out.printf("%-13s%-3s%s\n", "Name", "-", details[foundIndex].getNames());
                                System.out.printf("%-13s%-3s%d\n", "Quantity", "-", details[foundIndex].getQuantityOfBurger());

                                double totalValue = details[foundIndex].getQuantityOfBurger() * 500.0;
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
                                        System.out.printf("%-13s%-3s%s\n", "Order ID", "-", details[foundIndex].getOrderId());
                                        System.out.printf("%-13s%-3s%s\n", "Customer ID", "-", details[foundIndex].getCudId());
                                        System.out.printf("%-13s%-3s%s\n", "Name", "-", details[foundIndex].getNames());
                                        System.out.println();

                                        System.out.print("Enter your quantity update value - ");
                                        int newQuantity = input.nextInt();
                                        input.nextLine();
                                        if (newQuantity > 0) {
                                            details[foundIndex].setQuantityOfBurger(newQuantity);
                                            System.out.println("\tUpdate order quantity successfully...\n");
                                            System.out.println("New order quantity - " + details[foundIndex].getQuantityOfBurger());
                                            totalValue = details[foundIndex].getQuantityOfBurger() * 500.0;
                                            System.out.printf("%s%.2f\n\n", "New order value - ", totalValue);
                                        } else {
                                            System.out.println("Invalid quantity. Please enter a positive integer.\n");
                                        }
                                        break;

                                    case 2:
                                        System.out.println("\nStatus Update");
                                        System.out.println("=============\n");
                                        System.out.printf("%-13s%-3s%s\n", "Order ID", "-", details[foundIndex].getOrderId());
                                        System.out.printf("%-13s%-3s%s\n", "Customer ID", "-", details[foundIndex].getCudId());
                                        System.out.printf("%-13s%-3s%s\n", "Name", "-", details[foundIndex].getNames());
                                        System.out.println("\t(0) Preparing");
                                        System.out.println("\t(1) Delivered");
                                        System.out.println("\t(2) Cancel");

                                        System.out.print("Enter new order status - ");
                                        int status = input.nextInt();
                                        input.nextLine();
                                        if (status == 0 || status == 1 || status == 2) {
                                            details[foundIndex].setOrderStatus(status);
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
                            } else if (details[foundIndex].getOrderStatus() == DELIVERED) {
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

/// main method is over from here


class CustomerDetails {
    private String orderId;
    private String cudId;
    private String names;
    private int quantityOfBurger;
    private int orderStatus;

    CustomerDetails(String pOrderId, String pCudId, String pNames, int pQuantityOfBurger, int pOrderStatus) {
        this.orderId = pOrderId;
        this.cudId = pCudId;
        this.names = pNames;
        this.quantityOfBurger = pQuantityOfBurger;
        this.orderStatus = pOrderStatus;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCudId() {
        return cudId;
    }

    public String getNames() {
        return names;
    }

    public int getQuantityOfBurger() {
        return quantityOfBurger;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    // for case 6
    public void setQuantityOfBurger(int quantity) {
        this.quantityOfBurger = quantity;
    }

    public void setOrderStatus(int status) {
        this.orderStatus = status;
    }
}

class FindBestCustomer {
    private String cudId;
    private String name;
    private double total;

    public FindBestCustomer(String pCudId) {
        this.cudId = pCudId;
        this.name = "";
        this.total = 0.0;
    }

    public String getCudId() {
        return cudId;
    }

    public String getName() {
        return name;
    }

    public double getTotal() {
        return total;
    }

    public void setName(String pName) {
        this.name = pName;
    }

    public void setTotal(double pTotal) {
        this.total = pTotal;
    }
}