package com.ibm.service;

import com.ibm.entity.Department;
import com.ibm.jdbc.MainJdbc;
import com.ibm.mapper.MianMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MainService {

    /**
     * org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration.CacheConfigurationImportSelector#selectImports(org.springframework.core.type.AnnotationMetadata)
     * <p>
     * org.springframework.boot.autoconfigure.cache.SimpleCacheConfiguration【默认】---->org.springframework.boot.autoconfigure.cache.SimpleCacheConfiguration#cacheManager()
     * org.springframework.cache.concurrent.ConcurrentMapCacheManager#getCache(java.lang.String)
     * <p>
     * <p>
     * <p>
     * <p>
     * <p>
     * 几个属性：
     * cacheNames/value：指定缓存组件的名字;将方法的返回结果放在哪个缓存中，是数组的方式，可以指定多个缓存；
     * <p>
     * key：缓存数据使用的key；可以用它来指定。默认是使用方法参数的值  1-方法的返回值
     * 编写SpEL； #i d;参数id的值   #a0  #p0  #root.args[0]
     * getEmp[2]
     * <p>
     * keyGenerator：key的生成器；可以自己指定key的生成器的组件id
     * key/keyGenerator：二选一使用;
     * <p>
     * <p>
     * cacheManager：指定缓存管理器；或者cacheResolver指定获取解析器
     * <p>
     * condition：指定符合条件的情况下才缓存；
     * ,condition = "#id>0"
     * condition = "#a0>1"：第一个参数的值》1的时候才进行缓存
     * <p>
     * unless:否定缓存；当unless指定的条件为true，方法的返回值就不会被缓存；可以获取到结果进行判断
     * unless = "#result == null"  为空表示为true，为true不缓存，也就是为null不进行缓存
     * unless = "#a0==2":如果第一个参数的值是2，结果不缓存；
     * sync：是否使用异步模式
     */

    @Autowired
    MainJdbc mainJdbc;
    /**
     *
     */
    @Autowired
    MianMapper mianMapper;

    public void testJdbcMethod() {
        Map<String, Object> depart = mainJdbc.getDepart();
        if (null == depart) {
            System.out.println("query result is empty");
            return;
        } else {
            // Set<Map.Entry<String, Object>> entries = depart.entrySet();
            for (Map.Entry<String, Object> mapResult : depart.entrySet()) {
                System.out.println("this key is {}" + mapResult.getKey() + "this value is {}" + mapResult.getValue());
            }
        }
    }

    /**
     * 一般来说先执行@Cacheable,如果有更新需要执行一次@CachePut更新缓存，然后再次调用@Cacheable能拿到最新的值   但必须保持二个注解的key一样
     */

    /**
     * 下查看key是否存在，不存在调用方法，存在不调用方法
     *
     * @return
     */
    @Cacheable(cacheNames = {"dept"})
    public List<Department> testMapperCache() {
        System.out.println("service start cache >>>  <<< ");
        List<Department> deptList = mianMapper.getDeptList();
        if (0 == deptList.size()) {
            return null;
        } else {
            return deptList;
        }
    }

    @Cacheable(cacheNames = {"dept"})
    public Department getdeptById(String id) {
        return mianMapper.getdept(id);
    }

    /**
     * 即调用方法，又更新缓存
     * 先调用方法
     *
     * @return
     */
    @CachePut
    public void updatedept(Department department) {
        mianMapper.updateDept(department);

    }

    @CachePut
    public void delDept(String id) {
        mianMapper.delDept(id);

    }

}
