import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import java.util.ArrayList;
import javafx.scene.control.Label;
import java.util.HashSet;
import javafx.scene.control.ComboBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.List;

public class ListController implements Initializable
{

    ObservableList list = FXCollections.observableArrayList();
    ArrayList<AirbnbListing> airbnbArray;

    
    private int total;
    private String option;
    private String borough;
    private int from;
    private int to;
    
    @FXML private ComboBox sortBox;

    @FXML private ListView<String> hostNames = new ListView<>();
    
    private List<AirbnbListing> sortedList;
    
    @FXML private Label title = new Label();
    @FXML private ListController SortedListController;
    /**
     * Takes a String borough, this will be used to decide which data is selected for the lists
     */
    public ListController(String borough, ArrayList<AirbnbListing> airbnbArray, int from, int to)
    {
       // this.borough = borough;
        this.airbnbArray = airbnbArray;
        this.borough = borough;
        this.to = to;
        this.from = from;
        //this.from=from;
        //this.to=to;
        
        LoadPropertyData(borough,from,to);
    }
    
    public void setTitle(String s)
    {
        title.setText(s);
    } 
    
    public ListController(ArrayList<AirbnbListing> airbnbArray, HashSet<String> propIds)
    {
        this.airbnbArray = airbnbArray;
        if(propIds == null) list.add("No favourites yet ;(");
        else LoadPropertyData(propIds);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        initCombo();
        hostNames.getItems().addAll(list);
    }

    /**
     * Loads Data into the ListView list to be displayed on the popup list
     * on selecting a borough map button
     */
    private void LoadPropertyData(String borough, int from, int to){
        int index = 0;
        hostNames = new ListView<>();

        while(index < airbnbArray.size() - 1){
            AirbnbListing a = airbnbArray.get(index);
            int price = a.getPrice();

            if(price>=from && price<=to 
            && a.getNeighbourhood().equals(borough)){
                list.add(toString(a));
            }
            index++;
        }

        total = list.size();
    }
    

    public void noComboBox()
    {
        sortBox.setVisible(false);
    }
    
    public void initCombo(){
        sortBox.getItems().addAll(
            "sort by",
            "price",
            "Number of reviews",
            "Host name"
        );
        sortBox.getSelectionModel().selectFirst();
    }
    
    private void LoadPropertyData(HashSet<String> propIds)
    {
        int index = 0;
        hostNames = new ListView<>();
        while(index < airbnbArray.size() - 1){
            AirbnbListing a = airbnbArray.get(index);
            
            if(propIds.contains(a.getId()))
                list.add(toString(a));
            
            index++;
        }

        total = list.size();
    }
    
    public int getNrProp()
    {
        return total;   
    }

    /**
     * A To string method to display the lists
     */
    public String toString(AirbnbListing a){
        return "Host Name:  " + a.getHost_name() + "     Price:   " + a.getPrice() 
        + "     No of Reviews:   " + a.getNumberOfReviews() + "     Minimum nights per stay:   " + 
        a.getMinimumNights() + "   Borough    " + a.getNeighbourhood();
    }
    
    @FXML
    private void buttonHandle(ActionEvent event) throws Exception
    {
        SortedListController sortedListController = new SortedListController(borough,airbnbArray, from,to, "price");

                    /**
                     * Loads the fxml lists for the map buttons and will open a new popup window
                     */
                    FXMLLoader fxmlLoaderList = new FXMLLoader(getClass().getResource("ListViewer.fxml"));
                    fxmlLoaderList.setController(sortedListController);
                    Parent listView = fxmlLoaderList.load();
            
                    Scene listViewScene = new Scene(listView, 700, 700);
            
                    Stage stage = new Stage();
                    stage.setTitle("List of properties");
                    stage.setScene(listViewScene);
                    stage.show();
        
    }
    
    @FXML
    private void comboHandle(ActionEvent event) throws Exception
    {
                String x = String.valueOf(sortBox.getSelectionModel().getSelectedItem());
                showNewList(x);
            
                // if(x.equals("price")){
                    // SortedListController sortedListController = new SortedListController(borough,airbnbArray, from,to, "price");

                    // /**
                     // * Loads the fxml lists for the map buttons and will open a new popup window
                     // */
                    // FXMLLoader fxmlLoaderList = new FXMLLoader(getClass().getResource("ListViewer.fxml"));
                    // fxmlLoaderList.setController(sortedListController);
                    // Parent listView = fxmlLoaderList.load();
            
                    // Scene listViewScene = new Scene(listView, 700, 700);
            
                    // Stage stage = new Stage();
                    // stage.setTitle("List of properties");
                    // stage.setScene(listViewScene);
                    // stage.show();
                // } else if (x.equals("Number of reviews")){
                    // SortedListController sortedListController = new SortedListController(borough,airbnbArray, from,to, "Number of reviews");

                    // /**
                     // * Loads the fxml lists for the map buttons and will open a new popup window
                     // */
                    // FXMLLoader fxmlLoaderList = new FXMLLoader(getClass().getResource("ListViewer.fxml"));
                    // fxmlLoaderList.setController(sortedListController);
                    // Parent listView = fxmlLoaderList.load();
            
                    // Scene listViewScene = new Scene(listView, 700, 700);
            
                    // Stage stage = new Stage();
                    // stage.setTitle("List of properties");
                    // stage.setScene(listViewScene);
                    // stage.show();
                // } else if(x.equals("Host name")){
                    
                    
                // }
                
            
    }
    
    private void showNewList(String sortedBy) throws Exception
    {
        SortedListController sortedListController = new SortedListController(borough,airbnbArray, from,to, sortedBy);

                    /**
                     * Loads the fxml lists for the map buttons and will open a new popup window
                     */
                    FXMLLoader fxmlLoaderList = new FXMLLoader(getClass().getResource("ListViewer.fxml"));
                    fxmlLoaderList.setController(sortedListController);
                    Parent listView = fxmlLoaderList.load();
            
                    Scene listViewScene = new Scene(listView, 700, 700);
            
                    Stage stage = new Stage();
                    stage.setTitle("List of properties");
                    stage.setScene(listViewScene);
                    stage.show();
    }

    
}