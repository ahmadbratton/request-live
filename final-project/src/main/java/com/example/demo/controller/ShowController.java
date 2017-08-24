package com.example.demo.controller;

import com.example.demo.model.Artist;
import com.example.demo.model.Booleans;
import com.example.demo.model.Playlist;
import com.example.demo.model.Show;
import com.example.demo.repository.ArtistRepository;
import com.example.demo.repository.PlaylistRepository;
import com.example.demo.repository.ShowRepository;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by duhlig on 8/17/17.
 */
@Controller
@RequestMapping("/api")
public class ShowController {
    @Autowired
    ShowRepository showRepo;

    @Autowired
    ArtistRepository artistRepo;

    @Autowired
    PlaylistRepository playlistRepo;


    @GetMapping("/create-show")
    public String renderCreateShow(HttpSession session){
        if (session.getAttribute("artistId") == null) {
            return "redirect:/api/artist/login";
        }
        return "create-show";
    }

    @PostMapping("/create-show")
    @CrossOrigin
    public String createShow(String locationName, String locationAddress, HttpSession session) {
        Artist createdBy = artistRepo.findOne((Integer) session.getAttribute("artistId"));
//        List<Show> showList = new ArrayList<>();
//        showList.add(newShow);
        Show newShow = new Show();
        List<Show> showList = createdBy.getShows();
        newShow.setStarted(false);
        newShow.setLocationName(locationName);
        newShow.setLocationAddress(locationAddress);
        showList.add(newShow);

        try {
            showRepo.save(newShow);

        } catch (Exception ex) {
            return "error creating event";
        }
        int showId = newShow.getShowId();
        return "redirect:/api/"+showId+"/add-playlist";
    }

    @GetMapping("/view-shows")
    @CrossOrigin
    public String allShows(HttpSession session, Model model) {
        if (session.getAttribute("artistId") == null) {
            return "redirect:/api/artist/login";
        }
        Artist currentArtist = artistRepo.findOne((Integer) session.getAttribute("artistId"));
        List<Show> allShows = currentArtist.getShows();
        model.addAttribute("allShows", allShows);
        return "view-shows";
    }

    @GetMapping("/{showId}/add-playlist")
    public String renderAddPlaylist(@PathVariable int showId, Model model, HttpSession session) {

        Artist currentArtist = artistRepo.findOne((Integer) session.getAttribute("artistId"));
        Show show = showRepo.findOne(showId);
//        Iterable<Playlist> playlists = playlistRepo.findAll();
//        ArrayList<Playlist> allPlaylists = new ArrayList<>();
//        for(Playlist currentPlaylist : playlists) {
//            allPlaylists.add(currentPlaylist);
//        }
        List<Playlist> allPlaylists = currentArtist.getArtistPlaylists();
        model.addAttribute("allPlaylists", allPlaylists);
        model.addAttribute("show", show);
        model.addAttribute("renderPlaylistCreator", Booleans.getRenderPlaylistCreator());
        Booleans.setRenderPlaylistCreator(false);
        return "add-playlist";
    }


    @PostMapping("/{showId}/{playlistId}/add-playlist")
    @CrossOrigin
    public String addPlaylistShow(@PathVariable int playlistId , @PathVariable int showId, HttpSession session){
        Artist currentArtist = artistRepo.findOne((Integer) session.getAttribute("artistId"));
        Show show = showRepo.findOne(showId);
        Playlist currentPlaylist = playlistRepo.findOne(playlistId);
        show.setPlaylist(currentPlaylist);
//        ArrayList<Playlist> artistPlaylist = new ArrayList<>();
//        currentArtist.getArtistPlaylists().forEach(artistPlaylist::add);


//        for (Playlist listChoice: artistPlaylist) {
//            if (listChoice.getPlaylistName().equals(currentPlaylist.getPlaylistName())){
//                playlists.add(listChoice);
//                System.out.println("this is the playlist: " + playlists);
//            }
//        }

        try{
            showRepo.save(show);
        }
        catch (Exception ex){
            return "play list could not be added";
        }

        return "redirect:/api/view-shows";

    }

    @PostMapping("/{showId}/render-create-playlist")
    public String renderCreatePlaylist(@PathVariable int showId, Model model) {
        Show show = showRepo.findOne(showId);
        Booleans.setRenderPlaylistCreator(true);
        return "redirect:/api/"+showId+"/add-playlist";
    }

    @DeleteMapping("/{showId}/delete")
    @CrossOrigin
    public String deleteShow(@PathVariable int showId , HttpSession session){
        try {
            Show selectedShow = showRepo.findOne(showId);
            Artist currentArtist = artistRepo.findOne((Integer) session.getAttribute("artistId"));
            List<Show> artistShows = currentArtist.getShows();
            artistShows.remove(selectedShow);
            artistRepo.save(currentArtist);
            showRepo.delete(selectedShow);

        } catch (Exception ex) {
            return "error deleting show";
        }
        return "redirect:/api/view-shows";
    }
}
