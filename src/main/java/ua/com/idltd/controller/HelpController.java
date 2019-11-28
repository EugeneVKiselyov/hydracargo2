package ua.com.idltd.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/help")
public class HelpController {

    @RequestMapping(value = {"","/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView login(ModelAndView model
    ){
        model.setViewName("/help/cover");
        return model;
    }

}
