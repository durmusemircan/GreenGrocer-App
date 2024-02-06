package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class userDetailsController {	

    @FXML
    private Button deleteAccountButton;

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
    private TextField signUsername;

    @FXML
    private Button updateButton;
    
    private User currentUser;
    
    public void initialize() {
        String currentUsername = LoginController.getLoggedInUsername();
        currentUser = DatabaseAdapter.getUserDetails(currentUsername);

        if (currentUser != null) {
            signUsername.setText(currentUser.getUsername());
            signPassword.setText(currentUser.getPassword());
            signFirstName.setText(currentUser.getFirstName());
            signLastName.setText(currentUser.getLastName());
            signAdress.setText(currentUser.getAddress());
            signEmail.setText(currentUser.getEmail());

        } else {
        }
    }

    @FXML
    void deleteAccount(ActionEvent event) {
    	DatabaseAdapter.deleteUser(currentUser.getId());
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) deleteAccountButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("login");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void updateAccount(ActionEvent event) {
    	DatabaseAdapter.updateUser(
                currentUser.getId(), 
                signUsername.getText(), 
                signPassword.getText(), 
                signFirstName.getText(), 
                signLastName.getText(), 
                signAdress.getText(), 
                signEmail.getText());
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) updateButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("login");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

}
