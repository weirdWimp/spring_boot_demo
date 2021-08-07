package com.example.springboot.demo.controller.mybatis;

import com.example.springboot.demo.mapper.LearnSqlMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

@RestController
@RequestMapping("/mybatis")
public class MybatisFeatureController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MybatisFeatureController.class);


    @Resource
    LearnSqlMapper learnSqlMapper;

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @GetMapping("returnMap")
    public void returnMap() {
        final Map<String, Object> productById1 = learnSqlMapper.getProductById("1000");
        System.out.println("productById1 = " + productById1);

        final Map<String, Object> productById2 = learnSqlMapper.getProductById("1000");
        System.out.println("productById2 = " + productById2);

        final Map<String, Map<String, Object>> allProduct = learnSqlMapper.getAllProduct();
        for (Map.Entry<String, Map<String, Object>> product : allProduct.entrySet()) {
            System.out.println("productId: " + product.getKey() + " " + product.getValue());
        }


        final Map<String, Object> ywtInfoByKey = learnSqlMapper.getYwtInfoByKey("no0000000010", "ywt0000000010");
        System.out.println("ywtInfoByKey = " + ywtInfoByKey);

        HashMap<String, Object> map = new HashMap<>();
        map.put("productId", "1004");

        final int i = learnSqlMapper.insertToProduct(map);
        System.out.println("i = " + i);

        System.out.println("done...");


    }

    /**
     * 每当一个新 session 被创建，MyBatis 就会创建一个与之相关联的本地缓存。任何在 session 执行过的查询结果都会被保存在本地缓存中，
     * 所以，当再次执行参数相同的相同查询时，就不需要实际查询数据库了。本地缓存将会在做出修改、事务提交或回滚，以及关闭 session 时清空。
     */
    @GetMapping("/session-scope")
    public void sessionScope() {


        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            LearnSqlMapper mapper = sqlSession.getMapper(LearnSqlMapper.class);

            Map<String, Object> productById3 = mapper.getProductById("1000");
            System.out.println("productById3 = " + productById3);

            // 调用 clearCache 可以清除本地缓存
            // sqlSession.clearCache();

            // 从 local cache 本地缓存中获取
            Map<String, Object> productById4 = mapper.getProductById("1000");
            System.out.println("productById4 = " + productById4);

            // 强制提交，默认没有检测到更新等操作时，不会提交
            sqlSession.commit(true);
        }

    }


    @GetMapping("/script")
    public void annotationScript() {
        List<Map<String, Object>> productByIds = learnSqlMapper.getProductByIds(Arrays.asList("1000", "1001"));
        for (Map<String, Object> productById : productByIds) {
            System.out.println("productById = " + productById);
        }
    }

    @GetMapping("/page")
    public void getSpecificPage() {
        long start = System.currentTimeMillis();
        List<Map<String, Object>> allYwtInfo = learnSqlMapper.getAllYwtInfoLimit();
        System.out.println("sql limit page = " + allYwtInfo.size());
        for (Map<String, Object> resultMap : allYwtInfo) {
            System.out.println("resultMap = " + resultMap);
        }

        System.out.println("sql limit page takes " + (System.currentTimeMillis() - start) + " mills");

        long start2 = System.currentTimeMillis();
        List<Map<String, Object>> allYwtInfo2 = learnSqlMapper.getAllYwtInfoWithRowBounds(new RowBounds(0, 10));
        System.out.println("row bounds page = " + allYwtInfo.size());
        for (Map<String, Object> resultMap : allYwtInfo2) {
            System.out.println("resultMap = " + resultMap);
        }
        System.out.println("row bounds page takes " + (System.currentTimeMillis() - start2) + " mills");


        // 保证在 PageHelper 方法调用后紧跟 MyBatis 查询方法
        Page page = PageHelper.startPage(1, 100);
        List<Map<String, Object>> pagedYwtInfos = learnSqlMapper.getAllYwtInfo();
        System.out.println("page.getTotal() = " + page.getTotal());
        for (Map<String, Object> pagedYwtInfo : pagedYwtInfos) {
            System.out.println("pagedYwtInfo = " + pagedYwtInfo);
        }

        // 使用参数方式
        long count = PageHelper.count(learnSqlMapper::getAllYwtInfo);
        System.out.println("count = " + count);

        List<Map<String, Object>> allYwtInfoPageHelperParam = learnSqlMapper.getAllYwtInfoPageHelperParam(1, 20);
        System.out.println("allYwtInfoPageHelperParam = " + allYwtInfoPageHelperParam.size());
    }

    @GetMapping("/page/transfer")
    public void transfer() {

        new Thread(() -> {
            int pageNum = 1;
            int pageSize = 1000;
            int batchInsertSize = 10;

            boolean isContinue = true;

            while (isContinue) {
                Page<Map<String, Object>> page = PageHelper.startPage(pageNum, pageSize, false)
                        .doSelectPage(learnSqlMapper::getAllYwtInfo);

                List<Map<String, Object>> pagedData = page.getResult();
                if (pagedData.size() == 0) {
                    LOGGER.info("The page data size is 0, done.");
                    isContinue = false;
                }

                List<Map<String, Object>> batchInsertData = new ArrayList<>();
                for (int i = 0; i < pagedData.size(); i++) {
                    batchInsertData.add(pagedData.get(i));
                    if (batchInsertData.size() == batchInsertSize || i == (pagedData.size() - 1)) {
                        learnSqlMapper.insertToYwtInfoA(batchInsertData);
                        batchInsertData.clear();
                    }
                }

                if (pagedData.size() < pageSize) {
                    LOGGER.info("The last page data has bee processed.");
                    isContinue = false;
                }
                pageNum++;
            }
        }).start();
    }
}
