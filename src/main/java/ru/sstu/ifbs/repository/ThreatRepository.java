package ru.sstu.ifbs.repository;

import org.springframework.stereotype.Component;
import ru.sstu.ifbs.entity.Threat;

@Component(ThreatRepository.NAME)
public class ThreatRepository extends DefaultRepository<Threat>  {
    public static final String NAME = "gwf_ThreatRepository";

    @Override
    protected Class<Threat> entityClass() {
        return Threat.class;
    }
}
