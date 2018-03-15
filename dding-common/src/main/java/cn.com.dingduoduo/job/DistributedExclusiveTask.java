package cn.com.dingduoduo.job;

import java.lang.annotation.*;

//@Inherited很重要，可以保证cglib enhanced的timer子类可以保留annotation
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface DistributedExclusiveTask {
	public String value();
}
