package cn.trasen.tsrelease.controller;

import cn.trasen.tsrelease.model.TbModRely;
import cn.trasen.tsrelease.service.ModRelyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/modRely")
public class ModRelyController {

    @Autowired
    ModRelyService service;

    public void getModRelyList(){
        service.select();
    }
    public int updateModRelyList(){
        TbModRely modRely = new TbModRely();
        service.updateModRely(modRely);

        return 1;
    }

    public int addModRely(){
        TbModRely modRely = new TbModRely();
        service.insertModRely(modRely);
        return 1;
    }

}
