package simulation;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

/**
 * Generates nodes for each account and creates follower links...
 * @author Megan
 *
 */
public class NetworkBuilder {
	private ArrayList<Node> allNodes;
	
	public NetworkBuilder(int sizeOfNetwork) {
		allNodes = new ArrayList<Node>();
		for(int i = 0; i < sizeOfNetwork -1; i++) {
			Node node = new Node();
			allNodes.add(node);
		}
		
		EvilNode eve = new EvilNode();
		allNodes.add(eve);
		
		setFollowers(allNodes);
	}
	
	private void setFollowers(ArrayList<Node> nodes) {
		Random numberGenerator = new Random();
		
		for(int j = 0; j < nodes.size(); j++) {
			ArrayList<Node> followers = new ArrayList<Node>();
			while(followers.size() < 10) {
				int random = numberGenerator.nextInt(nodes.size());
				if(!followers.contains(nodes.get(random)) && !nodes.get(random).equals(nodes.get(j))) {
					followers.add(nodes.get(random));
				}
			}
			nodes.get(j).setFollowers(followers);
			
		}
		
	}
	
	public ArrayList<Node> getNodes() {
		return allNodes;
	}
	
	public static void main(String[] args) {
		NetworkBuilder network = new NetworkBuilder(100);
	}
	
	

}
