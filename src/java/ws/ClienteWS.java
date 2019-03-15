package ws;

import beans.Cliente;
import beans.Respuesta;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
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
        if (nombre.isEmpty()) {
            respuesta.setError(Boolean.TRUE);
            respuesta.setErrorcode(1);
            respuesta.setMensaje("El nombre del cliente no puede estar vacío");
        } else if (email.isEmpty()) {
            respuesta.setError(Boolean.TRUE);
            respuesta.setErrorcode(2);
            respuesta.setMensaje("El correo del cliente no puede estar vacío");
        } else if (direccion.isEmpty()) {
            respuesta.setError(Boolean.TRUE);
            respuesta.setErrorcode(3);
            respuesta.setMensaje("La dirección del cliente no puede estar vacía");
        } else if (contraseña.isEmpty()) {
            respuesta.setError(Boolean.TRUE);
            respuesta.setErrorcode(4);
            respuesta.setMensaje("La contraseña no puede estar vacía");
        } else if (!contraseña.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,16}$")) {
            respuesta.setError(Boolean.TRUE);
            respuesta.setErrorcode(5);
            respuesta.setMensaje("La contraseña debe tener una longitud entre 8 y 16 y "
                    + "debe contener al menos 1 mayúscula y 1 digito");
        } else if (telefono.isEmpty()) {
            respuesta.setError(Boolean.TRUE);
            respuesta.setErrorcode(6);
            respuesta.setMensaje("El número de teléfono no puede estar vacío");
        } else if (usuario.isEmpty()) {
            respuesta.setError(Boolean.TRUE);
            respuesta.setErrorcode(11);
            respuesta.setMensaje("El nombre de usuario no puede estar vacío");
        } else if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            respuesta.setError(Boolean.TRUE);
            respuesta.setErrorcode(9);
            respuesta.setMensaje("El valor de correo no es un correo válido");
        } else if (!telefono.matches("^\\d{10}$")) {
            respuesta.setError(Boolean.TRUE);
            respuesta.setErrorcode(10);
            respuesta.setMensaje("El valor del teléfono debe contener 10 dígitos");
        } else {
            if (ClienteDAO.existeTelefonoCliente(telefono)) {
                respuesta.setError(Boolean.TRUE);
                respuesta.setErrorcode(7);
                respuesta.setMensaje("No se puede registrar el cliente porque ya existe un cliente con el mismo teléfono");
            } else if (ClienteDAO.existeUsuarioCliente(usuario)) {
                respuesta.setError(Boolean.TRUE);
                respuesta.setErrorcode(8);
                respuesta.setMensaje("No se puede registrar el cliente porque ya existe el nombre de usuario en la base de datos");
            } else {
                Cliente cliente = new Cliente();
                cliente.setNombre(nombre);
                cliente.setEmail(email);
                cliente.setContraseña(contraseña);
                cliente.setDireccion(direccion);
                cliente.setTelefono(telefono);
                cliente.setUsuario(usuario);
                cliente.setToken(String.format("%04d", ThreadLocalRandom.current().nextInt(10000)));
                int filasAfectadas = ClienteDAO.registrarCliente(cliente);
                if (filasAfectadas > 0) {
                    respuesta.setError(Boolean.FALSE);
                    respuesta.setErrorcode(0);
                    respuesta.setMensaje("Cliente registrado correctamente.");
                    respuesta.setIdGenerado(cliente.getIdCliente());
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("No se puede registrar cliente");
                }
            }
        }
        return respuesta;
    }
}
