/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mms.utils;

import java.io.IOException;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Mapper;
import org.w3c.dom.Document;

import edu.cornell.mannlib.vivo.mms.utils.xml.XmlUtils;
import edu.cornell.mannlib.vivo.mms.utils.xml.XmlUtils.XmlUtilsException;

/**
 * A collection of methods to help with the Hadoop context.
 */
public class HadoopContextHelper {

	/**
	 * A property in the configuration holds a fully qualified class name. Find
	 * that property and return an instance of that class.
	 * 
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
					+ resultClass.getName() + " ", e);
		}

		try {
			return resultClass.cast(helperClass.newInstance());
		} catch (Exception e) {
			throw new Error(
					"Cannot continue because could not get new instance of "
							+ helperClassName + " ", e);
		}
	}

	/**
	 * A property in the configuration holds a path to an XML file in the Hadoop
	 * file system. Find that property, read and parse that file.
	 * 
	 * @return the parsed XML document
	 * @throws Error
	 *             If no such property was found, if the file does not exist, if
	 *             the file could not be read, or if the file is not valid XML.
	 */
	public static Document parseXmlFileAtProperty(
			Mapper<?, ?, ?, ?>.Context context, String propertyName) {
		String filePath = context.getConfiguration().get(propertyName);
		if (filePath == null) {
			throw new Error("Cannot continue: no configuration "
					+ "value was provided for '" + propertyName + "'");
		}

		Path p = new Path(filePath);
		try (FileSystem fs = FileSystem.get(context.getConfiguration())) {
			if (!fs.exists(p)) {
				throw new Error("Cannot continue: file '" + filePath
						+ "' does not exist in the file system");
			}
			if (fs.getFileStatus(p).isDir()) {
				throw new Error("Cannot continue: '" + filePath
						+ "' is a directory; expecting a file.");
			}

			try (FSDataInputStream stream = fs.open(p)) {
				return XmlUtils.parseXml(stream);
			} catch (XmlUtilsException e) {
				throw new Error(
						"Cannot continue: failed to parse the XML in file '"
								+ filePath + "'", e);
			}
		} catch (IOException e) {
			throw new Error("Cannot continue: unable to read file '" + filePath
					+ "' from the Hadoop file system.", e);
		}
	}

}
