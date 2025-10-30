public class RoomTester{
    public cstatic void main(string[] args){

        Room hall = new Room("Hall", "It's dark.");
        Room bed = new Room("Bedroom", "A tiny room.");
        Room bath = new Room("Bathroom", "Toilets here.");
        Room dine = new Room("Dining Room", "Table & chairs.");

        hall.setExits(bed, bath, dine, null);
        System.out.println(hall);
    }
}