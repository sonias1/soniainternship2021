package com.google;
import java.util.*;
import java.util.stream.Collectors;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;
  private Video nowPlaying;
  private boolean isPaused;
  private boolean playlistCreated;
  private HashMap<String, VideoPlaylist> playlists = new HashMap();


  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  public void showAllVideos() {
    System.out.println("Here's a list of all available videos:");
    List<String> videos = this.videoLibrary.getVideos();
    java.util.Collections.sort(videos);
    for (String video : videos) {
      System.out.println(video);
    }
  }

  public void playVideo(String videoId) {
    if (videoId == null || !videoLibrary.containsVideo(videoId)) {
      System.out.println("Cannot play video: Video does not exist");
      return;
    }
    Video video = this.videoLibrary.getVideo(videoId);
    if (nowPlaying != null) {
      System.out.println("Stopping video: " + nowPlaying.getTitle());
    }
    nowPlaying = video;
    isPaused = false;
    System.out.println("Playing video: " + nowPlaying.getTitle());
  }

  public void stopVideo() {
    Video video = nowPlaying;
    if (video != null) {
      System.out.println("Stopping video: " + video.getTitle());
      this.nowPlaying = null;
      isPaused = true;
    } else {
      System.out.println("Cannot stop video: No video is currently playing");
    }
  }

  public void playRandomVideo() {
    Video video = nowPlaying;
    if (video != null) {
      this.stopVideo();
    }
    isPaused = false;
    List<String> videos = this.videoLibrary.getVideos();
    Random rand = new Random();
    String random = videos.get(rand.nextInt(videos.size()));
    System.out.println("Playing video: " + random);

  }

  public void pauseVideo() {
    Video video = nowPlaying;
    if (video == null) {
      System.out.println("Cannot pause video: No video is currently playing");
    } else if (isPaused == false) {
      isPaused = true;
      System.out.println("Pausing video: " + video.getTitle());
    } else {
      System.out.println("Video already paused: " + video.getTitle());

    }
  }

  public void continueVideo() {
    Video video = nowPlaying;
    if (video == null) {
      System.out.println("Cannot continue video: No video is currently playing");
    } else if (isPaused == true) {
      isPaused = false;
      System.out.println("Continuing video: " + video.getTitle());
    } else {
      System.out.println("Cannot continue video: Video is not paused");
    }
  }

  public void showPlaying() {
    Video video = nowPlaying;
    if (video == null) {
      System.out.println("No video is currently playing");
      return;
    }
    String str = video.getTitle() + " " + "(" + video.getVideoId() + ")" + " " + video.getTags();
    str = str.replaceAll(",", "");
    if (isPaused == true) {
      System.out.println("Currently playing: " + str + " - PAUSED");
    } else {
      System.out.println("Currently playing: " + str);
    }
  }

  public void createPlaylist(String playlistName) {
    String playlistCase = playlistName.toLowerCase(Locale.ROOT);
    if(this.playlists.containsKey(playlistCase)) {
      playlistCreated = false;
      System.out.println("Cannot create playlist: A playlist with the same name already exists");
    } else {
      playlistCreated = true;
      VideoPlaylist newPlaylist = new VideoPlaylist(playlistName);
      System.out.println("Successfully created new playlist: " + playlistName);
      this.playlists.put(playlistCase, newPlaylist);
    }
    return;
  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    String playlistCase = playlistName.toLowerCase(Locale.ROOT);
    Video videoToAdd = this.videoLibrary.getVideo(videoId);
    if (!this.playlists.containsKey(playlistCase)) {
      System.out.println("Cannot add video to " + playlistName + ": Playlist does not exist");
    } else if(videoToAdd == null) {
      System.out.println("Cannot add video to " + playlistName + ": Video does not exist");
    } else {
      VideoPlaylist playlist = this.playlists.get(playlistCase);
      if (playlist.containsVideo(videoId)) {
        System.out.println("Cannot add video to " + playlistName + ": Video already added");
      } else {
        playlist.addVideo(videoToAdd);
        System.out.println("Added video to " + playlistName +": " + videoToAdd.getTitle());
      }
    }
  }

  public void showAllPlaylists() {
    if (playlists.isEmpty()) {
      System.out.println("No playlists exist yet");
    } else {
      System.out.println("Showing all playlists:");
      for (VideoPlaylist playlist: this.playlists.values()) {
        System.out.println("  " + playlist.getName());
      }
    }
  }

  public void showPlaylist(String playlistName) {
    if (!this.playlists.containsKey(playlistName.toLowerCase(Locale.ROOT))) {
      System.out.println("Cannot show playlist " + playlistName + ": Playlist does not exist");
    } else {
      System.out.println("Showing playlist: " + playlistName);
      VideoPlaylist playlist = this.playlists.get(playlistName.toLowerCase(Locale.ROOT));
      List<String> videoList = playlist.getVideoList();
      if (videoList.isEmpty()) {
        System.out.println("  No videos here yet");
      } else {
        for (String videoID: videoList) {
          Video video = this.videoLibrary.getVideo(videoID);
          System.out.print("  " + video);
        }
      }
    }
  }

  public void removeFromPlaylist(String playlistName, String videoId) {
    if (!this.playlists.containsKey(playlistName.toLowerCase(Locale.ROOT))) {
      System.out.println("Cannot remove video from " + playlistName + ": Playlist does not exist");
    } else if (this.videoLibrary.getVideo(videoId) == null) {
      System.out.println("Cannot remove video from " + playlistName + ": Video does not exist");
    } else {
      VideoPlaylist playlist = this.playlists.get(playlistName.toLowerCase(Locale.ROOT));
      if (!playlist.containsVideo(videoId)) {
        System.out.println("Cannot remove video from " + playlistName + ": Video is not in playlist");
      } else {
        playlist.removeVideo(videoId);
        System.out.println("Removed video from " + playlistName + ": " + this.videoLibrary.getVideo(videoId).getTitle());
      }
    }
  }

  public void clearPlaylist(String playlistName) {
    if (!this.playlists.containsKey(playlistName.toLowerCase(Locale.ROOT))) {
      System.out.println("Cannot clear playlist " + playlistName + ": Playlist does not exist");
    } else {
      this.playlists.get(playlistName.toLowerCase(Locale.ROOT)).clearList();
      System.out.println("Successfully removed all videos from " + playlistName);
    }
  }

  public void deletePlaylist(String playlistName) {
    if (!this.playlists.containsKey(playlistName.toLowerCase(Locale.ROOT))) {
      System.out.println("Cannot delete playlist " + playlistName + ": Playlist does not exist");
    } else {
      this.playlists.remove(playlistName.toLowerCase(Locale.ROOT));
      System.out.println("Deleted playlist: " + playlistName);
    }
  }

  public void searchVideos(String searchTerm) {
    System.out.println("searchVideos needs implementation");
  }

  public void searchVideosWithTag(String videoTag) {
    System.out.println("searchVideosWithTag needs implementation");
  }

  public void flagVideo(String videoId) {
    System.out.println("flagVideo needs implementation");
  }

  public void flagVideo(String videoId, String reason) {
    System.out.println("flagVideo needs implementation");
  }

  public void allowVideo(String videoId) {
    System.out.println("allowVideo needs implementation");
  }
}