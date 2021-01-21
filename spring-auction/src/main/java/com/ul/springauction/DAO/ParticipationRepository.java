package com.ul.springauction.DAO;

import model.Participation;
import model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipationRepository extends CrudRepository<Participation, Long> {

    List<Participation> findAllByBidder(User u);
}
