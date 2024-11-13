public class SearchTrack {




    private static final String clientId = "YOUR_SPOTIFY_CLIENT_ID";
    private static final String clientSecret = "YOUR_SPOTIFY_CLIENT_SECRET";

    public static void main(String[] args) {
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .build();

        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();

        try {
            // Get access token
            AuthorizationCodeCredentials credentials = clientCredentialsRequest.execute();
            spotifyApi.setAccessToken(credentials.getAccessToken());

            // Create a search request to search for a track
            SearchRequest searchRequest = spotifyApi.searchTracks("Blinding Lights").build();
            Paging<Track> trackPaging = searchRequest.execute();

            // Print the first track from the search results
            if (trackPaging.getItems().length > 0) {
                Track track = trackPaging.getItems()[0];
                System.out.println("Track Name: " + track.getName());
                System.out.println("Artist: " + track.getArtists()[0].getName());
                System.out.println("Album: " + track.getAlbum().getName());
            } else {
                System.out.println("No results found for 'Blinding Lights'.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}


}
