package ua.com.idltd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.unbescape.html.HtmlEscape;

import javax.servlet.http.HttpServletRequest;

/**
 * Application home page, login, errors.
 */
@Controller
public class MainController {

    @RequestMapping(value = {"/", "/index","/index.html"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView index(ModelAndView model
    ){
        model.setViewName("index");
        return model;
    }

    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView pageLogin(ModelAndView model) {
        model.setViewName("login");
        return model;
    }

    // Login form with error
    @RequestMapping("/login-error.html")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login.html";
    }

}
