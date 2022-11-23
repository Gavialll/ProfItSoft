package task_1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static task_1.Action.VALUE;
import static task_1.Action.ALL_ROW;

/**
 * Class take "XML" in format
 * <pre>
 * &#60person surname="Шевченко" name="Тарас"/&#62
 * </pre>
 *
 * @author  Andrii Dutko
 */

public class XML {

    private String xml;

    /**
     * <p> Method join two attribute in one, and You can set new name for attribut
     * <p> Example:
     * <pre>
     * Call   -> joinFields("name", "surname", "fullName");
     * Input  -> &#60person surname="Шевченко" name="Тарас"/&#62 ,
     * Output -> &#60person fullName="Тарас Шевченко"/&#62 ,
     * </pre>
     *
     * @param firstFieldName Attribute name
     * @param secondFieldName Attribute name
     * @param newName Set new name for attribute
     * @return Method will return itself
     */
    public XML joinFields(String firstFieldName, String secondFieldName, String newName) {

        String first = getField(firstFieldName, ALL_ROW);
        String second = getField(secondFieldName, VALUE);

        String newValue = first.substring(0, first.length() - 1) + " " + second + "\"";

        newValue = newValue.replaceAll(firstFieldName, newName);

        xml = xml.replaceAll(getField(secondFieldName, ALL_ROW), "");
        xml = xml.replaceAll(getField(firstFieldName, ALL_ROW), newValue);

        return this;
    }

    /**
     * <p>Method take one attribute from "XML"
     *
     * <p>Method can return part of field or all field,
     * You can choose action for choose part of field.
     * <pre>
     *  {@link task_1.Action}.ALL_ROW return String = "<i>name="value"</i>"
     *  {@link task_1.Action}.KEY     return String = "<i>name</i>"
     *  {@link task_1.Action}.VALUE   return String = "<i>value</i>"
     * </pre>
     *
     * @param fieldName Attribute name
     * @param action Enum for choose action
     * @return String
     */
    public String getField(String fieldName, Action action) {
        Pattern pattern = Pattern.compile("\\s(" + fieldName + ")\\s*=\\s*\\\"([А-я|A-z|іІїЇ.,|0-9]*)\\\"");

        Matcher matcher = pattern.matcher(xml);
        if(matcher.find()) return matcher.group(action.getValue());

        throw new IllegalArgumentException("Field with name = \"" + fieldName + "\" not found");
    }

    public String getXml() {
        return xml;
    }

    public XML(String xml) {
        this.xml = xml;
    }
}
