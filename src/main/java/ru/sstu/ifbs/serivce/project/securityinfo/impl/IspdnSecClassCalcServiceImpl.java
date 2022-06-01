package ru.sstu.ifbs.serivce.project.securityinfo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sstu.ifbs.entity.project.securityinfo.common.DamageDegreeLevel;
import ru.sstu.ifbs.entity.project.securityinfo.common.SecurityClass;
import ru.sstu.ifbs.entity.project.securityinfo.common.SystemScale;
import ru.sstu.ifbs.entity.project.securityinfo.ispdn.ActualThreatsType;
import ru.sstu.ifbs.entity.project.securityinfo.ispdn.PersonalDataCategory;
import ru.sstu.ifbs.entity.project.securityinfo.ispdn.PersonalDataProtectionLevel;
import ru.sstu.ifbs.entity.project.securityinfo.ispdn.PersonalDataSubjectType;
import ru.sstu.ifbs.serivce.project.securityinfo.GisSecClassCalcService;
import ru.sstu.ifbs.serivce.project.securityinfo.IspdnSecClassCalcService;

import static ru.sstu.ifbs.entity.project.securityinfo.ispdn.PersonalDataProtectionLevel.*;

@Component(IspdnSecClassCalcService.NAME)
public class IspdnSecClassCalcServiceImpl implements IspdnSecClassCalcService {

    @Autowired
    private GisSecClassCalcService gisSecClassCalcService;

    @Override
    public PersonalDataProtectionLevel getProtectLevel(PersonalDataCategory category,
                                                       PersonalDataSubjectType subjectType,
                                                       Integer count,
                                                       ActualThreatsType actualThreatsType) {
        return new PersonalDataProtectionLevelBuilder(
                category, subjectType, count > 100_000, actualThreatsType)
                .getProtectLevel();
    }

    @Override
    public SecurityClass getSecClass(DamageDegreeLevel confidentiality,
                                     DamageDegreeLevel integrity,
                                     DamageDegreeLevel accessibility,
                                     SystemScale systemScale) {
        return gisSecClassCalcService.getSecClass(
                systemScale, gisSecClassCalcService.getSignificanceLevel(integrity, confidentiality, accessibility));
    }

    private record PersonalDataProtectionLevelBuilder(
            PersonalDataCategory category,
            PersonalDataSubjectType subjectType,
            boolean countMore100k,
            ActualThreatsType actualThreatsType
    ) {
        public PersonalDataProtectionLevel getProtectLevel() {
            return switch (actualThreatsType) {
                case T1 -> getT1();
                case T2 -> getT2();
                case T3 -> getT3();
            };
        }

        private PersonalDataProtectionLevel getT1() {
            return switch (category) {
                case SPECIAL, BIOMETRIC -> PL1;
                case OTHER -> switch (subjectType) {
                    case EMPLOYEES -> PL2;
                    case EXTERNAL_USERS -> countMore100k ? PL1 : PL2;
                };
                case PUBLIC -> PL2;
            };
        }

        private PersonalDataProtectionLevel getT2() {
            return switch (category) {
                case SPECIAL -> switch (subjectType) {
                    case EXTERNAL_USERS -> countMore100k ? PL1 : PL2;
                    case EMPLOYEES -> PL2;
                };
                case BIOMETRIC -> PL2;
                case OTHER, PUBLIC -> switch (subjectType) {
                    case EXTERNAL_USERS -> countMore100k ? PL2 : PL3;
                    case EMPLOYEES -> PL3;
                };
            };
        }

        private PersonalDataProtectionLevel getT3() {
            return switch (category) {
                case SPECIAL -> switch (subjectType) {
                    case EXTERNAL_USERS -> countMore100k ? PL2 : PL3;
                    case EMPLOYEES -> PL3;
                };
                case BIOMETRIC -> PL3;
                case OTHER -> switch (subjectType) {
                    case EXTERNAL_USERS -> countMore100k ? PL3 : PL4;
                    case EMPLOYEES -> PL4;
                };
                case PUBLIC -> PL4;
            };
        }
    }
}
