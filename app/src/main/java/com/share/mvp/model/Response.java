package com.share.mvp.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**

 */
public class Response<T>
{

    /**
     * status : 1
     * result :   []
     * msg : success
     */

    private int code;
    private String msg;
    private T data;

    public int getStatus()
    {
        return code;
    }

    public void setStatus(int status)
    {
        this.code = status;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public T getResult()
    {
        return data;
    }

    public void setResult(T result)
    {
        this.data = result;
    }

    public boolean isSuccess()
    {
        return 200 == getStatus();
    }



    @Override
    public String toString()
    {
        Gson gson = new GsonBuilder().
                registerTypeAdapter(Double.class, new JsonSerializer<Double>()
                {

                    @Override
                    public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context)
                    {
                        if (src == src.longValue())
                            return new JsonPrimitive(src.longValue());
                        return new JsonPrimitive(src);
                    }
                }).create();
        return "Response--->{" +
                "\"status\":\"" + code + '\"' +
                ", \"msg\":\"" + msg + '\"' +
                ", \"result\":" + gson.toJson(data) +
                '}';
    }
}
