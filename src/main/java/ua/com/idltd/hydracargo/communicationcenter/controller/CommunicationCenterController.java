package ua.com.idltd.hydracargo.communicationcenter.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
@RequestMapping("/communicationcenter")
public class CommunicationCenterController {

    @RequestMapping(value = {"","/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView index(
    ){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/communicationcenter/cover");
        return mav;
    }
}
