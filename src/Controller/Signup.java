package Controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Signup {

	private Marketplace controller=new Marketplace();
	public Marketplace getController() {
		return controller;
	}
	public void setController(Marketplace controller) {
		this.controller = controller;
	}
	
    @FXML
    private TextField address;

    @FXML
    private TextField email;

    @FXML
    private Button loginbtn;

    @FXML
    private TextField name;

    @FXML
    private TextField password;

    @FXML
    private Button signupbtn;
    
    @FXML
    private TextField customer;

    @FXML
    void handleLogin(ActionEvent event) throws IOException {
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
    void handleSignup(ActionEvent event) throws IOException {
    	
    	int id = Integer.parseInt(customer.getText());
    	
    	if(id>0 && id <3) {
	    	if(id==1) {
	    		controller.handleSignUp(name.getText(), email.getText(), address.getText(), password.getText(), true);
	    	}
	    	else if(id==2) {
	    		controller.handleSignUp(name.getText(), email.getText(), address.getText(), password.getText(), false);
	    	}
	    	
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

    }

}
