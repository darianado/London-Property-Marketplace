import java.util.List;
import javafx.fxml.FXML;
import java.net.URL;
import java.util.ArrayList;
import java.util.Stack;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import javafx.event.*;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.shape.*;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

/**
 * The scene for the initial page.
 */
public class ImageViewer
{

    private ComboBox comboBox;
    private ComboBox comboBox2;

    static int from = 0;
    static int to = 0;

    public BorderPane border;
    private Button backButton;
    private Button forwardButton;
    //static Label priceRangeLabel;
    private BorderPane bottomBorder;

    private  Label fromLabel = new Label("Price range from:  ");
    private  Label toLabel = new Label("to:  ");
    private  Label spaceLabel = new Label("   ");
    private  Label errorLabel = new Label("Error: Please select a valid price range");
    private  Label priceRangeLabel = new Label("Your price range is: ");


    public Pane getNavBar()
    {
        return bottomBorder;   
    }

    
    public BorderPane startView() 
    {
        BorderPane border = new BorderPane();
        border.setBorder(new Border(new BorderStroke(Color.BLACK, 
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        bottomNavInit();

        border.setCenter(welcomeBox());

        border.setTop(priceSelector());

        border.setBottom(getNavBar());

        return border;
    }

    public Pane welcomeBox()
    {  
        Pane welcomeBox = new StackPane();

        try
        {
            Parent welcome = FXMLLoader.load(getClass().getResource("welcome.fxml"));
            welcomeBox.getChildren().add(welcome);

        }
        catch (java.io.IOException ioe)
        {
            ioe.printStackTrace();
        }

        return welcomeBox;
        // Label errorLabel = new Label("Error: Please select a valid price range");

        // Label label1 = new Label("Welcome to the London Property MarketPlace Application!");
        // Label label2 = new Label("Here you'll be able to view all properties registered on the AirBnb App, to get started:");
        // Label label3 = new Label("Select a valid price range from the dropdown box above");
        // label1.setFont(Font.font ("Verdana", 20));

        // VBox welcomeBox = new VBox();
        // welcomeBox.getChildren().addAll(label1, label2,label3);
        // welcomeBox.setSpacing(10);


    }

    public Label getTitle(String s)
    {
        Label l = new Label(s);
        l.getStyleClass().add("topText");
        
        return l;
        
    
    }
    public Pane priceRange()
    {

        BorderPane topBorder = new BorderPane();

        
        
        HBox hboxTop = new HBox();
        topBorder.setLeft(priceRangeLabel);
        topBorder.setRight(hboxTop);
        hboxTop.setAlignment(Pos.CENTER_RIGHT);

        
        hboxTop.getChildren().addAll(new Label(""+from), spaceLabel, new Label("-> "+to));
        topBorder.getStyleClass().add("topText");
        

        return topBorder;
    }

    public Pane priceSelector()
    {
        boolean isWithinRange = false;

        comboBox = new ComboBox();
        comboBox2 = new ComboBox();
        
        comboBox.getStyleClass().add("button");
        comboBox2.getStyleClass().add("button");

        //The From price range combo box values
        comboBox.getItems().addAll(
            "0",
            "20",
            "40",
            "60",
            "80",
            "100"
        );

        //The To price range combo box values
        comboBox2.getItems().addAll(
            "-",
            "20",
            "40",
            "60",
            "80",
            "100",
            "80000"
        );

        // error label invisble to begin with
        errorLabel.setVisible(false);
        errorLabel.getStyleClass().add("error");
        // errorLabel.setAlignment(Pos.CENTER_LEFT);
        // errorLabel.setMaxHeight(Double.MAX_VALUE);
        // errorLabel.setStyle("-fx-text-fill: red;");
        

        /**
         * The "From" combobox listener
         * Checks to see the users next selection
         * if the selection is invalid it will display an error message &
         * disable the backwards and forward buttons
         */

        comboBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) ->
            {
                from = Integer.parseInt((String) newValue);
                if(from > to){
                    System.out.println("select a different price range");
                    backButton.setDisable(true);
                    forwardButton.setDisable(true);
                    errorLabel.setVisible(true);
                    // priceRangeLabel.setVisible(false);
                } else {
                    backButton.setDisable(false);
                    forwardButton.setDisable(false);
                    errorLabel.setVisible(false);
                    // priceRangeLabel.setVisible(true);
                }
                System.out.println(""+from+" "+to);
            }
        );

        /**
         * The "To" combobox listener
         * Checks to see the users next selection
         * if the selection is invalid it will display an error message &
         * disable the backwards and forward buttons
         */

        comboBox2.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) ->
            {
                to = Integer.parseInt((String) newValue);
                if(to < from){
                    System.out.println("select a different price range");
                    backButton.setDisable(true);
                    forwardButton.setDisable(true);
                    errorLabel.setVisible(true);

                } else {
                    backButton.setDisable(false);
                    forwardButton.setDisable(false);
                    errorLabel.setVisible(false);

                }
                System.out.println(""+from+" "+to);
            }
        );

        // Create a BorderPane within the top of the origin border
        BorderPane topBorder = new BorderPane();
        HBox hboxTop = new HBox();
        topBorder.setLeft(errorLabel);
        topBorder.setRight(hboxTop);

        hboxTop.setAlignment(Pos.CENTER_RIGHT);
        topBorder.setBorder(new Border(new BorderStroke(Color.BLACK, 
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        StackPane paneTop1 = new StackPane();
        StackPane paneTop2 = new StackPane();

        paneTop1.getChildren().add(comboBox);
        paneTop2.getChildren().add(comboBox2);

        comboBox.getSelectionModel().selectFirst();
        comboBox2.getSelectionModel().selectFirst();
        hboxTop.getChildren().addAll(fromLabel,paneTop1, spaceLabel, toLabel, paneTop2);

        topBorder.getStyleClass().addAll("selector");
        
        return topBorder;
    }

    private void bottomNavInit()
    {
        // Backwards and forward button initalisation
        backButton = new Button("<");
        forwardButton = new Button(">");
        //Set button sizes
        backButton.setPrefSize(100, 20);
        forwardButton.setPrefSize(100, 20);

        // Sets Initial button state
        backButton.setDisable(true);
        forwardButton.setDisable(true);

        // Create a BorderPane within the bottom of the origin border
        bottomBorder = new BorderPane();
        HBox hboxLeft = new HBox();
        HBox hboxRight = new HBox();
        bottomBorder.setLeft(hboxLeft);
        bottomBorder.setRight(hboxRight);
        hboxLeft.getChildren().add(backButton);
        hboxRight.getChildren().add(forwardButton);
        hboxLeft.setAlignment(Pos.CENTER_LEFT);
        hboxRight.setAlignment(Pos.CENTER_RIGHT);

    }

    private boolean priceRangeValid(){
        return from < to;
    }

    public int getFromValue()
    {
        return from;
    }

    public int getToValue()
    {
        return to;   
    }

    public BorderPane getPanel(){
        return border;
    }

    public Button getBackButton(){
        return backButton;
    }

    public Button getForwardButton(){
        return forwardButton;
    }

    public void borderSetCenter(Object obj){
        border.setCenter((Node) obj);
    }

}
