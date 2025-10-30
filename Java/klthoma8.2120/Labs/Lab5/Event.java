import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.io.Serializable;

public class Event implements Serializable
{

    private String name;
    private SimpleDateFormat dateFormat;
    private Date date;
    private String location;
    private String description;

    public Event(String name, String date, String location, String description)
    {
        this.name = name;
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        
        try
        {
            this.date = dateFormat.parse(date);
        }
        catch(ParseException e)
        {
            System.out.println("Date unparseable using" + this.dateFormat);
        }

        this.location = location;
        this.description = description;

    }

    public String getName()
    {
        return this.name;
    }

    public String getDate()
    {
        return this.date.toString();
    }

    public String getLocation()
    {
        return this.location;
    }

    public String getDescription()
    {
        return this.description;
    }

    public String toString()
    {
        String obj = "Name: " + this.getName() + "\nDate: " + this.getDate() + "\nLocation: " + this.getLocation() + "\nDescription: " + this.getDescription();

        return obj;
    }

    @Override

    public boolean equals(Object other) {

        if (other instanceof Event) {
            Event otherAsEvent = (Event)other;
            if (this.name.equals(otherAsEvent.getName()) &&
                this.date.equals(otherAsEvent.getDate()) &&
                this.location.equals(otherAsEvent.getLocation()) &&
                this.description.equals(otherAsEvent.getDescription()) )
                return true;

        }
        return false;  // if other is not an Event, it can't possible be equal to this

    }

}