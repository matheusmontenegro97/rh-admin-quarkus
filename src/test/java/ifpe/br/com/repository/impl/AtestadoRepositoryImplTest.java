package ifpe.br.com.repository.impl;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.model.GridFSFile;
import ifpe.br.com.model.Atestado;
import ifpe.br.com.model.Funcionario;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AtestadoRepositoryImplTest {

    @Mock
    MongoClient mongoClient;

    @InjectMocks
    AtestadoRepositoryImpl atestadoRepositoryImpl;

    @Mock
    MongoDatabase database;


    @Mock
    MongoCollection<Atestado> coll;

    @Mock
    MongoCollection<GridFSFile> collGrid;

    @Mock
    MongoCollection<Document> collGridChunks;

    @Mock
    MongoCollection<Object> collGridObject;

    @Mock
    FuncionarioRepositoryImpl funcionarioRepository;

    @Mock
    GridFSBucket gridFSBucket;

    @Mock
    ObjectId objectId;

    @Mock
    FindIterable<Object> findIterable;

    @Test
    public void saveAtestadoTest() throws Exception {
        Atestado atestado = new Atestado();
        atestado.setCodigoFuncionario("1");
        atestado.setAtestado("/C:/Users/mathe/Downloads/693876.jpg");

        Funcionario func = new Funcionario();
        String bucketName = "testBucket";

        when(mongoClient.getDatabase(anyString())).thenReturn(database);
        when(database.getCollection(anyString(), eq(Atestado.class))).thenReturn(coll);
        when(funcionarioRepository.findFuncionarioById(anyString())).thenReturn(func);

        File file = new File(atestado.getAtestado());
        InputStream targetStream = new FileInputStream(file);
        lenient().when(gridFSBucket.uploadFromStream(atestado.getCodigoAtestado(), targetStream)).thenReturn(objectId);
        when(database.getCollection(eq("fs.files"), eq(GridFSFile.class))).thenReturn(collGrid);
        when(database.getCollection(eq("fs.chunks"))).thenReturn(collGridChunks);

        CodecRegistry codecRegistry = null;
        when(collGrid.withCodecRegistry(any())).thenReturn(collGrid);
        when(collGrid.withDocumentClass(any())).thenReturn(collGridObject);
        when(collGrid.withDocumentClass(any()).withReadPreference(any())).thenReturn(collGridObject);
        when(collGridObject.find()).thenReturn(findIterable);
        when(findIterable.projection(any())).thenReturn(findIterable);
        when(findIterable.first()).thenReturn(new Object());
        //when(collGridObject.find().projection(any()).first()).thenReturn(findIterable);
        when(collGridChunks.withCodecRegistry(any())).thenReturn(collGridChunks);
        when(database.getCollection(bucketName + ".files", GridFSFile.class)).thenReturn(collGrid);
        when(database.getCodecRegistry()).thenReturn(codecRegistry);
        when(database.getCollection(bucketName + ".chunks")).thenReturn(collGridChunks);

        getFilesCollection(database, bucketName);
        getChunksCollection(database, bucketName);

        atestadoRepositoryImpl.saveAtestado(atestado);

        verify(coll, times(1)).insertOne(atestado);
    }

    private static MongoCollection<GridFSFile> getFilesCollection(MongoDatabase database, String bucketName) {
        return database.getCollection(bucketName + ".files", GridFSFile.class).withCodecRegistry(CodecRegistries.fromRegistries(new CodecRegistry[]{database.getCodecRegistry(), MongoClientSettings.getDefaultCodecRegistry()}));
    }

    private static MongoCollection<Document> getChunksCollection(MongoDatabase database, String bucketName) {
        return database.getCollection(bucketName + ".chunks").withCodecRegistry(MongoClientSettings.getDefaultCodecRegistry());
    }
}

