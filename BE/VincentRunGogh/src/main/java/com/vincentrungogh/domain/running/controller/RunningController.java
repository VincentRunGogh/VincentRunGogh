package com.vincentrungogh.domain.running.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RunningController {

    private final SimpMessagingTemplate simpMessagingTemplate;



}
