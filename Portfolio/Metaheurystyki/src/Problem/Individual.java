package Problem;

import java.util.*;
import java.util.stream.Collectors;

public class Individual implements Comparable<Individual>{

    private int[] genotype;
    private double fitness;
    private int trucks;

    public Individual(){
    }

    public Individual(int[] genotype){
        this.genotype = genotype;
    }

    public void mutation(int mutationMethod, double mutationProb){
        Random random = new Random();
        double prob = random.nextDouble();
        int indexA = random.nextInt(genotype.length);
        int indexB = random.nextInt(genotype.length);
        while (indexA >= indexB) {
            indexA = random.nextInt(genotype.length);
            indexB = random.nextInt(genotype.length);
        }
        if(mutationMethod == 1){
            swapMutationOnGene(mutationProb);
        }else{
            if(prob <= mutationProb){
                if(mutationMethod == 0){
                    swapMutation(indexA, indexB);
                }else{
                    inversionMutation(indexA, indexB);
                }
            }
        }
    }

    public void swapMutationOnGene(double mutationProb){
        Random random = new Random();
        for(int i=0; i<genotype.length; i++){
            if(random.nextDouble() < mutationProb){
                int swapTo = random.nextInt(genotype.length);
                int tmp = genotype[i];
                genotype[i] = genotype[swapTo];
                genotype[swapTo] = tmp;
            }
        }
    }

    public void swapMutation(int indexA, int indexB){
        int tmp = genotype[indexA];
        genotype[indexA] = genotype[indexB];
        genotype[indexB] = tmp;
    }

    public void inversionMutation(int indexA, int indexB){
        int middlePoint = indexA + ((indexB  + 1) - indexA) / 2;
        int endPoint = indexB;
        for(int i=indexA; i < middlePoint; i++){
            int tmp = genotype[i];
            genotype[i] = genotype[endPoint];
            genotype[endPoint] = tmp;
            endPoint--;
        }
    }

    public Individual crossover(int crossoverMethod, Individual other, double crossoverProb){
        Random random = new Random();
        double prob = random.nextDouble();
        if(prob < crossoverProb){
            int indexA = random.nextInt(genotype.length);
            int indexB = random.nextInt(genotype.length);
            while (indexA >= indexB) {
                indexA = random.nextInt(genotype.length);
                indexB = random.nextInt(genotype.length);
            }
            if(crossoverMethod == 0){
                return oxCrossover(other, indexA, indexB);
            }else{
                return pmxCrossover(other, indexA, indexB);
            }
        }else{
            return other;
        }

    }

    public Individual oxCrossover(Individual other, int indexA, int indexB){
        int[] newGenotype = new int[genotype.length];
        for(int i=indexA; i<=indexB; i++){
            newGenotype[i] = genotype[i];
        }
        List<Integer> lackingCities = new ArrayList<>();
        for(int i=0; i< other.getGenotype().length; i++){
            if(!contains(newGenotype, other.getGenotype()[i]))
                lackingCities.add(other.getGenotype()[i]);
        }
        for(int i=0; i< newGenotype.length; i++){
            if(newGenotype[i] == 0){
                newGenotype[i] = lackingCities.get(0);
                lackingCities.remove(0);
            }
        }

        return new Individual(newGenotype);
    }

    public Individual pmxCrossover(Individual other, int indexA, int indexB){
        int[] childGenotype = new int[genotype.length];
        for (int j=0; j<genotype.length; j++){
            childGenotype[j] = genotype[j];
        }
        for(int i=indexA; i<indexB; i++){
            int tmp = other.getGenotype()[i];
            childGenotype[indexOf(childGenotype, tmp)] = childGenotype[i];
            childGenotype[i] = tmp;
        }
        return new Individual(childGenotype);

    }

    private boolean contains(int[] array, int number){
        boolean contains = false;
        int index = 0;
        while(!contains && index<array.length){
            if(array[index] == number) contains = true;
            index++;
        }
        return contains;
    }

    private int indexOf(int[] array, int number){
        for(int i=0; i<array.length; i++){
            if(array[i] == number)
                return i;
        }
        return -1;
    }

    public int[] getGenotype() {
        return genotype;
    }

    public void setGenotype(int[] genotype) {
        this.genotype = genotype;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public int getTrucks() {
        return trucks;
    }

    public void setTrucks(int trucks) {
        this.trucks = trucks;
    }

    public boolean sameGenotypes(int[] otherGenotype){
        boolean equal = true;
        int index = 0;
        while(equal && index < otherGenotype.length){
            if(genotype[index] != otherGenotype[index]){
                equal = false;
            }
            index++;
        }
        return equal;
    }

    @Override
    public int compareTo(Individual other) {
        return Double.compare(getFitness(), other.getFitness());
    }

    @Override
    public String toString() {
        String genotypeString = "";
        for (int gene : genotype) {
            genotypeString += String.valueOf(gene) + ", ";
        }
        return "Individual{" +
                "genotype=" + genotypeString +
                ", fitness=" + fitness +
                '}';
    }

//    public double calculateFitness(CVRP cvrp){
//        List<Integer> feasibleRoute = prepareFeasibleRoute(cvrp);
//        fitness = calculateDistance(cvrp, feasibleRoute);
//        return fitness;
//    }
//
//    private List<Integer> prepareFeasibleRoute(CVRP cvrp){
//        List<Integer> feasibleRoute = new ArrayList<>();
//        for ( int gene : genotype) {
//            feasibleRoute.add(gene);
//        }
//        feasibleRoute.add(0, cvrp.getDepotID());
//        ListIterator<Integer> iterator = feasibleRoute.listIterator();
//        int capacityLeft = cvrp.getCapacity();
//        while(iterator.hasNext()){
//            int currentCity = iterator.next();
//            capacityLeft -= cvrp.getCities().get(currentCity - 1).getDemand();
//            if(capacityLeft == 0){
//                iterator.add(cvrp.getDepotID());
//                capacityLeft = cvrp.getCapacity();
//                iterator.previous();
//            }
//            if(capacityLeft < 0){
//                iterator.remove();
//                iterator.add(cvrp.getDepotID());
//                iterator.add(currentCity);
//                capacityLeft = cvrp.getCapacity();
//                iterator.previous();
//            }
//        }
//        if(feasibleRoute.get(feasibleRoute.size()-1) != cvrp.getDepotID())
//            feasibleRoute.add(cvrp.getDepotID());
//
//        for (int node : feasibleRoute) {
//            System.out.println(node);
//        }
//        return feasibleRoute;
//    }
//
//    private double calculateDistance(CVRP cvrp, List<Integer> route){
//        double distance = 0.0;
//        for (int i=0; i < route.size() - 1; i++){
//            int helper = i;
//           City cityFrom = cvrp.getCities().stream()
//                   .filter(c -> c.getId() == route.get(helper))
//                   .collect(Collectors.toList())
//                   .get(0);
//           City cityTo = cvrp.getCities().stream()
//                   .filter(c -> c.getId() == route.get(helper+1))
//                   .collect(Collectors.toList())
//                   .get(0);
//           distance += cityFrom.distanceTo(cityTo);
//        }
//        System.out.println("FITNESS__" + distance + "__");
//        return distance;
//    }
}
