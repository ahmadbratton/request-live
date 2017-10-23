package com.example.demo.repository;

import com.example.demo.model.Votes;
import org.springframework.data.repository.CrudRepository;

public interface VotesRepository extends CrudRepository<Votes, Integer>{
}
