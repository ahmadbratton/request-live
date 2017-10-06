package com.example.demo.repository;

import com.example.demo.model.Song;
import org.springframework.data.repository.CrudRepository;

public interface SongRepository extends CrudRepository<Song, Integer> {
    Song findBySongName(String songName);
}
