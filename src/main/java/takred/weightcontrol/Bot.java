package takred.weightcontrol;

import lombok.SneakyThrows;
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
    private final GetChartByButton getChartByButton;
    private final GetWeightList getWeightList;
    private final GetChartByCommand getChartByCommand;
    private final GetButtons getButtons;
    private final RedactWeight redactWeight;

    public Bot(WeightService weightService, AddWeight addWeight, GetChartByButton getChartByButton, GetWeightList getWeightList, GetChartByCommand getChartByCommand, GetButtons getButtons, RedactWeight redactWeight) {
        this.weightService = weightService;
        this.addWeight = addWeight;
        this.getChartByButton = getChartByButton;
        this.getWeightList = getWeightList;
        this.getChartByCommand = getChartByCommand;
        this.getButtons = getButtons;
        this.redactWeight = redactWeight;
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
            throw new RuntimeException(e);
        }
    }

    public void sendPhoto(Message message) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setPhoto(new File("thumb-1920-1123013.jpg"));
        sendPhoto.setChatId(message.getChatId().toString());
        sendPhoto.setReplyToMessageId(message.getMessageId());
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }
    }


    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        System.out.println("111");
        Message message = update.getMessage();
        if (message == null) {
            if (update.hasCallbackQuery()) {
                if (update.getCallbackQuery().getData().equals("/gwc")) {
                    System.out.println("777");
                    getChartByButton.getChart(this, update);
                }
            }
            return;
        }
        System.out.println("222");
        if (message.getText().equals("/gw")) {
            System.out.println("333");
            getWeightList.getWeightList(this, message);
        } else if (message.getText().equals("/gwc")) {
            System.out.println("444");
            getChartByCommand.getChartByCommand(this, message);
        } else if (message.getText().equals("b")) {
            System.out.println("666");
            getButtons.getButtons(this, message);
        } else {
            System.out.println("888");
            LocalDateTime dateTime = LocalDateTime.now();
            LocalTime midday = LocalTime.of(12, 00);

            List<WeightDto> weights = weightService.getMyWeight(message.getFrom().getId().toString());
            if (weights.isEmpty()) {
                System.out.println("883");
                addWeight.addWeight(this, message);
                return;
            }
            System.out.println("881");
            WeightDto obj = weights.get(weights.size() - 1);
            System.out.println(obj);
            boolean now = Integer.valueOf(dateTime.toLocalTime().getHour()) >= Integer.valueOf(midday.getHour());
            boolean inRecording = Integer.valueOf(obj.getDate().toLocalTime().getHour()) >= Integer.valueOf(midday.getHour());
            if (dateTime.toLocalDate().equals(obj.getDate().toLocalDate())
                    && now == inRecording) {
                System.out.println("882");
                System.out.println(obj.getId());
                redactWeight.redactWeight(this, message, obj);
            } else {
                System.out.println("883");
                addWeight.addWeight(this, message);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "weight_controlBot";
    }

    @Override
    public String getBotToken() {
        return "1783806176:AAHvQSrI2Tp0XK-FqfNZNyTYlcYpInFYpbk";
    }
}
