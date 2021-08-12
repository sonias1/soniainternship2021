package com.google;

import java.util.*;

/** A class used to represent a Playlist */
class VideoPlaylist {

    private String playlistName;
    private final ArrayList<Video> videos = new ArrayList();
    private Set<String> videoCase= new HashSet();
    List<String> videoList;
    LinkedHashMap<String, Video> v;

    String getName() {
        return this.playlistName;
    }

    public VideoPlaylist(String name) {
        videoList = new ArrayList<>();
        this.playlistName= name;
    }
    public List<String> getVideoList() {
        return new ArrayList<>(videoList);
    }

    public void addVideo(Video v) {
        this.videos.add(v);
        this.videoCase.add(v.getVideoId());
    }

    public boolean containsVideo(String videoId) {
        return this.videoCase.contains(videoId);
    }

    public void removeVideo(String videoID) {
        videoList.remove(videoID);
    }

    public void clearList() {
        videoList.clear();
    }
}
