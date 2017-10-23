package com.example.demo.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by duhlig on 8/16/17.
 */
@Entity
@Table(name="songs")
public class Song {
    @Id
    @GeneratedValue
    private int songId;

    @Column(name="originalArtist")
    private String originalArtist;

    @Column(name="songName")
    private String songName;

    @Column(name="genre")
    private String genre;


    @Column(name="isPlaylistVisible")
    private Boolean isPlaylistVisible;



    public Song() {
    }

    public Song(String originalArtist, String songName, String genre, boolean isPlaylistVisible ) {
        this.originalArtist = originalArtist;
        this.songName = songName;
        this.genre = genre;
        this.isPlaylistVisible = isPlaylistVisible;


    }

    public Boolean getPlaylistVisible() {
        return isPlaylistVisible;
    }

    public void setPlaylistVisible(Boolean playlistVisible) {
        isPlaylistVisible = playlistVisible;
    }

    public String getOriginalArtist() {
        return originalArtist;
    }

    public void setOriginalArtist(String originalArtist) {
        this.originalArtist = originalArtist;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getSongId() {
        return songId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Song)) return false;

        Song song = (Song) o;

        if (getSongId() != song.getSongId()) return false;
        if (getOriginalArtist() != null ? !getOriginalArtist().equals(song.getOriginalArtist()) : song.getOriginalArtist() != null)
            return false;
        if (getSongName() != null ? !getSongName().equals(song.getSongName()) : song.getSongName() != null)
            return false;
        if (getGenre() != null ? !getGenre().equals(song.getGenre()) : song.getGenre() != null) return false;
        return isPlaylistVisible != null ? isPlaylistVisible.equals(song.isPlaylistVisible) : song.isPlaylistVisible == null;
    }

    @Override
    public int hashCode() {
        int result = getSongId();
        result = 31 * result + (getOriginalArtist() != null ? getOriginalArtist().hashCode() : 0);
        result = 31 * result + (getSongName() != null ? getSongName().hashCode() : 0);
        result = 31 * result + (getGenre() != null ? getGenre().hashCode() : 0);
        result = 31 * result + (isPlaylistVisible != null ? isPlaylistVisible.hashCode() : 0);
        return result;
    }
}
