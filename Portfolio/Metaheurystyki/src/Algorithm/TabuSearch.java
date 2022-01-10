package Algorithm;

import Problem.CVRP;
import Problem.Individual;
import Utilities.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TabuSearch implements Algorithm {
    private Individual currentSolution;
    private Individual bestSolution;
    private Individual bestNeighbour;
    private Individual[] neighbours;
    private Individual[] tabuList;
    private int iterations;
    private int initializationMethod;
    private int neighbourMethod;
    private int currentTabuIndex = 0;
    private Logger logger;
    private RandomAlgorithm randomAlgorithm = new RandomAlgorithm();

    public TabuSearch(int iterations, int tabuSize, int neighboursQuantity, int initializationMethod, int neighbourMethod, Logger logger){
        this.iterations = iterations;
        this.initializationMethod = initializationMethod;
        this.neighbourMethod = neighbourMethod;
        neighbours = new Individual[neighboursQuantity];
        tabuList = new Individual[tabuSize];
        this.logger = logger;
    }

    @Override
    public void solveProblem(CVRP cvrp) {
        initialize(initializationMethod, cvrp);
        for(int i=0; i<iterations; i++){
            findNeighbours();
            evaluateNeighbours(cvrp);
            findBestNeighbour();
            updateCurrentSolution();
            updateBestSolution();
            updateTabuList();
            saveToLogger();
        }
    }

    private void findBestNeighbour() {
        bestNeighbour = neighbours[0];
        for (Individual neighbour : neighbours) {
            if(neighbour.getFitness() < bestNeighbour.getFitness()){
                if(isNotInTabuList(neighbour)){
                    updateIndividual(neighbour.getGenotype(), neighbour.getFitness(), bestNeighbour);
                }
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


    private void updateTabuList() {
        tabuList[currentTabuIndex%tabuList.length] = new Individual();
        updateIndividual(currentSolution.getGenotype(), currentSolution.getFitness(), tabuList[currentTabuIndex%tabuList.length]);
    }

    private void updateBestSolution() {
        if(currentSolution.getFitness() < bestSolution.getFitness()){
            updateIndividual(currentSolution.getGenotype(), currentSolution.getFitness(), bestSolution);
        }
    }

    private void updateCurrentSolution() {
        updateIndividual(bestNeighbour.getGenotype(), bestNeighbour.getFitness(), currentSolution);
    }

    private boolean isNotInTabuList(Individual neighbour) {
        boolean isNot = true;
        int index = 0;
        while(isNot && index < tabuList.length && tabuList[index] != null){
            if(tabuList[index].sameGenotypes(neighbour.getGenotype())){
                isNot = false;
            }
            index ++;
        }
        return isNot;
    }

    private void evaluateNeighbours(CVRP cvrp) {
        for (Individual individual : neighbours) {
            cvrp.calculateFitness(individual);
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

    private void initialize(int initializationMethod, CVRP cvrp) {
        if(initializationMethod == 0){
            currentSolution = randomAlgorithm.generateIndividual(cvrp);
            bestNeighbour = new Individual();
            bestSolution = new Individual();
            updateIndividual(currentSolution.getGenotype(), currentSolution.getFitness(), bestSolution);
            cvrp.calculateFitness(currentSolution);
            bestSolution.setFitness(currentSolution.getFitness());
        }else{

        }
    }

    private void saveToLogger() {
        //System.out.println(bestSolution.getFitness() + " _______ " + currentSolution.getFitness());
        List<Individual> loggerList = new ArrayList<>();
        Collections.addAll(loggerList, neighbours);
        logger.saveGeneration(loggerList);
        logger.updateBestTabu(bestSolution.getFitness());
    }
}
