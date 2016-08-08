package com.gypsophila.commonlib.net;

import java.io.Serializable;

/**
 * Created by Gypsophila on 2016/7/31.
 * 传递接口所需键值对
 */
public class RequestParameter implements Serializable, Comparable<Object> {

    private static final long serialVersionUID = 1274906854152052510L;

    private String name;
    private String value;

    public RequestParameter(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public int compareTo(Object another) {
        int compared;
        final RequestParameter parameter = (RequestParameter) another;
        compared = name.compareTo(parameter.name);
        if (compared == 0) {
            compared = value.compareTo(parameter.value);
        }
        return compared;
    }

    public boolean equals(Object o) {
        if(null == o) return false;
        if(this == o) return true;
        if (o instanceof RequestParameter) {
            final RequestParameter parameter = (RequestParameter) o;
            return name.equals(parameter.name) && value.equals(parameter.value);
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
