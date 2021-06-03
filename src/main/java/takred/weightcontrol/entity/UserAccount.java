package takred.weightcontrol.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "user_accounts")
public class UserAccount {
    @Id
    private Integer telegramUserId;
    private boolean sendNotifications;

    public UserAccount() {}

    public UserAccount(Integer telegramUserId) {
        this.telegramUserId = telegramUserId;
        this.sendNotifications = false;
    }

    public UserAccount(Integer telegramUserId, boolean sendNotifications) {
        this.telegramUserId = telegramUserId;
        this.sendNotifications = sendNotifications;
    }

    @Override
    public boolean equals(Object obj) {
        UserAccount userAccount = (UserAccount) obj;
        if (telegramUserId.equals(userAccount.telegramUserId)){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return telegramUserId.hashCode();
    }
}