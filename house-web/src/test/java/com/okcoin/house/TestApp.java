package com.okcoin.house;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: liupeng
 * @date: 2019/5/4 23:24
 * @description(功能描述):
 */
//@SpringBootTest
//@RunWith(SpringRunner.class)
public class TestApp {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestApp.class);

    @Test
    public void testLoadProperties() {
        InputStream resourceAsStream = this.getClass().getResourceAsStream("/mybatis-config.xml");
//        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("mybatis-config.xml");
//        InputStream resourceAsStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = build.openSession();
        com.okcoin.house.UserMapper mapper = sqlSession.getMapper(com.okcoin.house.UserMapper.class);
        List<com.okcoin.house.User> users = mapper.selectByPrimaryKey();
        Assert.assertNotNull("is null", users);
//        users.forEach(x -> System.out.println(x.getName()));
    }

    /**
     * 功能描述: 测试反射赋值
     *
     * @param:
     * @return:
     * @author: liupeng
     * @date: 2019/5/21 14:06
     */
    @Test
    public void testReflex() {
        Configuration configuration = new Configuration();
        configuration.getMap().put("2", "b");
        configuration.setNum(2);
        System.out.println(configuration);
        Class<? extends Configuration> aClass = configuration.getClass();
    }

    /**
     * 功能描述:
     *
     * @param: 测试io读取乱码
     * @return:
     * @author: liupeng
     * @date: 2019/5/22 10:29
     */
    @Test
    public void testIo() throws IOException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("static/pod-scale-alarm.html");
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer buffer = new StringBuffer();
        String line;
        while ((line = fileReader.readLine()) != null) {
            buffer.append(line);
        }
        inputStream.close();
        fileReader.close();
        LOGGER.error("###########ceshi={}", buffer.toString());
    }
}

class Configuration {
    protected String databaseId = "1";
    protected Integer num;
    protected Class<?> configurationFactory;
    protected final Map<String, String> map = new HashMap();

    public Configuration() {
        num = 1;
        map.put("1", "a");
    }

    public String getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(String databaseId) {
        this.databaseId = databaseId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Class<?> getConfigurationFactory() {
        return configurationFactory;
    }

    public void setConfigurationFactory(Class<?> configurationFactory) {
        this.configurationFactory = configurationFactory;
    }

    public Map getMap() {
        return map;
    }
}
