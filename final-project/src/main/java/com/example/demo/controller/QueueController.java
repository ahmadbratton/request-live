package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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

    @Autowired
    VotesRepository VotesRepo;

    @PostMapping("/{showId}/{songId}")
    public String addSong(@PathVariable int showId, @PathVariable int songId) {
        Show currentShow = showRepo.findOne(showId);

        Song selectedSong = songRepo.findOne(songId);
        selectedSong.setPlaylistVisible(false);
        Queue showQueue = currentShow.getSongQueue();



        Boolean duplicate;

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

          duplicate =  queueSongs.contains(selectedSong);

           if (duplicate != true){
               queueSongs.add(selectedSong);
           }

           Iterable<Votes> databaseVotes = VotesRepo.findAll();
           List<Votes> songShowVotes = new ArrayList<Votes>();

           databaseVotes.forEach(songShowVotes::add);









               if (songShowVotes.size() <= 0 ){
                   Votes newVote = new Votes();
                   newVote.setSongVoted(selectedSong);
                   newVote.setSongShow(currentShow);
                   VotesRepo.save(newVote);
               }
               else {
                   Boolean exist = false;
                   Votes currentVote = new Votes();
                   for (int i = 0; i < songShowVotes.size(); i++) {
                       currentVote = songShowVotes.get(i);
                       if (currentVote.getSongShow().getShowId() == showId && currentVote.getSongVoted().getSongId() == songId) {
                            currentVote.setNumberOfVotes(currentVote.getNumberOfVotes() + 1 );
                            exist = true;
                       }
                   }
                   if (!exist){
                       Votes newVote = new Votes();
                       newVote.setSongVoted(selectedSong);
                       newVote.setSongShow(currentShow);
                       VotesRepo.save(newVote);
                   }

                   if (exist)

                   VotesRepo.save(currentVote);

               }







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
