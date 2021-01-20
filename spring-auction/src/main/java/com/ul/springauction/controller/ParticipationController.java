package com.ul.springauction.controller;

import com.ul.springauction.services.participation.ParticipationService;
import com.ul.springauction.shared.dto.NewParticipation;
import com.ul.springauction.shared.exception.BadRequestException;
import model.Participation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
