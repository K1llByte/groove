package Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import Model.MediaCenterMask;

import java.io.IOException;

public abstract class UsableController
{
    protected MediaCenterMask mediacenter;


    private void initModel(MediaCenterMask mc)
    {
        this.mediacenter = mc;
    }

    protected abstract void postInit();


    protected final static Scene init(MediaCenterMask mc, String path) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(UsableController.class.getResource(path));
        Parent papi = loader.load();
        UsableController usableController = loader.getController();
        usableController.initModel(mc);
        usableController.postInit();
        return new Scene(papi);
    }

    //public abstract Scene init(MediaCenter mc) throws IOException;
}
