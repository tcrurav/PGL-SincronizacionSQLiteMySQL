/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ieselrincon.convalidaciones.rest;

import java.sql.SQLException;
import java.util.ArrayList;
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
import org.ieselrincon.convalidaciones.bd.AlumnoBD;
import org.ieselrincon.convalidaciones.pojos.Alumno;

/**
 * REST Web Service
 *
 * @author Tiburcio
 */
@Path("alumno")
public class AlumnoResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public AlumnoResource() {
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

        return "Funciona!!!!!";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Alumno> getAllAlumno() {
        try {
            List<Alumno> registros = AlumnoBD.getAllAlumno();
            return registros;
        } catch (Exception ex) {
            //ex.printStackTrace();
            return null;
        }
    }

    // Use data from the client source to create a new Person object, returned in JSON format. 
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String addAlumno(Alumno registro) {

        try {
            AlumnoBD.addAlumno(registro);
            return Integer.toString(registro.getPK_ID());
        } catch (SQLException e) {
            //e.printStackTrace();
            return "-1";
        }
    }

    // Actualizar un registro. 
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String updateAlumno(Alumno registro) {

        try {
            AlumnoBD.updateAlumno(registro);
            return Integer.toString(registro.getPK_ID());
        } catch (SQLException e) {
            //e.printStackTrace();
            return "-1";
        }
    }

    // Borrar un registro. 
    @DELETE
    @Path("{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String delAlumno(@PathParam("id") int id) {

        try {
            AlumnoBD.delAlumno(id);
            return Integer.toString(id);
        } catch (SQLException e) {
            //e.printStackTrace();
            return "-1";
        }
    }
}
