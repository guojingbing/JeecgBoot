package org.jeecg.modules.zxecg.phoenix.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jeecg.modules.zxecg.phoenix.annotation.PhoenixEntityAnnotation;
import org.jeecg.modules.zxecg.phoenix.config.JdbcTemplateConfig;
import org.jeecg.modules.zxecg.phoenix.exception.CustomPhoenixException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @Description: Phoenix操作工具类
 */
@Slf4j
@Component
public class PhoenixUtil {
    private static JdbcTemplateConfig jdbcTemplateConfig;

    @Autowired
    public PhoenixUtil(JdbcTemplateConfig jdbcTemplateConfig) {
        this.jdbcTemplateConfig = jdbcTemplateConfig;
    }

    /**
     * 根据类构造建表语句
     * @param clazz
     * @param compressLevel，压缩级别：null,不压缩；1, 低压缩率使用"SNAPPY"压缩算法；2、中等压缩率使用"LZO"压缩算法；3，高压缩率使用"GZ"压缩算法
     * @return
     */
    public static String initHbTableCreateSql(Class<?> clazz, Integer compressLevel) {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
        PhoenixEntityAnnotation.ClassAnnotation cAnno = clazz.getAnnotation(PhoenixEntityAnnotation.ClassAnnotation.class);
        String tableName = cAnno.table();
        sb.append(tableName);
        sb.append("(");
        //处理多列主键排序
        String[] keyArr = new String[10];
        int keyNum = 0;
        int i = 0;
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String typeName = field.getType().toString();
            typeName = typeName.substring(typeName.lastIndexOf(".") + 1);
            PhoenixEntityAnnotation.FieldAnnotation fAnno = field.getAnnotation(PhoenixEntityAnnotation.FieldAnnotation.class);
            if (fAnno == null) {
                continue;
            }
            String column = fAnno.column();
            String cf = fAnno.cf();
            if (!StringUtils.isBlank(column)) {
                if (i > 0) {
                    sb.append(",");
                }
                if (!"".equalsIgnoreCase(cf)) {
//          sb.append("\"");
                    sb.append(cf);
//          sb.append("\"");
                    sb.append(".");
                }
                sb.append(column);
                sb.append(" ");
                if (typeName.equalsIgnoreCase("STRING")) {
                    int length = fAnno.length();
                    typeName = "VARCHAR";
                    if (length > 0) {
                        typeName += "(" + length + ")";
                    }
                }
                if (typeName.equalsIgnoreCase("LONG")) {
                    typeName = "BIGINT";
                }
                if (typeName.equalsIgnoreCase("SHORT")) {
                    typeName = "SMALLINT";
                }
                if (typeName.equalsIgnoreCase("class [B")) {
                    typeName = "VARBINARY";
                }
                sb.append(typeName);

                int pkNum = fAnno.pkNum();
                if (pkNum > 0) {
                    keyArr[pkNum - 1] = column;
                    sb.append(" NOT NULL");
                    keyNum++;
                }
                i++;
            }
        }
        if (keyNum > 0) {
            sb.append(" constraint pk primary key(");
            for (int j = 0; j < keyNum; j++) {
                if (!StringUtils.isEmpty(keyArr[j])) {
                    if (j > 0) {
                        sb.append(",");
                    }
                    sb.append(keyArr[j]);
                }
            }
            sb.append(")");
        }
        sb.append(") SALT_BUCKETS=16");//分区
        if (compressLevel != null) {
            if (compressLevel.intValue() == 1) {
                sb.append(",COMPRESSION='SNAPPY'");//低压缩率
            } else if (compressLevel.intValue() == 2) {
                sb.append(",COMPRESSION='LZO'");//中等压缩率
            } else if (compressLevel.intValue() == 3) {
                sb.append(",COMPRESSION='GZ'");//高压缩率
            }
        }
        sb.append(",COLUMN_ENCODED_BYTES='NONE'");//hbase显示加密
        return sb.toString().toUpperCase();
    }

    /**
     * 根据类注解生成创建索引语句
     * @param clazz
     * @return
     */
    public static List<String> initHbIndexCreateSql(Class<?> clazz) {
        List<String> indexSqlList = new ArrayList<>();
        PhoenixEntityAnnotation.ClassAnnotation cAnno = clazz.getAnnotation(PhoenixEntityAnnotation.ClassAnnotation.class);
        String tableName = cAnno.table();
        String[] indexs = cAnno.indexs();
        if (indexs != null && indexs.length > 0) {
            for (String index : indexs) {
                if (StringUtils.isNotBlank(index)) {
                    String[] idxArr = index.split(",");
                    String sql = initHbIndexCreateSql(jdbcTemplateConfig.getSchema(), tableName, idxArr[0], Arrays.copyOfRange(idxArr, 1, idxArr.length));
                    indexSqlList.add(sql);
                }
            }
        }

        return indexSqlList;
    }

    /**
     * 根据类注解生成删除索引语句
     * @param clazz
     * @return
     */
    public static List<String> initHbIndexDropSql(Class<?> clazz) {
        List<String> indexSqlList = new ArrayList<String>();
        PhoenixEntityAnnotation.ClassAnnotation cAnno = clazz.getAnnotation(PhoenixEntityAnnotation.ClassAnnotation.class);
        String tableName = cAnno.table();
        String[] indexs = cAnno.indexs();
        if (indexs != null && indexs.length > 0) {
            for (String index : indexs) {
                if (StringUtils.isNotBlank(index)) {
                    String[] idxArr = index.split("-");
                    String sql = initHbIndexDropSql(jdbcTemplateConfig.getSchema(), tableName, idxArr[0]);
                    indexSqlList.add(sql);
                }
            }
        }

        return indexSqlList;
    }

    /**
     * 生成创建索引语句
     * @param schema
     * @param tableName
     * @param fields
     * @return
     */
    public static String initHbIndexCreateSql(String schema, String tableName, String idxName, String[] fields) {
        StringBuilder sb = new StringBuilder("CREATE INDEX IF NOT EXISTS " + idxName + " ON ");
        sb.append("\"");
        sb.append(schema);
        sb.append("\".");
        sb.append("\"");
        sb.append(tableName);
        sb.append("\"");
        sb.append("(");
        for (int i = 0; i < fields.length; i++) {
            String f = fields[i];
            if (i > 0) {
                sb.append(",");
            }
            sb.append(f);
        }
        sb.append(")");
        return sb.toString().toUpperCase();
    }

    /**
     * 生成drop索引语句
     * @param schema
     * @param tableName
     * @param idxName
     * @return
     */
    public static String initHbIndexDropSql(String schema, String tableName, String idxName) {
        StringBuilder sb = new StringBuilder("DROP INDEX " + idxName + " ON ");
        sb.append("\"");
        sb.append(schema);
        sb.append("\".");
        sb.append("\"");
        sb.append(tableName);
        sb.append("\";");
        return sb.toString().toUpperCase();
    }

    /**
     * 根据类构造删表语句
     *
     * @param clazz
     * @return
     */
    public static String initHbTableDropSql(Class<?> clazz) {
        StringBuilder sb = new StringBuilder("DROP TABLE IF EXISTS ");
        PhoenixEntityAnnotation.ClassAnnotation cAnno = clazz.getAnnotation(PhoenixEntityAnnotation.ClassAnnotation.class);
        String tableName = cAnno.table();
        sb.append(tableName);
        return sb.toString().toUpperCase();
    }

    /**
     * 生成创建序列sql
     * @param schema
     * @param sequenceName
     * @param start
     * @param end
     */
    public static String initHbSequenceCreateSql(String schema, String sequenceName, Integer start, Integer end) {
        String formatSql="CREATE SEQUENCE IF NOT EXISTS \"%s\".\"%s\"";
        List<Object> params = new ArrayList<>();
        params.add(schema);
        params.add(sequenceName);
        if(start!=null){
            formatSql+=" MINVALUE %d";
            params.add(start);
        }
        if(end!=null){
            formatSql+=" MAXVALUE %d";
            params.add(end);
        }
        return String.format(formatSql,params.toArray()).toUpperCase();
    }

    /**
     * 生成删除序列sql
     * @param schema
     * @param sequenceName
     * @return
     */
    public static String initHbSequenceDropSql(String schema, String sequenceName) {
        return String.format("DROP SEQUENCE IF EXISTS \"%s\".\"%s\"",schema,sequenceName).toUpperCase();
    }

    /**
     * 根据类注解生成upsert语句
     * @param clazz
     * @param isInsert 如果是插入操作，才会生成sequence语句，如果表存在sequence注解，插入和更新数据不能一起处理
     * @return
     * @param <T>
     * @throws CustomPhoenixException
     */
    public static <T> String initUpsertSql(Class<T> clazz, boolean isInsert) throws CustomPhoenixException {
        StringBuilder sb = new StringBuilder("UPSERT INTO ");
        StringBuilder sbv = new StringBuilder("(");
        List<String> columns = new ArrayList<>();

        PhoenixEntityAnnotation.ClassAnnotation cAnno = clazz.getAnnotation(PhoenixEntityAnnotation.ClassAnnotation.class);
        String tableName = cAnno.table();
        if(StringUtils.isBlank(tableName)){
            throw new CustomPhoenixException("实体类表名注解配置有误");
        }
        sb.append(tableName);
        sb.append(" ");
        sb.append("(");

        //排序字段，保证sql和参数顺序一致
        Field[] fields = clazz.getDeclaredFields();
        Arrays.sort(fields, Comparator.comparing(Field::getName));

        for (Field field : fields) {
            field.setAccessible(true);
            PhoenixEntityAnnotation.FieldAnnotation fAnno = field.getAnnotation(PhoenixEntityAnnotation.FieldAnnotation.class);
            if (fAnno == null) {
                continue;
            }
            String column = fAnno.column();
            String sequence = fAnno.sequence();

            if (StringUtils.isNotBlank(column)) {
                if (StringUtils.isNotBlank(sequence)&&isInsert) {
                    if (CollectionUtils.isNotEmpty(columns)) {
                        sb.append(",");
                        sbv.append(",");
                    }
                    sb.append(column);
                    sbv.append("NEXT VALUE FOR \""+ jdbcTemplateConfig.getSchema() +"\".\""+ sequence+"\"");
                }else{
                    if (CollectionUtils.isNotEmpty(columns)) {
                        sb.append(",");
                        sbv.append(",");
                    }
                    sb.append(column);
                    sbv.append("?");
                }
                columns.add(column);
            }
        }
        sbv.append(")");
        sb.append(")");
        sb.append("VALUES");
        sb.append(sbv);
        return sb.toString().toUpperCase();
    }

    /**
     * 生成upsert语句的参数
     * @param eList
     * @param isInsert 如果是插入操作，才会生成sequence语句，如果表存在sequence注解，插入和更新数据不能一起处理
     * @return
     * @param <T>
     * @throws CustomPhoenixException
     */
    public static <T> Map<String, Object> initUpsertParams(List<T> eList, boolean isInsert) throws CustomPhoenixException {
        if (eList == null || eList.isEmpty()) {
            return null;
        }
        Map<String, Object> sqlMap = new HashMap<>();

        //生成upsert语句
        String sql=initUpsertSql(eList.get(0).getClass(), isInsert);
        sqlMap.put("sql", sql);

        //处理要插入的记录的值集合
        List<Map<String, Object>> vList = new ArrayList<>();
        for (int i = 0; i < eList.size(); i++) {
            T obj = eList.get(i);
            if(obj==null){
                continue;
            }
            //要插入的一条记录的值集合
            Map<String, Object> vMap = new HashMap<>();
            Class clazz = obj.getClass();
            int j = 0;
            //排序字段，保证sql和参数顺序一致
            Field[] fields = clazz.getDeclaredFields();
            Arrays.sort(fields, Comparator.comparing(Field::getName));
            for (Field field : fields) {
                field.setAccessible(true);
                PhoenixEntityAnnotation.FieldAnnotation fAnno = field.getAnnotation(PhoenixEntityAnnotation.FieldAnnotation.class);
                if (fAnno == null) {
                    continue;
                }
                String column = fAnno.column();
                String sequence = fAnno.sequence();
                Object value;
                try {
                    value = field.get(obj);
                } catch (IllegalAccessException e) {
                    log.error("获取实体类字段【"+column+"】值失败",e);
                    throw new CustomPhoenixException("获取实体类字段【"+column+"】值失败："+e.getMessage());
                }
                if(isInsert&&StringUtils.isNotBlank(sequence)&&value!=null){
                    throw new CustomPhoenixException("插入数据，sequence字段【"+column+"】不可赋值");
                }
                if (column != null && !column.equals("") && value!=null) {
//                    if(value instanceof String){
//                        vMap.put(String.valueOf(j + 1), (String)value);
//                    }else if(value instanceof Long){
//                        vMap.put(String.valueOf(j + 1), (Long)value);
//                    }else if(value instanceof Integer){
//                        vMap.put(String.valueOf(j + 1), (Integer)value);
//                    }else if(value instanceof Short){
//                        vMap.put(String.valueOf(j + 1), (Short)value);
//                    }else if(value instanceof Double){
//                        vMap.put(String.valueOf(j + 1), (Double)value);
//                    }else if(value instanceof Float){
//                        vMap.put(String.valueOf(j + 1), (Float)value);
//                    }else if(value instanceof Boolean){
//                        vMap.put(String.valueOf(j + 1), (Boolean)value);
//                    }else if(value instanceof Byte){
//                        vMap.put(String.valueOf(j + 1), (Byte)value);
//                    }else if(value instanceof byte[]){
//                        vMap.put(String.valueOf(j + 1), (byte[])value);
//                    }else{
//                        vMap.put(String.valueOf(j + 1), value);
//                    }
                    vMap.put(String.valueOf(++j), value);
                }
            }
            vList.add(vMap);
        }
        sqlMap.put("datas", vList);
        return sqlMap;
    }

    /**
     * 初始化删除操作语句
     * @param clazz
     * @param matchAllColumn 是否匹配所有字段,false只匹配主键字段
     * @return
     * @param <T>
     * @throws CustomPhoenixException
     */
    public static <T> String initDeleteSql(Class<T> clazz,boolean matchAllColumn) throws CustomPhoenixException {
        StringBuilder sb = new StringBuilder("DELETE FROM ");
        List<String> columns = new ArrayList<>();

        PhoenixEntityAnnotation.ClassAnnotation cAnno = clazz.getAnnotation(PhoenixEntityAnnotation.ClassAnnotation.class);
        String tableName = cAnno.table();
        if(StringUtils.isBlank(tableName)){
            throw new CustomPhoenixException("实体类表名注解配置有误");
        }
        sb.append(tableName);
        sb.append(" ");
        sb.append("WHERE ");

        List<String> keyFieldList = new ArrayList<>();
        List<String> nonKeyFieldList = new ArrayList<>();

        //排序字段，保证sql和参数顺序一致
        Field[] fields = clazz.getDeclaredFields();
        Arrays.sort(fields, Comparator.comparing(Field::getName));
        for (Field field : fields) {
            field.setAccessible(true);
            PhoenixEntityAnnotation.FieldAnnotation fAnno = field.getAnnotation(PhoenixEntityAnnotation.FieldAnnotation.class);
            if (fAnno == null) {
                continue;
            }
            String column = fAnno.column();
            int pkNum=fAnno.pkNum();

            if (StringUtils.isNotBlank(column)) {
                if(pkNum>0){
                    keyFieldList.add(pkNum-1,column);
                }else{
                    nonKeyFieldList.add(column);
                }
                columns.add(column);
            }
        }

        if(CollectionUtils.isEmpty(keyFieldList)){
            throw new CustomPhoenixException("删除操作，必须按顺序指定主键字段");
        }

        for(int i=0;i<keyFieldList.size();i++){
            String column = keyFieldList.get(i);
            if(i>0){
                sb.append(" AND ");
            }

            sb.append(column);
            sb.append("=?");
        }
        //删除语句匹配所有数据
        if(matchAllColumn&&CollectionUtils.isNotEmpty(nonKeyFieldList)){
            for(int i=0;i<nonKeyFieldList.size();i++){
                String column = nonKeyFieldList.get(i);
                sb.append(" AND ");
                sb.append(column);
                sb.append("=?");
            }
        }

        return sb.toString().toUpperCase();
    }

    /**
     * 初始化删除操作参数
     * @param eList
     * @param matchAllColumn 是否匹配所有字段,false只匹配主键字段
     * @return
     */
    public static <T> Map<String, Object> initDeleteParams(List<T> eList,boolean matchAllColumn) throws CustomPhoenixException {
        if (eList == null || eList.isEmpty()) {
            return null;
        }
        Map<String, Object> sqlMap = new HashMap<>();

        //生成upsert语句
        String sql=initDeleteSql(eList.get(0).getClass(), matchAllColumn);
        sqlMap.put("sql", sql);

        //处理要插入的记录的值集合
        List<Map<String, Object>> vList = new ArrayList<>();
        for (int i = 0; i < eList.size(); i++) {
            T obj = eList.get(i);
            if(obj==null){
                continue;
            }
            //要插入的一条记录的值集合
            Map<String, Object> vMap = new HashMap<>();
            Class clazz = obj.getClass();
            //排序字段，保证sql和参数顺序一致
            Field[] fields = clazz.getDeclaredFields();
            Arrays.sort(fields, Comparator.comparing(Field::getName));
            List<Object> nonKeyFieldValueList = new ArrayList<>();
            int keyColumnNum = 0;
            for (Field field : fields) {
                field.setAccessible(true);
                PhoenixEntityAnnotation.FieldAnnotation fAnno = field.getAnnotation(PhoenixEntityAnnotation.FieldAnnotation.class);
                if (fAnno == null) {
                    continue;
                }
                String column = fAnno.column();
                int pkNum=fAnno.pkNum();

                Object value;
                try {
                    value = field.get(obj);
                } catch (IllegalAccessException e) {
                    log.error("获取实体类字段【"+column+"】值失败",e);
                    throw new CustomPhoenixException("获取实体类字段【"+column+"】值失败："+e.getMessage());
                }

                if(pkNum>0){
                    if(value==null){
                        throw new CustomPhoenixException("column【"+column+"】值为空，删除操作必须按顺序指定主键字段值：");
                    }
                    vMap.put(String.valueOf(pkNum), value);
                    keyColumnNum++;
                }else{
                    nonKeyFieldValueList.add(value);
                }
            }
            if(matchAllColumn&&CollectionUtils.isNotEmpty(nonKeyFieldValueList)){
                for (int j = 0; j < nonKeyFieldValueList.size(); j++) {
                    Object value=nonKeyFieldValueList.get(j);
                    vMap.put(String.valueOf(keyColumnNum+j+1), value);
                }
            }
            vList.add(vMap);
        }
        sqlMap.put("datas", vList);
        return sqlMap;
    }

    /**
     * 初始化查询操作sql
     * @param clazz
     * @param queryPager
     * @return
     */
    public static <T> String initQuerySql(Class<T> clazz,PhoenixQueryPager queryPager) {
        if(queryPager==null||CollectionUtils.isEmpty(queryPager.getKeyParams())){
            throw new CustomPhoenixException("查询操作，必须按顺序指定主键字段值");
        }
        PhoenixEntityAnnotation.ClassAnnotation classAnno = clazz.getAnnotation(PhoenixEntityAnnotation.ClassAnnotation.class);
        if (classAnno == null) {
            throw new CustomPhoenixException("类【"+clazz.getName()+"】未添加PhoenixEntityAnnotation注解");
        }
        String tableName = classAnno.table();
        if(StringUtils.isBlank(tableName)){
            throw new CustomPhoenixException("实体类表名注解配置有误");
        }
        StringBuffer sb=new StringBuffer();
        sb.append("SELECT * FROM ");
        sb.append(tableName);
        sb.append(" WHERE 1=1 ");

        //排序字段，保证sql和参数顺序一致
        Field[] fields = clazz.getDeclaredFields();
        Arrays.sort(fields, Comparator.comparing(Field::getName));
        List<String> keyFieldList = new ArrayList<>();
        for (Field field : fields) {
            field.setAccessible(true);
            PhoenixEntityAnnotation.FieldAnnotation fAnno = field.getAnnotation(PhoenixEntityAnnotation.FieldAnnotation.class);
            if (fAnno == null) {
                continue;
            }
            String column = fAnno.column();
            int pkNum=fAnno.pkNum();
            if(pkNum>0){
                keyFieldList.add(pkNum-1,column);
            }
        }

        if(CollectionUtils.isNotEmpty(keyFieldList)){
            List<Object> params=queryPager.getKeyParams();
            for (int i = 0; i < keyFieldList.size(); i++) {
                String keyField=keyFieldList.get(i);
                if(params.size()>i){
                    sb.append(" AND ");
                    sb.append(keyField);
                    sb.append("=?");
                }
            }
        }

        if(StringUtils.isNotBlank(queryPager.getCaseSql())){
            sb.append(" AND ");
            sb.append(queryPager.getCaseSql());
        }
        if(StringUtils.isNotBlank(queryPager.getOrderSql())){
            sb.append(" ORDER BY ");
            sb.append(queryPager.getOrderSql());
        }
        if(queryPager.getPageSize()>0){
            sb.append(" LIMIT ");
            sb.append(queryPager.getPageSize());
        }
        if(queryPager.getPageNo()>0){
            sb.append(" OFFSET ");
            sb.append(queryPager.getPageSize()*(queryPager.getPageNo()-1));
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 扫描phoenix实体类
     * @return
     */
    public static List<Class<?>> scanPhoenixEntities() {
        ClassWithCustomAnnoScaner packageClassesScaner=new ClassWithCustomAnnoScaner(Arrays.asList("org.jeecg.modules.zxecg"), PhoenixEntityAnnotation.ClassAnnotation.class);
        List<Class<?>> classes=new ArrayList<>();
        try {
            classes=packageClassesScaner.getClassList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classes;
    }

    /**
     * 扫描phoenix实体类对应的表名
     * @return
     */
    public static List<String> scanPhoenixTables() {
        List<String> tables=new ArrayList<>();
        try {
            List<Class<?>> classes=scanPhoenixEntities();
            if(CollectionUtils.isNotEmpty(classes)){
                for(Class clazz:classes){
                    PhoenixEntityAnnotation.ClassAnnotation classAnno = (PhoenixEntityAnnotation.ClassAnnotation) clazz.getAnnotation(PhoenixEntityAnnotation.ClassAnnotation.class);
                    if (classAnno == null) {
                        continue;
                    }
                    String table = classAnno.table();
                    if(StringUtils.isBlank(table)){
                        table=clazz.getSimpleName();
                    }
                    tables.add(table);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tables;
    }

    public static void main(String args[]) {
    }
}
