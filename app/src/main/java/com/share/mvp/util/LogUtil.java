package com.share.mvp.util;

import android.util.Log;

/**
 * print log
 */
public class LogUtil
{

    private static final String LOG_PREFIX = "log_";
    private static final int LOG_PREFIX_LENGTH = LOG_PREFIX.length();
    private static final int MAX_LOG_TAG_LENGTH = 23;

    public static boolean LOGGING_ENABLED = true;//配置是否打印

    public static String makeLogTag(String str)
    {
        if (str.length() > MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH)
        {
            return LOG_PREFIX + str.substring(0, MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH - 1);
        }
        return LOG_PREFIX + str;
    }

    /**
     * Don't use this when obfuscating class names!
     */
    public static String makeLogTag(Class cls)
    {
        return makeLogTag(cls.getSimpleName());
    }

    public static void d(final String tag, String message)
    {
        if (LOGGING_ENABLED)
        {
            Log.d(tag, message);
            //Logger.d(tag, message);
        }
    }

    public static void d(final String tag, String message, Throwable cause)
    {
        if (LOGGING_ENABLED)
        {
            Log.d(tag, message, cause);
        }
    }

    public static void v(final String tag, String message)
    {
        if (LOGGING_ENABLED)
        {
            Log.v(tag, message);
            //Logger.v(tag, message);
        }
    }

    public static void v(final String tag, String message, Throwable cause)
    {
        if (LOGGING_ENABLED)
        {
            Log.v(tag, message, cause);
        }
    }

    public static void i(final String tag, String message)
    {
        if (LOGGING_ENABLED)
        {
            Log.i(tag, message);
            //Logger.i(tag, message);
        }
    }

    public static void i(final String tag, String message, Throwable cause)
    {
        if (LOGGING_ENABLED)
        {
            Log.i(tag, message, cause);
        }
    }

    public static void w(final String tag, String message)
    {
        if (LOGGING_ENABLED)
        {
            Log.w(tag, message);
            //Logger.w(tag, message);
        }
    }

    public static void w(final String tag, String message, Throwable cause)
    {
        if (LOGGING_ENABLED)
        {
            Log.w(tag, message, cause);
        }
    }

    public static void e(final String tag, String message)
    {
        if (LOGGING_ENABLED)
        {
            Log.e(tag, message);
            //Logger.e(tag, message);
        }
    }

    public static void e(final String tag, String message, Throwable cause)
    {
        if (LOGGING_ENABLED)
        {
            Log.e(tag, message, cause);
            //Logger.e(tag, cause, message);
        }
    }

    private LogUtil()
    {
    }

}
