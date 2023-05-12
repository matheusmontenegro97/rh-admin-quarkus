package ifpe.br.com.controller;

import ifpe.br.com.model.Clocking;
import ifpe.br.com.repository.ClockingRepository;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/rh/api/clocking")
public class PontoController {

    @Inject
    ClockingRepository clockingRepository;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Clocking saveClocking(Clocking clocking) throws Exception {
        return clockingRepository.saveClocking(clocking);
    }
}
