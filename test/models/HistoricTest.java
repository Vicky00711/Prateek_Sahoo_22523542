package models;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class HistoricTest {

    static Historic historicA;
    static Historic historicB;
    static Historic historicC;

    @ParameterizedTest
    @CsvFileSource(resources = "/historicTestResources.csv")

    void estimateWasteSplitWithValidInitialWasteAndLocation(double initialWasteData, double expectedInitialWaste, double expectedPaperWaste, double expectedPlasticWaste, double expectedMetallicWaste) {
        historicA=new Historic(Location.A, initialWasteData);
        assertAll(()->assertEquals(expectedInitialWaste, historicA.getRemainingWaste(),0.01),
                ()->assertEquals(expectedPaperWaste, historicA.getPaper(),0.01, "Incorrect paper waste"),
                ()->assertEquals(expectedPlasticWaste, historicA.getPlasticGlass(), 0.01, "Incorrect plastic waste"),
                ()->assertEquals(expectedMetallicWaste, historicA.getMetallic(), 0.01,"Incorrect metallic waste")
        );

    }

    @ParameterizedTest
    @MethodSource("estimateWasteSplitEdgeCasesData")

    void estimateWasteSplitWithEdgeCases(Location location,double initialWasteData, double expectedPaperWaste, double expectedPlasticWaste, double expectedMetallicWaste) {
        historicA=new Historic(location, initialWasteData);
        assertAll(
                ()->assertEquals(expectedPaperWaste, historicA.getPaper(),0.01, "Incorrect paper waste"),
                ()->assertEquals(expectedPlasticWaste, historicA.getPlasticGlass(), 0.01, "Incorrect plastic waste"),
                ()->assertEquals(expectedMetallicWaste, historicA.getMetallic(), 0.01,"Incorrect metallic waste")
        );

    }
    private static Stream<Arguments> estimateWasteSplitEdgeCasesData() {
        return Stream.of(
                Arguments.of(Location.A, 0, 0, 0, 0),
                Arguments.of(Location.B, Double.MAX_VALUE, Double.MAX_VALUE*0.5, Double.MAX_VALUE*0.3, Double.MAX_VALUE*0.2)


        );
    }


    @ParameterizedTest
    @MethodSource("invalidDataForTestConstructor")
    void testHistoricConstructorWithInvalidInputs(Location location, double initialWaste) {
        assertThrows(IllegalArgumentException.class,()->new Historic(location,initialWaste),"Invalid data entered.Please enter a valid historic location (A,B or C) and an initial waste.");
    }

    private static Stream<Arguments> invalidDataForTestConstructor() {
        return Stream.of(
                Arguments.of(null, 1000),
                Arguments.of(null, -1000),
                Arguments.of(Location.A, -1000)


        );
    }

    @ParameterizedTest
    @CsvFileSource(resources="/initialWasteDataForHistoric.csv")
    void setRemainingWasteWithValidData(double initialWaste,double remainingWaste, double expectedWasteData) {
        historicB=new Historic(Location.A, initialWaste);
        historicB.setRemainingWaste(remainingWaste);

        double actualWasteData= historicB.getRemainingWaste();
        assertEquals(actualWasteData, expectedWasteData, "Setter is not updating the value properly.");
    }



    @ParameterizedTest
    @CsvSource(value={"5000,240,1000, 1000",
            "2000,240,1000,1000"})
    void setMetallicWasteWithValidData(double initialWaste, double initialMetallicWaste,double updatedMetallicWaste, double expectedMetallicWasteData) {
        historicB=new Historic(Location.A, initialWaste);
        historicB.setMetallic(initialMetallicWaste);
        historicB.setMetallic(updatedMetallicWaste);

        double actualMetallicWasteData= historicB.getMetallic();
        assertEquals(actualMetallicWasteData, expectedMetallicWasteData, "Setter is not updating the value properly.");

    }


    @ParameterizedTest
    @CsvSource(value={"5000,240,1000, 1000",
            "2000,240,1000,1000"})
    void setPaperWasteWithValidData(double initialWaste, double initialPaperWaste,double updatedPaperWaste, double expectedPaperWasteData) {
        historicB=new Historic(Location.A, initialWaste);
        historicB.setPaper(initialPaperWaste);
        historicB.setPaper(updatedPaperWaste);

        double actualPaperWasteData= historicB.getPaper();
        assertEquals(actualPaperWasteData, expectedPaperWasteData, "Setter is not updating the value properly.");

    }



    @ParameterizedTest
    @CsvSource(value={"5000,240,1000, 1000",
            "2000,240,1000,1000"})
    void setPlasticGlassWithValidData(double initialWaste, double initialPlasticGlassWaste,double updatedPlasticGlassWaste, double expectedPlasticGlassWasteData) {
        historicC=new Historic(Location.A, initialWaste);
        historicC.setPlasticGlass(initialPlasticGlassWaste);
        historicC.setPlasticGlass(updatedPlasticGlassWaste);

        double actualPlasticGlassWasteData= historicC.getPlasticGlass();
        assertEquals(actualPlasticGlassWasteData, expectedPlasticGlassWasteData, "Setter is not updating the value properly.");

    }





























}