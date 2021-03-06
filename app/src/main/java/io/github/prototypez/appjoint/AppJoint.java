package io.github.prototypez.appjoint;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppJoint {

    private List<Application> moduleApplications = new ArrayList<>();

    private Map<Class, Class> routersMap = new HashMap<>();

    private Map<Class, Object> routerInstanceMap = new HashMap<>();

    private AppJoint() {
    }

    public static synchronized <T> T service(Class<T> routerType) {
        T requiredRouter = null;
        if (!get().routerInstanceMap.containsKey(routerType)) {
            try {
                requiredRouter = (T) get().routersMap.get(routerType).newInstance();
                get().routerInstanceMap.put(routerType, requiredRouter);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } else {
            requiredRouter = (T) get().routerInstanceMap.get(routerType);
        }
        return requiredRouter;
    }

    public List<Application> moduleApplications() {
        return moduleApplications;
    }

    public Map<Class, Class> routersMap() {
        return routersMap;
    }

    public static AppJoint get() {
        return SingletonHolder.INSTANCE;
    }

    static class SingletonHolder {
        static AppJoint INSTANCE = new AppJoint();
    }
}