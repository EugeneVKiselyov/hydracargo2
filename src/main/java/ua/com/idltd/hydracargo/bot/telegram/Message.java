package ua.com.idltd.hydracargo.bot.telegram;

public class Message {
    public Long chatId;
    public String text;
    public Long SourceTelegramID;

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getSourceTelegramID() {
        return SourceTelegramID;
    }

    public void setSourceTelegramID(Long sourceTelegramID) {
        SourceTelegramID = sourceTelegramID;
    }
}
