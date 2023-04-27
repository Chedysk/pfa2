package tn.esprit.spring;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//class ExamThourayaS2ApplicationTests {
//
//	@Test
//	void contextLoads() {
//	}
//
//}
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javassist.expr.NewArray;
import junit.framework.Assert;
import tn.esprit.spring.entities.Train;
import tn.esprit.spring.entities.Ville;
import tn.esprit.spring.entities.Voyage;
import tn.esprit.spring.entities.Voyageur;
import tn.esprit.spring.repository.TrainRepository;
import tn.esprit.spring.repository.VoyageRepository;
import tn.esprit.spring.repository.VoyageurRepository;
import tn.esprit.spring.services.TrainServiceImpl;
import tn.esprit.spring.services.VoyageServiceImpl;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TrainServiceImplTest {

    @Mock
    private VoyageurRepository voyageurRepository;

    @Mock
    private TrainRepository trainRepository;

    @Mock
    private VoyageRepository voyageRepository;

     @InjectMocks
     private TrainServiceImpl trainService;

     @InjectMocks
     private VoyageServiceImpl voyageService;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testTrainPlacesLibres() {
        List<Voyage> voyages = new ArrayList<>();
        Train train1 = new Train();
        train1.setNbPlaceLibre(2);
        Voyage voyage1 = new Voyage();
        voyage1.setTrain(train1);
        voyage1.setGareDepart(Ville.TUNIS);
        voyages.add(voyage1);
        Voyage voyage2 = new Voyage();
        voyage2.setTrain(train1);
        voyage2.setGareDepart(Ville.TUNIS);
        voyages.add(voyage2);
        when(voyageRepository.findAll()).thenReturn(voyages);
        assertEquals(trainService.TrainPlacesLibres(Ville.TUNIS), 2);
    }

    @Test
    public void testListerTrainsIndirects() {
        List<Voyage> voyages = new ArrayList<>();
        Voyage voyage1 = new Voyage();
        voyage1.setGareDepart(Ville.TUNIS);
        voyage1.setGareArrivee(Ville.SFAX);
        voyage1.setTrain(new Train());
        voyages.add(voyage1);

        Voyage voyage2 = new Voyage();
        voyage2.setGareDepart(Ville.SFAX);
        voyage2.setGareArrivee(Ville.SOUSSE);
        voyage2.setTrain(new Train());
        voyages.add(voyage2);

        Voyage voyage3 = new Voyage();
        voyage3.setGareDepart(Ville.TUNIS);
        voyage3.setGareArrivee(Ville.SOUSSE);
        voyage3.setTrain(new Train());
        voyages.add(voyage3);

        when(voyageRepository.findAll()).thenReturn(voyages);

        List<Train> trainsIndirects = trainService.ListerTrainsIndirects(Ville.TUNIS, Ville.SOUSSE);

        Assert.assertEquals(1, trainsIndirects.size());
    }
}

 
