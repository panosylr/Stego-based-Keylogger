package covertkeylogger;

import java.io.File;

public class DeleteFolderContents {

	public static void delete(File folder) {
	    File[] files = folder.listFiles();
	    if(files!=null) { //some JVMs return null for empty dirs
	        for(File f: files) {
	            if(f.isDirectory()) {
	                delete(f);
	            } else {
	                f.delete();
	            }
	        }
	    }
	   // folder.delete();
	}
	
}
