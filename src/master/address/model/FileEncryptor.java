package master.address.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

public class FileEncryptor
{
	
	private static final String EOS = null;
	private byte key = -1; //-1 means the key has not been generated at all

	private SimpleEncryptor encryptor; 
	
	public byte getKey()
	{ //Accessor method to get encryption key
		return key;
	}

	public void setKey(byte key){
		this.key = key;
	}
	
	/* Takes name of input text file as a string "inputFile" and spits out the contents
	 * of each line into an ArrayList as strings
	*/
	public ArrayList<String> getFileContents(String inputFile) throws IOException
	{
		ArrayList<String> lines = new ArrayList<String>(); //Container to hold file contents
		
		BufferedReader reader = readSafely(inputFile,"Enter valid file name to read from: ");
		String inLine;
		while( (inLine = reader.readLine()) != EOS )
		{
			//Dump every line into the ArrayList
			lines.add(inLine);
		}
		File file = new File(inputFile);
		key = (byte) (file.length() % 128);
		reader.close();
		
		return lines;
	}

	/*
	 * Takes file contents from ArrayList of strings
	 * Dumps the contents into the output file specified by "outputFile"
	 */
	public void dumpIntoFile(String outputFile, ArrayList<String> fileContents) throws IOException
	{
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
		
		for(Iterator<String> itr = fileContents.iterator(); itr.hasNext();)
		{
			writer.write(itr.next());
			writer.newLine();
		}
		writer.close();
	
		return;
	}
	
	/* Takes an ArrayList of Strings which are lines 
	 * Dumps the encrypted version of every line
	 *  into another ArrayList
	 */
	public ArrayList<String> encryptContent(ArrayList<String> fileContents, byte somekey)
	{
		ArrayList<String> newFileContents = new ArrayList<String>(); //ArrayList container to hold encrypted lines
		
		for(Iterator<String> itr = fileContents.iterator();itr.hasNext();)
		{ //Iterate through every item in the ArrayList
			newFileContents.add(textEncrypt(itr.next(),somekey));
		}
		/*
		 * Encryptor only generates the key when it has encrypted something 
		 */ 
		return newFileContents;
	}

	/* 
	 * Takes an ArrayList of strings, decrypts the content using
	 * the specified key and returns the decrypted version of the ArrayList
	 */
	public ArrayList<String> decryptContent(ArrayList<String> encryptedContent, byte somekey)
	{
		encryptor = new SimpleEncryptor();
		ArrayList<String> decryptedContent = new ArrayList<String>(); //ArrayList container to hold decrypted lines
		for(Iterator<String> itr = encryptedContent.iterator(); itr.hasNext();)
		{ //Iterate through every item in the ArrayList
			decryptedContent.add(encryptor.textDecrypt(itr.next(), somekey));
		}
		return decryptedContent;
		
	}

	/*
	 * Encrypts contents in inputFile and dumps encrypted content into outputFile
	 */
	public void encryptFile(String inputFile, String outputFile, byte somekey) throws IOException
	{
		ArrayList<String> fileContent;
		fileContent = getFileContents(inputFile);
		fileContent = encryptContent(fileContent,somekey);
		dumpIntoFile(outputFile, fileContent);
	}
	
	/*
	 * Decrypts contents in inputFile and dumps decrypted content into outputFile
	 */
	
	public void decryptFile(String inputFile, String outputFile, byte somekey) throws IOException
	{
		ArrayList<String> fileContent = new ArrayList<String>();
		fileContent = getFileContents(inputFile);
		fileContent = decryptContent(fileContent, somekey);
		dumpIntoFile(outputFile, fileContent);
	}

	/*
	 * Wrapper code to protect BufferedReader and BufferedWriter from invalid files or 
	 * non-existent files
	 */
	
	
	public BufferedReader readSafely(String fileName, String prompt) throws IOException
	{
		BufferedReader rd = null;
		BufferedReader userReader = new BufferedReader( new InputStreamReader(System.in));
		
		while(rd == null)
		{
			try
			{	
				rd = new BufferedReader(new FileReader(fileName));
				
				
			}
			catch(IOException ex)
			{
				System.out.println("Invalid file or does not exist.");
				System.out.println(prompt);
				fileName = userReader.readLine();
			}
			if(rd == null)
			{
				
			}
		}
		return rd;
	}
	
	public BufferedWriter writeSafely(String fileName, String prompt) throws IOException
	{
		BufferedWriter wr = null;
		BufferedReader userReader = new BufferedReader( new InputStreamReader(System.in));
		
		while(wr == null)
		{
			try
			{	
				wr = new BufferedWriter(new FileWriter(fileName));
			}
			catch(IOException ex)
			{
				System.out.println("Invalid file or does not exist.");
				System.out.println(prompt);
				fileName = userReader.readLine();
			
			}
		}
		return wr;
	}
	
	public String textEncrypt(String plainText, byte key)
	{
		//Begin encrypting the text
		char[] tempText = plainText.toCharArray();
		String cipherText = "";
		
		for(int i  = 0; i < plainText.length();i++)
		{	
			tempText[i] += 4;
			tempText[i] ^= key;
			cipherText += tempText[i];
		}
		return cipherText;
	}
	
}
