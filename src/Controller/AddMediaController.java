package Controller;

import Model.Media.Audio;
import Model.Media.Genre;
import Model.Media.Video;
import Model.MediaCenterMask;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

public class AddMediaController implements Initializable
{
    @FXML
    private TextField name_field;

    @FXML
    private HBox author_hbox;

    @FXML
    private TextField author_field;

    @FXML
    private Button add_media_button;

    @FXML
    private Label error_message;

    @FXML
    private ChoiceBox<Genre> genre_choicebox;

    @FXML
    private ToggleButton audio_toggle;

    @FXML
    private ToggleButton video_toggle;

    @FXML
    private Button file_chooser_button;

    @FXML
    private Label file_choosed_label;

    private MediaCenterMask mc;

    private File choosed_file;


    public void setModel(MediaCenterMask model)
    {
        this.mc = model;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        this.genre_choicebox.setValue(Genre.UNDIFINED);
        this.genre_choicebox.setItems(FXCollections.observableArrayList(Genre.values()));

        switch_add_audio();
    }

    @FXML
    public void switch_add_audio()
    {
        this.audio_toggle.setSelected(true);
        this.video_toggle.setSelected(false);

        this.author_hbox.setVisible(true);
        this.author_field.setVisible(true);
        this.genre_choicebox.setVisible(true);
    }

    @FXML
    public void choose_file()
    {
        FileChooser fileChooser = new FileChooser();
        this.choosed_file = fileChooser.showOpenDialog(this.file_chooser_button.getScene().getWindow());

        this.file_choosed_label.setText(choosed_file.getName());
    }

    @FXML
    public void switch_add_video()
    {
        this.video_toggle.setSelected(true);
        this.audio_toggle.setSelected(false);

        this.author_hbox.setVisible(false);
        this.author_field.setVisible(false);
        this.genre_choicebox.setVisible(false);
    }

    @FXML
    public void add_media()
    {
        if(this.audio_toggle.isSelected())
        {
            // Restore error style color
            this.error_message.setStyle("");

            String a_name = this.name_field.getText();
            String a_author = this.author_field.getText();
            Genre a_genre = this.genre_choicebox.getValue();

            if(a_name.equals("") || a_author.equals(""))
            {
                this.error_message.setText("Please fill all fields");
                return;
            }

            if(this.choosed_file == null)
            {
                this.error_message.setText("Please choose a file");
                return;
            }

            if(!is_valid_audio_format(this.choosed_file.getName()))
            {
                this.error_message.setText("Invalid audio format");
                return;
            }

            Audio audio = new Audio(a_name,a_author,a_genre,"resources/media/"+uri_safe_name(this.choosed_file.getName()));

            try
            {
                // Create new file
                File tmp = new File(AppController.class.getResource("../resources/media/").getPath()
                        + uri_safe_name(this.choosed_file.getName()));

                upload_file(tmp);

                this.mc.addMedia(audio);
            }
            catch(Exception e)
            {
                this.error_message.setText("Unable to upload file");
                return;
            }

            this.error_message.setText("Successfully added");
            this.error_message.setStyle("-fx-text-fill: #75f079");

        }
        else if(this.video_toggle.isSelected())
        {
            // Restore error style color
            this.error_message.setStyle("");

            String v_name = this.name_field.getText();
            String v_author = this.author_field.getText();
            Genre v_genre = this.genre_choicebox.getValue();

            if(v_name.equals(""))
            {
                this.error_message.setText("Please fill all fields");
                return;
            }

            if(this.choosed_file == null)
            {
                this.error_message.setText("Please choose a file");
                return;
            }

            if(!is_valid_video_format(this.choosed_file.getName()))
            {
                this.error_message.setText("Invalid video format");
                return;
            }

            Video video = new Video(v_name,"resources/media/"+uri_safe_name(this.choosed_file.getName()));

            try
            {
                // Create new file
                File tmp = new File(AppController.class.getResource("../resources/media/").getPath()
                        + uri_safe_name(this.choosed_file.getName()));

                upload_file(tmp);

                this.mc.addMedia(video);
            }
            catch(Exception e)
            {
                this.error_message.setText("Unable to upload file");
                return;
            }

            this.error_message.setText("Successfully added");
            this.error_message.setStyle("-fx-text-fill: #75f079");
        }
    }

    private void upload_file(File f) throws Exception
    {
        InputStream is = null;
        OutputStream os = null;
        if(!f.createNewFile())
            throw new Exception();

        is = new FileInputStream(this.choosed_file);
        os = new FileOutputStream(f);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = is.read(buffer)) > 0)
        {
            os.write(buffer, 0, length);
        }
        is.close();
        os.close();
    }

    private boolean is_valid_audio_format(String file_name)
    {
        // Num periodo de testes apenas permitiremos audio do tipo mp3
        String[] tmp = file_name.split("\\.");
        return tmp[tmp.length-1].equals("mp3");
    }

    private boolean is_valid_video_format(String file_name)
    {
        // Num periodo de testes apenas permitiremos videos do tipo mp4
        String[] tmp = file_name.split("\\.");
        return tmp[tmp.length-1].equals("mp4");
    }

    private String uri_safe_name(String name)
    {
        // Eventualmente havera mais criterios a serem cumpridos
        return name.replace(' ','_');
    }
}
