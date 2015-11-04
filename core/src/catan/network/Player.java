package catan.network;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Player {

	private String nickname;
	private String address;
	
	Player(String nick, String ip) throws UnknownHostException
	{
		nickname = nick;
		address = ip;
		//address = InetAddress.getByName(ip);
	}
	public String getNickname()
	{
		return nickname;
	}	
	public String getIp()
	{
		return address;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
