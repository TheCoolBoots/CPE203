import java.util.ArrayList;
import java.util.HashMap;

public class Customer {

	private String customer;
	private HashMap<String, Session> sessions;
	private ArrayList<String> purchasedItems;

	public Customer(String customer) {

		this.customer = customer;
		this.sessions = new HashMap<String, Session>();
		this.purchasedItems = new ArrayList<String>();

	}

	public void startSession(String sessionID) {
		if (!sessionExists(sessionID)) {
			sessions.put(sessionID, new Session(sessionID));
		}
	}

	public void viewedItem(String sessionID, Product product) {
		if (sessionExists(sessionID)) {
			getSession(sessionID).addViewedProduct(product);
		}
	}

	public void purchasedItem(String sessionID, Product product) {
		if (sessionExists(sessionID)) {
			getSession(sessionID).addPurchsedProduct(product);
		}
		purchasedItems.add(product.getId());
	}

	public Session getSession(String sessionID) {
		return sessions.get(sessionID);
	}

	public boolean sessionExists(String sessionID) {
		return sessions.containsKey(sessionID);
	}

	public int getNumViewed() {
		int numViews = 0;

		for (String i : sessions.keySet()) {
			numViews += sessions.get(i).getNumViews();
		}

		return numViews;
	}

	public boolean boughtSomething() {
		boolean bought = false;

		for (String i : sessions.keySet()) {
			bought = bought && sessions.get(i).puchasedAProduct();
		}

		return bought;
	}

	public String getOutput3String() {
		String output = customer + "\n";
		for (String i : purchasedItems) {
			int numSessions = 0;
			for (String j : sessions.keySet()) {
				if (sessions.get(j).viewedProduct(i)) {
					numSessions += 1;
				}
			}
			output += "\t" + i + " " + String.valueOf(numSessions) + "\n";
		}
		return output;
	}

	public String getCustomer() {
		return customer;
	}

	public String generatePurchaseOutputString() {
		String output = "";

		for (String i : sessions.keySet()) {
			output += sessions.get(i).getOutputFormat();
		}

		return output;
	}

	public int getNumSessionsWithoutPurchase() {
		int numWithoutPurchase = 0;

		for (String i : sessions.keySet()) {
			if (!sessions.get(i).puchasedAProduct())
				numWithoutPurchase += 1;
		}

		return numWithoutPurchase;
	}

	public int getNumItemsViewedFromLameSessions() {
		int numViewedWithoutPurchase = 0;

		for (String i : sessions.keySet()) {
			if (!sessions.get(i).puchasedAProduct())
				numViewedWithoutPurchase += sessions.get(i).getNumViews();
		}

		return numViewedWithoutPurchase;
	}

}
