package Tests;

import Problem.Individual;
import Utilities.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoggerTests {
    private List<Individual> generation;
    private Logger logger = new Logger();


    @BeforeEach
    public void setUp(){
        generation = new ArrayList<>();
        Individual i1 = new Individual();
        Individual i2 = new Individual();
        Individual i3 = new Individual();

        i1.setFitness(15.0);
        i2.setFitness(20.0);
        i3.setFitness(7.0);

        generation.add(i1);
        generation.add(i2);
        generation.add(i3);
    }


    @Test
    void generationStatisticsTest(){
        String expected = "20.0;7.0;14.0;";
        logger.saveGeneration(generation);
        String actual = logger.getResults().get(0);
        assertEquals(expected, actual);
    }


}
