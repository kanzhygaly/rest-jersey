package kz.ya.service;

import java.math.BigDecimal;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import kz.ya.dao.AccountDAO;
import kz.ya.dao.AccountDAOImpl;
import kz.ya.domain.Account;
import kz.ya.exception.CommonException;
import org.apache.log4j.Logger;

/**
 *
 * @author Yerlan
 */
@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
public class AccountService {

    private final AccountDAO accountDAO = new AccountDAOImpl();
    private static final Logger LOG = Logger.getLogger(AccountService.class);
    
    /**
     * Find account by id.
     *
     * @param accountId
     * @return
     * @throws CommonException
     */
    @GET
    @Path("/{accountId}")
    public Account getAccount(@PathParam("accountId") long accountId) throws CommonException {
        return accountDAO.getAccountById(accountId);
    }

    /**
     * Create Account.
     *
     * @param accountNo
     * @return new created Account
     * @throws CommonException
     */
    @PUT
    @Path("/create")
    public Account createAccount(String accountNo) throws CommonException {
        return accountDAO.createAccount(accountNo);
    }

    /**
     * Deposit amount by account Id.
     *
     * @param accountId
     * @param amount
     * @return updated Account
     * @throws CommonException
     */
    @PUT
    @Path("/{accountId}/deposit/{amount}")
    public Account deposit(@PathParam("accountId") long accountId,
            @PathParam("amount") BigDecimal amount) throws CommonException {

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new WebApplicationException("Not valid amount", Response.Status.BAD_REQUEST);
        }

        accountDAO.updateAccountBalance(accountId, amount);
        
        return accountDAO.getAccountById(accountId);
    }

    /**
     * Withdraw amount by account Id
     *
     * @param accountId
     * @param amount
     * @return updated Account
     * @throws CommonException
     */
    @PUT
    @Path("/{accountId}/withdraw/{amount}")
    public Account withdraw(@PathParam("accountId") long accountId,
            @PathParam("amount") BigDecimal amount) throws CommonException {

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new WebApplicationException("Not valid amount", Response.Status.BAD_REQUEST);
        }
        
        BigDecimal delta = amount.negate();
        if (LOG.isDebugEnabled()) {
            LOG.debug("Withdraw service: delta change to account  " + delta + " Account ID = " + accountId);
        }
        
        accountDAO.updateAccountBalance(accountId, delta);
        
        return accountDAO.getAccountById(accountId);
    }

    /**
     * Find all accounts.
     *
     * @return
     * @throws CommonException
     */
    @GET
    @Path("/all")
    public List<Account> getAllAccounts() throws CommonException {
        throw new WebApplicationException("Not supported yet.", Response.Status.NOT_IMPLEMENTED);
    }

    /**
     * Find balance by account Id.
     *
     * @param accountId
     * @return balance
     * @throws CommonException
     */
    @GET
    @Path("/{accountId}/balance")
    public BigDecimal getBalance(@PathParam("accountId") long accountId) throws CommonException {
        throw new WebApplicationException("Not supported yet.", Response.Status.NOT_IMPLEMENTED);
    }

    /**
     * Delete amount by account Id
     *
     * @param accountId
     * @return
     * @throws CommonException
     */
    @DELETE
    @Path("/{accountId}")
    public Response deleteAccount(@PathParam("accountId") long accountId) throws CommonException {
        throw new WebApplicationException("Not supported yet.", Response.Status.NOT_IMPLEMENTED);
    }
}
