package takred.weightcontrol.useraccountservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import takred.weightcontrol.entity.UserAccount;
import takred.weightcontrol.repository.UserAccountRepository;
import takred.weightcontrol.service.UserAccountService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class SetNotificationsTest {
    @Mock
    UserAccountRepository userAccountRepository;
    @InjectMocks
    UserAccountService userAccountService;
    @Mock
    Update update;
    @Mock
    Message message;
    @Mock
    User user;
    @Mock
    CallbackQuery callbackQuery;

    @Test

    void setNotificationByMessage() {
        doReturn(true).when(update).hasMessage();
        doReturn(message).when(update).getMessage();
        doReturn(user).when(message).getFrom();
        doReturn(1).when(user).getId();

        UserAccount userAccount = new UserAccount(1, 123L);
        userAccount.setSendNotifications(false);
        Optional<UserAccount> optionalUserAccount = Optional.of(userAccount);
        doReturn(optionalUserAccount).when(userAccountRepository).findById(eq(1));

        userAccountService.setNotifications(update);
        assertEquals(true, userAccount.isSendNotifications());
    }

    @Test
    void setNotificationByCallBackQuery() {
        doReturn(true).when(update).hasCallbackQuery();
        doReturn(callbackQuery).when(update).getCallbackQuery();
        doReturn(user).when(callbackQuery).getFrom();
        doReturn(1).when(user).getId();

        UserAccount userAccount = new UserAccount(1, 123L);
        userAccount.setSendNotifications(false);
        Optional<UserAccount> optionalUserAccount = Optional.of(userAccount);
        doReturn(optionalUserAccount).when(userAccountRepository).findById(eq(1));

        userAccountService.setNotifications(update);
        assertEquals(true, userAccount.isSendNotifications());
    }
}
