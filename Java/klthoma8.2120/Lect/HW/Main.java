public class Main {
    public static void main(String[] args) {
        PetiteBete bete1 = new PetiteBete("Dragon", 5, 120, 15, 20);
        PetiteBete bete2 = new PetiteBete("Goblin", 3, 80, 12, 15);

        bete1.attack(bete2);  // Dragon attacks Goblin
        System.out.println("Goblin's new tolerance: " + bete2.getTolerance());
    }
}
