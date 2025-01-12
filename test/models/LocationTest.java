package models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {


    @ParameterizedTest
    @MethodSource("travelTimeData")
    void travelTime(double actualTime, double expectedTime) {
        assertEquals(expectedTime, actualTime,"The expected time and the actual time do not match.");
    }
    static Stream<Arguments> travelTimeData(){
        return Stream.of(Arguments.of(Location.A.travelTime(Location.B), 2),
                Arguments.of(Location.A.travelTime(Location.A), 1),
                Arguments.of(Location.A.travelTime(Location.C), 4),
                Arguments.of(Location.B.travelTime(Location.A), 2),
                Arguments.of(Location.B.travelTime(Location.B), 1),
                Arguments.of(Location.B.travelTime(Location.C), 3),
                Arguments.of(Location.C.travelTime(Location.A), 4),
                Arguments.of(Location.C.travelTime(Location.B), 3),
                Arguments.of(Location.C.travelTime(Location.C), 1)

        );
    }

    @ParameterizedTest
    @MethodSource("invalidTestTravelTimeData")
    void testTravelTimeInvalid(Location from, Location to) {
        assertThrows(NullPointerException.class,()->from.travelTime(to));
    }
    static Stream<Arguments> invalidTestTravelTimeData(){
        return Stream.of(Arguments.of(Location.A, null),
                Arguments.of(null, null),
                Arguments.of(null, Location.A)
        );
    }

    @ParameterizedTest
    @MethodSource("abDataWithValidInputs")
    void testAbWithValidData(Location from, Location to) {;
        assertTrue(Location.ab(from, to) && Location.ab(to, from));

    }
    static Stream<Arguments> abDataWithValidInputs(){
        return Stream.of(Arguments.of(Location.A, Location.B)


        );
    }




    @ParameterizedTest
    @MethodSource("acDataWithValidInputs")
    void testAcWithValidInputs(Location from, Location to) {
        assertTrue(Location.ac(from,to) && Location.ac(to, from));
    }
    static Stream<Arguments> acDataWithValidInputs(){
        return Stream.of(Arguments.of(Location.A, Location.C)


        );
    }




    @ParameterizedTest
    @MethodSource("bcDataWithValidInputs")
    void testBcWithValidInputs(Location from, Location to) {
        assertTrue(Location.bc(from,to) && Location.bc(to,from));
    }
    static Stream<Arguments> bcDataWithValidInputs(){
        return Stream.of(Arguments.of(Location.B, Location.C)


        );
    }


    @Test
    void testListOfEnumABC(){
        Location[] location= Location.values();
        Location[] location2= new Location[]{Location.A, Location.B, Location.C};
        assertArrayEquals(location2, location);
    }


}