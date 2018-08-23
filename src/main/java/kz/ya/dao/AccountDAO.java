package kz.ya.dao;

import java.math.BigDecimal;
import kz.ya.domain.Account;
import kz.ya.dto.UserTransfer;
import kz.ya.exception.CommonException;

/**
 *
 * @author Yerlan
 */
public interface AccountDAO {
    
    Account getAccountById(long accountId) throws CommonException;
    
    Account createAccount(String accountNo) throws CommonException;
    
    void updateAccountBalance(long accountId, BigDecimal deltaAmount) throws CommonException;
    
    void transferAccountBalance(UserTransfer dto) throws CommonException;
}
