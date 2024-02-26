package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.InfoService;

@RestController
@RequestMapping("info/")
public class InfoController {
    private  final InfoService infoService;

    public InfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    @Value("${server.port}")
    Integer port;

    @GetMapping("port")
    public Integer getPort() {
        return port;
    }

    @GetMapping("sum")
    public String getSum() {
        return infoService.getSum();
    }
}
