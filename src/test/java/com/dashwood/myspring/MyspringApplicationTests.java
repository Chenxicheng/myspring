package com.dashwood.myspring;

import com.dashwood.myspring.common.utils.DatabaseColumnInfo;
import com.dashwood.myspring.common.utils.DatabaseInfo;
import com.dashwood.myspring.model.datax.croe.DataXJobCounter;
import com.dashwood.myspring.model.datax.croe.DataxEnginService;
import com.dashwood.myspring.model.datax.croe.JobJsonGenerator;
import com.dashwood.myspring.model.datax.croe.TaskRunning;
import com.dashwood.myspring.model.mysql.dao.MysqlService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MyspringApplicationTests {

	private static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";

	private static final String JDBC_URL = "jdbc:mysql://192.168.30.10:3306/test";

	private static final String USERNAME = "root";

	private static final String PASSWORD = "password";

	@Autowired
	private JobJsonGenerator jobJsonGenerator;
	@Autowired
	private DataxEnginService dataxEnginService;

	@Autowired
	private MysqlService mysqlService;

	@Test
	public void test6 () {
//		DatabaseInfo databaseInfo = new DatabaseInfo();
//		databaseInfo.setJdbcUrl(JDBC_URL);
//		databaseInfo.setDriver(MYSQL_DRIVER);
//		databaseInfo.setUsername(USERNAME);
//		databaseInfo.setPassword(PASSWORD);
//
//		try {
//			mysqlService.connection(databaseInfo);
//
//			List<DatabaseColumnInfo> list = mysqlService.finListOfColumnByTable("zhcx_kkllfb");
//			System.out.println(list.toString());
//
//			mysqlService.createTable("zhcx_test", list);
//
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				mysqlService.closeConnection();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}


	}

	@Test
	public void contextLoads() {
		/*String readJson = "{\"name\":\"mysqlreader\",\"parameter\":{\"column\":[\"id\",\"name\",\"age\",\"sex\"],\"connection\":[{\"jdbcUrl\":[\"jdbc:mysql://192.168.30.179:3306/test\"],\"table\":[\"test_name\"]}],\"password\":\"password\",\"username\":\"root\"}}";
		String writeJson = "{\"name\":\"mysqlwriter\",\"parameter\":{\"column\":[\"id\",\"create_time\",\"modify_time\",\"title\",\"author\",\"click\",\"content\",\"deleted\",\"status\",\"summary\",\"top\",\"topic\",\"type\",\"category\",\"user\"],\"connection\":[{\"jdbcUrl\":\"jdbc:mysql://192.168.30.10:3306/data_x_1538_0\",\"table\":[\"article\"]}],\"password\":\"password\",\"postSql\":[],\"preSql\":[],\"session\":[],\"username\":\"root\",\"writeMode\":\"replace\"}}";

		String hdfsWriteJson = "{\"name\":\"hdfswriter\",\"parameter\":{\"defaultFS\": \"hdfs://192.168.30.179:9000\",\"fileType\": \"text\",\"path\": \"/test/\",\"fileName\": \"test_name_20180228\",\"column\":[{\"name\": \"id\", \"type\": \"double\"},{\"name\": \"name\", \"type\": \"string\"},{\"name\": \"age\", \"type\": \"string\"},{\"name\": \"sex\", \"type\": \"string\"}],\"writeMode\": \"append\",\"fieldDelimiter\": \",\"}}";
		//		String readJson = "{\"name\":\"mysqlreader\",\"parameter\":{\"column\":[\"ANCHEID\",\"PRIPID\",\"ANCHEDATE\",\"ANCHEYEAR\",\"REGNO\",\"UNISCID\",\"ENTNAME\",\"ENTTYPE\",\"TEL\",\"ADDR\",\"POSTALCODE\",\"EMAIL\",\"BUSST\",\"BUSST_CN\",\"ANTYPE\",\"DEPENDENTENTNAME\",\"NUMPARM\",\"PARINS\",\"PARINS_CN\",\"RESPARSECSIGN\",\"LASTUPDATETIME\",\"S_EXT_FROMNODE\",\"S_EXT_DATATIME\"],\"connection\":[{\"jdbcUrl\":[\"jdbc:mysql://123.138.111.171:50615/gongshang_data\"],\"table\":[\"AN_BASEINFO\"]}],\"password\":\"password#@!\",\"username\":\"root\"}}";
//		String writeJson = "{\"name\":\"mysqlwriter\",\"parameter\":{\"column\":[\"ANCHEID\",\"PRIPID\",\"ANCHEDATE\",\"ANCHEYEAR\",\"REGNO\",\"UNISCID\",\"ENTNAME\",\"ENTTYPE\",\"TEL\",\"ADDR\",\"POSTALCODE\",\"EMAIL\",\"BUSST\",\"BUSST_CN\",\"ANTYPE\",\"DEPENDENTENTNAME\",\"NUMPARM\",\"PARINS\",\"PARINS_CN\",\"RESPARSECSIGN\",\"LASTUPDATETIME\",\"S_EXT_FROMNODE\",\"S_EXT_DATATIME\"],\"connection\":[{\"jdbcUrl\":\"jdbc:mysql://192.168.30.10:3306/test\",\"table\":[\"an_baseinfo\"]}],\"password\":\"password\",\"postSql\":[],\"preSql\":[],\"session\":[],\"username\":\"root\",\"writeMode\":\"replace\"}}";
		String settingJson = "{\"speed\":{\"channel\":\"5\"},\"errorLimit\":{\"record\":0}}";
		//String fileName = "6a226fa870cf4041a05b8f4ab12a89cc";
		String fileName = "hdfstest2";
		try {
			jobJsonGenerator.generateJsonFile(fileName, readJson, hdfsWriteJson, settingJson);
		} catch (IOException e) {
			e.printStackTrace();
		}*/

	}

	@Test
	public void test() {
		/*long start = System.currentTimeMillis();
	    DataXJobCounter dataxJobStatus = dataxEnginService.execute("hdfstest2");
		System.out.println(dataxJobStatus);
		System.out.println(System.currentTimeMillis() - start);*/
	}



	//@Test
	/*public void deleteFileTest() throws IOException {
		String readJson = "{\"name\":\"mysqlreader\",\"parameter\":{\"column\":[\"id\",\"create_time\",\"modify_time\",\"title\",\"author\",\"click\",\"content\",\"deleted\",\"status\",\"summary\",\"top\",\"topic\",\"type\",\"category\",\"user\"],\"connection\":[{\"jdbcUrl\":[\"jdbc:mysql://192.168.30.10:3306/datax\"],\"table\":[\"article\"]}],\"password\":\"password\",\"username\":\"root\"}}";
		String writeJson = "{\"name\":\"mysqlwriter\",\"parameter\":{\"column\":[\"id\",\"create_time\",\"modify_time\",\"title\",\"author\",\"click\",\"content\",\"deleted\",\"status\",\"summary\",\"top\",\"topic\",\"type\",\"category\",\"user\"],\"connection\":[{\"jdbcUrl\":\"jdbc:mysql://192.168.30.10:3306/data_x_1538_0\",\"table\":[\"article\"]}],\"password\":\"password\",\"postSql\":[],\"preSql\":[],\"session\":[],\"username\":\"root\",\"writeMode\":\"replace\"}}";
		String settingJson = "{\"speed\":{\"channel\":\"5\"},\"errorLimit\":{\"record\":0}}";
		String fileName = "6a226fa870cf4041a05b8f4ab12a89cc";
		if (!jobJsonGenerator.verificateJsonFileExit(fileName)) {
			jobJsonGenerator.generateJsonFile(fileName, readJson, writeJson, settingJson);
		}
		DataXJobCounter dataXJobCounter = dataxEnginService.execute(fileName);
		System.out.println("counter============================================");
		System.out.println(dataXJobCounter);
		jobJsonGenerator.deleteJsonFile("6a226fa870cf4041a05b8f4ab12a89cc");
	}*/



}
