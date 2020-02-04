import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPService {

	public static String call(String urlCalled) throws IOException {
	
		URL url = new URL(urlCalled); // URL à appeller
		HttpURLConnection con = (HttpURLConnection) url.openConnection(); // Ouverture connection
		
		// Configuration de la requête
		con.setRequestMethod("GET");
		con.setConnectTimeout(5000);
		con.setReadTimeout(5000);
		con.setInstanceFollowRedirects(false); // Permet d'accepter les redirections s'il y en a
		
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
		
		return content.toString();
	}
}
