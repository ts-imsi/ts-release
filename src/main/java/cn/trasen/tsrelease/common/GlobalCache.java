package cn.trasen.tsrelease.common;

import cn.trasen.tsrelease.model.HospitalVo;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author luoyun
 * @ClassName: IntelliJ IDEA
 * @Description: 操作类型
 * @date 2018/2/8
 */
public class GlobalCache {
    protected Logger logger = Logger.getLogger(getClass());

    private static GlobalCache globalCache = new GlobalCache();

    private ConcurrentMap<String, List<HospitalVo>> hospMap;

    private GlobalCache() {
        hospMap = new ConcurrentHashMap<>();
    }


    public static GlobalCache getGlobalCache() {
        return globalCache;
    }

    public ConcurrentMap<String, List<HospitalVo>> getHospMap() {
        return hospMap;
    }

    public void setHospMap(ConcurrentMap<String, List<HospitalVo>> hospMap) {
        this.hospMap = hospMap;
    }
}
