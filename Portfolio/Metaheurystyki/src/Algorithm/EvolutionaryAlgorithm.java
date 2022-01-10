package Algorithm;

import Problem.CVRP;
import Problem.Individual;
import Problem.Population;
import Utilities.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EvolutionaryAlgorithm implements Algorithm{

    private final int SWAP_MUTATION = 1;
    private final int INVERSION_MUTATION = 2;
    private int popSize;
    private int generations;
    private double crossProb;
    private double mutationProb;
    private double swapMutationProb;
    private double inversionMutationProb;
    private double mutationWeight;
    private int mixedMutation;
    private int selectionMethod;
    private int tournamentSize;
    private int initializationMethod;
    private int mutationMethod;
    private int crossoverMethod;
    private Population currentPopulation;
    private Population nextPopulation;
    private Logger logger;
    private Random random = new Random();

    public EvolutionaryAlgorithm(int popSize,
                                 int generations,
                                 double crossProb,
                                 double mutationProb,
                                 int selectionMethod,
                                 int tournamentSize,
                                 int initializationMethod,
                                 int mutationMethod,
                                 int crossoverMethod,
                                 double swapMutationProb,
                                 double inversionMutationProb,
                                 double mutationWeight,
                                 int mixedMutation,
                                 Logger logger) {
        this.popSize = popSize;
        this.generations = generations;
        this.crossProb = crossProb;
        this.mutationProb = mutationProb;
        this.selectionMethod = selectionMethod;
        this.tournamentSize = tournamentSize;
        this.initializationMethod = initializationMethod;
        this.mutationMethod = mutationMethod;
        this.crossoverMethod = crossoverMethod;
        this.mixedMutation = mixedMutation;
        this.mutationWeight = mutationWeight;
        this.swapMutationProb = swapMutationProb;
        this.inversionMutationProb = inversionMutationProb;
        this.logger = logger;
    }



    @Override
    public void solveProblem(CVRP cvrp) {
        int swapCounter = 0;
        int inversionCounter = 0;
        currentPopulation = initialize(cvrp);
        nextPopulation = new Population();
        for(int i=0; i<generations; i++){
            evaluate(currentPopulation, cvrp);
            while(nextPopulation.getPopulation().size() < currentPopulation.getPopulation().size()){
                Individual firstParent = currentPopulation.selection(selectionMethod, (popSize*tournamentSize)/100);
                Individual secondParent = currentPopulation.selection(selectionMethod, (popSize*tournamentSize)/100);
                Individual child = firstParent.crossover(crossoverMethod, secondParent, crossProb);
                if(mixedMutation>=1){
                    if(nextPopulation.getPopulation().size() < mutationWeight * currentPopulation.getPopulation().size()){
                        child.mutation(INVERSION_MUTATION, inversionMutationProb);
                        inversionCounter++;
                    }else{
                        child.mutation(SWAP_MUTATION, swapMutationProb);
                        swapCounter++;
                    }
                }else{
                    child.mutation(mutationMethod, mutationProb);
                }
                nextPopulation.getPopulation().add(child);
            }
            currentPopulation = nextPopulation;
            nextPopulation = new Population();
        }
        System.out.println("SWAP: " + swapCounter + " INVERSION: " + inversionCounter);
    }


    private Population initialize(CVRP cvrp){
        List<Individual> population = new ArrayList<>();
        for(int i=0; i<popSize; i++){
            population.add(generateIndividual(cvrp));
        }
        return new Population(population);
    }

    private void evaluate(Population population, CVRP cvrp){
        for (Individual individual : population.getPopulation()) {
            cvrp.calculateFitness(individual);
        }
        logger.saveGeneration(population.getPopulation());
    }

    private Individual generateIndividual(CVRP cvrp) {
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


}
