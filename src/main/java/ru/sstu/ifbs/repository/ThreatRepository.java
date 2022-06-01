package ru.sstu.ifbs.repository;

import io.jmix.core.FetchPlan;
import org.springframework.stereotype.Component;
import ru.sstu.ifbs.entity.storage.ImpactSource;
import ru.sstu.ifbs.entity.storage.ImpactTarget;
import ru.sstu.ifbs.entity.storage.Threat;

import java.util.Collection;
import java.util.List;

@Component(ThreatRepository.NAME)
public class ThreatRepository extends DefaultRepository<Threat> {
    public static final String NAME = "gwf_ThreatRepository";

    @Override
    protected Class<Threat> entityClass() {
        return Threat.class;
    }

    public List<Threat> getByImpactSourcesAndTargets(Collection<ImpactSource> sources,
                                                     Collection<ImpactTarget> targets,
                                                     FetchPlan fetchPlan) {
        return dataManager.load(Threat.class)
                .query("""
                        select distinct thr
                          from gwf_Threat thr
                          join thr.sources src
                          join thr.targets trg
                         where src in :sources
                           and trg in :targets
                       """)
                .parameter("sources", sources)
                .parameter("targets", targets)
                .fetchPlan(fetchPlan)
                .list();
    }
}
