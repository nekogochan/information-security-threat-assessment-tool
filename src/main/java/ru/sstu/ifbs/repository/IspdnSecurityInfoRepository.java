package ru.sstu.ifbs.repository;

import io.jmix.core.FetchPlan;
import org.springframework.stereotype.Component;
import ru.sstu.ifbs.entity.project.Project;
import ru.sstu.ifbs.entity.project.securityinfo.ispdn.IspdnSecurityInfo;

@Component(IspdnSecurityInfoRepository.NAME)
public class IspdnSecurityInfoRepository extends DefaultRepository<IspdnSecurityInfo> {

    public static final String NAME = "gwf_IspdnSecurityInfoRepository";

    public IspdnSecurityInfo getByProject(Project project, FetchPlan fetchPlan) {
        return load()
                .query("where e.project = :project")
                .parameter("project", project)
                .fetchPlan(fetchPlan)
                .one();
    }

    @Override
    protected Class<IspdnSecurityInfo> entityClass() {
        return IspdnSecurityInfo.class;
    }
}
