package ru.sstu.ifbs.serivce.project.impl;

import io.jmix.core.FetchPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sstu.ifbs.entity.DefaultEntity;
import ru.sstu.ifbs.entity.project.ActualThreat;
import ru.sstu.ifbs.entity.project.Project;
import ru.sstu.ifbs.entity.storage.Threat;
import ru.sstu.ifbs.entity.storage.scenario.ThreatScenario;
import ru.sstu.ifbs.repository.ThreatScenarioRepository;
import ru.sstu.ifbs.serivce.project.ThreatScenarioMatchingService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Component(ThreatScenarioMatchingService.NAME)
public class ThreatScenarioMatchingServiceImpl implements ThreatScenarioMatchingService {

    @Autowired
    private ThreatScenarioRepository threatScenarioRepository;

    @Override
    public Map<Threat, List<ThreatScenario>> getMatches(Project project, FetchPlan scenarioFetchPlan) {
        var projectThreats = project.getActualThreats().stream().map(ActualThreat::getThreat)
                .collect(toMap(
                        DefaultEntity::getId,
                        it -> it
                ));

        var scenarios = threatScenarioRepository.getByThreatsSourcesAndTargets(
                new ArrayList<>(projectThreats.values()),
                project.getImpactSources(),
                project.getImpactTargets(),
                scenarioFetchPlan);

        return scenarios.entrySet().stream()
                .collect(toMap(
                        it -> projectThreats.get(it.getKey().getId()),
                        Map.Entry::getValue
                ));
    }
}
