public class Caboose extends TrainCar {

    public Caboose(double dist) {
        super(null, dist);
    }
    @Override
    public void advance(double howFar) {
        itsDistanceFromHome += howFar;
    }
    @Override
    public Boolean isMemberOfValidTrain() {
        return true;
    }
}

