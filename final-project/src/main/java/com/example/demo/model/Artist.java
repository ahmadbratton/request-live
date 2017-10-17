package com.example.demo.model;


import javax.persistence.*;
import java.util.List;

/**
 * Created by duhlig on 8/16/17.
 */
@Entity
@Table(name="artists")
public class Artist {

    @Id
    @GeneratedValue
    private int artistId;

    @Column(name="firstName")
    private String firstName;

    @Column(name="lastName")
    private String lastName;

    @Column( unique = true , name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="bio")
    private String bio;

    @OneToMany(orphanRemoval = true , cascade = CascadeType.REMOVE)
    private List<Show> shows;

    @OneToMany
    private List<Playlist> artistPlaylists;

    public Artist() {
    }

    public Artist(String firstName, String lastName, String email, String password, String bio, List<Show> shows, List<Playlist> artistPlaylists) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.shows = shows;
        this.artistPlaylists = artistPlaylists;
    }

    public Artist(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<Show> getShows() {
        return shows;
    }

    public void setShows(List<Show> shows) {
        this.shows = shows;
    }

    public List<Playlist> getArtistPlaylists() {
        return artistPlaylists;
    }

    public void setArtistPlaylists(List<Playlist> artistPlaylists) {
        this.artistPlaylists = artistPlaylists;
    }

    public int getArtistId() {
        return artistId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Artist)) return false;

        Artist artist = (Artist) o;

        return getArtistId() == artist.getArtistId();
    }

    @Override
    public int hashCode() {
        return getArtistId();
    }

    @Override
    public String toString() {
        return "Artist{" +
                "artistId=" + artistId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", bio='" + bio + '\'' +
                ", shows=" + shows +
                ", artistPlaylists=" + artistPlaylists +
                '}';
    }
}
