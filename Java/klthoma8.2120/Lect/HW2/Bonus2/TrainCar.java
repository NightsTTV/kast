public abstract class TrainCar {

    protected double itsDistanceFromHome;
    protected TrainCar itsNextConnectedCar;

    public TrainCar(TrainCar nextCar, double initialDist) {
        itsNextConnectedCar = nextCar;
        itsDistanceFromHome = initialDist;
    }
    public abstract void advance(double howFar);
    public abstract Boolean isMemberOfValidTrain();
}


