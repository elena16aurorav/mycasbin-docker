package my.example.mycasbindocker.controller;

import my.example.mycasbindocker.entity.CasbinRuleEntity;
import my.example.mycasbindocker.service.CasbinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CasbinController {
    @Autowired
    CasbinService casbinService;

    @RequestMapping("/select")
    public List<CasbinRuleEntity> select(){
        casbinService.test();

        return null;
    }
}
