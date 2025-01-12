import models.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ScenarioConfigurationTest {

    static Historic historic1;
    static Historic historic2;
    static Historic historic3;
    static List<Recycling> list1;
    static List<Recycling> list2;
    static List<Recycling> list3;

    @BeforeAll
    static void setUp() {
        list1 = new ArrayList<>(List.of(new Alpha(Location.A, 1), new Beta(Location.B, 2), new Gamma(Location.C, 3)));
        list2 = new ArrayList<>(List.of(new Alpha(Location.B, 2), new Alpha(Location.C, 2), new Beta(Location.C, 5)));
        list3 = new ArrayList<>(List.of(new Beta(Location.B, 2), new Gamma(Location.C, 5)));
        historic1 = new Historic(Location.A, 5000);
        historic2 = new Historic(Location.B, 1250);
        historic3 = new Historic(Location.C, 1000);
    }

    @Test
    void testDefaultConstructor() {

        ScenarioConfiguration scenario = new ScenarioConfiguration();
        assertAll(()->assertTrue(scenario.getRecycling().isEmpty(), "Recycling list should initially be empty."),
                ()->assertNull(scenario.getHistoric(), "Historic should initially be null."),
                ()->assertEquals(new ArrayList<Recycling>(), scenario.getRecycling())
        )
        ;
    };
    @Test
    void testDefaultConstructorWithNull() {

        ScenarioConfiguration scenario = new ScenarioConfiguration(null, new ArrayList<>());
        assertAll(()->assertTrue(scenario.getRecycling().isEmpty(), "Recycling list should initially be empty."),
                ()->assertNull(scenario.getHistoric(), "Historic should initially be null."),
                ()->assertEquals(new ArrayList<Recycling>(), scenario.getRecycling())
        )
        ;
    };

    @ParameterizedTest
    @MethodSource("testDataWithValidData")
    void testConstructorWithValidData(ScenarioConfiguration scenario, Location expectedLocation, double expectedRemainingWaste, List<Recycling> expectedRecyclingList) {
        assertAll(()->assertNotNull(scenario, "Scenario should not be null"),
                ()->assertEquals(expectedLocation, scenario.getHistoric().getLocation(), "Location mismatch"),
                ()->assertEquals(expectedRemainingWaste, scenario.getHistoric().getRemainingWaste(), "Remaining waste mismatch."),
                ()->assertIterableEquals(expectedRecyclingList, scenario.getRecycling(), "Recycling list mismatch.")
        );
    }

    static Stream<Arguments> testDataWithValidData() {
        return Stream.of(
                Arguments.of(new ScenarioConfiguration(historic1, list1), Location.A, 5000, list1),
                Arguments.of(new ScenarioConfiguration(historic2, list2), Location.B, 1250, list2),
                Arguments.of(new ScenarioConfiguration(historic3, list3), Location.C, 1000, list3)
        );
    }

    @ParameterizedTest
    @MethodSource("validHistoricData")
    void setHistoricWithValidData(ScenarioConfiguration scenario, Historic initialHistoric, Historic updatedHistoric, Historic expectedHistoric) {
        scenario.setHistoric(initialHistoric);
        scenario.setHistoric(updatedHistoric);

        assertEquals(expectedHistoric, scenario.getHistoric(), "Setter not working properly.");
    }

    static Stream<Arguments> validHistoricData() {
        return Stream.of(
                Arguments.of(new ScenarioConfiguration(historic1, list1), historic1, historic2, historic2)
        );
    }



    @ParameterizedTest
    @MethodSource("getRecyclingData")
    void getRecycling(ScenarioConfiguration scenario, List<Recycling> expectedList) {
        assertIterableEquals(expectedList, scenario.getRecycling());
    }

    static Stream<Arguments> getRecyclingData() {
        return Stream.of(
                Arguments.of(new ScenarioConfiguration(historic1, list1), list1)
        );
    }

    @Test
    void testAddRecyclingWithValidData() {
        ScenarioConfiguration scenario = new ScenarioConfiguration(historic1, list1);
        Recycling newRecycling = new Alpha(Location.C, 2);

        scenario.addRecycling(newRecycling);

        assertTrue(scenario.getRecycling().contains(newRecycling), "New recycling was not added.");
    }

    @Test
    void testAddRecyclingWithNull() {
        ScenarioConfiguration scenario = new ScenarioConfiguration(historic1, list1);

        assertThrows(NullPointerException.class, ()->scenario.addRecycling(null), "Should not add a empty recycling centre.");


    }
}
