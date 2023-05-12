package ifpe.br.com.controller;

import ifpe.br.com.model.SickNote;
import ifpe.br.com.repository.SickNoteRepository;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/rh/api/sickNote")
public class SickNoteController {
    @Inject
    SickNoteRepository sickNoteRepository;

    @POST
    public SickNote saveSickNote(SickNote sickNote) throws Exception {
        return sickNoteRepository.saveSickNote(sickNote);
    }
}
