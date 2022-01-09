package ru.milovtim.bondschedule.adapter;

import lombok.val;
import ru.milovtim.bondschedule.Bond;
import ru.milovtim.bondschedule.moex.MoexIssInfo;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public class MoexIssBondAdapter implements Bond {
    private static final String DATA_INDEX_FIELD = "name";
    private static final String VALUE_INDEX_FIELD = "value";
    private static final String TYPE_INDEX_FIELD = "type";
    private static final String PRECISION_INDEX_FIELD = "precision";


    private final MoexIssInfo info;
    private final Map<String, MoexIssInfo.FieldData> index;

    public MoexIssBondAdapter(MoexIssInfo info) {
        this.info = info;
        this.index = createDataIndex();
    }

    private <T> T getAndConvertIndexedValue(String name, Class<T> clazz) {
        val fieldData = this.index.get(name);
        if (fieldData == null) {
            return null;
        }
        // [string, date, number, boolean]
        val type = fieldData.getDataAt(typeIndex()).orElseThrow();
        val value = fieldData.getDataAt(valueIndex()).orElseThrow();
        Object result;
        switch (type) {
            case "string":
                result = value;
                break;
            case "boolean":
                result = Boolean.valueOf(value);
                break;
            case "number":
                try {
                    result = NumberFormat.getInstance(Locale.US).parse(value);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "date":
                result = LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE);
                break;
            default:
                throw new RuntimeException("Cannot parse value '" + value + "' in unknown data type: " + type);
        }
        return clazz.cast(result);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private final Function<MoexIssInfo.FieldData, String> INDEX_FN =
            fieldData -> fieldData.getDataAt(this.nameIndex()).get();

    private Map<String, MoexIssInfo.FieldData> createDataIndex() {
        return info.getDescription()
                .getData().stream()
                .filter(fieldData -> fieldData.getDataAt(nameIndex()).isPresent())
                .collect(toMap(INDEX_FN, Function.identity()));

    }

    private int nameIndex() {
        return positionOfField(DATA_INDEX_FIELD);
    }


    private int typeIndex() {
        return positionOfField(TYPE_INDEX_FIELD);
    }

    private int valueIndex() {
        return positionOfField(VALUE_INDEX_FIELD);
    }

    private int positionOfField(String fieldName) {
        return info.getDescription().getColumns().indexOf(fieldName);
    }

    @Override
    public String getSecId() {
        return getAndConvertIndexedValue("SECID", String.class);
    }

    @Override
    public String getName() {
        return getAndConvertIndexedValue("NAME", String.class);
    }

    @Override
    public String getShortName() {
        return getAndConvertIndexedValue("SHORTNAME", String.class);
    }

    @Override
    public String getRegNumber() {
        return getAndConvertIndexedValue("REGNUMBER", String.class);
    }

    @Override
    public String getIsin() {
        return getAndConvertIndexedValue("ISIN", String.class);
    }

    @Override
    public LocalDate getIssueDate() {
        return getAndConvertIndexedValue("ISSUEDATE", LocalDate.class);
    }

    @Override
    public LocalDate getMatDate() {
        return getAndConvertIndexedValue("MATDATE", LocalDate.class);
    }

    @Override
    public LocalDate getBuybackDate() {
        return getAndConvertIndexedValue("BUYBACKDATE", LocalDate.class);
    }

    @Override
    public Number getInitialFaceValue() {
        return getAndConvertIndexedValue("INITIALFACEVALUE", Number.class);
    }

    @Override
    public String getFaceUnit() {
        return getAndConvertIndexedValue("FACEUNIT", String.class);
    }

    @Override
    public String getLatName() {
        return getAndConvertIndexedValue("LATNAME", String.class);
    }

    @Override
    public LocalDate getStartDateMoex() {
        return getAndConvertIndexedValue("STARTDATEMOEX", LocalDate.class);
    }

    @Override
    public String getProgramRegistryNumber() {
        return getAndConvertIndexedValue("PROGRAMREGISTRYNUMBER", String.class);
    }

    @Override
    public Boolean getEarlyRepayment() {
        return getAndConvertIndexedValue("EARLYREPAYMENT", Boolean.class);
    }

    @Override
    public Number getListLevel() {
        return getAndConvertIndexedValue("LISTLEVEL", Number.class);
    }

    @Override
    public Number getDaysToRedemption() {
        return getAndConvertIndexedValue("DAYSTOREDEMPTION", Number.class);
    }

    @Override
    public Number getIssueSize() {
        return getAndConvertIndexedValue("ISSUESIZE", Number.class);
    }

    @Override
    public Number getFaceValue() {
        return getAndConvertIndexedValue("FACEVALUE", Number.class);
    }

    @Override
    public Boolean getIsQualifiedInvestors() {
        return getAndConvertIndexedValue("ISQUALIFIEDINVESTORS", Boolean.class);
    }

    @Override
    public Number getCouponFrequency() {
        return getAndConvertIndexedValue("COUPONFREQUENCY", Number.class);
    }

    @Override
    public LocalDate getCouponDate() {
        return getAndConvertIndexedValue("COUPONDATE", LocalDate.class);
    }

    @Override
    public Number getCouponPercent() {
        return getAndConvertIndexedValue("COUPONPERCENT", Number.class);
    }

    @Override
    public Number getCouponValue() {
        return getAndConvertIndexedValue("COUPONVALUE", Number.class);
    }

    @Override
    public String getType() {
        return getAndConvertIndexedValue("TYPENAME", String.class);
    }

    @Override
    public String getTypeName() {
        return getAndConvertIndexedValue("GROUP", String.class);
    }

    @Override
    public String getGroup() {
        return getAndConvertIndexedValue("TYPE", String.class);
    }

    @Override
    public String getGroupName() {
        return getAndConvertIndexedValue("GROUPNAME", String.class);
    }

    @Override
    public Number getEmitterId() {
        return getAndConvertIndexedValue("EMITTER_ID", Number.class);
    }
}
