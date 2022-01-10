package Algorithm;

import Problem.CVRP;
import Problem.Individual;
import Utilities.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TS extends LocalSearch{


    private Individual[] tabuList;
    private int currentTabuIndex = 0;
    private int tabuSize;
    private List<Individual> loggerList;

    public TS(int iterations,
              int neighbourhoodSize,
              int initializationMethod,
              int neighbourMethod,
              Logger logger,
              int tabuSize) {
        super(iterations, neighbourhoodSize, initializationMethod, neighbourMethod, logger);
        this.tabuSize = tabuSize;
        tabuList = new Individual[tabuSize];
        for(int i=0; i<tabuSize; i++){
            tabuList[i] = new Individual();
        }
        loggerList = new ArrayList<>();
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
            updateTabuList();
            saveToLogger();
        }
    }

    private void updateTabuList() {
       updateIndividual(currentSolution.getGenotype(), currentSolution.getFitness(), tabuList[currentTabuIndex%tabuList.length]);
    }

    @Override
    protected void findBestNeighbour() {
        bestNeighbour = neighbours[0];
        for (Individual neighbour : neighbours) {
            if(neighbour.getFitness() < bestNeighbour.getFitness()){
                if(isNotInTabuList(neighbour)){
                    updateIndividual(neighbour.getGenotype(), neighbour.getFitness(), bestNeighbour);
                }
            }
        }

        bestNeighbour = neighbours[0];
        for (Individual neighbour : neighbours) {
            if(neighbour.getFitness() < bestNeighbour.getFitness()){
                updateIndividual(neighbour.getGenotype(), neighbour.getFitness(), bestNeighbour);
            }
        }
    }

    private boolean isNotInTabuList(Individual neighbour) {
        boolean isNot = true;
        int index = 0;
        while(isNot && index < tabuList.length && tabuList[index].getGenotype() != null){
            if(tabuList[index].sameGenotypes(neighbour.getGenotype())){
                isNot = false;
            }
            index ++;
        }
        return isNot;
    }

    @Override
    protected void saveToLogger() {
        if(loggerList.size() == 0){
            for(int i=0; i<neighbours.length; i++){
                loggerList.add(neighbours[i]);
            }
        }else{
            for(int i=0; i<neighbours.length; i++){
                loggerList.set(i, neighbours[i]);
            }
        }
        //System.out.println(bestSolution.getFitness() + " _______ " + currentSolution.getFitness());
        logger.saveGeneration(loggerList);
        logger.updateBestTabu(globalBest.getFitness());
    }
}
