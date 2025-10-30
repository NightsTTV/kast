class Terrier extends Dog {
    public Terrier() {
        super("Terrier");
    }

    @Override
    public void move() {
        System.out.println("The Terrier is running around energetically!");
    }
    public void bark() {
        System.out.println("Woowoowoowoowoo");
    }

    public void actPsycho() {
        System.out.println("The Terrier is going psycho! Running in circles, barking non-stop!");
    }
}
