package eventbu.sun.com.library.anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import eventbu.sun.com.library.mode.ThreadMode;

@Target(ElementType.METHOD)//定义在方法之上
@Retention(RetentionPolicy.RUNTIME)//生命周期运行是
public @interface Subscribe {
    ThreadMode threadMode() default ThreadMode.POSTING;
}
