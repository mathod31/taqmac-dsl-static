package fr.taqmac.services;

import fr.taqmac.utils.ResponseHttpUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


public class HTTPService {

	public static final String GET = "GET";
	public static final String POST = "POST";
	public static final String PUT = "PUT";
	public static final String DELETE = "DELETE";
	
	
	/**
	 * Appel API sans argument
	 * @param urlCalled : Adresse URL (HTTP)
	 * @param requestMethod : Type de methode (HTTPService.POST, PUT, DELETE, GET)
	 * @return TupleHttpUtils : Réponse de la requête body, code de retour
	 * @throws IOException
	 */
	public static ResponseHttpUtils call(String urlCalled, String requestMethod) throws IOException {
	
		return call(urlCalled, requestMethod, "");
	}
	
	/**
	 * Appel API, précision de la méthode utilisé et arguments dans un tableau de string
	 * @param urlCalled : Adresse URL (HTTP)
	 * @param requestMethod : Type de méthode (HTTPService.POST, PUT, DELETE, GET)
	 * @param requestArgs : Tableau de String (ex : [a=alpha,b=beta])
	 * @return Réponse de la requête body, code de retour
	 * @throws IOException
	 */
	public static ResponseHttpUtils call(String urlCalled, String requestMethod, String[] requestArgs) throws IOException {
		
		String argString = "";
		for (int i=0; i<requestArgs.length; i++) {
			argString += requestArgs[i];
			if(i+1 < requestArgs.length) {
				argString += "&";
			}
		}
		return call(urlCalled, requestMethod, argString);
	}
	
	/**
	 * Appel API, précision de la méthode utilisé et arguments
	 * @param urlCalled : Adresse URL (HTTP)
	 * @param requestMethod (HTTPService.POST, PUT, DELETE, GET)
	 * @param requestArgs : String (ex : a=apha&b=beta)
	 * @return Réponse de la requête body, code de retour
	 * @throws IOException
	 */	
	public static ResponseHttpUtils call(String urlCalled, String requestMethod, String requestArgs) throws IOException {

        URL url = new URL(urlCalled); // URL à appeller
		HttpURLConnection con = (HttpURLConnection) url.openConnection(); // Ouverture connection
		
		// Configuration de la requête
		con.setRequestMethod(requestMethod);
		con.setRequestProperty("user-Agent", "Java client");
		con.setConnectTimeout(5000);
		con.setReadTimeout(5000);
		con.setInstanceFollowRedirects(false); // Permet d'accepter les redirections s'il y en a
		
		// Si besoins d'envoyer des arguments dans la requête :
		if (requestArgs != "") {
			con.setDoOutput(true); // Précise que l'on ajoute des arguments dans la requête (dans le body)
			byte[] postData = requestArgs.getBytes(StandardCharsets.UTF_8);
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }
		}
		
		// Gère la redirection
		int status = con.getResponseCode();
		if (status == HttpURLConnection.HTTP_MOVED_TEMP || status == HttpURLConnection.HTTP_MOVED_PERM) {
			String location = con.getHeaderField("Location");
			URL newUrl = new URL(location);
			con = (HttpURLConnection) newUrl.openConnection();
		}
		
		// Récupère la donnée de sortie
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
		    content.append(inputLine);
		}
		in.close();
		
		// Fermeture de la connection
		con.disconnect();
		
		return new ResponseHttpUtils(content.toString(), status);
	}

	public static ResponseEntity<String> createResponse(String content, HttpStatus status){
		HttpHeaders headers = new HttpHeaders();
		headers.add("Access-Control-Allow-Origin","*");
		return new ResponseEntity<>(content,headers, status);
	}
}
