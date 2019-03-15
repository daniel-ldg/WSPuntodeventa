package ws;

import beans.Cliente;
import beans.Respuesta;
import java.util.List;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
    
    @POST
    @Path("registrar")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta registrarClinte(
            @FormParam("nombre") String nombre,
            @FormParam("email") String email,
            @FormParam("contraseña") String contraseña,
            @FormParam("direccion") String direccion,
            @FormParam("telefono") String telefono,
            @FormParam("usuario") String  usuario
    ) {
        Respuesta respuesta = new Respuesta();
        //int filasAfectadas = ClienteDAO.registrarCliente(nombre, email, contraseña, direccion, telefono, usuario);
        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setEmail(email);
        cliente.setContraseña(contraseña);
        cliente.setDireccion(direccion);
        cliente.setTelefono(telefono);
        cliente.setUsuario(usuario);
        int filasAfectadas = ClienteDAO.registrarCliente(cliente);
        if (filasAfectadas > 0) {
            respuesta.setMensaje("Cliente registrado correctamente.");
            respuesta.setIdGenerado(cliente.getIdCliente());
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("No se puede registrar cliente");
        }
        return respuesta;
    }
}
