package application;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Base64;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ServerCommunicationModule extends Thread {

	
	
	public static void main(String []args) throws IOException, ParseException {
		ServerCommunicationModule test = new ServerCommunicationModule();
		test.StartServer();
		
		
	}
	
	private DatagramSocket dSocket;
	private boolean currentlyRunning;
	private byte[] sentMessage = new byte[6000];
	private Dispatcher dispatcher;

	public ServerCommunicationModule() throws IOException {

		dSocket = new DatagramSocket(80);

	}

	// Starts the server by firing the server communication module
	public void StartServer() throws IOException, ParseException {
		currentlyRunning = true;

		while (currentlyRunning) {
			DatagramPacket incomingRequest = new DatagramPacket(sentMessage, sentMessage.length);
			
			dSocket.receive(incomingRequest);
			
			String requestMessage = new String(sentMessage, 0, incomingRequest.getLength());
			
			System.out.println("OH YEAH : " + requestMessage);
			
			JsonObject requestAsJsonObject = new JsonParser().parse(requestMessage).getAsJsonObject();
			sentMessage = this.startDispatcher(requestAsJsonObject);
			
			String msgInBytes = new String(sentMessage);
			System.out.println("RETRIEVED MESSAGE " + msgInBytes);
			
			InetAddress clientAddress = incomingRequest.getAddress();
			int clientPort = incomingRequest.getPort();
			

			DatagramPacket response =  new DatagramPacket(msgInBytes.getBytes(), msgInBytes.getBytes().length, clientAddress, clientPort);

			dSocket.send(response);
//			
//			if (dPacket.getData() != null) {
//				try {
//					byte[] packetData = dPacket.getData();
//					System.out.println(packetData);
//					String packetData64String = new String(packetData);
//
//					JSONParser parser = new JSONParser();
//					
//
//					String packetDataString = packetData64String.toString();
//					int lastBracket = packetDataString.lastIndexOf("}");
//					
//					packetDataString = packetDataString.substring(0,lastBracket+1);
//					JSONObject packetDataRequest = new JSONObject();
//
//					packetDataRequest = (JSONObject) parser.parse(packetDataString);
//
//
//					String dispatchedItem = startDispatcher(packetDataRequest);
//
//
//					sentMessage = dispatchedItem.getBytes();
//
//
//				}
//				catch(Exception e) {
//					e.printStackTrace();
//				}
//				
//			}
		}

	}

	// Fires up the dispatcher whenever a request comes in to the server
	// communication model
	public byte[] startDispatcher(JsonObject request) throws ParseException {

		dispatcher = new Dispatcher();
		
		boolean loginFlag= false;
		boolean songFlag = false;
		boolean registerFlag = false;
		boolean playlistFlag = false;
		
		System.out.println("ENTER DISPATCHER HERE");
		
		LoginDispatcher loginDispatcher = new LoginDispatcher();
		SongDispatcher songDispatcher = new SongDispatcher();
		RegisterDispatcher registerDispatcher = new RegisterDispatcher();
		PlaylistDispatcher playlistDispatcher = new PlaylistDispatcher();

		//Determines if the JsonObject request contains an "objectName" property
		if(request.has("objectName")) {
			
			String dispatcherName = request.get("objectName").getAsString();
			switch(dispatcherName) {
				case "LoginDispatcher":
					loginFlag = true;
					break;
				case "SongDispatcher":
					songFlag = true;
					break;
				case "RegisterDispatcher":
					registerFlag = true;
					break;
				case "PlaylistDispatcher":
					playlistFlag = true;
					break;
			}
			
			
			if(loginFlag == true) {
				try {
					dispatcher.registerObject(loginDispatcher, request.get("objectName").toString());
					return (dispatcher.dispatch(request.toString())).getBytes();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else if(songFlag == true){
				try {
					dispatcher.registerObject(songDispatcher, request.get("objectName").toString());
					return (dispatcher.dispatch(request.toString())).getBytes();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else if(registerFlag == true) {
				try {
					dispatcher.registerObject(registerDispatcher, request.get("objectName").toString());
					return (dispatcher.dispatch(request.toString())).getBytes();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else if(playlistFlag == true) {
				try {
					dispatcher.registerObject(playlistDispatcher, request.get("objectName").toString());
					return (dispatcher.dispatch(request.toString())).getBytes();
				} catch (Exception e) {
					e.printStackTrace();
				}
			
			
			}
			
			
			
		}
		
		
		
		
		
		
		return null;

	}

}
