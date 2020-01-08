package ua.com.idltd.hydracargo.controller.telegram_line;

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
import ua.com.idltd.hydracargo.bot.telegram.web.repository.telegram_list.MenuTelegramLineRepository;
import ua.com.idltd.hydracargo.user.entity.Users;
import ua.com.idltd.hydracargo.user.repository.UsersRepository;
import ua.com.idltd.hydracargo.utils.JSONDatatable;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

@RestController
@RequestMapping("/telegram_line")
public class TelegramLineController {

    private final MenuTelegramLineRepository menuTelegramLineRepository;
    private final TelegramVehicleRepository telegramVehicleRepository;
    private final TelegramUserRepository telegramUserRepository;
    private final UsersRepository usersRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public TelegramLineController(
            MenuTelegramLineRepository menuTelegramLineRepository,
            TelegramVehicleRepository telegramVehicleRepository,
            TelegramUserRepository telegramUserRepository,
            UsersRepository usersRepository
    ) {
        this.menuTelegramLineRepository = menuTelegramLineRepository;
        this.telegramVehicleRepository = telegramVehicleRepository;
        this.telegramUserRepository = telegramUserRepository;
        this.usersRepository = usersRepository;
    }

    @Secured({ "ROLE_ADMIN" })
    @RequestMapping(value = {"","/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView index(
            @RequestParam(name = "tv_id", required = false) Long tv_id
    ){
        ModelAndView mav = new ModelAndView();

        mav.addObject("tv_id", tv_id);
        mav.setViewName("/telegram_line/cover");
        return mav;
    }

    @PostMapping("/get_main_table")
    public JSONDatatable get_main_table(
    ) {
           JSONDatatable result = new JSONDatatable();
                result.setData(menuTelegramLineRepository.findByTelegramLine(new Long(1), new Long(1)));
        return result;
    }

    @PostMapping("/add_messages")
    public ModelAndView add_messages(
            @RequestParam(name = "tv_id") Long tv_id,
            @RequestParam(name = "tvm_message") String tvm_message
    ) throws Exception {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        Users user = usersRepository.findByUser_username(userDetail.getUsername());
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
}
