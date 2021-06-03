package takred.weightcontrol;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import takred.weightcontrol.service.UserAccountService;


@Service
public class Recorder {
    private final UserAccountService userAccountService;

    public Recorder(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    public void addForNotExists(Bot bot, Update update) {
        if (update.hasMessage()) {
            Integer telegramUserId = update.getMessage().getFrom().getId();
            if (!userAccountService.userAccountExist(telegramUserId)) {
                userAccountService.addUserAccount(telegramUserId);
            }
        }
        if (update.hasCallbackQuery()) {
            Integer telegramUserId = update.getCallbackQuery().getFrom().getId();
            if (!userAccountService.userAccountExist(telegramUserId)) {
                userAccountService.addUserAccount(telegramUserId);
            }
        }
    }
}
