package com.biubiu.myblog;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.biubiu.myblog.dao.DictInfoDao;
import com.biubiu.myblog.entity.DictInfo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Collections;

@SpringBootTest
class MyBlogApplicationTests {

    @Resource
    DictInfoDao dictInfoDao;

    @Test
    void contextLoads() {
        DictInfo dictInfo = new DictInfo();
        dictInfo.setName("IMG_ACCESS_PATH");
        dictInfo.setValue("/img/*");
        dictInfo.setInfo("访问图片时的路径");
        dictInfo.setCode("/blog/uploadImg");

        int i = dictInfoDao.addDictInfo(dictInfo);

        System.out.println(i);
    }

    @Test
    public void test() {
        String msg = "heHHkk;;KK  百晓吧";
        System.out.println(msg.toLowerCase());
    }

    @Test
    public void codeGenerator() {
        String url = "jdbc:mysql://127.0.0.1:3306/blog?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false";
        String username="root";
        String password= "root";
        String xmlPath = "D:\\scode\\github\\my-blog\\myblog\\src\\main\\resources";
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("baijq") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D://"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.biubiu.myblog") // 设置父包名
                            .moduleName("blog") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, xmlPath)); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("t_user", "t_tag", "t_blog") // 设置需要生成的表名
                            .addTablePrefix("t_", "c_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
