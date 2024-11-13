import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
<dependency>
    <groupId>se.michaelthelin.spotify</groupId>
    <artifactId>spotify-web-api-java</artifactId>
    <version>7.2.0</version>
</dependency>

public class SpotifyAPI {

    import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;

// ...

    String clientId = "your_client_id";
    String clientSecret = "your_client_secret";

    SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRedirectUri(SpotifyHttpManager.makeUri("your_redirect_uri"))
            .build();

    // Your client ID and secret from the Spotify Developer Dashboard
    private static final String clientId = "YOUR_SPOTIFY_CLIENT_ID";
    private static final String clientSecret = "YOUR_SPOTIFY_CLIENT_SECRET";

    public static void main(String[] args) {
        // Create a SpotifyApi instance
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .build();

        // Request an access token using Client Credentials Flow
        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();

        try {
            // Get credentials (Access Token)
            AuthorizationCodeCredentials credentials = clientCredentialsRequest.execute();
            System.out.println("Access Token: " + credentials.getAccessToken());

            // Set the access token for SpotifyApi
            spotifyApi.setAccessToken(credentials.getAccessToken());

            // Example API call: Get a track by ID
            Track track = spotifyApi.getTrack("3n3PpKnDCXnv5cmgPvUBgs").build().execute();
            System.out.println("Track Name: " + track.getName());

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
}


