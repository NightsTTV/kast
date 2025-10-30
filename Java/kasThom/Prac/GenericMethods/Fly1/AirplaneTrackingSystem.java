// Position Class
 class Position {
 	private String location;

 	public Position(String location) {
 		this.location = location;
 	}

 	public String getLocation() {
 		return location;
 	}

 	@Override
 	public String toString() {
 		return location;
 	}
 }

 class Tracker<T, P> {
 	private T airplaneId;
 	private P currentPosition;

 	public Tracker(T airplaneId, P currentPosition) {
 		this.airplaneId = airplaneId;
 		this.currentPosition = currentPosition;
 	}

 	public void updatePosition(P newPosition) {
 		System.out.println("Airplane " + airplaneId + " moved from " + currentPosition + " to " + newPosition);
 		this.currentPosition = newPosition;
 	}

 	public P getCurrentPosition() {
 		return currentPosition;
 	}

 	public T getAirplaneId() {
 		return airplaneId;
 	}
 }

 public class AirplaneTrackingSystem {
 	public static void main(String[] args) {
 		Tracker<String, Position> plane1 = new Tracker<>("Flight-101", new Position("New Orleans, LA"));
 		plane1.updatePosition(new Position("Houston"));
 		plane1.updatePosition(new Position("Dallas"));

 		 Tracker<Integer, Position> plane2 = new Tracker<>(202, new Position("Chicago"));
 		plane2.updatePosition(new Position("Denver"));
 	}
 }