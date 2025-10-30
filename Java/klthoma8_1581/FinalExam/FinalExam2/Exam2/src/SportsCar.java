class SportsCar extends Car {
    private boolean useTurbo;

    public SportsCar(String make, String model, String id, String type) {
        super(make, model, id, type);
        this.useTurbo = false;
    }

    private void useTurbo() {
        useTurbo = true;
        System.out.println("Turbo turned on! Going faster..");
    }

    @Override
    public void move() {
        super.move();
        useTurbo();

        System.out.printf("%d%n", getAcceletator(),goFaster());

        System.out.println("SportCar is going crazy fast!");
    }
}