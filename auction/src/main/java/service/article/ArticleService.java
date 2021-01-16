package service.article;

import dao.article.ArticleDAOLocal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import model.Article;
import shared.dto.ArticleCreation;
import shared.entities.ArticleEntity;
import shared.entities.Entity;
import shared.params.SearchParams;
import web.exceptions.BadValuesException;

@Stateless
public class ArticleService implements ArticleServiceLocal {

    @EJB
    private ArticleDAOLocal dao;

    @Override
    public Collection<Entity> getAll(SearchParams search) {
        return convertListToEntities(dao.getAll(search));
    }

    @Override
    public Entity getOne(long id) {
        Article a = dao.getOne(id);
        if (a != null) {
            return ArticleEntity.convertArticleToEntity(a);
        } else {
            throw new BadValuesException("Article inexistant");
        }
    }

    @Override
    public Collection<Entity> getMine(String login) {
        return convertListToEntities(dao.getMine(login));
    }

    @Override
    public Entity postOne(ArticleCreation article, String login) {
        return ArticleEntity.convertArticleToEntity(dao.postOne(article, login));
    }

    private Collection<Entity> convertListToEntities(Collection<Article> articles) {
        List<Entity> entities = new ArrayList<>();
        articles.forEach(a -> {
            entities.add(ArticleEntity.convertArticleToEntity(a));
        });
        return entities;
    }

    @Override
    public void delete(long id, String login) {
        if (dao.delete(id, login) == 0) {
            throw new BadValuesException("Article inexistant pour cet utilisateur");
        }
    }

    @Override
    public void removeFromMarket(long id, String login) {
        if (dao.removeFromMarket(id, login) == 0) {
            throw new BadValuesException("Article inexistant pour cet utilisateur");
        }
    }
}
