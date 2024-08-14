package application;

import java.util.List;

public class Transaction {
    private Customer customer;
    private List<Product> products;
    private double amount;

    public Transaction(Customer customer, List<Product> products, double amount) {
        this.customer = customer;
        this.products = products;
        this.amount = amount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public double getAmount() {
        return amount;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Transaction for Customer ").append(customer.getName()).append(" (ID: ").append(customer.getId()).append(")\n");
        sb.append("Amount: $").append(amount).append("\n");
        sb.append("Products:\n");
        for (Product product : products) {
            sb.append("\t- ").append(product.getName()).append(" (ID: ").append(product.getProductId()).append(")\n");
        }
        return sb.toString();
    }

    
}
