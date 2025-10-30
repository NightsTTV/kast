public class Monster {
    private String name;
    private int health;
    private int strength;
    private int xp;

    // Constructor for Monster class
    public Monster(String name, int health, int strength, int xp) {
        this.name = name;
        this.health = health;
        this.strength = strength;
        this.xp = xp;
    }

    // MonsterFactory class to spawn monsters
    public static class MonsterFactory {
        public static Monster spawn(String type) {
            switch (type.toLowerCase()) {
                case "goblin":
                    return new Monster("goblin", 60, 8, 1);
                case "orc":
                    return new Monster("orc", 100, 12, 3);
                case "troll":
                    return new Monster("troll", 150, 15, 5);
                default:
                    return null;  // Added return statement for the default case
            }
        }
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getStrength() {
        return strength;
    }

    public int getXP() {
        return xp;
    }

    // Attack method to deal damage to the player
    public void attack(Monster.Player hero) {
        System.out.printf("%s attacks player for %d damage\n", name, strength);
        hero.takeDamage(strength);
    }

    // Method to take damage
    public void takeDamage(int damage) {
        health = Math.max(0, health - damage);
    }

    // toString method to print the Monster information
    @Override
    public String toString() {
        return String.format("[%s] HP:%d, AP:%d", name, health, strength); // Corrected to use strength
    }

    // Player class for simulating battles
    public static class Player {
        private String name;
        private int health;

        // Constructor for Player class
        public Player(String name, int health) {
            this.name = name;
            this.health = health;
        }

        // Method to take damage
        public void takeDamage(int damage) {
            health = Math.max(0, health - damage);
        }

        // Check if player is alive
        public boolean isAlive() {
            return health > 0;
        }

        // Getter for player's name
        public String getName() {
            return name;
        }

        // Getter for player's health
        public int getHealth() {
            return health;
        }

        // toString method to print the Player information
        @Override
        public String toString() {
            return String.format("[Player: %s] HP: %d", name, health);
        }
    }
}