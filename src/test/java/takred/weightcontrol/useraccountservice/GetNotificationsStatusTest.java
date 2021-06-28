package takred.weightcontrol.useraccountservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import takred.weightcontrol.entity.UserAccount;
import takred.weightcontrol.repository.UserAccountRepository;
import takred.weightcontrol.service.UserAccountService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class GetNotificationsStatusTest {
    @Mock
    UserAccountRepository userAccountRepository;
    @InjectMocks
    UserAccountService userAccountService;

    @Test
    void notificationsOn() {
        UserAccount userAccount = new UserAccount(1, 123L);
        userAccount.setSendNotifications(true);
        Optional<UserAccount> optionalUserAccount = Optional.of(userAccount);
        doReturn(optionalUserAccount).when(userAccountRepository).findById(1);
        assertEquals("Уведомления включены.", userAccountService.getNotificationsStatus(1));
    }

    @Test
    void notificationsOff() {
        UserAccount userAccount = new UserAccount(1, 123L);
        userAccount.setSendNotifications(false);
        Optional<UserAccount> optionalUserAccount = Optional.of(userAccount);
        doReturn(optionalUserAccount).when(userAccountRepository).findById(1);
        assertEquals("Уведомления отключены.", userAccountService.getNotificationsStatus(1));
    }
}
