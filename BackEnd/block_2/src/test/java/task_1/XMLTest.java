package task_1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class XMLTest {

    @Test
    void joinFields() {
        String xml = getXML();

        String actual = new XML(xml)
                .joinFields("name", "surname", "fullname")
                .getXml();
        String expected = "<person fullname=\"Іван Котляревський\" birthDate=\"09.09.1769\"/>";

        assertEquals(expected, actual);


        actual = new XML(xml)
                .joinFields( "surname","birthDate", "surnameAndDate")
                .getXml();
        expected = "<person name=\"Іван\" surnameAndDate=\"Котляревський 09.09.1769\"/>";

        assertEquals(expected, actual);
    }

    @Test
    void getField() {
        String xml = getXML();

        String actual = new XML(xml).getField("name", Action.ALL_ROW);
        String expected = " name=\"Іван\"";

        assertEquals(expected, actual);


        actual = new XML(xml).getField("surname", Action.VALUE);
        expected = "Котляревський";

        assertEquals(expected, actual);


        actual = new XML(xml).getField("birthDate", Action.KEY);
        expected = "birthDate";

        assertEquals(expected, actual);
    }

    String getXML(){
        return "<person name=\"Іван\" surname=\"Котляревський\" birthDate=\"09.09.1769\"/>";
    }
}