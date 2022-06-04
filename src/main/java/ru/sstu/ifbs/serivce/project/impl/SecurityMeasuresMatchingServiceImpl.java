package ru.sstu.ifbs.serivce.project.impl;

import io.jmix.core.FetchPlan;
import io.jmix.core.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sstu.ifbs.entity.project.ActualSecurityMeasure;
import ru.sstu.ifbs.entity.project.Project;
import ru.sstu.ifbs.entity.project.securityinfo.gis.GisSecurityInfo;
import ru.sstu.ifbs.entity.project.securityinfo.ispdn.IspdnSecurityInfo;
import ru.sstu.ifbs.entity.project.securityinfo.ispdn.PersonalDataProtectionLevel;
import ru.sstu.ifbs.entity.storage.measures.SecurityMeasure;
import ru.sstu.ifbs.repository.SecurityMeasureRepository;
import ru.sstu.ifbs.serivce.project.SecurityMeasuresMatchingService;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Component(SecurityMeasuresMatchingService.NAME)
public class SecurityMeasuresMatchingServiceImpl implements SecurityMeasuresMatchingService {

    @Autowired
    private SecurityMeasureRepository securityMeasureRepository;
    @Autowired
    private Metadata metadata;

    @Override
    public List<ActualSecurityMeasure> getMatches(Project project, GisSecurityInfo secInfo,
                                                  FetchPlan securityMeasureFetchPlan) {
        return securityMeasureRepository.getByProtectionLevel(
                        (switch (secInfo.getSecurityClass()) {
                            case K1 -> PersonalDataProtectionLevel.PL1;
                            case K2 -> PersonalDataProtectionLevel.PL2;
                            case K3 -> PersonalDataProtectionLevel.PL3;
                        }),
                        securityMeasureFetchPlan)
                .stream()
                .map(toActual(project))
                .collect(toList());
    }

    @Override
    public List<ActualSecurityMeasure> getMatches(Project project, IspdnSecurityInfo secInfo,
                                                  FetchPlan securityMeasureFetchPlan) {
        return securityMeasureRepository.getByProtectionLevel(
                        secInfo.getPersonalData().getProtectionLevel(),
                        securityMeasureFetchPlan)
                .stream()
                .map(toActual(project))
                .collect(toList());
    }

    private Function<SecurityMeasure, ActualSecurityMeasure> toActual(Project project) {
        return it -> {
            var asm = metadata.create(ActualSecurityMeasure.class);
            asm.setProject(project);
            asm.setValue(it);
            return asm;
        };
    }
}
