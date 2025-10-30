public class Administrator {
	private int administratorId;
	private String username; 
	private String password;

	public class Administrator(int administratorId, String password, String password) {
		this.administratorId = administratorId;
		this.username = username;
		this.password = password;
	}

	public void setAdministratorId(int administratorId) {
		this.administratorId = administratorId;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAdministratorId() {
		return administratorId;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
}