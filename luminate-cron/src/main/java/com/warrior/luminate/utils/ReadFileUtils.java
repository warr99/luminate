package com.warrior.luminate.utils;

import cn.hutool.core.text.csv.CsvReadConfig;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvRowHandler;
import cn.hutool.core.text.csv.CsvUtil;
import com.google.common.base.Throwables;
import com.warrior.luminate.csv.CountFileRowHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author WARRIOR
 * @version 1.0
 * 处理人群文件
 */
@Slf4j
public class ReadFileUtils {

    public static final String RECEIVER_KEY = "userId";

    public static void getCsvRow(String path, CsvRowHandler csvRowHandler) {
        try {
            // 把首行当做是标题，获取reader
            CsvReader reader = CsvUtil.getReader(new FileReader(path),
                    new CsvReadConfig().setContainsHeader(true));
            reader.read(csvRowHandler);
        } catch (Exception e) {
            log.error("ReadFileUtils#getCsvRow fail!{}", Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * 将行数据中除了 接受者 的其余参数放进 HashMap
     *
     * @param fieldMap 行数据map
     * @return 参数map
     */
    public static HashMap<String, String> getParamFromLine(Map<String, String> fieldMap) {
        HashMap<String, String> params = new HashMap<>(16);
        for (Map.Entry<String, String> paramKv : fieldMap.entrySet()) {
            if (!RECEIVER_KEY.equals(paramKv.getKey())) {
                params.put(paramKv.getKey(), paramKv.getValue());
            }
        }
        return params;
    }

    /**
     * 读取csv文件，获取文件里的行数
     *
     * @param path                文件路径
     * @param countFileRowHandler 统计文件行数的 handler
     */
    public static long countCsvRow(String path, CountFileRowHandler countFileRowHandler) {
        try {
            // 把首行当做是标题，获取reader
            CsvReader reader = CsvUtil.getReader(new FileReader(path),
                    new CsvReadConfig().setContainsHeader(true));
            reader.read(countFileRowHandler);
        } catch (Exception e) {
            log.error("ReadFileUtils#getCsvRow fail!{}", Throwables.getStackTraceAsString(e));
        }
        return countFileRowHandler.getRowSize();
    }
}
