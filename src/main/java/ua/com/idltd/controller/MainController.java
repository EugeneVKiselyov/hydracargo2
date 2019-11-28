package ua.com.idltd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.com.idltd.beans.Currentworkplace;
import ua.com.idltd.user.repository.WorkplaceRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Application home page, login, errors.
 */
@RestController
public class MainController {

    private final WorkplaceRepository workplaceRepository;
    private final Currentworkplace currentworkplace;

    private final DataSource dataSource;

    @Autowired
    public MainController(@Qualifier("dataSource") DataSource dataSource, WorkplaceRepository workplaceRepository, Currentworkplace currentworkplace) {
        this.dataSource = dataSource;
        this.workplaceRepository = workplaceRepository;
        this.currentworkplace = currentworkplace;
    }

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

    // Login success
    @RequestMapping("/login-success")
    public ResponseEntity loginSuccess(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            if (!currentworkplace.isLoad()) {
                currentworkplace.setWorkplace(workplaceRepository.queryByUsername(userDetail.getUsername()));
            }
        }
        response.sendRedirect(request.getContextPath()+"/index");
        return null;
//        return "redirect:/index";
    }

    // Login form with error
    @RequestMapping("/login-error")
    public ModelAndView loginError(ModelAndView model) {
        model.addObject("loginError", true);
        model.setViewName("login");
        return model;
    }

    //проверка на живая ли сессия
    private void closesession(){
        ServletRequestAttributes attr = (ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true); // true == allow create
        session.invalidate();
    }
    @RequestMapping(value = {"/sessioncheck"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> sessioncheck(){
        ResponseEntity result;
        try(Connection conn = dataSource.getConnection()) {
            if (!conn.isValid(30)) {
                closesession();
                result=new ResponseEntity<>("Fail connect to database", HttpStatus.INTERNAL_SERVER_ERROR);
            } else result=new ResponseEntity<>("Fail connect to database", HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            closesession();
            result=new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(result);
    }
}
