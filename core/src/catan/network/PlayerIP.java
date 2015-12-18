package catan.network;

/**
 * Class which aggregates informations required to connect with a node in a network
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
	/**
	 * 
	 * @return Online status of this player
	 */
	public boolean isOnline()
	{
		return online;
	}
	public String toString()
	{
		return (nickname + ": " + address + ", " + online);
	}	

}
