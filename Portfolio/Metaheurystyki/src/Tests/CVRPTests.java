package Tests;

import Problem.CVRP;
import Problem.Individual;
import Utilities.Loader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class CVRPTests {
    CVRP cvrp = CVRP.getInstance();



    @Test
    void fitnessTest(){
        CVRP cvrp = CVRP.getInstance();
        String filePath = "res\\input\\A-n32-k5.txt";
        Loader loader = new Loader(filePath, cvrp);
        loader.loadFromFile();
        cvrp.initializeDistanceMatrix();
        Individual individual = new Individual();
        int[] genotype = new int[]{27, 30, 11, 26, 10, 12, 5, 29, 24, 7, 14, 22, 32, 20, 18, 3, 4, 9, 19, 23, 16, 6, 21, 28, 13, 2, 8, 17, 31, 25, 15};
        individual.setGenotype(genotype);
        cvrp.calculateFitness(individual);
        assertEquals(721.79, individual.getFitness());
    }

    @Test
    void swapMutationTest(){
        Individual individual = new Individual();
        int[] genotype = new int[]{2,5,6,4,3,1};
        individual.setGenotype(genotype);
        int[] expected = new int[]{6,5,2,4,3,1};
        individual.swapMutationOnGene( 0.1);
        assertTrue(Arrays.equals(expected, individual.getGenotype()));
    }

    @Test
    void inversionMutationTest(){
        Individual individual = new Individual();
        int[] genotype = new int[]{1,5,6,4,3,2};
        individual.setGenotype(genotype);
        int[] expected = new int[]{2,3,4,6,5,1};
        individual.inversionMutation(0,5);
        assertTrue(Arrays.equals(expected, individual.getGenotype()));
    }

    @Test
    void pmxCrossoverTest(){
        Individual parent1 = new Individual();
        int[] genotype1 = new int[]{2,4,3,1,6,5};
        parent1.setGenotype(genotype1);
        Individual parent2 = new Individual();
        int[] genotype2 = new int[]{4,6,5,3,1,2};
        parent2.setGenotype(genotype2);
        Individual child = parent1.pmxCrossover(parent2, 1, 3);
        int[] expected = new int[]{2,6,5,1,4,3};
        assertTrue(Arrays.equals(expected, child.getGenotype()));
    }

    @Test
    void compareGenotypesTest(){
        Individual individual = new Individual();
        int[] genotype = new int[]{1,2,3,4,5,6};
        individual.setGenotype(genotype);
        int[] other = new int[]{1,2,3,5,4,6};
        assertFalse(individual.sameGenotypes( other));
    }
}
