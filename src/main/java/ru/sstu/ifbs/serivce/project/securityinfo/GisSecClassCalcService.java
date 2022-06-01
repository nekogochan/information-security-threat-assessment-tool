package ru.sstu.ifbs.serivce.project.securityinfo;

import ru.sstu.ifbs.entity.project.securityinfo.common.DamageDegreeLevel;
import ru.sstu.ifbs.entity.project.securityinfo.common.SecurityClass;
import ru.sstu.ifbs.entity.project.securityinfo.common.SystemScale;
import ru.sstu.ifbs.entity.project.securityinfo.gis.SignificanceLevel;

public interface GisSecClassCalcService {
    String NAME = "gwf_GisSecClassCalcService";

    SignificanceLevel getSignificanceLevel(DamageDegreeLevel confidentiality,
                                           DamageDegreeLevel integrity,
                                           DamageDegreeLevel accessibility);

    SecurityClass getSecClass(SystemScale systemScale, SignificanceLevel significanceLevel);
}
