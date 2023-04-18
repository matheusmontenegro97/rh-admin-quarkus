package ifpe.br.com.controller;

import ifpe.br.com.model.Ponto;
import ifpe.br.com.repository.PontoRepository;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/rh/api/ponto")
public class PontoController {

    @Inject
    PontoRepository pontoRepository;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Ponto savePonto(Ponto ponto) throws Exception {
        return pontoRepository.savePonto(ponto);
    }
}
