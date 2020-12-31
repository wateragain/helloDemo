package com.example.demo.config;

import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.util.IOUtils;
import com.alibaba.fastjson.util.TypeUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 不使用fastjson是因为
 * 保持原类属性中的排序-实现起来麻烦
 * 而且每个版本都在修BUG
 */
@Deprecated
//@Configuration
public class FastJsonWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        //自定义配置

        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.BrowserSecure, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullListAsEmpty, SerializerFeature.PrettyFormat);
        fastJsonConfig.setFeatures(Feature.SafeMode);
        converter.setFastJsonConfig(fastJsonConfig);
        //保持原类属性中的排序-getDeclaredFields


        //保持原字段大小写
        TypeUtils.compatibleWithFieldName = true;

        //SpringBoot 2.0.1版本中加载WebMvcConfigurer的顺序发生了变动，故需使用converters.add(0, converter);指定FastJsonHttpMessageConverter在converters内的顺序，否则在SpringBoot 2.0.1及之后的版本中将优先使用Jackson处理
        converters.add(0, converter);
    }
}
