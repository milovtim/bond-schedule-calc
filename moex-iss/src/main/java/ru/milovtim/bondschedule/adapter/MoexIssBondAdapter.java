package ru.milovtim.bondschedule.adapter;

import lombok.val;
import ru.milovtim.bondschedule.Bond;

import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class MoexIssBondAdapter implements PojoAdapter<MoexIssInfo, Bond> {
    private static final String DATA_INDEX_FIELD = "name";
    private static final String VALUE_INDEX_FIELD = "value";
    private static final String TYPE_INDEX_FIELD = "type";
    private static final String PRECISION_INDEX_FIELD = "precision";


    public final MoexIssInfo info;

    public MoexIssBondAdapter(MoexIssInfo info) {
        this.info = info;
    }

    @Override
    public MoexIssInfo getSource() {
        return info;
    }

    @Override
    public Bond createDestination() {
        val index = createDataIndex();
        val builder = Bond.builder();
        forEachBuilderMethod(invokeOnBuilderWithDataFromIndex(builder, index));
        return builder.build();
    }

    private Consumer<Method> invokeOnBuilderWithDataFromIndex(Bond.BondBuilder builder, Map<String, MoexIssInfo.FieldData> index) {
        return method -> findAndConvertValue(index, method.getName())
                .ifPresent(valFromIndex -> {
                    try {
                        method.invoke(builder, valFromIndex);
                    } catch (ReflectiveOperationException roe) {
                        throw new RuntimeException(roe);
                    }
                });
    }

    private Optional<Object> findAndConvertValue(Map<String, MoexIssInfo.FieldData> index, String methodName) {
        return index.keySet().stream().filter(methodName::equalsIgnoreCase).findFirst()
                .map(index::get)
                .map(fieldData -> {
                    // [string, date, number, boolean]
                    val type = fieldData.getDataAt(typeIndex()).orElseThrow();
                    val value = fieldData.getDataAt(typeIndex()).orElseThrow();
                    switch (type) {
                        case "string":
                            return value;
                        case "boolean":
                            return Boolean.valueOf(value);
                        case "number":
                            try {
                                return NumberFormat.getInstance(Locale.US).parse(value);
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                        case "date":
                            return LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE);
                        default:
                            throw new RuntimeException("Cannot parse value '" + value + "' in unknown data type: " + type);
                    }
                });
    }

    private void forEachBuilderMethod(Consumer<Method> methodConsumer) {
        Stream.of(Bond.BondBuilder.class.getDeclaredMethods())
                .filter(m -> m.getReturnType() == Bond.BondBuilder.class)
                .forEach(methodConsumer);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private final Function<MoexIssInfo.FieldData, String> INDEX_FN =
            fieldData -> fieldData.getDataAt(this.nameIndex())
                    .map(s -> s.replace("_", ""))
                    .get();

    private Map<String, MoexIssInfo.FieldData> createDataIndex() {
        return info.getDescription()
                .getData().stream()
                .filter(fieldData -> fieldData.getDataAt(nameIndex()).isPresent())
                .collect(toMap(INDEX_FN, Function.identity()));

    }

    private int nameIndex() {
        return positionOfField(DATA_INDEX_FIELD);
    }

    private int valueIndex() {
        return positionOfField(VALUE_INDEX_FIELD);
    }

    private int typeIndex() {
        return positionOfField(TYPE_INDEX_FIELD);
    }

    private int precisionIndex() {
        return positionOfField(PRECISION_INDEX_FIELD);
    }

    private int positionOfField(String fieldName) {
        return info.getDescription().getColumns().indexOf(fieldName);
    }
}
