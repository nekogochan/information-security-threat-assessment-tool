package ru.sstu.ifbs.serivce.project.securityinfo;

import ru.sstu.ifbs.entity.project.securityinfo.common.PossibleDamageDegree;
import ru.sstu.ifbs.entity.project.securityinfo.common.SecurityClass;
import ru.sstu.ifbs.entity.project.securityinfo.common.SystemScale;
import ru.sstu.ifbs.entity.project.securityinfo.gis.SignificanceLevel;

public interface GisSecurityClassCalcService {
    String NAME = "gwf_GisSecurityClassCalcService";

    SignificanceLevel getSignificanceLevel(PossibleDamageDegree possibleDamageDegree);

    SecurityClass getSecClass(SystemScale systemScale, SignificanceLevel significanceLevel);
}
