/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ieselrincon.convalidaciones.rest;

import com.google.gson.Gson;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.ieselrincon.convalidaciones.bd.CicloBD;
import org.ieselrincon.convalidaciones.pojos.Ciclo;

/**
 * REST Web Service
 *
 * @author Tiburcio
 */
@Path("ciclo")
public class CicloResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public CicloResource() {
    }

    /**
     * Retrieves representation of an instance of alumnado.GenericResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("prueba")
    @Produces(MediaType.TEXT_PLAIN)
    public String getPrueba() {
        Date fecha = new Date();
        System.out.println(fecha.toString() + ": Prueba");

        return "Funciona!!!!! ";
    }

    // Devuelve todos los Registros
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCiclo() {
        try {
            List<Ciclo> registros = CicloBD.getAllCiclo();
            Date fecha = new Date();
            System.out.println(fecha.toString() + ": Se ha consultado - getAllCiclo");
            String json = new Gson().toJson(registros);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (Exception ex) {
            //ex.printStackTrace();
            return Response.status(Response.Status.SEE_OTHER).entity("No se pudo Consultar los ciclos ").build();
        }
    }

    // Añadir Registro. 
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCiclo(Ciclo registro) {
        try {
            CicloBD.addCiclo(registro);
            Date fecha = new Date();
            System.out.println(fecha.toString() + ": Se ha añadido - addCiclo " + registro.getPK_ID());
            String json = "{ \"id\": \"" + String.valueOf(registro.getPK_ID()) + "\" }";
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            //e.printStackTrace();
            return Response.status(Response.Status.SEE_OTHER).entity("No se pudo Insertar Registro: " + registro.getPK_ID()).build();
        }
    }

    // Actualizar un registro. 
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCiclo(Ciclo registro, @PathParam("id") int id) {
        System.out.println("Llegó a la actualización");
        try {
            CicloBD.updateCiclo(registro);
            Date fecha = new Date();
            System.out.println(fecha.toString() + ": Se ha actualizado - updateCiclo " + registro.getPK_ID());
            String json = "{ \"id\": \"" + String.valueOf(registro.getPK_ID()) + "\" }";
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            //e.printStackTrace();
            return Response.status(Response.Status.SEE_OTHER).entity("No se pudo Actualizar Registro: " + registro.getPK_ID()).build();
        }
    }

    // Borrar un registro. 
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delCiclo(@PathParam("id") int id) {

        try {
            CicloBD.delCiclo(id);
            Date fecha = new Date();
            System.out.println(fecha.toString() + ": Se ha borrado - delCiclo " + id);
            String json = "{ \"id\": \"" + id + "\" }";
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            //e.printStackTrace();
            return Response.status(Response.Status.SEE_OTHER).entity("No se pudo Borrar Registro: " + id).build();
        }
    }
}
