package models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
class RecyclingTest {


    @DisplayName("TestingConstructor_ForAlphaBetaGamma_WithValidInputs")
    @ParameterizedTest
    @MethodSource("recyclingData")
    void testRecyclingConstructorWithValidInputs(Recycling recycling, List<Double> expectedRates,Location expectedLocation,int yearsActive,
                                                 String expectedGeneration) {
        String actualGeneration = recycling.getGeneration();
        List<Double> actualRate = recycling.getRates();
        Location actualLocation = recycling.getLocation();
        int actualYearsActive = recycling.getYearsActive();
        assertEquals(expectedGeneration, actualGeneration);
        assertEquals(actualRate, expectedRates);
        assertEquals(expectedLocation, actualLocation);
        assertEquals(yearsActive, actualYearsActive);
    }

    private static Stream<Arguments> recyclingData(){
        return Stream.of(Arguments.of(new Alpha(Location.A,1),List.of(1.0,1.0,1.0), Location.A, 1, "Alpha" ),
                Arguments.of(new Beta(Location.B,2),List.of(1.5,1.5,1.5), Location.B, 2, "Beta" ),
                Arguments.of(new Gamma(Location.C,3),List.of(1.5,2.0,3.0), Location.C, 3, "Gamma" ));
    }








}