package Algorithm;

import Problem.CVRP;
import Problem.Individual;
import Utilities.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LocalSearch implements Algorithm{

    protected int initializationMethod;
    protected int neighbourMethod;
    protected int iterations;
    protected int neighbourhoodSize;
    protected Individual[] neighbours;
    protected Individual currentSolution;
    protected Individual bestNeighbour;
    protected Individual globalBest;
    protected RandomAlgorithm randomAlgorithm = new RandomAlgorithm();
    protected Logger logger;


    public LocalSearch(int iterations, int neighbourhoodSize,int initializationMethod, int neighbourMethod, Logger logger) {
        this.iterations = iterations;
        this.neighbourhoodSize = neighbourhoodSize;
        this.initializationMethod = initializationMethod;
        this.neighbourMethod = neighbourMethod;
        neighbours = new Individual[neighbourhoodSize];
        this.logger = logger;
    }

    @Override
    public void solveProblem(CVRP cvrp) {
        initialization(cvrp);
        for(int i=0; i<iterations; i++){
            findNeighbours();
            evaluateNeighbours(cvrp);
            findBestNeighbour();
            updateCurrentSolution();
            updateBestSolution();
            saveToLogger();
        }
    }

    protected void findBestNeighbour() {
        bestNeighbour = neighbours[0];
        for (Individual neighbour : neighbours) {
            if(neighbour.getFitness() < bestNeighbour.getFitness()){
                updateIndividual(neighbour.getGenotype(), neighbour.getFitness(), bestNeighbour);
            }
        }
    }

    protected void updateCurrentSolution(){
        if(bestNeighbour.getFitness() < currentSolution.getFitness()){
            updateIndividual(bestNeighbour.getGenotype(), bestNeighbour.getFitness(), currentSolution);
        }
    }

    protected void updateIndividual(int[] genotype, double fitness, Individual individual) {
        int[] solutionGenotype = new int[genotype.length];
        for(int i=0; i<solutionGenotype.length; i++){
            solutionGenotype[i] = genotype[i];
        }
        individual.setGenotype(solutionGenotype);
        individual.setFitness(fitness);
    }

    protected void evaluateNeighbours(CVRP cvrp) {
        for (Individual neighbour : neighbours) {
            cvrp.calculateFitness(neighbour);
        }
    }

    protected void updateBestSolution() {
        if(currentSolution.getFitness() < globalBest.getFitness()){
            updateIndividual(currentSolution.getGenotype(), currentSolution.getFitness(), globalBest);
        }
    }

    protected void findNeighbours() {
        for(int i=0; i<neighbours.length; i++){
            updateIndividual(currentSolution.getGenotype(), 0.0, neighbours[i]);
//            neighbours[i] = new Individual();
//            int[] neighbourGenotype = new int[currentSolution.getGenotype().length];
//            for(int j=0; j<neighbourGenotype.length; j++){
//                neighbourGenotype[j] = currentSolution.getGenotype()[j];
//            }
//            neighbours[i].setGenotype(neighbourGenotype);
            neighbours[i].mutation(neighbourMethod, 1);
        }
    }

    protected void initialization(CVRP cvrp) {
        currentSolution = randomAlgorithm.generateIndividual(cvrp);
        bestNeighbour = new Individual();
        globalBest = new Individual();
        neighbours = new Individual[neighbourhoodSize];
        for(int i=0; i<neighbourhoodSize; i++){
            neighbours[i] = new Individual();
        }
        updateIndividual(currentSolution.getGenotype(), currentSolution.getFitness(), globalBest);
        cvrp.calculateFitness(currentSolution);
        globalBest.setFitness(currentSolution.getFitness());

    }

    protected void saveToLogger() {
        List<Individual> loggerList = new ArrayList<>();
        loggerList.add(globalBest);
        loggerList.add(currentSolution);
        logger.saveGeneration(loggerList);
    }
}
