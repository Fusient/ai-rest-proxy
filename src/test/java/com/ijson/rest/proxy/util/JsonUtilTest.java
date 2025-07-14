package com.ijson.rest.proxy.util;

import lombok.Data;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * JsonUtil单元测试
 */
public class JsonUtilTest {

    @Test
    public void testToJson() {
        TestObject obj = new TestObject();
        obj.setName("测试");
        obj.setAge(25);
        
        String json = JsonUtil.toJson(obj);
        
        assertNotNull(json);
        assertTrue(json.contains("\"name\":\"测试\""));
        assertTrue(json.contains("\"age\":25"));
    }

    @Test
    public void testFromJson() {
        String json = "{\"name\":\"测试\",\"age\":25}";
        
        TestObject obj = JsonUtil.fromJson(json, TestObject.class);
        
        assertNotNull(obj);
        assertEquals("测试", obj.getName());
        assertEquals(Integer.valueOf(25), obj.getAge());
    }

    @Test
    public void testToJsonWithNull() {
        String json = JsonUtil.toJson(null);
        assertEquals("null", json);
    }

    @Test
    public void testToJsonWithNullField() {
        TestObject obj = new TestObject();
        obj.setName("测试");
        // age为null
        
        String json = JsonUtil.toJsonWithNull(obj);
        
        assertNotNull(json);
        assertTrue(json.contains("\"name\":\"测试\""));
        assertTrue(json.contains("\"age\":null"));
    }

    @Test
    public void testToPrettyJson() {
        TestObject obj = new TestObject();
        obj.setName("测试");
        obj.setAge(25);
        
        String json = JsonUtil.toPrettyJson(obj);
        
        assertNotNull(json);
        assertTrue(json.contains("\"name\": \"测试\""));
        assertTrue(json.contains("\"age\": 25"));
        // 检查是否有格式化（包含换行符）
        assertTrue(json.contains("\n"));
    }

    @Test
    public void testFromJsonWithInvalidJson() {
        try {
            JsonUtil.fromJson("invalid json", TestObject.class);
            fail("应该抛出异常");
        } catch (Exception e) {
            // 预期的异常
        }
    }

    @Test
    public void testFromJsonWithNullJson() {
        TestObject obj = JsonUtil.fromJson(null, TestObject.class);
        assertNull(obj);
    }

    @Data
    public static class TestObject {
        private String name;
        private Integer age;
    }
}
