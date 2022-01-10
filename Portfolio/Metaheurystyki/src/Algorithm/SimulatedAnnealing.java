package Algorithm;

import Problem.CVRP;
import Problem.Individual;
import Utilities.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SimulatedAnnealing implements Algorithm{

    private int iterations;
    private int neighbourhoodSize;
    private int coolingSchema;
    private double initialTemperature;
    private double finalTemperature;
    private double currentTemperature;
    private double alpha;
    private int initializationMethod;
    private int neighbourMethod;
    private Individual[] neighbours;
    private Individual currentSolution;
    private Individual bestNeighbour;
    private Individual globalBest;
    private Logger logger;
    private RandomAlgorithm randomAlgorithm = new RandomAlgorithm();

    public SimulatedAnnealing(int iterations, int neighbourhoodSize, int coolingSchema, double initialTemperature, double finalTemperature, double alpha, int initializationMethod, int neighbourMethod, Logger logger) {
        this.iterations = iterations;
        this.neighbourhoodSize = neighbourhoodSize;
        this.coolingSchema = coolingSchema;
        this.initialTemperature = initialTemperature;
        this.finalTemperature = finalTemperature;
        this.alpha = alpha;
        this.initializationMethod = initializationMethod;
        this.neighbourMethod = neighbourMethod;
        neighbours = new Individual[neighbourhoodSize];
        this.logger = logger;
    }

    @Override
    public void solveProblem(CVRP cvrp) {
        initialization(cvrp);
        currentTemperature = initialTemperature;
        for(int i=0; i<iterations; i++){
            findNeighbours();
            evaluateNeighbours(cvrp);
            findBestNeighbour();
            updateCurrentSolution();
            updateBestSolution();
            cooling(i);
            saveToLogger();
        }
    }

    private void saveToLogger() {
        List<Individual> loggerList = new ArrayList<>();
        loggerList.add(globalBest);
        loggerList.add(currentSolution);
        logger.saveGeneration(loggerList);
    }


    private void cooling(int i) {
        currentTemperature = initialTemperature * Math.pow(alpha, i);
        //currentTemperature -= 10;
        System.out.println(currentTemperature);
    }

    private void findBestNeighbour() {
        bestNeighbour = neighbours[0];
        for (Individual neighbour : neighbours) {
            if(neighbour.getFitness() < bestNeighbour.getFitness()){
                updateIndividual(neighbour.getGenotype(), neighbour.getFitness(), bestNeighbour);
            }
        }

    }

    private void updateCurrentSolution(){
        if(bestNeighbour.getFitness() < currentSolution.getFitness()){
            updateIndividual(bestNeighbour.getGenotype(), bestNeighbour.getFitness(), currentSolution);
        }else{
            Random random = new Random();
            if(random.nextDouble() < acceptanceProbability()){
                updateIndividual(bestNeighbour.getGenotype(), bestNeighbour.getFitness(), currentSolution);
            }
        }
    }

    private void updateIndividual(int[] genotype, double fitness, Individual individual) {
        int[] solutionGenotype = new int[genotype.length];
        for(int i=0; i<solutionGenotype.length; i++){
            solutionGenotype[i] = genotype[i];
        }
        individual.setGenotype(solutionGenotype);
        individual.setFitness(fitness);
    }

    private void evaluateNeighbours(CVRP cvrp) {
        for (Individual neighbour : neighbours) {
            cvrp.calculateFitness(neighbour);
        }
    }

    private void updateBestSolution() {
        if(currentSolution.getFitness() < globalBest.getFitness()){
            updateIndividual(currentSolution.getGenotype(), currentSolution.getFitness(), globalBest);
        }
    }

    private void findNeighbours() {
        for(int i=0; i<neighbours.length; i++){
            neighbours[i] = new Individual();
            int[] neighbourGenotype = new int[currentSolution.getGenotype().length];
            for(int j=0; j<neighbourGenotype.length; j++){
                neighbourGenotype[j] = currentSolution.getGenotype()[j];
            }
            neighbours[i].setGenotype(neighbourGenotype);
            neighbours[i].mutation(neighbourMethod, 1);
        }
    }

    private void initialization(CVRP cvrp) {
        currentSolution = randomAlgorithm.generateIndividual(cvrp);
        bestNeighbour = new Individual();
        globalBest = new Individual();
        updateIndividual(currentSolution.getGenotype(), currentSolution.getFitness(), globalBest);
        cvrp.calculateFitness(currentSolution);
        globalBest.setFitness(currentSolution.getFitness());
    }

    private double acceptanceProbability(){
        double fitnessDifference = (currentSolution.getFitness() - bestNeighbour.getFitness());
        return Math.exp(fitnessDifference/currentTemperature);
    }


}
