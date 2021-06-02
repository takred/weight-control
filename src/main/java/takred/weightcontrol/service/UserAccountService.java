package takred.weightcontrol.service;

import org.springframework.stereotype.Service;
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

    public List<Integer> getTrueNotificationUsers() {
        List<UserAccount> userAccounts = userAccountRepository.findByNotification(true);
        List<Integer> telegramUsersId = new ArrayList<>();

        for (int i = 0; i < userAccounts.size(); i++) {
            telegramUsersId.add(userAccounts.get(i).getTelegramUserId());
        }
        return telegramUsersId;
    }
}
