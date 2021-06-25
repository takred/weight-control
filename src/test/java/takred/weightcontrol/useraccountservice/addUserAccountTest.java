package takred.weightcontrol.useraccountservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import takred.weightcontrol.entity.UserAccount;
import takred.weightcontrol.repository.UserAccountRepository;
import takred.weightcontrol.service.UserAccountService;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class addUserAccountTest {
    @Mock
    UserAccountRepository userAccountRepository;
    @InjectMocks
    UserAccountService userAccountService;

    @Test
    void save() {
        userAccountService.addUserAccount(1, 123L);
        verify(userAccountRepository).save(new UserAccount(1, 123L));
    }
}
