package master.address.model;

import java.io.File;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainEncryptorUI extends Application {

	String in = null;
	String out = null;
	
	@Override
	public void start(Stage primaryStage) {
		
		BorderPane mainBorder = new BorderPane();
		
		/*
		 * Standard Grid Formatting
		 */
		GridPane center = new GridPane();
		center.setAlignment(Pos.CENTER);
		center.setHgap(15);
		center.setVgap(15);
		center.setPadding(new Insets(50,50,50,50));
		
		Scene mainScene = new Scene(mainBorder,640,480);
		
		/*
		 * Button to determine which scene to use
		 */
		
		Button encryptButton = new Button("USE ENCRYPTER");
		Button decryptButton = new Button("USE DECRYPTER");
		center.add(encryptButton, 0, 0);
		center.add(decryptButton, 1, 0);
		
		/*
		 * Standard Grid Formatting for ENCRYPTER INTERFACE
		 */
		GridPane encrypterGrid = new GridPane();
		Scene encrypterScene = new Scene(encrypterGrid,640,480);
		encrypterGrid.setAlignment(Pos.CENTER);
		encrypterGrid.setHgap(15);
		encrypterGrid.setVgap(15);
		encrypterGrid.setPadding(new Insets(50,50,50,50));
		
		/*
		 * UI Components for encrypter
		 */
		
		Button selectInputFileEnc = new Button("Select Input File");
		Button selectOutputFileEnc = new Button("Select Output File");
		Button encryptMe = new Button("Encrypt me!!");
		Label keyStatus = new Label("Your key...");
		Label inputFile = new Label("No File Selected");
		inputFile.setFont(new Font("Arial",16));
		Label outputFile = new Label("No File Selected");
		outputFile.setFont(new Font("Arial",16));
		keyStatus.setFont(new Font("Arial",16));
		
		/*
		 * Added buttons required for encrypting
		 */
		
		encrypterGrid.add(selectInputFileEnc, 0, 0);
		encrypterGrid.add(inputFile, 1, 0);
		encrypterGrid.add(selectOutputFileEnc, 0, 1);
		encrypterGrid.add(outputFile, 1, 1);
		encrypterGrid.add(encryptMe, 0, 2);
		encrypterGrid.add(keyStatus, 0, 3);
		
		/*
		 * Standard Grid Formatting for DECRYPTER INTERFACE
		 */
		GridPane decrypterGrid = new GridPane();
		Scene decrypterScene = new Scene(decrypterGrid,640,480);
		decrypterGrid.setAlignment(Pos.CENTER);
		decrypterGrid.setHgap(15);
		decrypterGrid.setVgap(15);
		decrypterGrid.setPadding(new Insets(50,50,50,50));
		
		/*
		 * UI Components for decrypter
		 */
		
		Button selectInputFileDec = new Button("Select Input File");
		Button selectOutputFileDec = new Button("Select Output File");
		Button decryptMe = new Button("Decrypt me!!");
		TextField keyValue = new TextField("Enter the key here...");
		Label inputFileDec = new Label("No File Selected");
		inputFileDec.setFont(new Font("Arial",16));
		Label outputFileDec = new Label("No File Selected");
		outputFileDec.setFont(new Font("Arial",16));
		
		/*
		 * Add UI buttons to the grid
		 */
		decrypterGrid.add(selectInputFileDec, 0, 0);
		decrypterGrid.add(inputFileDec, 1, 0);
		decrypterGrid.add(selectOutputFileDec, 0, 1);
		decrypterGrid.add(outputFileDec, 1, 1);
		decrypterGrid.add(keyValue, 0, 2);
		decrypterGrid.add(decryptMe, 0, 3);
		
		
		
		/*
		 * Grab file names from file dialog and display them
		 * for the user to see
		 */
		
		
		/*
		 * Silly way to keep track of file names but oh well..
		 */
		selectOutputFileDec.setOnAction(e->{
			File outputFileNameDec = openFileDialog(primaryStage);
			outputFileDec.setText(outputFileNameDec.getName());
			out = outputFileNameDec.toString();
		});
		
		
		selectInputFileDec.setOnAction(e->{
			File inputFileNameDec = openFileDialog(primaryStage);
			inputFileDec.setText(inputFileNameDec.getName());
			in = inputFileNameDec.toString();
		});

		
		selectOutputFileEnc.setOnAction(e->{
			File outputFileNameEnc = openFileDialog(primaryStage);
			outputFile.setText(outputFileNameEnc.getName());
			out = outputFileNameEnc.toString();
		});
		
		
		selectInputFileEnc.setOnAction(e->{
			File inputFileNameEnc = openFileDialog(primaryStage);
			inputFile.setText(inputFileNameEnc.getName());
			in = inputFileNameEnc.toString();
		});
		
		
		
		encryptMe.setOnAction(e->{
			FileEncryptor fileEncryptor = new FileEncryptor();
			
			try {
				fileEncryptor.encryptFile(in, out);
				int key = (int) fileEncryptor.getKey();
				keyStatus.setText(String.valueOf(key));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			Label status = new Label("Done Encrypting.");
			encrypterGrid.add(status, 3, 3);
			
		});
		
		decryptMe.setOnAction(e->{
			FileEncryptor fileEncryptor = new FileEncryptor();
			int key = Integer.parseInt(keyValue.getText());
			
			try {
				fileEncryptor.decryptFile(in, out, (byte) key);
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			Label status = new Label("Done Decrypting.");
			decrypterGrid.add(status, 3, 3);
		});
		
		mainBorder.setCenter(center);
		primaryStage.setScene(mainScene);
		
		encryptButton.setOnAction(e ->{
			primaryStage.setScene(encrypterScene);
		});
		
		decryptButton.setOnAction(e ->{
			primaryStage.setScene(decrypterScene);
		});
		
		
		primaryStage.show();
		
	}
	
	public File openFileDialog(Stage currentStage)
	{
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(currentStage);
		return file;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
