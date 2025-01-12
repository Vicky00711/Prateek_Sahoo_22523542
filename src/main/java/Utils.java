import models.Historic;
import models.Recycling;
import models.Transport;

import java.util.*;
import java.util.stream.Collectors;

// Sam @FourWallsSoft: I've made all methods on this public to make testing easier. Hope this helps!
public final class Utils {

    private static final double TRANSPORT_CAPACITY = 20d;

    // Sam @FourWallsSoft: Finds viable recycling centres.
    public static List<Recycling> findViableCentres(Historic historic, List<Recycling> candidateCentres) {

        // Sam @FourWallsSoft: First, check for metallic waste (if not present, only alpha and beta centres are viable)
        if (historic.getMetallic() <= 0) {
            candidateCentres.removeIf(c -> c.getGeneration().equals("Gamma"));
        }

        // Sam @FourWallsSoft: Second, create candidateTransports to determine journey times.
        // Sam @FourWallsSoft: Note: Having to create candidateTransports is feeling a bit clunky, but should work. TODO: Refactor at next opportunity.
        List<Recycling> toRemove = new ArrayList<Recycling>();

        for (Recycling candidateCentre: candidateCentres) {
            Transport candidateTransport = new Transport(historic.getLocation(), candidateCentre.getLocation());
            double travelTime = candidateTransport.getTravelTime();
            if (travelTime > 3) {
                toRemove.add(candidateCentre);
            }
        }

        candidateCentres.removeAll(toRemove);

        // Sam @FourWallsSoft: By this point, remaining candidates should be viable.
        return candidateCentres;
    }

    // Sam @FourWallsSoft: Finds the optimal centre.
    public static Recycling findOptimalCentre(Historic historic, List<Recycling> candidateCentres) {
        List<Recycling> nearestCentres = findNearestCentres(historic, candidateCentres);
        List<Recycling> youngestCentres = findLeastYearsActive(nearestCentres);
        List<Recycling> highestGenerations = findHighestGenerations(youngestCentres);

        return highestGenerations.getFirst();
    }

    public static List<Recycling> findNearestCentres(Historic historic, List<Recycling> candidateCentres) {
        // Sam @FourWallsSoft: Create a list of travel times
        List<Double> travelTimes = candidateCentres.stream().map(c -> {
            Transport candidateTransport = new Transport(historic.getLocation(), c.getLocation());
            return candidateTransport.getTravelTime();
        }).toList();

        // Sam @FourWallsSoft: Find the minimum travel time
        double minTravelTime = Collections.min(travelTimes);

        // Sam @FourWallsSoft: Filter the candidate centers with the minimum travel time
        return candidateCentres.stream().filter(c -> {
            Transport candidateTransport = new Transport(historic.getLocation(), c.getLocation());
            return candidateTransport.getTravelTime() == minTravelTime;
        }).toList();
    }

    public static List<Recycling> findHighestGenerations(List<Recycling> candidateCentres) {
        if (candidateCentres == null || candidateCentres.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> generations = Arrays.asList("Alpha", "Beta", "Gamma");
        String highestGeneration = "Alpha"; // Start with the lowest generation

        // Find the highest generation
        for (Recycling centre : candidateCentres) {
            if (compareGenerations(centre.getGeneration(), highestGeneration) > 0) {
                highestGeneration = centre.getGeneration();
            }
        }

        final String finalHighestGeneration = highestGeneration;

        // Collect all centres with the highest generation
        return candidateCentres.stream()
                .filter(centre -> centre.getGeneration().equalsIgnoreCase(finalHighestGeneration))
                .collect(Collectors.toList());
    }

    // Sam @FourWallsSoft: Should return value greater than 0, when gen1 is greater than gen2
    public static int compareGenerations(String gen1, String gen2) {
        List<String> generations = Arrays.asList("Alpha", "Beta", "Gamma");
        return Integer.compare(generations.indexOf(gen1), generations.indexOf(gen2));
    }

    public static List<Recycling> findLeastYearsActive(List<Recycling> candidateCentres) {
        if (candidateCentres == null || candidateCentres.isEmpty()) {
            return Collections.emptyList();
        }

        // Find the minimum number of years active
        int minYearsActive = candidateCentres.stream()
                .mapToInt(Recycling::getYearsActive)
                .min()
                .orElse(Integer.MAX_VALUE);

        // Filter the centres with the minimum number of years active
        return candidateCentres.stream()
                .filter(centre -> centre.getYearsActive() == minYearsActive)
                .collect(Collectors.toList());
    }

    public static double calculateTravelDuration(Historic historic, Recycling recyclingCentre) {
        // Sam @FourWallsSoft: Handle invalid input.
        if (historic.getRemainingWaste() < TRANSPORT_CAPACITY) {
            return -1.0;
        }

        // Sam @FourWallsSoft: Model/simulate transport journeys.
        List<Transport> transportsUsed = new ArrayList<>();

        while (historic.getRemainingWaste() > 0) {
            Transport transport = new Transport(historic.getLocation(), recyclingCentre.getLocation());

            while (transport.getTotalWaste() < TRANSPORT_CAPACITY) {
                if (historic.getMetallic() > 0) {
                    double fillAmount = Math.min(historic.getMetallic(), TRANSPORT_CAPACITY);
                    transport.setMetallicWaste(fillAmount);
                } else if (historic.getPlasticGlass() > 0) {
                    double fillAmount = Math.min(historic.getPlasticGlass(), TRANSPORT_CAPACITY);
                    transport.setPlasticGlassWaste(fillAmount);
                } else if (historic.getPaper() > 0){
                    double fillAmount = Math.min(historic.getPaper(), TRANSPORT_CAPACITY);
                    transport.setPaperWaste(fillAmount);
                }
            }

            transportsUsed.add(transport);

            historic.setRemainingWaste(historic.getRemainingWaste() - transport.getTotalWaste());
        }

        // Sam @FourWallsSoft: Calculate travel time.
        double travelTime = 0.0;
        for (Transport transport : transportsUsed) {
            travelTime += transport.getTravelTime();
        }

        // Sam @FourWallsSoft: Return travel time.
        return travelTime;
    }

    public static double calculateProcessDuration(Historic historic, Recycling recycling) {
        double timeToProcessPlastic = historic.getPlasticGlass() / recycling.getRates().get(0);
        double timeToProcessPaper = historic.getPaper() / recycling.getRates().get(1);
        double timeToProcessMetallic = historic.getMetallic() / recycling.getRates().get(2);

        return timeToProcessPlastic + timeToProcessPaper + timeToProcessMetallic;
    }
}

