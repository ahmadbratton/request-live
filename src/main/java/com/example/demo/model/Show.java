package com.example.demo.model;

import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by duhlig on 8/16/17.
 *
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
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "MM/dd/yyyy hh:mm:ss a")
    private Date startTime;

    @Column(name="endTime")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "MM/dd/yyyy hh:mm:ss a")
    private Date endTime;

    @Column(name="startTimeFormatted")
    private String startTimeFormatted;

    @Column(name="endTimeFormatted")
    private String endtimeFormatted;

    @Column(name="dateFormatted")
    private String dateFormatted;

    @ManyToOne
    private Playlist playlist;



//    @Column(name="songQueue")
//    private ArrayList<Song> songQueue;
    @OneToOne
    private Queue songQueue;



    @Column(name="isStarted")
    private Boolean isStarted;

    private static int tempShowId;


    public Show() {
    }

<<<<<<< HEAD:src/main/java/com/example/demo/model/Show.java
    public Show(String locationName, String locationAddress, Date startTime, Date endTime, Playlist playlist, Boolean isStarted, int tempShowId) {
=======
    public Show(String locationName, String locationAddress, Date startTime, Date endTime, Playlist playlist, Boolean isStarted ) {
>>>>>>> e04b5543e881731b7f72ffd16e9b74c1c930e06d:final-project/src/main/java/com/example/demo/model/Show.java
        this.locationName = locationName;
        this.locationAddress = locationAddress;
        this.startTime = startTime;
        this.endTime = endTime;
        this.playlist = playlist;
//        this.songQueue = songQueue;
        this.isStarted = isStarted;
        this.tempShowId = tempShowId;

    }

    public Show(String locationName, String locationAddress) {
        this.locationName = locationName;
        this.locationAddress = locationAddress;
        this.isStarted = false;
    }





    public static int getTempShowId() {
        return tempShowId;
    }

    public static void setTempShowId(int tempShowId) {
        Show.tempShowId = tempShowId;
    }

    public String getStartTimeFormatted() {
        return startTimeFormatted;
    }

    public void setStartTimeFormatted(String startTimeFormatted) {
        this.startTimeFormatted = startTimeFormatted;
    }

    public String getEndtimeFormatted() {
        return endtimeFormatted;
    }

    public void setEndtimeFormatted(String endtimeFormatted) {
        this.endtimeFormatted = endtimeFormatted;
    }

    public String getDateFormatted() {
        return dateFormatted;
    }

    public void setDateFormatted(String dateFormatted) {
        this.dateFormatted = dateFormatted;
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
                ", startTimeFormatted='" + startTimeFormatted + '\'' +
                ", endtimeFormatted='" + endtimeFormatted + '\'' +
                ", dateFormatted='" + dateFormatted + '\'' +
                ", playlist=" + playlist +
                ", songQueue=" + songQueue +
                ", isStarted=" + isStarted +
                ", tempShowId=" + tempShowId +
                '}';
    }

    public Date formatDate(String date , String time ){
        String startingtime = date + " " + time;
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        Date showtime = new Date();
        String formatedDate="";

        if(startingtime.length() >= 19) {
            String datehalf = startingtime.substring(0, 16);
            String AmOrPm = startingtime.substring(17, startingtime.length());
            formatedDate = datehalf + ":00" + " " + AmOrPm;

        }
        else{
            String datehalf = startingtime.substring(0, 15);
            String AmOrPm = startingtime.substring(16, startingtime.length());
            formatedDate = datehalf + ":00" + " " + AmOrPm;
            System.out.println();
        }

        try{
             showtime = formatter.parse(formatedDate);


            return showtime;

        }catch (Exception ex){
            return null;
        }
    }




}
