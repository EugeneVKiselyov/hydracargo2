package ua.com.idltd.sheduler.telegram.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.com.idltd.sheduler.entity.TelegramBotLog;
import ua.com.idltd.sheduler.repository.TelegramBotLogRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private static final Logger logger = LoggerFactory.getLogger(TelegramBot.class);
    private final TelegramBotLogRepository telegramBotLogRepository;

//    private final String shipmentRegex="GE[0-9]{10}UA";

    @Value("${telegram.BOT_TOKEN}")
    private String BOT_TOKEN;
    @Value("${telegram.BOT_USERNAME}")
    private String BOT_USERNAME;

    @Value("${telegram.BOT_CREATOR_ID}")
    private int BOT_CREATOR_ID;

    public TelegramBot(TelegramBotLogRepository telegramBotLogRepository) {
        this.telegramBotLogRepository = telegramBotLogRepository;
    }


    public SendMessage sendMsg (Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);

        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add("Zammler");
//        keyboardFirstRow.add("Register");

//        // Вторая строчка клавиатуры
//        KeyboardRow keyboardSecondRow = new KeyboardRow();
//        // Добавляем кнопки во вторую строчку клавиатуры
//        keyboardSecondRow.add("Команда 3");
//        keyboardSecondRow.add("Команда 4");

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
//        keyboard.add(keyboardSecondRow);
        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);

        sendMessage.setChatId(message.getChatId().toString());
//        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        return sendMessage;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        if (message != null && message.hasText()) {
            SendMessage sendMessage;
            switch (message.getText()) {
                case "/start":
                    sendMessage=sendMsg(message, String.format("Hello %s\n"+"ZAMMLER GROUP is logistics operator that offers the full range of logistics services.\n" +
                            "Welcome to the best logistics company!\n" +
                            "\n" +
                            "Contacts:\n" +
                            "Ukraine: 3, Proviantska Str. Kyiv, 04116. 38 (044) 233-62-28\n" +
                            "Poland: 3, Maja 8 Str, Millenium Logistic Park, Building C7, 05-800, Pruszkow. 48 (22) 299-45-80\n" +
                            "China: Yinzhou Commerce Building, North Tower, off. 3102, Ningbo. 86 (0574) 89-017-366\n" +
                            "You chat id is %s",message.getFrom().getFirstName(),message.getChatId()));
                    break;
                case "Zammler":
                    sendMessage=sendMsg(message, "ZAMMLER GROUP is logistics operator that offers the full range of logistics services.\n" +
                            "Welcome to the best logistics company!\n" +
                            "\n" +
                            "Contacts:\n" +
                            "Ukraine: 3, Proviantska Str. Kyiv, 04116. 38 (044) 233-62-28\n" +
                            "Poland: 3, Maja 8 Str, Millenium Logistic Park, Building C7, 05-800, Pruszkow. 48 (22) 299-45-80\n" +
                            "China: Yinzhou Commerce Building, North Tower, off. 3102, Ningbo. 86 (0574) 89-017-366");
                    break;
                default:
                    String returntext = "Hello "+message.getFrom().getFirstName()+" You wrote '"+message.getText()+"' and we remember that";
                    sendMessage=sendMsg(message, returntext);
                    break;
            }
            try {

                TelegramBotLog telegramBotLog = new TelegramBotLog();
                telegramBotLog.tbl_message = message.toString();
                telegramBotLog.tbl_userid = Long.valueOf(message.getFrom().getId());
                telegramBotLog.tbl_username = message.getFrom().getUserName();
                telegramBotLog.tbl_text = message.getText();
                telegramBotLogRepository.save(telegramBotLog);

                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
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

}
