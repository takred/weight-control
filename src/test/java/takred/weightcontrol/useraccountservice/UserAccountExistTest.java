package takred.weightcontrol.useraccountservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import takred.weightcontrol.repository.UserAccountRepository;
import takred.weightcontrol.service.UserAccountService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class UserAccountExistTest {
    @Mock
    UserAccountRepository userAccountRepository;
    @InjectMocks
    UserAccountService userAccountService;
    @Test
    void exists() {
        doReturn(true).when(userAccountRepository).existsById(eq(1));
        assertEquals(true, userAccountService.userAccountExist(1));
    }

    @Test
    void notExists() {
        doReturn(false).when(userAccountRepository).existsById(eq(1));
        assertEquals(false, userAccountService.userAccountExist(1));
    }
}
