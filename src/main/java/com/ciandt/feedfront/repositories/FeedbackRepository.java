package com.ciandt.feedfront.repositories;

//TODO: IMPLEMENTE A INTERFACE JPAREPOSITORY E MAPEIE A CLASSE PARA O SPRINGBOOT


import com.ciandt.feedfront.models.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
