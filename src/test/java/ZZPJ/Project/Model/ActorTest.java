package ZZPJ.Project.Model;

import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ActorTest {
    @Mock
    public DataManager mockedDataManager;

    @Mock
    public Document mockedDocument;

    @InjectMocks
    public Actor sutActor;

    @Before
    public void setup() {
        initMocks(this);
    }

    @Test
    public void downloadActorInfoTest(){
        Date date = new Date();
        when(mockedDataManager.downloadDocument(anyString())).thenReturn(mockedDocument);
        when(mockedDataManager.getPageId(any(Document.class))).thenReturn("nm12345");
        when(mockedDataManager.getActorName(any(Document.class))).thenReturn("Leonardo DiCaprio");
        when(mockedDataManager.getActorBirthDate(any(Document.class))).thenReturn(date);

        String url = "";
        sutActor.downloadActorInfo(mockedDataManager, url);
        assertEquals("nm12345", sutActor.getId());
        assertEquals("Leonardo DiCaprio", sutActor.getName());
        assertEquals(date, sutActor.getBirthDate());
        assertEquals(Collections.emptyList(), sutActor.getMovies());

        sutActor.showActorInformatation();
    }
}
