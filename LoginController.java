package application;
import javafx.scene.control.Label;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginController {
	@FXML
    private Label loginMessage; 
	
    @FXML
    private Label signupMessage;

    @FXML
    private PasswordField logPassword;

    @FXML
    private TextField logUsername;

    @FXML
    private Button loginButton;

    @FXML
    private TextField signAdress;

    @FXML
    private TextField signEmail;

    @FXML
    private TextField signFirstName;

    @FXML
    private TextField signLastName;

    @FXML
    private PasswordField signPassword;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField signUsername;
    
    private static String loggedInUsername;

    public static String getLoggedInUsername() {
        return loggedInUsername;
    }
    @FXML
    void handleLoginAction(ActionEvent event) throws SQLException {
    	DatabaseAdapter.OutOfStockDeletion();
    	String username = logUsername.getText();
        String password = logPassword.getText();
        if (DatabaseAdapter.validateUser(username, password)) {
        	loggedInUsername = username;
        	loginMessage.setVisible(false);
            newScene(username);
        } else {
        	loginMessage.setVisible(true);
            loginMessage.setText("Invalid Username or Password");
        }
    }

    @FXML
    void handleSignUpAction(ActionEvent event) {
        String username = signUsername.getText();
        String password = signPassword.getText();
        String firstName = signFirstName.getText();
        String lastName = signLastName.getText();
        String email = signEmail.getText();
        String address = signAdress.getText();
        
        if (DatabaseAdapter.usernameoremailCheck(username, email) || password.isEmpty() || username.isEmpty()) {
        	signupMessage.setVisible(true);
            signupMessage.setText("Username or Email Is Already In Use or Empty");
        } else {
            signupMessage.setVisible(false);
            if (DatabaseAdapter.addNewUser(username, password, firstName, lastName, email, address)) {
            } else {
                signupMessage.setText("Kayıt sırasında bir hata oluştu.");
            }
        }
    }

    private void newScene(String username) {
        String userRole = DatabaseAdapter.userRole(username);
        
        String fxmlFile = "";
        String windowTitle = "";

        if ("customer".equals(userRole)) {
            fxmlFile = "/application/customer.fxml";
            windowTitle = "customer";
        } else if ("carrier".equals(userRole)) {
            fxmlFile = "/application/carrier.fxml";
            windowTitle = "carrier";
        } else if ("owner".equals(userRole)) {
            fxmlFile = "/application/owner.fxml";
            windowTitle = "owner";
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) logUsername.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle(windowTitle);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
}
}


