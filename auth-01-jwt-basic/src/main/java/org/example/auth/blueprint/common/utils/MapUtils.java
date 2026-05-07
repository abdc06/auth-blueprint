package org.example.auth.blueprint.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.auth.blueprint.common.model.DataMap;

import java.util.ArrayList;
import java.util.List;

public class MapUtils {

    private static final ObjectMapper MAPPER = SpringContextUtils.getBean(ObjectMapper.class);

    public static <T> DataMap convert(T t) {
        DataMap map = MAPPER.convertValue(t, new TypeReference<>() {});
        map.pagination();
        return map;
    }

    public static <T, B> B convert(T t, TypeReference<B> typeReference) {
        return MAPPER.convertValue(t, typeReference);
    }

    public static <T> T convert(DataMap map, TypeReference<T> typeReference) {
        return MAPPER.convertValue(map, typeReference);
    }

    public static <T> T convert(DataMap map) {
        return MAPPER.convertValue(map, new TypeReference<>() {});
    }

    public static <T, B> List<B> convert(List<T> tList) {
        List<B> bList = new ArrayList<>();

        if (!tList.isEmpty()) {
            for (T t : tList) {
                if (t instanceof DataMap) {
                    bList.add(convert((DataMap) t, new TypeReference<>() {}));
                } else {
                    bList.add((B) convert(t));
                }
            }
        }
        return bList;
    }
}
