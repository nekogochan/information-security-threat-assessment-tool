package ru.sstu.ifbs.serivce.project.securityinfo;

import ru.sstu.ifbs.entity.project.securityinfo.common.DamageDegreeLevel;
import ru.sstu.ifbs.entity.project.securityinfo.common.SecurityClass;
import ru.sstu.ifbs.entity.project.securityinfo.common.SystemScale;
import ru.sstu.ifbs.entity.project.securityinfo.ispdn.ActualThreatsType;
import ru.sstu.ifbs.entity.project.securityinfo.ispdn.PersonalDataCategory;
import ru.sstu.ifbs.entity.project.securityinfo.ispdn.PersonalDataProtectionLevel;
import ru.sstu.ifbs.entity.project.securityinfo.ispdn.PersonalDataSubjectType;

public interface IspdnSecClassCalcService {
    String NAME = "gwf_IspdnSecClassCalcService";

    PersonalDataProtectionLevel getProtectLevel(PersonalDataCategory category,
                                                PersonalDataSubjectType subjectType,
                                                Integer count,
                                                ActualThreatsType actualThreatsType);

    SecurityClass getSecClass(DamageDegreeLevel confidentiality,
                              DamageDegreeLevel integrity,
                              DamageDegreeLevel accessibility,
                              SystemScale systemScale);
}
