package takred.weightcontrol.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import takred.weightcontrol.dto.UserAccountDto;
import takred.weightcontrol.entity.UserAccount;
import takred.weightcontrol.repository.UserAccountRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;

    public UserAccountService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    public List<Integer> getTrueSendNotificationsUsers() {
        List<UserAccount> userAccounts = userAccountRepository.findBySendNotifications(true);
        List<Integer> telegramUsersId = new ArrayList<>();

        for (int i = 0; i < userAccounts.size(); i++) {
            telegramUsersId.add(userAccounts.get(i).getTelegramUserId());
        }
        return telegramUsersId;
    }

    public void addUserAccount(Integer telegramUserId) {
        userAccountRepository.save(new UserAccount(telegramUserId));
    }

    public void setNotifications(Update update) {
        UserAccount userAccount;
        if (update.hasMessage()) {
            userAccount = userAccountRepository.findById(update.getMessage().getFrom().getId()).get();
        } else if (update.hasCallbackQuery()) {
            userAccount = userAccountRepository.findById(update.getCallbackQuery().getFrom().getId()).get();
        } else {
            return;
        }
        System.out.println(userAccount.getTelegramUserId() + " " + !userAccount.isSendNotifications());
        userAccountRepository.save(new UserAccount(userAccount.getTelegramUserId(), !userAccount.isSendNotifications()));
    }

    public boolean userAccountExist(Integer telegramUserId) {
        return userAccountRepository.existsById(telegramUserId);
    }

    public UserAccount getUserAccount(Integer telegramUserId) {
        return userAccountRepository.findById(telegramUserId).get();
    }

    public String getNotificationsStatus(Integer telegramUserId) {
        if (userAccountRepository.findById(telegramUserId).get().isSendNotifications()) {
            return "Уведомления включены.";
        }
        return "Уведомления отключены.";
    }

    public String getNotificationsNameButton(Integer telegramUserId) {
        if (userAccountRepository.findById(telegramUserId).get().isSendNotifications()) {
            return "Выключить уведомления";
        }
        return "Включить уведомления";
    }
}
