package eventbu.sun.com.library;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import eventbu.sun.com.library.anotation.Subscribe;
import eventbu.sun.com.library.core.MethodManager;

public class EventBus {
    //不允许线程内部的缓存,轻量级
    private static volatile EventBus eventBus;
    //用来保存eventbus的订阅方法 key(Activity,Fragment) value为多个方法的集合
    //MapActivity有多少个方法
    private Map<Object, List<MethodManager>> cacheMap;


    public static EventBus getDefault() {
        if (eventBus == null) {
            synchronized (EventBus.class) {
                if (eventBus == null)
                    eventBus = new EventBus();
            }
        }
        return eventBus;
    }

    private EventBus() {
        cacheMap = new HashMap<>();
    }

    //接收
    //找到注册的所有带方法的加到map
    public void register(Object getter) {
        List<MethodManager> methodLists = cacheMap.get(getter);
        if (methodLists == null) {
            methodLists = findAnnotationMethod(getter);
            cacheMap.put(getter,methodLists);
        }
    }

    /**
     * 获取Activity 所有订阅方法
     *
     * @param getter
     * @return
     */
    private List<MethodManager> findAnnotationMethod(Object getter) {
        List<MethodManager> methodLists = new ArrayList<>();
        //反射获取
        Class classz = getter.getClass();
        //获取当前类的方法
        Method[] methods = classz.getMethods();
        while (classz != null) {
            String className = classz.getName();
            if (className.startsWith("java.") || className.startsWith("javax.") || className.startsWith("android."))
                break;
            for (Method method : methods) {
                //拿到自定义的注解
                Subscribe subscribe = method.getAnnotation(Subscribe.class);
                if (subscribe == null)
                    continue;

                //方法上的校验
                Type returnType = method.getGenericReturnType();
                if (!"void".equals(returnType.toString()))
                    throw new RuntimeException(method.getName() + "返回值必须是void");
                //获取方法的参数
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length != 1) {
                    throw new RuntimeException(method.getName() + "参数只能有一个");
                }

                MethodManager methodManager = new MethodManager(parameterTypes[0], subscribe.threadMode(), method);
                methodLists.add(methodManager);
            }
            //不断找出父类的订阅方法直为null
            classz=classz.getSuperclass();
        }


        return methodLists;
    }
    //发送
    public void post(Object setter) {
        Set<Object> set = cacheMap.keySet();

        //获取每个Activity的方法集合
        for (Object getter : set) {
            List<MethodManager> methodManagers = cacheMap.get(getter);
            if (methodManagers!=null){
                for (MethodManager method : methodManagers) {
                    if (method.getType().isAssignableFrom(setter.getClass())){
                        invoke(method,getter,setter);
                    }
                }
            }
        }
    }

    /**
     * 执行订阅方法
     * @param method 要初始化的方法
     * @param getter 某个类
     * @param setter 参数
     */
    private void invoke(MethodManager method, Object getter, Object setter) {
        Method exect=method.getMethod();
        try {
            exect.invoke(getter,setter);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void unregister(Object getter) {
        cacheMap.remove(getter);
    }
}
