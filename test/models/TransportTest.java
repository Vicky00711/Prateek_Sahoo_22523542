package models;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TransportTest {

    static Transport transportAB;
    static Transport transportBA;
    static Transport transportBC;
    static Transport transportCB;
    static Transport transportAC;
    static Transport transportCA;
    static Transport transportAA;
    static Transport transportBB;
    static Transport transportCC;
    @BeforeAll

    static void beforeEachTestMethod(){
        transportAB=new Transport(Location.A,Location.B);
        transportBA=new Transport(Location.B,Location.A);
        transportBC=new Transport(Location.B,Location.C);
        transportCB=new Transport(Location.C,Location.B);
        transportAC=new Transport(Location.A,Location.C);
        transportCA=new Transport(Location.C,Location.A);
        transportAA=new Transport(Location.A,Location.A);
        transportCC=new Transport(Location.C,Location.C);
        transportBB=new Transport(Location.B,Location.B);
    }

    @ParameterizedTest
    @CsvSource(value={"1000,1000",
            "5000.00,5000.00"})
    void getPaperWasteWithValidInputs(double value, double expectedData) {

        transportAB.setPaperWaste(value);
        assertEquals(expectedData, transportAB.getPaperWaste());

    }

    @ParameterizedTest
    @CsvSource(value={"-100000",
            "-5000"})
    void getPaperWasteWithInValidInputs(double value) {


        assertThrows(Exception.class, ()->{transportAB.setPaperWaste(value);},"Should throw an exception saying invalid paper waste quantity entered.");

    }

    @ParameterizedTest
    @CsvSource(value={"1000,1000",
            "5000.00,5000.00"})
    void getPlasticGlassWasteWithValidInputs(double value, double expectedData) {
        transportAC.setPlasticGlassWaste(value);
        assertEquals(expectedData, transportAC.getPlasticGlassWaste());
    }

    @ParameterizedTest
    @CsvSource(value={"-1000",
            "-5000.00"})
    void getPlasticGlassWasteWithInvalidInputs(double value) {

        assertThrows(Exception.class,()->{ transportAC.setPlasticGlassWaste(value);}, "Should throw an exception saying invalid plastic/glass waste quantity entered. ");
    }

    @ParameterizedTest
    @CsvSource(value={"1000,1000",
            "5000.00,5000.00"})
    void getMetallicWasteWithValidInputs(double value, double expectedData) {
        transportBC.setMetallicWaste(value);
        assertEquals(expectedData, transportBC.getMetallicWaste());
    }

    @ParameterizedTest
    @CsvSource(value={"-1000",
            "-5000.00"})
    void getMetallicWasteWithInvalidInputs(double value) {

        assertThrows(Exception.class, ()->{transportBC.setMetallicWaste(value);}, "Should throw an exception saying invalid metallic waste quantity entered.");
    }

    @ParameterizedTest
    @CsvSource(value={"3000,1000, 1000, 1000",
            "15000.00,5000.00, 5000.00, 5000.00",
            "10000.00,5000.00, 0.00, 5000.00"})
    void getTotalWaste(double expectedResult, double metallicWaste, double plasticGlassWaste, double paperWaste) {
        transportAC.setMetallicWaste(metallicWaste);
        transportAC.setPlasticGlassWaste(plasticGlassWaste);
        transportAC.setPaperWaste(paperWaste);
        double actualTotalWaste= transportAC.getTotalWaste();
        assertEquals(expectedResult, actualTotalWaste);
    }

    @ParameterizedTest
    @MethodSource("travelTimeData")
    void getTravelTime(double actualTravelTime, double expectedTravelTime) {
        assertEquals(expectedTravelTime, actualTravelTime);
    }
    private static Stream<Arguments> travelTimeData(){
        return Stream.of(

                Arguments.of(transportAB.getTravelTime(), 2.0),
                Arguments.of(transportBA.getTravelTime(), 2.0),
                Arguments.of(transportBC.getTravelTime(), 3.0),
                Arguments.of(transportCB.getTravelTime(), 3.0),
                Arguments.of(transportCA.getTravelTime(), 4.0),
                Arguments.of(transportAC.getTravelTime(), 4.0),
                Arguments.of(transportAA.getTravelTime(), 1.0),
                Arguments.of(transportCC.getTravelTime(), 1.0),
                Arguments.of(transportBB.getTravelTime(), 1.0)
        );


    }

}