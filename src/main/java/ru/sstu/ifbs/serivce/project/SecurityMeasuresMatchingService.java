package ru.sstu.ifbs.serivce.project;

import io.jmix.core.FetchPlan;
import ru.sstu.ifbs.entity.project.ActualSecurityMeasure;
import ru.sstu.ifbs.entity.project.Project;
import ru.sstu.ifbs.entity.project.securityinfo.gis.GisSecurityInfo;
import ru.sstu.ifbs.entity.project.securityinfo.ispdn.IspdnSecurityInfo;

import java.util.List;

public interface SecurityMeasuresMatchingService {
    String NAME = "gwf_SecurityMeasuresMatchingService";

    List<ActualSecurityMeasure> getMatches(Project project, GisSecurityInfo secInfo,
                                           FetchPlan securityMeasureFetchPlan);

    List<ActualSecurityMeasure> getMatches(Project project, IspdnSecurityInfo secInfo,
                                           FetchPlan securityMeasureFetchPlan);
}
