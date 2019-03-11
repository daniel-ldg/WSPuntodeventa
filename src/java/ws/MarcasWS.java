/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import beans.Marca;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import model.dao.MarcaDAO;

/**
 * REST Web Service
 *
 * @author Daniel
 */
@Path("marcas")
public class MarcasWS {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of MarcasWS
     */
    public MarcasWS() {
    }

    @GET
    @Path("todas")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Marca> getTodas() {
        return MarcaDAO.getMarcas();
    }
    
    @GET
    @Path("buscarnombre/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Marca> getMarcasPorNombre(
            @PathParam("nombre") String nombre
    ) {
        return MarcaDAO.getMarcasPorNombre(nombre);
    }
    
    @GET
    @Path("registroget/{nombre}")
    @Produces(MediaType.TEXT_PLAIN)
    public String registrarGet(
            @PathParam("nombre") String nombre
    ) {
        String respuesta = "";
        
        int filas = 0;
        filas = MarcaDAO.registrarMarca(nombre);
        if (filas > 0) {
            respuesta = "Registro afectado correctamente";
        } else {
            respuesta = "No se puede ejecutar el registro";
        }
        return respuesta;
    }
    
    @POST
    @Path("registrar")
    @Produces(MediaType.TEXT_PLAIN)
    public String registrar(
            @FormParam("nombre") String nombre
    ) {        
        int filas = 0;
        filas = MarcaDAO.registrarMarca(nombre);
        if (filas > 0) {
            return "Registro afectado correctamente";
        } else {
            return "No se puede ejecutar el registro";
        }
    }
    
    @DELETE
    @Path("eliminar")
    @Produces(MediaType.TEXT_PLAIN)
    public String eliminar(
            @FormParam("idmarca") Integer idMarca
    ) {
        int filas;
        filas = MarcaDAO.eliminarMarca(idMarca);
        if (filas > 0) {
            return "Registro eliminado";
        } else {
            return "Registro no eliminado";
        }
    }
}
