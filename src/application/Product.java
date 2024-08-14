package application;

public class Product {
    private int productId;
    private String name;
    private String description,category;
    private double price;
    private int quantityAvailable;
    private Seller seller;
    
    // Constructor
    public Product(Seller seller, int productId, String name, String description, String category, double price, int quantityAvailable) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.category=category;
        this.price = price;
        this.quantityAvailable = quantityAvailable;
        this.seller=seller;
    }


    // Getters and Setters
    
    public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}
    
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantityAvailable() {
		return quantityAvailable;
	}

	public void setQuantityAvailable(int quantityAvailable) {
		this.quantityAvailable = quantityAvailable;
	}
	
	public boolean isValid() {
//	    return (this.quantityAvailable > 0) && (this.price > 0.0);
		return (this.price > 0.0);
	}


}

