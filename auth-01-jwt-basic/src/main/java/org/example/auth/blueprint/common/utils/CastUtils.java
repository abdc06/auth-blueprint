package org.example.auth.blueprint.common.utils;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

public class CastUtils {

    public static String obj2Str(Object obj) {
        if (ObjectUtils.isEmpty(obj)) {
            return null;
        } else {
            return StringUtils.strip(obj.toString());
        }
    }

    public static int obj2Int(Object obj) {
        if (ObjectUtils.isEmpty(obj)) {
            return 0;
        } else {
            try {
                return Integer.parseInt(obj.toString());
            } catch (NumberFormatException e) {
                return 0;
            }
        }
    }

    public static long obj2Long(Object obj) {
        if (ObjectUtils.isEmpty(obj)) {
            return 0L;
        } else {
            try {
                return Long.parseLong(obj.toString());
            } catch (NumberFormatException e) {
                return 0L;
            }
        }
    }
    
}
