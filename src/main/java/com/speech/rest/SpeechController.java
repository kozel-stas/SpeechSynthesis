package com.speech.rest;

import com.speech.service.SpeechSynthesis;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/speech")
public class SpeechController {

    private final SpeechSynthesis speechSynthesis;

    public SpeechController(SpeechSynthesis speechSynthesis) {
        this.speechSynthesis = speechSynthesis;
    }

    @RequestMapping(
            value = "/audio",
            method = RequestMethod.POST
    )
    public HttpEntity<byte[]> synthesis(@RequestBody String text) {
        byte[] data = speechSynthesis.synthesis(text);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("audio", "wav"));
        header.setContentLength(data.length);
        return new HttpEntity<>(data, header);
    }

}

