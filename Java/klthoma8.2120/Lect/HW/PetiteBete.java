public class PetiteBete{

// instance variables

	protected String name;
	protected int level;
	protected int tolerance;
	protected int speed;
	protected int baseAttack; 

// constructor

	public PetiteBete(String name, int level, int tolerance, int speed, int baseAttack){
		this.name = name;
		this.level = level;
		this.tolerance = tolerance;
		this.speed = speed;
		this.baseAttack = baseAttack;
	}

	// Default Constructor

		public PetiteBete() {
			this("DefaultBete", 1, 100, 10, 10);
		}

	// getters and setters

	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public int getLevel(){
		return level;
	}
	public void setLevel(int level){
		this.level = level;
	}
	public int getTolerance(){
		return tolerance;
	}
	public void setTolerance(int tolerance){
		this.tolerance = tolerance;
	}
	public int getSpeed(){
		return speed;
	}
	public void setSpeed(int speed){
		this.speed = speed;
	}
	public int getBaseAttack(){
		return baseAttack;
	}
	public void setBaseAttack(int baseAttack) {
		this.baseAttack = baseAttack;
	}

    // takeDamage method

	public void takeDamage(int damage){
		this.tolerance -= damage;
		if (this.tolerance < 0) {
			this.tolerance = 0;
		}
	}

	// attack method

	public void attack(PetiteBete target) {
		int attackPower = this.baseAttack + (this.level * 2);
		System.out.println(this.name + " attacks " + target.getName() + " for " + attackPower + " damage!");
		target.takeDamage(attackPower);
	}
}