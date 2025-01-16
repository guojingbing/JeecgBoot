package org.jeecg.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 树形结构数据处理
 */
public class TreeUtils {
	private static Logger logger = LoggerFactory.getLogger(TreeUtils.class);

    /**
     * 数据集合构建树形结构数据
     * @param data
     * @param idField
     * @param parentIdField
     * @return
     */
    public static List<Map<String, Object>> buildTreeGridMap(List<Map<String, Object>> data, String idField, String parentIdField){
        List<Map<String, Object>> list = new ArrayList<>();
        HashMap<Object, Map> fullMap = new HashMap<>();

        for(Map<String, Object> map : data){
            fullMap.put(map.get(idField).toString(), map);
        }

        for(Map<String, Object> map : data){
            String pid = map.get(parentIdField).toString();
            Map<String, Object> treeMap = fullMap.get(map.get(idField).toString());
            if(pid == null || pid.equalsIgnoreCase("0")){
                list.add(treeMap);
            }else{
                Map<String, Object> pmap = fullMap.get(pid);
                if(pmap == null) continue;
                List<Map<String, Object>> children = (List<Map<String, Object>>) pmap.get("children");
                if(children == null) {
                    children = new ArrayList<>();
                    pmap.put("children",children);
                }
                children.add(treeMap);
            }
        }

        return list;
    }

    /**
     * 生成非延迟树对象
     * @param data
     * @param idField
     * @param parentIdField
     * @param textField
     * @return
     */
    public static List<Map<String, Object>> buildTreeMap(List<Map<String, Object>> data, String idField, String parentIdField, String textField, String iconClsField){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        HashMap<Object, Map> fullMap = new HashMap<Object, Map>();

        for(Map<String, Object> map : data){
            HashMap<String, Object> treeMap = new HashMap<String, Object>();
            treeMap.put("id", map.get(idField));
            if(iconClsField!=null){
                treeMap.put("iconCls", map.get(iconClsField));
            }
            treeMap.put("attributes", map);
            treeMap.put("text", map.get(textField));
            fullMap.put(map.get(idField), treeMap);
        }
        for(Map<String, Object> map : data){
            Number pid = (Number) map.get(parentIdField);
            Map<String, Object> treeMap = fullMap.get(map.get(idField));
            if(pid == null || pid.intValue() == 0){
                list.add(treeMap);
            }else{
                Map<String, Object> pmap = fullMap.get(pid);
                if(pmap == null) continue;
                List<Map<String, Object>> children = (List<Map<String, Object>>) pmap.get("children");
                if(children == null) {
                    children = new ArrayList<Map<String, Object>>();
                    pmap.put("children",children);
                    pmap.put("state","closed");
                }
                children.add(treeMap);
            }
        }

        return list;

    }

    /**
     * 生成适用lazytree的map对象
     * @param data
     * @param idField
     * @param textField
     * @param isLeafField
     * @return
     */
    public static List<Map<String, Object>> parse2TreeMap(List<Map<String, Object>> data, String idField, String textField, String isLeafField){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for(Map<String, Object> map : data){
            HashMap<String, Object> treeMap = new HashMap<String, Object>();
            treeMap.put("id", map.get(idField));
            treeMap.put("attributes", map);
            treeMap.put("state",getNodeState(map,isLeafField));
            treeMap.put("text", map.get(textField));
            list.add(treeMap);
        }
        return list;
    }

    public static String getNodeState(Map map, String isLeafField){
        String state = "closed";
        if(parseBool(map.get(isLeafField)))
            state = "open";
        return state;
    }

    private static boolean parseBool(Object o){
        if(o == null)return false;
        if(o instanceof Boolean)
            return (Boolean) o;
        if(o instanceof Number)
            return ((Number)o).intValue() == 1;
        if(o instanceof String)
            return "true".equalsIgnoreCase(o.toString());
        return false;
    }
}
