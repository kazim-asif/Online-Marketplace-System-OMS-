package Controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.View;

import application.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Seller {

private Marketplace controller;
	
	public Marketplace getController() {
		return controller;
	}
	public void setController(Marketplace controller) {
		this.controller = controller;
	}
	
	
    @FXML
    private TextField category;

    @FXML
    private Button deletebtn;

    @FXML
    private TextArea description;

    @FXML
    private Button logoutbtn;

    @FXML
    private TextField name;

    @FXML
    private TextField pid;

    @FXML
    private TextField price;

    @FXML
    private TextField quantity;
    
    @FXML
    private TextField updatepid;
    
    @FXML
    private Button refreshbtn;
    
    @FXML
    private Button gobackbtn;

    @FXML
    private Button addbtn;
    
    @FXML
    private Button updatebtn;
    
    @FXML
    private TextArea showproductsarea;

    @FXML
    void handleDelete(ActionEvent event) {

    	controller.handleRemoveProduct(Integer.parseInt(pid.getText()));
    	List<Product> products = new ArrayList<>();
    	products= controller.getSellerProducts();
    	ViewProducts(products);
    	pid.setText("");
    }
    
    @FXML
    void handleRefresh(ActionEvent event) {
    	List<Product> products = new ArrayList<>();
    	products= controller.getSellerProducts();
    	ViewProducts(products);
    }
    
    void ViewProducts(List<Product> products) {
    	showproductsarea.setText("\n------------------------\n");
    	for (Product product : products) {
    		showproductsarea.setText( showproductsarea.getText() + "Id : "+product.getProductId() +"\n"
    				+ "Name : "+product.getName()+"\n"
    				+ "Price : "+product.getPrice()+"\n"
    				+ "Quantity : "+product.getQuantityAvailable()+"\n"
    				+ "Category : "+product.getCategory()+"\n"
    				+ "Description : "+product.getDescription()+"\n------------------------\n"   );
		}
    }

    
    @FXML
    void handleLogout(ActionEvent event) throws IOException {

    	FXMLLoader loader = new FXMLLoader();
		Stage currentStage=(Stage)((Node)event.getSource()).getScene().getWindow();
		String fxmlDocPath = "src/GUI/Login.fxml";
		FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
		AnchorPane pane = (AnchorPane) loader.load(fxmlStream);
		Login obj= loader.getController();
		obj.setController(controller);
		Scene scene = new Scene(pane,710,468);
		currentStage.setScene(scene);
		currentStage.show();
    	
    }

    
    @FXML
    void handleAdd(ActionEvent event) {
    	controller.handleAddProduct(name.getText(), description.getText(), category.getText(), Integer.parseInt(price.getText()), Integer.parseInt(quantity.getText()));
    	List<Product> products = new ArrayList<>();
    	products= controller.getSellerProducts();
    	ViewProducts(products);
    	
    	name.setText("");quantity.setText("");category.setText("");
    	price.setText("");description.setText("");
    	
    	
    }
    
    @FXML
    void handleUpdate(ActionEvent event) {
    	
    	int id = Integer.parseInt(updatepid.getText());
    	int quan = Integer.parseInt(quantity.getText()) ;
    	String p = price.getText();
    	double price = Double.parseDouble(p);
    	if(id>=0) {
    		controller.handleUpdateProduct(id, description.getText(), category.getText(), price , quan );
    	}
    	
    } 
    
    @FXML
    void toUpdatePage(ActionEvent event) throws IOException {

    	name.setText("");description.setText("");
    	quantity.setText("");category.setText("");price.setText("");
    	
    	FXMLLoader loader = new FXMLLoader();
		Stage currentStage=(Stage)((Node)event.getSource()).getScene().getWindow();
		String fxmlDocPath = "src/GUI/UpdateProduct.fxml";
		FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
		AnchorPane pane = (AnchorPane) loader.load(fxmlStream);
		Seller obj= loader.getController();
		obj.setController(controller);
		Scene scene = new Scene(pane,710,468);
		currentStage.setScene(scene);
		currentStage.show();
    	
    }
    
    @FXML
    void handleGoBack(ActionEvent event) throws IOException {

    	FXMLLoader loader = new FXMLLoader();
		Stage currentStage=(Stage)((Node)event.getSource()).getScene().getWindow();
		String fxmlDocPath = "src/GUI/SellerMain.fxml";
		FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
		AnchorPane pane = (AnchorPane) loader.load(fxmlStream);
		Seller obj= loader.getController();
		obj.setController(controller);
		Scene scene = new Scene(pane,710,468);
		currentStage.setScene(scene);
		currentStage.show();
    	
    }
    
}