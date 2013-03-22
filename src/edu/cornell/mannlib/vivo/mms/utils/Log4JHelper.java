/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mms.utils;

import java.net.URL;

import org.apache.log4j.PropertyConfigurator;

/**
 * Methods that will help when setting up the Logging.
 */
public class Log4JHelper {
	/**
	 * Find a properties file in the classpath, and add it to the Log4J
	 * configuration.
	 * 
	 * Fault tolerance: If the file can't be found, write a message to Standard
	 * Error, but don't throw an exception.
	 */
	public static void addConfigfile(String resourcePath) {
		URL configUrl = ClassLoader.getSystemResource(resourcePath);
		if (configUrl == null) {
			System.err.println("ERROR Log4JHelper: Can't find '" + resourcePath
					+ "' in the classpath.");
		} else {
			PropertyConfigurator.configure(configUrl);
			System.out.println("Log4JHelper: Added Log4j configuration from '"
					+ resourcePath + "'");
		}
	}
}
