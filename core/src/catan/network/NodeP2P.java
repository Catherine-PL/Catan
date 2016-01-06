package catan.network;

/**
 * Class which aggregates informations required to connect with a node in a network
 * @author Sebastian
 *
 */
public class NodeP2P {
	
	String address;
	//String nickname;
		
	/**
	 * 
	 * @param ip - String representation of ip.
	 */
	public NodeP2P(String ip)
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
	//public String getNickname()
	//{
	//	return nickname;
	//}
	/**
	 * 
	 * @return Online status of this player
	 */
	public String toString()
	{
		//return (nickname + ": " + address);
		return (address);
	}	

}
