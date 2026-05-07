package org.example.auth.blueprint.common.model;

import org.example.auth.blueprint.common.utils.CamelUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedHashMap;

public class DataMap extends LinkedHashMap<String, Object> implements Serializable {

    @Serial
    private static final long serialVersionUID = -1439901972906783909L;

    @Override
    public Object put(String key, Object value) {
        if ("offset".equalsIgnoreCase(key) || "pageSize".equalsIgnoreCase(key)) {
            return super.put(key, value);
        } else {
            return super.put(CamelUtils.convert2CamelCase(key), value);
        }
    }

    public void pagination() {
        Object page = this.get("page");
        Object size = this.get("size");

        if (page != null && size != null) {
            int pageValue = Integer.parseInt(page.toString());
            int sizeValue = Integer.parseInt(size.toString());

            this.put("page", pageValue);
            this.put("size", sizeValue);

            this.put("pageSize", sizeValue);
            this.put("offset", (pageValue - 1) * sizeValue);
        }
    }
    
    public Long getLong(String key) {
        Object value = this.get(key);
        
        if (value == null) {
            return null;
        }
        
        if (value instanceof Long) {
            return (Long) value;
        } 
        
        try {
            return Long.parseLong(value.toString());
        } catch (NumberFormatException e) {
            System.err.println("WARN: Cannot parse value for key " + key + " to Long. Value: " + value);
            return null; 
        }
    }
    
    /**
     * 특정 키에 해당하는 값을 String 타입으로 가져옵니다.
     * 값이 null이거나 존재하지 않으면 null을 반환합니다.
     * * @param key 가져올 값의 키
     * @return String 타입의 값 또는 null
     */
    public String getString(String key) {
        Object value = get(key);
        
        // 1. 값이 null인지 확인
        if (value == null) {
            return null;
        }
        
        // 2. 값의 타입이 String이면 바로 반환
        if (value instanceof String) {
            return (String) value;
        }
        
        // 3. 그 외 타입(숫자, Boolean 등)은 String으로 변환 후 반환
        return String.valueOf(value);
    }
    
    /**
     * 특정 키에 해당하는 값을 String 타입으로 가져옵니다.
     * 값이 null이거나 존재하지 않으면 기본값을 반환합니다.
     * * @param key 가져올 값의 키
     * @param defaultValue 값이 없을 경우 반환할 기본값
     * @return String 타입의 값 또는 기본값
     */
    public String getString(String key, String defaultValue) {
        String result = getString(key);
        
        // getString(key) 결과가 null이면 defaultValue 반환
        if (result == null) {
            return defaultValue;
        }
        
        return result;
    }


}
