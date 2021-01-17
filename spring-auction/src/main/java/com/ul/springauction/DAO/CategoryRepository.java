package com.ul.springauction.DAO;

import model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Category findById(long id);
}
