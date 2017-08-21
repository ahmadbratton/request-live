package com.example.demo.controller;


import com.example.demo.model.Artist;
import com.example.demo.model.Playlist;
import com.example.demo.model.Song;
import com.example.demo.repository.ArtistRepository;
import com.example.demo.repository.PlaylistRepository;
import com.example.demo.repository.SongRepository;
import com.sun.media.jfxmedia.events.PlayerStateListener;
import com.twilio.twiml.Play;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
        return "register";
    }

    @PostMapping("/login")
    public String artistLogin(@RequestBody Artist artist, HttpSession session){

        Artist foundArtist = artistRepo.findByEmail(artist.getEmail());

        if(foundArtist == null){
            return "no artist found";
        }
        if (BCrypt.checkpw(artist.getPassword(), foundArtist.getPassword())){
            session.setAttribute("artistId", foundArtist.getArtistId());
            return "user successfully logged in";

        }

        return "No email/password combination exsist";
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
