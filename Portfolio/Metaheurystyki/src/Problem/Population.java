package Problem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Population {

    List<Individual> population;

    public Population(){
        population = new ArrayList<>();
    }

    public Population(List<Individual> population){
        this.population = population;
    }

    public Individual selection(int selectionMethod, int tournamentSize){
        if(selectionMethod == 0){
            return tournamentSelection(tournamentSize);
        }else{
            return rouletteSelection();
        }
    }

    private Individual rouletteSelection() {
        double sumOfFitness = population.stream().mapToDouble(Individual::getFitness).sum();
        int fixedPoint = new Random().nextInt((int)sumOfFitness);
        double partialSum = 0.0;
        for (Individual individual : population) {
            partialSum += individual.getFitness();
            if(partialSum >= fixedPoint) return individual;
        }
        return null;
    }

    private Individual tournamentSelection(int tournamentSize) {
        Individual bestParticipant = new Individual();
        Random random = new Random();
        Double min = Double.MAX_VALUE;
        for(int i=0; i<tournamentSize; i++){
            int randomIndex = random.nextInt(population.size());
            if(min > population.get(randomIndex).getFitness()){
                min = population.get(randomIndex).getFitness();
                bestParticipant = population.get(randomIndex);
            }
        }
        return bestParticipant;
//        List<Individual> participants = new ArrayList<>();
//        Random random = new Random();
//        while(participants.size() < tournamentSize){
//            participants.add(population.get(random.nextInt(population.size())));
//        }
//        return Collections.min(participants);
    }


    public List<Individual> getPopulation() {
        return population;
    }

    public void setPopulation(ArrayList<Individual> population) {
        this.population = population;
    }


}
