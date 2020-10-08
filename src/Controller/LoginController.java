package Controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import Model.MediaCenterMask;
import java.io.IOException;

public final class LoginController extends UsableController
{
    @FXML
    private AnchorPane pane;

    @FXML
    private TextField email_textfield;

    @FXML
    private PasswordField password_textfield;

    @FXML
    private Button login_button;

    @FXML
    private ImageView cross;

    @FXML
    private ImageView minimize;

     @FXML
     private Label error_message;

    private double xOffset = 0;
    private double yOffset = 0;

    // ============== INITIALIZATION ============== //

    public static Scene init(MediaCenterMask mc) throws IOException
    {
        return UsableController.init(mc, "../resources/fxml/login.fxml");
    }

    protected void postInit()
    {
        this.pane.setOnKeyPressed(event ->
        {
            if(event.getCode() == KeyCode.ENTER)
            {
                this.loginEvent();
            }
        });


        this.pane.setOnMousePressed(event ->
        {
            this.xOffset = event.getSceneX();
            this.yOffset = event.getSceneY();
        });
        this.pane.setOnMouseDragged(event ->
        {
            Stage stage = (Stage) this.pane.getScene().getWindow();
            stage.setX(event.getScreenX() - this.xOffset);
            stage.setY(event.getScreenY() - this.yOffset);
        });
    }

    // ============== EVENTS ============== //

    @FXML
    public void loginEvent()
    {
        try
        {
            this.mediacenter.login(this.email_textfield.getText(), this.password_textfield.getText());

            Stage stage = (Stage) this.login_button.getScene().getWindow();
            stage.setScene(AppController.init(super.mediacenter));
        }
        catch (Exception e) {
            this.error_message.setText(e.getMessage());
        }
    }

    @FXML
    public void close(MouseEvent event)
    {
        System.exit(0);
    }

    @FXML
    public void minimize(MouseEvent event)
    {
        ((Stage) this.cross.getScene().getWindow()).setIconified(true);
    }
}