package com.example.demo.controller;


import com.example.demo.model.Artist;
import com.example.demo.model.Playlist;
import com.example.demo.model.Song;
import com.example.demo.repository.ArtistRepository;
import com.example.demo.repository.PlaylistRepository;
import com.example.demo.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/create-playlist")
    public String renderCreatePlaylist(Model model, HttpSession session) {
        if (session.getAttribute("artistId") == null) {
            return "redirect:/api/artist/login";
        }
        return "create-playlist";
    }

    @PostMapping("/create-playlist")
    @CrossOrigin
    public String createPlaylist(String playlistName , HttpSession session, Model model){

        Artist createdBy = artistRepo.findOne((Integer) session.getAttribute("artistId"));
        System.out.println(createdBy);

        List<Playlist> ArtistPlaylist = createdBy.getArtistPlaylists();
        Playlist playlist = new Playlist();
        playlist.setPlaylistName(playlistName);



        ArtistPlaylist.add(playlist);
        model.addAttribute("nameOfPlaylist", playlist.getPlaylistName());



        try {
            playlistRepo.save(playlist);
        } catch (Exception ex) {
            return "error creating playlist";
        }
        int playlistId = playlist.getPlaylistId();
        return "redirect:/api/"+playlistId+"/add-song";
    }


    @GetMapping("/{playlistId}/add-song")
    public String renderAddSong(@PathVariable int playlistId, Model model) {
        Playlist currentPlaylist = playlistRepo.findOne(playlistId);

        model.addAttribute("nameOfPlaylist", currentPlaylist.getPlaylistName());
        model.addAttribute("songList", currentPlaylist.getSongsList());
        return "create-playlist";
    }


    @PostMapping("/{playlistId}/add-song")
    @CrossOrigin
    public String addSong(@PathVariable int playlistId , String originalArtist, String songName, String genre) {
        Boolean Newsong = false;

        Playlist currentplayList = playlistRepo.findOne(playlistId);

        List<Song> playlistSongs = currentplayList.getSongsList();

        ArrayList<Song> allSongs = new ArrayList<>();

        songRepo.findAll().forEach(allSongs::add);

        Song song = new Song();
        song.setOriginalArtist(originalArtist);
        song.setSongName(songName);
        song.setGenre(genre);

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


        return "redirect:/api/"+playlistId+"/add-song";
    }

    @GetMapping("/view-playlist")
    @CrossOrigin
    public List<Playlist> viewPlaylist(HttpSession session){
        Artist currentArtist = artistRepo.findOne((Integer) session.getAttribute("artistId"));
        List<Playlist> artistPlaylist = currentArtist.getArtistPlaylists();
        return artistPlaylist;
    }
}
