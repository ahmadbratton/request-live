package com.example.demo.controller;

import com.example.demo.model.Artist;
import com.example.demo.model.Playlist;
import com.example.demo.model.Show;
import com.example.demo.repository.ArtistRepository;
import com.example.demo.repository.PlaylistRepository;
import com.example.demo.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by duhlig on 8/17/17.
 */
@RestController
@RequestMapping("/api")
public class ShowController {
    @Autowired
    ShowRepository showRepo;

    @Autowired
    ArtistRepository artistRepo;

    @Autowired
    PlaylistRepository playlistRepo;

    @PostMapping("/create-show")
    @CrossOrigin
    public String createShow(@RequestBody Show newShow, HttpSession session) {
        Artist createdBy = artistRepo.findOne((Integer) session.getAttribute("artistId"));
//        List<Show> showList = new ArrayList<>();
//        showList.add(newShow);
        List<Show> showList = createdBy.getShows();
        newShow.setStarted(false);
        showList.add(newShow);
        try {
            showRepo.save(newShow);
        } catch (Exception ex) {
            return "error creating event";
        }
        return "event created successfully";
    }

    @GetMapping("/view-shows")
    @CrossOrigin
    public List<Show> allShows(HttpSession session) {
        Artist currentArtist = artistRepo.findOne((Integer) session.getAttribute("artistId"));
        return currentArtist.getShows();
    }

    @PostMapping("/{showId}/add-playlist")
    @CrossOrigin
    public String addPlaylistShow(@RequestBody Playlist addedPlaylist , @PathVariable int showId, HttpSession session){
        Artist currentArtist = artistRepo.findOne((Integer) session.getAttribute("artistId"));
        Show show = showRepo.findOne(showId);
        List<Playlist> playlists = show.getPlaylist();
        ArrayList<Playlist> artistPlaylist = new ArrayList<>();
        currentArtist.getArtistPlaylists().forEach(artistPlaylist::add);


        for (Playlist listChoice: artistPlaylist) {
            if (listChoice.getPlaylistName().equals(addedPlaylist.getPlaylistName())){
                playlists.add(listChoice);
                System.out.println("this is the playlist: " + playlists);

            }
        }

        try{
            showRepo.save(show);
        }
        catch (Exception ex){
            return "play list could not be added";
        }

        return "playlist added to show successfully";

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
        return "show deleted successfully";
    }
}
