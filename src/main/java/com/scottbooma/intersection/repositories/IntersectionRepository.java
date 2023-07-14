package com.scottbooma.intersection.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.scottbooma.intersection.models.Intersection;

@Repository
public interface IntersectionRepository extends MongoRepository<Intersection, Integer> {
    @Query("{id:'?0'}")
    Intersection findById(String id);
}
