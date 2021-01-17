package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.*;

/**
 * POJO for a user to map with the BDD
 */
@Entity
@Table(name = "USER")
@NamedQueries({
    @NamedQuery(
            name = "User.isABidder",
            query = "SELECT u FROM User u JOIN u.sold a JOIN a.auction.participations p "
            + "WHERE u.login = :login "
            + "AND p.id = :id"
    ),
    @NamedQuery(
            name = "User.findOne",
            query = "SELECT u FROM User u WHERE u.login = :login"
    ),
    @NamedQuery(
            name = "User.own",
            query = "SELECT u FROM User u JOIN u.sold a WHERE a.id = :id AND u.login = :login"
    ),
    @NamedQuery(
            name = "User.findMine",
            query = "SELECT a FROM User u JOIN u.sold a WHERE u.login = :login"
    ),
    @NamedQuery(
            name = "User.getParticipations",
            query = "SELECT p FROM User u JOIN u.sold a JOIN a.auction.participations p"
            + " WHERE u.login = :login"
            + " AND a.auction.timeLimit  > :date"
            + " OR a.auction.best.id = p.id"
    ),
    @NamedQuery(
            name = "User.getOneParticipation",
            query = "SELECT p FROM User u JOIN u.sold a JOIN a.auction.participations p"
            + " WHERE u.login = :login"
            + " AND a.id = :id"
            + " AND a.auction.timeLimit  > :date"
            + " OR a.auction.best.id = p.id"
    )

})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String login;
    @JsonIgnore
    private String pass;
    private String name;
    private String lastname;

    @OneToOne(targetEntity = Address.class, cascade = CascadeType.ALL)
    @JsonIgnore
    private Address home;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    private List<Delivery> deliveries;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    private List<Article> sold;

    public User(long id, String login, String pass, String name, String lastname,
            Address home, List<Delivery> deliveries,
            List<Article> sold) {
        this.id = id;
        this.login = login;
        this.pass = pass;
        this.name = name;
        this.lastname = lastname;
        this.home = home;
        this.deliveries = deliveries;
        this.sold = sold;
    }

    public User(String login, String pass, String name, String lastname, Address home, List<Delivery> deliveries, List<Article> sold) {
        this.login = login;
        this.pass = pass;
        this.name = name;
        this.lastname = lastname;
        this.home = home;
        this.deliveries = deliveries;
        this.sold = sold;
    }

    public User() {
    }

    public void removeArticle(long id) {
        for (int i = 0; i < sold.size(); i++) {
            if (sold.get(i).getId() == id) {
                sold.remove(i);
            }
        }
    }

    public void addArticle(Article a) {
        sold.add(a);
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
