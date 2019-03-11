package ws;

import beans.Cliente;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.dao.ClienteDAO;

@Path("clientes")
public class ClienteWS {

    @Context
    private UriInfo context;

    public ClienteWS() {
    }
    
    @GET
    @Path("buscartodos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cliente> buscarTodos() {
        return ClienteDAO.buscarTodos();
    }
    
    @GET
    @Path("buscarpornombre/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cliente> buscarPorNombre(
            @PathParam("nombre") String nombre
    ) {
        return ClienteDAO.buscarPorNombre(nombre);
    }
    
}
