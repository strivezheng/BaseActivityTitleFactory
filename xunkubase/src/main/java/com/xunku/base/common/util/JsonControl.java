package com.xunku.base.common.util;



import com.google.gson.JsonParseException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * json处理
 */
public class JsonControl {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * JSON转换为bean
     */
    public static Object jsonToBean(String jsonStr, Object beanModel) {

        try {
            objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            beanModel = objectMapper.readValue(jsonStr, beanModel.getClass());
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return beanModel;
    }

    public static <T> List<T> getObjectsFromJson(String in, Class<T> clsT) {
        JsonParser parser;
        try {
            objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            parser = objectMapper.getJsonFactory().createJsonParser(in);
            JsonNode nodes = parser.readValueAsTree();
            List<T> list = new ArrayList<T>(nodes.size());
            for (JsonNode node : nodes) {
                list.add(objectMapper.readValue(node, clsT));
            }
            return list;
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<T>();
    }

    /** 把HashMap转换成json */
    public static String mapToJson(Map<String, String> map) {
        StringBuffer strBuf = new StringBuffer("{");
        for (String key : map.keySet()) {
            strBuf.append("'");
            strBuf.append(key);
            strBuf.append("':");
            strBuf.append("'");
            strBuf.append(map.get(key));
            strBuf.append("',");
        }
        String retStr = strBuf.toString();
        retStr = retStr.substring(0, retStr.lastIndexOf(","));
        retStr += "}";
        return retStr;
    }

}
