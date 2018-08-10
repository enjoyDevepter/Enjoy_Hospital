package me.jessyan.mvparms.demo.util;

import com.jess.arms.integration.cache.Cache;
import com.jess.arms.integration.cache.IntelligentCache;
import com.jess.arms.utils.ArmsUtils;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import me.jessyan.mvparms.demo.BuildConfig;

public class CacheUtil {

    private static Cache<String, Object> extras;

    static {
        extras = ArmsUtils.obtainAppComponentFromContext(ArmsUtils.getContext()).extras();
    }

    public static void saveCache(String key,Object value){
        extras.put(key,value);
    }

    /**自动以常量的方式存储，适合生命周期等同于应用的属性*/
    public static void saveConstant(String key,Object value){
        extras.put(IntelligentCache.KEY_KEEP+key,value);
    }

    public static void clearConstant(String key){
        extras.remove(IntelligentCache.KEY_KEEP+key);
    }

    public static Object getCaChe(String key){
        return extras.get(key);
    }

    public static Object getConstant(String key){
        return extras.get(IntelligentCache.KEY_KEEP + key);
    }

    /**自动获取属性，优先获取全局常量，如果没有获取到才获取局部常量*/
    public static Object getExtras(String key){
        Object constant = getConstant(key);
        if(null == constant){
            return getCaChe(key);
        }
        return constant;
    }
}
