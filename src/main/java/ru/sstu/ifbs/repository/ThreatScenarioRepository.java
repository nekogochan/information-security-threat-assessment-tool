package ru.sstu.ifbs.repository;

import io.jmix.core.FetchPlan;
import io.jmix.core.FetchPlans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sstu.ifbs.entity.DefaultEntity;
import ru.sstu.ifbs.entity.storage.ImpactSource;
import ru.sstu.ifbs.entity.storage.ImpactTarget;
import ru.sstu.ifbs.entity.storage.Threat;
import ru.sstu.ifbs.entity.storage.ThreatImplMethod;
import ru.sstu.ifbs.entity.storage.scenario.ThreatScenario;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

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

        var implMethods = dataManager.load(ThreatImplMethod.class)
                .query("""
                               select distinct im
                                 from gwf_ThreatImplMethodsLink imLink
                                 join imLink.implMethods im
                                where imLink.source in :sources
                                  and imLink.targets in :targets
                               """)
                .parameters(Map.of(
                        "sources", sources,
                        "targets", targets))
                .list();

        var scenarios = load().query("""
                                             select distinct scen
                                               from gwf_ThreatScenario scen
                                               join scen.sources src
                                              where src in :sources
                                                and scen.implMethod in :implMethods
                                                and scen.threat in :threats
                                             """)
                .parameters(Map.of(
                        "sources", sources,
                        "implMethods", implMethods,
                        "threats", threats))
                .fetchPlan(scenarioFetchPlan)
                .list();

        return scenarios.stream()
                .collect(groupingBy(ThreatScenario::getThreat));
    }

    public List<ThreatScenario> getByThreat(Threat threat, FetchPlan fetchPlan) {
        return load().query("where e.threat = :threat")
                .parameter("threat", threat)
                .fetchPlan(fetchPlan)
                .list();
    }

    public Collection<ThreatScenario> reloadAll(Collection<ThreatScenario> scenarios, FetchPlan fetchPlan) {
        var ids = scenarios.stream().map(DefaultEntity::getId).collect(toList());
        return getByIds(ids, fetchPlan);
    }
}
