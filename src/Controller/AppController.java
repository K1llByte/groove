package Controller;

import Model.Media.Audio;
import Model.Users.Admin;
import Model.Users.User;
import Model.Utils.Utils;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import Model.MediaCenterMask;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;

public final class AppController extends UsableController
{
    @FXML
    private AnchorPane root;

    @FXML
    private AnchorPane menu;

    @FXML
    private AnchorPane selector;

    @FXML
    private AnchorPane player;

    @FXML
    private MediaView media_view;

    @FXML
    private StackPane media_view_background;

    @FXML
    private ImageView cross;

    @FXML
    private ImageView minimize;

    @FXML
    private FontAwesomeIconView play_button;

    @FXML
    private Slider slider;

    @FXML
    private Label time_now;

    @FXML
    private Label time_total;

    @FXML
    private ToggleButton option_menu;

    @FXML
    private ToggleButton add_user;

    @FXML
    private ToggleButton add_media;

    @FXML
    private ToggleButton log_in_out;

    private MediaPlayer media_player;

    // ======= Media Selector ======= //
    private ListView<Model.Media.Media> ms_list_view;

    private ObservableList<Model.Media.Media> ms_obs_list;
    // ======= Register User ======== //
    private AnchorPane ru_background;

    private RegisterController ru_controller;
    // ======= Add Media ======== //
    private AnchorPane am_background;

    private AddMediaController am_controller;
    // ============================== //

    private AppState app_state = AppState.MAIN_MENU;

    private enum AppState
    {
        MAIN_MENU,
        REGISTER_USER,
        ADD_MEDIA;
    }

    // ============== INITIALIZATION ============== //

    public static Scene init(MediaCenterMask mc) throws IOException
    {
        return UsableController.init(mc, "../resources/fxml/media_center.fxml");
    }


    protected void postInit()
    {
        // ----------------------------- Add Media Panel ----------------------------- //
        // ----------------- LOAD FXML PANEL ------------------ //
        try
        {
            FXMLLoader loader = new FXMLLoader(AppController.class.getResource("../resources/fxml/add_media_panel.fxml"));
            this.am_background = loader.load();
            this.am_controller = loader.getController();
            this.am_controller.setModel(super.mediacenter);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        // ----------------------------- Register User Panel ----------------------------- //
        // ----------------- LOAD FXML PANEL ------------------ //

        try
        {
            FXMLLoader loader = new FXMLLoader(AppController.class.getResource("../resources/fxml/add_user_panel.fxml"));
            this.ru_background = loader.load();
            ru_controller = loader.getController();
            ru_controller.setModel(super.mediacenter);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        // ----------------------------- Media Selector ----------------------------- //
        // ----------------- LOAD FXML PANEL ------------------ //
        try
        {
            this.ms_list_view = FXMLLoader.load(AppController.class.getResource("../resources/fxml/media_selector.fxml"));
        }
        catch (IOException e) {}
        // -------------- LOAD MEDIA SELECTOR CELLS -------------- //
        ms_obs_list = FXCollections.observableArrayList();
        this.ms_list_view.setCellFactory(listView -> new MediaListCell());
        // -------------- SET MEDIA SELECTOR EVENTS -------------- //
        this.ms_list_view.setOnMouseClicked(event ->
        {
            if(event.getButton() == MouseButton.PRIMARY)
            {
                if(event.getClickCount() == 2)
                {
                    Model.Media.Media tmp = this.ms_list_view.getSelectionModel().getSelectedItem();

                    if(tmp instanceof Audio)
                    {
                        this.media_view_background.setStyle("-fx-background-image: url(resources/images/default-audio-thumbnail.png);" +
                                "-fx-background-repeat: no-repeat;\n" +
                                "    -fx-background-size: 30%;\n" +
                                "    -fx-background-position: center;");
                    }
                    else
                        this.player.setStyle("");


                    if(this.media_player != null)
                        this.media_player.dispose();


                    //System.out.println("file://" + AppController.class.getResource("..").getPath() + tmp.getPath());
                    //System.out.println("file:///home/killbyte/Documents/MIEI/3_Grade/DSS/Groove/src/" + tmp.getPath());
                    this.media_player = new MediaPlayer(
                            new Media("file://" + AppController.class.getResource("..").getPath() + tmp.getPath()));
                    this.media_player.setOnReady(() ->
                    {
                        this.slider.setMax(this.media_player.getTotalDuration().toSeconds());
                        this.time_total.setText(Utils.millisToTime((long) this.media_player.getTotalDuration().toMillis()));
                        this.media_player.currentTimeProperty().addListener((observable, oldDuration, newDuration) ->
                        {
                            this.slider.setValue(newDuration.toSeconds());
                            this.time_now.setText(Utils.millisToTime((long) newDuration.toMillis()));
                        });

                        //Slider Move Event
                        this.slider.valueProperty().addListener((obs, previous, now) ->
                        {
                            if ((!slider.isValueChanging() && this.slider.isPressed())
                                    || now.doubleValue() == slider.getMax()
                                    || now.doubleValue() == slider.getMin())
                            {
                                this.media_player.seek(Duration.seconds(now.doubleValue()));
                            }
                        });

                        this.media_view.setMediaPlayer(media_player);
                        this.media_player.play();
                        this.play_button.setIcon(FontAwesomeIcon.PAUSE);
                    });

                    this.media_player.setOnEndOfMedia(() -> this.play_button.setIcon(FontAwesomeIcon.PLAY));
                }
            }
        });
        // ------------------------------------------------------- //
        this.selector.getChildren().add(this.ms_list_view);
        // ------------------------ Media View Fullscreen ------------------------ //
        /*this.root.getChildren().add();*/
        // ------------------------------------------------------- //
        this.ms_obs_list.addAll(this.mediacenter.getAllMedia()); // Set media observable list
        this.ms_list_view.setItems(ms_obs_list); // Add all media to list view

        toggle();
    }


    // ============== EVENTS ============== //

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


    @FXML
    public void play_and_pause(MouseEvent event)
    {
        if(this.media_player == null)
            return ;

        if(this.media_player.getStatus() == MediaPlayer.Status.PAUSED)
        {
            this.media_player.play();
            this.play_button.setIcon(FontAwesomeIcon.PAUSE);
        }
        else
        {
            this.media_player.pause();
            this.play_button.setIcon(FontAwesomeIcon.PLAY);
        }
    }

    @FXML
    public void main_menu(MouseEvent event)
    {
        if(this.app_state != AppState.MAIN_MENU)
        {
            this.selector.getChildren().remove(0);
            this.selector.getChildren().add(this.ms_list_view);

            this.app_state = AppState.MAIN_MENU;
            //toggle();
        }
        toggle();
    }

    @FXML
    public void load_register_user(MouseEvent event)
    {
        /*
        //Example code (delete later)
        FileChooser fileChooser = new FileChooser();
        File choosed = fileChooser.showOpenDialog(this.root.getScene().getWindow());
        System.out.println(choosed.getPath());*/

        if(!(this.mediacenter.getLoggedInUser() instanceof Admin))
            return;

        if(this.app_state != AppState.REGISTER_USER)
        {
            this.selector.getChildren().remove(0);
            this.selector.getChildren().add(this.ru_background);

            this.app_state = AppState.REGISTER_USER;
            //toggle();
        }
        toggle();
    }

    @FXML
    public void load_add_media()
    {
        if(this.mediacenter.getLoggedInUser() == null)
            return;

        if(this.app_state != AppState.ADD_MEDIA)
        {
            this.selector.getChildren().remove(0);
            this.selector.getChildren().add(this.am_background);

            this.app_state = AppState.ADD_MEDIA;
            //toggle();
        }
        toggle();
    }

    @FXML
    private void load_login()
    {
        // Dispose current media player activity (Independently of action success)
        if(this.media_player != null)
            this.media_player.dispose();

        if(this.mediacenter.getLoggedInUser() != null) //logout
        {
            this.log_in_out.setSelected(false);
            this.mediacenter.logout();

            main_menu(null);
        }
        else //login
        {
            try
            {
                Stage stage = (Stage) this.root.getScene().getWindow();
                stage.setScene(LoginController.init(super.mediacenter));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }


    private void toggle()
    {
        // Discolor Menu (Login Dependant) options if user not logged in
        User tmp = this.mediacenter.getLoggedInUser();
        if(!(tmp instanceof Admin))
        {
            final String disable_style = "-fx-opacity: 0.2;";
            this.add_user.setStyle(disable_style);
            if(tmp == null)
            {
                this.add_media.setStyle(disable_style);
            }
        }

        this.option_menu.setDisable(this.app_state == AppState.MAIN_MENU);
        this.add_user.setDisable(this.app_state == AppState.REGISTER_USER);
        this.add_media.setDisable(this.app_state == AppState.ADD_MEDIA);
        this.log_in_out.setSelected(tmp != null);
    }
}