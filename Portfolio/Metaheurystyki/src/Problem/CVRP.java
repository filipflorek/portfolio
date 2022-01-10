package Problem;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CVRP {

    private static CVRP cvrpInstance;
    private String name;
    private String comment;
    private String type;
    private int dimension;
    private String edgeType;
    private int capacity;
    private int depotID;
    private double[][] distanceMatrix;
    private List<City> cities = new ArrayList<>();
    ArrayList<Integer> feasibleRoute = new ArrayList<>();


    private CVRP(){
        if(cvrpInstance != null){
            throw new IllegalStateException("Cannot create another instance of a singleton!");
        }
    }

    public static CVRP getInstance(){
        if(cvrpInstance == null){
            cvrpInstance = new CVRP();
        }
        return cvrpInstance;
    }

    public void initializeDistanceMatrix(){
        distanceMatrix = new double[dimension][dimension];
        for(int i=0; i < distanceMatrix.length; i++){
            for(int j=0; j< distanceMatrix.length; j++){
                if(i == j) distanceMatrix[i][j] = 0.0;
                else distanceMatrix[i][j] = cities.get(i).distanceTo(cities.get(j));
            }
        }
    }

    public void calculateFitness(Individual individual){
        prepareFeasibleRoute(individual);
        individual.setFitness(calculateDistance(feasibleRoute));
    }
    
    public double calculateDistance(ArrayList<Integer> route){
        double distance = 0.0;
        for (int i=0; i < route.size() - 1; i++){
            int currentCityIndex = cities.get(route.get(i) - 1).getId() - 1;
            int nextCityIndex = cities.get(route.get(i+1) - 1).getId() - 1;
            distance += distanceMatrix[currentCityIndex][nextCityIndex];
        }
        feasibleRoute.clear();
        return Math.round(distance * 100.0) / 100.0;
    }

    private void prepareFeasibleRoute(Individual individual){
        int trucks = 1;
        int capacityLeft = capacity - cities.get(individual.getGenotype()[0] - 1).getDemand();
        feasibleRoute.add(depotID);
        feasibleRoute.add(individual.getGenotype()[0]);
        for (int i=0; i<individual.getGenotype().length - 1; i++){
            int nextCityIndex = individual.getGenotype()[i+1] - 1;
            capacityLeft -= cities.get(nextCityIndex).getDemand();
            if (capacityLeft > 0){
                feasibleRoute.add(nextCityIndex + 1);
            }
            if(capacityLeft == 0){
                trucks++;
                capacityLeft = capacity;
                feasibleRoute.add(nextCityIndex + 1);
                feasibleRoute.add(depotID);
            }
            if(capacityLeft < 0 ){
                i--;
                trucks ++;
                capacityLeft = capacity;
                feasibleRoute.add(depotID);
            }
        }
        individual.setTrucks(trucks);
        feasibleRoute.add(depotID);
    }

    public City getCityByID(int cityID){
        return cities.stream()
                .filter(c -> c.getId() == cityID)
                .collect(Collectors.toList())
                .get(0);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public String getEdgeType() {
        return edgeType;
    }

    public void setEdgeType(String edgeType) {
        this.edgeType = edgeType;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double[][] getDistanceMatrix() {
        return distanceMatrix;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public int getDepotID() {
        return depotID;
    }

    public void setDepotID(int depotID) {
        this.depotID = depotID;
    }

    @Override
    public String toString() {
        return "CVRP{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", dimension=" + dimension +
                ", edgeType='" + edgeType + '\'' +
                ", capacity=" + capacity +
                ", depotID=" + depotID +
                '}';
    }

    public void printCities(){
        for (City city : cities) {
            System.out.println(city.toString());
        }
    }

    public void printDistanceMatrix(){
        for(int i=0; i < distanceMatrix.length; i++){
            for(int j=0; j < distanceMatrix.length; j++){
                System.out.print(distanceMatrix[i][j] + " | ");
            }
            System.out.println();
        }
    }
}
