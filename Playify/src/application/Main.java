package application;
	
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import application.Models.Song;
import application.Models.SongResponse;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.text.Font; 
import javafx.scene.text.FontPosture; 
import javafx.scene.text.FontWeight; 
import javafx.scene.text.Text; 


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		
		try {
			
			
			
			
//			List<Song> allSongs = new Gson().fromJson(new FileReader("music.json"), new TypeToken<List<Song>>(){}.getType());
//			
//			List<Song> tempSongs = new ArrayList<Song>();
//			int counter=0;
//			for(int i=0; i<allSongs.size(); i++) {
//				tempSongs.add(allSongs.get(i));
//				if(counter==99) {
//					SongResponse sr = new SongResponse();
//					sr.setSongsInPage(tempSongs);
//					File f = new File();
//					new Gson().toJson(sr, new FileWriter());
//							
//						
//					tempSongs = new ArrayList<Song>();
//				}
//				counter++;
//			}
			
			
			
			
			Parent root = FXMLLoader.load(getClass().getResource("/application/Login.fxml"));
			Scene scene = new Scene(root,1080,720);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Playify");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		
//		List<Song> allSongs = new Gson().fromJson(new FileReader("music.json"), new TypeToken<List<Song>>(){}.getType());
//		List<Song> tempSongs = new ArrayList<Song>();
//		int stopAt=0;
//		int fileIndex=1;
//		
//		for(int i=0; i<allSongs.size(); i++) {
//			tempSongs.add(allSongs.get(i));
//			if(stopAt==49) {
//				System.out.println(fileIndex);
//				SongResponse sr = new SongResponse();
//				sr.setSongsInPage(tempSongs);
//				File f = new File("C:\\Users\\mtome\\Documents\\Playify\\8150617422953629580\\" + fileIndex);
//				
//				
//				Gson gson = new GsonBuilder().create();
//				FileWriter fWriter = new FileWriter(f.getPath());
//				
//				fWriter.write(gson.toJson(sr));
//				fWriter.flush();
//				System.out.println(sr.getSongsInPage().size());
//				System.out.println(new Gson().toJson(tempSongs));
//				
//				for(int j=0; j<sr.getSongsInPage().size(); j++) {
//					System.out.println(sr.getSongsInPage().get(j).getSongDetails().getTitle() + " " + sr.getSongsInPage().get(j).getArtistDetails().getName());
//				}
//				
//				tempSongs = new ArrayList<Song>();
//				fileIndex++;
//				stopAt = 0;
//			}
//			else {
//				stopAt++;
//			}
//		}
//		

		launch(args);
	}
	
	public Group UI (Stage stage) {
		Text title = new Text();
		title.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20)); 
	    title.setX(490); 
	    title.setY(90);          
	    title.setText("Playify");
	    
	    Group root = new Group(title);
	    return root;
	}
}
