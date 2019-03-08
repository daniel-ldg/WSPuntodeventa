/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import beans.Mensaje;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Daniel
 */
@Path("utilerias")
public class UtileriasWS {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UtileriasWS
     */
    public UtileriasWS() {
    }

    /**
     * Retrieves representation of an instance of ws.UtileriasWS
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Mensaje> getJson() {
        List<Mensaje> lista = new ArrayList<>();
        Mensaje m1 = new Mensaje();
        m1.setId(1);
        m1.setMensaje("Hola mundo");
        m1.setDestino("A todos");
        lista.add(m1);
        
        return lista;
    }
    
    @GET
    @Path("mensajes")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Mensaje> getMensajes() {
        List<Mensaje> lista = new ArrayList<>();
        Mensaje m1 = new Mensaje();
        m1.setId(1);
        m1.setMensaje("Saludos desde el segundo metodo del WS");
        m1.setDestino("Todos");
        Mensaje m2 = new Mensaje();
        m2.setId(2);
        m2.setMensaje("Este es un segundo mensaje");
        m2.setDestino("Para los alumnos");
        lista.add(m1);
        lista.add(m2);
        return lista;
    }
    
    @GET
    @Path("mensajesdinamicos/{nummensajes}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Mensaje> getMensajesDinamicos(
            @PathParam("nummensajes") Integer mensajes
    ) {
        List<Mensaje> lista = new ArrayList<>();
        for (int i = 1; i <= mensajes; i++) {
            Mensaje m = new Mensaje();
            m.setId(i);
            m.setMensaje("Mensaje número: " + i);
            m.setDestino("Todos");
            lista.add(m);
        }
        return lista;
    }
    
    @GET
    @Path("mensajesrango/{num1},{num2}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Mensaje> getMensajesRango(
            @PathParam("num1") Integer num1,
            @PathParam("num2") Integer num2
    ) {
        List<Mensaje> lista = new ArrayList<>();
        for (int i = num1; i <= num2; i++) {
            Mensaje m = new Mensaje();
            m.setId(i);
            m.setMensaje("Mensaje número: " + i);
            m.setDestino("Todos");
            lista.add(m);
        }
        return lista;
    }

    /**
     * PUT method for updating or creating an instance of UtileriasWS
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
