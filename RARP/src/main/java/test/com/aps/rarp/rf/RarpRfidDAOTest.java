package test.com.aps.rarp.rf;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aps.rarp.rf.model.RarpRfidDAO;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath*:rarp/spring/context-*.xml",		
		"file:src/main/webapp/WEB-INF/tiles/tiles-rarp.xml",
		"file:src/main/webapp/WEB-INF/config/springmvc/dispatcher-servlet.xml" })
public class RarpRfidDAOTest {
	Log logger = LogFactory.getLog(RarpRfidDAOTest.class);


	
}
