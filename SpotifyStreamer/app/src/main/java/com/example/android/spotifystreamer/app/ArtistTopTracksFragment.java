package com.example.android.spotifystreamer.app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;


/**
 * A placeholder fragment containing a simple view.
 */
public class ArtistTopTracksFragment extends Fragment {

    TrackAdapter mTrackAdapter;

    public ArtistTopTracksFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        String ArtistID;
        View rootView = inflater.inflate(R.layout.fragment_artist_top_tracks, container, false);

        mTrackAdapter = new TrackAdapter(getActivity().getBaseContext(),
                R.layout.list_item_tracks,
                R.id.textview_list_item_artist
        );

        ListView artistList = (ListView) rootView.findViewById(R.id.listview_artist_top_tracks);
        artistList.setAdapter(mTrackAdapter);

        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            ArtistID = intent.getStringExtra(Intent.EXTRA_TEXT);
            FetchTracks fetchTracks = new FetchTracks();
            fetchTracks.execute(ArtistID);
        }

        return rootView;
    }

    public class FetchTracks extends AsyncTask<String, Void, List<Track>> {
        String ArtistID;
        Artist artist;

        @Override
        protected List<Track> doInBackground(String... params) {
            this.ArtistID = params[0];
            SpotifyApi api = new SpotifyApi();
            SpotifyService spotify = api.getService();
            artist = spotify.getArtist(this.ArtistID);
            Map<String, Object> country = new HashMap<>();
            country.put("country", "US");
            Tracks results = spotify.getArtistTopTrack(this.ArtistID, country);

            return results.tracks;
        }

        @Override
        protected void onPostExecute(List<Track> tracks) {
            Toast toast = Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);

            if (tracks != null && tracks.size() != 0) {
                toast.cancel();
                mTrackAdapter.clear();
                mTrackAdapter.addAll(tracks);
            } else {
                mTrackAdapter.clear();
                toast.setText("No Tracks available for the Artist: " + artist.name);
                toast.show();
            }
        }
    }
}
