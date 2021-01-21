package com.ul.springauction.DAO.repository;

import model.Auction;
import model.Participation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionRepository extends CrudRepository<Auction, Long> {

    Auction findByParticipationsContaining(Participation p);
}
