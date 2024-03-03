
import java.util.HashMap;
import java.net.URL;
import com.opencsv.CSVReader;
import java.io.*;
import java.net.URISyntaxException;

/**
 * Write a description of class Users here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class UsersLoader
{
    private HashMap<String,String> users;

    /**
     * Constructor for objects of class Users
     */
    public UsersLoader()
    {
        users = new HashMap<>();
        try{
            URL url = getClass().getResource("users.csv");
            CSVReader reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
            String [] line;
            //skip the first row (column headers)
            //reader.readNext();
            while ((line = reader.readNext()) != null) {
                users.put(line[0], line[1]);
            }
        }
        catch(IOException | URISyntaxException e){
            System.out.println("Failure! Something went wrong");
            e.printStackTrace();
        }
        

        users.forEach((k,v) -> {System.out.println(k+" "+v);});
    }

    public boolean verify(String name, String password)
    {
        return password.equals(users.get(name));

    }
}
