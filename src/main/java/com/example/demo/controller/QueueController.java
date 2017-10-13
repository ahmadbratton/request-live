package com.example.demo.controller;

import com.example.demo.model.*;
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
        selectedSong.setPlaylistVisible(false);
        Queue showQueue = currentShow.getSongQueue();

        Booleans.setRefreshQueue(true);

        System.out.println("user should be set to true " + Booleans.getRefreshQueue());

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
            return "redirect:/api/ " + showId +"/view-queue";
        } else {
           List<Song> queueSongs = showQueue.getSongs();
            queueSongs.add(selectedSong);

        }

        try {
            queueRepo.save(showQueue);
        } catch (Exception ex) {
            return "problem adding song to queue";
        }
        return "redirect:/api/" + showId +"/view-queue";
    }

    @GetMapping("/{showId}/view-queue")
    String viewQueue(@PathVariable int showId, Model model) {

        Show currentShow = showRepo.findOne(showId);
        Queue currentQueue = currentShow.getSongQueue();
        List<Song> songList = currentQueue.getSongs();
        model.addAttribute("show", currentShow);
        model.addAttribute("showLocation", currentShow.getLocationName());
        model.addAttribute("showAddress", currentShow.getLocationAddress());
        model.addAttribute("songs", songList);
        model.addAttribute("listing", songList);

        return "song-queue";
    }

    @DeleteMapping("/{showId}/{songId}/mark-as-played")
    String deleteFromQueue(@PathVariable int showId, @PathVariable int songId) {
        Song selectedSong = songRepo.findOne(songId);
        Show show = showRepo.findOne(showId);
        Queue queue = show.getSongQueue();
        List<Song> songList = queue.getSongs();
        songList.remove(selectedSong);
        queueRepo.save(queue);
        return "redirect:/api/start-show/" + showId ;
    }
}
