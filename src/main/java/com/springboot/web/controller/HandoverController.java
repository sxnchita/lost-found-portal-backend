package com.springboot.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.springboot.web.dto.HandoverRequestDto;
import com.springboot.web.dto.HandoverResponseDto;
import com.springboot.web.service.HandoverService;

@RestController
@RequestMapping("/handovers")
public class HandoverController {

    @Autowired
    private HandoverService handoverService;

    @PostMapping
    public HandoverResponseDto scheduleHandover(@RequestBody HandoverRequestDto handoverRequestDto) {
        return handoverService.scheduleHandover(handoverRequestDto);
    }

    @GetMapping
    public List<HandoverResponseDto> getAllHandovers() {
        return handoverService.getAllHandovers();
    }

    @PutMapping("/{id}/complete")
    public HandoverResponseDto markHandoverCompleted(@PathVariable Long id) {
        return handoverService.markHandoverCompleted(id);
    }
}