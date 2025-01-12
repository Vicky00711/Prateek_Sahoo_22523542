import models.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    static Historic historic1;
    static Historic historic2;
    static Historic historic3;
    static Historic historic4;
    static Historic historic5;
    static List<Recycling> list1;
    static List<Recycling> list2;
    static List<Recycling> list3;
    static List<Recycling> list4;
    static List<Recycling> list5;
    static List<Recycling> list6;
    static List<Recycling> list7;


    @BeforeAll
    static void setUp() {
        list1 = new ArrayList<>(List.of(new Alpha(Location.A, 1), new Beta(Location.A, 1))); // Same location
        list2 = new ArrayList<>(List.of(new Alpha(Location.B, 2), new Alpha(Location.C, 2)));// Same generation
        list4 = new ArrayList<>(List.of(new Beta(Location.B, 1), new Gamma(Location.C, 5))); //All different
        list5 = new ArrayList<>(List.of(new Beta(Location.B, 1), new Beta(Location.B, 5))); //Same Location and generation, different years
        list6 = new ArrayList<>(List.of(new Alpha(Location.A, 1), new Beta(Location.B, 5), new Gamma(Location.C,5)));
        list7 = new ArrayList<>(List.of(new Alpha(Location.A, 1), new Gamma(Location.B, 5), new Gamma(Location.C,5)));
        historic1 = new Historic(Location.A, 1251); //metallic threshold above 1250
        historic2 = new Historic(Location.B, 1250);// metallic threshold exactly on 1250
        historic3 = new Historic(Location.C, 1000);// metallic threshold below 1250
        historic4 = new Historic(Location.A, 5000);
        historic5 = new Historic(Location.A, 1000);// metallic threshold below 1250
        list3 = new ArrayList<>(List.of(new Beta(Location.C, 5), new Gamma(Location.B, 5))); //Same Years


    }
    private static RecyclingProperties extractProperties(List<Recycling> recyclingList) {
        List<String> generations = new ArrayList<>();
        List<Location> locations = new ArrayList<>();
        List<Integer> yearsActive = new ArrayList<>();

        for (Recycling properties : recyclingList) {
            generations.add(properties.getGeneration());
            locations.add(properties.getLocation());
            yearsActive.add(properties.getYearsActive());
        }

        return new RecyclingProperties(generations, locations, yearsActive);
    }

    private static class RecyclingProperties {
        List<String> generations;
        List<Location> locations;
        List<Integer> yearsActive;

        RecyclingProperties(List<String> generations, List<Location> locations, List<Integer> yearsActive) {
            this.generations = generations;
            this.locations = locations;
            this.yearsActive = yearsActive;
        }
    }

    @ParameterizedTest
    @MethodSource("testDataWithValidData")
    void findViableCentres(Historic historic, List<Recycling> list, List<Recycling> expectedList) {
        List<Recycling> actualList = Utils.findViableCentres(historic, list);

        if (expectedList.isEmpty()) {
            assertTrue(actualList.isEmpty(), "Expected no viable centers, but found some.");
        } else {
            RecyclingProperties actualProperties = extractProperties(actualList);
            RecyclingProperties expectedProperties = extractProperties(expectedList);
            assertAll(
                    () -> assertEquals(actualList.size(), expectedList.size(), "Size of expected list and actual list don't match."),
                    () -> assertTrue(expectedProperties.generations.containsAll(actualProperties.generations) && actualProperties.generations.containsAll(expectedProperties.generations), "List of actual and expected generations don't match."),
                    () -> assertTrue(expectedProperties.locations.containsAll(actualProperties.locations) && (actualProperties.locations.containsAll(expectedProperties.locations)), "List of actual and expected locations don't match."),
                    () -> assertTrue(expectedProperties.yearsActive.containsAll(actualProperties.yearsActive) && (actualProperties.yearsActive.containsAll(expectedProperties.yearsActive)), "List of actual and expected years active don't match.")
            );
        }
    }

    static Stream<Arguments> testDataWithValidData() {
        return Stream.of(
                Arguments.of(historic1, list1, new ArrayList<>(List.of(new Alpha(Location.A, 1), new Beta(Location.A, 1)))),
                Arguments.of(historic1, list2, new ArrayList<>(List.of(new Alpha(Location.B, 2)))),
                Arguments.of(historic3, list3, new ArrayList<>(List.of(new Beta(Location.C, 5)))),
                Arguments.of(historic2, list3, new ArrayList<>(List.of(new Beta(Location.C, 5)))),
                Arguments.of(historic5, new ArrayList<>(List.of(new Beta(Location.C, 5), new Gamma(Location.C,5))),new ArrayList<>())


        );
    }



    @ParameterizedTest
    @MethodSource("findOptimalCentreData")
    void findOptimalCentre(Historic historic, List<Recycling> candidateCentres, Recycling expectedOptimalCentre) {
        Recycling actualOptimalCentre= Utils.findOptimalCentre(historic, candidateCentres);



        assertAll(
                ()->assertEquals(expectedOptimalCentre.getGeneration(),actualOptimalCentre.getGeneration()," Actual and expected optimal generations don't match."),
                ()->assertEquals(expectedOptimalCentre.getLocation(),actualOptimalCentre.getLocation()," Actual and expected locations don't match."),
                ()->assertEquals(expectedOptimalCentre.getYearsActive(),actualOptimalCentre.getYearsActive()," Actual and expected generations don't match.")

        );
    }

    static Stream<Arguments> findOptimalCentreData() {
        return Stream.of(
                Arguments.of(historic1, list1, new Beta(Location.A, 1)),
                Arguments.of(historic1, list2, new Alpha(Location.B, 2)),
                Arguments.of(historic1, list3, new Gamma(Location.B, 5)),
                Arguments.of(historic1, list5, new Beta(Location.B, 1)),
                Arguments.of(historic3, list3,new Beta(Location.C, 5)),
                Arguments.of(historic3, list4, new Beta(Location.B, 1))



        );
    }


    @ParameterizedTest
    @MethodSource("findNearestCentres")
    void findNearestCentres(Historic historic, List<Recycling> candidateCentres, List<Recycling> expectedNearestGenerations) {
        List<Recycling> actualNearestList= Utils.findViableCentres(historic,candidateCentres);


        RecyclingProperties actualProperties = extractProperties(actualNearestList);
        RecyclingProperties expectedProperties = extractProperties(expectedNearestGenerations);
        assertAll(
                ()->assertTrue(expectedProperties.locations.containsAll(actualProperties.locations) && (actualProperties.locations.containsAll(expectedProperties.locations)),"List of actual and expected locations don't match.")

        );
    }
    static Stream<Arguments> findNearestCentres() {
        return Stream.of(
                Arguments.of(historic2, list4,  new ArrayList<>(List.of( new Beta(Location.B, 1)))),
                Arguments.of(historic1, list4,  new ArrayList<>(List.of( new Beta(Location.B, 1)))),
                Arguments.of(historic1, list6,  new ArrayList<>(List.of( new Alpha(Location.A, 1), new Beta(Location.B,5), new Gamma(Location.B, 5))))




        );
    }

    @ParameterizedTest
    @MethodSource("findHighestGenerationData")
    void testFindHighestGenerations(List<Recycling> candidateCentre, List<Recycling> expectedHighestGenerationList ) {
        List<Recycling> actualHighestGenerationList=Utils.findHighestGenerations(candidateCentre);
        RecyclingProperties actualProperties = extractProperties(actualHighestGenerationList);
        RecyclingProperties expectedProperties = extractProperties(expectedHighestGenerationList);
        assertAll(
                ()->assertEquals(expectedHighestGenerationList.size(), actualHighestGenerationList.size(), "The actual and expected list size don't match"),

                ()->assertTrue(expectedProperties.generations.containsAll(actualProperties.generations) && actualProperties.generations.containsAll(expectedProperties.generations), "Actual and expected highest generation list don't match.")

        );
    }
    static Stream<Arguments> findHighestGenerationData() {
        return Stream.of(

                Arguments.of(list6 , new ArrayList<>(List.of(new Gamma(Location.B, 5) ))),
                Arguments.of(list7 , new ArrayList<>(List.of( new Gamma(Location.B, 5), new Gamma(Location.C,5)))),
                Arguments.of(list5 , new ArrayList<>(List.of(new Beta(Location.B, 1), new Beta(Location.B, 5))))





        );
    }

    @ParameterizedTest
    @CsvSource(value={"Gamma, Alpha",
            "Beta,Alpha"
    })
    void compareGenerationsFirstTest(String gen1, String gen2 ) {
        assertTrue(Utils.compareGenerations(gen1, gen2)>0);
        assertTrue(Utils.compareGenerations(gen2, gen1)<0);
    }
    @ParameterizedTest
    @CsvSource(value={"Gamma, Gamma",

    })
    void compareGenerationsSecondTest(String gen1, String gen2 ) {
        assertTrue(Utils.compareGenerations(gen1, gen2)==0);

    }

    @ParameterizedTest
    @MethodSource("findLeastYearsActiveData")
    void findLeastYearsActive(List<Recycling> candidateCentre, List<Recycling> expectedLeastYearsActiveList) {
        List<Recycling> actualLeastYearsActiveList=Utils.findLeastYearsActive(candidateCentre);
        RecyclingProperties actualProperties = extractProperties(actualLeastYearsActiveList);
        RecyclingProperties expectedProperties = extractProperties(expectedLeastYearsActiveList);


        assertTrue(expectedProperties.yearsActive.containsAll(actualProperties.yearsActive) && actualProperties.yearsActive.containsAll(expectedProperties.yearsActive), "Actual and expected least years active  list don't match.");

    }

    static Stream<Arguments> findLeastYearsActiveData() {
        return Stream.of(

                Arguments.of(list6 , new ArrayList<>(List.of(new Alpha(Location.A,1) ))),
                Arguments.of(list3 , new ArrayList<>(List.of(new Beta(Location.C, 5), new Gamma(Location.B, 5)))),
                Arguments.of(list4 , new ArrayList<>(List.of(new Beta(Location.B, 1))))




        );
    }


    @ParameterizedTest
    @MethodSource("calculateTravelDurationData")

    void calculateTravelDuration(Historic historic, Recycling recyclingCentre, double expectedTravelDuration) {
        assertTimeout(Duration.ofSeconds(2), () -> {
            double actualTravelDuration = Utils.calculateTravelDuration(historic, recyclingCentre);
            assertEquals(expectedTravelDuration, actualTravelDuration);
        });
    }
    static Stream<Arguments> calculateTravelDurationData() {
        return Stream.of(

                Arguments.of(historic4,new Alpha(Location.A,5) , 250 ),
                Arguments.of(historic4,new Alpha(Location.C,5) , -1),
                Arguments.of(historic3,new Gamma(Location.C,5) , -1),
                Arguments.of(new Historic(Location.B, 19),new Gamma(Location.C,5) , -1),
                Arguments.of(new Historic(Location.A, 25),new Alpha(Location.B,5) , 50)







        );
    }


    @Test
    void test1CalculateProcessDuration() {
        // Arrange
        Historic historic = new Historic(Location.A, 1251);



        Recycling recyclingCentre = new Alpha(Location.A, 10);

        double actualDuration = Utils.calculateProcessDuration(historic, recyclingCentre);

        double expectedDuration = (historic.getPlasticGlass() + historic.getPaper() + historic.getMetallic()); // (plastic + paper + metallic) / rate
        assertEquals(expectedDuration, actualDuration, "The calculated process duration should match the expected value.");
    }
    @Test
    void test2CalculateProcessDuration() {
        // Arrange
        Historic historic = new Historic(Location.A, 1250);



        Recycling recyclingCentre = new Beta(Location.B, 10);

        double actualDuration = Utils.calculateProcessDuration(historic, recyclingCentre);

        double expectedDuration = (historic.getPlasticGlass()/1.5) + (historic.getPaper()/1.5) + historic.getMetallic()/1.5; // (plastic + paper + metallic) / rate
        assertEquals(expectedDuration, actualDuration,0.01, "The calculated process duration should match the expected value.");
    }



    @ParameterizedTest
    @MethodSource("invalidFindViableCentresData")
    void findViableCentresWithEmptyRecyclingList(Historic historic, List<Recycling> candidateCentres) {
        assertTrue(Utils.findViableCentres(historic, candidateCentres).isEmpty());
    }

    static Stream<Arguments> invalidFindViableCentresData() {
        return Stream.of(
                Arguments.of((List<Recycling>) null), // Null candidate centres


                Arguments.of(historic1, new ArrayList<>()) // Empty list
        );
    }



    @ParameterizedTest
    @MethodSource("invalidFindNearestCentresData")
    void findNearestCentresWithEmptyRecyclingList(Historic historic, List<Recycling> candidateCentres) {
        assertTrue(Utils.findNearestCentres(historic, candidateCentres).isEmpty());
    }

    static Stream<Arguments> invalidFindNearestCentresData() {
        return Stream.of(
                Arguments.of((List<Recycling>) null), // Null candidate centres


                Arguments.of(historic1, new ArrayList<>()) // Empty list
        );
    }

    @ParameterizedTest
    @MethodSource("invalidFindHighestGenerationsData")
    void testFindHighestGenerationsWithEmptyRecyclingList(List<Recycling> candidateCentre) {
        assertTrue(Utils.findHighestGenerations(candidateCentre).isEmpty());
    }

    static Stream<Arguments> invalidFindHighestGenerationsData() {
        return Stream.of(
                Arguments.of((List<Recycling>) null), // Null candidate centres

                Arguments.of(new ArrayList<>()) // Empty list
        );
    }

    @ParameterizedTest
    @MethodSource("invalidFindLeastYearsActiveData")
    void findLeastYearsActiveWithEmptyRecyclingList(List<Recycling> candidateCentre) {
        assertTrue(Utils.findLeastYearsActive(candidateCentre).isEmpty());
    }

    static Stream<Arguments> invalidFindLeastYearsActiveData() {
        return Stream.of(
                Arguments.of((List<Recycling>) null), // Null candidate centres
                Arguments.of(new ArrayList<>()) // Empty list
        );
    }




}