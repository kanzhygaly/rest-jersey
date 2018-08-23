package kz.ya.dao;

import java.math.BigDecimal;
import javax.persistence.LockModeType;
import kz.ya.DbConnection;
import kz.ya.domain.Account;
import kz.ya.dto.UserTransfer;
import kz.ya.exception.CommonException;

/**
 *
 * @author Yerlan
 */
public class AccountDAOImpl implements AccountDAO {
    
    @Override
    public Account getAccountById(long accountId) throws CommonException {
        return DbConnection.getEntityManager().find(Account.class, accountId);
    }

    /**
     * Create account.
     *
     * @param accountNo
     * @return accountId
     * @throws CommonException
     */
    @Override
    public Account createAccount(String accountNo) throws CommonException {
        Account account = null;
        try {
            DbConnection.beginTransaction();

            account = DbConnection.getEntityManager().merge(new Account(accountNo));
            
            DbConnection.commit();
        } catch (RuntimeException e) {
            if (DbConnection.getEntityManager() != null && DbConnection.getEntityManager().isOpen()) {
                DbConnection.rollback();
            }
            throw new CommonException(e);
        } finally {
            DbConnection.closeEntityManager();
        }
        return account;
    }

    /**
     * Update account balance.
     *
     * @param accountId
     * @param deltaAmount
     * @throws CommonException
     */
    @Override
    public void updateAccountBalance(long accountId, BigDecimal deltaAmount) throws CommonException {
        try {
            DbConnection.beginTransaction();

            Account targetAccount = DbConnection.getEntityManager().find(
                    Account.class, accountId, LockModeType.PESSIMISTIC_READ);

            if (targetAccount == null) {
                throw new CommonException("updateAccountBalance(): failed to lock account : " + accountId);
            }

            // update account upon success locking
            BigDecimal balance = targetAccount.getBalance().add(deltaAmount);
            if (balance.compareTo(BigDecimal.ZERO) < 0) {
                throw new CommonException("Not enough funds on account : "
                        + targetAccount.getAccountNo());
            }

            // proceed with update
            targetAccount.setBalance(balance);

            DbConnection.getEntityManager().merge(targetAccount);

            DbConnection.commit();
        } catch (RuntimeException e) {
            if (DbConnection.getEntityManager() != null && DbConnection.getEntityManager().isOpen()) {
                DbConnection.rollback();
            }
            throw new CommonException(e);
        } finally {
            DbConnection.closeEntityManager();
        }
    }

    /**
     * Transfer balance between two accounts.
     *
     * @param dto
     * @throws CommonException
     */
    @Override
    public void transferAccountBalance(UserTransfer dto) throws CommonException {
        try {
            DbConnection.beginTransaction();

            Account fromAccount = DbConnection.getEntityManager().find(
                    Account.class, dto.getFromAccountId(), LockModeType.PESSIMISTIC_READ);

            Account toAccount = DbConnection.getEntityManager().find(
                    Account.class, dto.getToAccountId(), LockModeType.PESSIMISTIC_READ);

            // check locking status
            if (fromAccount == null) {
                throw new CommonException("transferAccountBalance(): failed to lock account : "
                        + dto.getFromAccountId());
            }
            if (toAccount == null) {
                throw new CommonException("transferAccountBalance(): failed to lock account : "
                        + dto.getToAccountId());
            }

            // check enough balance in source account
            BigDecimal leftAmount = fromAccount.getBalance().subtract(dto.getAmount());
            if (leftAmount.compareTo(BigDecimal.ZERO) < 0) {
                throw new CommonException("Not enough funds on account : "
                        + fromAccount.getAccountNo());
            }

            // proceed with update
            fromAccount.setBalance(leftAmount);
            toAccount.getBalance().add(dto.getAmount());

            DbConnection.getEntityManager().merge(fromAccount);
            DbConnection.getEntityManager().merge(toAccount);

            DbConnection.commit();
        } catch (RuntimeException e) {
            if (DbConnection.getEntityManager() != null && DbConnection.getEntityManager().isOpen()) {
                DbConnection.rollback();
            }
            throw new CommonException(e);
        } finally {
            DbConnection.closeEntityManager();
        }
    }
}
