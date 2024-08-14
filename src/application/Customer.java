package application;

public class Customer extends User {
    private ShoppingCart cart;

    // Constructor
    public Customer(int id, String name, String email, String address, String pass) {
        super(id, name, email, address,pass);
        cart = new ShoppingCart();
    }

    // Getters and Setters
   
    public ShoppingCart getCart() {
		return cart;
	}

	public void setCart(ShoppingCart cart) {
		this.cart = cart;
	}
    
    public void addToCart(Product product) {
        cart.addProduct(product);
    }


	public void removeFromCart(Product product) {
        cart.removeProduct(product);
    }
	
	public void emptyCart() {
		cart = new ShoppingCart();
	}
}

