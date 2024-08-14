package Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import application.Customer;
import application.Product;
import application.Seller;
import application.Transaction;

public class FileHandling {
	
	// Reading customer info
	
    public void ReadCustomer(List<Customer> customerList) {
        
    	File file = new File("src/customers.txt");
        
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Customer customer = createCustomerFromLine(line);
                customerList.add(customer);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Customer createCustomerFromLine(String line) {
        // parse the line and create a Customer object
        int id = Integer.parseInt(line.split(",")[0]);
        String name = line.split(",")[1];
        String email = line.split(",")[2];
        String address = line.split(",")[3];
        String pass = line.split(",")[4];
        return new Customer(id, name, email, address, pass);
    }
    
    // Writing custoer info
    public void writeCustomersToFile(List<Customer> customers) {
        try (PrintWriter writer = new PrintWriter("src/customers.txt")) {
            for (Customer customer : customers) {
                writer.println(customerToLine(customer));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String customerToLine(Customer customer) {
        StringBuilder sb = new StringBuilder();
        sb.append(customer.getId()).append(",");
        sb.append(customer.getName()).append(",");
        sb.append(customer.getEmail()).append(",");
        sb.append(customer.getAddress()).append(",");
        sb.append(customer.getPassword());
        return sb.toString();
    }
    
    
    // write seller info
    
    public void writeSellersToFile(List<Seller> sellers) {
        try (PrintWriter writer = new PrintWriter("src/sellers.txt")) {
            for (Seller seller : sellers) {
                writer.println(sellerToLine(seller));
                for (Product product : seller.getProducts()) {
                    writer.println(ProductToLine(product));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String sellerToLine(Seller seller) {
        StringBuilder sb = new StringBuilder();
        sb.append(seller.getId()).append(",");
        sb.append(seller.getName()).append(",");
        sb.append(seller.getEmail()).append(",");
        sb.append(seller.getAddress()).append(",");
        sb.append(seller.getPassword()).append(",");
        sb.append(seller.getRevenue());
        return sb.toString();
    }

    private String ProductToLine(Product product) {
        StringBuilder sb = new StringBuilder();
        sb.append(product.getProductId()).append(",");
        sb.append(product.getName()).append(",");
        sb.append(product.getDescription()).append(",");
        sb.append(product.getCategory()).append(",");
        sb.append(product.getPrice()).append(",");
        sb.append(product.getQuantityAvailable()).append(",-1");
        return sb.toString();
    }

    // read seller info
    
    public void readSellersFromFile(List<Seller> sellers , List<Product> products) throws IOException {
        
        String filename="src/sellers.txt";
        
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            Seller currentSeller = null;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 6) {
                    // This is a seller line
                    if (currentSeller != null) {
                        sellers.add(currentSeller);
                    }
                    int id = Integer.parseInt(fields[0]);
                    String name = fields[1];
                    String email = fields[2];
                    String address = fields[3];
                    String pass = fields[4];
                    double revenue = Double.parseDouble(fields[5]);
                    currentSeller = new Seller(id, name, email, address, pass);
                    currentSeller.setRevenue(revenue);
                } else if (fields.length == 7) {
                    // This is a product line
                    int id = Integer.parseInt(fields[0]);
                    String name = fields[1];
                    String description = fields[2];
                    String cat = fields[3];
                    double price = Double.parseDouble(fields[4]);
                    int quan = Integer.parseInt(fields[5]);
                    Product product = new Product(currentSeller,id,name, description,cat, price,quan);
                    currentSeller.addProduct(product);
                    products.add(product);
                }
            }
            // Add the last seller in the file
            if (currentSeller != null) {
                sellers.add(currentSeller);
            }
        }
        
    }

    // write transaction info
    
    public void WriteTransaction(List<Transaction> ts) {
    	try (PrintWriter writer = new PrintWriter("src/transactions.txt")) {
            for (Transaction t : ts) {
                writer.println(TransactionToLine(writer,t));
                
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

	private String TransactionToLine(PrintWriter writer, Transaction t) {
		StringBuilder sb = new StringBuilder();
        sb.append(t.getCustomer().getId()).append(",");
        sb.append(t.getAmount()).append(",");
        for (Product p : t.getProducts()) {
        	sb.append(p.getProductId()).append(",");
		}
        return sb.toString();
	}
    
	//Read Transaction info
	
    public void ReadTransaction(List<Transaction> ts, List<Product> products, List<Customer> custs) {
        
    	File file = new File("src/transactions.txt");
        
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Transaction t = createTransactionFromLine(line, products, custs);
                ts.add(t);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

	private Transaction createTransactionFromLine(String line, List<Product> products, List<Customer> custs) {
		// TODO Auto-generated method stub
		
		int id = Integer.parseInt(line.split(",")[0]);
        double amount = Double.parseDouble(line.split(",")[1]);
        
        Customer cust = null;
        for (Customer c : custs) {
			if(c.getId()==id) {
				cust=c;
			}
		}
        
        List<Product> ps = new ArrayList<Product>();
        
        
        
        for(int i=2 ; i<line.split(",").length ; i++) {
        	for (Product p : products) {
				if(p.getProductId()==Integer.parseInt(line.split(",")[i])) {
					ps.add(p);
				}
			}
        }
        
        return new Transaction(cust, ps,amount);
		
	}
	
	
	

    
    // write product info 
    
//    public void writeProductsToFile(List<Product> products) {
//        try (PrintWriter writer = new PrintWriter("C:\\Users\\Taha Asif\\Desktop\\Waqas\\OMS\\src\\products.txt")) {
//            for (Product p : products) {
//                writer.println(sellerToLine(p.getSeller()));
//                writer.println(ProductToLine(p));
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

    //read product info
    
//    public void readProductsFromFile(List<Product> products) throws IOException {
//        
//        String filename="C:\\Users\\Taha Asif\\Desktop\\Waqas\\OMS\\src\\products.txt";
//        
//		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
//            String line;
//            Seller currentSeller = null;
//            while ((line = br.readLine()) != null) {
//                String[] fields = line.split(",");
//                if (fields.length == 6) {
//                    // This is a seller line
//                    if (currentSeller != null) {
//                        sellers.add(currentSeller);
//                    }
//                    int id = Integer.parseInt(fields[0]);
//                    String name = fields[1];
//                    String email = fields[2];
//                    String address = fields[3];
//                    String pass = fields[4];
//                    double revenue = Double.parseDouble(fields[5]);
//                    currentSeller = new Seller(id, name, email, address, pass);
//                    currentSeller.setRevenue(revenue);
//                } else if (fields.length == 7) {
//                    // This is a product line
//                    int id = Integer.parseInt(fields[0]);
//                    String name = fields[1];
//                    String description = fields[2];
//                    String cat = fields[3];
//                    double price = Double.parseDouble(fields[4]);
//                    int quan = Integer.parseInt(fields[5]);
//                    Product product = new Product(currentSeller,id,name, description,cat, price,quan);
//                    currentSeller.addProduct(product);
//                }
//            }
//            // Add the last seller in the file
//            if (currentSeller != null) {
//                sellers.add(currentSeller);
//            }
//        }
//        
//    }

    
}
