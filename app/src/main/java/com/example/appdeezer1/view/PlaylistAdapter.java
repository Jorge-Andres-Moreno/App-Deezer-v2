package com.example.appdeezer1.view;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdeezer1.R;
import com.squareup.picasso.Picasso;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistHolder> {

    private Activity context;
    private ControllerPlaylist controllerPlaylist;

    PlaylistAdapter(Activity context, ControllerPlaylist controllerPlaylist) {
        this.context = context;
        this.controllerPlaylist = controllerPlaylist;
    }

    @NonNull
    @Override
    public PlaylistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_playlist, parent, false);
        return new PlaylistHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistHolder holder, int position) {
        Picasso.get().load(controllerPlaylist.playlists.get(position).picture_medium).into(holder.imagePlaylist);
        holder.name_playlist.setText("Nombre de la lista: " + controllerPlaylist.playlists.get(position).title);
        holder.name_created_user.setText("Nombre del creador: " + controllerPlaylist.playlists.get(position).user_name);
        holder.count_items.setText("Numero de canciones: " + controllerPlaylist.playlists.get(position).nb_tracks);
        holder.pos = position;
    }

    @Override
    public int getItemCount() {
        Log.i("getItemCount()", controllerPlaylist.playlists.size() + "");
        return controllerPlaylist.playlists.size();
    }

    public class PlaylistHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        int pos;
        ImageView imagePlaylist;
        TextView name_playlist;
        TextView name_created_user;
        TextView count_items;

        public PlaylistHolder(@NonNull View itemView) {
            super(itemView);
            imagePlaylist = itemView.findViewById(R.id.image_playlist);
            name_playlist = itemView.findViewById(R.id.name_playlist);
            name_created_user = itemView.findViewById(R.id.name_created_user);
            count_items = itemView.findViewById(R.id.count_items);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.card) {
                Intent in = new Intent(context, PlaylistActivity.class);
                in.putExtra("playlistID", controllerPlaylist.playlists.get(pos).id);
                context.startActivity(in);
            }
        }
    }
}
