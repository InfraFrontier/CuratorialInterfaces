package org.emmanet.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * Configuration class provides a method for looking up configuration values
 * stored in the file "config.properties"
 * 
 */
public class Configuration {

	private static Properties properties;

	// Load the properties file once
	static {
		Resource resource = new ClassPathResource("/config.properties");
		try {
			properties = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		resource = null;
	}

	// Configuration is a static class, hence, should not be instanced
	private Configuration() {
	}

	/**
	 * get returns the interpolated value from the configuration file.
	 * 
	 * @param key
	 *            the key to lookup in the configuration file
	 * @return the interpolated value. Keys may refer to other keys by including
	 *         the key name decorated with ${...}. For instance
	 * 
	 *         myKey = ${otherKey}/additional/data
	 * 
	 */
	public static String get(String key) {

		String prop = properties.getProperty(key);

		// Check if this is a property that requires
		// further interpolation
		if (prop.contains("${")) {

			List<String> matchList = new ArrayList<String>();
			Pattern regex = Pattern.compile("\\{([^}]*)\\}");
			Matcher regexMatcher = regex.matcher(prop);
			while (regexMatcher.find())
			{
				// the first group contains the matched text
				matchList.add(regexMatcher.group(1));
			}

			// Replace the place-holder tokens with the appropriate
			// value retrieved from the configuration file
			for (String match : matchList) {
				String m = match.replaceAll("\\{", "").replaceAll("\\}", "");

				if (m.equals(key)) {
					// Trying to self-reference...bail out
					return "";
				}

				prop = prop.replaceAll("\\$\\{" + m + "\\}",
						Configuration.get(m));
			}
		}

		return prop;
	}
}
