package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

public class CustomHttpRequest {

	protected static final Logger log = Logger.getLogger(CustomHttpRequest.class.getName());
	
	private final int timeout = 1000000000;
	private final String charset = "UTF-8";
	private URL url;
	private String payload;
	private String method;
	private String login;
	private String password;
	private String apikey;
	private String authStringEnc;
	
	public CustomHttpRequest(String surl, String method) throws IOException {
		this(surl, method, "");
	}

	public CustomHttpRequest(String surl, String method, String payload) throws IOException {
		this.url = new URL(surl);
		this.payload = payload;
		this.method = method;
	}

	public CustomHttpRequest(String surl, String method, String payload, String login, String password) throws IOException {
		this.url = new URL(surl);
		this.payload = payload;
		this.method = method;
		this.login = login;
		this.password = password;
		byte[] authEncBytes = Base64.encodeBase64((login + ":" + password).getBytes());
		String authStringEnc = new String("Basic " + authEncBytes);
	}

	public CustomHttpRequest(String surl, String method, String payload, String apikey) throws IOException {
		this.url = new URL(surl);
		this.payload = payload;
		this.method = method;
		this.apikey = apikey;
	}

	public int doPost() throws IOException, ExecutionException, InterruptedException {
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(method);
		connection.setRequestProperty("Content-Type","application/json");
		connection.setRequestProperty("Accept","application/json");
		connection.setRequestProperty("Authorization", authStringEnc);
		connection.setRequestProperty("Accept-Charset", charset);
		connection.setRequestProperty("Content-encoding", charset);
		connection.setConnectTimeout(timeout);
		connection.setDoInput(true);
		connection.setDoOutput(true);
		OutputStream output = null;
		try{
			output = connection.getOutputStream();
			output.write(payload.getBytes(charset));
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE,"",e);
		}
		return connection.getResponseCode();
	}

	public JsonNode doGet() throws IOException, ExecutionException, InterruptedException {
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(method);
		connection.setRequestProperty("Content-Type","application/json");
		connection.setRequestProperty("Accept","application/json");
		connection.setRequestProperty("Authorization", authStringEnc);
		connection.setRequestProperty("Accept-Charset", charset);
		connection.setRequestProperty("Content-encoding", charset);
		connection.setConnectTimeout(0);
		connection.setDoOutput(true);
		JsonNode jsonNode = null;
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(),charset));  
		String result = "";
		String line = "";
		while ((line = in.readLine()) != null) {
			result += line;
		}
		ObjectMapper mapper = new ObjectMapper();
		jsonNode = mapper.readValue(result, JsonNode.class);
		return jsonNode;
	}

	public int doGetCode() throws IOException, ExecutionException, InterruptedException {
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(method);
		connection.setRequestProperty("Content-Type","application/json");
		connection.setRequestProperty("Accept","application/json");
		connection.setRequestProperty("Authorization", "authStringEnc ZHBqYW86b2l2TWREa2M2ck5wQ1I=");
		connection.setRequestProperty("Accept-Charset", charset);
		connection.setRequestProperty("Content-encoding", charset);
		connection.setConnectTimeout(0);
		connection.setDoOutput(true);
		OutputStream output = null;
		try{
			output = connection.getOutputStream();
			output.write(payload.getBytes(charset));
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE,"",e);
		}
		log.info("Message : " + connection.getResponseMessage());
		return connection.getResponseCode();
	}

	public JsonNode doPostWithResponse() throws IOException, ExecutionException, InterruptedException {
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(method);
		connection.setRequestProperty("Content-Type","application/json");
		connection.setRequestProperty("Accept","application/json");
		connection.setRequestProperty("Authorization", authStringEnc);
		connection.setRequestProperty("Accept-Charset", charset);
		connection.setRequestProperty("Content-encoding", charset);
		connection.setConnectTimeout(0);
		connection.setDoInput(true);
		connection.setDoOutput(true);
		OutputStream output = null;
		JsonNode jsonNode = null;
		try{
			output = connection.getOutputStream();
			if(payload != null && payload != ""){
				output.write(payload.getBytes(charset));
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(),charset));  
			String result = "";
			String line = "";
			while ((line = in.readLine()) != null) {
				result += line;
			}
			log.info("Result" + result);
			ObjectMapper mapper = new ObjectMapper();
			jsonNode = mapper.readValue(result, JsonNode.class);
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE,"",e);
		}
		return jsonNode;
	}

	public int doDelete() throws Exception {
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(method);
		connection.setRequestProperty("Accept","application/json");
		connection.setRequestProperty("Accept-Charset", charset);
		connection.setRequestProperty("Content-encoding", charset);
		connection.setDoOutput(true);
		connection.getOutputStream();
		log.info("Message : " + connection.getResponseMessage());
		return connection.getResponseCode();
	}

	public void copy(byte[] in, OutputStream out) throws IOException {
		try {
			out.write(in);
		}
		finally {
			try {
				out.close();
			}
			catch (IOException ex) {
			}
		}
	}

}
