package application;

import java.util.*;

//ProductSearchEngine class implementing IProductSearch interface using polymorphism
public class ProductSearchEngine implements IProductSearch {
	//Search for products by name
	public List<Product> searchByName(List<Product> products, String name) {
	List<Product> result = new ArrayList<>();
	for (Product product : products) {
		if (product.getName().toLowerCase().contains(name.toLowerCase())) {
			result.add(product);
		}
	}
	return result;
	}
	
	// Search for products by category
	public List<Product> searchByCategory(List<Product> products, String category) {
	    List<Product> result = new ArrayList<>();
	    for (Product product : products) {
	        if (product.getCategory().toLowerCase().equals(category.toLowerCase())) {
	            result.add(product);
	        }
	    }
	    return result;
	}

	// Search for products within a price range
	public List<Product> searchByPriceRange(List<Product> products, double minPrice, double maxPrice) {
	    List<Product> result = new ArrayList<>();
	    for (Product product : products) {
	        if (product.getPrice() >= minPrice && product.getPrice() <= maxPrice) {
	            result.add(product);
	        }
	    }
	    return result;
	}

	
}