public class main {
    public static void main(String[] args) {
        // Create an array of Animal objects
        Animal[] animals = {
            new Snake(),
            new Fish(),
            new Bird(),
            new Terrier(),
            new Dog("Poodle")
        };

        for (Animal animal : animals) {  
            System.out.println(animal);
            animal.move();
        }
    }
}
