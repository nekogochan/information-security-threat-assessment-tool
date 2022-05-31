package ru.sstu.ifbs.serivce.project.securityinfo.impl;

import org.springframework.stereotype.Component;
import ru.sstu.ifbs.entity.project.securityinfo.common.DamageDegreeLevel;
import ru.sstu.ifbs.entity.project.securityinfo.common.PossibleDamageDegree;
import ru.sstu.ifbs.entity.project.securityinfo.common.SecurityClass;
import ru.sstu.ifbs.entity.project.securityinfo.common.SystemScale;
import ru.sstu.ifbs.entity.project.securityinfo.gis.SignificanceLevel;
import ru.sstu.ifbs.serivce.project.securityinfo.GisSecurityClassCalcService;

import java.util.Arrays;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Stream;

import static ru.sstu.ifbs.entity.project.securityinfo.common.SecurityClass.*;
import static ru.sstu.ifbs.entity.project.securityinfo.common.SystemScale.*;

@Component(GisSecurityClassCalcService.NAME)
public class GisSecurityClassCalcServiceImpl implements GisSecurityClassCalcService {

    @Override
    public SecurityClass getSecClass(SystemScale systemScale, SignificanceLevel significanceLevel) {
        return switch (significanceLevel) {
            case SL1 -> K1;
            case SL2 -> systemScale == FEDERAL ? K1 : K2;
            case SL3 -> systemScale == FEDERAL ? K2 : K3;
        };
    }

    @Override
    public SignificanceLevel getSignificanceLevel(PossibleDamageDegree possibleDamageDegree) {
        return Stream.of(
                        possibleDamageDegree.getIntegrity(),
                        possibleDamageDegree.getAccessibility(),
                        possibleDamageDegree.getConfidentiality())
                .peek(it -> {
                    if (it == null) throw new IllegalStateException();
                })
                .reduce((a, b) -> switch (a) {
                    case HIGH -> a;
                    case MEDIUM -> b == DamageDegreeLevel.HIGH ? b : a;
                    case LOW -> b;
                })
                .map(it -> switch (it) {
                    case LOW -> SignificanceLevel.SL3;
                    case MEDIUM -> SignificanceLevel.SL2;
                    case HIGH -> SignificanceLevel.SL1;
                })
                .get();
    }
}
