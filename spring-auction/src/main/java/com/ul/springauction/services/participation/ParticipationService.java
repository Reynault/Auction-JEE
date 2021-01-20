package com.ul.springauction.services.participation;

import com.ul.springauction.DAO.ArticleRepository;
import com.ul.springauction.services.DtoValidator;
import com.ul.springauction.services.user.UserService;
import com.ul.springauction.shared.dto.NewParticipation;
import com.ul.springauction.shared.exception.BadRequestException;
import model.Article;
import model.Auction;
import model.Participation;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class ParticipationService {

    @Autowired
    private UserService userService;
    @Autowired
    private ArticleRepository articleRepo;
    @Autowired
    private DtoValidator dtoValidator;

    public Participation participate(String token, NewParticipation participation, long id) throws BadRequestException {
        dtoValidator.validation(participation);
        Participation p = null;
        Article a = articleRepo.findById(id);
        if(a.getAuction() == null){
            throw new BadRequestException("Aucune enchere disponible pour cet article");
        } else {
            Auction auc = a.getAuction();
            if (auc.getTimeLimit().before(Date.from(Instant.now())) || a.isHasBeenSold()) {
                throw new BadRequestException("Il n'est plus possible de participer aux encheres pour cet article");
            } else {
                User u = userService.findUser(token);
                if (u.getSold().contains(a)) {
                    throw new BadRequestException("Impossible de participer aux encheres de votre propre article");
                } else {
                    if(participation.getValue() < auc.getFirstPrice()){
                        throw new BadRequestException("Impossible de proposer un prix de moins de "+auc.getFirstPrice()+" €");
                    } else {
                        p = new Participation(participation.getValue(), u);
                        auc.addParticipation(p);
                        if (p.getPrice() > auc.getBestPrice()) auc.setBest(p);
                        a.setAuction(auc);
                        articleRepo.save(a);
                    }
                }
            }
        }
        return p;
    }
}
