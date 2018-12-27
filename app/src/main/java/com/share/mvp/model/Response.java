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

    private int status;
    private String msg;
    private T result;

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
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
        return result;
    }

    public void setResult(T result)
    {
        this.result = result;
    }

    public boolean isSuccess()
    {
        return 1 == getStatus();
    }

    /**
     * token已失效
     *
     * @return
     */
    public boolean isToken()
    {
        return -2 == getStatus();
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
                "\"status\":\"" + status + '\"' +
                ", \"msg\":\"" + msg + '\"' +
                ", \"result\":" + gson.toJson(result) +
                '}';
    }
}
