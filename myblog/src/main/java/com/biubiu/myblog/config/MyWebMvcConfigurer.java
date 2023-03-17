package com.biubiu.myblog.config;

import com.biubiu.myblog.constant.Constant;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 资源映射路径配置
 *
 * @author wbbaijq
 */
//@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {

//    @Resource
    private DictInfoCache dictInfoCache;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //资源映射路径
        String resourceHandler = dictInfoCache.getValue(Constant.IMG_ACCESS_PATH);
        //资源存储路径
        String resourceLocations = "file:" + dictInfoCache.getValue(Constant.IMG_LOCAL_LOCATION);

        registry.addResourceHandler(resourceHandler)
                .addResourceLocations(resourceLocations);
    }
}
