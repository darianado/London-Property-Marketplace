
/**
 * Enumeration class InnerLondonBoroughs - write a description of the enum class here
 *
 * @author (your name here)
 * @version (version number or date here)
 */
public enum InnerLondonBorough
{
    CAMD ("Camden"),
    GWCH ("Greenwich"),
    HACK ("Hackney"),
    HAMM ("Hammersmith and Fulham"),
    ISLI ("Islington"),
    KENS ("Kensington and Chelsea"),
    LAMB ("Lambeth"),
    LEWS ("Lewisham"),
    STHW ("Southwark"),
    TOWH ("Tower Hamlets"),
    WAND ("Wandsworth"),
    WSTM ("Westminster");
    
    private String name;
    
    InnerLondonBorough(String name)
    {
        this.name= name;
    }
    
    public String getName()
    {
         return name;   
    }
    
}
