package Controller;

import Model.Media.Audio;
import Model.Media.Media;
import Model.Media.Video;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;

public class MediaListCell extends ListCell<Media>
{
    @FXML
    private AnchorPane background;

    @FXML
    private HBox hbox;

    @FXML
    private ImageView thumbnail;

    @FXML
    private Label name;

    @FXML
    private Label author;

    public MediaListCell()
    {
        //FXMLLoader a = new FXMLLoader(Controller.MediaListCell.class.getResource("resources/fxml/media_cell.fxml"));

    }

    @Override
    protected void updateItem(Media item, boolean empty)
    {
        super.updateItem(item, empty);
        if (empty || item == null)
        {
            //remove all the views
            setText(null);
            setGraphic(background);
        }
        else
        {
            FXMLLoader a = new FXMLLoader(MediaListCell.class.getResource("../resources/fxml/media_cell.fxml"));
            a.setController(this);

            try
            {
                a.load();
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            if(item instanceof Audio)
            {
                Audio tmp = (Audio) item;
                this.name.setText(tmp.getName());
                this.author.setText(tmp.getAuthor());
                this.thumbnail.setImage(new Image("resources/images/default-audio-thumbnail.png"));
            }
            else if(item instanceof Video)
            {
                Video tmp = (Video) item;
                this.name.setText(tmp.getName());
                this.author.setText("");
                this.thumbnail.setImage(new Image("resources/images/default-video-thumbnail.png"));
            }

            setText(null);
            setGraphic(background);
        }
    }
}
