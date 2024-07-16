package com.vr.miniauthorizer.repository;

import com.vr.miniauthorizer.document.Card;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CardRepository extends MongoRepository<Card, String> {
}