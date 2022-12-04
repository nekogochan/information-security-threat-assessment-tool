package ru.sstu.ifbs.repository;

import io.jmix.core.FetchPlan;
import org.springframework.stereotype.Component;
import ru.sstu.ifbs.entity.project.Project;
import ru.sstu.ifbs.entity.project.securityinfo.common.SecurityClass;
import ru.sstu.ifbs.entity.storage.measures.SecurityMeasure;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component(SecurityMeasureRepository.NAME)
public class SecurityMeasureRepository extends DefaultRepository<SecurityMeasure> {
    public static final String NAME = "gwf_SecurityMeasureRepository";

    @Override
    protected Class<SecurityMeasure> entityClass() {
        return SecurityMeasure.class;
    }

    public List<SecurityMeasure> getBySecurityClass(SecurityClass securityClass,
                                                    FetchPlan fetchPlan) {
        return load().all()
                .fetchPlan(fetchPlan)
                .list()
                .stream()
                .filter(it -> {
                    var pl = it.getSecurityClass();
                    return pl != null && pl.compareTo(securityClass) >= 0;
                })
                .collect(toList());
    }

    public List<SecurityMeasure> getByProject(Project project, FetchPlan fetchPlan) {
        return load().query(
                        """
                                select distinct val
                                  from gwf_Project p
                                  join p.actualMeasures am
                                  join am.value val
                                 where p = :project
                                """)
                .parameter("project", project)
                .fetchPlan(fetchPlan)
                .list();
    }
}
