package com.example.demo.model;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "votes")
public class Votes {

    @Id
    @GeneratedValue
    private int voteId;

    @ManyToOne
    private Show songShow;

    @ManyToOne
    private Song songVoted;

    @Column(name = "numberOfVotes")
    private int numberOfVotes = 0;



    public Votes() {
    }

    public Votes(int voteId,  Show songShow, Song songVoted, int numberOfVotes) {
        this.voteId = voteId;
        this.songShow = songShow;
        this.songVoted = songVoted;
        this.numberOfVotes= numberOfVotes;
    }

    public int getVoteId() {
        return voteId;
    }

    public int getNumberOfVotes() {
        return numberOfVotes;
    }

    public void setNumberOfVotes(int numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }

    public Song getSongVoted() {
        return songVoted;
    }

    public void setSongVoted(Song songVoted) {
        this.songVoted = songVoted;
    }

    public Show getSongShow() {
        return songShow;
    }

    public void setSongShow(Show songShow) {
        this.songShow = songShow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Votes)) return false;

        Votes votes = (Votes) o;

        if (getVoteId() != votes.getVoteId()) return false;
        if (getNumberOfVotes() != votes.getNumberOfVotes()) return false;
        if (getSongShow() != null ? !getSongShow().equals(votes.getSongShow()) : votes.getSongShow() != null)
            return false;
        return getSongVoted() != null ? getSongVoted().equals(votes.getSongVoted()) : votes.getSongVoted() == null;
    }

    @Override
    public int hashCode() {
        int result = getVoteId();
        result = 31 * result + (getSongShow() != null ? getSongShow().hashCode() : 0);
        result = 31 * result + (getSongVoted() != null ? getSongVoted().hashCode() : 0);
        result = 31 * result + getNumberOfVotes();
        return result;
    }

    @Override
    public String toString() {
        return "Votes{" +
                "voteId=" + voteId +
                ", songShow=" + songShow +
                ", songVoted=" + songVoted +
                ", numberOfVotes=" + numberOfVotes +
                '}';
    }
}
