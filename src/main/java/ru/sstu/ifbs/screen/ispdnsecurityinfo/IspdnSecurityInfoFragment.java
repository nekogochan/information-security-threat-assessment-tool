package ru.sstu.ifbs.screen.ispdnsecurityinfo;

import io.jmix.ui.component.ComboBox;
import io.jmix.ui.component.Field;
import io.jmix.ui.component.TextField;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sstu.ifbs.entity.project.securityinfo.common.DamageDegreeLevel;
import ru.sstu.ifbs.entity.project.securityinfo.common.SecurityClass;
import ru.sstu.ifbs.entity.project.securityinfo.common.SystemScale;
import ru.sstu.ifbs.entity.project.securityinfo.ispdn.*;
import ru.sstu.ifbs.serivce.project.securityinfo.IspdnSecClassCalcService;

import javax.validation.constraints.PositiveOrZero;
import java.util.stream.Stream;

@UiController("gwf_IspdnSecurityInfoFragment")
@UiDescriptor("ispdn-security-info-fragment.xml")
public class IspdnSecurityInfoFragment extends ScreenFragment {
    @Autowired
    private IspdnSecClassCalcService ispdnSecClassCalcService;

    @Autowired
    private Field<ActualThreatsType> actualThreatsType;
    @Autowired
    private Field<PersonalDataCategory> personalDataCategory;
    @Autowired
    private Field<Integer> personalDataCount;
    @Autowired
    private Field<PersonalDataProtectionLevel> personalDataProtectionLevel;
    @Autowired
    private Field<PersonalDataSubjectType> personalDataSubjectType;
    @Autowired
    private Field<DamageDegreeLevel> possibleDamageDegreeAccessibility;
    @Autowired
    private Field<DamageDegreeLevel> possibleDamageDegreeConfidentiality;
    @Autowired
    private Field<DamageDegreeLevel> possibleDamageDegreeIntegrity;
    @Autowired
    private Field<SecurityClass> securityClass;
    @Autowired
    private Field<SystemScale> systemScale;

    @Autowired
    private InstanceContainer<IspdnSecurityInfo> ispdnSecurityInfoDc;

    public InstanceContainer<IspdnSecurityInfo> getIspdnSecurityInfoDc() {
        return ispdnSecurityInfoDc;
    }

    @Subscribe
    public void onInit(InitEvent event) {
        Stream.of(possibleDamageDegreeConfidentiality,
                  possibleDamageDegreeAccessibility,
                  possibleDamageDegreeIntegrity,
                  systemScale)
                .forEach(field -> {
                    field.addValueChangeListener($ -> {
                        var conf = possibleDamageDegreeConfidentiality.getValue();
                        var acc = possibleDamageDegreeAccessibility.getValue();
                        var integ = possibleDamageDegreeIntegrity.getValue();
                        var ss = systemScale.getValue();

                        if (conf == null || acc == null || integ == null || ss == null) {
                            securityClass.setValue(null);
                            return;
                        }

                        securityClass.setValue(
                                ispdnSecClassCalcService.getSecClass(conf, integ, acc, ss));
                    });
                });

        Stream.of(
                personalDataCategory,
                personalDataSubjectType,
                personalDataCount,
                actualThreatsType
        ).forEach(field -> {
            field.addValueChangeListener($ -> {
                var cat = personalDataCategory.getValue();
                var subType = personalDataSubjectType.getValue();
                var count = personalDataCount.getValue();
                var thrType = actualThreatsType.getValue();

                if (cat == null || subType == null || count == null || count < 0 || thrType == null) {
                    personalDataProtectionLevel.setValue(null);
                    return;
                }

                personalDataProtectionLevel.setValue(
                        ispdnSecClassCalcService.getProtectLevel(cat, subType, count, thrType));
            });
        });
    }
}