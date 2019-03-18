package ws;

import beans.Cliente;
import beans.Respuesta;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.dao.ClienteDAO;
import ws.util.ClienteValidator;
import ws.util.ValidatorException;

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
        try {
            Cliente cliente = new Cliente();
            cliente.setNombre(nombre);
            cliente.setEmail(email);
            cliente.setContraseña(contraseña);
            cliente.setDireccion(direccion);
            cliente.setTelefono(telefono);
            cliente.setUsuario(usuario);
            cliente.setToken(String.format("%04d", ThreadLocalRandom.current().nextInt(10000)));
            
            ClienteValidator.validarRegistro(cliente);
            
            int filasAfectadas = ClienteDAO.registrarCliente(cliente);
            
            if (filasAfectadas > 0) {
                respuesta.setError(Boolean.FALSE);
                respuesta.setErrorcode(0);
                respuesta.setMensaje("Cliente registrado correctamente.");
                respuesta.setIdGenerado(cliente.getIdCliente());
            } else {
                respuesta.setError(Boolean.TRUE);
                respuesta.setErrorcode(-1);
                respuesta.setMensaje("No se puede registrar cliente");
            }
            
        } catch (ValidatorException ex) {
            respuesta.setError(Boolean.TRUE);
            respuesta.setErrorcode(ex.getErrorCode());
            respuesta.setMensaje(ex.getMessage());
        }
        return respuesta;
    }
    
    @PUT
    @Path("actualizar")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta registrarClinte(
            @FormParam("idCliente") Integer idCliente,
            @FormParam("nombre") String nombre,
            @FormParam("email") String email,
            @FormParam("contraseña") String contraseña,
            @FormParam("direccion") String direccion,
            @FormParam("telefono") String telefono,
            @FormParam("usuario") String  usuario
    ) {
        Respuesta respuesta = new Respuesta();
        try {
            Cliente cliente = new Cliente();
            cliente.setIdCliente(idCliente);
            cliente.setNombre(nombre);
            cliente.setEmail(email);
            cliente.setContraseña(contraseña);
            cliente.setDireccion(direccion);
            cliente.setTelefono(telefono);
            cliente.setUsuario(usuario);
            cliente.setToken(String.format("%04d", ThreadLocalRandom.current().nextInt(10000)));
            
            ClienteValidator.validarActualizacion(cliente);
            
            int filasAfectadas = ClienteDAO.actualizarCliente(cliente);
            
            if (filasAfectadas > 0) {
                respuesta.setError(Boolean.FALSE);
                respuesta.setErrorcode(0);
                respuesta.setMensaje("Cliente actualizado correctamente.");
            } else {
                respuesta.setError(Boolean.TRUE);
                respuesta.setErrorcode(-1);
                respuesta.setMensaje("No se puede actualizar cliente");
            }
            
        } catch (ValidatorException ex) {
            respuesta.setError(Boolean.TRUE);
            respuesta.setErrorcode(ex.getErrorCode());
            respuesta.setMensaje(ex.getMessage());
        }
        return respuesta;
    }
    
    @DELETE
    @Path("eliminar")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta eliminar(
            @FormParam("idCliente") Integer idCliente
    ) {
        Respuesta respuesta = new Respuesta();
        
        try {
            ClienteValidator.validarElimminacion(idCliente);
            
            int filasAfectadas = ClienteDAO.eliminarCliente(idCliente);
            
            if (filasAfectadas > 0) {
                respuesta.setError(Boolean.FALSE);
                respuesta.setErrorcode(0);
                respuesta.setMensaje("Cliente eliminado correctamente.");
            } else {
                respuesta.setError(Boolean.TRUE);
                respuesta.setErrorcode(-1);
                respuesta.setMensaje("No se puede eliminar cliente");
            }
        } catch (ValidatorException ex) {
            respuesta.setError(Boolean.TRUE);
            respuesta.setErrorcode(ex.getErrorCode());
            respuesta.setMensaje(ex.getMessage());
        }
        return respuesta;
    }
}
