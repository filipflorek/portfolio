package Utilities;

import Algorithm.*;
import Problem.CVRP;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Controller {
    static String pathSlash;
    static String inputPathRoot;
    static String dataFilePath;
    public static void main(String[] argst) {
        String[] args = new String[3];
        args[0] = "A-n60-k9";
        args[1] = "ea.properties";
        args[2] = "windows";
        chooseOS(args[2]);
        Properties props = new Properties();
        loadProps(args[1], props);
        CVRP cvrp = CVRP.getInstance();
        chooseFile(args[0]);
        Loader loader = new Loader(dataFilePath, cvrp);
        loader.loadFromFile();
        cvrp.initializeDistanceMatrix();
        String propsString = preparePropsString(props);



        switch(props.getProperty("heuristic")){
            case "random": {
                Logger logger = new Logger(cvrp);
                logger.writePropsInfo("random-summary-" + args[0] + ".txt", propsString);
                RandomAlgorithm randomAlgorithm = new RandomAlgorithm(logger,
                        Integer.parseInt(props.getProperty("iterations")));
                randomAlgorithm.solveProblem(cvrp);
                logger.writeGenerationInfoToFile("random-generation-" + args[0] + ".txt", propsString);
                logger.writeSummaryInfoToFile("random-summary-" + args[0] + ".txt");
                break;
            }
            case "greedy": {
                Logger logger = new Logger(cvrp);
                logger.writePropsInfo("greedy-summary-" + args[0] + ".txt", propsString);
                GreedyAlgorithm greedyAlgorithm = new GreedyAlgorithm(logger,
                        Double.parseDouble(props.getProperty("capacityWeight")),
                        Double.parseDouble(props.getProperty("distanceWeight")));
                greedyAlgorithm.solveProblem(cvrp);
                //greedyAlgorithm.printSolutions();
                //logger.writeGenerationInfoToFile("greedy-generation-" + args[0] + ".txt", propsString);
                logger.writeSummaryInfoToFile("greedy-summary-" + args[0] + ".txt");
                break;
            }
            case "greedy-research": {
                double capacityWeight = 0.0;
                double distanceWeight = 1.0;
                while(capacityWeight<=1.0){
                    Logger logger = new Logger(cvrp);
                    logger.writePropsInfo("greedy-summary-" + args[0] + ".txt", "CW: " + capacityWeight + ", DW: " + distanceWeight);
                    GreedyAlgorithm greedyAlgorithm = new GreedyAlgorithm(logger,
                            capacityWeight,
                            distanceWeight);
                    greedyAlgorithm.solveProblem(cvrp);
                    //greedyAlgorithm.printSolutions();
                    //logger.writeGenerationInfoToFile("greedy-generation-" + args[0] + ".txt", propsString);
                    logger.writeSummaryInfoToFile("greedy-summary-" + args[0] + ".txt");
                    capacityWeight += 0.01;
                    distanceWeight -= 0.01;
                }
                break;
            }
            case "ea": {
                for(int i=0; i<1; i++){
                    Logger logger = new Logger(cvrp);
                    if(i==0) logger.writePropsInfo("ea-summary-" + args[0] + ".txt", propsString);
                    EvolutionaryAlgorithm ea = new EvolutionaryAlgorithm(
                            Integer.parseInt(props.getProperty("populationSize")),
                            Integer.parseInt(props.getProperty("iterations")),
                            Double.parseDouble(props.getProperty("crossProb")),
                            Double.parseDouble(props.getProperty("mutProb")),
                            Integer.parseInt(props.getProperty("selectionMethod")),
                            Integer.parseInt(props.getProperty("tournamentSize")),
                            Integer.parseInt(props.getProperty("initMethod")),
                            Integer.parseInt(props.getProperty("mutOperator")),
                            Integer.parseInt(props.getProperty("crossOperator")),
                            Double.parseDouble(props.getProperty("swapMutationProb")),
                            Double.parseDouble(props.getProperty("inversionMutationProb")),
                            Double.parseDouble(props.getProperty("mutationWeight")),
                            Integer.parseInt(props.getProperty("mixedMutation")),
                            logger);
                    ea.solveProblem(cvrp);
                    logger.writeGenerationInfoToFile("ea-generation-" + args[0] + ".txt", propsString);
                    logger.writeSummaryInfoToFile("ea-summary-" + args[0] + ".txt");
                }
                break;
            }
            case "ts": {
                for(int i=0; i<1; i++){
                    Logger logger = new Logger(cvrp);
                    if(i==0) logger.writePropsInfo("ts-summary-" + args[0] + ".txt", propsString);
                    TS tabuSearch = new TS(
                            Integer.parseInt(props.getProperty("iterations")),
                            Integer.parseInt(props.getProperty("nSize")),
                            0,
                            Integer.parseInt(props.getProperty("nMethod")),
                            logger,
                            Integer.parseInt(props.getProperty("tabuSize")));
                    tabuSearch.solveProblem(cvrp);
                    logger.writeGenerationInfoToFile("ts-generation-" + args[0] + ".txt", propsString);
                    logger.writeSummaryInfoToFile("ts-summary-" + args[0] + ".txt");
                }
                break;
            }
            case "sa": {
                for(int i=0; i<1; i++){
                    Logger logger = new Logger(cvrp);
                    if(i==0) logger.writePropsInfo("sa-summary-" + args[0] + ".txt", propsString);
                    SA simulatedAnnealing = new SA(
                            Integer.parseInt(props.getProperty("iterations")),
                            Integer.parseInt(props.getProperty("nSize")),
                            0,
                            Integer.parseInt(props.getProperty("nMethod")),
                            logger,
                            0,
                            Integer.parseInt(props.getProperty("initTemp")),
                            Double.parseDouble(props.getProperty("alpha")));
                    simulatedAnnealing.solveProblem(cvrp);
                    logger.writeGenerationInfoToFile("sa-generation-" + args[0] + ".txt", propsString);
                    logger.writeSummaryInfoToFile("sa-summary-" + args[0] + ".txt");
                }
                break;
            }
        }
//        randomAlgorithm.solveProblem(cvrp);
//        logger.saveGeneration(randomAlgorithm.getGeneration());
//        GreedyAlgorithm greedyAlgorithm = new GreedyAlgorithm(logger);
//        greedyAlgorithm.solveProblem(cvrp);
//        greedyAlgorithm.printSolutions();
//        logger.writeGenerationInfoToFile("A-n32-k5-greedy-generation.txt");
//        logger.writeSummaryInfoToFile("A-n32-k5-greedy-summary.txt");

//        for(int i=0; i<10; i++){
//            Logger logger = new Logger(cvrp);
//            EvolutionaryAlgorithm ea = new EvolutionaryAlgorithm(200, 500, 0.7, 0.01, 0, 5, 0,0,1,logger);
//            ea.solveProblem(cvrp);
//            logger.writeGenerationInfoToFile("A-n32-k5-ea-generation.txt");
//            logger.writeSummaryInfoToFile("summary.txt");
//        }

//        for(int i=0; i<10; i++){
//            Logger logger = new Logger(cvrp);
//            //TabuSearch tabuSearch = new TabuSearch(2000, 10, 100, 0, 0, logger);
//            TS tabuSearch = new TS(2000, 10, 0, 0, logger, 100);
//            tabuSearch.solveProblem(cvrp);
//            logger.writeGenerationInfoToFile("tabu-gen.txt");
//            logger.writeSummaryInfoToFile("tabu.txt");
//        }

//        TabuSearch tabuSearch = new TabuSearch(2000, 2, 20, 0, 0, logger);
//        tabuSearch.solveProblem(cvrp);
//        logger.writeGenerationInfoToFile("tabu-gen.txt");
//        logger.writeSummaryInfoToFile("tabu.txt");

//        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(500, 5, 0, 20000, 0, 0.99, 2, 0, logger);
//        simulatedAnnealing.solveProblem(cvrp);
//        logger.writeGenerationInfoToFile("sa-gen.txt");
//        logger.writeSummaryInfoToFile("sa.txt");

//        for(int i=0; i<10; i++){
//            Logger logger = new Logger(cvrp);
//            SA simulatedAnnealing = new SA(2000, 100, 0, 0, logger, 0, 20000, 0.99);
//            //SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(500, 10, 0, 20000, 0, 0.99, 0, 0, logger);
//            simulatedAnnealing.solveProblem(cvrp);
//            logger.writeSummaryInfoToFile("sa.txt");
//        }
    }

    private static void chooseOS(String OS){
        if(OS.equals("windows")){
            pathSlash = "\\";
            inputPathRoot = "res\\input\\";
        }else{
            if(OS.equals("linux")){
                pathSlash = "/";
                inputPathRoot = "res/input/";
            }
        }
    }

    private static void loadProps(String fileName, Properties props){
        try (InputStream input = new FileInputStream(inputPathRoot + "props" + Controller.pathSlash + fileName)){
            props.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String preparePropsString(Properties props){
        StringBuffer propsString = new StringBuffer();
        props.forEach((key, value) -> propsString.append(key + ": " + value + ", "));
        return propsString.toString();
    }

    private static void chooseFile(String fileName){
        switch(fileName){
            case "A-n32-k5": {
                dataFilePath = inputPathRoot + "A-n32-k5.txt";
                break;
            }
            case "A-n37-k6": {
                dataFilePath = inputPathRoot + "A-n37-k6.txt";
                break;
            }
            case "A-n39-k5": {
                dataFilePath = inputPathRoot + "A-n39-k5.txt";
                break;
            }
            case "A-n45-k6": {
                dataFilePath = inputPathRoot + "A-n45-k6.txt";
                break;
            }
            case "A-n48-k7": {
                dataFilePath = inputPathRoot + "A-n48-k7.txt";
                break;
            }
            case "A-n54-k7": {
                dataFilePath = inputPathRoot + "A-n54-k7.txt";
                break;
            }
            case "A-n60-k9": {
                dataFilePath = inputPathRoot + "A-n60-k9.txt";
                break;
            }
            default: {
                dataFilePath = inputPathRoot + "A-n32-k5.txt";
            }
        }
    }
}
