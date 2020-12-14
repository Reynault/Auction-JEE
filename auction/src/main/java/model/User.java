package model;

import javax.persistence.*;

/**
 * POJO for a user to map with the BDD
 */
@Entity
@Table
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String login;
    private String pass;
    private String salt;
    private String name;
    private String lastname;

    public User(long id, String login, String pass, String salt, String name, String lastname) {
        this.id = id;
        this.login = login;
        this.pass = pass;
        this.salt = salt;
        this.name = name;
        this.lastname = lastname;
    }

    public User(String login, String pass, String salt, String name, String lastname) {
        this.login = login;
        this.pass = pass;
        this.salt = salt;
        this.name = name;
        this.lastname = lastname;
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
