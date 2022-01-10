package Algorithm;

import Problem.CVRP;
import Problem.Individual;
import Utilities.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class RandomAlgorithm implements Algorithm{
    private int numberOfIndividuals;
    private Logger logger;
    private List<Individual> generation;

    public RandomAlgorithm(Logger logger, int numberOfIndividuals){
        this.logger = logger;
        this.numberOfIndividuals = numberOfIndividuals;
        generation = new ArrayList<>();
    }

    public RandomAlgorithm(){}

    @Override
    public void solveProblem(CVRP cvrp) {
        for(int i=0; i< numberOfIndividuals; i++){
            Individual individual = generateIndividual(cvrp);
            cvrp.calculateFitness(individual);
            generation.add(individual);
        }
        logger.saveGeneration(generation);
    }

    public Individual generateIndividual(CVRP cvrp) {
        int[] genotype = new int[cvrp.getDimension()-1];

        for (int i=0; i < genotype.length; i++){
           genotype[i] = i+2;
        }

       return new Individual(shuffleArray(genotype));
    }

    private int[] shuffleArray(int[] array){
        Random rand = new Random();
        for (int i = 0; i < array.length; i++) {
            int randomIndexToSwap = rand.nextInt(array.length);
            int temp = array[randomIndexToSwap];
            array[randomIndexToSwap] = array[i];
            array[i] = temp;
        }
        return array;
    }

    public List<Individual> getGeneration() {
        return generation;
    }
}
