package ifpe.br.com.repository.impl;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.model.GridFSFile;
import ifpe.br.com.exceptions.EmployeeNotFoundException;
import ifpe.br.com.model.SickNote;
import ifpe.br.com.model.Employee;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SickNoteRepositoryImplTest {

    @Mock
    private MongoClient mongoClient;

    @InjectMocks
    private SickNoteRepositoryImpl sickNoteRepositoryImpl;

    @Mock
    private MongoDatabase database;


    @Mock
    private MongoCollection<SickNote> coll;

    @Mock
    private MongoCollection<GridFSFile> collGrid;

    @Mock
    private MongoCollection<Document> collGridChunks;

    @Mock
    private MongoCollection<Object> collGridObject;

    @Mock
    private EmployeeRepositoryImpl employeeRepositoryImpl;

    @Mock
    private GridFSBucket gridFSBucket;

    @Mock
    private ObjectId objectId;

    @Mock
    private FindIterable<Object> findIterable;

    @Test
    void saveSickNoteSuccessTest() throws Exception {
        SickNote sickNote = new SickNote();
        sickNote.setEmployeeCode("1");
        sickNote.setSickNote("/C:/Users/mathe/Downloads/693876.jpg");

        Employee func = new Employee();
        String bucketName = "testBucket";

        when(mongoClient.getDatabase(anyString())).thenReturn(database);
        when(database.getCollection(anyString(), eq(SickNote.class))).thenReturn(coll);
        when(coll.withCodecRegistry(any())).thenReturn(coll);
        when(employeeRepositoryImpl.findEmployeeById(anyString())).thenReturn(func);

        File file = new File(sickNote.getSickNote());
        InputStream targetStream = new FileInputStream(file);
        lenient().when(gridFSBucket.uploadFromStream(sickNote.getSickNoteCode(), targetStream)).thenReturn(objectId);
        when(database.getCollection(eq("fs.files"), eq(GridFSFile.class))).thenReturn(collGrid);
        when(database.getCollection(eq("fs.chunks"))).thenReturn(collGridChunks);

        when(collGrid.withCodecRegistry(any())).thenReturn(collGrid);
        when(collGrid.withDocumentClass(any())).thenReturn(collGridObject);
        when(collGrid.withDocumentClass(any()).withReadPreference(any())).thenReturn(collGridObject);
        when(collGridObject.find()).thenReturn(findIterable);
        when(findIterable.projection(any())).thenReturn(findIterable);
        when(findIterable.first()).thenReturn(new Object());
        when(collGridChunks.withCodecRegistry(any())).thenReturn(collGridChunks);
        when(database.getCollection(bucketName + ".files", GridFSFile.class)).thenReturn(collGrid);
        when(database.getCodecRegistry()).thenReturn(null);
        when(database.getCollection(bucketName + ".chunks")).thenReturn(collGridChunks);

        getFilesCollection(database, bucketName);
        getChunksCollection(database, bucketName);

        sickNoteRepositoryImpl.saveSickNote(sickNote);

        verify(coll, times(1)).insertOne(sickNote);
    }

    @Test
    void saveSickNoteErrorTest() throws Exception {
        SickNote sickNote = new SickNote();
        sickNote.setEmployeeCode("1");
        sickNote.setSickNote("/C:/Users/mathe/Downloads/693876.jpg");

        String bucketName = "testBucket";

        when(employeeRepositoryImpl.findEmployeeById(anyString())).thenReturn(null);

        File file = new File(sickNote.getSickNote());
        InputStream targetStream = new FileInputStream(file);
        lenient().when(gridFSBucket.uploadFromStream(sickNote.getSickNoteCode(), targetStream)).thenReturn(objectId);

        when(collGrid.withCodecRegistry(any())).thenReturn(collGrid);
        when(collGridChunks.withCodecRegistry(any())).thenReturn(collGridChunks);
        when(database.getCollection(bucketName + ".files", GridFSFile.class)).thenReturn(collGrid);
        when(database.getCodecRegistry()).thenReturn(null);
        when(database.getCollection(bucketName + ".chunks")).thenReturn(collGridChunks);

        getFilesCollection(database, bucketName);
        getChunksCollection(database, bucketName);

        assertThrows(EmployeeNotFoundException.class, () -> sickNoteRepositoryImpl.saveSickNote(sickNote));

    }

    private static MongoCollection<GridFSFile> getFilesCollection(MongoDatabase database, String bucketName) {
        return database.getCollection(bucketName + ".files", GridFSFile.class).withCodecRegistry(CodecRegistries.fromRegistries(new CodecRegistry[]{database.getCodecRegistry(), MongoClientSettings.getDefaultCodecRegistry()}));
    }

    private static MongoCollection<Document> getChunksCollection(MongoDatabase database, String bucketName) {
        return database.getCollection(bucketName + ".chunks").withCodecRegistry(MongoClientSettings.getDefaultCodecRegistry());
    }
}

