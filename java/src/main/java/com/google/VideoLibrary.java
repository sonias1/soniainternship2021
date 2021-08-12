package com.google;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * A class used to represent a Video Library.
 */
class VideoLibrary {

  private final HashMap<String, Video> videos;

  VideoLibrary() {
    this.videos = new HashMap<>();
    try {
      File file = new File(this.getClass().getResource("/videos.txt").getFile());

      Scanner scanner = new Scanner(file);
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] split = line.split("\\|");
        String title = split[0].strip();
        String id = split[1].strip();
        List<String> tags;
        if (split.length > 2) {
          tags = Arrays.stream(split[2].split(",")).map(String::strip).collect(
              Collectors.toList());
        } else {
          tags = new ArrayList<>();
        }
        this.videos.put(id, new Video(title, id, tags));
      }
    } catch (FileNotFoundException e) {
      System.out.println("Couldn't find videos.txt");
      e.printStackTrace();
    }
  }

  List<String> getVideos() {
    ArrayList<String> T= new ArrayList<>();
    for (Video v : this.videos.values()) {
      String a = new String(v.getTitle());
      String b = new String(v.getVideoId());
      String c = new String(String.valueOf(v.getTags()));
      String video = a+ " " + "("+b+")"+" "+ c;
      video = video.replaceAll(",","");

      T.add(video);
    }
    // return new ArrayList<>(this.videos.values());
    return T ;
  }

  /**
   * Get a video by id. Returns null if the video is not found.
   */
  Video getVideo(String videoId) {
    return this.videos.get(videoId);
  }

  public boolean containsVideo(String videoId) {
      return this.videos.containsKey(videoId);
  }
}
