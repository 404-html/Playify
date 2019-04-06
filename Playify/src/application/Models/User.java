package application.Models;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class User{
	
	@SerializedName("username")
	@Expose
	private String username;
	
	@SerializedName("password")
	@Expose
	private String password;
	@SerializedName("playlists")
	@Expose
	private List<Playlist> playlists;

	public User(String user, String pass) {
		this.username = user;
		this.password = pass;
		this.playlists = new ArrayList<Playlist>();
	}
	
	/**
	 * A overloaded constructor that intializes a username, password, and list of playlists
	 * @param user
	 * @param pass
	 * @param playlists
	 */
	public User(String user, String pass, List<Playlist> playlists) {
		this.username = user;
		this.password = pass;
		this.playlists = playlists; 
	}
	
	public String getUsername()
	{
		return username;
	}

	public String getPassword() {
		return password;
	}

	public List<Playlist> getPlaylists(){
		return this.playlists;
	}
	
	public Playlist getSpecificPlaylist(String name) {
		Playlist found = null;
		for(int i = 0; i < playlists.size(); i++) {
			if(playlists.get(i).getPlaylistName().equals(name)) {
				found = playlists.get(i);
				break;
			}
		}
		
		return found;
	}
	
	/**
	 * <p> Removes a playlist from a user's list of playlists </p>
	 * @return True if successful in removing playlist, false otherwise
	 */
	public boolean removePlaylist(String playlistName) {
		for(int i=0;i<playlists.size();i++) {
			Playlist currentPlaylist = playlists.get(i);
			if(currentPlaylist.getPlaylistName().equals(playlistName)) {
				playlists.remove(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * <p> Retrieve the list of playlistnames that is found in the user's list of playlists</p>
	 * @return List of playlistsnames
	 */
	public List<String> getPlaylistNames() {
		List<String> names = new ArrayList<String>();
		for(Playlist p: this.playlists) {
			names.add(p.getPlaylistName());
		}
		
		return names;
	}

	/**
	 * <p>Set the username that the user has selected</p>
	 * @param uname The user's new username
	 */
	public void setUsername(String uname) {
		this.username = uname;
		try {
			updateUserJSON();
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Unable to update users.json: Users.setUsername()");
		}
	}

	/**
	 * <p>Set the users password to a new one once changed/added</p>
	 * @param pass The password selected by the user
	 */
	public void setPassword(String pass) {
		this.password = pass;
		try {
			updateUserJSON();
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Unable to update users.json: Users.setPassword()");
		}
	}

	/**
	 * <p>For initialization, set the users playlists</p>
	 * @param thePlaylists Playlist list retrieved from the file
	 */
	public void setPlaylists(List<Playlist> thePlaylists){
		this.playlists = thePlaylists;
		try {
			updateUserJSON();
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Unable to update users.json: Users.setPlaylists()");
		}
	}
	
	@SuppressWarnings("unchecked")
	public void updateUserJSON() throws FileNotFoundException, IOException, ParseException {

		JSONParser parsing = new JSONParser();
		JSONObject mainObject = (JSONObject) parsing.parse(new FileReader("users.json"));
		
		JSONArray userArray = (JSONArray) mainObject.get("users"); //the list of user objects found is users.json
		
		JSONObject userEdit = new JSONObject(); //user JSON object to replace the current user object
		userEdit.put("username", this.username);
		userEdit.put("password", this.password);
		if(this.playlists.size() > 0) {
			
			JSONArray pl = new JSONArray(); //new playlist array 
			for (Playlist tempPL : this.playlists) {
				
		         JSONObject playlistJSON = new JSONObject();
		         JSONArray songs = new JSONArray();//new song array to be inserted into the current playlist
		         
		         //Check if there are any actual songs in the playlists
		         if(tempPL.getSongs()!=null) {
		        	 for(Song song : tempPL.songs) {
		        		 
			        	 JSONObject songJSON = new JSONObject();
			        	 JSONObject releaseJSON = new JSONObject();
			        	 JSONObject artistJSON = new JSONObject();
			        	 
			        	 songJSON.put(	"title"	, song.getSongDetails().getTitle());
			        	 songJSON.put("id", song.getSongDetails().getSongId());
			        	 
			        	 releaseJSON.put( "id", song.getRelease().getReleaseID());
			        	 releaseJSON.put( "name", song.getRelease().getReleaseName());
			        	 
			        	 artistJSON.put( "terms", song.getArtistDetails().getTerms());
			        	 artistJSON.put( "name", song.getArtistDetails().getName());
			        	 
			        	 JSONObject finalizedSongJSON = new JSONObject();
			        	 finalizedSongJSON.put( "release", releaseJSON);
			        	 finalizedSongJSON.put( "artist", artistJSON);
			        	 finalizedSongJSON.put( "song", songJSON);
			        	 
			        	 songs.add(finalizedSongJSON);
			         }
		        	 playlistJSON.put("songs", songs);// add all songs to playlist array
		         }
		         //otherwise in the case of an empty playlist, put a JSONArray in place
		         else {
		        	 playlistJSON.put("songs", new JSONArray());
		         }
		         
		         playlistJSON.put("playlistname", tempPL.getPlaylistName()); //add playlist name to the playlist array
		         pl.add(playlistJSON); 
		         
		    
			}
			
			userEdit.put("playlists", pl);//add playlist array to edited user object
		} else {
			userEdit.put("playlists", new JSONArray());
		}
		
		
		if(userArray.size() > 0) {
			for(int i = 0; i < userArray.size(); i++) { //find the user with the same username as this object
				JSONObject traverseUser = (JSONObject) userArray.get(i);
				if(traverseUser.get("username").equals(this.username)) {
					userArray.remove(i); //remove the information from the main user list
					userArray.add(i, userEdit); //add the new information to the main user list in the same area
					
					//Pretty Print the json string
					GsonBuilder builder = new GsonBuilder();
					builder.disableHtmlEscaping();
					Gson gson = builder.setPrettyPrinting().create();
					String json = gson.toJson(mainObject);
					
					//Writes the new user object into the users.json file
					try {
						BufferedWriter fileWrite  = new BufferedWriter(new FileWriter("users.json"));

						fileWrite.write(json);
						fileWrite.flush();
						fileWrite.close();
					}
					catch(IOException ioException) {
						ioException.printStackTrace();
					}
					catch(Exception e) {
						e.printStackTrace();
					}
					break;
				}
			}
		}
	}
	
	/**
	 * <p>Add a playlist to the users current list of playlists </p>
	 * @param newPlaylist New playlist to be added
	 */
	public void addPlaylist(Playlist newPlaylist) {
		this.playlists.add(newPlaylist);
		setPlaylists(this.playlists);
	}
	
	/**
	 * <p>Retrieve the String representation of the current class</p>
	 * @return The String representation of the current class
	 */
	@Override
	public String toString() {
		List<String> playlistsnames = this.getPlaylistNames();
		String result = 
		"User Name: " + username + "\n" + 
		"Password: " + password + "\n" + 
		"Names: {\n";
		for(String name : playlistsnames) {
			result += (name + "\n");
		}
		result += ("}\n");
		return result;
	}
}