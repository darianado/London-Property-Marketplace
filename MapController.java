import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;

public class MapController implements Initializable 
{

    //ObservableList list = FXCollections.observableArrayList();
    //AirbnbDataLoader dataLoader = new AirbnbDataLoader();
    //ArrayList<AirbnbListing> airbnbArray = dataLoader.load();

    private HashMap<Button, String> boroughs = new HashMap<>();

    private ArrayList<AirbnbListing> airbnbArray ;

    @FXML private Button enfi;
    @FXML private Button barn;
    @FXML private Button hrgy;
    @FXML private Button walt;
    @FXML private Button hrrw;
    @FXML private Button bren;
    @FXML private Button camd;
    @FXML private Button isli;
    @FXML private Button hack;
    @FXML private Button redb;
    @FXML private Button have;
    @FXML private Button hill;
    @FXML private Button eali;
    @FXML private Button kens;
    @FXML private Button wstm;
    @FXML private Button towh;
    @FXML private Button newh;
    @FXML private Button bark;
    @FXML private Button houn;
    @FXML private Button hamm;
    @FXML private Button wand;
    @FXML private Button city;
    @FXML private Button gwch;
    @FXML private Button bexl;
    @FXML private Button rich;
    @FXML private Button mert;
    @FXML private Button lamb;
    @FXML private Button sthw;
    @FXML private Button lews;
    @FXML private Button king;
    @FXML private Button sutt;
    @FXML private Button croy;
    @FXML private Button brom;

    @FXML private ListController listController;
    private int from;
    private int to;

    public MapController(ArrayList<AirbnbListing> airbnbArray)
    {
        this.airbnbArray = airbnbArray ;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        init();
    }

    private void init()
    {
        boroughs.put(enfi, "Enfield");
        boroughs.put(king, "Kingston upon Thames");
        boroughs.put(croy, "Croydon");
        boroughs.put(brom, "Bromley");
        boroughs.put(houn, "Hounslow");
        boroughs.put(eali, "Ealing");
        boroughs.put(have, "Havering");
        boroughs.put(hill, "Hillingdon");
        boroughs.put(hrrw, "Harrow");
        boroughs.put(barn, "Barnet");
        boroughs.put(walt, "Waltham Forest");
        boroughs.put(redb, "Redbridge");
        boroughs.put(sutt, "Sutton");
        boroughs.put(lamb, "Lambeth");
        boroughs.put(sthw, "Southwark");
        boroughs.put(lews, "Lewisham");
        boroughs.put(brom, "Bromley");
        boroughs.put(gwch, "Greenwich");
        boroughs.put(bexl, "Bexley");
        boroughs.put(rich, "Richmond upon Thames");
        boroughs.put(mert, "Merton");
        boroughs.put(wand, "Wandsworth");
        boroughs.put(hamm, "Hammersmith and Fulham");
        boroughs.put(kens, "Kensington and Chelsea");
        boroughs.put(city, "City of London");
        boroughs.put(wstm, "Westminster");
        boroughs.put(camd, "Camden");
        boroughs.put(towh, "Tower Hamlets");
        boroughs.put(isli, "Islington");
        boroughs.put(hrgy, "Haringey");
        boroughs.put(newh, "Newham");
        boroughs.put(bark, "Barking and Dagenham");
        boroughs.put(hack, "Hackney");
        boroughs.put(bren, "Brent");
    }

    public void setFromTo(int from, int to)
    {
        this.from= from;
        this.to= to;

        mapVisualInication();
    }

    public int getNrPropIn(String borough)
    {
        ListController lc = new ListController(borough,airbnbArray,0,Integer.MAX_VALUE);
        
        return lc.getNrProp();
    }

    private void mapVisualInication()
    {

        
        boroughs.forEach((b,n)-> 
            {
                //b.getStylesheets().add("style.css");
                //b.getStyleClass().em
                
                b.getStyleClass().clear();
                
                ListController lc = new ListController(n,airbnbArray,from,to);
                if(lc.getNrProp()<10)
                    b.getStyleClass().addAll("red","center");
                else if(lc.getNrProp()<200)
                    b.getStyleClass().addAll("yellow","center");
                else b.getStyleClass().addAll("green","center");
            }
        );
    }

    @FXML
    private void buttonHandle(ActionEvent event) throws Exception
    {
        /**
         * When a button is pressed on the map scene it will check to see
         * if the ticker matches the string and then will be used as a 
         * parameter to the ListController class.
         */

        Button x = (Button) event.getSource();
        ListController listController = new ListController(boroughs.get(x),airbnbArray,from,to);

        /**
         * Loads the fxml lists for the map buttons and will open a new popup window
         */
        FXMLLoader fxmlLoaderList = new FXMLLoader(getClass().getResource("ListView.fxml"));
        fxmlLoaderList.setController(listController);
        Parent listView = fxmlLoaderList.load();

        Scene listViewScene = new Scene(listView, 700, 700);

        Stage stage = new Stage();
        stage.setTitle("List of properties");
        stage.setScene(listViewScene);
        stage.show();
    }
}