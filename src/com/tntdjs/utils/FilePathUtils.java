package com.tntdjs.utils;

/**
 * 
 * @author tsenauskas
 *
 */
public class FilePathUtils {

	/**
	 * cleanFilePath returns a filepath striped of protocol and space escaped values
	 * @param fileLocation
	 */
	public static String cleanFilePath(String fileLocation) {
		//This needs to go away
		if (fileLocation.startsWith("file:")) {
			fileLocation= fileLocation.replace("file://", "");
		}
		if (fileLocation.contains("%20")) {
			fileLocation = fileLocation.replaceAll("%20", " ");
		}
		//
		return fileLocation;
	}
	
	/**
	 * addFilePathLocation returns a valid file&path protocol and no plan spaces
	 * @param fileLocation
	 * @return
	 */
	public static String addFilePathDetails(String fileLocation) {
		return "file:///"+fileLocation.replaceAll(" ", "%20");
	}
	
}
