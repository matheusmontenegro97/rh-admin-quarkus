package ifpe.br.com.controller;

import ifpe.br.com.model.Atestado;
import ifpe.br.com.repository.AtestadoRepository;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/rh/api/atestado")
public class AtestadoController {
    @Inject
    AtestadoRepository atestadoRepository;

    @POST
    public Atestado saveAtestado(Atestado atestado) throws Exception {
        return atestadoRepository.saveAtestado(atestado);
    }
}
