package com.example.android.spotifystreamer.app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    View rootView;
    ArtistAdapter mArtistAdapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mArtistAdapter = new ArtistAdapter(getActivity().getBaseContext(),
                R.layout.list_item_artist,
                R.id.textview_list_item_artist
        );

        EditText txtArtistName = (EditText) rootView.findViewById(R.id.txtArtistName);
        txtArtistName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                FetchArtists fetchArtists = new FetchArtists();
                fetchArtists.execute(s.toString());
            }
        });

        ListView artistList = (ListView) rootView.findViewById(R.id.listview_artist_search_result);
        artistList.setAdapter(mArtistAdapter);
        artistList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent tracksIntent = new Intent(getActivity(), ArtistTopTracks.class)
                        .putExtra(Intent.EXTRA_TEXT, (mArtistAdapter.getItem(position)).id);
                startActivity(tracksIntent);
            }
        });

        return rootView;
    }

    public class FetchArtists extends AsyncTask<String, Void, List<Artist>> {
        Toast toast = Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);
        String ArtistName = "";

        @Override
        protected List<Artist> doInBackground(String... params) {
            ArtistName = params[0];

            try {
                SpotifyApi api = new SpotifyApi();
                SpotifyService spotify = api.getService();

                spotify.searchArtists(ArtistName, new Callback<ArtistsPager>() {
                    @Override
                    public void success(ArtistsPager artistsPager, Response response) {
                        final ArtistsPager mArtistsPager = artistsPager;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showArtists(mArtistsPager.artists.items);
                            }
                        });
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        final RetrofitError mError = error;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showErrorMessage(mError.getMessage());
                            }
                        });
                    }
                });

            } catch (Exception e) {
                Log.e("", "Could not fetch the Artist: " + ArtistName);
            }

            return null;
        }

        private void showArtists(List<Artist> Artists) {
            if (Artists != null && Artists.size() != 0) {
                toast.cancel();
                mArtistAdapter.clear();
                mArtistAdapter.addAll(Artists);
            } else {
                mArtistAdapter.clear();
                toast.setText("Could not fetch the Artist: " + ArtistName);
                toast.show();
            }
        }

        private void showErrorMessage(String message) {
            toast.setText("Error: " + message);
            toast.show();
        }
    }
}
