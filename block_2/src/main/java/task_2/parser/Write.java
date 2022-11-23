package task_2.parser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Write {
    /**
     * Method take list of elements which implement {@link ToJSON}
     * and write it to file in "JSON" format.
     * <p>
     * @param file File for writing.
     * @param list List of element.
     * @throws IOException
     */
    public static boolean JSON(File file, List<ToJSON> list) throws IOException {
        file.delete();

        try(FileWriter fileWriter = new FileWriter(file, true)) {
            if(list == null) {
                throw new IllegalArgumentException("Input list = \"null\"");
            } else {
                fileWriter.write("[\n");

                try {
                    for(int i = 0; i < list.size(); i++) {
                        if(i == list.size()-1) {
                            fileWriter.write(String.format("%s\n", list.get(i).toJSON()));
                        }else {
                            fileWriter.write(String.format("%s,\n", list.get(i).toJSON()));
                        }
                    }
                } catch(IOException e) {
                    throw new IOException(e.getMessage());
                }
                fileWriter.write("]");
            }
        }
        return true;
    }

    /**
     * Method take list of elements which implement {@link ToXML}
     * and write it to file in "XML" format.
     * <p>
     * @param file File for writing.
     * @param list List of element.
     * @throws IOException
     */
    public static boolean XML(File file, List<ToXML> list) throws IOException {
        file.delete();

        try(FileWriter fileWriter = new FileWriter(file, true)) {
            if(list == null) {
                throw new IllegalArgumentException("Input list = \"null\"");
            } else {
                String tagName = list.get(0).getClass().getSimpleName().toLowerCase(Locale.ROOT);

                fileWriter.write(String.format("<%ss>\n", tagName));

                list.forEach(violation -> {
                    try {
                        fileWriter.write(String.format("\t%s\n", violation.toXML()));
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                });
                fileWriter.write(String.format("</%ss>\n", tagName));
            }
        }
        return true;
    }
    private Write() {}
}
