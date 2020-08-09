package com.shenfeng.autoconfig;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
/**
 *  流式任务类型：业务实现两个接口：抓取(fetchData)和处理(processData)数据
 *    a.流式处理数据只有fetchData方法的返回值为null或集合长度为空时，作业才停止抓取，否则作业将一直运行下去；
 *    b.非流式处理数据则只会在每次作业执行过程中执行一次fetchData方法和processData方法，随即完成本次作业
 */
public @interface ElasticDataflowJob {
    String jobName() default "";

    String cron() default "";

    int shardingTotalCount() default 1;

    boolean overwrite() default false;

    boolean streamProcess() default false;

}
