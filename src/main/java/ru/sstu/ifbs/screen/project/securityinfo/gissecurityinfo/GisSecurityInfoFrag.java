package ru.sstu.ifbs.screen.project.securityinfo.gissecurityinfo;

import io.jmix.ui.component.Field;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sstu.ifbs.entity.project.securityinfo.common.DamageDegreeLevel;
import ru.sstu.ifbs.entity.project.securityinfo.common.SecurityClass;
import ru.sstu.ifbs.entity.project.securityinfo.common.SystemScale;
import ru.sstu.ifbs.entity.project.securityinfo.gis.GisSecurityInfo;
import ru.sstu.ifbs.entity.project.securityinfo.gis.SignificanceLevel;
import ru.sstu.ifbs.serivce.project.securityinfo.GisSecClassCalcService;

import java.util.stream.Stream;

@UiController("gwf_GisSecurityInfoFrag")
@UiDescriptor("gis-security-info-frag.xml")
public class GisSecurityInfoFrag extends ScreenFragment {

    @Autowired
    private GisSecClassCalcService gisSecClassCalcService;

    @Autowired
    private Field<SecurityClass> securityClass;
    @Autowired
    private Field<DamageDegreeLevel> possibleDamageDegreeIntegrity;
    @Autowired
    private Field<DamageDegreeLevel> possibleDamageDegreeConfidentiality;
    @Autowired
    private Field<DamageDegreeLevel> possibleDamageDegreeAccessibility;
    @Autowired
    private Field<SignificanceLevel> significanceLevel;
    @Autowired
    private Field<SystemScale> systemScale;

    @Autowired
    private InstanceContainer<GisSecurityInfo> gisSecurityInfoDc;

    public InstanceContainer<GisSecurityInfo> getGisSecurityInfoDc() {
        return gisSecurityInfoDc;
    }

    @Subscribe
    public void onInit(InitEvent event) {
        Stream.of(
                possibleDamageDegreeIntegrity,
                possibleDamageDegreeConfidentiality,
                possibleDamageDegreeAccessibility
        ).forEach(field -> {
            field.addValueChangeListener(it -> {
                var integ = possibleDamageDegreeIntegrity.getValue();
                var conf = possibleDamageDegreeConfidentiality.getValue();
                var acc = possibleDamageDegreeAccessibility.getValue();

                if (integ == null || conf == null || acc == null) {
                    significanceLevel.setValue(null);
                    return;
                }

                significanceLevel.setValue(
                        gisSecClassCalcService.getSignificanceLevel(integ, conf, acc));
            });
        });

        Stream.of(
                systemScale,
                significanceLevel
        ).forEach(field -> {
            field.addValueChangeListener(it -> {
                var ss = systemScale.getValue();
                var sl = significanceLevel.getValue();

                if (ss == null || sl == null) {
                    securityClass.setValue(null);
                    return;
                }

                securityClass.setValue(
                        gisSecClassCalcService.getSecClass(ss, sl));
            });
        });
    }
}