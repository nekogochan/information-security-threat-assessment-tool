package ru.sstu.ifbs.repository;

import org.springframework.stereotype.Component;
import ru.sstu.ifbs.entity.storage.scenario.ThreatScenario;

@Component(ThreatScenarioRepository.NAME)
public class ThreatScenarioRepository extends DefaultRepository<ThreatScenario> {

    public static final String NAME = "gwf_ThreatScenarioRepository";

    @Override
    protected Class<ThreatScenario> entityClass() {
        return ThreatScenario.class;
    }
}
