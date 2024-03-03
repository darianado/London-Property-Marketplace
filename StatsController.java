import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import java.util.ArrayList;
import javafx.scene.layout.BorderPane;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import java.util.LinkedList;
import javafx.fxml.FXMLLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Collections;

/**
 * Write a description of class StatsController here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class StatsController implements Initializable
{
    private ArrayList<AirbnbListing> airbnbArray ;

    //private Statistic[] shownStats = new Statistic[4];

    private LinkedList<Statistic> notShownStats = new LinkedList<>();

    private MapController map;

    @FXML private BorderPane statBox1;
    @FXML private BorderPane statBox2;
    @FXML private BorderPane statBox3;
    @FXML private BorderPane statBox4;

    private HashMap <BorderPane, Statistic>  currentStats = new HashMap<>();

    public StatsController( ArrayList<AirbnbListing> airbnbArray, MapController map )
    {
        this.airbnbArray =  airbnbArray;
        this.map = map;
    }

    private void makeStats()
    {
        statBox1.getStyleClass().add("boxGradient");
        statBox2.getStyleClass().add("boxGradient");
        statBox3.getStyleClass().add("boxGradient");
        statBox4.getStyleClass().add("boxGradient");
        
        currentStats.put(statBox1, avgReviews());
        currentStats.put(statBox2, homesAndApts());
        currentStats.put(statBox3, mostExpensiveBorough());
        currentStats.put(statBox4, totalNrProp());
        notShownStats.add(mostPopularBorough());
        notShownStats.add(propInInnerLnd());
        notShownStats.add(mostCongestedBorough());
        notShownStats.add(spaciousProp());
        

    }
    
    private Statistic spaciousProp()
    {
        int sum = 0;
        for(AirbnbListing abnb : airbnbArray)
        {
            if(abnb.isSpacious()) sum++; 
        }
        
        return new Statistic("Number of spacious properties",""+sum);
        
    }
    
    private Statistic mostCongestedBorough()
    {
        Map<String, Long> sortedBoroughs =
            airbnbArray.stream().collect(
                Collectors.groupingBy(
                    AirbnbListing::getNeighbourhood, Collectors.counting())
               
            );

        Map.Entry<String, Long> maxBorough = null;
        for (Map.Entry<String, Long> entry : sortedBoroughs.entrySet()) {
            if (maxBorough == null || entry.getValue() > maxBorough.getValue()) {
                maxBorough = entry;
            }
        }

        return new Statistic("The most congested borough", maxBorough.getKey());
            
    }
    
    private Statistic totalNrProp()
    {
        return new Statistic("Total number of available properties", ""+airbnbArray.size() );
    }

    private Statistic propInInnerLnd()
    {
        int s = 0;
        for(InnerLondonBorough b : InnerLondonBorough.values())
        {
            s += map.getNrPropIn(b.getName());
        }

        return new Statistic("Total properties in inner London boroughs" , ""+s);
    }

    private Statistic avgReviews()
    {
        float sum = 0;
        for(AirbnbListing abnb : airbnbArray)
        {
            sum += abnb.getNumberOfReviews() ;  
        }

        return new Statistic("Average number of reviews per property", ""+(int) sum/ airbnbArray.size() );

    }

    private Statistic homesAndApts()
    {
        int c = 0;
        for(AirbnbListing abnb : airbnbArray)
        {
            if(! abnb.getRoom_type().equals("Private room") ) 
                c++;
        }

        return new Statistic("The number of entire home and apartments ", ""+c );

    }

    private Statistic mostPopularBorough()
    {

        Map<String, Integer> sortedBoroughs =
            airbnbArray.stream().collect(
                Collectors.groupingBy(
                    AirbnbListing::getNeighbourhood, 
                    Collectors.summingInt(AirbnbListing::getAvailability365)
                )
            );

        //System.out.println(sortedBoroughs);

        HashMap<String, Integer> x = new HashMap<>();

        for (Map.Entry<String, Integer> entry : sortedBoroughs.entrySet()) {
            x.put(entry.getKey() ,entry.getValue()/ map.getNrPropIn(entry.getKey()) );
        }

        //x.forEach((k,v) -> {System.out.println(k+": "+v);});

        Map.Entry<String, Integer> min = null;
        for(Map.Entry<String, Integer> entry : x.entrySet()){
            if (min == null || entry.getValue()< min.getValue()) {
                min = entry;
            }
        }

        return new Statistic("The most popular borough", min.getKey());
    }

    private Statistic mostExpensiveBorough()
    {
        Map<String, Integer> sortedBoroughs =
            airbnbArray.stream().collect(
                Collectors.groupingBy(
                    AirbnbListing::getNeighbourhood, 
                    Collectors.summingInt(AirbnbListing::getMinimPrice)
                )
            );

        Map.Entry<String, Integer> maxBorough = null;
        for (Map.Entry<String, Integer> entry : sortedBoroughs.entrySet()) {
            if (maxBorough == null || entry.getValue()> maxBorough.getValue()) {
                maxBorough = entry;
            }
        }

        return new Statistic("The most expensive borough", maxBorough.getKey());
    }

    @FXML
    private void nextArrowButtonHandle(ActionEvent event)
    {
        Button b = (Button) event.getSource();
        BorderPane statBox = (BorderPane) b.getParent();

        implementStatInBox(getNextStat(statBox), statBox);

    }

    private Statistic getNextStat(BorderPane statBox)
    {

        Statistic s = notShownStats.removeFirst();

        notShownStats.addLast(currentStats.get(statBox));

        return s;

    }

    @FXML
    private void prevArrowButtonHandle(ActionEvent event)
    {
        Button b = (Button) event.getSource();
        BorderPane statBox = (BorderPane) b.getParent();

        implementStatInBox(getPrevStat(statBox), statBox);
    }

    private Statistic getPrevStat(BorderPane statBox)
    {
        Statistic s = notShownStats.removeLast();

        notShownStats.addFirst(currentStats.get(statBox));

        return s; 
    }

    private void implementStatInBox( Statistic s, BorderPane sBox)
    {
        currentStats.put(sBox, s);

        Label n = (Label) sBox.getTop();
        n.setText(s.getStatName());

        Label v = (Label) sBox.getCenter();
        v.setText(s.getStatValue());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        makeStats();

        currentStats.forEach((k, v) -> {
                implementStatInBox(v, k);
            });

        // Label n = (Label) statBox1.getTop();
        // n.setText(stats.get(0).getStatName());

        // Label v = (Label) statBox1.getCenter();
        // v.setText(stats.get(0).getStatValueString());
    }
}
