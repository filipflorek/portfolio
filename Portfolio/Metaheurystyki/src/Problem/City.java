package Problem;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class City {

    private int id;
    private double xCoord;
    private double yCoord;
    private int demand;

    public City(int id, double xCoord, double yCoord, int demand) {
        this.id = id;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.demand = demand;
    }

    public City(String[] splittedData) {
        this.id = Integer.parseInt(splittedData[0]);
        this.xCoord = Double.parseDouble(splittedData[1]);
        this.yCoord = Double.parseDouble(splittedData[2]);
    }

    public City() {
    }

    public double distanceTo(City to) {
        BigDecimal bd = new BigDecimal(Double.toString(Math.sqrt(Math.pow(this.xCoord - to.getxCoord(),2) + Math.pow(this.yCoord - to.getyCoord(),2))));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getxCoord() {
        return xCoord;
    }

    public void setxCoord(double xCoord) {
        this.xCoord = xCoord;
    }

    public double getyCoord() {
        return yCoord;
    }

    public void setyCoord(double yCoord) {
        this.yCoord = yCoord;
    }

    public int getDemand() {
        return demand;
    }

    public void setDemand(int demand) {
        this.demand = demand;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", xCoord=" + xCoord +
                ", yCoord=" + yCoord +
                ", demand=" + demand +
                '}';
    }

}
