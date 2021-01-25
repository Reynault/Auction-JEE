package com.ul.springauction.controller;

import com.ul.springauction.configuration.Authentificated;
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
@CrossOrigin(origins = "http://localhost:5201")
public class ParticipationController {

    @Autowired
    private ParticipationService partService;

    @PostMapping(value = "/{id}")
    @Authentificated
    public Participation participate(@RequestHeader(value = "Authorization") String token, @RequestBody NewParticipation participation, @PathVariable long id) throws BadRequestException {
        return partService.participate(token, participation, id);
    }

    @GetMapping(value = "/my")
    @Authentificated
    public List<Article> getInfoAllParticipation(@RequestHeader(value = "Authorization") String token) throws BadRequestException {
        return partService.getInfoAllParticipation(token);
    }

    @GetMapping(value = "/{id}/my")
    @Authentificated
    public Article getInfoOneParticipation(@RequestHeader(value = "Authorization") String token, @PathVariable long id) throws BadRequestException {
        return partService.getInfoOneParticipation(token, id);
    }
}
