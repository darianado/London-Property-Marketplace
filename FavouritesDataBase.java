import java.util.HashSet;
import java.net.URL;
import com.opencsv.CSVReader;
import java.io.*;
import java.net.URISyntaxException;
import java.util.HashMap;

/**
 * Write a description of class User here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class FavouritesDataBase
{
    // instance variables - replace the example below with your own
    private HashMap<String, HashSet<String> > favs;

    /**
     * Constructor for objects of class User
     */
    public FavouritesDataBase()
    {
        favs = new HashMap<>();
        try{
            URL url = getClass().getResource("favourites.csv");
            CSVReader reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
            String [] line;
            reader.readNext();
            while ((line = reader.readNext()) != null ) {
                System.out.println(favs);
                 if(!favs.containsKey(line[0])) favs.put(line[0], new HashSet<>());
                favs.get(line[0]).add(line[1]);
                 
            }
        }
        catch(IOException | URISyntaxException e){
            System.out.println("Failure! Something went wrong");
            e.printStackTrace();
        }
        System.out.println(favs);
    }
    
    public HashSet<String> getFavouritesOf(String name)
    {
        // HashSet<String> f = new HashSet<>();
        // favs.forEach((k,v)-> {
            // if(k.equals(name)) f.add(v);
        // });

      // return f;
      
      return favs.get(name);
    }
}
