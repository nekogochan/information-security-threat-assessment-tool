package ru.sstu.ifbs.repository;

import io.jmix.core.FetchPlan;
import org.springframework.stereotype.Component;
import ru.sstu.ifbs.entity.project.securityinfo.ispdn.PersonalDataProtectionLevel;
import ru.sstu.ifbs.entity.storage.measures.SecurityMeasure;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component(SecurityMeasureRepository.NAME)
public class SecurityMeasureRepository extends DefaultRepository<SecurityMeasure> {
    public static final String NAME = "gwf_SecurityMeasureRepository";

    @Override
    protected Class<SecurityMeasure> entityClass() {
        return SecurityMeasure.class;
    }

    public List<SecurityMeasure> getByProtectionLevel(PersonalDataProtectionLevel protectionLevel,
                                                      FetchPlan fetchPlan) {
        return load().all()
                .fetchPlan(fetchPlan)
                .list()
                .stream()
                .filter(it -> {
                    var pl = it.getProtectionLevel();
                    return pl != null && pl.compareTo(protectionLevel) >= 0;
                })
                .collect(toList());
    }
}
