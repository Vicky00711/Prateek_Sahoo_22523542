# Technical Memo

This technical memo documents the Command Line Interface (CLI) that has been developed for the Landfill Labs Worker Service Prototype. This CLI has been developed so that the system can be demonstrated/used by the council, allowing them to provide some initial feedback to the development team before the full Worker Service (in the form of a SpringBatch application) is developed. As the lead of the testing team on this project, you're expected to test this prototype version of the system before we give it to the council. To assist with this, we have put together the following use cases for the CLI (you should consider these alongside the Spec_v1 document, the correction to the Spec_v1 document issued below, and the Worked Example detailed as a part of the Memorandum of Understanding).

In general, the CLI should fail gracefully (i.e., not display uncaught exceptions to the user) and display text that is free of spelling mistakes (the examples below have been checked and approved by our marketing team). Additionally, where numeric values are reported, these should be displayed to a maximum of 2 decimal places.

## Use Cases

| Use Case ID | Use Case Name | Use Case Description | Preconditions | Postconditions |
|-------------|---------------|----------------------|---------------|----------------|
| UC001       | Start-Up Options  | On starting the application, the user is presented with 3 options: Configure/Run Scenario; About; Exit. | The CLI has started. | The start-up options text is displayed (see Appendix A: Initial Options Text). |
| UC002       | View About | The user is able to view some about text by selecting the appropriate option having started the CLI.  | The CLI has started. | The about text is displayed to the user (see Appendix B: About Text), followed by the start-up options text (see Appendix A: Initial Options Text). |
| UC003       | Configure/Run Scenario | The user can select a Configure/Run Scenario option to begin configuring and running a scenario. | The CLI has started. | The scenario configuration options are displayed to the user (see Appendix C: Scenario Options Text). |
| UC004       | Enter a Historic Site | The user can enter the details of a historic site, including: A location for the historic site (A, B, or C); And an initial waste quantity. | The CLI has started and the user has selected the Configure/Run Scenario > Add Historic option. | Having created a historic site, the user is presented with the historic site created text (see Appendix D: Historic Site Created Text), followed by the scenario configuration options (see Appendix C: Scenario Options Text). |
| UC005       | Enter Recycling Centres | The user can enter the details of multiple recycling centres. | The CLI has started and the user has selected the Configure/Run Scenario > Add Recycling Centres option. | Having created any number of Recycling Centres the user is presented with recycling centres created text (see Appendix E: Recycling Centres Created Text), followed by the scenario configuration options (see Appendix C: Scenario Options Text). |
| UC006       | Run a Scenario | The user can run valid scenarios. | The CLI has started and the user has configured a valid scenario (i.e., at-least one historic site and at-least one recycling centre). | Having selected the Run Scenario option a user is presented with the results (see Appendix F: Scenario Completion Text), followed by the start-up options text (see Appendix A: Initial Options Text). |
| UC007       | Exit | The user can exit the application. | The CLI has started and the user is not in the middle of configuring a scenario. | Having selected the Exit option the userr is presented with the exit text (see Appendix G: Exit Text). |

## Corrections to Spec_v1

With regards to section 1.c.iii (FindViableCentres), the following correction applies (i.e., `/paper` was a mistake):

```diff
- "if metallic/paper waste is present, all centres within 3 hours (inclusive) are considered viable..."
+ "if metallic waste is present, all centres within 3 hours (inclusive) are considered viable..."
```

## Appendices

### Appendix A: Initial Options Text

```
Options (enter number to select):
1. Configure/Run Scenario.
2. About.
3. Exit.
```

### Appendix B: About Text

```
About:
- This is a prototype of the Landfill Labs Worker Service.
- Use it to configure and run a single waste management scenario.
- To run multiple scenarios, you should run the application multiple times.
- If you're confused, you may wish to consult the application specification or contact Four Walls Software.
```

### Appendix C: Scenario Options Text

```
Configuring scenario (enter number to select):
1. Add Historic.
2. Add Recycling Centres.
3. Run Scenario.
```

### Appendix D: Historic Site Created Text

Note that the values `A` and `5000.00` are examples.

```
Historic site created with: location = A; initialWaste = 5000.00.
```

### Appendix E: Recycling Centres Created Text

Note that the values `2`, `A`, `B`, `12`, `10`, `Alpha` and `Beta` are all examples.

```
2 recycling centres created.
Locations: [A, B]
YearsActive: [12, 10]
Types: [Alpha, Beta]
```

### Appendix F: Scenario Completion Text

```
Running scenario...

Scenario successfully completed. Results:

Time to fill recycling centre: 250.00 hours; Time to process the waste after delivery: 5000.00 hours.

The total duration is therefore: 5250.00 hours.
---------------------------------------------
```

### Appendix G: Exit Text

```
----------------------------------------
End
----------------------------------------
```
