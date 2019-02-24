package application;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Song {
	@SerializedName("release")
	@Expose
	private ReleaseDetails release;
	@SerializedName("artist")
	@Expose
	private ArtistDetails artist;
	@SerializedName("song")
	@Expose
	private SongDetails song;
	
	
	public String toString() {
		return song.getTitle();
	}
	
	public ArtistDetails getArtistDetails() {
		return this.artist;
	}
	
	public ReleaseDetails getRelease() {
		return this.release;
	}
	
	public SongDetails getSongDetails() {
		return this.song;
	}
	
	public class ReleaseDetails{
		private int id;
		private String name;
		
		public int getReleaseID() {
			return this.id;
		}
		public void setReleaseID(int id) {
			this.id = id;
		}
		public String getReleaseName() {
			return this.name;
		}
		public void setReleaseName(String name) {
			this.name= name;
		}
	}
	
	public class ArtistDetails{
	    private String terms;
	    private String name; 

	    public void setTerms(String terms) {
	    	this.terms = terms;
	    }
	    
	    public String getTerms() {
	    	return this.terms;
	    }
	    
	    public void setName(String name) {
	    	this.name = name;
	    }
	    
	    public String getName() {
	    	return this.name;
	    }
	}
	
	public class SongDetails{
		
		private String title; 
		private String id; 
		
		public void setTitle(String title) {
			this.title = title;
		}
		
		public String getTitle() {
			return this.title;
		}
		
		public void setSongId(String id) {
			this.id = id;
		}
		public String getSongId() {
			return this.id;
		}
	}

}
