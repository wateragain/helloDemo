package com.example.demo;

import com.example.demo.feign.TestClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class DemoApplicationTests {

    @MockBean
    private TestClient testClient;

	@Test
	void contextLoads() {
	    when(testClient.test(any())).thenReturn("aaa");
        String test = testClient.test("bbb");
        //这里的判断可以导入junit等包做Assert
        Assert.isTrue(test.equals("bbb"));
    }

}
