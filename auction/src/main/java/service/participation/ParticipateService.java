package service.participation;

import dao.article.ArticleDAOLocal;
import dao.auction.AuctionDAOLocal;
import dao.participate.ParticipationDAOLocal;
import java.util.Collection;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import model.Article;
import model.Participation;
import shared.dto.UserParticipate;
import web.exceptions.BadValuesException;

@Stateless
public class ParticipateService implements ParticipateServiceLocal {

    @EJB
    private ParticipationDAOLocal dao;

    @EJB
    private ArticleDAOLocal articles;
    @EJB
    private AuctionDAOLocal auctions;

    @Override
    public Participation participate(UserParticipate participation, String login, long id) {
        // if article is not expired
        if (auctions.isSold(id) && !auctions.isFinished(id)) {
            // if user isn't owner
            if (!articles.ownArticle(id, login)) {
                // if value is greater
                if (dao.valueIsGreater(participation.getValue(), id)) {
                    return dao.updateParticipation(participation.getValue(), login, id);
                } else {
                    throw new BadValuesException("Il faut fournir une valeur plus grande !");
                }
            } else {
                throw new BadValuesException("L'utilisateur possède l'article");
            }
        } else {
            throw new BadValuesException("L'article n'est pas en vente");
        }
    }

    @Override
    public Collection<Article> getMyParticipatedArticles(String login) {
        return dao.getMyParticipatedArticles(login);
    }

    @Override
    public Article getOneParticipatedArticle(String login, long id) {
        if (dao.isABidder(login, id)) {
            Article a = dao.getOneParticipatedArticle(login, id);
            if (a == null) {
                throw new BadValuesException("Article plus en vente");
            } else {
                return a;
            }
        } else {
            throw new BadValuesException("Article inexistant");
        }
    }

}
