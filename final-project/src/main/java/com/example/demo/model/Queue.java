package com.example.demo.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by duhlig on 8/20/17.
 */
@Entity
@Table(name = "queue")
public class Queue {
    @Id
    @GeneratedValue
    private int queueId;

    @ManyToMany
    private List<Song> songs;

//    @OneToOne
//    private Show show;

    public Queue() {
    }

    public Queue(List<Song> songs, Show show) {
        this.songs = songs;
//        this.show = show;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

//    public Show getShow() {
//        return show;
//    }

//    public void setShow(Show show) {
//        this.show = show;
//    }

    public int getQueueId() {
        return queueId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Queue)) return false;

        Queue queue = (Queue) o;

        return getQueueId() == queue.getQueueId();
    }

    @Override
    public int hashCode() {
        return getQueueId();
    }

    @Override
    public String toString() {
        return "Queue{" +
                "queueId=" + queueId +
                ", songs=" + songs +
//                ", show=" + show +
                '}';
    }
}
