package ua.com.idltd.hydracargo.sheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.com.idltd.hydracargo.bot.telegram.bot.BotUser;
import ua.com.idltd.hydracargo.bot.telegram.bot.TelegramBot;
import ua.com.idltd.hydracargo.bot.telegram.bot.entity.TelegramUser;
import ua.com.idltd.hydracargo.bot.telegram.bot.repository.TelegramUserRepository;
import ua.com.idltd.hydracargo.sheduler.entity.Scheduler_TelegramTask;
import ua.com.idltd.hydracargo.sheduler.repository.Scheduler_TelegramTaskRepository;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;

@Component
public class ShedulerTelegram {

    private final Scheduler_TelegramTaskRepository scheduler_telegramTaskRepository;
    private final TelegramUserRepository telegramUserRepository;
    private final TelegramBot telegramBot;

    @Autowired
    public ShedulerTelegram(TelegramBot telegramBot, Scheduler_TelegramTaskRepository scheduler_telegramTaskRepository, TelegramUserRepository telegramUserRepository) {
        this.scheduler_telegramTaskRepository = scheduler_telegramTaskRepository;
        this.telegramBot=telegramBot;
        this.telegramUserRepository = telegramUserRepository;
    }

    @Scheduled(fixedRateString = "${scheduler.telegram.fixedRate.in.milliseconds}")
    public void sendAll(){
        if (scheduler_telegramTaskRepository.countByStateisNull()!=0) {
            List<Scheduler_TelegramTask> telegramTaskList = scheduler_telegramTaskRepository.queryByStateisNull();
            for (Scheduler_TelegramTask tt : telegramTaskList) {
                TelegramUser telegramUser = telegramUserRepository.findByTelegramid(tt.tt_chatid);
                BotUser botUser= new BotUser(telegramUser);
                SendMessage response = telegramBot.sendMsg(tt.tt_text,botUser);
                try {
                    telegramBot.execute(response);
                    tt.tt_state = "done";
                } catch (TelegramApiException e) {
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    e.printStackTrace(pw);
                    tt.tt_error = sw.toString();
                    tt.tt_state = "error";

                }
                tt.tt_done = new Date();
                scheduler_telegramTaskRepository.save(tt);
            }
        }
    }
}
