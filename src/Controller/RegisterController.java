package Controller;

import Model.Exceptions.UserAlreadyExists;
import Model.MediaCenterMask;
import Model.Users.Admin;
import Model.Users.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RegisterController
{
    @FXML
    private TextField username_field;

    @FXML
    private TextField email_field;

    @FXML
    private TextField confirm_email_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private PasswordField confirm_password_field;

    @FXML
    private ToggleButton is_admin;

    @FXML
    private Button register;

    @FXML
    private Label error_message;

    private MediaCenterMask mc;


    public void setModel(MediaCenterMask model)
    {
        this.mc = model;
    }


    @FXML
    public void register_user()
    {
        // Restore error style color
        this.error_message.setStyle("");

        String username = this.username_field.getText();
        String email = this.email_field.getText();
        String confirm_email = this.confirm_email_field.getText();
        String password = this.password_field.getText();
        String confirm_password = this.confirm_password_field.getText();

        if(username.equals("") || email.equals("") ||
                confirm_email.equals("") || password.equals("") ||
                confirm_password.equals(""))
        {
            this.error_message.setText("Please fill all fields");
            return;
        }

        if(!email.equals(confirm_email))
        {
            this.error_message.setText("Email confirmation\n does not match");
            return;
        }

        if(!password.equals(confirm_password))
        {
            this.error_message.setText("Password confirmation\n does not match");
            return;
        }

        if(!User.isValidPassword(password))
        {
            this.error_message.setText("Password not valid:\nAt least one lowercase,\n uppercase and a digit");
            return;
        }

        if(!User.isValidEmail(email))
        {
            this.error_message.setText("Email not valid");
            return;
        }

        User uta; //User to add
        try
        {
            Admin logged = (Admin) this.mc.getLoggedInUser();
            uta = logged.register_user(email,username,password,this.is_admin.isSelected());
        }
        catch (Exception e)
        {
            this.error_message.setText("Unexpected behavior");
            return;
        }

        try
        {
            this.mc.addUser(uta);
            this.error_message.setStyle("-fx-text-fill: #75f079");
            this.error_message.setText("Successfully Created");
        }
        catch (UserAlreadyExists e)
        {
            this.error_message.setText("User already exists"/*e.getMessage()*/);
        }

    }
}
