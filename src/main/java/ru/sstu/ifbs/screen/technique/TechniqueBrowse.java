package ru.sstu.ifbs.screen.technique;

import io.jmix.core.DataManager;
import io.jmix.core.annotation.Internal;
import io.jmix.core.common.event.Subscription;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.*;
import io.jmix.ui.component.data.ValueSource;
import io.jmix.ui.component.validation.Validator;
import io.jmix.ui.icon.Icons;
import io.jmix.ui.meta.PropertyType;
import io.jmix.ui.meta.StudioElementsGroup;
import io.jmix.ui.meta.StudioProperty;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.*;
import io.jmix.ui.screen.LookupComponent;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sstu.ifbs.entity.storage.tactic.Tactic;
import ru.sstu.ifbs.entity.storage.tactic.Technique;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.function.Consumer;

class BufferedFieldDecorator<T> implements Field<T>, Buffered {
    private final Field<T> it;

    BufferedFieldDecorator(Field<T> it) {
        this.it = it;
    }

    @Override
    public void commit() {
        System.out.println("BufferedFieldDecorator.commit");
    }

    @Override
    public void discard() {
        System.out.println("BufferedFieldDecorator.discard");
    }

    @Override
    public boolean isBuffered() {
        System.out.println("BufferedFieldDecorator.isBuffered");
        return false;
    }

    @Override
    public void setBuffered(boolean buffered) {
        System.out.println("BufferedFieldDecorator.setBuffered");
    }

    @Override
    public boolean isModified() {
        System.out.println("BufferedFieldDecorator.isModified");
        return false;
    }

    @Override
    public void setValueSource(@Nullable ValueSource<T> valueSource) {
        it.setValueSource(valueSource);
    }

    @Override
    @Nullable
    public ValueSource<T> getValueSource() {
        return it.getValueSource();
    }

    @Override
    @Nullable
    public String getCaption() {
        return it.getCaption();
    }

    @Override
    @StudioProperty(type = PropertyType.LOCALIZED_STRING)
    public void setCaption(@Nullable String caption) {
        it.setCaption(caption);
    }

    @Override
    @Nullable
    public String getDescription() {
        return it.getDescription();
    }

    @Override
    @StudioProperty(type = PropertyType.LOCALIZED_STRING)
    public void setDescription(@Nullable String description) {
        it.setDescription(description);
    }

    @Override
    @Nullable
    public T getValue() {
        return it.getValue();
    }

    @Override
    public void setValue(@Nullable T value) {
        it.setValue(value);
    }

    @Override
    public void clear() {
        it.clear();
    }

    @Override
    @Nullable
    public T getEmptyValue() {
        return it.getEmptyValue();
    }

    @Override
    public boolean isEmpty() {
        return it.isEmpty();
    }

    @Override
    public Subscription addValueChangeListener(Consumer<ValueChangeEvent<T>> listener) {
        return it.addValueChangeListener(listener);
    }

    @Override
    @Nullable
    public String getId() {
        return it.getId();
    }

    @Override
    @StudioProperty(type = PropertyType.COMPONENT_ID)
    public void setId(@Nullable String id) {
        it.setId(id);
    }

    @Override
    @Nullable
    public Component getParent() {
        return it.getParent();
    }

    @Override
    @Internal
    public void setParent(@Nullable Component parent) {
        it.setParent(parent);
    }

    @Override
    public boolean isEnabled() {
        return it.isEnabled();
    }

    @Override
    @StudioProperty(name = "enable", defaultValue = "true")
    public void setEnabled(boolean enabled) {
        it.setEnabled(enabled);
    }

    @Override
    public boolean isResponsive() {
        return it.isResponsive();
    }

    @Override
    @StudioProperty(defaultValue = "false")
    public void setResponsive(boolean responsive) {
        it.setResponsive(responsive);
    }

    @Override
    public boolean isVisible() {
        return it.isVisible();
    }

    @Override
    @StudioProperty(defaultValue = "true")
    public void setVisible(boolean visible) {
        it.setVisible(visible);
    }

    @Override
    public boolean isVisibleRecursive() {
        return it.isVisibleRecursive();
    }

    @Override
    public boolean isEnabledRecursive() {
        return it.isEnabledRecursive();
    }

    @Override
    public float getHeight() {
        return it.getHeight();
    }

    @Override
    public SizeUnit getHeightSizeUnit() {
        return it.getHeightSizeUnit();
    }

    @Override
    @StudioProperty(type = PropertyType.SIZE, defaultValue = "-1px")
    public void setHeight(@Nullable String height) {
        it.setHeight(height);
    }

    @Override
    public void setHeightAuto() {
        it.setHeightAuto();
    }

    @Override
    public void setHeightFull() {
        it.setHeightFull();
    }

    @Override
    public float getWidth() {
        return it.getWidth();
    }

    @Override
    public SizeUnit getWidthSizeUnit() {
        return it.getWidthSizeUnit();
    }

    @Override
    @StudioProperty(type = PropertyType.SIZE, defaultValue = "-1px")
    public void setWidth(@Nullable String width) {
        it.setWidth(width);
    }

    @Override
    public void setWidthAuto() {
        it.setWidthAuto();
    }

    @Override
    public void setWidthFull() {
        it.setWidthFull();
    }

    @Override
    public void setSizeFull() {
        it.setSizeFull();
    }

    @Override
    public void setSizeAuto() {
        it.setSizeAuto();
    }

    @Override
    public Alignment getAlignment() {
        return it.getAlignment();
    }

    @Override
    @StudioProperty(name = "align", type = PropertyType.ENUMERATION, defaultValue = "TOP_LEFT")
    public void setAlignment(Alignment alignment) {
        it.setAlignment(alignment);
    }

    @Override
    public String getStyleName() {
        return it.getStyleName();
    }

    @Override
    @StudioProperty(name = "stylename", type = PropertyType.CSS_CLASSNAME_LIST)
    public void setStyleName(@Nullable String styleName) {
        it.setStyleName(styleName);
    }

    @Override
    public void addStyleName(String styleName) {
        it.addStyleName(styleName);
    }

    @Override
    public void removeStyleName(String styleName) {
        it.removeStyleName(styleName);
    }

    @Override
    public <X> X unwrap(Class<X> internalComponentClass) {
        return it.unwrap(internalComponentClass);
    }

    @Override
    @Nullable
    public <X> X unwrapOrNull(Class<X> internalComponentClass) {
        return it.unwrapOrNull(internalComponentClass);
    }

    @Override
    public <X> void withUnwrapped(Class<X> internalComponentClass, Consumer<X> action) {
        it.withUnwrapped(internalComponentClass, action);
    }

    @Override
    public <X> X unwrapComposition(Class<X> internalCompositionClass) {
        return it.unwrapComposition(internalCompositionClass);
    }

    @Override
    @Nullable
    public <X> X unwrapCompositionOrNull(Class<X> internalCompositionClass) {
        return it.unwrapCompositionOrNull(internalCompositionClass);
    }

    @Override
    public <X> void withUnwrappedComposition(Class<X> internalCompositionClass, Consumer<X> action) {
        it.withUnwrappedComposition(internalCompositionClass, action);
    }

    @Override
    public boolean isEditable() {
        return it.isEditable();
    }

    @Override
    @StudioProperty(defaultValue = "true")
    public void setEditable(boolean editable) {
        it.setEditable(editable);
    }

    @Override
    public boolean isEditableWithParent() {
        return it.isEditableWithParent();
    }

    @Override
    @Nullable
    public Frame getFrame() {
        return it.getFrame();
    }

    @Override
    public void setFrame(@Nullable Frame frame) {
        it.setFrame(frame);
    }

    @Override
    public boolean isValid() {
        return it.isValid();
    }

    @Override
    public void validate() throws ValidationException {
        it.validate();
    }

    @Override
    public boolean isValidateOnCommit() {
        return it.isValidateOnCommit();
    }

    @Override
    @Nullable
    public String getIcon() {
        return it.getIcon();
    }

    @Override
    @StudioProperty(type = PropertyType.ICON_ID)
    public void setIcon(@Nullable String icon) {
        it.setIcon(icon);
    }

    @Override
    public void setIconFromSet(@Nullable Icons.Icon icon) {
        it.setIconFromSet(icon);
    }

    @Override
    @Nullable
    public String getContextHelpText() {
        return it.getContextHelpText();
    }

    @Override
    @StudioProperty(type = PropertyType.LOCALIZED_STRING)
    public void setContextHelpText(@Nullable String contextHelpText) {
        it.setContextHelpText(contextHelpText);
    }

    @Override
    public boolean isContextHelpTextHtmlEnabled() {
        return it.isContextHelpTextHtmlEnabled();
    }

    @Override
    @StudioProperty(defaultValue = "false")
    public void setContextHelpTextHtmlEnabled(boolean enabled) {
        it.setContextHelpTextHtmlEnabled(enabled);
    }

    @Override
    @Nullable
    public Consumer<ContextHelpIconClickEvent> getContextHelpIconClickHandler() {
        return it.getContextHelpIconClickHandler();
    }

    @Override
    public void setContextHelpIconClickHandler(@Nullable Consumer<ContextHelpIconClickEvent> handler) {
        it.setContextHelpIconClickHandler(handler);
    }

    @Override
    public boolean isCaptionAsHtml() {
        return it.isCaptionAsHtml();
    }

    @Override
    @StudioProperty(defaultValue = "false")
    public void setCaptionAsHtml(boolean captionAsHtml) {
        it.setCaptionAsHtml(captionAsHtml);
    }

    @Override
    public boolean isDescriptionAsHtml() {
        return it.isDescriptionAsHtml();
    }

    @Override
    @StudioProperty(defaultValue = "false")
    public void setDescriptionAsHtml(boolean descriptionAsHtml) {
        it.setDescriptionAsHtml(descriptionAsHtml);
    }

    @Override
    public boolean isHtmlSanitizerEnabled() {
        return it.isHtmlSanitizerEnabled();
    }

    @Override
    @StudioProperty(defaultValue = "true")
    public void setHtmlSanitizerEnabled(boolean htmlSanitizerEnabled) {
        it.setHtmlSanitizerEnabled(htmlSanitizerEnabled);
    }

    @Override
    @StudioElementsGroup(caption = "Validators", xmlElement = "validators", icon = "io/jmix/ui/icon/element/validators.svg")
    public void addValidator(Validator<? super T> validator) {
        it.addValidator(validator);
    }

    @Override
    public void removeValidator(Validator<T> validator) {
        it.removeValidator(validator);
    }

    @Override
    public void addValidators(Validator<? super T>... validators) {
        it.addValidators(validators);
    }

    @Override
    public Collection<Validator<T>> getValidators() {
        return it.getValidators();
    }

    @Override
    public boolean isRequired() {
        return it.isRequired();
    }

    @Override
    @StudioProperty(defaultValue = "false")
    public void setRequired(boolean required) {
        it.setRequired(required);
    }

    @Override
    @Nullable
    public String getRequiredMessage() {
        return it.getRequiredMessage();
    }

    @Override
    @StudioProperty(type = PropertyType.LOCALIZED_STRING)
    public void setRequiredMessage(@Nullable String msg) {
        it.setRequiredMessage(msg);
    }
}

@UiController("gwf_Technique.browse")
@UiDescriptor("technique-browse.xml")
@LookupComponent("techniquesTable")
public class TechniqueBrowse extends StandardLookup<Technique> {
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private CollectionContainer<Tactic> tacticsDc;
    @Autowired
    private DataManager dataManager;

    @Install(to = "techniquesTable.tactic", subject = "editFieldGenerator")
    private Field<?> techniquesTableTacticEditFieldGenerator(DataGrid.EditorFieldGenerationContext<Technique> editorFieldGenerationContext) {
        var field = uiComponents.create(SuggestionField.of(Tactic.class));
        field.setValueSource((ValueSource<Tactic>) editorFieldGenerationContext
                .getValueSourceProvider().getValueSource("tactic"));
        field.setSearchExecutor((name, par) -> dataManager.load(Tactic.class)
                .query("e.name like ?1 order by e.name", "(?i)%" + name + "%")
                .list());
        field.setMinSearchStringLength(3);
        return new BufferedFieldDecorator<>(field);
    }
}