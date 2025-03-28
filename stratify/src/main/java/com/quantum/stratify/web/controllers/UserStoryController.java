package com.quantum.stratify.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quantum.stratify.services.UserStoryService;
import com.quantum.stratify.web.dtos.TotalCardsDTO;

@RestController
@CrossOrigin("*")
@RequestMapping("/userStory")
public class UserStoryController {

    @Autowired
    private UserStoryService userStoryService;


    @GetMapping("/total-cards")
    public ResponseEntity<TotalCardsDTO> getTotalCardCount() {
        return ResponseEntity.ok().body(userStoryService.getTotalCardCount());
    }
}
