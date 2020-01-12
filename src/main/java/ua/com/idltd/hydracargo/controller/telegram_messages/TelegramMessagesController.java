package ua.com.idltd.hydracargo.controller.telegram_messages;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.com.idltd.hydracargo.bot.telegram.bot.entity.TelegramUser;
import ua.com.idltd.hydracargo.bot.telegram.bot.entity.TelegramVehicle;
import ua.com.idltd.hydracargo.bot.telegram.bot.repository.TelegramUserRepository;
import ua.com.idltd.hydracargo.bot.telegram.bot.repository.TelegramVehicleRepository;
import ua.com.idltd.hydracargo.bot.telegram.web.repository.lists.BotsListRepository;
import ua.com.idltd.hydracargo.bot.telegram.web.repository.lists.RoleListRepository;
import ua.com.idltd.hydracargo.bot.telegram.web.repository.telegram_cars.MenuTelegramCarsRepository;
import ua.com.idltd.hydracargo.bot.telegram.web.repository.telegram_cars.MenuTelegramMessagesRepository;
import ua.com.idltd.hydracargo.bot.telegram.web.repository.telegram_user.DetailTelegramUserRepository;
import ua.com.idltd.hydracargo.user.entity.User;
import ua.com.idltd.hydracargo.user.repository.UsersRepository;
import ua.com.idltd.hydracargo.utils.JSONDatatable;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

@RestController
@RequestMapping("/telegram_messages")
public class TelegramMessagesController {

    private final MenuTelegramCarsRepository menuTelegramCarsRepository;
    private final MenuTelegramMessagesRepository menuTelegramMessagesRepository;
    private final UsersRepository usersRepository;
    private final DetailTelegramUserRepository detailTelegramUserRepository;
    private final BotsListRepository botsListRepository;
    private final RoleListRepository roleListRepository;
    private final TelegramVehicleRepository telegramVehicleRepository;
    private final TelegramUserRepository telegramUserRepository;



    @PersistenceContext
    private EntityManager entityManager;

    public TelegramMessagesController(
            MenuTelegramCarsRepository menuTelegramCarsRepository,
            MenuTelegramMessagesRepository menuTelegramMessagesRepository,
            UsersRepository usersRepository,
            DetailTelegramUserRepository detailTelegramUserRepository,
            BotsListRepository botsListRepository,
            RoleListRepository roleListRepository,
            TelegramVehicleRepository telegramVehicleRepository, TelegramUserRepository telegramUserRepository) {
        this.menuTelegramCarsRepository = menuTelegramCarsRepository;
        this.menuTelegramMessagesRepository = menuTelegramMessagesRepository;
        this.usersRepository = usersRepository;
        this.detailTelegramUserRepository = detailTelegramUserRepository;
        this.botsListRepository = botsListRepository;
        this.roleListRepository = roleListRepository;
        this.telegramVehicleRepository = telegramVehicleRepository;
        this.telegramUserRepository = telegramUserRepository;
    }

    @Secured({ "ROLE_ADMIN" })
    @RequestMapping(value = {"","/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView index(
            @RequestParam(name = "tv_id", required = false) Long tv_id
    ){
        ModelAndView mav = new ModelAndView();

        mav.addObject("tv_id", tv_id);
        mav.setViewName("/telegram_messages/cover");
        return mav;
    }

    @PostMapping("/get_cars_table")
    public JSONDatatable get_cars_table(
            @RequestParam(name = "tv_id", required = false) Long tv_id
    ) {
           JSONDatatable result = new JSONDatatable();
                result.setData(menuTelegramCarsRepository.findByTelegramCars(new Long(1), new Long(1)));
        return result;
    }

    @PostMapping("/get_messages_table")
    public JSONDatatable get_messages_table(
            @RequestParam(name = "tv_id", required = false) Long tv_id
    ) {
        JSONDatatable result = new JSONDatatable();
        if(tv_id != null) {
            result.setData(menuTelegramMessagesRepository.findByTelegramMessages(new Long(1), new Long(1), tv_id));
        }
        return result;
    }


    @PostMapping("/add_messages")
    public ModelAndView add_messages(
            @RequestParam(name = "tv_id") Long tv_id,
            @RequestParam(name = "tvm_message") String tvm_message
    ) throws Exception {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User user = usersRepository.findByUser_username(userDetail.getUsername());
        if (user.getTu_id() == null) throw new Exception("User "+user.getUser_username()+" do not have default tu_id (telegram user)");

        StoredProcedureQuery AddMessageQuery = entityManager
                .createStoredProcedureQuery("PKG_TELEGRAM.pr_AddMsgFromManagerToTruck")
                .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
                .setParameter(1, user.getTu_id())
                .setParameter(2, tv_id)
                .setParameter(3, tvm_message)
                ;
        AddMessageQuery.execute();

        // дублируем то что отправили всем менеджерам
        TelegramVehicle telegramVehicle =  telegramVehicleRepository.findById(tv_id).orElse(null);
        TelegramUser telegramUser = telegramUserRepository.findById(user.getTu_id()).orElse(null);
        String vmessage = String.format("%s %s-->%s%n%s",telegramUser.tu_firstname,telegramUser.tu_lastname,telegramVehicle.tv_registrationnumber,tvm_message);
        StoredProcedureQuery AddMessageRoleQuery = entityManager
                .createStoredProcedureQuery("PKG_TELEGRAM.pr_AddMsgFromUserToRole")
                .registerStoredProcedureParameter("vTU_ID", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("vROL_ID", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("vTVM_MESSAGE", String.class, ParameterMode.IN)
                .setParameter("vTU_ID", user.getTu_id()) //от кого отправляем
                .setParameter("vROL_ID", 5L) //всем менеджерам 5 - роль менеджер
                .setParameter("vTVM_MESSAGE", vmessage)
                ;
        AddMessageRoleQuery.execute();

        return null;
    }

    @GetMapping("/get_bots_list")
    public JSONDatatable get_bots_list(
    ) {
        JSONDatatable result = new JSONDatatable();

        result.setData(botsListRepository.getAllBotsList());
        return result;
    }

    @GetMapping("/get_send_role_list")
    public JSONDatatable get_send_role_list(
    ) {
        JSONDatatable result = new JSONDatatable();

        result.setData(roleListRepository.getSendListRoles());
        return result;
    }

    @PostMapping("/add_usertorole_messages")
    public ModelAndView add_usertorole_messages(
            @RequestParam(name = "rol_id") Long rol_id,
            @RequestParam(name = "tvm_message") String tvm_message
    ) throws Exception {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User user = usersRepository.findByUser_username(userDetail.getUsername());
        if (user.getTu_id() == null) throw new Exception("User "+user.getUser_username()+" do not have default tu_id (telegram user)");

        StoredProcedureQuery AddMessageQuery = entityManager
                .createStoredProcedureQuery("PKG_TELEGRAM.pr_AddMsgFromUserToRole")
                .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
                .setParameter(1, user.getTu_id())
                .setParameter(2, rol_id)
                .setParameter(3, tvm_message)
                ;
        AddMessageQuery.execute();
        return null;
    }

}
