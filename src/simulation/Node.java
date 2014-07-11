package simulation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Defines a node, similar to a tumblr account
 * 
 * @author Tom
 * 
 */
public class Node extends Thread {

	// Synchro stuff
	static int nodes = 1000;
	static CyclicBarrier barrier = new CyclicBarrier(nodes);
	static ReentrantLock printLock = new ReentrantLock();

	// Node 'account' settings and data
	ArrayList<Data> dataStore;
	ArrayList<Node> followers;
	int remainingBandwidth;

	// Network settings
	final int averageUpload = 1; // 19 hours
	final int averageKeylogData = 2000; //
	final int averageBandwidth = 64000; // image capacity per 19 hours, in byte
	final int dataPacketSize = 100; // in bytes
	final int followersToDownload = 1;
	int time = 15000;

	// Account settings
	final int bufferSize = 300000; // in bytes
	final int totalSize = 1000000; // in images
	static boolean ownDataLimit = false; // time limit to stop producing own
											// data

	// Needs things like;
	// A link to followers...

	public Node() {

		this.dataStore = new ArrayList<Data>();
	}

	public static void setOwnDataLimit() {
		ownDataLimit = true;
	}

	public void setFollowers(ArrayList<Node> nodeynode) {

		this.followers = nodeynode;

	}

	public synchronized ArrayList<Data> getData() {

		if (dataStore.size() < (bufferSize / dataPacketSize)) {
			
			List sublist = new ArrayList();
			sublist = new ArrayList<Data>(dataStore.subList(0,dataStore.size())); 
			return (ArrayList<Data>) sublist;

		} else {

			List sublist = new ArrayList();
			sublist = new ArrayList<Data>(dataStore.subList(0,(bufferSize / dataPacketSize)));
			return (ArrayList<Data>) sublist;
		}
	}

	public void run() {

		while (true) {

			// Sleep for 5000... (do this first rather than last so, on
			// initial start all nodes have time to start..)
			// try {
			// Thread.sleep(time);
			// } catch (InterruptedException e) {
			// // have been interrupted, so finish
			// System.out.println("Thread " + this.getName()
			// + " has been killed :(");
			// return;
			// }

			// Set remaining bandwidth for the day ^_^
			remainingBandwidth = averageBandwidth;
			// System.out.println(Thread.currentThread().getId());
			// if (Thread.currentThread().getId() == 8) {
			// System.out.print("I am " + this.toString() + "with ALL data ");
			// for (Data data : dataStore) {
			// System.out.print(data.getIdentifier() + ", ");
			// }
			// System.out.print("\n");
			// }
			// Get data from followers and check for uniqueness
			ArrayList<Data> allData = getAllFollowerData();

			// Extract only unique not seen before...
			ArrayList<Data> uniqueData = extractUniqueData(allData);

			// 'Upload' data to own 'account'
			uploadData(uniqueData);

			// Create own data object and add to the list
			if (!ownDataLimit) {
				uploadOwnData();
			}

			// if (Thread.currentThread().getId() == 8) {
			// System.out.print("I am " + this.toString() + "with new data ");
			// for (Data data : dataStore) {
			// System.out.print(data.getIdentifier() + ", ");
			// }
			// System.out.print("\n");
			// }

			// Syncro threads
			try {
				barrier.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			printIntermediate();

		}
	}

	protected boolean hasBandwidthRemaining() {

		return !(remainingBandwidth < dataPacketSize);

	}

	private void uploadOwnData() {
		// Determine how many data packets we need..
		// round down so we never exceed our allocated bandwidth
		int packets = averageKeylogData / dataPacketSize;
		// if (Thread.currentThread().getId() == 8) {
		// System.out.print("I am " + this.toString() + "this is my data ");
		// }
		for (int a = 0; a < packets; a++) {

			Data tempData = new Data(dataPacketSize);
			uploadDataSingle(tempData);

			// if (Thread.currentThread().getId() == 8) {
			// System.out.print(tempData.getIdentifier() + ", ");
			// }

		}
		// if (Thread.currentThread().getId() == 8) {
		// System.out.print("\n");
		// }
	}

	private synchronized void uploadDataSingle(Data data) {

		if (hasBandwidthRemaining()) {

			if (dataStore.size() < ((totalSize * (averageBandwidth / dataPacketSize)) - 1)) {

				// Just add the item to the begining...
				dataStore.add(0, data);

			} else {

				// need to remove the last element...
				dataStore.remove(dataStore.size() - 1);

				// add new element to begining
				dataStore.add(0, data);

			}

			// subtract remaing bandwidth;

			remainingBandwidth = remainingBandwidth - dataPacketSize;

		}

	}

	private void uploadData(ArrayList<Data> uniqueData) {
		// assume that low to high index is new to old data...

		// create iterator
		ListIterator<Data> iter = uniqueData.listIterator();

		// go to the end
		while (iter.hasNext()) {
			// last iteration
			iter.next();
		}

		while (iter.hasPrevious()) {
			Data data = iter.previous();
			uploadDataSingle(data);

		}
	}

	private ArrayList<Data> extractUniqueData(ArrayList<Data> allData) {

		ArrayList<Data> tempData = new ArrayList<Data>();

		// Determine how much data we need to upload
		int dataRequired = ((averageBandwidth - averageKeylogData))
				/ dataPacketSize;

		// For all items that have been received from followers, up to bandwidth
		// limit
		for (Data followerData : allData) {

			boolean duplicate = false;

			for (Data nodeData : dataStore) {

				if (nodeData.getIdentifier() == followerData.getIdentifier()) {

					duplicate = true;
					break;
				}

			}

			// If we aren't a duplicate... add to the list
			if (!duplicate) {

				tempData.add(followerData);

			}

			if ((tempData.size() >= dataRequired)) {

				break;
			}
		}

		// if (Thread.currentThread().getId() == 8) {
		// System.out.print("I am " + this.toString()
		// + "with non duplicate data ");
		// for (Data data : tempData) {
		// System.out.print("data is: " + data.getIdentifier() + ", ");
		// }
		// System.out.print("\n");
		// }

		return tempData;
	}

	private ArrayList<Data> getAllFollowerData() {

		ArrayList<Data> followerData = new ArrayList<Data>();

		// select random values for followers to be read;
		ArrayList<Integer> toDownload = new ArrayList<Integer>();
		Random random = new Random();

		for (int i = 0; i < followersToDownload; i++) {

			int randomVal = random.nextInt(followers.size());

			boolean alreadyExists = false;

			// ensure we dont already have the random value..
			for (Integer inte : toDownload) {

				if (inte.intValue() == randomVal) {
					alreadyExists = true;
				}
			}

			if (!alreadyExists) {
				// System.out.println(randomVal);
				toDownload.add(randomVal);
			} else {
				i--;
			}
		}

		ArrayList<ArrayList<Data>> followerDataList = new ArrayList<ArrayList<Data>>();
		ArrayList<Iterator<Data>> dataIter = new ArrayList<Iterator<Data>>();

		// cycle through all followers extracting their data
		for (Integer follower : toDownload) {

			ArrayList<Data> tempData = followers.get(follower.intValue())
					.getData();

			followerDataList.add(tempData);
			Iterator<Data> iter = tempData.iterator();
			dataIter.add(iter);
			// System.out.println("Follower is: " +
			// followers.get(follower.intValue()));

		}

		for (int a = 0; a < (bufferSize / dataPacketSize) / followersToDownload; a++) {
			for (Iterator<Data> iter : dataIter) {
				if (iter.hasNext()) {
					followerData.add(iter.next());
				}
			}
		}

		// if (Thread.currentThread().getId() == 8) {
		// System.out.print("I am " + this.toString()
		// + "with ALL follower data ");
		// for (Data data : followerData) {
		// System.out.print(data.getIdentifier() + ", ");
		// }
		// System.out.print("\n");
		// }

		return followerData;

	}

	public double percentageAcquired() {

		return (Double) null;

	}

	protected void printIntermediate() {

		return;
	}
}
