package ru.sstu.ifbs.screen.securitymeasure;

import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sstu.ifbs.entity.storage.measures.SecurityMeasure;

import static java.util.Comparator.comparing;

@UiController("gwf_SecurityMeasures.browse")
@UiDescriptor("security-measure-browse.xml")
@LookupComponent("securityMeasuresTable")
public class SecurityMeasureBrowse extends StandardLookup<SecurityMeasure> {

    @Autowired
    private CollectionContainer<SecurityMeasure> securityMeasuresDc;

    public CollectionContainer<SecurityMeasure> getSecurityMeasuresDc() {
        return securityMeasuresDc;
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        getSecurityMeasuresDc().getMutableItems().sort(SecurityMeasure.COMPARATOR);
    }
}