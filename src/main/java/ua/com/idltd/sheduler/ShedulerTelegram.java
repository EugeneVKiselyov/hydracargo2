package ua.com.idltd.sheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.com.idltd.sheduler.entity.Scheduler_TelegramTask;
import ua.com.idltd.sheduler.repository.Scheduler_TelegramTaskRepository;
import ua.com.idltd.sheduler.telegram.bot.TelegramBot;

import java.util.Date;
import java.util.List;

@Component
public class ShedulerTelegram {

    private final Scheduler_TelegramTaskRepository scheduler_telegramTaskRepository;
    private final TelegramBot telegramBot;

    @Autowired
    public ShedulerTelegram(TelegramBot telegramBot, Scheduler_TelegramTaskRepository scheduler_telegramTaskRepository) {
        this.scheduler_telegramTaskRepository = scheduler_telegramTaskRepository;
        this.telegramBot=telegramBot;
    }

    @Scheduled(fixedRateString = "${scheduler.telegram.fixedRate.in.milliseconds}")
    public void sendAll(){
        List<Scheduler_TelegramTask> telegramTaskList = scheduler_telegramTaskRepository.queryByStateisNull();
        for (Scheduler_TelegramTask tt : telegramTaskList) {
            SendMessage response = new SendMessage();
            Long chatId = new Long(tt.tt_chatid);
            response.setChatId(chatId);
            response.setText(tt.tt_text);

            try {
                telegramBot.execute(response);
                tt.tt_state = "done";
            } catch (TelegramApiException e) {
                tt.tt_error = e.getMessage();
                tt.tt_state = "error";

            }

            tt.tt_done = new Date();
            scheduler_telegramTaskRepository.save(tt);
        }

    }
}
