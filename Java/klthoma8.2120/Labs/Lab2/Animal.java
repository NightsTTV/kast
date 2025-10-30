class Animal {
    protected String type;

    public Animal(String type) {
        this.type = type;
    }

    public void move() {
        System.out.println("*Started movement");
    }

    @Override
    public String toString() {
        return "This animal type: " + this.type;
    }
}

