package com.ijson.rest.proxy.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * MD5工具类单元测试
 */
public class MD5Test {

    @Test
    public void testMD5Encode() {
        String input = "hello world";
        String expected = "5d41402abc4b2a76b9719d911017c592";
        
        String result = MD5.mD5Encode(input);
        
        assertEquals(expected, result);
    }

    @Test
    public void testMD5EncodeWithEmptyString() {
        String input = "";
        String expected = "d41d8cd98f00b204e9800998ecf8427e";
        
        String result = MD5.mD5Encode(input);
        
        assertEquals(expected, result);
    }

    @Test
    public void testMD5EncodeWithNull() {
        String result = MD5.mD5Encode(null);
        
        assertNull(result);
    }

    @Test
    public void testMD5EncodeWithChineseCharacters() {
        String input = "你好世界";
        
        String result = MD5.mD5Encode(input);
        
        assertNotNull(result);
        assertEquals(32, result.length()); // MD5结果应该是32位
        // 验证结果只包含十六进制字符
        assertTrue(result.matches("[a-f0-9]+"));
    }

    @Test
    public void testMD5EncodeConsistency() {
        String input = "test string";
        
        String result1 = MD5.mD5Encode(input);
        String result2 = MD5.mD5Encode(input);
        
        assertEquals(result1, result2);
    }

    @Test
    public void testMD5EncodeDifferentInputs() {
        String input1 = "test1";
        String input2 = "test2";
        
        String result1 = MD5.mD5Encode(input1);
        String result2 = MD5.mD5Encode(input2);
        
        assertNotEquals(result1, result2);
    }
}
