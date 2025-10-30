public class Monster{
	private String name;
	private int health;
	private int strength;
	private int xp;

	public Monster(String name, int health, int strength, int xp) {
		this.name = name;
		this.health = health;
		this.strength = strength;
		this.xp = xp;
	}

	public static Monster spawn(String type) {
		switch (type.toLowerCase()) {
		case "goblin":
			return new Monster("goblin", 60, 8, 1);
		case "orc":
			return new Monster("orc", 100, 12, 3);
		case "troll":
			return new Monster("troll", 150, 15, 5);
		default:
			throw new IllegalArgumentException("Illegal Argument monster: " + type);
		}
	}

	public String getName() {
		return name;
	}

	public int getHealth() {
		return health;
	}
	public int getStrength() {
		return strength;
	}
	public int getXP(){
		return xp;
	}
	public void attack(Player hero) {
		System.out.printf("%s attacks player for %d damage\n", name strength);
		hero.takeDamage(strength);
	}
	public void takeDamage(int damage) {
		health = Math.max(0, health - damage);
	}

	@Override
	public String toString() {
		return String.format("[%s] HP: %d, STR: %d", name, health, strength);
	}
}