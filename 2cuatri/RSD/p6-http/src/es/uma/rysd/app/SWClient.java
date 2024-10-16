/*
ALUMNO: Rubén Oliva Zamora
GRADO: Ingeniería del Software A
*/

package es.uma.rysd.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.Gson;

import es.uma.rysd.entities.*;

import javax.net.ssl.HttpsURLConnection;

public class SWClient {
	// Complete the application name
	private final String app_name = "joge";
	private final int year = 2024;

	private final String url_api = "https://swapi.dev/api/";

	// Auxiliary methods provided

	// Gets the URL of the resource id of the type resource
	public String buildResourceUrl(String resource, Integer id){
		return url_api + resource + "/" + id + "/";
	}

	// Given a resource URL, gets its ID
	public Integer extractIdFromUrl(String url){
		String[] parts = url.split("/");

		return Integer.parseInt(parts[parts.length-1]);
	}

	// Queries a resource and returns how many elements it has
	public int countResources(String resource) {
		// Handle possible exceptions appropriately

		// Create the corresponding URL: https://swapi.dev/api/{resource}/ replacing resource with the parameter
		URL url = null;
		try {
			url = new URL(url_api+resource+"/");
		} catch (MalformedURLException e) {
			System.err.println("La URL es incorrecta");
			e.printStackTrace();
			return -1;
		}
		// Create the connection from the URL
		HttpsURLConnection conexion = null;
		try {
			conexion = (HttpsURLConnection) url.openConnection();
		} catch (IOException e) {
			System.err.println("No he podido conectarme a " + url);
			e.printStackTrace();
			return -1;
		}
		// Add the headers User-Agent and Accept (see the statement)
		conexion.setRequestProperty("User-Agent",app_name + "-" + year);
		conexion.setRequestProperty("Accept","application/json");

		//Indicate that it is a GET request
		try {
			conexion.setRequestMethod("GET");
		} catch (ProtocolException e) {
			System.err.println("No se pudo conseguir hacer GET");
			e.printStackTrace();
			return -1;
		}

		//Check that the response code received is correct
		int code = 0;
		try {
			code = conexion.getResponseCode();
		} catch (IOException e) {
			System.err.println("No se pudo conseguir el código. Se devolvió " + code);
			e.printStackTrace();
			return -1;
		}

		if(code < 200 || code > 299){
			try {
				System.err.println("La respuesta fue inválida. Código " + code + " Mensaje: " + conexion.getResponseCode());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			return -1;
		}

		// Deserialize the response to ResourceCountResponse
		Gson parser = new Gson();
		InputStream in = null; // Get the InputStream from the connection
		try {
			in = conexion.getInputStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		ResourceCountResult c = parser.fromJson(new InputStreamReader(in), ResourceCountResult.class);

		conexion.disconnect();

		// Return the number of elements
		return c.count;
	}

	public Person getPerson(String urlname) {
		Person p = null;
		// Just in case it comes as http, we change it to https
		urlname = urlname.replaceAll("http:", "https:");

		// Handle possible exceptions appropriately
		// Create the connection from the received URL
		URL url = null;
		try {
			url = new URL(urlname);
		} catch (MalformedURLException e) {
			System.err.println("La URL es incorrecta");
			e.printStackTrace();
			return null;
		}


		HttpsURLConnection conexion = null;
		try {
			conexion = (HttpsURLConnection) url.openConnection();
		} catch (IOException e) {
			System.err.println("No he podido conectarme a " + url);
			e.printStackTrace();
			return null;
		}

		// Add the headers User-Agent and Accept (see the statement)
		conexion.setRequestProperty("User-Agent",app_name + "-" + year);
		conexion.setRequestProperty("Accept","application/json");

		// Indicate that it is a GET request
		try {
			conexion.setRequestMethod("GET");
		} catch (ProtocolException e) {
			System.err.println("No se pudo conseguir hacer GET");
			e.printStackTrace();
			return null;
		}

		// Check that the response code received is correct
		int code = 0;
		try {
			code = conexion.getResponseCode();
		} catch (IOException e) {
			System.err.println("No se pudo conseguir el código. Se devolvió " + code);
			e.printStackTrace();
			return null;
		}

		if(code < 200 || code > 299){
			try {
				System.err.println("La respuesta fue inválida. Código " + code + " Mensaje: " + conexion.getResponseCode());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			return null;
		}

		// Deserialize the response to Person
		Gson parser = new Gson();
		InputStream in = null; // Get the InputStream from the connection
		try {
			in = conexion.getInputStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		p = parser.fromJson(new InputStreamReader(in), Person.class);


		// For questions 2 and 3 (do not need to complete this for question 1)
		// From the URL in the homeworld field, get the planet data and store it in the homeplanet attribute
		if(p.homeworld!=null){
			p.homeplanet = getWorld(p.homeworld);
		}


		conexion.disconnect();
		return p;
	}

	public World getWorld(String urlname) {
		World w = null;
		// Just in case it comes as http, we change it to https
		urlname = urlname.replaceAll("http:", "https:");

		// Handle possible exceptions appropriately

		// Create the connection from the received URL
		URL url = null;
		try {
			url = new URL(urlname);
		} catch (MalformedURLException e) {
			System.err.println("La URL es incorrecta");
			e.printStackTrace();
			return null;
		}

		HttpsURLConnection conexion = null;
		try {
			conexion = (HttpsURLConnection) url.openConnection();
		} catch (IOException e) {
			System.err.println("No he podido conectarme a " + url);
			e.printStackTrace();
			return null;
		}

		// Add the headers User-Agent and Accept (see the statement)
		conexion.setRequestProperty("User-Agent",app_name + "-" + year);
		conexion.setRequestProperty("Accept","application/json");

		// Indicate that it is a GET request
		try {
			conexion.setRequestMethod("GET");
		} catch (ProtocolException e) {
			System.err.println("No se pudo conseguir hacer GET");
			e.printStackTrace();
			return null;
		}

		// Check that the response code received is correct
		int code = 0;
		try {
			code = conexion.getResponseCode();
		} catch (IOException e) {
			System.err.println("No se pudo conseguir el código. Se devolvió " + code);
			e.printStackTrace();
			return null;
		}

		if(code < 200 || code > 299){
			try {
				System.err.println("La respuesta fue inválida. Código " + code + " Mensaje: " + conexion.getResponseCode());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			return null;
		}

		// Deserialize the response to Planet
		Gson parser = new Gson();
		InputStream in = null; // Get the InputStream from the connection
		try {
			in = conexion.getInputStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		w = parser.fromJson(new InputStreamReader(in), World.class);

		conexion.disconnect();
		return w;
	}

	public Person searchPersonByName(String name){
		Person p = null;
		// Handle possible exceptions appropriately

		// Create the connection from the URL (url_api + name processed - see the statement)
		URL url = null;
		try {
			url = new URL(url_api + "people/?search=" + URLEncoder.encode(name, "utf-8"));
		} catch (MalformedURLException e) {
			System.out.println("La URL es incorrecta");
			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e) {	// Como ahora hay encoding, hay que controlar también este error
			e.printStackTrace();
		}

		HttpsURLConnection conexion = null;
		try {
			conexion = (HttpsURLConnection) url.openConnection();
		} catch (IOException e) {
			System.err.println("No he podido conectarme a " + url);
			e.printStackTrace();
			return null;
		}

		// Add the headers User-Agent and Accept (see the statement)
		conexion.setRequestProperty("User-Agent",app_name + "-" + year);
		conexion.setRequestProperty("Accept","application/json");

		// Indicate that it is a GET request
		try {
			conexion.setRequestMethod("GET");
		} catch (ProtocolException e) {
			System.err.println("No se pudo conseguir hacer GET");
			e.printStackTrace();
			return null;
		}
		// Check that the response code received is correct
		int code = 0;
		try {
			code = conexion.getResponseCode();
		} catch (IOException e) {
			System.err.println("No se pudo conseguir el código. Se devolvió " + code);
			e.printStackTrace();
			return null;
		}

		if(code < 200 || code > 299){
			try {
				System.err.println("La respuesta fue inválida. Código " + code + " Mensaje: " + conexion.getResponseCode());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			return null;
		}

		// Deserialize the response to SearchResponse -> Use the first position of the array as the result
		Gson parser = new Gson();
		InputStream in = null; // Get the InputStream from the connection
		try {
			in = conexion.getInputStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		QueryResponse query = parser.fromJson(new InputStreamReader(in), QueryResponse.class);
		if(query.results != null && query.count>0){
			p = query.results[0];
			// For questions 2 and 3 (do not need to complete this for question 1)
			// From the URL in the homeworld field, get the planet data and store it in the homeplanet attribute
			if(p.homeworld!=null){
				p.homeplanet = getWorld(p.homeworld);
			}
		}

		conexion.disconnect();

		return p;
	}

	public SpaceShip getSpaceShip(String urlname){
		SpaceShip s = null;
		// Just in case it comes as http, we change it to https
		urlname = urlname.replaceAll("http:", "https:");

		// Handle possible exceptions appropriately
		// Create the connection from the received URL
		URL url = null;
		try {
			url = new URL(urlname);
		} catch (MalformedURLException e) {
			System.err.println("La URL es incorrecta");
			e.printStackTrace();
			return null;
		}

		HttpsURLConnection conexion = null;
		try {
			conexion = (HttpsURLConnection) url.openConnection();
		} catch (IOException e) {
			System.err.println("No he podido conectarme a " + url);
			e.printStackTrace();
			return null;
		}

		// Add the headers User-Agent and Accept
		conexion.setRequestProperty("User-Agent", app_name + "-" + year);
		conexion.setRequestProperty("Accept", "application/json");

		// Indicate that it is a GET request
		try {
			conexion.setRequestMethod("GET");
		} catch (ProtocolException e) {
			System.err.println("No se pudo conseguir hacer GET");
			e.printStackTrace();
			return null;
		}

		// Check that the response code received is correct
		int code = 0;
		try {
			code = conexion.getResponseCode();
		} catch (IOException e) {
			System.err.println("No se pudo conseguir el código. Se devolvió " + code);
			e.printStackTrace();
			return null;
		}

		if (code < 200 || code > 299) {
			/*
			try {
				//System.err.println("	La respuesta fue inválida. Código " + code + " Mensaje: " + conexion.getResponseMessage());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			*/
			return null;
		}

		// Deserialize the response to SpaceShip
		Gson parser = new Gson();
		InputStream in = null;
		try {
			in = conexion.getInputStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		s = parser.fromJson(new InputStreamReader(in), SpaceShip.class);

		conexion.disconnect();
		return s;
	}
}
