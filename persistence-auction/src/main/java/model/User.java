package model;

import java.util.List;
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

    @OneToMany(targetEntity = Participation.class, mappedBy = "bidder")
    private List<Participation> participate;

    @OneToMany(targetEntity = Delivery.class, mappedBy = "user")
    private List<Delivery> deliveries;

    @OneToMany(targetEntity = Article.class, mappedBy = "owner")
    private List<Article> sold;

    public User(long id, String login, String pass, String name, String lastname,
            Address home, List<Participation> participate, List<Delivery> deliveries,
            List<Article> sold) {
        this.id = id;
        this.login = login;
        this.pass = pass;
        this.name = name;
        this.lastname = lastname;
        this.home = home;
        this.participate = participate;
        this.deliveries = deliveries;
        this.sold = sold;
    }

    public User(String login, String pass, String name, String lastname, Address home,
            List<Participation> participate, List<Delivery> deliveries, List<Article> sold) {
        this.login = login;
        this.pass = pass;
        this.name = name;
        this.lastname = lastname;
        this.home = home;
        this.participate = participate;
        this.deliveries = deliveries;
        this.sold = sold;
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

    public List<Participation> getParticipate() {
        return participate;
    }

    public void setParticipate(List<Participation> participate) {
        this.participate = participate;
    }

    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(List<Delivery> deliveries) {
        this.deliveries = deliveries;
    }

    public List<Article> getSold() {
        return sold;
    }

    public void setSold(List<Article> sold) {
        this.sold = sold;
    }

}
