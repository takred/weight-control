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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class GetUserAccountTest {
    @Mock
    UserAccountRepository userAccountRepository;
    @InjectMocks
    UserAccountService userAccountService;

    @Test
    void findById() {
        UserAccount userAccount = new UserAccount(1, 123L);
        Optional<UserAccount> optionalUserAccount = Optional.of(userAccount);
        doReturn(optionalUserAccount).when(userAccountRepository).findById(eq(1));
        UserAccount userAccount1 = new UserAccount(1, 123L);
        assertEquals(userAccount1, userAccountService.getUserAccount(1));
    }
}
