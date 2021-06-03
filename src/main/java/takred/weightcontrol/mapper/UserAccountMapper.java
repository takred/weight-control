package takred.weightcontrol.mapper;

import org.springframework.stereotype.Component;
import takred.weightcontrol.builder.UserAccountDtoBuilder;
import takred.weightcontrol.dto.UserAccountDto;
import takred.weightcontrol.entity.UserAccount;

@Component
public class UserAccountMapper {
    public UserAccountDto map(UserAccount entity) {
        return new UserAccountDtoBuilder()
                .withTelegramUserId(entity.getTelegramUserId())
                .withSendNotifications(entity.isSendNotifications())
                .build();
    }

}
