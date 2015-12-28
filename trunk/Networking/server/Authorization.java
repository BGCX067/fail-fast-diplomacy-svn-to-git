package Networking.server;

public class Authorization 
{
	private boolean isNewUser;
	private String userName;
	private String hash;
	
	public Authorization(boolean isNewUser, String userName, String hash)
	{
		this.isNewUser = isNewUser;
		this.userName = userName;
		this.hash = hash;
	}

	/**
	 * @return Return false if this User is registered, true if they are registering a new account.
	 */
	public boolean isNewUser() {
		return isNewUser;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @return the password hash
	 * @TODO Encrypt this.
	 */
	public String getHash() {
		return hash;
	}
}
