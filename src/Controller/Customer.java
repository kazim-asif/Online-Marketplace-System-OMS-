

package Controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import application.Product;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Customer {

	private Marketplace controller;
	
	public Marketplace getController() {
		return controller;
	}
	public void setController(Marketplace controller) {
		this.controller = controller;
	}
	
	
    @FXML
    private Button addbtn;

    @FXML
    private TextArea cartarea;

    @FXML
    private Button checkoutbtn;

    @FXML
    private DialogPane dialog;

    @FXML
    private Button logoutbtn;

    @FXML
    private TextField pid;
    
    @FXML
    private TextField pid1;

    @FXML
    private TextArea showproductsarea;
    
    @FXML
    private Button refreshbtn;

    @FXML
    private Button searchbtn;

    @FXML
    private TextField maxprice;

    @FXML
    private TextField name;
    
    @FXML
    private TextField category;
    
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

    void ViewCart(List<Product> products) {
    	cartarea.setText("\n------------------------\n");
    	for (Product product : products) {
    		cartarea.setText( cartarea.getText() + "Id : "+product.getProductId() +"\n"
    				+ "Name : "+product.getName()+"\n"
    				+ "Price : "+product.getPrice()+"------------------------\n");
		}
    }
    
    @FXML
    void handleRefresh(ActionEvent event) {
    	
    	ViewCart(controller.getCartProducts());
		ViewProducts(controller.getAllAvailableProducts());
    	
    }
    
    @FXML
    void handleAddToCart(ActionEvent event) {
    	int id = Integer.parseInt(pid.getText()) ;
    	if(id>-1) {
    		controller.handleAddToCart(id);
    		ViewCart(controller.getCartProducts());
    		ViewProducts(controller.getAllAvailableProducts());
    	}
    	
    }
    
    @FXML
    void handleRemoveFromCartCart(ActionEvent event) {

    	int id = Integer.parseInt(pid1.getText()) ;
    	if(id>-1) {
    		controller.handleRemoveFromCart(id);
    		ViewCart(controller.getCartProducts());
    		ViewProducts(controller.getAllAvailableProducts());
    	}
    	
    }
    
    

    @FXML
    void handleCheckOut(ActionEvent event) {
        double bill = controller.handlePurchase();
        if(bill>0) {
	        dialog.setContentText("Dear valuable customer, your total bill is " + bill);
	        dialog.setVisible(true);
	        
	        ViewCart(controller.getCartProducts());
			ViewProducts(controller.getAllAvailableProducts());
	        
	
	        // Create a PauseTransition to delay hiding the dialog box
	        PauseTransition delay = new PauseTransition(Duration.seconds(2.5));
	        delay.setOnFinished(e -> dialog.setVisible(false));
	        delay.play();
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
    void handlesearch(ActionEvent event) {

    	if(name.getText().isEmpty()) {
    		name.setText("");
    	}
    	if(category.getText().isEmpty()) {
    		category.setText("");
    	}
    	if(maxprice.getText().isEmpty()) {
    		maxprice.setText("0");
    	}
    	List<Product> filteredlist= controller.getFilteredProducts(name.getText(), category.getText(), Double.parseDouble(maxprice.getText()));
    	ViewProducts(filteredlist);
    }
    
}
