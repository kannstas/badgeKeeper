package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.SimpleDateFormat;

public class JsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static String toJson(Object object) throws JsonProcessingException {
        objectMapper.setDateFormat(dateFormat);
        return objectMapper.writeValueAsString(object);
    }
}