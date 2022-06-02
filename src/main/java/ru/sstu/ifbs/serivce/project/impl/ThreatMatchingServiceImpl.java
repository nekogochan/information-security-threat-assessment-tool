package ru.sstu.ifbs.serivce.project.impl;

import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sstu.ifbs.entity.project.ActualThreat;
import ru.sstu.ifbs.entity.project.Project;
import ru.sstu.ifbs.entity.storage.ImpactSource;
import ru.sstu.ifbs.entity.storage.ImpactTarget;
import ru.sstu.ifbs.repository.ThreatRepository;
import ru.sstu.ifbs.serivce.project.ThreatMatchingService;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Component(ThreatMatchingService.NAME)
public class ThreatMatchingServiceImpl implements ThreatMatchingService {

    @Autowired
    private ThreatRepository threatRepository;
    @Autowired
    private DataManager dataManager;

    @Override
    public List<ActualThreat> getMatches(Project project, FetchPlan threatFetchPlan, FetchPlan scenarioFetchPlan) {
        return threatRepository.getByImpactSourcesAndTargets(project.getImpactSources(),
                                                             project.getImpactTargets(),
                                                             threatFetchPlan)
                .stream()
                .map(it -> {
                    var at = dataManager.create(ActualThreat.class);
                    at.setProject(project);
                    at.setThreat(it);
                    return at;
                })
                .collect(toList());
    }
}
