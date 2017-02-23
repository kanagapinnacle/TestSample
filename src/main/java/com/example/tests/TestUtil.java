package com.example.tests;

import java.io.FileInputStream;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.ITestContext;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class TestUtil {

	// Initialize variables from Application.properties
	private static final Log logger = LogFactory.getLog(TestUtil.class);
	public static String environment;
	public static Properties p = null;

	@BeforeSuite
	@Parameters({ "environment" })
	public void initFramework(@Optional("dev.configuration.properties") String configfile, ITestContext context)
			throws Exception {
		environment = configfile;
		String s = "in SuiteTestBase";
		logger.info("In SuiteTestBase.java" + s);
		init(configfile);
		// configureProxySettings();
		addPropertiesToTestContext(context);
	}

	private void addPropertiesToTestContext(ITestContext context) {
		for (Map.Entry<Object, Object> property : p.entrySet()) {
			context.setAttribute((String) property.getKey(), property.getValue());
		}
	}

	protected String getProperty(String name) {
		return getProperty(name, null);
	}

	protected String getProperty(String name, String defaultValue) {
		return p.getProperty(name, defaultValue);
	}

	// Load the properties file
	public void init(String configFile) {
		try {
			FileInputStream fis = new FileInputStream(configFile);
			p = new Properties();
			p.load(fis);
		} catch (Exception e) {
			logger.info("Exception " + e.getMessage());
		}
	}

}
