package com.example.appdeezer1.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdeezer1.R;
import com.example.appdeezer1.utils.MyCallback;
import com.squareup.picasso.Picasso;

public class PlaylistActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private SongsAdapter adapter;
    private Toolbar toolbar;
    private ControllerPlaylist controller;
    private TextView count_songs;
    private TextView fans;
    private TextView name_playlist;
    private TextView description_playlist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        controller = new ControllerPlaylist(this);

        count_songs = findViewById(R.id.count_items);
        name_playlist = findViewById(R.id.name_playlist);
        description_playlist = findViewById(R.id.description_playlist);
        fans = findViewById(R.id.count_fans);

        // Init recycler view with custom adapter for items in list
        adapter = new SongsAdapter(this, controller);
        recycler = findViewById(R.id.recycler_songs);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);

        //Customize App toolbar
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.GRAY);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        controller.searchPlaylist("" + getIntent().getExtras().get("playlistID"), new MyCallback() {
            @Override
            public void notify(Object result, int statusCode) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        fans.setText("Fans: " + controller.selectPlaylist.fans);
                        count_songs.setText(" Canciones: " + controller.selectPlaylist.tracks.size());
                        name_playlist.setText(controller.selectPlaylist.title);
                        description_playlist.setText(controller.selectPlaylist.description);
                        Picasso.get().load(controller.selectPlaylist.picture_big).into((ImageView) findViewById(R.id.banner_image));

                        adapter.notifyDataSetChanged();
                    }
                });


            }
        });
        findViewById(R.id.button_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.button_back)
                    onBackPressed();
            }
        });
    }
}
