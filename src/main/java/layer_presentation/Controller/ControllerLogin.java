package layer_presentation.Controller;

import dto.AdminDTO;
import dto.UserDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import layer_business.Functions;
import layer_presentation.GUI;
import layer_presentation.util.AlertBox;

import java.io.IOException;

public class ControllerLogin {
	
	@FXML
	public Button loginButton;
	
	@FXML
	public TextField mail;
	
	@FXML
	public TextField password;

	public void login() throws IOException {
		Functions f = new Functions();

		if(mail.getText().equals("") || password.getText().equals(""))
			AlertBox.display("No input", "You forgot to write your mail/password");
		else {
			UserDTO user = f.userLoginPlayer(mail.getText(), password.getText());
			AdminDTO admin = f.adminLoginPlayer(mail.getText(), password.getText());

			if (user != null) {
				transferUser(user);
			}
			else{
				if(admin != null){
					Scene scene = GUI.changeScene("admin.fxml");
				}
			}
		}
    }

    //Method used to transfer the current logged in user to the ControllerUser class
    private void transferUser(UserDTO userDTO) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/user.fxml"));
		Parent root = loader.load();

		ControllerUser controllerUser = loader.getController();
		controllerUser.getUserFromControllerLogin(userDTO);

		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.show();
	}

}
