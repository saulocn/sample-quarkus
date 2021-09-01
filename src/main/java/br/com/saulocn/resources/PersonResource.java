package br.com.saulocn.resources;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.saulocn.model.Person;

@Path("/person")
public class PersonResource {

    @POST
    @Transactional
    @Consumes("application/json")
    public Response add(Person personToSave) {
        personToSave.persist();
        return Response
                .created(URI.create("/person/" + personToSave.getId()))
                .build();
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Person get(@PathParam("id") Long id) {
        return Person.findById(id);
    }

    @GET
    @Produces("application/json")
    public List<Person> list() {
        return Person.listAll();
    }

    @Transactional
    @PUT
    @Path("{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response update(@PathParam("id") Long id, Person personToSave) {
        Person entity = Person.findById(id);
        if (entity == null) {
            return Response.status(404).build();
        }
        entity.setBirth(personToSave.getBirth());
        entity.setName(personToSave.getName());
        return Response.accepted(personToSave).build();
    }

    @Transactional
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        if (Person.deleteById(id)) {
            return Response.ok().build();
        }
        return Response.noContent().build();
    }

}