package com.example.demo.model;



import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.List;

/**
 * Created by duhlig on 8/16/17.
 */
@Entity
@Table(name="playlists")
public class Playlist {
    @Id
    @GeneratedValue
    private int playlistId;

    @Column(name="playlistName")
    private String playlistName;

    @Nullable
    @ManyToMany
    private List<Song> songsList;

    public Playlist() {
    }

    public Playlist(String playlistName, List<Song> songsList) {
        this.playlistName = playlistName;
        this.songsList = songsList;
    }


    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public List<Song> getSongsList() {
        return songsList;
    }

    public void setSongsList(List<Song> songsList) {
        this.songsList = songsList;
    }

    public int getPlaylistId() {
        return playlistId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Playlist)) return false;

        Playlist playlist = (Playlist) o;

        return getPlaylistId() == playlist.getPlaylistId();
    }

    @Override
    public int hashCode() {
        return getPlaylistId();
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "playlistId=" + playlistId +
                ", playlistName='" + playlistName + '\'' +
                ", songsList=" + songsList +
                '}';
    }
}
