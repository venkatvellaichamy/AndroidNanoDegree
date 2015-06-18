package com.example.android.spotifystreamer.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Image;

/**
 * Created by vvellaichamy on 6/8/15.
 */
public class ArtistAdapter extends ArrayAdapter<Artist> {
    Context context;

    static class ViewHolder {
        public ImageView ArtistImage;
        public TextView ArtistName;
    }

    public ArtistAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View ArtistRowView = convertView;
        if(ArtistRowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ArtistRowView = inflater.inflate(R.layout.list_item_artist, null);

            // Configure View Holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.ArtistName = (TextView) ArtistRowView.findViewById(R.id.textview_list_item_artist);
            viewHolder.ArtistImage = (ImageView) ArtistRowView.findViewById(R.id.imageview_artist_image);
            ArtistRowView.setTag(viewHolder);
        }

        // Fill Data
        Artist artist = this.getItem(position);
        ViewHolder holder = (ViewHolder) ArtistRowView.getTag();
        holder.ArtistName.setText(artist.name);
        if(artist.images.size() > 0) {
            Picasso.with(context).load(((Image) (artist.images.toArray())[0]).url).into(holder.ArtistImage);
        }
        return ArtistRowView;
    }
}