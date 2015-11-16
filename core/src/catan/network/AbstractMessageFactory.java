package catan.network;

class ContentException extends Exception{}

	
/**
 * Class needed to create messages
 * @author Sebastian
 *
 */
public abstract class AbstractMessageFactory {
	
	abstract SystemMessage getSystemMessage(SystemMessage.SystemType type, Object content) throws ContentException;
	
}
