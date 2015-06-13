package com.example.android.spotifystreamer.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Track;

/**
 * Created by vvellaichamy on 6/12/15.
 */
public class TrackAdapter extends ArrayAdapter<Track>{
    Context context;

    public TrackAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View TracksListView = inflater.inflate(R.layout.list_item_tracks, null);
        TextView AlbumName = (TextView) TracksListView.findViewById(R.id.textview_list_item_album);
        TextView TrackName = (TextView) TracksListView.findViewById(R.id.textview_list_item_track);
        ImageView AlbumArt = (ImageView) TracksListView.findViewById(R.id.imageview_album_art);

        Track track = this.getItem(position);
        AlbumName.setText(track.album.name);
        TrackName.setText(track.name);
        if(track.album.images.size() > 0) {
            Picasso.with(context).load(((Image) (track.album.images.toArray())[0]).url).into(AlbumArt);
        }
        return TracksListView;
    }
}