import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SpotifyTopHits {

    private static final String CLIENT_ID = "dc8340db3ae847fe8202ba1dc2492daa";  // Replace with your Spotify client ID
    private static final String CLIENT_SECRET = "7aaecf4f84d942c3a3549b5b1e09ab32";  // Replace with your Spotify client secret
    private static final String TOKEN_URL = "https://accounts.spotify.com/api/token";
    private static final String CHARTS_URL = "https://api.spotify.com/v1/playlists/37i9dQZEVXcZNbnp20O0IU";  // Replace with a valid playlist ID

    public static void main(String[] args) {
        try {
            String accessToken = getAccessToken();
            getTopHits(accessToken);
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Get the access token using HttpURLConnection
    private static String getAccessToken() throws Exception {
        String auth = CLIENT_ID + ":" + CLIENT_SECRET;
        String encodedAuth = new String(Base64.encodeBase64(auth.getBytes()));

        // Open the URL connection for the token request
        URL url = new URL(TOKEN_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Basic " + encodedAuth);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setDoOutput(true);

        // Set the body of the POST request to grant the access token
        String body = "grant_type=client_credentials";
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = body.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // Check the HTTP status code
        int statusCode = connection.getResponseCode();
        if (statusCode != 200) {
            throw new Exception("Failed to get access token. HTTP status code: " + statusCode);
        }

        // Read the response body
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }

        // Parse the response to extract the access token
        JSONObject jsonObject = new JSONObject(response.toString());
        return jsonObject.getString("access_token");
    }

    // Fetch the top hits from the playlist using the access token
    private static void getTopHits(String accessToken) throws Exception {
        URL url = new URL(CHARTS_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + accessToken);

        // Check the HTTP status code
        int statusCode = connection.getResponseCode();
        if (statusCode != 200) {
            throw new Exception("Failed to fetch playlist. HTTP status code: " + statusCode);
        }

        // Read the response body
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }

        // Parse the response to get the top hit songs
        JSONObject jsonObject = new JSONObject(response.toString());
        JSONArray items = jsonObject.getJSONArray("tracks").getJSONArray("items");

        System.out.println("Top 10 Hit Songs:");
        for (int i = 0; i < Math.min(10, items.length()); i++) {
            JSONObject trackObject = items.getJSONObject(i).getJSONObject("track");
            String trackName = trackObject.getString("name");
            String artistName = trackObject.getJSONArray("artists").getJSONObject(0).getString("name");
            System.out.println((i + 1) + ". " + trackName + " by " + artistName);
        }
    }
}
