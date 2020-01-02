package ua.com.idltd.hydracargo.controller.telegram_user;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.com.idltd.hydracargo.bot.telegram.web.entity.lists.RolesList;
import ua.com.idltd.hydracargo.bot.telegram.web.entity.telegram_user.DetailTelegramUser;
import ua.com.idltd.hydracargo.bot.telegram.web.repository.lists.RoleListRepository;
import ua.com.idltd.hydracargo.bot.telegram.web.repository.telegram_user.DetailTelegramUserRepository;
import ua.com.idltd.hydracargo.bot.telegram.web.repository.telegram_user.MenuTelegramUserRepository;
import ua.com.idltd.hydracargo.utils.JSONDatatable;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

@RestController
@RequestMapping("/telegramuser")
public class TelegramUserController {

    private final MenuTelegramUserRepository telegramUserRepository;
    private final DetailTelegramUserRepository detailTelegramUserRepository;
    private final RoleListRepository roleListRepository;

    public TelegramUserController(
            MenuTelegramUserRepository telegramUserRepository,
            DetailTelegramUserRepository detailTelegramUserRepository,
            RoleListRepository roleListRepository
    ) {
        this.telegramUserRepository = telegramUserRepository;
        this.detailTelegramUserRepository = detailTelegramUserRepository;
        this.roleListRepository = roleListRepository;
    }

    @Secured({ "ROLE_ADMIN" })
    @RequestMapping(value = {"","/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView index(
    ){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/telegram_user/cover");
        return mav;
    }

    @PostMapping("/gettable")
    public JSONDatatable gettable(@RequestParam(name = "tu_id", required = false) Long tu_id) {
        JSONDatatable result = new JSONDatatable();
        //if (tu_id != null) {
            result.setData(telegramUserRepository.findByTelegramAll(new Long(1), new Long(1)));
        //}
        return result;
    }

    @RequestMapping("/detail")
    public ModelAndView detail(@RequestParam(value="mode") Long mode,
                               @RequestParam(value="tu_id") Long tu_id,
  
                               @RequestParam(value="telegram_user_table_order_column") Long telegram_user_table_order_column,
                               @RequestParam(value="telegram_user_table_order_type") String telegram_user_table_order_type,
                               @RequestParam(value="telegram_user_table_search") String telegram_user_table_search,
                               @RequestParam(value="telegram_user_table_pagelen") Long telegram_user_table_pagelen,
                               @RequestParam(value="telegram_user_table_page") Long telegram_user_table_page
    ) {
        ModelAndView mav = new ModelAndView();
        List<DetailTelegramUser> editDetailTelegramUser;
        List<DetailTelegramUser> delDetailTelegramUser;
        List<RolesList> roleList;
        mav.addObject("mode", mode);

        if(mode == 0){
            roleList = roleListRepository.getAllRoles();
            mav.addObject("role_list", roleList);
        }
        else if(mode == 1){
            editDetailTelegramUser = detailTelegramUserRepository.DetailTelegramByID(new Long(1), new Long(1), tu_id);
            mav.addObject("tu_id", editDetailTelegramUser.get(0).tu_id);
            mav.addObject("tu_botname", editDetailTelegramUser.get(0).tu_botname);
            mav.addObject("tu_username", editDetailTelegramUser.get(0).tu_username);
            mav.addObject("tu_firstname", editDetailTelegramUser.get(0).tu_firstname);
            mav.addObject("tu_lastname", editDetailTelegramUser.get(0).tu_lastname);
            mav.addObject("tu_languagecode", editDetailTelegramUser.get(0).tu_languagecode);
            mav.addObject("tu_isbot", editDetailTelegramUser.get(0).tu_isbot);
            mav.addObject("tu_telegramid", editDetailTelegramUser.get(0).tu_telegramid);
            mav.addObject("tu_startdate", editDetailTelegramUser.get(0).tu_startdate);
            mav.addObject("tu_enddate", editDetailTelegramUser.get(0).tu_enddate);
            mav.addObject("tu_isactive", editDetailTelegramUser.get(0).tu_isactive);

            mav.addObject("rol_id", editDetailTelegramUser.get(0).rol_id);
            roleList = roleListRepository.getAllRoles();
            mav.addObject("role_list", roleList);
        }
        else{
            delDetailTelegramUser = detailTelegramUserRepository.DetailTelegramByID(new Long(1), new Long(1), tu_id);
            mav.addObject("tu_id", delDetailTelegramUser.get(0).tu_id);
            mav.addObject("tu_botname", delDetailTelegramUser.get(0).tu_botname);
            mav.addObject("tu_username", delDetailTelegramUser.get(0).tu_username);
            mav.addObject("tu_firstname", delDetailTelegramUser.get(0).tu_firstname);
            mav.addObject("tu_lastname", delDetailTelegramUser.get(0).tu_lastname);
            mav.addObject("tu_languagecode", delDetailTelegramUser.get(0).tu_languagecode);
            mav.addObject("tu_isbot", delDetailTelegramUser.get(0).tu_isbot);
            mav.addObject("tu_telegramid", delDetailTelegramUser.get(0).tu_telegramid);
            mav.addObject("tu_startdate", delDetailTelegramUser.get(0).tu_startdate);
            mav.addObject("tu_enddate", delDetailTelegramUser.get(0).tu_enddate);
            mav.addObject("tu_isactive", delDetailTelegramUser.get(0).tu_isactive);

            mav.addObject("rol_id", delDetailTelegramUser.get(0).rol_id);
            roleList = roleListRepository.getAllRoles();
            mav.addObject("role_list", roleList);
        }

        mav.addObject("telegram_user_table_order_column", telegram_user_table_order_column);
        mav.addObject("telegram_user_table_order_type", telegram_user_table_order_type);
        mav.addObject("telegram_user_table_search", telegram_user_table_search);
        mav.addObject("telegram_user_table_pagelen", telegram_user_table_pagelen);
        mav.addObject("telegram_user_table_page", telegram_user_table_page);

        mav.setViewName("/telegram_user/detail");
        return mav;
    }

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping("/model")
    public ModelAndView model(
            @RequestParam(value="mode") Long mode,
            @RequestParam(value="tu_id", required = false) Long tu_id,

            @RequestParam(value="tu_botname", required = false) String tu_botname,
            @RequestParam(value="tu_username", required = false) String tu_username,
            @RequestParam(value="tu_firstname", required = false) String tu_firstname,
            @RequestParam(value="tu_lastname", required = false) String tu_lastname,
            @RequestParam(value="tu_languagecode", required = false) String tu_languagecode,
            @RequestParam(value="tu_isbot", required = false) Boolean tu_isbot,
            @RequestParam(value="tu_telegramid", required = false) Long tu_telegramid,
            @RequestParam(value="rol_id", required = false) Long rol_id,

            @RequestParam(value="telegram_user_table_order_column", required = false) Long telegram_user_table_order_column,
            @RequestParam(value="telegram_user_table_order_type", required = false) String telegram_user_table_order_type,
            @RequestParam(value="telegram_user_table_search", required = false) String telegram_user_table_search,
            @RequestParam(value="telegram_user_table_pagelen", required = false) Long telegram_user_table_pagelen,
            @RequestParam(value="telegram_user_table_page", required = false) Long telegram_user_table_page
    ) {
        ModelAndView mav = new ModelAndView();
        Long isBot;

        if(tu_isbot == null){ isBot = new Long("0");}
        else{ isBot = new Long("1");}

        try {
            switch (mode.intValue()) {
                case 0:
                    StoredProcedureQuery AddTelegramUserQuery = entityManager
                            .createStoredProcedureQuery("PKG_TELEGRAM.pr_AddTelegrammUser")
                            .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                            .registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
                            .registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
                            .registerStoredProcedureParameter(4, String.class, ParameterMode.IN)
                            .registerStoredProcedureParameter(5, String.class, ParameterMode.IN)
                            .registerStoredProcedureParameter(6, String.class, ParameterMode.IN)
                            .registerStoredProcedureParameter(7, String.class, ParameterMode.IN)
                            .registerStoredProcedureParameter(8, Long.class, ParameterMode.IN)
                            .registerStoredProcedureParameter(9, Long.class, ParameterMode.IN)
                            .registerStoredProcedureParameter(10, Long.class, ParameterMode.IN)
                            .setParameter(1, new Long(1))
                            .setParameter(2, new Long(1))
                            .setParameter(3, tu_botname)
                            .setParameter(4, tu_username)
                            .setParameter(5, tu_firstname)
                            .setParameter(6, tu_lastname)
                            .setParameter(7, tu_languagecode)
                            .setParameter(8, isBot)
                            .setParameter(9, tu_telegramid)
                            .setParameter(10, rol_id)
                            ;
                    AddTelegramUserQuery.execute();
                    break;
                case 1:
                    StoredProcedureQuery EditTelegramUserQuery = entityManager
                            .createStoredProcedureQuery("PKG_TELEGRAM.pr_EditTelegrammUser")
                            .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                            .registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
                            .registerStoredProcedureParameter(3, Long.class, ParameterMode.IN)
                            .registerStoredProcedureParameter(4, String.class, ParameterMode.IN)
                            .registerStoredProcedureParameter(5, String.class, ParameterMode.IN)
                            .registerStoredProcedureParameter(6, String.class, ParameterMode.IN)
                            .registerStoredProcedureParameter(7, String.class, ParameterMode.IN)
                            .registerStoredProcedureParameter(8, String.class, ParameterMode.IN)
                            .registerStoredProcedureParameter(9, Long.class, ParameterMode.IN)
                            .registerStoredProcedureParameter(10, Long.class, ParameterMode.IN)
                            .registerStoredProcedureParameter(11, Long.class, ParameterMode.IN)
                            .setParameter(1, new Long(1))
                            .setParameter(2, new Long(1))
                            .setParameter(3, tu_id)
                            .setParameter(4, tu_botname)
                            .setParameter(5, tu_username)
                            .setParameter(6, tu_firstname)
                            .setParameter(7, tu_lastname)
                            .setParameter(8, tu_languagecode)
                            .setParameter(9, isBot)
                            .setParameter(10, tu_telegramid)
                            .setParameter(11, rol_id)
                            ;
                    EditTelegramUserQuery.execute();
                    break;
                case 2:
                    StoredProcedureQuery DelTelegramUserQuery = entityManager
                            .createStoredProcedureQuery("PKG_TELEGRAM.pr_DelTelegrammUser")
                            .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                            .registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
                            .registerStoredProcedureParameter(3, Long.class, ParameterMode.IN)
                            .setParameter(1, new Long(1))
                            .setParameter(2, new Long(1))
                            .setParameter(3, tu_id)
                            ;
                    DelTelegramUserQuery.execute();
                    break;
                default:
                    throw new Exception("Mode:"+mode.toString()+" undefined");
            }
            mav.addObject("error_code",0);
        } catch (Exception e) {
            mav.addObject("error_code",500);
            mav.addObject("exception",e.getLocalizedMessage());
        }

        //Параметры на форме
        mav.addObject("mode", mode);

        mav.addObject("telegram_user_table_order_column", telegram_user_table_order_column);
        mav.addObject("telegram_user_table_order_type", telegram_user_table_order_type);
        mav.addObject("telegram_user_table_search", telegram_user_table_search);
        mav.addObject("telegram_user_table_pagelen", telegram_user_table_pagelen);
        mav.addObject("telegram_user_table_page", telegram_user_table_page);

        mav.setViewName("/telegram_user/detail");
        return mav;
    }
}
