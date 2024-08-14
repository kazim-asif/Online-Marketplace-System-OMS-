package application;

import java.util.*;

public class Marketplace {
    private List<Product> products;
    private List<Customer> customers;
    private List<Seller> sellers;
    private List<Transaction> transactions;

    private double MARKETPLACE_TRANSACTION_FEE = 1.5;
 // Constructor
    public Marketplace() {
        products = new ArrayList<>();
        customers = new ArrayList<>();
        sellers = new ArrayList<>();
        transactions = new ArrayList<>();
    }

    // Methods to manage products
    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public void updateProduct(Product product) {
        // TODO: Implement updateProduct method
    }

    public double calculateCartTotal(Customer customer) {
        double total = 0.0;
        for (Product product : customer.getCart().getProducts()) {
            total += product.getPrice();
        }
        return total;
    }

    // Methods to manage transactions
    public void processPurchase(Customer customer, Product product) throws InvalidProductException, OutOfStockException {
        // Check if the product is valid
    	if (!product.isValid()) {
    		throw new InvalidProductException("Product is not valid.");
    	}
    	if (product.getQuantityAvailable() < 1) {
    		throw new OutOfStockException("Product is out of stock.");
    	}
        // Update product quantity and customer shopping cart
        int newQuantity = product.getQuantityAvailable() - 1;
        product.setQuantityAvailable(newQuantity);
        customer.getCart().addProduct(product);
        
        // Update seller's list of products and revenue
        Seller seller = product.getSeller();
        seller.removeProduct(product);
        seller.addRevenue(product.getPrice());
        
        // Update marketplace revenue
        double transactionFee = product.getPrice() * MARKETPLACE_TRANSACTION_FEE;
        double marketplaceRevenue = transactionFee + (product.getPrice() - transactionFee);
        addRevenue(marketplaceRevenue);
        
        // Add transaction to list of transactions
        Transaction transaction = new Transaction(customer,customer.getCart().getProducts(), marketplaceRevenue);
        transactions.add(transaction);
        
        System.out.println("Purchase successful. Thank you for shopping at our marketplace!");
    }

    // Method to add revenue to the marketplace
    public void addRevenue(double revenue) {
        // TODO: Implement addRevenue method
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

    

    public static void main(String[] args) {
        List<Customer> customers = new ArrayList<Customer>();
        List<Seller> sellers = new ArrayList<Seller>();
        List<Product> products = new ArrayList<Product>();
        List<Transaction> transactions = new ArrayList<Transaction>();

        // Create some customers
        Customer customer1 = new Customer(1, "John", "john@gmail.com", "123 Main St","12345678");
        Customer customer2 = new Customer(2, "Jane", "jane@gmail.com", "456 Oak Ave","12345678");
        customers.add(customer1);
        customers.add(customer2);

        // Create some sellers
        Seller seller1 = new Seller(1, "Bob", "bob@gmail.com", "789 Elm St","12345678");
        Seller seller2 = new Seller(2, "Alice", "alice@gmail.com", "321 Pine Ave","12345678");
        sellers.add(seller1);
        sellers.add(seller2);

        // Create some products
        Product product1 = new Product(seller1, 1, "Product 1", "Description of product 1", "category1", 10.0, 20);
        Product product2 = new Product(seller2, 2, "Product 2", "Description of product 2", "category1", 15.0, 15);
        Product product3 = new Product(seller1, 3, "Product 3", "Description of product 3", "category2", 25.0, 10);
        products.add(product1);
        products.add(product2);
        products.add(product3);

        // Add products to sellers' product lists
        seller1.addProduct(product1);
        seller1.addProduct(product2);
        seller2.addProduct(product3);

        // Customer 1 adds product 1 to their cart
        customer1.addToCart(product1);

        // Customer 1 removes product 1 from their cart
        customer1.removeFromCart(product1);

        // Customer 1 adds product 2 to their cart
        customer1.addToCart(product2);

        // Customer 1 checks out their cart and creates a transaction
        ShoppingCart cart = customer1.getCart();
        double totalAmount = cart.getTotalCost();
        Transaction transaction1 = new Transaction(customer1, customer1.getCart().getProducts(), totalAmount);
        transactions.add(transaction1);

        // Seller 1 adds revenue for the sale
        seller1.addRevenue(totalAmount);

        // Print out transaction details
        System.out.println("Transaction Details:");
        System.out.println("Customer Name: " + transaction1.getCustomer().getName());
        System.out.println("Product Name: " + transaction1.getProducts());
        System.out.println("Amount: $" + transaction1.getAmount());
    }
    
    
    
}



