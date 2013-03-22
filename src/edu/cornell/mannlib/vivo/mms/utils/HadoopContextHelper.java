/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mms.utils;

import org.apache.hadoop.mapreduce.Mapper;

/**
 * A collection of methods to help with the Hadoop context.
 */
public class HadoopContextHelper {

	/**
	 * A property in the configuration holds a fully qualified class name. Find
	 * that property and return an instance of that class.
	 * 
	 * @param context
	 * @param propertyName
	 *            The name of the configuration property
	 * @param resultClass
	 *            The class or interface that the instance will be cast to.
	 * @return An instance of the specified class.
	 * 
	 * @throws Error
	 *             If no such property was found, if the class could not be
	 *             loaded, or if an instance could not be created.
	 */
	public static <T> T instantiateFromProperty(
			Mapper<?, ?, ?, ?>.Context context, String propertyName,
			Class<T> resultClass) {
		String helperClassName = context.getConfiguration().get(propertyName);
		if (helperClassName == null) {
			throw new Error("Cannot continue: no configuration "
					+ "value was provided for '" + propertyName + "'");
		}

		Class<?> helperClass;
		try {
			helperClass = Class.forName(helperClassName);
		} catch (ClassNotFoundException e) {
			throw new Error("Cannot continue because could not load "
					+ helperClassName + " implementation of "
					+ resultClass.getName() + " " + e);
		}

		try {
			return resultClass.cast(helperClass.newInstance());
		} catch (Exception e) {
			throw new Error(
					"Cannot continue because could not get new instance of "
							+ helperClassName + " " + e);
		}
	}

}
