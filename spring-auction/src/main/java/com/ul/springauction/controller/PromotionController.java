package com.ul.springauction.controller;

import com.ul.springauction.services.PromotionService;
import com.ul.springauction.shared.exception.BadRequestException;
import com.ul.springauction.shared.response.Response;
import model.Article;
import model.Promotion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RequestMapping(value = "/participation")
@RestController
@CrossOrigin(origins = "http://localhost:5201")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @GetMapping(value = "/promotions")
    public List<Promotion> getAllPromotions(){
        return promotionService.getAllPromotion();
    }

    @GetMapping(value = "/{id}/checkPrice")
    public Response calculNewPrice(@RequestHeader(value = "Authorization")String token, @PathVariable long id, @RequestParam(required = false)List<Long> o) throws BadRequestException {
        return promotionService.calcuNewPrice(token, id, o);
    }
}
