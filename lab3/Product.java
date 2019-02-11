
public class Product {
	
	private int priceCents;
	private String id;
	public Product(int priceCents, String id) {
		this.priceCents = priceCents;
		this.id = id;
	}
	public int getPrice() {
		return priceCents;
	}
	public String getId() {
		return id;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other.getClass() == this.getClass())
			return (priceCents == ((Product) other).getPrice())&&(id.equals(((Product) other).getId()));
		return false;
	}
	
}
