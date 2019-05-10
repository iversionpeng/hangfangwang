package com.okcoin.house.web;

import com.okcoin.house.api.service.HouseService;
import com.okcoin.house.dto.HouseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: liupeng
 * @date: 2019/4/2 18:20
 * @description(功能描述):
 */
@Controller
public class IndexController {

    @Autowired
    private HouseService houseService;

    @GetMapping("/index")
    public String index(HttpServletRequest request) {
        List<HouseDto> lateHouse = houseService.getLateHouse();
        request.setAttribute("recomHouses", lateHouse);
        return "homepage/index";
    }

    @GetMapping("")
    public String index() {
        return "redirect:/index";
    }

}
