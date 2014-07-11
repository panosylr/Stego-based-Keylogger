package simulation;

/**
 * Node that will extract relevant data form the main nodes and pass to
 * our main thread for aggregation and displaying..
 * @author Tom
 *
 */
public class EvilNode extends Node{

	public double percentageAcquired() {
		
		double acquiredData = dataStore.size();
		double totalNetworkData = Data.getIDAllocator();
		
		return (acquiredData/totalNetworkData) * 100;
		
	}
	
	public EvilNode() {
		super();
		time = 1000;

		// TODO Auto-generated constructor stub
	}

	private synchronized void uploadDataSingle(Data data) {
	
			if (hasBandwidthRemaining()) {
	
				if (dataStore.size() < ((totalSize * (averageBandwidth / dataPacketSize)) - 1)) {
	
					// Just add the item to the end...
					dataStore.add(data);
	
				} else {
	
					// need to remove the first element...
					//dataStore.remove(0);
					//We want to keep this... we need a list of all unique items...
					
	
					// add new element to end
					dataStore.add(data);
	
				}
	
				// subtract remaing bandwidth;
	
				remainingBandwidth = remainingBandwidth - dataPacketSize;
	
			}
	
		}
	
	private void uploadOwnData() {
		// Determine how many data packets we need..
		// round down so we never exceed our allocated bandwidth
	//	int packets = averageKeylogData / dataPacketSize;
//		if (Thread.currentThread().getId() == 8) {
//			System.out.print("I am " + this.toString() + "this is my data ");
//		}
		//for (int a = 0; a < packets; a++) {

		//	Data tempData = new Data(dataPacketSize);
		//	uploadDataSingle(tempData);

//			if (Thread.currentThread().getId() == 8) {
//				System.out.print(tempData.getIdentifier() + ", ");
//			}

		//}
//		if (Thread.currentThread().getId() == 8) {
//			System.out.print("\n");
//		}
	}
	
	protected void printIntermediate() {
		Main.printData();
		return;
		
	}

}
