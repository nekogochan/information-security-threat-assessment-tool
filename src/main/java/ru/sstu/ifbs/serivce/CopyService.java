package ru.sstu.ifbs.serivce;

import io.jmix.core.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sstu.ifbs.entity.storage.scenario.ScenarioTactic;
import ru.sstu.ifbs.entity.storage.scenario.ScenarioTechnique;
import ru.sstu.ifbs.entity.storage.scenario.ThreatScenario;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@Component(CopyService.NAME)
public class CopyService {
    public static final String NAME = "gwf_CopyService";
    @Autowired
    private Metadata metadata;

    public ThreatScenario copy(ThreatScenario scenarioSrc,
                               UnaryOperator<ThreatScenario> scenarioMpr,
                               UnaryOperator<ScenarioTactic> tacticMpr,
                               UnaryOperator<ScenarioTechnique> techMpr) {
        var scenarioCopy = metadata.create(scenarioSrc.getClass());
        scenarioCopy.setName(scenarioSrc.getName());
        scenarioCopy.setDescription(scenarioSrc.getDescription());
        scenarioCopy.setImplMethod(scenarioSrc.getImplMethod());
        scenarioCopy.setSources(new ArrayList<>(scenarioSrc.getSources()));
        scenarioCopy.setTactics(map(scenarioSrc.getTactics(), copyScenarioTactics(tacticMpr, techMpr, scenarioCopy)));
        return scenarioMpr.apply(scenarioCopy);
    }

    private Function<ScenarioTactic, ScenarioTactic> copyScenarioTactics(UnaryOperator<ScenarioTactic> tacticMpr,
                                                                         UnaryOperator<ScenarioTechnique> techMpr,
                                                                         ThreatScenario scenarioCopy) {
        return tacticSrc -> {
            var tacticCopy = metadata.create(tacticSrc.getClass());
            tacticCopy.setValue(tacticSrc.getValue());
            tacticCopy.setThreatScenario(scenarioCopy);
            tacticCopy.setTechniques(map(tacticSrc.getTechniques(), copyScenarioTechniques(techMpr, tacticCopy)));
            return tacticMpr.apply(tacticCopy);
        };
    }

    private Function<ScenarioTechnique, ScenarioTechnique> copyScenarioTechniques(UnaryOperator<ScenarioTechnique> techMpr,
                                                                                  ScenarioTactic tacticCopy) {
        return techSrc -> {
            var techCopy = metadata.create(techSrc.getClass());
            techCopy.setValue(techSrc.getValue());
            techCopy.setTactic(tacticCopy);
            return techMpr.apply(techCopy);
        };
    }

    private <T, U> List<U> map(List<T> src, Function<T, U> fn) {
        return src.stream().map(fn).collect(Collectors.toList());
    }
}
