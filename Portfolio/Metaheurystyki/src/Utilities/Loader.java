package Utilities;

import Problem.CVRP;
import Problem.City;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Loader {

    private final int METADATA_SIZE = 6;
    private final String COORD_SECTION = "NODE_COORD_SECTION";
    private final String DEMAND_SECTION = "DEMAND_SECTION";
    private final String DEPOT_SECTION = "DEPOT_SECTION";


    private String filePath;
    private CVRP cvrpInstance;


    public Loader(String filePath, CVRP cvrpInstance) {
        this.filePath = filePath;
        this.cvrpInstance = cvrpInstance;
    }

    public void loadFromFile(){
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            String[] metadata = new String[METADATA_SIZE];
            String currentLine;

            for(int i=0; i < METADATA_SIZE; i++){
                metadata[i] = bufferedReader.readLine().split("\\s:\\s")[1];
            }
            loadMetadata(metadata);
            bufferedReader.readLine();
            while (!(currentLine = bufferedReader.readLine()).contains(DEMAND_SECTION)){
                cvrpInstance.getCities().add(loadCity(currentLine.trim()));
            }
            while (!(currentLine = bufferedReader.readLine()).contains(DEPOT_SECTION)){
                loadDemand(currentLine);
            }
            cvrpInstance.setDepotID(Integer.parseInt(bufferedReader.readLine()));
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadMetadata(String[] metadata){
        cvrpInstance.setName(metadata[0]);
        cvrpInstance.setComment(metadata[1]);
        cvrpInstance.setType(metadata[2]);
        cvrpInstance.setDimension(Integer.parseInt(metadata[3]));
        cvrpInstance.setEdgeType(metadata[4]);
        cvrpInstance.setCapacity(Integer.parseInt(metadata[5]));
    }

    private City loadCity(String cityData){
        String[] splittedData = cityData.split("\\s");
        return new City(splittedData);
    }

    private void loadDemand(String demandData){
        String[] splittedData = demandData.split("\\s");
        cvrpInstance.getCities().get(Integer.parseInt(splittedData[0]) - 1).setDemand(Integer.parseInt(splittedData[1]));
    }
}
