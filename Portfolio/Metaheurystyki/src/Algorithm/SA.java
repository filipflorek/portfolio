package Algorithm;

import Problem.CVRP;
import Utilities.Logger;

import java.util.Random;

public class SA extends LocalSearch{
    private int coolingSchema;
    private double initialTemperature;
    private double finalTemperature;
    private double currentTemperature;
    private double alpha;

    public SA(int iterations,
              int neighbourhoodSize,
              int initializationMethod,
              int neighbourMethod,
              Logger logger,
              int coolingSchema,
              double initialTemperature,
              double alpha) {
        super(iterations, neighbourhoodSize, initializationMethod, neighbourMethod, logger);
        this.coolingSchema = coolingSchema;
        this.alpha = alpha;
        this.initialTemperature = initialTemperature;
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

    private void cooling(int i) {
        currentTemperature = initialTemperature * Math.pow(alpha, i);
        //currentTemperature -= 10;
        //System.out.println(currentTemperature);
    }

    @Override
    protected void updateCurrentSolution(){
        if(bestNeighbour.getFitness() < currentSolution.getFitness()){
            updateIndividual(bestNeighbour.getGenotype(), bestNeighbour.getFitness(), currentSolution);
        }else{
            Random random = new Random();
            if(random.nextDouble() < acceptanceProbability()){
                updateIndividual(bestNeighbour.getGenotype(), bestNeighbour.getFitness(), currentSolution);
            }
        }
    }

    private double acceptanceProbability(){
        double fitnessDifference = (currentSolution.getFitness() - bestNeighbour.getFitness());
        return Math.exp(fitnessDifference/currentTemperature);
    }
}
