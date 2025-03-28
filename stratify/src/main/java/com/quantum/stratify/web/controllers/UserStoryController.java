package com.quantum.stratify.web.controllers;
 
 import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.CrossOrigin;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
 
 import com.quantum.stratify.services.UserStoryService;
import com.quantum.stratify.web.dtos.ResponseQuantidadeCardsByPeriodo;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
 
 @RestController
 @CrossOrigin("*")
 @RequestMapping("/userStory")
 public class UserStoryController {
 
     @Autowired
     private UserStoryService userStoryService;

     @GetMapping("/count-by-periodo")
    public ResponseEntity<ResponseQuantidadeCardsByPeriodo> countUserStoriesByPeriod(
            @RequestBody @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime dataInicio,
            @RequestBody @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime dataFim) {
        
        ResponseQuantidadeCardsByPeriodo response = userStoryService.countUserStoriesByPeriod(dataInicio, dataFim);
        return ResponseEntity.ok(response);
    }

 }