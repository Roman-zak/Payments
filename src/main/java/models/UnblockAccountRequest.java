package models;

import java.io.Serializable;
import java.util.Objects;

public class UnblockAccountRequest implements Serializable {
    private int id;
    private Account account;

    public UnblockAccountRequest(int id, Account account) {
        this.id = id;
        this.account = account;
    }

    public UnblockAccountRequest(Account account) {
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UnblockAccountRequest)) return false;
        UnblockAccountRequest that = (UnblockAccountRequest) o;
        return account.equals(that.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account);
    }
}
