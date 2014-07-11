package simulation;

/**
 * Defines the data packets used within the network. Almost like stego images in tumblr,
 * but using each data item is formed from a new keylogging simulation. Data objects do not
 * contain data form multiple instances of keylogging.
 * @author Tom
 *
 */
public class Data {

	private static int identifierAllocator = 0;
	
	private int size;
	private int identifier;
	
	public static int getIDAllocator() {
		
		return identifierAllocator;
	}
	
	public int getSize() {
		return size;
	}

	public int getIdentifier() {
		return identifier;
	}

	public Data(int size){
		
		//Set Id
		this.identifier = allocateId();
		this.size = size;
		
	}
	
	private static synchronized int allocateId() {
		
		identifierAllocator++;
		return identifierAllocator;
		
	}
	
}
