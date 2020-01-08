package ua.com.idltd.hydracargo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import ua.com.idltd.hydracargo.beans.Currentworkplace;
import ua.com.idltd.hydracargo.graph.entity.MessageDayGraph;
import ua.com.idltd.hydracargo.graph.repository.MessageDayGraphRepository;
import ua.com.idltd.hydracargo.user.repository.WorkplaceRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Application home page, login, errors.
 */
@RestController
public class IndexController {

    private final WorkplaceRepository workplaceRepository;
    private final Currentworkplace currentworkplace;
    private final MessageDayGraphRepository messageDayGraphRepository;

    private final DataSource dataSource;

    @Autowired
    public IndexController(
            @Qualifier("dataSource") DataSource dataSource,
            WorkplaceRepository workplaceRepository,
            Currentworkplace currentworkplace,
            MessageDayGraphRepository messageDayGraphRepository
    ) {
        this.dataSource = dataSource;
        this.workplaceRepository = workplaceRepository;
        this.currentworkplace = currentworkplace;
        this.messageDayGraphRepository = messageDayGraphRepository;
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
            } else result=new ResponseEntity<>("Active", HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            closesession();
            result=new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping(value = {"/get_message_day_graph"})
    public List<MessageDayGraph> get_message_day_graph(
            @RequestParam(name = "start_date", required = false, defaultValue = "01.12.2019") String start_date,
            @RequestParam(name = "end_date", required = false, defaultValue = "31.12.2019") String end_date
    ) {
        List<MessageDayGraph> result = new ArrayList<>();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            String authname = auth.getName();

            result = messageDayGraphRepository.queryMessageDayGraphByID(new Long(1), start_date, end_date);
        }
        return result;
    }
}
