package com.aktiviti6.controller;

import com.aktiviti6.model.Elena;
import com.aktiviti6.service.ElenaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/elena-api")
@RequiredArgsConstructor
public class    ElenaController {

    private final ElenaService elenaService;

    @GetMapping("/elena")
    public List<Elena> listElenas() {
        return elenaService.findAll();
    }

    @GetMapping("/elena/{id}")
    public Elena findElena(@PathVariable Long id) {
        return elenaService.findById(id)
                .orElseThrow(() -> new RuntimeException("Elena not found"));
    }

    @PostMapping("/elena")
    public Elena createElena(@RequestBody Elena elena) {
        return elenaService.createElena(
                elena.getEmail(),
                elena.getPassword(),
                Elena.Role.USER
        );
    }
}