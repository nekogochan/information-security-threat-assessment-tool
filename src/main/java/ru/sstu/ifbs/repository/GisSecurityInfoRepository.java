package ru.sstu.ifbs.repository;

import io.jmix.core.FetchPlan;
import org.springframework.stereotype.Component;
import ru.sstu.ifbs.entity.project.Project;
import ru.sstu.ifbs.entity.project.securityinfo.gis.GisSecurityInfo;

@Component(GisSecurityInfoRepository.NAME)
public class GisSecurityInfoRepository extends DefaultRepository<GisSecurityInfo> {

    public static final String NAME = "gwf_GisSecurityInfoRepository";

    public GisSecurityInfo getByProject(Project project, FetchPlan fetchPlan) {
        return load()
                .query("where e.project = :project")
                .parameter("project", project)
                .fetchPlan(fetchPlan)
                .one();
    }

    @Override
    protected Class<GisSecurityInfo> entityClass() {
        return GisSecurityInfo.class;
    }
}
