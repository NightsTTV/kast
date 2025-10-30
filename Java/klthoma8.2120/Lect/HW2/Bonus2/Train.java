public class Train {
    private Engine engine;
    public Train(Engine engine) {
        this.engine = engine;
    }
    public boolean isValidTrain() {
        return engine != null && engine.isMemberOfValidTrain();
    }
    public void moveTrain(double distance) {
        if (engine != null) {
            engine.advance(distance);
        }
    }
}

