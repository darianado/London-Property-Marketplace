
/**
 * Write a description of class Statistic here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Statistic
{
    // instance variables - replace the example below with your own
    private String name;
    private String value;

    /**
     * Constructor for objects of class Statistic
     */
    public Statistic(String name, String value)
    {
        this.name=name;
        this.value=value;
    }
    
    public String getStatValue()
    {
        return value;
    }
    
    public String getStatName()
    {
         return name;   
    }
    


   
}
