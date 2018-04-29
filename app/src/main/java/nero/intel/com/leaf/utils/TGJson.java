package nero.intel.com.leaf.utils;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import nero.intel.com.leaf.entity.Result;

/**
 * Created by ny on 2018/3/7.
 */

public class TGJson {
    static ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }
    public static Result fromJson(String json, Class clazz) {
        Gson gson = new Gson();
        Type objectType = type(Result.class, clazz);
        return gson.fromJson(json, objectType);
    }
}
