package com.okcoin.house;

import org.junit.Test;

/**
 * @author: liupeng
 * @date: 2019/5/4 23:24
 * @description(功能描述):
 */
public class TestApp {
    @Test
    public void testAop() {
        try {
//            int i = 1 / 0;
            try {
                System.out.print(2);
                int a = 1 / 0;
            } finally {
                System.out.print(3);
            }
            System.out.print(4);
        } catch (Exception e) {
            System.out.print(5);
        }
    }
}
