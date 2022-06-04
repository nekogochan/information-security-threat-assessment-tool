package ru.sstu.ifbs.repository;

import io.jmix.core.FetchPlan;
import io.jmix.core.FetchPlans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sstu.ifbs.entity.storage.ImpactSource;
import ru.sstu.ifbs.entity.storage.ImpactTarget;
import ru.sstu.ifbs.entity.storage.Threat;
import ru.sstu.ifbs.entity.storage.ThreatImplMethod;
import ru.sstu.ifbs.entity.storage.scenario.ThreatScenario;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Component(ThreatScenarioRepository.NAME)
public class ThreatScenarioRepository extends DefaultRepository<ThreatScenario> {

    public static final String NAME = "gwf_ThreatScenarioRepository";
    @Autowired
    private FetchPlans fetchPlans;

    @Override
    protected Class<ThreatScenario> entityClass() {
        return ThreatScenario.class;
    }

    public Map<Threat, List<ThreatScenario>> getByThreatsSourcesAndTargets(
            List<Threat> threats, List<ImpactSource> sources,
            List<ImpactTarget> targets, FetchPlan scenarioFetchPlan) {

        if (!scenarioFetchPlan.containsProperty("threat")) {
            scenarioFetchPlan = fetchPlans.builder(scenarioFetchPlan)
                    .add("threat", it -> it.add("id"))
                    .build();
        }

        return load().query("""
                            select distinct scen
                              from gwf_ThreatScenario scen
                              join scen.implMethods imLink
                              join imLink.implMethods im
                             where imLink.source in :sources
                               and imLink.target in :targets
                               and scen.threat in :threats
                            """)
                .parameters(Map.of(
                        "threats", threats,
                        "sources", sources,
                        "targets", targets
                ))
                .fetchPlan(scenarioFetchPlan)
                .list()
                .stream()
                .collect(groupingBy(ThreatScenario::getThreat));
    }
}
