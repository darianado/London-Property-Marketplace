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
import java.util.Arrays;
import java.util.Collections;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortedListController implements Initializable 
{

    ObservableList list = FXCollections.observableArrayList();
    ArrayList<AirbnbListing> airbnbArray;

    
    private int total;
    private List<AirbnbListing> sortedList;
    private String sortString;

    @FXML private ListView<String> hostNames = new ListView<>();
    
    @FXML private Label title = new Label();
    
    /**
     * Takes a String borough, this will be used to decide which data is selected for the lists
     */
    public SortedListController(String borough, ArrayList<AirbnbListing> airbnbArray, int from, int to, String sortString)
    {
       // this.borough = borough;
       
        this.airbnbArray = airbnbArray;
        this.sortString = sortString;
        
        //this.from=from;
        //this.to=to;
        LoadSortedPropertyData();
        LoadPropertyData(borough,from,to);
    }
    
    /*** Loads Data into the ListView list to be displayed on the popup list* on selecting a borough map button*/
    private void LoadSortedPropertyData(){
        if(sortString.equals("Number of reviews"))
        { sortedList = airbnbArray.stream().sorted(Comparator.comparing(AirbnbListing::getNumberOfReviews)).collect(Collectors.toList());
        } else if(sortString.equals("price"))
        { sortedList = airbnbArray.stream().sorted(Comparator.comparing(AirbnbListing::getMinimPrice)).collect(Collectors.toList());
        } else if(sortString.equals("Host name"))
        { sortedList = airbnbArray.stream().sorted(Comparator.comparing(AirbnbListing::getHost_name)).collect(Collectors.toList());
        } 
        
    }
    
    public void setTitle(String s)
    {
        title.setText(s);
    } 
    
    
    // public SortedListController(ArrayList<AirbnbListing> airbnbArray, HashSet<String> propIds)
    // {
        // this.airbnbArray = airbnbArray;
        // if(propIds == null) list.add("No favourites yet ;(");
        // else LoadPropertyData(propIds);
    // }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
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
            AirbnbListing a = sortedList.get(index);
            int price = a.getPrice();

            if(price>=from && price<=to 
            && a.getNeighbourhood().equals(borough)){
                list.add(toString(a));
            }
            index++;
        }

        total = list.size();
    }
    
    private void LoadPropertyData(HashSet<String> propIds)
    {
        int index = 0;
        hostNames = new ListView<>();
        while(index < airbnbArray.size() - 1){
            AirbnbListing a = sortedList.get(index);
            
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

    
}