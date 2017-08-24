package com.example.demo.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by duhlig on 8/16/17.
 */
@Entity
@Table(name="shows")
public class Show {
    @Id
    @GeneratedValue
    private int showId;

    @Column(name="locationName")
    private String locationName;

    @Column(name="locationAddress")
    private String locationAddress;

    @Column(name="startTime")
    private Date startTime;

    @Column(name="endTime")
    private Date endTime;

    @ManyToOne
    private Playlist playlist;

//    @Column(name="songQueue")
//    private ArrayList<Song> songQueue;
    @OneToOne
    private Queue songQueue;

    @Column(name="isStarted")
    private Boolean isStarted;

    public Show() {
    }

    public Show(String locationName, String locationAddress, Date startTime, Date endTime, Playlist playlist, Boolean isStarted) {
        this.locationName = locationName;
        this.locationAddress = locationAddress;
        this.startTime = startTime;
        this.endTime = endTime;
        this.playlist = playlist;
//        this.songQueue = songQueue;
        this.isStarted = isStarted;
    }

    public Show(String locationName, String locationAddress) {
        this.locationName = locationName;
        this.locationAddress = locationAddress;
        this.isStarted = false;
    }

    public Queue getSongQueue() {
        return songQueue;
    }

    public void setSongQueue(Queue songQueue) {
        this.songQueue = songQueue;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

//    public List<Song> getSongQueue() {
//        return songQueue;
//    }
//
//    public void setSongQueue(ArrayList<Song> songQueue) {
//        this.songQueue = songQueue;
//    }

    public Boolean getStarted() {
        return isStarted;
    }

    public void setStarted(Boolean started) {
        isStarted = started;
    }

    public int getShowId() {
        return showId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Show)) return false;

        Show show = (Show) o;

        return getShowId() == show.getShowId();
    }

    @Override
    public int hashCode() {
        return getShowId();
    }

    @Override
    public String toString() {
        return "Show{" +
                "showId=" + showId +
                ", locationName='" + locationName + '\'' +
                ", locationAddress='" + locationAddress + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", playlist=" + playlist +
//                ", songQueue=" + songQueue +
                ", isStarted=" + isStarted +
                '}';
    }
}
