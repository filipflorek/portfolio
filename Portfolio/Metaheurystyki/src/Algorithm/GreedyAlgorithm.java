package Algorithm;

import Problem.CVRP;
import Problem.City;
import Problem.Individual;
import Utilities.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GreedyAlgorithm implements Algorithm{
    private List<Integer> citiesToVisit = new ArrayList<>();
    private ArrayList<Individual> solutions;
    private int capacityLeft;
    private double distanceWeight;
    private double capacityWeight;
    private Logger logger;

    public GreedyAlgorithm(Logger logger, double capacityWeight, double distanceWeight){
        this.logger = logger;
        this.distanceWeight = distanceWeight;
        this.capacityWeight = capacityWeight;
    }
    public GreedyAlgorithm(){}


    @Override
    public void solveProblem(CVRP cvrp) {
        solutions = new ArrayList();
        for (int i=2; i<=cvrp.getDimension(); i++){
            ArrayList<Integer> solution = generateSolution(i, cvrp);
            double fitness = cvrp.calculateDistance(solution);
            Individual individual = new Individual(listToArray(solution));
            individual.setFitness(fitness);
            solutions.add(individual);
        }
        logger.saveGeneration(solutions);
    }

    private int[] listToArray(ArrayList<Integer> solution) {
        int[] genotype = new int[solution.size()];
        for(int i=0; i<solution.size(); i++){
            genotype[i] = solution.get(i);
        }
        return genotype;
    }

    public ArrayList<Integer> generateSolution(int startingCity, CVRP cvrp){
        ArrayList<Integer> solution = new ArrayList<>();
        initializeListOfCities(cvrp);
        solution.add(cvrp.getDepotID());
        solution.add(startingCity);
        capacityLeft = cvrp.getCapacity() - cvrp.getCities().get(startingCity - 1).getDemand();
        citiesToVisit.remove(Integer.valueOf(startingCity));
        int currentCity = startingCity;
        while(!citiesToVisit.isEmpty()){
            int nearestSuitable = getBestCity(currentCity, citiesToVisit, cvrp);
            solution.add(nearestSuitable);
            citiesToVisit.remove(Integer.valueOf(nearestSuitable));
            currentCity = nearestSuitable;
        }
        if(solution.get(solution.size()-1) != cvrp.getDepotID()){
            solution.add(cvrp.getDepotID());
        }
        return solution;
    }

    private int getBestCity(int currentCity, List<Integer> citiesToVisit, CVRP cvrp) {
        double nearest = Double.MAX_VALUE;
        int bestCityID = -1;
        int capacityToSubtract = 0;
        for ( int cityID : citiesToVisit) {
            City cityToCheck = cvrp.getCityByID(cityID);
            if(capacityLeft >= cityToCheck.getDemand()){
                if(nearest > cityScore(cvrp.getDistanceMatrix()[currentCity-1][cityToCheck.getId()-1],cityToCheck.getDemand())){
                    nearest = cvrp.getDistanceMatrix()[currentCity-1][cityToCheck.getId()-1];
                    bestCityID = cityToCheck.getId();
                    capacityToSubtract = cityToCheck.getDemand();
                }
            }
        }
        if(bestCityID == -1){
            capacityLeft = cvrp.getCapacity();
            return cvrp.getDepotID();
        }else{
            capacityLeft -= capacityToSubtract;
            return bestCityID;
        }
    }

    private double cityScore(double distance, int demand){
        return distanceWeight*distance - capacityWeight*demand;
    }

    public void printSolutions(){
        for (Individual solution : solutions) {
            System.out.println("______");
            for ( int city : solution.getGenotype()) {
                System.out.println(city);
            }
        }
        System.out.println(solutions.size());
    }

    private void initializeListOfCities(CVRP cvrp){
        for (int i = 0; i < cvrp.getCities().size(); i++) {
            if(cvrp.getCities().get(i).getId() != cvrp.getDepotID()){
                citiesToVisit.add(cvrp.getCities().get(i).getId());
            }
        }
    }

    public ArrayList<Individual> getSolutions() {
        return solutions;
    }
}
