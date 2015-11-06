package catan.network;

public class PlayerIP {
	
	String address;
	String nickname;
	
	public PlayerIP(String ip)
	{
		address = ip;
	}		
	public String getIp()
	{
		return address;
	}
	public String getNickname()
	{
		return nickname;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
