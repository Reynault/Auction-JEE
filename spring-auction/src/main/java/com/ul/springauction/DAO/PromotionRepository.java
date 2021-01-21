package com.ul.springauction.DAO;

import model.Promotion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionRepository extends CrudRepository<Promotion, Long> {

    List<Promotion> findAll();
    List<Promotion> findAllByDaily(boolean b);
}
