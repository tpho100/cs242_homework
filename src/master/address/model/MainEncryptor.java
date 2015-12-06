package master.address.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class MainEncryptor 
{
	public static void main(String[] args) throws IOException
	{
		BufferedReader userReader = new BufferedReader(new InputStreamReader(System.in));
		Scanner scanner = new Scanner(System.in);
		
		SimpleEncryptor encryptor = new SimpleEncryptor();
		FileEncryptor fileEncryptor = new FileEncryptor();
		
		//Check for command line arguments first before running the main console program
		if(args.length > 0) 
		{
			//Run encryption when first arg -e is used
			if(args[0].equals("-e"))
			{
				try
				{
					System.out.println(encryptor.textEncrypt(args[1]));
					System.out.println("Key:"+ encryptor.getEncryptionKey());
				}
				catch(Exception e)
				{//If any of the args after the -e causes an error, handle the error
					System.err.println("Received invalid command line arguments. Probably not enough arguments. Case \"-e\". Quitting. \n" + e.getMessage());
					
				}
				
			}//Run decryption arg when -d is used
			else if(args[0].equals("-d"))
			{
				try
				{ //If any of the args after the -d causes an error, handle the error
					System.out.println(encryptor.textDecrypt(args[1], (byte) Integer.parseInt(args[2])));
				}
				catch(Exception e)
				{
					System.err.println("Received invalid command line arguments. Case \"-d\". Quitting. \n" + e.getMessage());
				}
				
			}
			else if(args[0].equals("-fe"))
			{ //code to encrypt file
				
				/*
				 * There's probably a better way to check these for both encrypt and decrypt.
				 * Possibly re-write later.
				 */
				switch(args.length)
				{
					case 1:
						System.out.println("Not enough arguments. Try running with -fe [input filename] -o [output filename].");
						break;
					
					case 2: 
						/*
						 * Only 2 arguments so -fe must be one of them, the other does not matter since 
						 * it would be impossible to specify all 4
						 */
						System.out.println("Need to specify output file using -o [filename].");
						break;
						
					case 3:
						/*
						 * There are 3 arguments where the 1st is -fe, 2nd is some text, and 3rd can be anything
						 */
						if(!args[2].equals("-o")) //If the 3rd argument is not -o, quit anyway
						{
							System.out.println("Didn't recognize argument: " + args[2]);
						}
						else 
						{ 
							/*
							 * The third argument was -o but there isn't a fourth argument 
							 * which means there's no text to interpret as a filename
							 */
							System.out.println("Need to specify output file using -o [filename].");
							
						}
						break;
						
					case 4:
						if(!args[2].equals("-o")) //If the 3rd argument is not -o, quit anyway
						{
							System.out.println("Didn't recognize argument: " + args[2]);
						}
						else 
						{ 
							/*
							 * All 4 arguments are present. Andd the 3rd argument is -o. Try running the program.
							 */
							fileEncryptor.encryptFile(args[1], args[3],(byte) 24);
							System.out.println("Key: " + fileEncryptor.getKey());
							
						}
						break;
						
					default:
						/*
						 * There's 4+ arguments. Something is wrong.
						 */
						System.out.println("Too many arguments. Re-run program.");
						break;
				}
			}
			else if(args[0].equals("-fd"))
			{ //code to decrypt file
				/*
				 * There's probably a better way to do this. Fix later.
				 */
				switch(args.length)
				{
					case 1:
						System.out.println("Not enough arguments. Try running with -fe [input filename] -o [output filename].");
						break;
				
					case 2: 
						/*
						 * Only 2 arguments so -fd must be one of them, the other does not matter since 
						 * it would be impossible to specify all 5
						 */
						System.out.println("Need to specify output file using -o [filename].");
						break;
						
					case 3:
						/*
						 * There are 3 arguments where the 1st is -fd, 2nd is some text, and 3rd can be anything
						 */
						if(!args[2].equals("-o")) //If the 3rd argument is not -o, quit anyway
						{
							System.out.println("Didn't recognize argument: " + args[2]);
						}
						else 
						{ 
							/*
							 * The third argument was -o but there isn't a fourth argument 
							 * which means there's no text to interpret as a filename
							 */
							System.out.println("Need to specify output file using -o [filename].");
							
						}
						break;
						
					case 4:
					
						if(!args[2].equals("-o")) //If the 3rd argument is not -o, quit anyway
						{
							System.out.println("Didn't recognize argument: " + args[2]);
						}
						else 
						{ 
							System.out.println("Did not specify key. Re-run program with key.");
						}
						break;
						
					case 5:
						/*
						 * There are 5 arguments. The first must be -fd, the second is some text...
						 * The third is -o (it checks below)
						 * The fourth is some text
						 * The fifth is some text
						 */
						if(!args[2].equals("-o")) //If the 3rd argument is not -o, quit anyway
						{
							System.out.println("Didn't recognize argument: " + args[2]);
						}
						else
						{
							//fileEncryptor.writeSafely(args[3], "")
							try
							{
								fileEncryptor.decryptFile(args[1], args[3], Byte.valueOf(args[4]));
							}
							catch(NumberFormatException num)
							{
								System.err.println("Value of key is larger than a byte can hold. I would re-think what you're trying to do.");
							}
						}
						
						break;
					default:
						/*
						 * There's 5+ arguments. Something is wrong.
						 */
						System.out.println("Too many arguments. Re-run program.");
						break;
				} //Close off switch block
			} //Close off else-if block
			else {
					System.out.println("Invalid command line option. Re-run program.");
			}
		} //close off if block
		else
		{
				while(true)
				{
					System.out.println("****ENTER # TO SELECT OPTION****");
					System.out.println("1 - Encrypt Line of Text");
					System.out.println("2 - Decrypt Line of Text");
					System.out.println("3 - Encrypt File");
					System.out.println("4 - Decrypt File");
					System.out.println("5 - Quit Program");
					
					try
					{
						int option = scanner.nextInt();
					
						if(option == 5)
						{
							System.out.println("Quitting...\n\n");
							scanner.close();
							break;
						}
				
						switch(option)
						{
							case 1:
							{ // Encrypt text by using user provided text
								System.out.println("Enter text to encrypt: ");
								String userText = userReader.readLine();
								
								String encryptedText = encryptor.textEncrypt(userText);
								
								System.out.println("Encrypting...");
								System.out.println(encryptedText + "\n");
								System.out.println("Key:" + encryptor.getEncryptionKey() + "\n");
								System.out.println("Enter anything to continue...");
								System.in.read();
								break;
							}
					
							case 2:
							{ // Decrypt text by using user provided text and key
								System.out.println("Enter text to decrypt: ");
								String userText = userReader.readLine();
								
								System.out.println("Enter key: ");
								//scanner.nextLine(); //Used to fix scanner skipping lines behavior
								int userKey = scanner.nextInt();
								
								String decryptedText = encryptor.textDecrypt(userText,(byte) userKey);
								System.out.println("Decrypting...");
								System.out.println(decryptedText + "\n");
								System.out.println("Enter anything to continue...");
								System.in.read();
								break;
							}
						
							case 3:
							{ //Encrypt file
								System.out.println("Enter a file to encrypt: ");
								String inputFile;
								inputFile = userReader.readLine();
								
								System.out.println("Enter the output file: ");
								String outputFile;
								outputFile = userReader.readLine();
								
								fileEncryptor.encryptFile(inputFile, outputFile,(byte) 24);
								System.out.println("Your key: " + fileEncryptor.getKey() + "\n");
								System.out.println("Finished Encrypting.");
								System.out.println("Enter anything to continue...");
								System.in.read();
								break;
							}
						
							case 4:
							{ //Decrypt file
								
								System.out.println("Enter a file to decrypt: ");
								String inputFile;
								inputFile = userReader.readLine();
								
								System.out.println("Enter the output file: ");
								String outputFile;
								outputFile = userReader.readLine();
								
								System.out.println("Enter the key: ");
								byte key;
								key = scanner.nextByte();
								
								fileEncryptor.decryptFile(inputFile, outputFile, key);
								System.out.println("Finished Decrypting.");
								System.out.println("Enter anything to continue...");
								System.in.read();
								break;
							}
						
							default:
							{ // Not one of the choices. Loop back to option list.
								System.out.println("You did not select a proper option. Try again.\n");
								//System.out.println("Enter anything to continue...");
								//System.in.read();
								break;
							}
						}//Close off switch block
			
					} //Close off try block
					catch(Exception e)
					{
						System.err.println("Expected integer value at menu selection. Re-run the program.\n");
						break;
					}
					
					
				} //Close off while block	
		}	//Close off else block
	} //close off main

}