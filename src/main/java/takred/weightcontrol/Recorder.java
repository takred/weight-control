package takred.weightcontrol;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;


@Service
public class Recorder {

    public void AddForNotExists(Bot bot, Update update) {
        if (update.hasMessage()) {
            Integer telegramUserId = update.getMessage().getFrom().getId();
            if (!bot.userAccountService.userAccountExist(telegramUserId)) {
                bot.userAccountService.addUserAccount(telegramUserId);
            }
        }
        if (update.hasCallbackQuery()) {
            Integer telegramUserId = update.getCallbackQuery().getFrom().getId();
            if (!bot.userAccountService.userAccountExist(telegramUserId)) {
                bot.userAccountService.addUserAccount(telegramUserId);
            }
        }
    }
}
