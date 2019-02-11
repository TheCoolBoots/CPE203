
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;

public class Session {
	private String sessionID;
	private int numViews;
	private ArrayList<Product> viewedProducts;
	private ArrayList<Product> purchasedProducts;
	
	public Session(String sessionID) {
		
		this.numViews = 0;
		this.sessionID = sessionID;
		
		viewedProducts = new ArrayList<Product>();
		purchasedProducts = new ArrayList<Product>();
		
	}

	public void addViewedProduct(Product product) {
		numViews += 1;
		viewedProducts.add(product);
	}
	
	public void addPurchsedProduct(Product product) {
		purchasedProducts.add(product);
	}
	
	public boolean puchasedAProduct() {
		
		if (purchasedProducts.size() > 0) {
			return true;
		}
		return false;
		
	}
	
	public boolean viewedProduct(String productID) {
		for (Product i:viewedProducts) {
			if (i.getId().equals(productID))
				return true;
		}
		return false;
	}
	
	public String getSessionId() {
		return sessionID;
	}

	public int getNumViews() {
		return numViews;
	}
	
	public double getAvgViewPrice() {
		
		if (numViews == 0) {
			return 0;
		}
			
		double totalCents = 0.0;
		
		for (Product i:viewedProducts) {
			totalCents += i.getPrice();
		}
		
		return totalCents/(double)numViews;
	}
	
	public String getOutputFormat() {
		String output = "";
		if(purchasedProducts.size()>0) {
			output += sessionID+"\n";
			for(Product i:purchasedProducts) {
				output+= "\t"+i.getId()+" "+(i.getPrice()-getAvgViewPrice())+"\n";
			}
		}
		
		return output;
	}
	
	
}

