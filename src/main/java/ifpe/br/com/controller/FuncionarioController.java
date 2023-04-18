package ifpe.br.com.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import ifpe.br.com.model.Funcionario;
import ifpe.br.com.repository.FuncionarioRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/rh/api")
public class FuncionarioController {

    @Inject
    FuncionarioRepository funcionarioRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Funcionario> getFuncionarios() throws JsonProcessingException {
        return funcionarioRepository.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Funcionario getFuncionario
            (@PathParam("id") String id) throws JsonProcessingException {
        return funcionarioRepository.findFuncionarioById(id);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Funcionario updateFuncionarios
            (@PathParam("id") String id, Funcionario funcionario) throws JsonProcessingException {
        return funcionarioRepository.updateFuncionario(id, funcionario);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Funcionario addFuncionario(Funcionario funcionario) {

        return funcionarioRepository.saveFuncionario(funcionario);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteFuncionario(@PathParam("id") String id) {
        funcionarioRepository.deleteFuncionarioById(id);
    }
}
