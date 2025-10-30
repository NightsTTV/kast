public enum Pose {
	RIGHT("Assets/me-right.png", "Assets/me-right2.png"),
	LEFT("Assets/me-left.png", "Assets/me-left2.png");

	private final Animation animation;

	private Pose(String... images) {
		this.animation = new Animation(images);
	}

	public String getImage() {
		return this.animation.getImage();
	}
}