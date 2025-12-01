package org.jeecg.modules.system.test;

import org.jeecg.JeecgSystemApplication;
import org.jeecg.modules.system.service.ISysDataLogService;
import org.jeecg.modules.zxecg.mock.MockController;
import org.jeecg.modules.zxecg.phoenix.config.JdbcTemplateConfig;
import org.jeecg.modules.zxecg.phoenix.entity.JeecgDemo;
import org.jeecg.modules.zxecg.phoenix.mapper.JeecgDemoMapper;
import org.jeecg.modules.zxecg.phoenix.service.IJeecgDemoService;
import org.jeecg.modules.zxecg.phoenix.service.IPhoenixSupportService;
import org.jeecg.modules.zxecg.phoenix.util.PhoenixUtil;
import org.jeecg.modules.zxecg.test.service.ITestInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = JeecgSystemApplication.class)
public class SampleTest {

	@Resource
	private JeecgDemoMapper jeecgDemoMapper;
	@Resource
	private IJeecgDemoService jeecgDemoService;
	@Resource
	private ISysDataLogService sysDataLogService;
	@Resource
	private MockController mock;

    @Resource
    ITestInfoService testInfoService;

    @Resource
    IPhoenixSupportService phoenixSupportService;

    @Autowired
    JdbcTemplateConfig jdbcTemplateConfig;

	@Test
	public void testSelect() {
		System.out.println(("----- selectAll method test ------"));
//		List<JeecgDemo> userList = jeecgDemoMapper.selectList(null);
//		Assert.isTrue(15==userList.size(),"结果不是5条");
//		userList.forEach(System.out::println);
//        List<Class<?>> classes = new ArrayList<>();
//        classes.add(PhoenixDemoEntity.class);
//        phoenixSupportService.createTables(classes);
        PhoenixUtil.initHbSequenceCreateSql(jdbcTemplateConfig.getSchema(),"test-seq",1,null);
	}

	@Test
	public void testXmlSql() {
		System.out.println(("----- selectAll method test ------"));
		List<JeecgDemo> userList = jeecgDemoMapper.getDemoByName("Sandy12");
		userList.forEach(System.out::println);
	}

	/**
	 * 测试事务
	 */
	@Test
	public void testTran() {
		jeecgDemoService.testTran();
	}

	/**
	 * 测试数据日志添加
	 */
	@Test
	public void testDataLogSave() {
		System.out.println(("----- datalog test ------"));
		String tableName = "jeecg_demo";
		String dataId = "4028ef81550c1a7901550c1cd6e70001";
		String dataContent = mock.sysDataLogJson();
		sysDataLogService.addDataLog(tableName, dataId, dataContent);
	}
}
