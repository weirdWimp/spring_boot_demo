package com.example.springboot.demo.util;

import com.example.springboot.demo.entity.UserInfo;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;

/**
 * mybatis 3.5.1 后可实现 ProviderMethodResolver，mapper方法同名会被自动解析
 */
public class UserSqlProvider {

    public static String getProductById(final String productId) {
        // 定义一个匿名类，为SQL类的子类，{} 为类中非静态代码块，用来进行实例初始化（匿名类没有名称，无法定义构造器）
        return new SQL() {{
            SELECT("*");
            FROM("product");
            if (productId != null) {
                WHERE("product_id = #{productId}");
            }
        }}.toString();
    }

    public static String getProductByName(String productName) {
        return new SQL()
                .SELECT("*")
                .FROM("product")
                .WHERE("product_name = #{productName}")
                .toString();
    }

    /**
     * 动态条件（注意参数需要使用 final 修饰，以便匿名内部类对它们进行访问）
     */
    public static String selectPersonLike(final String id, final String firstName, final String lastName) {
        return new SQL() {{
            SELECT("P.ID, P.USERNAME, P.PASSWORD, P.FIRST_NAME, P.LAST_NAME");
            FROM("PERSON P");
            if (id != null) {
                WHERE("P.ID like #{id}");
            }
            if (firstName != null) {
                WHERE("P.FIRST_NAME like #{firstName}");
            }
            if (lastName != null) {
                WHERE("P.LAST_NAME like #{lastName}");
            }
            ORDER_BY("P.LAST_NAME");
        }}.toString();
    }

    public static String insertToYwtInfoA(final List<Map<String, Object>> ywtInfoList) {
        return new SQL() {{
            INSERT_INTO("ywt_eac_inf_b");
            INTO_COLUMNS("uid", "card_no", "clt_cid", "cd_flg", "ywt_name");
            Map<String, Object> firstRow = ywtInfoList.get(0);
            // INTO_VALUES(firstRow.get("uid"), firstRow.get("uid"), firstRow.get("uid"), firstRow.get("uid"), firstRow.get("uid"));
            for (int i = 1; i < ywtInfoList.size(); i++) {
                ADD_ROW();
                INTO_VALUES("#{uid}", "#{card_no}", "#{clt_cid}", "#{cd_flg}", "#{ywt_name}");
            }
        }}.toString();
    }

    public static String insertToUserInfo(List<UserInfo> userInfos) {
        return new SQL() {{
            INSERT_INTO("user_info");
            INTO_COLUMNS("uid", "name");
            for (UserInfo userInfo : userInfos) {
                ADD_ROW();
                INTO_VALUES(userInfo.getUid(), userInfo.getName());
            }
        }}.toString();
    }


    public static void main(String[] args) {
        System.out.println(selectPersonLike("id001", "firstName", "lastName"));
        System.out.println("args = " + args);
    }
}
