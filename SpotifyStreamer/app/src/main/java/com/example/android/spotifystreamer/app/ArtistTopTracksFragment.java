package com.example.android.spotifystreamer.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


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
        Toast toast = Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);

        @Override
        protected List<Track> doInBackground(String... params) {
            this.ArtistID = params[0];
            SpotifyApi api = new SpotifyApi();
            SpotifyService spotify = api.getService();
            artist = spotify.getArtist(this.ArtistID);
            Map<String, Object> parameters = new HashMap<>();

            SharedPreferences sharedPrefs =
                    PreferenceManager.getDefaultSharedPreferences(getActivity());
            String country = sharedPrefs.getString(
                    getString(R.string.pref_country_key),
                    getString(R.string.pref_country_default_value));
            parameters.put("country", country);

            spotify.getArtistTopTrack(this.ArtistID, parameters, new Callback<Tracks>() {
                @Override
                public void success(Tracks tracks, Response response) {
                    final Tracks trcks = tracks;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showTracks(trcks.tracks);
                        }
                    });
                }

                @Override
                public void failure(final RetrofitError error) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showError(error.getMessage());
                        }
                    });
                }
            });

            return null;
        }

        private void showError(String message) {
            toast.cancel();
            toast.setText("Error: " + message);
            toast.show();
        }

        private void showTracks(List<Track> tracks) {
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
