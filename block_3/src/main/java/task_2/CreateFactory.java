package task_2;

import lombok.SneakyThrows;

import java.io.FileReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

public class CreateFactory {

    @SneakyThrows
    public static<T> T loadFromProperty(Class<T> clazz, Path propertiesPath){
        Properties properties = new Properties();
        FileReader fileReader = new FileReader(propertiesPath.toFile());
        properties.load(fileReader);
        fileReader.close();

        ClassService<T> classService = new ClassService<>(clazz, properties);

        properties.stringPropertyNames().forEach(classService::setValue);

        return classService.getInstance();
    }

    private static class ClassService<T>{
        private final Class<T> clazz;
        private final Properties properties;
        private final T instance;

        public void setValue(String fieldName) {
            setThroughMethod(fieldName);
//          or
//          setThroughField(fieldName);
        }

        public T getInstance() {
            return instance;
        }

        public ClassService(Class<T> clazz, Properties properties) throws IllegalAccessException {
            this.clazz = clazz;
            this.properties = properties;
            this.instance = createInstance();
        }

        private void setThroughField(String fieldName){
            Field field = findFieldByName(fieldName);
            T v = selectType(field, properties.getProperty(fieldName));
          try{
                field.setAccessible(true);
                field.set(instance, v);
          } catch(IllegalAccessException e){
                throw new  IllegalArgumentException(String.format("Field with name \"%s\", was not added", v.getClass().getName()));
          }
        }

        private void setThroughMethod(String fieldName){
            Field field = findFieldByName(fieldName);
            String nameField = field.getName();
            String methodName = "set" + nameField.substring(0,1).toUpperCase(Locale.ROOT) + nameField.substring(1);
            Method method = findMethodByName(methodName);
            T v = selectType(field, properties.getProperty(fieldName));
            try{
                method.invoke(instance, v);
            } catch(IllegalAccessException |InvocationTargetException e){
                throw new  IllegalArgumentException(String.format("Method with name \"%s\", was not started ", v.getClass().getName()));
            }
        }

        /**
         * Convert value type to type of field
         * For example (String = "1", Field.getType() = java.lang.Integer) return Integer = 1
         */

        @SuppressWarnings("All")
        private <R> R selectType(Field field, String value) {
            switch(field.getType().getTypeName()){
                case "java.lang.String" : {
                        return (R) value;
                }
                case "java.time.Instant" : {
                    Property property = field.getAnnotation(Property.class);
                    if(property != null) {
                        String format = property.format();
                        return (R) parseDate(value, format);
                    } else {
                        return (R) parseDate(value, "dd.MM.yyyy HH:mm");
                    }
                }
                case "java.lang.Integer" : {
                    Integer integer = Integer.parseInt(value);
                    return (R) integer;
                }
                case "int" : {
                    Integer integer = Integer.parseInt(value);
                    return (R) integer;
                }
                default: throw new IllegalArgumentException(String.format("Field type is not correct -> %s", field.getType().getTypeName()));
            }
        }

        private Instant parseDate(String date, String format) {
            try {
                final String NEW_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

                String newDateString;

                SimpleDateFormat sdf = new SimpleDateFormat(format);

                Date d = sdf.parse(date);
                sdf.applyPattern(NEW_FORMAT);
                newDateString = sdf.format(d);

                return Instant.parse(newDateString+".Z");
            } catch(ParseException e){
                throw new IllegalArgumentException("Date is not correct: " + date);
            }
        }

        private Method findMethodByName(String name){
            Method[] methods = instance.getClass().getDeclaredMethods();
            for(Method method : methods) {
                if(method.getName().equals(name)){
                    return method;
                }
            }
            throw new IllegalArgumentException(String.format("Method with name \"%s\" in class not found ", name));
        }

        private Field findFieldByName(String name) {
            Field[] fields = instance.getClass().getDeclaredFields();
            for(Field field : fields) {
                Property property = field.getAnnotation(Property.class);
                if(property != null && name.equals(property.name())){
                    return field;
                }
            }
            for(Field field : fields) {
                if(field.getName().equals(name)){
                    return field;
                }
            }
            throw new IllegalArgumentException(String.format("Field with name \"%s\" in class not found ", name));
        }

        @SuppressWarnings("All")
        private T createInstance() throws IllegalAccessException {
            try {
                Constructor<?>[] constructors = clazz.getConstructors();
                for(Constructor<?> constructor : constructors) {
                    if(constructor.getParameters().length == 0) {
                        return (T) constructor.newInstance();
                    }
                }
                throw new IllegalAccessException("Class doesn't have empty constructor");
            } catch(InvocationTargetException | InstantiationException e){
                throw new IllegalAccessException("Class doesn't have empty constructor");
            }
        }
    }

    private CreateFactory() {}
}
