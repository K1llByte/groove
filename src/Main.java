import Controller.AppController;
import Controller.LoginController;
import Model.Media.Audio;
import Model.Media.Genre;
import Model.Media.Media;
import Model.Media.Video;
import Model.MediaCenterMask;
import Model.RemoteMediaCenter;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import Model.MediaCenter;

import java.util.Collection;

public class Main extends Application
{
    private double xOffset = 0;
    private double yOffset = 0;


    @Override
    public void start(Stage stage) throws Exception
    {
        MediaCenterMask mc = new RemoteMediaCenter();

        Scene root = AppController.init(mc);
        //initMainStageEvents(root,stage);

        stage.setTitle("Groove");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(root);
        stage.show();
    }


    private void initMainStageEvents(Scene root, Stage stage)
    {
        root.setOnMousePressed(event ->
        {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event ->
        {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }


    public static void main(String[] args) throws Exception
    {
        launch(args);
    }
}
