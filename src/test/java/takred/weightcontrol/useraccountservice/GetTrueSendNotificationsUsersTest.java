package takred.weightcontrol.useraccountservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import takred.weightcontrol.dto.UserAccountDto;
import takred.weightcontrol.entity.UserAccount;
import takred.weightcontrol.mapper.UserAccountMapper;
import takred.weightcontrol.repository.UserAccountRepository;
import takred.weightcontrol.service.UserAccountService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class GetTrueSendNotificationsUsersTest {
    @Mock
    UserAccountRepository userAccountRepository;
    @InjectMocks
    UserAccountService userAccountService;
    @Mock
    UserAccountMapper userAccountMapper;

    @Test
    void getList() {
        List<UserAccountDto> userAccountDtos = new ArrayList<>();
        UserAccount userAccount = new UserAccount(1, 123L);
        userAccount.setSendNotifications(true);
        map(1, 123L, true);
        userAccountDtos.add(userAccountMapper.map(userAccount));

        userAccount = new UserAccount(2, 234L);
        userAccount.setSendNotifications(false);
        map(2, 234L, false);
        userAccountDtos.add(userAccountMapper.map(userAccount));

        userAccount = new UserAccount(3, 345L);
        userAccount.setSendNotifications(true);
        map(3, 345L, true);
        userAccountDtos.add(userAccountMapper.map(userAccount));

        userAccount = new UserAccount(4, 456L);
        userAccount.setSendNotifications(false);
        map(4, 456L, false);
        userAccountDtos.add(userAccountMapper.map(userAccount));

        List<UserAccountDto> userAccountDtosTrue = new ArrayList<>();
        for (int i = 0; i < userAccountDtos.size(); i++) {
            UserAccountDto e = userAccountDtos.get(i);
            if (e.isSendNotifications()) {
                userAccountDtosTrue.add(e);
            }
        }
        List<UserAccount> returnedList = new ArrayList<>();
        userAccount = new UserAccount(1, 123L);
        userAccount.setSendNotifications(true);
        returnedList.add(userAccount);

        userAccount = new UserAccount(3, 345L);
        userAccount.setSendNotifications(true);
        returnedList.add(userAccount);

        doReturn(returnedList).when(userAccountRepository).findBySendNotifications(true);
        assertEquals(userAccountDtosTrue, userAccountService.getTrueSendNotificationsUsers());
    }

    void map(Integer telegramUserId, Long chatId, boolean notif) {
        UserAccount userAccount = new UserAccount(telegramUserId, chatId);
        userAccount.setSendNotifications(notif);
        UserAccountDto userAccountDto = new UserAccountDto(telegramUserId, chatId, notif);
        doReturn(userAccountDto).when(userAccountMapper).map(eq(userAccount));
    }
}
