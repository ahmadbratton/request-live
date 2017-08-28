package com.example.demo.model;

import javax.persistence.*;

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

    public Song(String originalArtist, String songName, String genre) {
        this.originalArtist = originalArtist;
        this.songName = songName;
        this.genre = genre;
        this.isPlaylistVisible = true;
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

        return getSongId() == song.getSongId();
    }

    @Override
    public int hashCode() {
        return getSongId();
    }

    @Override
    public String toString() {
        return "Song{" +
                "songId=" + songId +
                ", originalArtist='" + originalArtist + '\'' +
                ", songName='" + songName + '\'' +
                ", genre='" + genre + '\'' +
                ", isPlaylistVisible=" + isPlaylistVisible +
                '}';
    }
}
