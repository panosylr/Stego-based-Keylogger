package covertkeylogger;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class Cryptography {

	private KeyGenerator kgen;    //initialization on the constructor of the class for used elements of our crypto
	private SecretKey skey; 
	private byte[] ciphertext;
	private Cipher cipher;
	//private  String iv = "1234567812345678";
	//private javax.crypto.spec.IvParameterSpec ivspec = new javax.crypto.spec.IvParameterSpec(iv.getBytes());
	public Cryptography(String algorithm){
		
		try {
			kgen = KeyGenerator.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		kgen.init(128);
		skey= kgen.generateKey();
		
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
	}
	
	
	public byte[] getCiphertext(){
		return ciphertext;
	}
	
	public byte[] encrypt(byte[] buff){
		
		try {
			byte[] iv = new byte[]{0x00,0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x09,
		            0x00,0x01,0x02,0x03,0x04,0x05};
			AlgorithmParameters params =  cipher.getParameters();
			IvParameterSpec ivspecs = new  IvParameterSpec(iv);
			try {
				cipher.init(Cipher.ENCRYPT_MODE, skey, ivspecs);
			} catch (InvalidAlgorithmParameterException e) {
				e.printStackTrace();
			}
			
			ciphertext = cipher.doFinal(buff);
		}
		catch (InvalidKeyException e1) {
			e1.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		
		return ciphertext;
	}
	
	
	
	public List<String> decrypt(List<String> encryptedList){
		
		List<String> decryptedList = new ArrayList<String>();
		AlgorithmParameters params;
		byte[] iv = new byte[]{0x00,0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x09,
	            0x00,0x01,0x02,0x03,0x04,0x05};
		try {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			params =  cipher.getParameters();
			IvParameterSpec ivspecs = new  IvParameterSpec(iv);
			try {
				cipher.init(Cipher.DECRYPT_MODE, skey, ivspecs);
			} catch (InvalidAlgorithmParameterException e) {
				e.printStackTrace();
			}
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} /*catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (InvalidParameterSpecException e) {
				e.printStackTrace();
		} */catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}

		try {
			for (String s : encryptedList){
				decryptedList.add(cipher.doFinal(s.getBytes()).toString());
			}
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return decryptedList;
	}
}
