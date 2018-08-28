package kz.ya.service;

import java.math.BigDecimal;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import kz.ya.dao.AccountDAO;
import kz.ya.dao.AccountDAOImpl;
import kz.ya.domain.Account;
import kz.ya.dto.UserTransfer;
import kz.ya.exception.CommonException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TransferServiceTest extends CommonServiceTest {

    private Account accountFrom;
    private Account accountTo;

    private final AccountDAO accountDAO = new AccountDAOImpl();

    @Before
    public void setUp() throws Exception {
        // create test data
        this.accountFrom = accountDAO.createAccount("112100");
        this.accountTo = accountDAO.createAccount("112101");
    }

    @After
    public void tearDown() throws Exception {
        // clear test data
        accountDAO.deleteAccount(accountFrom.getId());
        accountDAO.deleteAccount(accountTo.getId());
    }
    
    @Test
    public void testTransferMoneySuccessful() {
        try {
            accountDAO.updateAccountBalance(accountFrom.getId(), new BigDecimal("1000"));
        } catch (CommonException ex) {
            System.out.println(ex);
        }

        UserTransfer dto = new UserTransfer(accountFrom.getId(), accountTo.getId(), new BigDecimal("113.00"));

        Response response = target.path("transfer")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(dto, MediaType.APPLICATION_JSON_TYPE));
        
        assertEquals(response.getStatusInfo().getStatusCode(), Response.Status.OK.getStatusCode());
    }
    
    @Test
    public void testTransferMoneyFail() {
        UserTransfer dto = new UserTransfer(accountFrom.getId(), accountTo.getId(), new BigDecimal("1113.00"));

        Response response = target.path("transfer")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(dto, MediaType.APPLICATION_JSON_TYPE));
        
        System.out.println(response);
        assertEquals(response.getStatusInfo().getStatusCode(), Response.Status.BAD_REQUEST.getStatusCode());
    }
}
