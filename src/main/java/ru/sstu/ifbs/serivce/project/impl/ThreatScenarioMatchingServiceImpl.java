package ru.sstu.ifbs.serivce.project.impl;

import io.jmix.core.FetchPlan;
import org.springframework.stereotype.Component;
import ru.sstu.ifbs.entity.project.Project;
import ru.sstu.ifbs.entity.storage.Threat;
import ru.sstu.ifbs.entity.storage.scenario.ThreatScenario;
import ru.sstu.ifbs.serivce.project.ThreatMatchingService;
import ru.sstu.ifbs.serivce.project.ThreatScenarioMatchingService;

import java.util.List;
import java.util.Map;

@Component(ThreatScenarioMatchingService.NAME)
public class ThreatScenarioMatchingServiceImpl implements ThreatScenarioMatchingService {

    @Override
    public Map<Threat, ThreatScenario> getMatches(Project project, List<Threat> threatList, FetchPlan scenarioFetchPlan) {
        return null;
    }
}
