package com.tntdjs.utils;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tntdjs.utils.FilePathUtils;

public class FilePathUtilsTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCleanFilePathA() {
		assertEquals(new String("test"), FilePathUtils.cleanFilePath("file://test"));
	}

//	@Test
//	public void testCleanFilePathB() {
//		assertEquals(new String("test//"), FilePathUtils.cleanFilePath("file://test/"));
//	}
//	
//	@Test
//	public void testAddFilePath() {
//		assertEquals(new String("test//"), FilePathUtils.addFilePathDetails("test/"));
//	}
	
}
