package com.example.demo.controller;


import com.example.demo.model.*;
import com.example.demo.repository.ArtistRepository;
import com.example.demo.repository.PlaylistRepository;
import com.example.demo.repository.ShowRepository;
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

    @Autowired
    ShowRepository showRepo;

    @GetMapping("/create-playlist")
    public String renderCreatePlaylist(Model model, HttpSession session) {
        if (session.getAttribute("artistId") == null) {
            return "redirect:/api/artist/login";
        }


        return "create-playlist";
    }

    @PostMapping("/create-playlist")
    public String createPlaylist(String playlistName, HttpSession session){
        Artist currentArtist = artistRepo.findOne((Integer) session.getAttribute("artistId") );
        List<Playlist> artistPlaylist = currentArtist.getArtistPlaylists();

        Playlist newPlaylist = new Playlist();
        newPlaylist.setPlaylistName(playlistName);

        artistPlaylist.add(newPlaylist);

        playlistRepo.save(newPlaylist);
        int playlistId = newPlaylist.getPlaylistId();
        return "redirect:/api/" + playlistId + "/add-song";
    }

    @PostMapping("/{showId}/create-playlist")
    @CrossOrigin
    public String createPlaylist(String playlistName , HttpSession session, Model model, @PathVariable int showId){
        Show show = showRepo.findOne(showId);
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
        Booleans.setRenderSongCreator(true);
        return "redirect:/api/"+showId + "/" + playlistId+"/add-song";
    }

    @GetMapping("/{showId}/{playlistId}/add-song")
    public String renderAddSongPlaylist(@PathVariable int showId, @PathVariable int playlistId, Model model, HttpSession session) {
        Artist currentArtist = artistRepo.findOne((Integer) session.getAttribute("artistId"));
        Playlist currentPlaylist = playlistRepo.findOne(playlistId);
        Show show = showRepo.findOne(showId);
        Booleans.setRenderPlaylistCreator(true);
//        Show show = showRepo.findOne(showId);
        List<Playlist> allPlaylists = currentArtist.getArtistPlaylists();
        System.out.println(Booleans.getRenderSongCreator());
        System.out.println(Booleans.getRenderPlaylistCreator());
        model.addAttribute("allPlaylists", allPlaylists);
        model.addAttribute("show", show);
        model.addAttribute("nameOfPlaylist", currentPlaylist.getPlaylistName());
        model.addAttribute("songList", currentPlaylist.getSongsList());
        model.addAttribute("renderPlaylistCreator", Booleans.getRenderPlaylistCreator());
        model.addAttribute("renderSongCreator", Booleans.getRenderSongCreator());
        return "add-playlist";
    }


    @GetMapping("/{playlistId}/add-song")
    public String renderAddSong(@PathVariable int playlistId, Model model) {
        Playlist currentPlaylist = playlistRepo.findOne(playlistId);

        model.addAttribute("nameOfPlaylist", currentPlaylist.getPlaylistName());
        model.addAttribute("songList", currentPlaylist.getSongsList());
        return "create-playlist";
    }

    @PostMapping("/{playlistId}/add-song")
    public String addPlaylist_song(@PathVariable int playlistId, String originalArtist, String songName, String genre ){
        Boolean Newsong = false;

        Playlist playlist = playlistRepo.findOne(playlistId);
        List<Song> songList = playlist.getSongsList();

        ArrayList<Song> Songs = new ArrayList<>();

        songRepo.findAll().forEach(Songs::add);

        Song song = new Song();
        song.setOriginalArtist(originalArtist);
        song.setSongName(songName);
        song.setGenre(genre);

        if(Songs.size() != 0) {
            for (int i = 0; i < Songs.size(); i++) {
                if (Songs.get(i).getOriginalArtist().equals(song.getOriginalArtist()) && Songs.get(i).getSongName().equals(song.getSongName())) {
                    songList.add(Songs.get(i));
                    songRepo.save(Songs.get(i));
                    playlistRepo.save(playlist);
                    return "redirect:/api/"+ playlistId + "/add-song";
//
                } else {
                    Newsong = true;
                }
            }
            if (Newsong) {
                songRepo.save(song);
                songList.add(song);
            }
        }
        else{
//            songRepo.save(song);
            songList.add(song);
        }

        try {

            playlistRepo.save(playlist);
        } catch (Exception ex) {
            return "redirect:/api/"+ playlistId + "/add-song";
        }
        return "redirect:/api/"+ playlistId + "/add-song";
     }


    @PostMapping("/{showId}/{playlistId}/add-song")
    @CrossOrigin
    public String addSong(@PathVariable int playlistId , @PathVariable int showId, String originalArtist, String songName, String genre) {
        Show show = showRepo.findOne(showId);
        Boolean Newsong = false;

        Playlist currentplayList = playlistRepo.findOne(playlistId);

        List<Song> playlistSongs = currentplayList.getSongsList();

        ArrayList<Song> allSongs = new ArrayList<>();

        songRepo.findAll().forEach(allSongs::add);

        Song song = new Song();
        song.setOriginalArtist(originalArtist);
        song.setSongName(songName);
        song.setGenre(genre);
        song.setPlaylistVisible(true);
        if(allSongs.size() != 0) {
            for (int i = 0; i < allSongs.size(); i++) {
                if (allSongs.get(i).getOriginalArtist().equals(song.getOriginalArtist()) && allSongs.get(i).getSongName().equals(song.getSongName())) {

                    playlistSongs.add(allSongs.get(i));
                    songRepo.save(allSongs.get(i));
                    playlistRepo.save(currentplayList);
                    return "redirect:/api/"+ showId + "/" + playlistId + "/add-song";
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
            System.out.println();
            return "redirect:/api/"+ showId + "/" + playlistId + "/add-song";
        }


        return "redirect:/api/"+ showId + "/" + playlistId + "/add-song";
    }

    @GetMapping("/view-playlist")
    @CrossOrigin
    public List<Playlist> viewPlaylists(HttpSession session){
        Artist currentArtist = artistRepo.findOne((Integer) session.getAttribute("artistId"));
        List<Playlist> artistPlaylist = currentArtist.getArtistPlaylists();
        return artistPlaylist;
    }

    @PostMapping("/{showId}/submit-playlist")
    public String submitPlaylist(@PathVariable int showId) {
        Show show = showRepo.findOne(showId);
        return "redirect:/api/" + showId + "/add-playlist";
    }

    @GetMapping("/{playlistId}/view-playlist")
    public String viewPlaylistById(@PathVariable int playlistId , Model model ){
        Playlist playlist = playlistRepo.findOne(playlistId);
        model.addAttribute("playlist",playlist);
        model.addAttribute("songs", playlist.getSongsList());
        model.addAttribute("editPlaylist", Booleans.getEditPlaylist());
        model.addAttribute("editPlaylistName", Booleans.getEditPlaylistName());
        Booleans.setEditPlaylist(false);
        Booleans.setEditPlaylistName(false);
        return "view-playlist";
    }

    @PostMapping("/{playlistId}/view-playlist")
    public String viewPlaylist(@PathVariable int playlistId){
        Playlist playlist = playlistRepo.findOne(playlistId);
        return "redirect:/api/" + playlistId + "/view-playlist";
    }

    @PostMapping("/{playlistId}/playlist-created")
    public String Playlistsubmit(@PathVariable int playlistId){
        Playlist playlist = playlistRepo.findOne(playlistId);
        return "redirect:/api/" + playlistId + "/view-playlist";
    }

    @PostMapping("/{playlistId}/edit-playlist")
    public String editPlaylist(@PathVariable int playlistId) {
        Playlist selectedPlaylist = playlistRepo.findOne(playlistId);
        Booleans.setEditPlaylist(true);
        return "redirect:/api/" + playlistId + "/view-playlist";
    }

    @PostMapping("/{playlistId}/edit-playlist-name")
    public String editPlaylistName(@PathVariable int playlistId) {
        Booleans.setEditPlaylistName(true);
        return "redirect:/api/" + playlistId + "/view-playlist";
    }

}
