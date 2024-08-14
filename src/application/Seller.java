package application;

import java.util.*;

public class Seller extends User {
    private List<Product> products;

    private double revenue;
    
    // Constructor
    public Seller(int id, String name, String email, String address, String pass) {
        super(id, name, email, address,pass);
        products = new ArrayList<Product>();
        revenue=0;
    }

    // Getters and Setters
    
    
	public void addProduct(Product product) {
        products.add(product);
    }

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public double getRevenue() {
		return revenue;
	}

	public void setRevenue(double revenue) {
		this.revenue = revenue;
	}
	

	public void removeProduct(Product product) {
        products.remove(product);
    }
	
	public void addRevenue(double amount) {
	    this.revenue += amount;
	}

	
}

