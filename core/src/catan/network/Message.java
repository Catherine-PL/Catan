package catan.network;

public interface Message 
{
	public enum Type
	{
		GRACZ, MAPA, KOSC, ZLODZIEJ, HANDEL, KARTA, BUDYNEK;
	}
	
	public Type getType();
	public Object getContent();

}
