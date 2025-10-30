public class MonsterTester {
    public static void main(String[] args) {
        // Create monsters using the MonsterFactory
        Monster goblin = Monster.MonsterFactory.spawn("goblin");
        Monster orc = Monster.MonsterFactory.spawn("orc");
        Monster troll = Monster.MonsterFactory.spawn("troll");

        // Display each monster's stats using the toString() method
        System.out.println(goblin);
        System.out.println(orc);
        System.out.println(troll);

        // Create a player
        Monster.Player hero = new Monster.Player("Hero", 100);
        System.out.println(hero);

        // Test attack functionality
        goblin.attack(hero);
        orc.attack(hero);
        troll.attack(hero);

        // Display player's health after attacks
        System.out.println(hero);

        // Check if the player is still alive
        if (hero.isAlive()) {
            System.out.println("The player survived the attacks!");
        } else {
            System.out.println("The player has been defeated.");
        }
    }
}
