package application;

import java.util.List;

public interface IProductSearch {
    List<Product> searchByName(List<Product> products, String name);
    List<Product> searchByCategory(List<Product> products, String category);
    List<Product> searchByPriceRange(List<Product> products, double minPrice, double maxPrice);
}

