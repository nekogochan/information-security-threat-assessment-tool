package ru.sstu.ifbs.serivce.project;

import io.jmix.core.FetchPlan;
import ru.sstu.ifbs.entity.project.ActualThreat;
import ru.sstu.ifbs.entity.project.Project;
import ru.sstu.ifbs.entity.storage.ImpactSource;
import ru.sstu.ifbs.entity.storage.ImpactTarget;

import java.util.Collection;
import java.util.List;

public interface ThreatMatchingService {
    String NAME = "gwf_ThreatMatchingService";

    List<ActualThreat> getMatches(Project project, FetchPlan fetchPlan);
}
