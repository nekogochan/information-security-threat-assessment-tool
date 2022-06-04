package ru.sstu.ifbs.serivce.project;

import io.jmix.core.FetchPlan;
import ru.sstu.ifbs.entity.project.Project;
import ru.sstu.ifbs.entity.storage.Threat;
import ru.sstu.ifbs.entity.storage.scenario.ThreatScenario;

import java.util.List;
import java.util.Map;

public interface ThreatScenarioMatchingService {
    String NAME = "gwf_ThreatScenarioMatchingService";

    Map<Threat, List<ThreatScenario>> getMatches(Project project, FetchPlan scenarioFetchPlan);
}
