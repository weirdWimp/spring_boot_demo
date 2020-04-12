package com.example.springboot.demo.mapper;

import com.example.springboot.demo.util.UserSqlProvider;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

@Mapper
public interface LearnSqlMapper {

    @Select("select count(*) from product")
    int count();

    @Select("select product_name from product where product_id = #{id}")
    String getNameById(String id);

    @SelectProvider(type = UserSqlProvider.class, method = "getProductById")
    Map<String, Object> getProductById(String productId);

    @Select({"<script>",
            "select * from product where product_id in",
            "   <foreach item='item' index='index' collection='productIds' open='(' separator=',' close=')'>",
            "       #{item}",
            "   </foreach>",
            "</script>"})
    List<Map<String, Object>> getProductByIds(@Param("productIds") List<String> productIds);


    /**
     * MapKey 将返回对象的某个属性作为 map 的 key
     */
    @Select("select * from product limit 0, 100")
    @MapKey(value = "product_id")
    Map<String, Map<String, Object>> getAllProduct();

    /**
     * 字符串数组作为参数，自动通过空格拼接
     */
    @Select({"select * from ywt_eac_inf_b",
            "where card_no = #{cardNo} and ywt_name = #{ywtName}",
            "limit 1"})
    Map<String, Object> getYwtInfoByKey(String cardNo, String ywtName);


    @Insert({"insert into product",
            "(product_id, product_name, product_type, sale_price, purchase_price, register_date, update_tim)",
            "values (#{productId}, \'glove heat proof\', \'guard\', 20, 10, \'2020-03-08\', \'2020-03-08 12:47:13\')"})
    @SelectKey(statement = "select max(cast(product_id as unsigned integer))+1 from product", keyProperty = "productId", before = true, resultType = String.class)
    int insertToProduct(Map<String, Object> params);

    @Select("select * from ywt_eac_inf_b limit 0, 10")
    List<Map<String, Object>> getAllYwtInfoLimit();

    @Select("select * from ywt_eac_inf_b")
    List<Map<String, Object>> getAllYwtInfoWithRowBounds(RowBounds rowBounds);

    @Select("select * from ywt_eac_inf_b")
    List<Map<String, Object>> getAllYwtInfo();

    @Select("select * from ywt_eac_inf_b")
    List<Map<String, Object>> getAllYwtInfoPageHelperParam(@Param("pageHelperNum") int pageNum, @Param("pageHelperSize") int pageSize);

    // uid, card_no, clt_cid, cd_flg, ywt_name
    @Insert({"<script>",
            "insert into ywt_eac_inf_a (uid, card_no, clt_cid, cd_flg, ywt_name) values",
            "   <foreach item='item' index='index' collection='ywtInfoList' open='' separator=',' close=''>",
            "       (#{item.uid}, #{item.card_no}, #{item.clt_cid}, #{item.cd_flg}, #{item.ywt_name})",
            "   </foreach>",
            "</script>"})
    int insertToYwtInfoA(@Param("ywtInfoList") List<Map<String, Object>> ywtInfoList);

}
