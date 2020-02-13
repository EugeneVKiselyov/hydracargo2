package ua.com.idltd.hydracargo.bot.telegram.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.com.idltd.hydracargo.bot.telegram.bot.entity.TelegramUser;
import ua.com.idltd.hydracargo.bot.telegram.bot.entity.TelegramVehicle;
import ua.com.idltd.hydracargo.bot.telegram.bot.entity.TelegramVehicleMessage;
import ua.com.idltd.hydracargo.bot.telegram.bot.repository.TelegramStandartMessageRepository;
import ua.com.idltd.hydracargo.bot.telegram.bot.repository.TelegramUserRepository;
import ua.com.idltd.hydracargo.bot.telegram.bot.repository.TelegramVehicleMessageRepository;
import ua.com.idltd.hydracargo.bot.telegram.bot.repository.TelegramVehicleRepository;
import ua.com.idltd.hydracargo.resource.Config;
import ua.com.idltd.hydracargo.sheduler.entity.Scheduler_TelegramTask;
import ua.com.idltd.hydracargo.sheduler.entity.TelegramBotLog;
import ua.com.idltd.hydracargo.sheduler.repository.Scheduler_TelegramTaskRepository;
import ua.com.idltd.hydracargo.sheduler.repository.TelegramBotLogRepository;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private static final Logger logger = LoggerFactory.getLogger(TelegramBot.class);
    private final TelegramBotLogRepository telegramBotLogRepository;
    private final MessageSource messageSourceBase;
    private final TelegramUserRepository telegramUserRepository;
    private final TelegramVehicleRepository telegramVehicleRepository;
    private final TelegramVehicleMessageRepository telegramVehicleMessageRepository;
    private final Scheduler_TelegramTaskRepository scheduler_telegramTaskRepository;
    private final TelegramStandartMessageRepository telegramStandartMessageRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Value("${telegram.BOT_TOKEN}")
    private String BOT_TOKEN;
    @Value("${telegram.BOT_USERNAME}")
    private String BOT_USERNAME;

    @Value("${telegram.BOT_CREATOR_ID}")
    private int BOT_CREATOR_ID;

    @Value("${telegram.INFO}")
    private String INFOMESSAGE;

    @Autowired
    public TelegramBot(@Qualifier("messageSource") MessageSource messageSourceBase, TelegramBotLogRepository telegramBotLogRepository, TelegramUserRepository telegramUserRepository, TelegramVehicleRepository telegramVehicleRepository, TelegramVehicleMessageRepository telegramVehicleMessageRepository, Scheduler_TelegramTaskRepository scheduler_telegramTaskRepository, TelegramStandartMessageRepository telegramStandartMessageRepository) {
        this.telegramBotLogRepository = telegramBotLogRepository;
        this.messageSourceBase = messageSourceBase;
        this.telegramUserRepository = telegramUserRepository;
        this.telegramVehicleRepository = telegramVehicleRepository;
        this.telegramVehicleMessageRepository = telegramVehicleMessageRepository;
        this.scheduler_telegramTaskRepository = scheduler_telegramTaskRepository;
        this.telegramStandartMessageRepository = telegramStandartMessageRepository;
    }


    public SendMessage sendMsg (String text, BotUser botUser) {
        SendMessage sendMessage = new SendMessage();
  //      sendMessage.enableMarkdown(true);
        sendMessage.disableWebPagePreview();
        sendMessage.enableHtml(true);

        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        KeyboardRow keyboardThirdRow = new KeyboardRow();

        // Добавляем кнопки в первую строчку клавиатуры
        switch (botUser.getRole_id().intValue()) {
            case Config.ROLE_NONEHANDLER : //role_none
                keyboardFirstRow.add("Info");
                keyboardFirstRow.add(messageSourceBase.getMessage("truck.register", null, botUser.getLocale()));
                break;
            case Config.ROLE_TRUCKMANAGER : //ROLE_TRUCKMANAGER
                keyboardFirstRow.add("Info");
                break;
            case Config.ROLE_TRUCKDRIVER : //role_truckdriver
                KeyboardButton keyboardButton;

                keyboardButton = new KeyboardButton();
                keyboardButton.setText(messageSourceBase.getMessage("loading", null, botUser.getLocale()));
//                keyboardButton.setRequestLocation(true);
                keyboardFirstRow.add(keyboardButton);

                keyboardButton = new KeyboardButton();
                keyboardButton.setText(messageSourceBase.getMessage("loaded", null, botUser.getLocale()));
                keyboardFirstRow.add(keyboardButton);

                keyboardButton = new KeyboardButton();
                keyboardButton.setText(messageSourceBase.getMessage("unloading", null, botUser.getLocale()));
                keyboardSecondRow.add(keyboardButton);

                keyboardButton = new KeyboardButton();
                keyboardButton.setText(messageSourceBase.getMessage("unloaded", null, botUser.getLocale()));
                keyboardSecondRow.add(keyboardButton);

                keyboardButton = new KeyboardButton();
                keyboardButton.setText(messageSourceBase.getMessage("task.resived", null, botUser.getLocale()));

                keyboardThirdRow.add(keyboardButton);
                break;
            default: keyboardFirstRow.add("Info");
        }

        // Добавляем все строчки клавиатуры в список
        if (keyboardFirstRow.size()!=0) keyboard.add(keyboardFirstRow);
        if (keyboardSecondRow.size()!=0) keyboard.add(keyboardSecondRow);
        if (keyboardThirdRow.size()!=0) keyboard.add(keyboardThirdRow);

        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
        sendMessage.setChatId(botUser.getTelegram_id());
        //если есть текст то передаем его
        sendMessage.setText(escape(text));

        return sendMessage;
    }

    //добавить нового пользователя бота
    private TelegramUser addNewUser(User user){
            TelegramUser telegramUser = telegramUserRepository.findByTelegramid(user.getId().longValue());
            //если старый то все обновляем если новый то заводим
                if (telegramUser==null) {
                    telegramUser = new TelegramUser();
                    telegramUser.tu_botname = BOT_USERNAME;
                    telegramUser.tu_telegramid = user.getId().longValue();
                    telegramUser.tu_isactive = 1L;
                    telegramUser.tu_isbot = user.getBot() ? 1L : 0L;
                    telegramUser.tu_startdate=new Date();
                }
                telegramUser.tu_username = user.getUserName();
                telegramUser.tu_firstname = user.getFirstName();
                telegramUser.tu_lastname = user.getLastName();
                telegramUser.tu_languagecode = user.getLanguageCode();
            telegramUserRepository.save(telegramUser);
            return telegramUser;
    }

    //Добавить-связать автомобиль с пользователем телеграмма
    private String addTruck(String registrationNumber, BotUser botUser){
        String returntext;
        TelegramVehicle telegramVehicle = telegramVehicleRepository.getOneVehicleBytu_id(botUser.getTu_id());
        if (telegramVehicle==null) {
            telegramVehicle = new TelegramVehicle();
            telegramVehicle.tv_registrationnumber = registrationNumber;
            telegramVehicle.tu_id = botUser.getTu_id();
            telegramVehicle.tv_isactive = 0L;
            telegramVehicleRepository.save(telegramVehicle);
            returntext = String.format(messageSourceBase.getMessage("truck.success.registered", null, botUser.getLocale()), telegramVehicle.tv_registrationnumber);
        } else returntext = String.format(messageSourceBase.getMessage("truck.already.registered", null, botUser.getLocale()), telegramVehicle.tv_registrationnumber);
      return returntext;
    }

    // Перевсти стандартный текст на русский
    private String translateStandartTextToRu(String text) {
        String result;
        Long message_code = telegramStandartMessageRepository.getCodeByText(text);
        if (message_code==null) {
            //код команды неизветен (не стандартный)
            result=text;
        } else {
            result = telegramStandartMessageRepository.getTextByCode(message_code,"ru");
        }
        return result;
    }
    //добавить переписку с водителем
    private String addTruckMessage(TelegramVehicle telegramVehicle, String text, Long direct, BotUser botUser) {
        String returntext;
        TelegramVehicleMessage telegramVehicleMessage = new TelegramVehicleMessage();
        telegramVehicleMessage.tu_id=(direct==1)?botUser.getTu_id():telegramVehicle.tu_id;
//        telegramVehicleMessage.tu_id=botUser.getTu_id();
        telegramVehicleMessage.tv_id=telegramVehicle.tv_id;
        telegramVehicleMessage.tvm_createdate=new Date();
        telegramVehicleMessage.tvm_message=text;
        telegramVehicleMessage.tvm_direct=direct;
        telegramVehicleMessageRepository.save(telegramVehicleMessage);
        returntext=String.format(messageSourceBase.getMessage("truck.message.Success.saved.id", null, botUser.getLocale()), telegramVehicleMessage.tvm_id);
        return returntext;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        if (message != null && message.hasText()) {

            TelegramUser telegramUser = telegramUserRepository.findByTelegramid(message.getFrom().getId().longValue());
            if (telegramUser == null) {
                //этого пользователя нет у нас в базе он новый
                telegramUser = addNewUser(message.getFrom());
            }

            BotUser botUser = new BotUser(telegramUser);


            switch (botUser.getRole_id().intValue()) {
                case Config.ROLE_NONEHANDLER: //Новый не подтвержденный
                    role_NoneHandler(message, botUser);
                    break;
                case Config.ROLE_TRUCKDRIVER: //ROLE_TRUCKDRIVER
                    role_truckdriverHandler(message, botUser);
                    break;
                case Config.ROLE_TRUCKMANAGER: //ROLE_TRUCKMANAGER
                    role_truckmanagerHandler(message, botUser);
                    break;
                default:
                    SendMessage sendMessage = sendMsg("Role = "+botUser.getRole_id().intValue()+"not supported",botUser);
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
            }

            //пишем сообщение в лог
            TelegramBotLog telegramBotLog = new TelegramBotLog();
            telegramBotLog.tbl_message = message.toString();
            telegramBotLog.tbl_userid = Long.valueOf(message.getFrom().getId());
            telegramBotLog.tbl_username = message.getFrom().getUserName();
            telegramBotLog.tbl_text = message.getText();
            telegramBotLogRepository.save(telegramBotLog);

        }
    }

    private void role_NoneHandler(Message message, BotUser botUser) {
        SendMessage sendMessage;
        Long message_code = getCommandCodeFromText(message.getText());

        switch (message_code.intValue()) {
            case 1: // Регистрация
                //запрашиваем номер машины
                sendMessage = sendMsg(messageSourceBase.getMessage("enter.truck.number", null, botUser.getLocale()),botUser);
                break;
            case 7: //Информация
                sendMessage = sendMsg(messageSourceBase.getMessage(INFOMESSAGE, null, botUser.getLocale()), botUser);
                break;
            default:
                String command = telegramBotLogRepository.getPreviosMessage(Long.valueOf(message.getFrom().getId()));
                Long command_code = getCommandCodeFromText(command);
                switch (command_code.intValue()) {
                    case 1 : // Регистрация
                        // считаем что в message номер машины
                        String registrationNumber = message.getText().toUpperCase();
                        sendMessage = sendMsg(addTruck(registrationNumber,botUser),botUser);
                        break;
                    default:
                        sendMessage = sendMsg(messageSourceBase.getMessage(INFOMESSAGE, null, botUser.getLocale()), botUser);
                        break;
                }
                break;
        }
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private Long getCommandCodeFromText(String text) {
        Long message_code = telegramStandartMessageRepository.getCodeByText(text);
        if (message_code==null) {
            //код команды неизветен (не стандартный)
            message_code = 0L;
        }
        return message_code;
    }

    private void role_truckmanagerHandler(Message message, BotUser botUser) {
        SendMessage sendMessage;
        Long message_code = getCommandCodeFromText(message.getText());

        switch (message_code.intValue()) {
            case 7: //Информация
                sendMessage = sendMsg(messageSourceBase.getMessage(INFOMESSAGE, null, botUser.getLocale()), botUser);
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
            default: //Отправляем то что набрали последнему водителю
                Scheduler_TelegramTask scheduler_telegramTask = scheduler_telegramTaskRepository.getLastDoneMessageFromVehicle(botUser.getTelegram_id());
                if (scheduler_telegramTask != null) {
                    List<TelegramVehicle> telegramVehicleList = telegramVehicleRepository.getVehicleByTelegramIDUser(scheduler_telegramTask.tt_sourcetelegramid);
                    for (TelegramVehicle telegramVehicle : telegramVehicleList) {
                        addTruckMessage(telegramVehicle, message.getText(), 1L, botUser);
                        // дублируем то что отправили всем менеджерам
                        StoredProcedureQuery AddMessageQuery = entityManager
                                .createStoredProcedureQuery("PKG_TELEGRAM.pr_AddMsgFromUserToRole")
                                .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                                .registerStoredProcedureParameter(2, int.class, ParameterMode.IN)
                                .registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
                                .setParameter(1, botUser.getTu_id()) //от кого отправляем
                                .setParameter(2, Config.ROLE_TRUCKMANAGER) //всем менеджерам 5 - роль менеджер
                                .setParameter(3, botUser.getFirstName()+" "+botUser.getLastName()+"-->"+telegramVehicle.tv_registrationnumber+":"+message.getText());
                        AddMessageQuery.execute();
                    }
                } else {
                    sendMessage = sendMsg("I do not know to whom to send this message", botUser);
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
        }

    }

    private void role_truckdriverHandler(Message message, BotUser botUser) {
        SendMessage sendMessage;
        TelegramVehicle telegramVehicle = telegramVehicleRepository.getOneVehicleBytu_id(botUser.getTu_id());
        if (telegramVehicle != null) {
            //трак привязан к пользователю - записываем от него сообщение на рассылку по менеджерам
            sendMessage = sendMsg(addTruckMessage(telegramVehicle, message.getText(),0L, botUser),botUser);
        } else {
            //ни одного трака не привязанно к пользователю - пишем пользователю что бы подождал регистрации
            sendMessage = sendMsg(messageSourceBase.getMessage("truck.No.vehicles.attached", null, botUser.getLocale()),botUser);
        }

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    public static String escape(String s) {
        StringBuilder builder = new StringBuilder();
        boolean previousWasASpace = false;
        for( char c : s.toCharArray() ) {
            if( c == ' ' ) {
                if( previousWasASpace ) {
                    builder.append("&nbsp;");
                    previousWasASpace = false;
                    continue;
                }
                previousWasASpace = true;
            } else {
                previousWasASpace = false;
            }
            switch(c) {
                case '<': builder.append("&lt;"); break;
                case '>': builder.append("&gt;"); break;
                case '&': builder.append("&amp;"); break;
                case '"': builder.append("&quot;"); break;
//                case '\n': builder.append("<br>"); break;
                // We need Tab support here, because we print StackTraces as HTML
//                case '\t': builder.append("&nbsp; &nbsp; &nbsp;"); break;
                default:
                    if( c < 128 ) {
                        builder.append(c);
                    } else {
                        builder.append("&#").append((int)c).append(";");
                    }
            }
        }
        return builder.toString();
    }
}
