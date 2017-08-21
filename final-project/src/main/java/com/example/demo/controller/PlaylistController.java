package com.example.demo.controller;


import com.example.demo.model.Artist;
import com.example.demo.model.Playlist;
import com.example.demo.model.Song;
import com.example.demo.repository.ArtistRepository;
import com.example.demo.repository.PlaylistRepository;
import com.example.demo.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api")
public class PlaylistController {

    @Autowired
    PlaylistRepository playlistRepo;

    @Autowired
    ArtistRepository artistRepo;

    @Autowired
    SongRepository songRepo;

    @PostMapping("/create-playlist")
    @CrossOrigin
    public String createPlaylist(@RequestBody Playlist playlist , HttpSession session){

        Artist createdBy = artistRepo.findOne((Integer) session.getAttribute("artistId"));
        System.out.println(createdBy);

        List<Playlist> ArtistPlaylist = createdBy.getArtistPlaylists();

        ArtistPlaylist.add(playlist);

        try {
            playlistRepo.save(playlist);
        } catch (Exception ex) {
            return "error creating playlist";
        }
        return "playlist created successfully";
    }


    @PostMapping("/{playlistId}/add-song")
    @CrossOrigin
    public String addSong(@PathVariable int playlistId , @RequestBody Song song) {
        Boolean Newsong = false;

        Playlist currentplayList = playlistRepo.findOne(playlistId);

        List<Song> playlistSongs = currentplayList.getSongsList();

        ArrayList<Song> allSongs = new ArrayList<>();

        songRepo.findAll().forEach(allSongs::add);

        if(allSongs.size() != 0) {
            for (int i = 0; i < allSongs.size(); i++) {
                if (allSongs.get(i).getOriginalArtist().equals(song.getOriginalArtist()) && allSongs.get(i).getSongName().equals(song.getSongName())) {
                    playlistSongs.add(song);
                    System.out.println("song already exist");
                } else {
                    Newsong = true;
                }
            }
            if (Newsong) {
                songRepo.save(song);
                playlistSongs.add(song);
            }
        }
        else{
            songRepo.save(song);
            playlistSongs.add(song);
        }

        try {

            playlistRepo.save(currentplayList);
        } catch (Exception ex) {
            return "songs could not be Added";
        }


        return "songs were added to play list";
    }

    @GetMapping("/view-playlist")
    @CrossOrigin
    public List<Playlist> viewPlaylist(HttpSession session){
        Artist currentArtist = artistRepo.findOne((Integer) session.getAttribute("artistId"));
        List<Playlist> artistPlaylist = currentArtist.getArtistPlaylists();
        return artistPlaylist;
    }
}
