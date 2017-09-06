package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.repository.ArtistRepository;
import com.example.demo.repository.PlaylistRepository;
import com.example.demo.repository.ShowRepository;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.twilio.twiml.Body;
import com.twilio.twiml.Message;
import com.twilio.twiml.MessagingResponse;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static spark.Spark.post;

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
    public String createShow(String locationName, String locationAddress, String startTime, String endTime, String date,  HttpSession session) {

        Artist createdBy = artistRepo.findOne((Integer) session.getAttribute("artistId"));
//        List<Show> showList = new ArrayList<>();
//        showList.add(newShow);

        String startingtime = date + " " + startTime;
        String endingtime = date + " " + endTime;
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        Date showStart = new Date();
        Date showEnd = new Date();
        String formatedDate="";
        String endFormatedDate="";
        if(startingtime.length() >= 19) {
            String datehalf = startingtime.substring(0, 16);
            String AmOrPm = startingtime.substring(17, startingtime.length());
            formatedDate = datehalf + ":00" + " " + AmOrPm;
            System.out.println("formated correctlly date " + formatedDate);
        }
        else{
            String datehalf = startingtime.substring(0, 15);
            String AmOrPm = startingtime.substring(16, startingtime.length());
             formatedDate = datehalf + ":00" + " " + AmOrPm;
            System.out.println("formated correctlly date " + formatedDate);
        }

        if (endingtime.length() >= 19) {
            String enddatehalf = endingtime.substring(0, 16);
            String endAmOrPm = endingtime.substring(17, endingtime.length());
             endFormatedDate = enddatehalf + ":00" + " " + endAmOrPm;
        }
        else {
            String enddatehalf = endingtime.substring(0, 15);
            String endAmOrPm = endingtime.substring(16, endingtime.length());
             endFormatedDate = enddatehalf + ":00" + " " + endAmOrPm;
        }
        System.out.println("formated correctlly start date " + formatedDate);

        System.out.println("formated correctlly end date " + endFormatedDate);
//
        System.out.println(startingtime);

        try{
             showStart = formatter.parse(formatedDate);

            showEnd = formatter.parse(endFormatedDate);

        }catch (Exception ex){
            System.out.println("could not format correctly");
        }



        System.out.println("show start time" + showStart);

        System.out.println("show end time" + showEnd);

        Show newShow = new Show();
        List<Show> showList = createdBy.getShows();
        newShow.setStarted(false);
        newShow.setLocationName(locationName);
        newShow.setLocationAddress(locationAddress);
        newShow.setStartTime(showStart);
        newShow.setEndTime(showEnd);
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
        String artistFirstName = currentArtist.getFirstName().toUpperCase();
        String artistLastName = currentArtist.getLastName().toUpperCase();
        model.addAttribute("allShows", allShows);
        model.addAttribute("currentArtist", currentArtist);
        model.addAttribute("artistFirstName", artistFirstName);
        model.addAttribute("artistLastName", artistLastName);
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

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");

        List<Playlist> allPlaylists = currentArtist.getArtistPlaylists();



        String startString = formatter.format(show.getStartTime());
        String endString = formatter.format(show.getEndTime());
        System.out.println("get startdate "+ startString);
        System.out.println("get enddate "+ endString);


//        if (startString.charAt(11) == '0' ){
//           String stringZero = startString.substring(11,12);
//
//           startString = startString.replace(stringZero , "");
//        }
//        if (endString.charAt(11) == '0' ){
//            String stringZero = endString.substring(11,12);
//
//            endString = endString.replace(stringZero , "");
//        }


        model.addAttribute("startTime", startString);
        model.addAttribute("endTime", endString);
        model.addAttribute("allPlaylists", allPlaylists);
        model.addAttribute("show", show);
        model.addAttribute("renderPlaylistCreator", Booleans.getRenderPlaylistCreator());
        model.addAttribute("selectPlaylist", Booleans.getSelectPlaylist());
        Booleans.setRenderPlaylistCreator(false);
        Booleans.setSelectPlaylist(false);
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

    @PostMapping("/start-show/{showId}")
    public String startShow(@PathVariable int showId) {
        Show currentShow = showRepo.findOne(showId);
        currentShow.setStarted(true);
        System.out.println(currentShow.getStarted());
        Playlist currentPlaylist = currentShow.getPlaylist();
        List<Song> playlistSongs = currentPlaylist.getSongsList();
        for (int i = 0; i < playlistSongs.size(); i++) {
            Song currentSong = playlistSongs.get(i);
            currentSong.setPlaylistVisible(true);
        }
        post("/receive-sms", (req, res) -> {
            Message sms = new Message.Builder()
                    .body(new Body("http://localhost:8080/api/user/" + showId + "/artist-playlist" ))
                    .build();
            MessagingResponse twiml = new MessagingResponse.Builder()
                    .message(sms)
                    .build();
            return twiml.toXml();
        });
        showRepo.save(currentShow);
        return "redirect:/api/start-show/" +showId;
    }

    @GetMapping("/start-show/{showId}")
    public String liveShow(@PathVariable int showId, HttpSession session, Model model) {

        Show currentShow = showRepo.findOne(showId);
//        Playlist showPlaylist = currentShow.getPlaylist();
//        List<Song> playlistSongs = showPlaylist.getSongsList();

        Artist currentArtist = artistRepo.findOne((Integer) session.getAttribute("artistId"));
        if (session.getAttribute("artistId") == null) {
            return "redirect:/api/artist/login";
        }
        List<Song> queueSongs = new ArrayList<>();
        Playlist currentPlaylist = currentShow.getPlaylist();
        if (currentShow.getSongQueue() != null) {
            Queue currentQueue = currentShow.getSongQueue();
            queueSongs = currentQueue.getSongs();
        }

        List<Song> playlistSongs = currentPlaylist.getSongsList();

        model.addAttribute("currentArtist", currentArtist);
        model.addAttribute("currentShow", currentShow);
        model.addAttribute("currentPlaylist", currentPlaylist);
        model.addAttribute("currentPlaylist", currentPlaylist);
        model.addAttribute("queueSongs", queueSongs);
        model.addAttribute("playlistSongs", playlistSongs);
        return "live-show";
    }

    @PostMapping("/{showId}/edit-show")
    public String editShow(@PathVariable int showId) {
        Show show = showRepo.findOne(showId);
        return "redirect:/api/" + showId + "/edit-show";
    }

    @GetMapping("{showId}/edit-show")
    public String renderEditShow(@PathVariable int showId, Model model, HttpSession session) {
        Artist currentArtist = artistRepo.findOne((Integer) session.getAttribute("artistId"));
        List<Playlist> artistPlaylists = currentArtist.getArtistPlaylists();
        Show show = showRepo.findOne(showId);
        model.addAttribute("show", show);
        model.addAttribute("artistPlaylists", artistPlaylists);
        model.addAttribute("editLocation", Booleans.getEditLocation());
        model.addAttribute("editAddPlaylist", Booleans.getEditAddPlaylist());
        Booleans.setEditAddPlaylist(false);
        Booleans.setEditLocation(false);
        return "edit-show";
    }

    @PostMapping("/{showId}/edit-show-info")
    public String editShowInfo(@PathVariable int showId, String locationName, String locationAddress){
        Show currentShow = showRepo.findOne(showId);
        currentShow.setLocationName(locationName);
        currentShow.setLocationAddress(locationAddress);
        showRepo.save(currentShow);
        return "redirect:/api/" + showId + "/edit-show";
    }

    @PostMapping("/{showId}/{playlistId}/edit-show")
    public String editAddPlaylist(@PathVariable int showId, @PathVariable int playlistId) {
        Show currentShow = showRepo.findOne(showId);
        Playlist selectedPlaylist = playlistRepo.findOne(playlistId);
        currentShow.setPlaylist(selectedPlaylist);
        showRepo.save(currentShow);
        return "redirect:/api/" + showId + "/edit-show";
    }

    @PostMapping("/{showId}/edit-location")
    public String editLocation(@PathVariable int showId) {
        Show currentShow = showRepo.findOne(showId);
        Booleans.setEditLocation(true);
        return "redirect:/api/" + showId + "/edit-show";
    }

    @PostMapping("/{showId}/edit-add-playlist")
    public String editAddPlaylist(@PathVariable int showId) {
        Show currentShow = showRepo.findOne(showId);
        Booleans.setEditAddPlaylist(true);
        return "redirect:/api/" + showId + "/edit-show";
    }
}
