package ifpe.br.com.repository;

import ifpe.br.com.model.SickNote;

public interface SickNoteRepository {
    SickNote saveSickNote(SickNote sickNote) throws Exception;
}
