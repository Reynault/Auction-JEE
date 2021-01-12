package com.ul.springauction.DAO;

//import com.ul.springauction.model.User;

import model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByLogin(String login);
}
