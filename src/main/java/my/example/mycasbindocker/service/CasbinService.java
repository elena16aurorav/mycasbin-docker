package my.example.mycasbindocker.service;

import my.example.mycasbindocker.casbin.CasbinAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CasbinService {

    @Autowired
    CasbinAdapter casbinAdapter;

    public void test(){
        casbinAdapter.loadPolicy(null);
//        Enforcer e = new Enforcer();
//        e.setAdapter(casbinAdapter);
//        e.loadPolicy();
//        e.getAllRoles();

    }


}
