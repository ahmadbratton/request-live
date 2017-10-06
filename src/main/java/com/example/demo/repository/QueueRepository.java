package com.example.demo.repository;

import com.example.demo.model.Queue;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by duhlig on 8/20/17.
 */
public interface QueueRepository extends CrudRepository<Queue, Integer> {
}
