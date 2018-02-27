package com.transen.tsrelease.controller;

import com.transen.tsrelease.model.TbProModule;
import com.transen.tsrelease.service.ProModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/proModule")
public class proModuleController {

    @Autowired
    ProModuleService proModuleService;

    public int getProModuleList(){
        proModuleService.selectProModule();
        return 1;
    }

    public int updateProModule(){
        TbProModule proModule = new TbProModule();
        proModuleService.updateProModule(proModule);
        return 1;
    }

    public int addProModule(){
        TbProModule proModule = new TbProModule();
        proModuleService.insertProModule(proModule);
        return 1;
    }
}
