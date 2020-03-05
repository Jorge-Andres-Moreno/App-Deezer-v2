package com.example.appdeezer1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

//import com.deezer.sdk.model.Playlist;
//import com.deezer.sdk.model.Track;
//import com.deezer.sdk.network.connect.DeezerConnect;
//import com.deezer.sdk.network.request.DeezerRequest;
//import com.deezer.sdk.network.request.DeezerRequestFactory;
//import com.deezer.sdk.network.request.event.DeezerError;
//import com.deezer.sdk.network.request.event.JsonRequestListener;
//import com.deezer.sdk.network.request.event.RequestListener;
//import com.deezer.sdk.player.TrackPlayer;
//import com.deezer.sdk.player.exception.TooManyPlayersExceptions;
//import com.deezer.sdk.player.networkcheck.WifiAndMobileNetworkStateChecker;

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

    //    final private String applicationID = "398524";
    //    private DeezerConnect deezerConnect;

    private Context context;

    public ArrayList<Playlist> playlists;
    public Playlist selectPlaylist;
//    public Track selectSong;

    public ControllerPlaylist(Context context) {
        this.context = context;
        connection(context);
//        Toast.makeText(context, deezerConnect != null ? "SUCESS CONECTION" : "FAILED CONECTION", Toast.LENGTH_SHORT).show();
        playlists = new ArrayList<>();
    }


    public void connection(Context context) {
//        deezerConnect = new DeezerConnect(context, applicationID);
    }

    public void searchNamePlaylist(final String playlist, final MyCallback callback) {

        try {
            String url = "https://api.deezer.com/search/playlist?q=" + playlist;
            OkHttpClient client = new OkHttpClient();

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
                    }

//            MainActivity.this.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    txtString.setText(myResponse);
//                }
//            });

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
//
//        RequestListener requestListener = new JsonRequestListener() {
//            @Override
//            public void onResult(Object o, Object o1) {
//                Toast.makeText(context, "onResult", Toast.LENGTH_SHORT).show();
//                playlists = (ArrayList<Playlist>) o;
//                callback.notify(o, MyCallback.SUCESS_CODE);
//
//            }
//
//            @Override
//            public void onUnparsedResult(String s, Object o) {
//                Toast.makeText(context, "onUnparsedResult", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onException(Exception e, Object o) {
//                Toast.makeText(context, "onException", Toast.LENGTH_SHORT).show();
//                callback.notify(e, MyCallback.SUCESS_CODE);
//            }
//        };
//        DeezerRequest deezerRequest = DeezerRequestFactory.requestSearchPlaylists(playlist);
//        deezerRequest.setId("searchPlaylist");
//        deezerConnect.requestAsync(deezerRequest, requestListener);
    }

    public void searchSongs(long playlistID, final MyCallback callback) {

//        RequestListener requestListener = new JsonRequestListener() {
//            @Override
//            public void onResult(Object o, Object o1) {
//                Toast.makeText(context, "onResult", Toast.LENGTH_SHORT).show();
//                selectPlaylist = (Playlist) o;
//                callback.notify(o, MyCallback.SUCESS_CODE);
//            }
//
//            @Override
//            public void onUnparsedResult(String s, Object o) {
//                Toast.makeText(context, "onUnparsedResult", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onException(Exception e, Object o) {
//                Toast.makeText(context, "onException", Toast.LENGTH_SHORT).show();
//                callback.notify(e, MyCallback.SUCESS_CODE);
//            }
//        };
//        DeezerRequest deezerRequest = DeezerRequestFactory.requestPlaylist(playlistID);
//        deezerRequest.setId("searchSongs");
//        deezerConnect.requestAsync(deezerRequest, requestListener);
    }

    public void searchSong(long songID, final MyCallback callback) {

//        RequestListener requestListener = new JsonRequestListener() {
//            @Override
//            public void onResult(Object o, Object o1) {
//                Toast.makeText(context, "onResult", Toast.LENGTH_SHORT).show();
//                selectSong = (Track) o;
//                callback.notify(o, MyCallback.SUCESS_CODE);
//            }
//
//            @Override
//            public void onUnparsedResult(String s, Object o) {
//                Toast.makeText(context, "onUnparsedResult", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onException(Exception e, Object o) {
//                Toast.makeText(context, "onException", Toast.LENGTH_SHORT).show();
//                callback.notify(e, MyCallback.SUCESS_CODE);
//            }
//        };
//        DeezerRequest deezerRequest = DeezerRequestFactory.requestTrack(songID);
//        deezerRequest.setId("searchSong");
//        deezerConnect.requestAsync(deezerRequest, requestListener);
    }

    public void playSong(Activity activity) {
//        TrackPlayer mTrackPlayer;
//
//        Uri webpage = Uri.parse(selectSong.getPreviewUrl());
//
//        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
//        activity.startActivity(webIntent);
//
//        try {
//            mTrackPlayer = new TrackPlayer(activity.getApplication(), deezerConnect, new WifiAndMobileNetworkStateChecker());
//        } catch (TooManyPlayersExceptions e) {
//            e.printStackTrace();
//        } catch (DeezerError e) {
//            e.printStackTrace();
//        }
    }
}
