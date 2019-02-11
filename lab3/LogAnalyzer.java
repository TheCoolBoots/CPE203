import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class LogAnalyzer {

	Scanner scan;
	HashMap<String, Customer> customers;
	//HashMap<String, String> products;
	boolean parsed;

	public LogAnalyzer() {

		this.customers = new HashMap<String, Customer>();
		parsed = false;
	}

	public void parseLogFile(String filepath) {
		File file = new File(filepath);
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (scan.hasNext()) {
			
			String line1 = scan.nextLine();
			String[] line = line1.split(" ");
			if(line[0].equals("START")){
				//System.out.println("start " + line[1] + line[2]);
				startSession(line[1], line[2]);
			} else if (line[0].equals("VIEW")) {
				//System.out.println("view " + line[1] + line[2] + line[3]);
				addViewedItem(line[1], line[2], line[3]);
			} else if (line[0].equals("BUY")) {
				//System.out.println("buy " + line[1] + line[2] + line[3]);
				addPurchasedItem(line[1], line[2], line[3]);
			}

		}
		parsed = true;
	}

	public String generateOutput() {
		if (parsed) {
			return generateOutput1() + "\n" + generateOutput2() + "\n" + generateOutput3();
		} else {
			return "Log File Has Not Been Parsed Yet!";
		}
	}

	public String generateOutput1() {
		int totalItemsViewed = 0;
		int totalSessions = 0;
		Set<String> keys = customers.keySet();

		for (String i : keys) {
			if (!customers.get(i).boughtSomething()) {
				
				totalSessions += customers.get(i).getNumSessionsWithoutPurchase();
				totalItemsViewed += customers.get(i).getNumItemsViewedFromLameSessions();
			}
		}
		
		if (totalSessions == 0) {
			return "Every session bought something"; 
		}
		return "Average views without purchase: " + String.valueOf((double) totalItemsViewed / (double) totalSessions) + "\n";
	}

	public String generateOutput2() {
		String output = "Price Difference for Purchased Product by Session\n";
		for (String i : customers.keySet()) {
			output += customers.get(i).generatePurchaseOutputString();
		}
		return output;
	}

	public String generateOutput3() {
		String output = "Number of Views for Purchased Product by Customer\n";
		for (String i : customers.keySet()) {
			output += customers.get(i).getOutput3String();
		}
		return output;
	}

	public void startSession(String sessionID, String customerID) {
		if (!customers.containsKey(customerID)) {
			customers.put(customerID, new Customer(customerID));
		}
		customers.get(customerID).startSession(sessionID);
	}

	public void addViewedItem(String sessionID, String productID, String priceCents) {
		customers.get(getCustomerIDWithSession(sessionID)).viewedItem(sessionID,
				new Product(Integer.parseInt(priceCents), productID));
	}

	public void addPurchasedItem(String sessionID, String productID, String priceCents) {
		customers.get(getCustomerIDWithSession(sessionID)).purchasedItem(sessionID,
				new Product(Integer.parseInt(priceCents), productID));
	}


	public String getCustomerIDWithSession(String sessionID) {
		for (String i : customers.keySet()) {
			if (customers.get(i).sessionExists(sessionID)) {
				return i;
			}
		}
		return null;
	}

	public static void main(String[] args) {
		LogAnalyzer analyze = new LogAnalyzer();
		analyze.parseLogFile("C:\\Users\\TheCoolBoots\\eclipse-workspace\\203lab3\\src\\small.log");
		System.out.println(analyze.generateOutput());
		System.out.println("I Made IT!");
	}
}
