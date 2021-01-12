package service.article;

import dao.article.ArticleDAOLocal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import model.Article;
import shared.entities.ArticleEntity;

@Stateless
public class ArticleService implements ArticleServiceLocal {

    @EJB
    private ArticleDAOLocal dao;

    @Override
    public Collection<ArticleEntity> getAll() {
        return convertListToEntities(dao.getAll());
    }

    private Collection<ArticleEntity> convertListToEntities(Collection<Article> articles) {
        List<ArticleEntity> entities = new ArrayList<>();
        articles.forEach(a -> {
            entities.add(ArticleEntity.convertArticleToEntity(a));
        });
        return entities;
    }
}
