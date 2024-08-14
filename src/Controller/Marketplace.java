package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import application.Customer;
import application.Product;
import application.Seller;
import application.Transaction;

public class Marketplace {
	
//	String sellerpassword="seller";
//	String selleremail="seller@gmail.com";
//	String custpassword="cust";
//	String custemail="cust@gmail.com";
	
//	private Customer currentCustomer = new Customer(1, "John", "john@gmail.com", "123 Main St","12345678");
//	private Seller currentSeller = new Seller(1, "Bob", "bob@gmail.com", "789 Elm St","12345678");
	
	private Customer currentCustomer = null;
	private Seller currentSeller = null;
	
	private FileHandling fh = new FileHandling();
	
	private final double MARKETPLACE_TRANSACTION_FEE = 1.5;
	
	private double marketplaceRevenue =0;
	
	private List<Customer> customers = new ArrayList<Customer>();
	private List<Seller> sellers = new ArrayList<Seller>();
	private List<Product> products = new ArrayList<Product>();
	private List<Transaction> transactions = new ArrayList<Transaction>();
	
	
	// Getters and Setters
    public List<Product> getProducts() {
        return products;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public List<Seller> getSellers() {
        return sellers;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
	
    
	public void Initialize() throws IOException {
		fh.ReadCustomer(customers);
		fh.readSellersFromFile(sellers, products );
		fh.ReadTransaction(transactions, products, customers);
		
		
	}
	
	// Methods to manage customers
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void removeCustomer(Customer customer) {
        customers.remove(customer);
    }
    
    // Methods to manage sellers
    public void addSeller(Seller seller) {
        sellers.add(seller);
    }

    public void removeSeller(Seller seller) {
        sellers.remove(seller);
    }

    // handle login of seller and customer
    
	public int handleLogin(String email, String pass) {
		
//		if(email.equals(currentCustomer.getEmail())&&pass.equals(currentCustomer.getPassword())) {
//			return 1;
//		}
//		else if(email.equals(currentSeller.getEmail())&&pass.equals(currentSeller.getPassword())) {
//			return 0;
//		}
		
		
		for (Customer customer : customers) {
			if(customer.getEmail().equals(email) && customer.getPassword().equals(pass)) {
				currentCustomer = customer;
				return 1;
			}
		}
		
		for (Seller seller : sellers) {
			if(seller.getEmail().equals(email) && seller.getPassword().equals(pass)) {
				currentSeller = seller;
				return 0;
			}
		}
		
		return -1;
	}
	
	public void handleSignUp(String name,String email,String address, String pass,  boolean isCustomer) {
		
		if(isCustomer) {
			int id = 0;
	    	if(customers.size()>0) {
	    		id = customers.get(customers.size()-1).getId()+1;
	    	}
			Customer newcust = new Customer(id,name,email,address,pass);
			addCustomer(newcust);
		}
		else {
			int id = 0;
	    	if(sellers.size()>0) {
	    		id = sellers.get(sellers.size()-1).getId()+1;
	    	}
			Seller newseller = new Seller(id,name,email,address,pass);
			addSeller(newseller);
		}
		
	}

    public void handleAddProduct(String name, String description, String category, double price, int quantity) {
    	int id = 0;
    	if(products.size()>0) {
    		id = products.get(products.size()-1).getProductId()+1;
    	}
    	
    	Product newproduct = new Product(currentSeller, id, name, description, category, price, quantity);
    	products.add(newproduct);
    	currentSeller.addProduct(newproduct);
    }
    
    public void handleRemoveProduct( int id) {
    	Product prodtoremove=null;
    	for (Product p : products) {
			if(p.getProductId()==id) {
				prodtoremove = p;
				break;
			}
		}
    	if(prodtoremove!=null) {
	    	products.remove(prodtoremove);
			currentSeller.removeProduct(prodtoremove);
    	}
    }

    public List<Product> getSellerProducts() {
    	return currentSeller.getProducts();
    }
    
    public List<Product> getCartProducts() {
    	return currentCustomer.getCart().getProducts();
    }
    
    public List<Product> getAllAvailableProducts() {
    	List<Product> newlist = new ArrayList<>();
    	for(Product p: products) {
    		if(p.getQuantityAvailable()>0) {
    			newlist.add(p);
    		}
    	}
    	return newlist;
    }
	
    public boolean handleAddToCart(int pid) {
    	
    	for(Product p: products) {
    		if(p.getProductId()==pid) {
    			currentCustomer.addToCart(p);
    			return true;
    		}
    	}
    	
    	return false;
    }

    public boolean handleRemoveFromCart(int pid) {
    	
    	for(Product p: products) {
    		if(p.getProductId()==pid) {
    			currentCustomer.removeFromCart(p);
    			return true;
    		}
    	}
    	
    	return false;
    }

    public double calculateCartTotal() {
        double total = 0.0;
        for (Product product : currentCustomer.getCart().getProducts()) {
        	if(product.getQuantityAvailable()>1) {
	            total += product.getPrice();
        	}
        }
        return total;
    }
    
    public void updateInventory() {
    	for(Product p: currentCustomer.getCart().getProducts()) {
    		
    		List<Product> sellerproducts = p.getSeller().getProducts();
    		
    		for (Product sellerprod : sellerproducts) {
				if(p==sellerprod && p.getQuantityAvailable()>0) {
					sellerprod.setQuantityAvailable(sellerprod.getQuantityAvailable()-1);
					p.getSeller().addRevenue(p.getPrice());
					for (Seller s : sellers) {
						if(s.getId()==p.getSeller().getId()) {
							s.setRevenue(p.getSeller().getRevenue());
						}
					}
					break;
				}
			}
//    		
    	}
    }
    
    public double handlePurchase() {
    	
    	double bill = calculateCartTotal();
    	if(bill==0.0) {
    		return 0;
    	}
    	bill += MARKETPLACE_TRANSACTION_FEE;
    	
    	updateInventory();
    	
    	marketplaceRevenue+=1.5;
        
    	// Add transaction to list of transactions
        Transaction transaction = new Transaction(currentCustomer, currentCustomer.getCart().getProducts(), bill);
        transactions.add(transaction);
        
        currentCustomer.emptyCart();
        
    	return bill;
    }
    
    
    public List<Product> getFilteredProducts(String name, String category, double maxprice) {
        List<Product> newlist = new ArrayList<>();
        for (Product p : products) {
            if (p.getQuantityAvailable() > 0) {
                if ((name == null || name.isEmpty() || p.getName().toLowerCase().contains(name.toLowerCase()))
                        && (category == null || category.isEmpty() || p.getCategory().toLowerCase().equals(category.toLowerCase()))
                        && (maxprice <= 0 || p.getPrice() <= maxprice)) {
                    newlist.add(p);
                }
            }
        }
        return newlist;
    }

    
    public void handleUpdateProduct(int id, String description, String category, double price, int quantity) {
    	
    	if(products.size()>0) {
    		
    		for (Product p : products) {
    			
    			if(p.getProductId()==id) {
    				p.setDescription(description);
    				p.setCategory(category);
    				p.setPrice(price);
    				p.setQuantityAvailable(quantity);
    			}
				
			}
    		
    		for (Product p : currentSeller.getProducts()) {
    			
    			if(p.getProductId()==id) {
    				p.setDescription(description);
    				p.setCategory(category);
    				p.setPrice(price);
    				p.setQuantityAvailable(quantity);
    			}
				
			}
    	}
    }

    public void WriteInfo() {
		
    	fh.writeCustomersToFile(customers);
    	fh.writeSellersToFile(sellers);
		fh.WriteTransaction(transactions);
	}
    
}
