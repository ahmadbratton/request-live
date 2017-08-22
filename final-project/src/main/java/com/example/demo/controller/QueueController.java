package com.example.demo.controller;

import com.example.demo.model.Playlist;
import com.example.demo.model.Queue;
import com.example.demo.model.Show;
import com.example.demo.model.Song;
import com.example.demo.repository.PlaylistRepository;
import com.example.demo.repository.QueueRepository;
import com.example.demo.repository.ShowRepository;
import com.example.demo.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by duhlig on 8/20/17.
 */
@Controller
@RequestMapping("/api")
public class QueueController {
    @Autowired
    QueueRepository queueRepo;

    @Autowired
    SongRepository songRepo;

    @Autowired
    ShowRepository showRepo;

    @Autowired
    PlaylistRepository playlistRepo;

    @PostMapping("/{showId}/{songId}")
    public String addSong(@PathVariable int showId, @PathVariable int songId) {
        Show currentShow = showRepo.findOne(showId);

        Song selectedSong = songRepo.findOne(songId);
        Queue showQueue = currentShow.getSongQueue();
        if(showQueue == null) {
            Queue newQueue = new Queue();

//        newQueue.setShow(currentShow);
            List<Song> songQueue = new ArrayList<>();
            songQueue.add(selectedSong);
            newQueue.setSongs(songQueue);

            currentShow.setSongQueue(newQueue);
            try {
                queueRepo.save(newQueue);
            } catch (Exception ex) {
                return "problem making que or adding song to queue";
            }
            return "song-queue";
        } else {
           List<Song> queueSongs = showQueue.getSongs();
            queueSongs.add(selectedSong);

        }

        try {
            queueRepo.save(showQueue);
        } catch (Exception ex) {
            return "problem adding song to queue";
        }
        return "redirect:/api/ " + showId +"/view-queue";
    }

    @GetMapping("/{showId}/view-queue")
    String viewQueue(@PathVariable int showId, Model model) {
        Show currentShow = showRepo.findOne(showId);
        Queue currentQueue = currentShow.getSongQueue();
        List<Song> songList = currentQueue.getSongs();

        model.addAttribute("songs", songList);




        return "song-queue";
    }
}
