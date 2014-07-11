package simulation;

import java.util.ArrayList;
import java.util.Random;

public class Main {

	private static double totalTime = 0;
	private static ArrayList<Node> nodes;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Random numberGen = new Random();

		System.out.println("Building network");
		// Create a network of size 1000
		NetworkBuilder network = new NetworkBuilder(1000);

		// Get nodes for future usage...
		nodes = network.getNodes();

		System.out.println("Starting all node threads.");
		// Start all the nodes!! :D
		for (Node node : nodes) {
//			try {
//				Thread.sleep(15);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			node.start();
		}

		while (true) {

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (totalTime >= 5) {
				Node.setOwnDataLimit();
			}

//			if (totalTime == 10.026666666666666) {
//				for (int i = 0; i < 100; i++) {
//					int rand = numberGen.nextInt(999);
//					nodes.get(rand).interrupt();
//					System.out.println("Thread " + nodes.get(rand).getName()
//							+ "killed");
//
//				}
//			}

		}

	}

	public static void printData() {

		totalTime = (0.8) + totalTime;
		double uData = ((double) Data.getIDAllocator() * (double) 100)
				/ (double) 1000;
		System.out.println("Days passed: " + totalTime + ", Unique Data: "
				+ uData + "kB" + ", Evil Node Data Aquired: "
				+ nodes.get(nodes.size() - 1).percentageAcquired() + "%");
		// System.out.println("");

	}

}
