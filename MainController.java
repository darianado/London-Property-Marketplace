import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.event.*;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import java.util.LinkedList;
import javafx.scene.control.Label;


public class MainController extends Application
{
    
    //Airbnb Data Load returns array of property values
    private AirbnbDataLoader dataLoader  = new AirbnbDataLoader();;
    private ArrayList<AirbnbListing> airbnbArray = dataLoader.load(); ;
  
    //@FXML private MapController mapController;

    //static Parent map;
    //static Parent statistics;
    //static Parent welcome ;
    //static Parent lists;
    static BorderPane root = new BorderPane() ;
    private BorderPane mainPane;
    
    private BorderPane mapPane;
    private MapController mapController;
    
    ImageViewer imageViewer;
    
    private LinkedList<BorderPane> panels = new LinkedList<>();
    
    
    
    public static void main(String[] args)
    {
         Application.launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception 
    {
        
        /** 
         * Initialize the initial Page, of type borderpane with a center
         * Center is used to change scenes between welcome page and map.
         */
         imageViewer = new ImageViewer();
         
        
        root  = imageViewer.startView();
        root.getStylesheets().add("style.css");
        
        
        Scene welcomeScene = new Scene(root, 700, 800);
        
        mainPane = new BorderPane();
        mainPane.setCenter(imageViewer.welcomeBox());

        mapController =  new MapController(airbnbArray);
        
        // Loader for the map fxml file
        FXMLLoader fxmlLoaderMap = new FXMLLoader(getClass().getResource("mapFinal.fxml"));
        fxmlLoaderMap.setController(mapController);
        Parent map = fxmlLoaderMap.load();
        mapPane = new BorderPane();
        mapPane.setCenter(map);
        mapPane.setTop(imageViewer.priceRange());
        
        StatsController statsController = new StatsController(airbnbArray, mapController);
        
        FXMLLoader fxmlLoaderStats = new FXMLLoader(getClass().getResource("StatisticsPanel.fxml"));
        fxmlLoaderStats.setController(statsController);
        Parent statistics = fxmlLoaderStats.load();
        BorderPane statPane = new BorderPane();
        statPane.setCenter(statistics);
        statPane.setTop(imageViewer.getTitle("Statistics: "));
        
        LoginController loginController = new LoginController(airbnbArray);
        
        FXMLLoader fxmlLoaderLogin = new FXMLLoader(getClass().getResource("login.fxml"));
        fxmlLoaderLogin.setController(loginController);
        Parent login = fxmlLoaderLogin.load();
        BorderPane loginPane = new BorderPane();
        loginPane.setCenter(login);
        loginPane.setTop(imageViewer.getTitle("Login to see your favourites "));
        
        
        

        panels.add(mainPane);
        panels.add(mapPane);
        panels.add(statPane);
        panels.add(loginPane);
        
        /**
         * Action On clicking forward button from welcome screen
         * Only goes to map for now.
         */
        
        imageViewer.getForwardButton().setOnAction((ActionEvent ev) -> {
                        changeView(getNextPane());
                    });
        imageViewer.getBackButton().setOnAction((ActionEvent ev) -> {
                        changeView(getPreviousPane());
                    });
        
        // Set the initial stage
        stage.setTitle("ImageViewer");
        stage.setScene(welcomeScene);
        stage.show();
    }
    
    private void changeView(Pane pane)
    {
        root.setCenter(pane);
        
        if(root.getCenter().equals(mainPane)) root.getTop().setVisible(true);
        else root.getTop().setVisible(false);
        
        if(root.getCenter().equals(mapPane)){
            mapController.setFromTo(imageViewer.getFromValue(),imageViewer.getToValue());
            mapPane.setTop(imageViewer.priceRange());
        }
    }
    
     private BorderPane getNextPane()
    {
        BorderPane x = panels.removeFirst();
        panels.addLast(x);
        return panels.getFirst();
    }
    
    private BorderPane getPreviousPane()
    {
        panels.addFirst(panels.removeLast());
        return panels.getFirst();
    }

}