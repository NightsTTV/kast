public class Room {
    private String name; // Name of the room
    private String description; // Description of the room
    private Room north; // Room to the north
    private Room east; // Room to the east
    private Room west; // Room to the west
    private Room south; // Room to the south

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
    }

    
    public void setExits(Room north, Room east, Room west, Room south) {
        this.north = north;
        this.east = east;
        this.west = west;
        this.south = south;
    }


    public Room getNorth() {
        return north;
    }

   
    public Room getEast() {
        return east;
    }

 
    public Room getWest() {
        return west;
    }

  
    public Room getSouth() {
        return south;
    }

 
    public String getName() {
        return name;
    }

   
    public String getExits() {
        StringBuilder exits = new StringBuilder();
        if (north != null) exits.append("[N]orth: " ).append(north.getName()).append(" \n");
        if (east != null) exits.append("[E]ast: " ).append(east.getName()).append(" \n");
        if (west != null) exits.append("[W]est: " ).append(west.getName()).append(" \n");
        if (south != null) exits.append("[S]outh: " ).append(south.getName()).append(" \n");

    
       if (exits.length() > 0) {
            exits.setLength(exits.length() - 1);
        }
        return exits.toString();
    }

   
    @Override
    public String toString() {
        return String.format("[%s]\n%s\n%s", name, description, getExits());
    }
}
