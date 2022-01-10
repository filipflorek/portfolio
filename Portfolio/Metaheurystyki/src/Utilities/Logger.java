package Utilities;

import Problem.CVRP;
import Problem.Individual;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Logger {
    private double globalBest = Double.MAX_VALUE;
    private double bestTabu = 0.0;
    private double globalWorst = 0.0;
    private double globalAverage = 0.0;
    private double standardDeviation = 0.0;
    private ArrayList<Double> allAverages = new ArrayList<>();
    private int bestTrucks = 0;
    Individual currentBest;
    double currentWorst;
    double currentAverage;
    private CVRP cvrp;
    private String PATH_ROOT = "res" + Controller.pathSlash + "output" + Controller.pathSlash;
    private List<String> results = new ArrayList<>();
    private String fileID = UUID.randomUUID().toString();

    public Logger(CVRP cvrp) {
        this.cvrp = cvrp;
    }

    public Logger(){

    }

    public void saveGeneration(List<Individual> generation){
        currentBest = getBest(generation);
        currentWorst = getWorst(generation);
        currentAverage = getAverage(generation);
        updateBestOfAllTime(currentBest);
        updateWorstOfAllTime(currentWorst);
        updateGlobalAverage(currentAverage);
        //System.out.println(currentBest.toString());
        results.add(generationInfo());
    }

    public void writeGenerationInfoToFile(String fileName, String propsString){
        try{
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(PATH_ROOT + fileName, true));
            fileWriter.write("PARAMETRY:   " + propsString + "\n");
            for (String result : results) {
                fileWriter.write(result + "\n");
            }
            fileWriter.write(summaryInfo() + "\n");
            fileWriter.write("________________________________________________" + "\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeSummaryInfoToFile(String fileName){
        try{
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(PATH_ROOT + fileName, true));
            //fileWriter.write("PARAMETRY:   " + propsString + "\n");
            fileWriter.write(summaryInfo() + "\n");
            //fileWriter.write("________________________________________________" + "\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writePropsInfo(String fileName, String propsString){
        try{
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(PATH_ROOT + fileName, true));
            fileWriter.write("PARAMETRY:   " + propsString + "\n");
            fileWriter.write("________________________________________________" + "\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveBestAllTimeToFile(){
        try{
            FileWriter fileWriter = new FileWriter(PATH_ROOT + "" + cvrp.getName() + "_best_" + fileID + ".txt");
            fileWriter.write("Fitness: " + globalBest + " Trucks: " + bestTrucks);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Individual getBest(List<Individual> generation){
        return Collections.min(generation);
    }

    private double getWorst(List<Individual> generation){
        return Collections.max(generation).getFitness();
    }

    private double getAverage(List<Individual> generation){
        return generation.stream()
                                .mapToDouble(Individual::getFitness)
                                .average()
                                .getAsDouble();

    }

    private void updateBestOfAllTime(Individual currentBest){
        if(currentBest.getFitness() < globalBest){
            globalBest = currentBest.getFitness();
            bestTrucks = currentBest.getTrucks();
        }
    }

    private void updateWorstOfAllTime(double currentWorst){
        if(currentWorst > globalWorst){
            globalWorst = currentWorst;
        }
    }

    private void updateGlobalAverage(double currentAverage){
        allAverages.add(currentAverage);
        globalAverage += currentAverage;
    }

    private void computeStandardDeviation(){
        double mean = allAverages.stream().mapToDouble(a -> a).average().getAsDouble();
        double sum = 0;

        for (Double avg : allAverages){
            sum += (avg - mean == 0 ? 0 : Math.pow((avg - mean), 2));
        }
        if(sum != 0){
            standardDeviation =  Math.sqrt( sum / ( (double)allAverages.size() - 1 ) );
        }else{
            standardDeviation = 0;
        }

    }

    private String generationInfo(){
        if(bestTabu==0.0){
            return currentBest.getFitness() + ";" + currentWorst + ";" + Math.round(currentAverage * 100.0) / 100.0 + ";";
        }else{
            return bestTabu + ";" + currentBest.getFitness() + ";" + currentWorst + ";" + Math.round(currentAverage * 100.0) / 100.0 + ";";
        }
    }

    private String summaryInfo(){
        double avg = Math.round((globalAverage/(double)allAverages.size()) * 100.0) / 100.0;
        computeStandardDeviation();
        return Math.round(globalBest) + "; " + Math.round(bestTrucks) + "; " + Math.round(globalWorst) + "; " + avg + "; " + Math.round(standardDeviation) + ";";
    }

    public List<String> getResults() {
        return results;
    }

    public void updateBestTabu(double fitness){
        bestTabu = fitness;
    }

    public void setPATH_ROOT(String PATH_ROOT) {
        this.PATH_ROOT = PATH_ROOT;
    }


}
