package com.example.demo.controller;


import com.example.demo.model.Playlist;
import com.example.demo.model.Show;
import com.example.demo.model.Song;
import com.example.demo.repository.ArtistRepository;
import com.example.demo.repository.PlaylistRepository;
import com.example.demo.repository.ShowRepository;
import com.example.demo.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    PlaylistRepository playlistRepo;

    @Autowired
    ArtistRepository artistRepo;

    @Autowired
    SongRepository songRepo;

    @Autowired
    ShowRepository showRepo;

    @GetMapping("/{showId}/artist-playlist")
    @CrossOrigin
    public String userSongView(@PathVariable int showId, Model model) {
        Show showAttending = showRepo.findOne(showId);
        Playlist showPlaylist = showAttending.getPlaylist();
        List<Song> showSongs = showPlaylist.getSongsList();

        model.addAttribute("showSongs", showSongs);
        model.addAttribute("show",showAttending);

        return "user-view-show";

    }

//    @PostMapping("/{showId}/add-queue")
//    @CrossOrigin
//    public String addSongQueue(@PathVariable int showId , @RequestBody Song song ){
//        Show showAttending = showRepo.findOne(showId);
//        List<Playlist> showPlaylists = showAttending.getPlaylist();
//        List<Song> songQueue =  showAttending.getSongQueue();
//        List<Song> showSongs = new ArrayList<>();
//
//
//        for (Playlist playlist: showPlaylists) {
//            showSongs = playlist.getSongsList();
//        }
//        System.out.println("songList!! " + showSongs);
//        System.out.println("songQueue!! " + songQueue);
//
//
//        for (Song songs: showSongs) {
//            if (songs.getSongName().equals(song.getSongName()) && songs.getOriginalArtist().equals(song.getOriginalArtist())&& songs.getGenre().equals(song.getGenre())){
//                System.out.println(songs);
//                System.out.println("IM IN LOOP FOR SONGS!!!!");
//                System.out.println("IM IN LOOP FOR SONGS!!!!");
//                System.out.println("IM IN LOOP FOR SONGS!!!!");
//
//
//                songQueue.add(songs);
//
//
//        }

//        for (int i = 0; i < showSongs.size() ; i++) {
//            if (song.getOriginalArtist().equals(showSongs.get(i).getOriginalArtist()) && song.getSongName().equals(showSongs.get(i).getSongName()) && song.getGenre().equals(showSongs.get(i).getGenre())){
//                songQueue.add(showSongs.get(i));
//            }

//        }
//        try{
//            showRepo.save(showAttending);
//        }
//        catch (Exception ex){
//            return "song unable to added to Queue ";
//        }
//        return "song added to queue successfully";
//    }

}