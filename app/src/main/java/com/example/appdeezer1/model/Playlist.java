package com.example.appdeezer1.model;

import java.util.ArrayList;

public class Playlist {
    public String id;
    public String title;
    public int nb_tracks;
    public String user_name;
    public String picture_medium;
    public String picture_big;
    public String fans;
    public String creation_date;
    public String description;

    public ArrayList<Track> tracks = new ArrayList<>();
}
