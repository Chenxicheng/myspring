package com.dashwood.myspring.util;

import com.dashwood.myspring.model.student.entity.Student;
import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.*;
import com.google.common.io.Files;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class GuavaTest {

    class User {
        private String id;
        private String name;

        public User(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Test
    public void test1() {
        User user1 = new User("1","q");
        User user2 = new User("1","a");
        User user3 = new User("2","c");

        List<User> list = Lists.newArrayList(user1,user2,user3);
        Map<String, User> map = Maps.uniqueIndex(list, user -> user.getId());
        System.out.println(map);


    }

    @Test
    public void test2() throws IOException {
        File file = new File("C:\\Users\\dashwood\\Downloads\\dengjicaipan_31_20180411173300__2bb65c7d_1536_465c_b141_3a3b36ef064a");
        System.out.println(file.getAbsolutePath());
        System.out.println(file.getCanonicalPath());
        System.out.println(file.getPath());
        System.out.println(Files.getFileExtension(file.getName()));
        if (file.isFile()) {
            System.out.println("yes");
        }
//        byte[] lines = Files.asByteSource(file).read();

        List<String> list =  Files.readLines(file, Charsets.UTF_8);
        for (String line: list) {
            System.out.println(line);
        }
//        System.out.println(new String(lines, Charsets.UTF_8));
//        String s = new String(lines, Charsets.UTF_8);
//        s += "1111111111111";
//        Files.write(s.getBytes(Charsets.UTF_8), file);
    }

    @Test
    public void test3() throws IOException {
        Multiset<String> wordOccurrences = HashMultiset.create(Splitter.on(CharMatcher.WHITESPACE)
                .trimResults()
                .omitEmptyStrings()
                .split(Files.asCharSource(new File("D:"+File.separator+"aa.txt"), Charsets.UTF_8).read()));
        System.out.println(wordOccurrences);
    }

    @Test
    public void test4() throws IOException {
        File file = new File("D:\\地球和地球仪.ppt");

//        System.out.println();
//        List<String> lines = Files.asCharSource(file, Charsets.UTF_8).readLines();
//        String s = Joiner.on("\n").skipNulls().join(lines);
        String writeFileName = "E:\\" + Files.getNameWithoutExtension(file.getName()) + ""
                + new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"."+Files.getFileExtension(file.getName());
        System.out.println(writeFileName);
        File writeFile = new File(writeFileName);
        if (!writeFile.exists()) {
            writeFile.createNewFile();
            Files.move(file, writeFile);
            System.out.println("复制完成");
        } else {
            System.out.println("文件已存在");
        }
    }

    @Test
    public void test5() {
        String s = "dashwood erppc fdjskajlf csfdjdksg de dse erppc sfd dashwood          ";
        Iterable<String> iterable = Splitter.on(" ").trimResults().omitEmptyStrings().split(s);
        Multiset<String> multiset = HashMultiset.create(iterable);

        multiset.elementSet().forEach(key -> {
            System.out.println(key+"count:"+multiset.count(key));
        });

        System.out.println("============================================");

        multiset.setCount("aaaa", 56);
        multiset.elementSet().forEach(key -> {
            System.out.println(key+"count:"+multiset.count(key));
        });

//        System.out.println(iterable);
    }

    @Test
    public void test6() {
        List<Student> list = Lists.newArrayList();
        for(int i=1; i<10; i++) {
            list.add(Student.builder().id(String.valueOf(i)).name("a"+1).age(19).build());
        }

        ImmutableMap<String, Student> immutableMap = Maps.uniqueIndex(list, student -> {
            return student.getId();
        });

        System.out.println(immutableMap);

        immutableMap.forEach((k,student) -> {
            System.out.println(k+":"+student.getName());
        });
    }

    @Test
    public void test7 () {
        List<Map<String, Object>> list = Lists.newArrayList();
        for (int i=1; i<10; i++) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("name", "a"+i);
            map.put("value", i);
            list.add(map);
        }

        ImmutableMap<String, Map<String, Object>> immutableMap = Maps.uniqueIndex(list, map -> {
           return  (String)map.get("name");
        });


    }

    @Test
    public void genFile() {
        File file = new File("E:\\"+123456+".json");
        String s = "{\"id\": \"123\"}";
        try {
            Files.write(s.getBytes(Charsets.UTF_8), file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test8() {
        // 长连接保持30秒
        PoolingHttpClientConnectionManager pollingConnectionManager = new PoolingHttpClientConnectionManager(30, TimeUnit.SECONDS);
        // 总连接数
        pollingConnectionManager.setMaxTotal(1000);
        // 同路由的并发数
        pollingConnectionManager.setDefaultMaxPerRoute(1000);

        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        httpClientBuilder.setConnectionManager(pollingConnectionManager);
        // 重试次数，默认是3次，没有开启
        httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(2, true));
        // 保持长连接配置，需要在头添加Keep-Alive
        httpClientBuilder.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy());

//        RequestConfig.Builder builder = RequestConfig.custom();
//        builder.setConnectionRequestTimeout(200);
//        builder.setConnectTimeout(5000);
//        builder.setSocketTimeout(5000);
//
//        RequestConfig requestConfig = builder.build();
//        httpClientBuilder.setDefaultRequestConfig(requestConfig);

        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.16 Safari/537.36"));
        headers.add(new BasicHeader("Accept-Encoding", "gzip,deflate"));
        headers.add(new BasicHeader("Accept-Language", "zh-CN"));
        headers.add(new BasicHeader("Connection", "Keep-Alive"));
        headers.add(new BasicHeader("Authorization", "Token "+"f665ca09b61c916e05086d1ffc2c50903942e234"));
        httpClientBuilder.setDefaultHeaders(headers);

        HttpClient httpClient = httpClientBuilder.build();

        // httpClient连接配置，底层是配置RequestConfig
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        // 连接超时
        clientHttpRequestFactory.setConnectTimeout(5000);
        // 数据读取超时时间，即SocketTimeout
        clientHttpRequestFactory.setReadTimeout(5000);
        // 连接不够用的等待时间，不宜过长，必须设置，比如连接不够用时，时间过长将是灾难性的
        clientHttpRequestFactory.setConnectionRequestTimeout(200);
        // 缓冲请求数据，默认值是true。通过POST或者PUT大量发送数据时，建议将此属性更改为false，以免耗尽内存。
        // clientHttpRequestFactory.setBufferRequestBody(false);

        StringHttpMessageConverter messageConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));


        List<HttpMessageConverter<?>> list=new ArrayList<HttpMessageConverter<?>>();



        list.add(messageConverter);
//        HttpHeaders headers = new HttpHeaders();
//        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
//        headers.setContentType(type);
//        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
//        headers.add("Authorization", "Token "+"f665ca09b61c916e05086d1ffc2c50903942e234");
//
//
//        HttpEntity<String> formEntity = new HttpEntity<String>(headers);

        RestTemplate restTemplate = new RestTemplate(list);
        restTemplate.setRequestFactory(clientHttpRequestFactory);
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
        ResponseEntity<String> result = restTemplate.getForEntity("http://113.200.12.236:61112/peoplecount/region_now/?name=小峪",  String.class);

        System.out.println(result.getStatusCode()+":"+result.getBody());
    }

    @Test
    public void test9() {
        double a = 30.15;
        double b = 0.15;
        String c = new BigDecimal(String.valueOf(a)).add(new BigDecimal(String.valueOf(b))).toString();
        System.out.println(a+b);
        System.out.println(c);
    }
}
