package nero.intel.com.leaf.entity;

import android.content.Intent;

import java.io.Serializable;

/**
 * Created by ny on 2018/3/6.
 */

public class Result<T> implements Serializable {
    private String msg;
    private T data;
    private Integer code;


    public Result(String msg, T data, Integer code) {
        this.msg = msg;
        this.data = data;
        code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        code = code;
    }

    @Override
    public String toString() {
        return "Result{" +
                "msg='" + msg + '\'' +
                ", data=" + data +
                ", code=" + code +
                '}';
    }
}
