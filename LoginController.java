import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import java.util.ArrayList;

/**
 * Write a description of class LoginController here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class LoginController implements Initializable
{
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button loginButton;
    @FXML
    private Label errorLabel;

    private UsersLoader users = new UsersLoader();
    private ArrayList<AirbnbListing> airbnbArray;

    public LoginController(ArrayList<AirbnbListing> airbnbArray)
    {
        this.airbnbArray= airbnbArray;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        errorLabel.getStyleClass().add("error");
        errorLabel.setVisible(false);
    }

    @FXML
    private void buttonHandle(ActionEvent event) throws Exception
    {
        if(users.verify(username.getText(),password.getText()))
        {
            FavouritesDataBase favdb = new FavouritesDataBase();
            ListController listController = 
                new ListController(airbnbArray, favdb.getFavouritesOf(username.getText()));
           
            
            FXMLLoader fxmlLoaderList = new FXMLLoader(getClass().getResource("ListView.fxml"));
            fxmlLoaderList.setController(listController);
            Parent listView = fxmlLoaderList.load();
            listController.noComboBox();
            
            listController.setTitle("Your favs dear " + username.getText()+"!");

            Scene listViewScene = new Scene(listView, 700, 700);

            Stage stage = new Stage();
            stage.setTitle("List of properties");
            stage.setScene(listViewScene);
            stage.show();
        } 
        else errorLabel.setVisible(true);
        
        username.clear();
        password.clear();
    }

}
