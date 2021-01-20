package com.ul.springauction.DAO;

import model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

    Category findById(long id);
    Category findByName(String name);
}
