package org.jeecg.modules.zxecg.phoenix.service;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PhoenixTestService {

    List<Map<String,Object>> list();
}
