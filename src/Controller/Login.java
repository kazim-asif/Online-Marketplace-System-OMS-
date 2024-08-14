package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Login implements Initializable {

	private Marketplace controller=new Marketplace();
	
	public Marketplace getController() {
		return controller;
	}
	public void setController(Marketplace controller) {
		this.controller = controller;
	}
	
	
    @FXML
    private TextField email;

    @FXML
    private Button loginbtn;

    @FXML
    private TextField password;

    @FXML
    void handleLogin(ActionEvent event) throws IOException {

//    	String sellerpassword="seller";
//    	String selleremail="seller@gmail.com";
//    	String custpassword="cust";
//    	String custemail="cust@gmail.com";
    	
//    	controller.Initialize();
    	
    	
    	int login = controller.handleLogin(email.getText(),password.getText());
    	
    	if(login==1) {
    		
    		FXMLLoader loader = new FXMLLoader();
 			Stage currentStage=(Stage)((Node)event.getSource()).getScene().getWindow();
 			String fxmlDocPath = "src/GUI/CustomerMain.fxml";
 			FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
 			AnchorPane pane = (AnchorPane) loader.load(fxmlStream);
 			Customer obj= loader.getController();
 			obj.setController(controller);
 			Scene scene = new Scene(pane,710,510);
 			currentStage.setScene(scene);
 			currentStage.show();
 			
    	}
    	else if(login==0) {
    		
    		FXMLLoader loader = new FXMLLoader();
 			Stage currentStage=(Stage)((Node)event.getSource()).getScene().getWindow();
 			String fxmlDocPath = "src/GUI/SellerMain.fxml";
 			FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
 			AnchorPane pane = (AnchorPane) loader.load(fxmlStream);
 			Seller obj= loader.getController();
 			obj.setController(controller);
 			Scene scene = new Scene(pane,710,510);
 			currentStage.setScene(scene);
 			currentStage.show();
    		
    		
    	}
    	
    }

    @FXML
    void handleSignup(ActionEvent event) throws IOException {

    	FXMLLoader loader = new FXMLLoader();
		Stage currentStage=(Stage)((Node)event.getSource()).getScene().getWindow();
		String fxmlDocPath = "src/GUI/Signup.fxml";
		FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
		AnchorPane pane = (AnchorPane) loader.load(fxmlStream);
		Signup obj= loader.getController();
		obj.setController(controller);
		Scene scene = new Scene(pane,710,468);
		currentStage.setScene(scene);
		currentStage.show();
    	
    }
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // code to be executed when the FXML file is loaded
    	
    	try {
			controller.Initialize();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    		
    }

    @FXML
    public void EndSession() {
    	controller.WriteInfo();
    }
    
    
}