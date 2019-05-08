package com.okcoin.house.web;

import com.okcoin.house.api.service.HouseService;
import com.okcoin.house.dto.HouseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Auther: liupeng
 * @Date: 2019/4/2 18:20
 * @Description(功能描述):
 */
@Controller
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private HouseService houseService;

    @GetMapping("")
    public String index(HttpServletRequest request) {
        List<HouseDto> lateHouse = houseService.getLateHouse();
        request.setAttribute("recomHouses", lateHouse);
        String errorMsg = request.getParameter("errorMsg");
        String successMsg = request.getParameter("successMsg");
        request.setAttribute("errorMsg", errorMsg);
        request.setAttribute("successMsg", successMsg);
        return "homepage/index";
    }

}
