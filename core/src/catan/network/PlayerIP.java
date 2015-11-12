package catan.network;

/**
 * Class which aggregates informations required to connect with a node
 * @author Sebastian
 *
 */
public class PlayerIP {
	
	String address;
	String nickname;
	boolean online = false;
	
	/**
	 * 
	 * @param ip - String representation of ip.
	 */
	public PlayerIP(String ip)
	{
		address = ip;
	}	
	/**
	 * 
	 * @return String representation of address ip.
	 */
	public String getIp()
	{
		return address;
	}
	/**
	 * 
	 * @return Nickname of a node. Useful after communication initialization. 
	 */
	public String getNickname()
	{
		return nickname;
	}
	
	public boolean isOnline()
	{
		return online;
	}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
