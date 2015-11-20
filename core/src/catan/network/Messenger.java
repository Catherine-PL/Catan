package catan.network;

public interface Messenger 
{
	public void sendUpdate();
	public void sendUpdate(String nickname);
}
