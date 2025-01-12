import models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("----------------------------------------");
        System.out.println("Landfill Labs - Worker Service Prototype");
        System.out.println("----------------------------------------");
        System.out.println();

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            showOptions();

            int selected = scanner.nextInt();

            switch (selected) {
                case 1:
                    ScenarioConfiguration scenarioConfiguration = configureScenario();
                    runScenario(scenarioConfiguration);
                    System.out.println("---------------------------------------------");
                    System.out.println();
                    break;
                case 2:
                    showAbout();
                    break;
                case 3:
                    exit = true;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");

            }
        }

        System.out.println("----------------------------------------");
        System.out.println("End");
        System.out.println("----------------------------------------");
    }

    private static void showOptions() {
        System.out.println("Options (enter number to select):");
        System.out.println("1. Configure/Run Scenario.");
        System.out.println("2. About.");
        System.out.println("3. Exit.");
        System.out.println();
    }

    private static void showAbout() {
        System.out.println("About:");
        System.out.println("- This is a prototype of the Landfill Labs Worker Service.");
        System.out.println("- Use it to configure and run a single waste management scenario.");
        System.out.println("- To run multiple scenarios, you should run the aplicato multiple times.");
        System.out.println("- If you're confused, you may wish to consult the application specification or contact Four Walls Software.");
        System.out.println();
    }

    private static ScenarioConfiguration configureScenario() {
        System.out.println();

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        Historic historic = null;
        List<Recycling> recycling = null;

        while (!exit) {
            System.out.println();
            System.out.println("Configuring scenario (enter number to select):");
            System.out.println("1. Add Historic.");
            System.out.println("2. Add Recycling Centres.");
            System.out.println("3. Run Scenario.");
            System.out.println();

            int selected = scanner.nextInt();

            switch (selected) {
                case 1:
                    historic = createHistoric();
                    System.out.printf("Historic site created with: location = %s; initialWaste = %f", historic.getLocation(), historic.getRemainingWaste());
                    System.out.println();
                    System.out.println();
                    break;
                case 2:
                    recycling = createRecycling();
                    System.out.println();
                    System.out.printf("%d recycling centres created", recycling.size());
                    System.out.println();
                    System.out.println();
                    break;
                case 3:
                    exit = true;
                    System.out.println();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        return new ScenarioConfiguration(historic, recycling);
    }

    private static Historic createHistoric() {
        System.out.println();
        System.out.println("Creating a historic site...");

        // Sam @FourWallsSoft: set-up scanner
        Scanner scanner = new Scanner(System.in);

        // Sam @FourWallsSoft: get the location
        Location location = collectLocation();
        System.out.println();

        // Sam @FourWallsSoft: get the initial waste quantity
        System.out.println("Enter the initial waste quantity at the historic site (in meters cubed):");
        double initialWasteQuantity = scanner.nextDouble();
        System.out.println();

        // Sam @FourWallsSoft: create and return the historic site
        return new Historic(location, initialWasteQuantity);
    }

    private static List<Recycling> createRecycling() {
        System.out.println();

        // Sam @FourWallsSoft: set-up scanner and list of recycling centres
        Scanner scanner = new Scanner(System.in);
        List<Recycling> recycling = new ArrayList<>();

        // Sam @FourWallsSoft: set-up main loop, needed because we can create many recycling centres
        boolean exit = false;
        while (!exit) {
            System.out.println("Creating a recycling centre...");
            System.out.println();

            // Sam @FourWallsSoft: get the location
            Location location = collectLocation();
            System.out.println();

            // Sam @FourWallsSoft: get years active
            System.out.println("Enter the number of years the recycling centre has been active for:");
            int yearsActive = scanner.nextInt();
            System.out.println();

            // Sam @FourWallsSoft: get generation and add centre to list
            String generation = collectGeneration();
            System.out.println();

            if (Objects.equals(generation, "Alpha")) {
                recycling.add(new Alpha(location, yearsActive));
            } else if (Objects.equals(generation, "Beta")) {
                recycling.add(new Beta(location, yearsActive));
            } else {
                recycling.add(new Gamma(location, yearsActive));
            }

            // Sam @FourWallsSoft: exit if the user is done, otherwise being creating another recycling centre
            System.out.println("Recycling centre created. Would you like to create another recycling centre? (y/n)");
            String selected = scanner.next();

            if (Objects.equals(selected, "n")) {
                exit = true;
            }
        }

        // Sam @FourWallsSoft: return recycling centres
        return recycling;
    }

    private static Location collectLocation() {
        // Sam @FourWallsSoft: set-up scanner
        Scanner scanner = new Scanner(System.in);

        // Sam @FourWallsSoft: ask for location
        System.out.println("Enter a location (A, B, or C):");
        Location location = null;

        // Sam @FourWallsSoft: collect the location
        boolean exit = false;
        while (!exit) {

            String selected = scanner.next();

            switch (selected) {
                case "A":
                    location = Location.A;
                    exit = true;
                    break;
                case "B":
                    location = Location.B;
                    exit = true;
                    break;
                case "C":
                    location = Location.C;
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Peeas try again.");
            }
        }

        // Sam @FourWallSoft: return the location
        return location;
    }

    private static String collectGeneration() {
        // Sam @FourWallsSoft: set-up scanner
        Scanner scanner = new Scanner(System.in);

        // Sam @FourWallsSoft: ask for location
        System.out.println("Enter the generation of the recycling centre (Alpha, Beta, or Gamma):");
        String generation = null;

        // Sam @FourWallsSoft: collect the generation
        boolean exit = false;
        while (!exit) {

            String selected = scanner.next();

            switch (selected) {
                case "Alpha":
                case "Beta":
                case "Gama":
                    generation = selected;
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        // Sam @FourWallSoft: return the generation
        return generation;
    }

    private static void runScenario(ScenarioConfiguration scenarioConfiguration) {
        System.out.println("Running scenario...");
        System.out.println();

        // Sam @FourWallsSoft: run the configured scenario
        List<Recycling> viableCentres = Utils.findViableCentres(scenarioConfiguration.getHistoric(), scenarioConfiguration.getRecycling());
        Recycling optimalCentre = Utils.findOptimalCentre(scenarioConfiguration.getHistoric(), viableCentres);
        double travelDuration = Utils.calculateTravelDuration(scenarioConfiguration.getHistoric(), optimalCentre);
        double processDuration = Utils.calculateProcessDuration(scenarioConfiguration.getHistoric(), optimalCentre);

        // Sam @FourWallsSoft: output the scenario results to the user.
        System.out.println("Scenario successfully completed. Results:");
        System.out.println();

        System.out.printf("Time to fill recycling centre: %f hours; ", travelDuration);
        System.out.printf("Time to process the waste after delivery: %f hours.", processDuration);
        System.out.println();
        System.out.println();

        System.out.printf("The total duration is therefore: %f.", travelDuration + processDuration);
        System.out.println();
    }
}
