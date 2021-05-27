package takred.weightcontrol;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import takred.weightcontrol.bot_commands.*;
import takred.weightcontrol.dto.WeightDto;
import takred.weightcontrol.service.WeightService;

import javax.annotation.PostConstruct;
import java.io.File;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class Bot extends TelegramLongPollingBot {
    public final WeightService weightService;
    private final AddWeight addWeight;
    private final GetButtons getButtons;
    private final GetChartByButton getChartByButton;
    private final GetChartByCommand getChartByCommand;
    private final GetWeightList getWeightList;
    private final RedactWeight redactWeight;
    private final String botToken;

    public Bot(WeightService weightService,
               AddWeight addWeight,
               GetButtons getButtons,
               GetChartByButton getChartByButton,
               GetChartByCommand getChartByCommand,
               GetWeightList getWeightList,
               RedactWeight redactWeight,
               @Value("${bot-token}") String botToken
               ) {
        this.weightService = weightService;
        this.addWeight = addWeight;
        this.getButtons = getButtons;
        this.getChartByButton = getChartByButton;
        this.getChartByCommand = getChartByCommand;
        this.getWeightList = getWeightList;
        this.redactWeight = redactWeight;
        this.botToken = botToken;
    }

    @PostConstruct
    public void init() {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try {
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiRequestException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(Message message, String result) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(result);
        try {
            sendApiMethod(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendPhoto(Message message, File photo) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setPhoto(photo);
        sendPhoto.setChatId(message.getChatId().toString());
        sendPhoto.setReplyToMessageId(message.getMessageId());
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendPhoto(CallbackQuery callbackQuery, File photo) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setPhoto(photo);
        sendPhoto.setChatId(callbackQuery.getMessage().getChatId());
        sendPhoto.setReplyToMessageId(callbackQuery.getMessage().getMessageId());
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendInlineKeyboardButton(Message message) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("График веса.");
        inlineKeyboardButton1.setCallbackData("/gwc");
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton1);
        List<List<InlineKeyboardButton>> list = new ArrayList<>();
        list.add(keyboardButtonsRow1);
        inlineKeyboardMarkup.setKeyboard(list);

        try {
            execute(new SendMessage().setChatId(message.getChatId()).setText("Список команд").setReplyMarkup(inlineKeyboardMarkup));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (getChartByButton.process(this, update)) {
            return;
        }
        if (getWeightList.process(this, update)) {
            return;
        }
        if (getChartByCommand.process(this, update)) {
            return;
        }
        if (getButtons.process(this, update)) {
            return;
        }
        LocalDateTime dateTime = LocalDateTime.now();
        LocalTime midday = LocalTime.of(12, 00);

        List<WeightDto> weights = weightService.getMyWeight(message.getFrom().getId().toString());
        if (addWeight.addWeight(this, message, weights)) {
            return;
        }
        WeightDto obj = weights.get(weights.size() - 1);
        boolean now = dateTime.toLocalTime().getHour() >= midday.getHour();
        boolean inRecording = obj.getDate().toLocalTime().getHour() >= midday.getHour();
        boolean condition = dateTime.toLocalDate().equals(obj.getDate().toLocalDate())
                && now == inRecording;
        if (addWeight.addWeight(this, message, condition)) {
            return;
        }
        redactWeight.redactWeight(this, message, obj);
    }

    @Override
    public String getBotUsername() {
        return "test_weight_controlBot";
    }

    @Override
    public String getBotToken() {
        System.out.println(botToken);
        return botToken;
    }
}
