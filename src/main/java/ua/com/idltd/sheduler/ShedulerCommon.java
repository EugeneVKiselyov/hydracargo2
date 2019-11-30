package ua.com.idltd.sheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ua.com.idltd.sheduler.entity.Scheduler_TelegramTask;
import ua.com.idltd.sheduler.entity.Sheduler_Event;
import ua.com.idltd.sheduler.repository.Scheduler_TelegramTaskRepository;
import ua.com.idltd.sheduler.repository.Sheduler_EventRepository;
import ua.com.idltd.sheduler.telegram.Message;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;

@Component
public class ShedulerCommon {

    private static final Logger logger = LoggerFactory.getLogger(ShedulerCommon.class);

    private final Sheduler_EventRepository sheduler_eventRepository;
    private final Scheduler_TelegramTaskRepository scheduler_telegramTaskRepository;

    public ShedulerCommon(Sheduler_EventRepository sheduler_eventRepository, Scheduler_TelegramTaskRepository scheduler_telegramTaskRepository) {
        this.sheduler_eventRepository = sheduler_eventRepository;
        this.scheduler_telegramTaskRepository = scheduler_telegramTaskRepository;
    }

    @Scheduled(fixedRateString = "${scheduler.common.fixedRate.in.milliseconds}")
    public void run()  {
        List<Sheduler_Event> shedulerEventList = sheduler_eventRepository.queryByStateisNull();
        for (Sheduler_Event se:shedulerEventList){
            try {
            switch (se.set_id.intValue()) {
                case 1 : se.se_result_detail = mailit(se);
                         se.se_state = "done";
                    break;
                case 2 : se.se_worker_id = sendIntoTelegramBot(se);
                         se.se_state = "done";
                    break;
                default: throw new UnsupportedOperationException();
            }
            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                se.se_result_detail = sw.toString();
                se.se_state = "error";
            }
            se.se_done=new Date();
            sheduler_eventRepository.save(se);
        }
    }

    private Long sendIntoTelegramBot(Sheduler_Event se) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Message message = objectMapper.readValue(se.se_todo, Message.class);
        Scheduler_TelegramTask tt = new Scheduler_TelegramTask();
        tt.tt_chatid=message.chatId;
        tt.tt_text=message.text;
        scheduler_telegramTaskRepository.save(tt);
        return tt.tt_id;
    }

    private String mailit(Sheduler_Event se) throws IOException {
        logger.info("send to mail");
        return "send to mail";
    }
}
