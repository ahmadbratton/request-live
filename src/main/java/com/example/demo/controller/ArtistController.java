package com.example.demo.controller;


import com.example.demo.model.*;
import com.example.demo.repository.ArtistRepository;
import com.example.demo.repository.PlaylistRepository;
import com.example.demo.repository.SongRepository;
import com.twilio.twiml.Play;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("api/artist")
public class ArtistController {

    @Autowired
    ArtistRepository artistRepo;
    @Autowired
    SongRepository songRepo;
    @Autowired
    PlaylistRepository playlistRepo;

    @GetMapping("/register")
    public String renderRegister(){
        return "register";
    }

    @PostMapping("/register")
    public String artistSignUp(String firstName, String lastName, String email, String password){

//        System.out.println(artist);
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));
//        String email = artist.getEmail();
//        String firstName = artist.getFirstName();
//        String lastName = artist.getLastName();

        Artist newArtist = new Artist();
        newArtist.setFirstName(firstName);
        newArtist.setLastName(lastName);
        newArtist.setEmail(email);
        newArtist.setPassword(hashed);

        artistRepo.save(newArtist);

//        return "user created";
        return "redirect:/api/artist/login";
    }

    @PostMapping("/login")
    public String artistLogin(String email, String password, HttpSession session, Model model){

        Artist foundArtist = artistRepo.findByEmail(email);

        if(foundArtist == null){
            String error = "username and/or password is incorrect";
            model.addAttribute("error", error);
            return "login";
        }
        if (BCrypt.checkpw(password, foundArtist.getPassword())){
            session.setAttribute("artistId", foundArtist.getArtistId());
            return "redirect:/api/view-shows";

        }
        Booleans.setLoginError(true);

        return "redirect:/api/artist/login";
    }

    @GetMapping("/login")
    public String renderLogin(Model model) {
        String errorMessage = "email/password combination does not exist";
        model.addAttribute("loginError", Booleans.getLoginError());
        model.addAttribute("errorMessage", errorMessage);
        Booleans.setLoginError(false);
        return "login";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/api/artist/login";
    }

    @GetMapping("/home")
    public String artistHome(HttpSession session , Model model){
        if (session.getAttribute("artistId") == null){
            return "redirect:/api/artist/login";
        }
        Artist currentArtist = artistRepo.findOne((Integer) session.getAttribute("artistId"));
        List<Show> artistShows = currentArtist.getShows();
        List<Playlist> artistPlaylist = currentArtist.getArtistPlaylists();
        model.addAttribute("artist", currentArtist);
        model.addAttribute("playlists", artistPlaylist);
        model.addAttribute("shows", artistShows);
        return "home";

    }

//    @PostMapping("/create-song")
//    public String createSong(@RequestBody Song song ){
//
//
//        ArrayList<Song> allSongs = new ArrayList<>();
//
//        songRepo.findAll().forEach(allSongs:: add);
//
//        for (int i = 0; i < allSongs.size(); i++) {
//            if (allSongs.get(i).getOriginalArtist().equals(song.getOriginalArtist()) && allSongs.get(i).getSongName().equals(song.getSongName())) {
//                return "song has already been created";
//            }
//
//        }
//        System.out.println(allSongs);
//        songRepo.save(song);
//
//        return "song created successfully";
//
//    }

}
