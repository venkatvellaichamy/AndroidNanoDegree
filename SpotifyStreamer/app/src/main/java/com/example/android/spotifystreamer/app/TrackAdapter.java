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

    static class ViewHolder {
        public ImageView AlbumArt;
        public TextView TrackName;
        public TextView AlbumName;
    }

    public TrackAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View TrackRowView = convertView;

        if(TrackRowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            TrackRowView = inflater.inflate(R.layout.list_item_tracks, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.AlbumName = (TextView) TrackRowView.findViewById(R.id.textview_list_item_album);
            viewHolder.TrackName = (TextView) TrackRowView.findViewById(R.id.textview_list_item_track);
            viewHolder.AlbumArt = (ImageView) TrackRowView.findViewById(R.id.imageview_album_art);

            TrackRowView.setTag(viewHolder);
        }

        // Fill Data
        ViewHolder holder = (ViewHolder) TrackRowView.getTag();
        Track track = this.getItem(position);
        holder.AlbumName.setText(track.album.name);
        holder.TrackName.setText(track.name);
        if(track.album.images.size() > 0) {
            Picasso.with(context).load(((Image) (track.album.images.toArray())[0]).url).into(holder.AlbumArt);
        }
        return TrackRowView;
    }
}