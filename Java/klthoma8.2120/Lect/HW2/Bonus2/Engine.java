public class Engine extends TrainCar {

    public Engine(TrainCar next, double dist) {
        super(next, dist);
    }
    @Override
    public void advance(double howFar) {
        itsDistanceFromHome += howFar;
        if (itsNextConnectedCar != null) {
            itsNextConnectedCar.advance(howFar);
        }
    }
    @Override
    public Boolean isMemberOfValidTrain() {
        return itsNextConnectedCar != null && itsNextConnectedCar.isMemberOfValidTrain();
    }
}


