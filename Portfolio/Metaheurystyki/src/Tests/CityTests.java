package Tests;

import Problem.City;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CityTests {

    @Test
    void euc2dTest(){
        City c1 = new City();
        City c2 = new City();
        c1.setxCoord(38);
        c1.setyCoord(46);
        c2.setxCoord(59);
        c2.setyCoord(46);

        assertEquals(21.0, c1.distanceTo(c2));
    }
}
