package cn.nqy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) // 作用域在类上
@Retention(RetentionPolicy.SOURCE) // 编译器生效
public @interface NqyRouter {

    String path();
    String group() default ""; // app\order\personal 针对每个模块都是group

}
