package com.example.appdeezer1.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.example.appdeezer1.model.Playlist;
import com.example.appdeezer1.model.Track;
import com.example.appdeezer1.utils.MyCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ControllerPlaylist {

    private Context context;

    public ArrayList<Playlist> playlists;
    public Playlist selectPlaylist;
    private OkHttpClient client;
    public Track selectSong;

    public ControllerPlaylist(Context context) {
        this.context = context;
        playlists = new ArrayList<>();
        connection();
    }

    public void connection() {
        client = new OkHttpClient();
    }

    public void searchNamePlaylist(final String playlist, final MyCallback callback) {

        try {

            String url = "https://api.deezer.com/search/playlist?q=" + playlist;

            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    call.cancel();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    try {

                        JSONArray data = (new JSONObject(response.body().string())).getJSONArray("data");
                        playlists = new ArrayList<>();
                        for (int i = 0; i < data.length(); i++) {

                            JSONObject json = data.getJSONObject(i);
                            Playlist item = new Playlist();
                            item.id = json.getString("id");
                            item.nb_tracks = json.getInt("nb_tracks");
                            item.picture_big = json.getString("picture_big");
                            item.picture_medium = json.getString("picture_medium");
                            item.title = json.getString("title");

                            json = json.getJSONObject("user");
                            item.user_name = json.getString("name");

                            playlists.add(item);

                        }
                        callback.notify(playlists, MyCallback.SUCESS_CODE);

                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.notify(null, MyCallback.WRONG_CODE);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            callback.notify(null, MyCallback.WRONG_CODE);
        }

    }

    public void searchPlaylist(String playlistID, final MyCallback callback) {

        try {

            String url = "https://api.deezer.com/playlist/" + playlistID;

            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    call.cancel();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    try {

                        JSONObject jsonObject = new JSONObject(response.body().string());
                        selectPlaylist = new Playlist();
                        selectPlaylist.id = jsonObject.getString("id");
                        selectPlaylist.nb_tracks = jsonObject.getInt("nb_tracks");
                        selectPlaylist.picture_big = jsonObject.getString("picture_big");
                        selectPlaylist.picture_medium = jsonObject.getString("picture_medium");
                        selectPlaylist.title = jsonObject.getString("title");
                        selectPlaylist.fans = jsonObject.getString("fans");
                        selectPlaylist.creation_date = jsonObject.getString("creation_date").split(" ")[0];
                        selectPlaylist.description = jsonObject.getString("description");
                        selectPlaylist.user_name = jsonObject.getJSONObject("creator").getString("name");

                        JSONArray data = jsonObject.getJSONObject("tracks").getJSONArray("data");

                        for (int i = 0; i < data.length(); i++) {

                            JSONObject json = data.getJSONObject(i);
                            Track track = new Track();
                            track.id = json.getString("id");
                            track.title = json.getString("title");
                            track.duration = json.getInt("duration");
                            track.artist_name = json.getJSONObject("artist").getString("name");
                            track.album_cover = json.getJSONObject("album").getString("cover");

                            selectPlaylist.tracks.add(track);
                        }
                        callback.notify(selectPlaylist, MyCallback.SUCESS_CODE);

                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.notify(null, MyCallback.WRONG_CODE);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            callback.notify(null, MyCallback.WRONG_CODE);
        }
    }

    public void searchSong(String songID, final MyCallback callback) {
        try {

            String url = "https://api.deezer.com/track/" + songID;

            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    call.cancel();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        selectSong = new Track();
                        selectSong.id = jsonObject.getString("id");
                        selectSong.title = jsonObject.getString("title");
                        selectSong.artist_name = jsonObject.getJSONObject("artist").getString("name");
                        selectSong.album_cover = jsonObject.getJSONObject("album").getString("cover_big");
                        selectSong.album_title = jsonObject.getJSONObject("album").getString("title");
                        selectSong.preview = jsonObject.getString("preview");
                        selectSong.duration = jsonObject.getInt("duration");

                        callback.notify(selectPlaylist, MyCallback.SUCESS_CODE);

                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.notify(null, MyCallback.WRONG_CODE);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            callback.notify(null, MyCallback.WRONG_CODE);
        }

    }

    public void playSong(Activity activity) {
        Uri uri = Uri.parse(selectSong.preview);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        activity.startActivity(intent);
    }
}
