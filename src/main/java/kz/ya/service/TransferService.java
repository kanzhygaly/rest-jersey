package kz.ya.service;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import kz.ya.dao.AccountDAO;
import kz.ya.dao.AccountDAOImpl;
import kz.ya.dto.UserTransfer;
import kz.ya.exception.CommonException;

/**
 *
 * @author Yerlan
 */
@Path("/transfer")
@Produces(MediaType.APPLICATION_JSON)
public class TransferService {
    
    private final AccountDAO accountDAO = new AccountDAOImpl();

    /**
     * 
     * @param bean
     * @return
     * @throws CommonException 
     */
    @POST
    public Response transferMoney(UserTransfer bean) throws CommonException {
        try {
            accountDAO.transferAccountBalance(bean);
        } catch (CommonException ex) {
            throw new WebApplicationException("Transaction failed: " + ex.getMessage(), Response.Status.BAD_REQUEST);
        }
        return Response.status(Response.Status.OK).build();
    }
}
