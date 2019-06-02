package com.tntdjs.utils;

import java.io.File;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * AbstractPropertiesMgr
 * Copyright (c) 2017, Todd M. Senauskas and/or its affiliates. All rights reserved.
 * @author tsenauskas
 *
 */
public abstract class AbstractPropertiesMgr {
	/** logging reference */
	private static final Logger LOGGER = LogManager.getLogger(AbstractPropertiesMgr.class.getName());
	/** configuration builder from a file */
	private FileBasedConfigurationBuilder<FileBasedConfiguration> builder = null;
	/** a configuration */
	private Configuration config = null;
	/** a dev configuration */
	private Configuration configDev = null;
	/** is this a dev configuration */
	private boolean isDevConfig = false;
	
	/**
	 * Get the configuration property file to use for the given impl of a property manager
	 * @return
	 */
	public abstract String getConfigFile();
	
	/**
	 * initialize()
	 */
	protected void initialize() {
		Parameters params = new Parameters();
		builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
		    .configure(params.properties().setFileName(getConfigFile()));

		Parameters paramsDev = new Parameters();
		FileBasedConfigurationBuilder<FileBasedConfiguration> builderDev = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
				PropertiesConfiguration.class).configure(paramsDev.properties().setFileName("config-dev/system.properties"));
		
		try {
			config = builder.getConfiguration();			
			if (new File("config-dev/system.properties").exists()) {
				isDevConfig = true;
				configDev = builderDev.getConfiguration();
			}		    
		} catch(ConfigurationException cex) {
		  LOGGER.error("Error loading system.properties configurations : ", cex);
		}		
	}
	
	/**
	 * getString(String configName)
	 * @param String configName
	 * @return String value
	 */
	public String getString(String configName) {
		String value = config.getString(configName);
		if (isDevConfig) {
			String temp = configDev.getString(configName);
			if (null != temp && !temp.isEmpty()) {
				value = temp;
			}
		}					
		return value;
	}
	
	/**
	 * getString(String configName, String configValue)
	 * @param String configName
	 * @param String configValue
	 * @return String value
	 */
	public String getString(String configName, String configValue) {
		String value = config.getString(configName, configValue);
		if (isDevConfig) {
			String temp = configDev.getString(configName);
			if (null != temp && !temp.isEmpty()) {
				value = temp;
			}
		}
		return value;
	}
	
	/**
	 * getInteger(String configName, Integer configValue)
	 * @param String configName
	 * @param Integer configValue
	 * @return Integer value
	 */
	public Integer getInteger(String configName, Integer configValue) {
		Integer value = config.getInteger(configName, configValue);
		if (isDevConfig) {
			Integer temp = configDev.getInteger(configName, configValue);
			if (null != temp && 0 != temp.compareTo(configValue)) {
				value = temp;
			}
		}
		return value;		
	}
	
	/**
	 * setString(String configName, String configValue)
	 * @param String configName
	 * @param String configValue
	 */
	public void setString(String configName, String configValue) {
		config.setProperty(configName, configValue);
	}
	
	/**
	 * saveProperties()
	 * @return boolean for success
	 */
	public boolean saveProperties() {
		try {
			builder.save();
		} catch (ConfigurationException e) {
			LOGGER.error("Error saving system.properties file : ", e);
			return false;
		}				
		return true;
	}
}