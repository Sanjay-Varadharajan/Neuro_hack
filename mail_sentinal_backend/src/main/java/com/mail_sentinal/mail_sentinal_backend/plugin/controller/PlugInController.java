package com.mail_sentinal.mail_sentinal_backend.plugin.controller;


import com.mail_sentinal.mail_sentinal_backend.model.FraudResult;
import com.mail_sentinal.mail_sentinal_backend.plugin.service.PlugInService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PlugInController {

    private final PlugInService plugInService;


    @GetMapping("/view/result/plugin")
    public List<FraudResult> viewAllResult(){
        return plugInService.viewAllResult();
    }
}
