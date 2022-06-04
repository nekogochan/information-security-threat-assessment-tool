package ru.sstu.ifbs.repository;

import io.jmix.core.FetchPlan;
import org.springframework.stereotype.Component;
import ru.sstu.ifbs.entity.storage.ImpactSource;
import ru.sstu.ifbs.entity.storage.ImpactTarget;
import ru.sstu.ifbs.entity.storage.ThreatImplMethod;

import java.util.List;

@Component(ThreatImplMethodRepository.NAME)
public class ThreatImplMethodRepository extends DefaultRepository<ThreatImplMethod> {
    public static final String NAME = "gwf_ThreatImplMethodRepository";

    @Override
    protected Class<ThreatImplMethod> entityClass() {
        return ThreatImplMethod.class;
    }

    public List<ThreatImplMethod> getBySourcesAndTargets(List<ImpactSource> sources, List<ImpactTarget> targets, FetchPlan fetchPlan) {
        return load().query("""
                            select distinct im
                              from gwf_ThreatImplMethodLink imLink
                              join imLink.implMethods im
                             where imLink.source in :sources
                               and imLink.targets in :targets
                            """)
                .parameter("sources", sources)
                .parameter("targets", targets)
                .fetchPlan(fetchPlan)
                .list();
    }
}
