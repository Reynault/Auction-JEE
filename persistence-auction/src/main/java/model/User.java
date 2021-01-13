package model;

import javax.persistence.*;

/**
 * POJO for a user to map with the BDD
 */
@Entity
@Table
@NamedQueries({
    @NamedQuery(
            name = "User.findOne",
            query = "SELECT u FROM User u WHERE u.login = :login"
    )
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String login;
    private String pass;
    private String name;
    private String lastname;

    @OneToOne(targetEntity = Address.class)
    private Address home;

    public User(long id, String login, String pass, String name, String lastname, Address home) {
        this.id = id;
        this.login = login;
        this.pass = pass;
        this.name = name;
        this.lastname = lastname;
        this.home = home;
    }

    public User(String login, String pass, String name, String lastname, Address home) {
        this.login = login;
        this.pass = pass;
        this.name = name;
        this.lastname = lastname;
        this.home = home;
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

    public Address getHome() {
        return home;
    }

    public void setHome(Address home) {
        this.home = home;
    }

}
