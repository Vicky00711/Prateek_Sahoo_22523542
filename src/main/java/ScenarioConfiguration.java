import models.Historic;
import models.Recycling;

import java.util.ArrayList;
import java.util.List;

public class ScenarioConfiguration {

    private Historic historic;
    private List<Recycling> recycling;

    public ScenarioConfiguration() {
        this.recycling = new ArrayList<>();
    }

    public ScenarioConfiguration(Historic historic, List<Recycling> recycling) {
        this.historic = historic;
        this.recycling = recycling;
    }

    public Historic getHistoric() {
        return this.historic;
    }

    public void setHistoric(Historic historic) {
        this.historic = historic;
    }

    public List<Recycling> getRecycling() {
        return this.recycling;
    }

    public void addRecycling(Recycling additionalRecycling) {
        this.recycling.add(additionalRecycling);
    }
}
