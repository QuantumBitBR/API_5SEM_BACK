package com.quantum.stratify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.quantum.stratify.service.*;

import java.util.Date;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    @GetMapping("/count")
    public Long getTotalCards(@RequestParam("startDate") Date startDate,
                              @RequestParam("endDate") Date endDate) {
        return cardService.getTotalCardsBetweenDates(startDate, endDate);
    }
}