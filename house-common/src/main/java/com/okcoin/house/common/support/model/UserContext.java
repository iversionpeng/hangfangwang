package com.okcoin.house.common.support.model;

/**
 * @author: liupeng
 * @date: 2019/5/9 11:01
 * @description(功能描述):
 */
public class UserContext {
    private static ThreadLocal<SecurityUser> USER_HOLDER = new ThreadLocal<>();

    public static SecurityUser getUserHolder() {
        return USER_HOLDER.get();
    }

    public static void setUserHolder(SecurityUser userHolder) {
        USER_HOLDER.set(userHolder);
    }

    public static void clearUserHolder() {
        USER_HOLDER.remove();
    }
}
