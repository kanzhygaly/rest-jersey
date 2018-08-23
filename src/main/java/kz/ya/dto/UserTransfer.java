package kz.ya.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author Yerlan
 */
public class UserTransfer {

    private Long fromAccountId;
    private Long toAccountId;
    private BigDecimal amount;

    public Long getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(Long fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public Long getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(Long toAccountId) {
        this.toAccountId = toAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.fromAccountId);
        hash = 13 * hash + Objects.hashCode(this.toAccountId);
        hash = 13 * hash + Objects.hashCode(this.amount);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserTransfer other = (UserTransfer) obj;
        if (!this.fromAccountId.equals(other.fromAccountId)) {
            return false;
        }
        if (!this.toAccountId.equals(other.toAccountId)) {
            return false;
        }
        return this.amount.compareTo(other.amount) == 0;
    }

    @Override
    public String toString() {
        return "UserTransfer{" + "fromAccountId=" + fromAccountId 
                + ", toAccountId=" + toAccountId + ", amount=" + amount + '}';
    }
}
