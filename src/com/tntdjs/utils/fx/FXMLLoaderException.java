package com.tntdjs.utils.fx;

/**
 *
 * Copyright (c) 2017, Todd M. Senauskas and/or its affiliates. All rights reserved.
 * @author tsenausk
 *
 */
public class FXMLLoaderException extends Exception {
	private static final long serialVersionUID = 1L;
	private Exception nestedException;
	private String msg = "";
	
	public FXMLLoaderException(String msg, Exception argNestedException) {
		super();
		this.msg = msg;
		nestedException = argNestedException;
	}

	@Override
	public String toString() {
		return this.msg + " :: " + nestedException + " :: " + super.toString();
	}
	
	
	
}
