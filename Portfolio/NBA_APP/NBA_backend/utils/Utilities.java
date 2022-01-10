package com.florek.NBA_backend.utils;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.regex.Pattern;

public class Utilities {


    public static final String PUBLICIST = "Publicist";
    public static final String USER = "User";
    public static final String ADMINISTRATOR = "Administrator";



    public static final String URL = "https://www.balldontlie.io/api/v1/";
    public static final String PLAYERS_ENDPOINT = "players";
    public static final String TEAMS_ENDPOINT = "teams";
    public static final String GAMES_ENDPOINT = "games";
    public static final String SINGLE_GAME_STATS_ENDPOINT = "stats";
    public static final String SEASON_STATS_ENDPOINT = "season_averages";
    public static final String PAGE_PARAMETER = "?page=";
    public static final String SEASON_PARAMETER = "?season=";
    public static final String PLAYER_PARAMETER = "&player_ids[]=";
    public static final int RESPONSE_CODE_TIMEOUT = 429;
    public static final long NEXT_REQUEST_DELAY_TIME = 60 * 1000;

    public static final String propsPath = "D:\\PWR\\INZYNIERKA\\NBA_APP\\NBA_backend\\NBA_backend\\src\\main\\resources\\update.properties";

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    public static String prepareSearchString(String searchPhrase){
        return "%" + searchPhrase + "%";
    }

    public static String prepareEndpointWithPageParameter(String endpoint, String pageNumber){
        return endpoint + PAGE_PARAMETER + pageNumber;
    }

    public static String prepareEndpointWithSeasonParameter(String endpoint, String season, String pageNumber){
        return endpoint + "?seasons[]=" + season + "&page=" + pageNumber;
    }


    public static String prepareSeasonPlayerEndpoint(String endpoint, int season, int[] range){
        String ids = prepareIDsString(range);
        return endpoint + SEASON_PARAMETER + season + PLAYER_PARAMETER + ids;
    }

    public static String prepareIDsString(int[] range){
        StringBuilder ids = new StringBuilder();
        for(int i=range[0]; i<=range[1]; i++){
            ids.append(Integer.toString(i));
            ids.append(",");
        }
        return ids.toString().substring(0, ids.toString().length() - 1);
    }

    public static Properties loadPropertiesFile(){
        try (InputStream input = new FileInputStream(propsPath)) {

            Properties prop = new Properties();
            prop.load(input);
            return prop;

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void writeToPropertiesFile(String key, int value){
        try (OutputStream output = new FileOutputStream(propsPath, true)) {

            Properties prop = new Properties();
            prop.setProperty(key, String.valueOf(value));

            prop.store(output, null);

            System.out.println("Aktualizacja pliku update.props: " + prop);

        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public static JSONObject getEndpointResponseJSON(String endpoint) throws IOException {
        StringBuilder inline = new StringBuilder();
        JSONObject responseJson = null;

        URL url = new URL(URL + endpoint);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Language", "en-US");
        conn.setUseCaches(true);
        conn.setDoOutput(true);
        conn.setDoInput(true);

        try {
            conn.connect();

            int responsecode = conn.getResponseCode();
            System.out.println("Response code is: " + responsecode);


            if (responsecode != 200) {
                if(responsecode == RESPONSE_CODE_TIMEOUT){
                    System.out.println("Going to sleep - waiting for API to be available" );
                    Thread.currentThread().sleep(NEXT_REQUEST_DELAY_TIME);
                    System.out.println("Waking up - executing query" );
                    return getEndpointResponseJSON(endpoint);
                }else
                    throw new RuntimeException("HttpResponseCode: " + responsecode);
            }
            else {

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    inline.append(inputLine);
                }

                responseJson = new JSONObject(inline.toString());
                System.out.println("\nJSON Response in String format");
                System.out.println(responseJson.toString());

                in.close();

            }

        }catch (IOException | InterruptedException ex){
            ex.printStackTrace();
        }

        return responseJson;
    }
}
