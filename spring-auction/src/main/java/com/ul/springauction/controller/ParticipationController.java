package com.ul.springauction.controller;

import com.ul.springauction.services.ParticipationService;
import com.ul.springauction.shared.dto.NewParticipation;
import com.ul.springauction.shared.exception.BadRequestException;
import model.Article;
import model.Participation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/auction/participation")
@RestController
@CrossOrigin
public class ParticipationController {

    @Autowired
    private ParticipationService partService;

    @PostMapping(value = "/{id}")
    public Participation participate(@RequestHeader(value = "Authorization") String token, @RequestBody NewParticipation participation, @PathVariable long id) throws BadRequestException {
        return partService.participate(token, participation, id);
    }

    @GetMapping(value = "/my")
    public List<Article> getInfoAllParticipation(@RequestHeader(value = "Authorization") String token){
        return partService.getInfoAllParticipation(token);
    }
}
