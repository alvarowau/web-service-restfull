package org.alvarowau.webservicerestfull.webservice;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.alvarowau.webservicerestfull.bbdd.ConnectionPoolManager;
import org.alvarowau.webservicerestfull.model.dto.person.PersonFullDto;
import org.alvarowau.webservicerestfull.model.dto.person.PersonSaveDto;
import org.alvarowau.webservicerestfull.model.dto.person.PersonUpdateDto;
import org.alvarowau.webservicerestfull.service.impl.PersonServiceImpl;
import org.alvarowau.webservicerestfull.validation.PersonValidation;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

@Path("/person")
@RequestScoped
public class PersonResource {

    private final PersonServiceImpl personService;
    private final Connection connection;


    public PersonResource() {
        connection = ConnectionPoolManager.getConnection();
        personService = new PersonServiceImpl(connection);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("find/{id}")
    public Response findPerson(@PathParam("id") Long id) {
        if (id > 0) {
            Optional<PersonFullDto> optional = personService.findById(id);

            return optional
                    .map(person -> Response.ok(person).build())
                    .orElseGet(() -> Response.status(Response.Status.NOT_FOUND)
                            .entity("Persona con ID " + id + " no encontrada").build());
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("El id tiene que ser mayor a 0").build();
        }
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("delete/{id}")
    public Response deletePerson(@PathParam("id") Long id) {
        if (id > 0) {
            if (personService.delete(id)) {
                return Response.ok().entity("Se ha borrado la persona con el id " + id).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity(
                        "No se pudo encontrar la persona con el id " + id).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("save")
    public PersonSaveDto savePerson(PersonSaveDto personSaveDto) {

        if (!checkConnection()) {
            return new PersonSaveDto("No hay conexion", "", -1, null);
        }

        String errors = PersonValidation.validateAndErrors(personSaveDto);
        if (!errors.isEmpty()) {
            return new PersonSaveDto(errors, "", -1, null);
        }

        if (personService.save(personSaveDto)) {
            return personSaveDto;
        }

        return new PersonSaveDto("Error al guardar la persona", "", -1, null);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("update")
    public Response updatePerson(PersonUpdateDto personUpdateDto) {
        if (!checkConnection()) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("No hay conexión con la base de datos")
                    .build();
        }
        if (PersonValidation.validate(personUpdateDto)) {
            if (personService.update(personUpdateDto)) {
                return Response.ok(personUpdateDto).build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("Datos inválidos o no se pudo actualizar la persona")
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("list")
    public List<PersonFullDto> listPersons() {
        if (!checkConnection()) {
            return List.of();
        }
        return personService.findAll();
    }

    private boolean checkConnection() {
        return personService != null && connection != null;
    }

}
