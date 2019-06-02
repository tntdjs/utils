package com.tntdjs.utils;

/**
 * 
 * Copyright (c) 2017, Todd M. Senauskas and/or its affiliates. All rights reserved.
 * @author tsenauskas
 *
 */
public final class SystemPropertyMgr extends AbstractPropertiesMgr {
	/** instance */
	private static SystemPropertyMgr instance;

	/**
	 * getInstance()
	 * @return an instance of SystemPropertyMgr
	 */
	public static SystemPropertyMgr getInstance() {
		if (null == instance) {
			instance = new SystemPropertyMgr();
		}
		return instance;
	}
	
	/**
	 * Constructor :: SystemPropertyMgr()
	 */
	public SystemPropertyMgr() {
		initialize();
	}

	/**
	 * getConfigFile()
	 * return String system.properties file/location
	 */
	@Override
	public String getConfigFile() {
		return "config/system.properties";
	}	
}
