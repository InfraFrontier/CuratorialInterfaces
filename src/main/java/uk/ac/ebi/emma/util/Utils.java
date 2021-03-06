/*
 * This class encapsulates various static methods useful throughout any java
 * class.
 */
package uk.ac.ebi.emma.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.validation.Errors;

/**
 *
 * @author mrelac
 */
public class Utils {
    protected static Logger logger = Logger.getLogger(Utils.class);

    /**
     * Given a property filename and a property name, this method returns the
     * desired property value from <code>filename</code> as a <code>String</code>.
     * <code>propertiesFile</code> is relative to the classloader's working
     * directory and typically is not an absolute path (e.g. no leading slash).
     * 
     * @param propertiesFile the name of the properties file
     * @param property name of desired property value
     * @return the property value, if found; null otherwise
     */
    public static String getProperty(String propertiesFile, String property) {
        Properties properties = getProperties(propertiesFile);
        if (properties == null) {
            return null;
        } else {
            return properties.getProperty(property);
        }
    }

    /**
     * Given a property filename, this method returns the properties.
     * <b>propertiesFile</b> is relative to the classloader's working
     * directory and typically is not an absolute path (e.g. no leading slash).
     * If <b>properitesFile</b> is not found, a null <code>Properties</code>
     * object is returned.
     * 
     * @param propertiesFile the name of the properties file
     * @return the properties, if found; null otherwise
     */
    public static Properties getProperties(String propertiesFile) {
        FileInputStream stream;
        Properties properties = new Properties();
        
        try {
            stream = new FileInputStream(propertiesFile);
            properties.load(stream);
            stream.close();
        }
        catch (IOException e) {
            return null;
        }

        return properties;
    }
    
    /**
     * Given a property filename, this method searches for the file on the classpath
     * and, if found, loads and returns the properties. Typically, <b>propertiesFile</b>
     * will begin with a forward slash (e.g. "/hibernate.properties").
     * If <b>properitesFile</b> is not found, a null <code>Properties</code>
     * object is returned.
     * 
     * @param propertiesFile the name of the properties file
     * @return the properties, if found; null otherwise
     */
    public static Properties getPropertiesFromClasspath(String propertiesFile) {
        Properties properties = null;
        Resource resource = new ClassPathResource(propertiesFile);
        try {
            properties = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) { }

        return properties;
    }
    
    
    /**
     * Given a property filename, a property name, and a delimiter, this method
     * returns the desired property values as an array of <code>String</code>.
     * The delimiter separates multiple values for the same property on the same
     * line. If the delimiter is null or is an empty string, then this function
     * returns the rvalue in its entirety, in an array of exactly one element.
     * @param propertiesFile the name of the properties file
     * @param property name of desired property value
     * @param delimiter The delimiter used to separate input property values
     * @return An array of <code>String</code> containing the property values
     */
    public static String[] getProperty(String propertiesFile, String property, String delimiter) {
        String[] retVal = null;
        
        if ((delimiter == null) || (delimiter.trim().length() == 0)) {
            retVal = new String[1];
            retVal[0] = getProperty(propertiesFile, property);
            return retVal;
        }
        FileInputStream stream = null;
        Properties properties = new Properties();
        
        try {
            stream = new FileInputStream(propertiesFile);
            properties.load(stream);
            String rvalue = properties.getProperty(property);
            if (rvalue == null)
                return null;
            
            if (rvalue.contains(delimiter)) {
                retVal = rvalue.split(delimiter);
                for (int i = 0; i < retVal.length; i++)
                    retVal[i] = retVal[i].trim();
            } else {
                retVal = new String[1];
                retVal[0] = rvalue.trim();
            }
        }
        catch (IOException e) {
            return retVal;
        }
        finally {
            if (stream != null) {
                try {
                    stream.close();
                }
                catch (IOException ioe) {
                System.out.println(ioe.getLocalizedMessage());
                }
            }
        }
        
        return retVal;
    }
    
    /**
     * Given an array of <code>String</code>, this method returns an equivalent
     * array of <code>Address</code>.
     * @param values
     * @return 
     */
    public static Address[] toAddress(String[] values) {
        if ((values == null) || (values.length == 0))
            return null;
        Address[] addresses = new Address[values.length];
        for (int i = 0; i < values.length; i++) {
            try {
                addresses[i] = new InternetAddress(values[i]);
            }
            catch (AddressException ae) {
                throw new RuntimeException("AddressException: " + ae.getLocalizedMessage());
            }
        }
        
        return addresses;
    }
    
    /**
     * Given two non-primitive types, either of which may be null or may contain
     * no elements, this method returns a single, new array of the contents of
     * array1 followed by the contents of array2. Empty arrays are treated as
     * null. If either array is null or empty (and the other is not), an array
     * of the other non-null, non-empty array values is returned.
     * @param <T> The array type
     * @param array1 the first array
     * @param array2 the second array
     * @return a single, new array of the contents of array1 followed by the
     *         contents of array2
     */
    public static <T> T[] join(T[] array1, T[] array2) {
        if ((array1 == null) || (array1.length == 0))
            array1 = null;
        if ((array2 == null) || (array2.length == 0))
            array2 = null;
        if (array1 == null)
            return array2;
        if (array2 == null)
            return array1;
        
        Class c = array1[0].getClass();
        
        int length = array1.length + array2.length;
        T[] retVal = (T[])Array.newInstance(c, length);
        int curIndex = 0;
        for (int i = 0; i < array1.length; i++) {
            retVal[curIndex] = array1[i];
            curIndex++;
        }
        for (int i = 0; i < array2.length; i++) {
            retVal[curIndex] = array2[i];
            curIndex++;
        }

        return retVal;
    }

    /**
     * Given a <code>ResultSet</code> and a column name, this method attempts to
     * fetch the value from the result set. If successful, the value is returned
     * as a string; otherwise, an empty <code>String</code> is returned. This
     * method guarantees a non-null return value regardless of <em>columnName</em>
     * to be suitable for client display.
     * @param rs the result set to search
     * @param columnName the desired column name
     * @return the value, if successful; an empty <code>String</code> otherwise
     */
    public static String getDbValue(ResultSet rs, String columnName) {
        String retVal = "";
        
        try {
            Object o = rs.getObject(columnName);
            if (o != null)
                retVal = o.toString();
        }
        catch (SQLException e) {
            throw new RuntimeException("Unable to get result set value for column '" + columnName + "'");
        }
        
        return retVal;
    }
    
    /**
     * Return the context path of the legacy emma application using the default
     * application properites file <code>/app-config.properties</code>.
     * 
     * @return the context path of the legacy emma application as configured in
     * <code>/app-config.properties</code>.
     */
    public static String getEmmaContextPath() {
        return getEmmaContextPath("/app-config.properties");
    }
    
    /**
     * Return the context path of the legacy emma application.
     * @param filename the properties filename
     * @return the context path of the legacy emma application.
     */
    public static String getEmmaContextPath(String filename) {
        String emmaContextPath = "";
        Resource resource = new ClassPathResource(filename);
        try {
            Properties emmaContextProperties = PropertiesLoaderUtils.loadProperties(resource);
            emmaContextPath = emmaContextProperties.getProperty("emmaContextPath");
        } catch (IOException e) {
            logger.error("Unable to load application configuration properties file '" + filename + "'");
        }
        return emmaContextPath;
    }

    /**
     * Given a <code>ResultSet</code> and a column name containing a [possibly
     * null] <code>Date</code>, this method attempts to fetch the date from the
     * result set. If successful, the value is returned
     * as a <code>Date</code>; otherwise, null <code>Date</code> is returned.
     * @param rs the result set to search
     * @param columnName the desired column name containing data of type <code>Date</code>
     * @return the value, if successful; null otherwise
     */
    public static Date getDbDateValue(ResultSet rs, String columnName) {
        Date retVal = null;
        
        try {
            Object o = rs.getObject(columnName);
            if (o != null)
                retVal = (Date)o;
        }
        catch (SQLException e) {
            throw new RuntimeException("Unable to get result set Date value for column '" + columnName + "'");
        }
        
        return retVal;
    }
    
    /**
     * Given an <code>Object</code> that may be null or may be a float or double, this
     * method attempts to convert the value to a <code>Double</code>. If successful,
     * the <code>Double</code> value is returned; otherwise, <code>null</code> is returned.
     * NOTE: the [non-null] object is first converted to a string and is trimmed of whitespace.
     * @param o the object to try to convert
     * @return the converted value, if <em>o</em> is a <code>Float or Double</code>; null otherwise
     */
    public static Double tryParseDouble(Object o) {
        if (o == null)
            return null;
        
        Double retVal = null;
        try {
            retVal = Double.parseDouble(o.toString().trim());
        }
        catch (NumberFormatException nfe ) { }
        
        return retVal;
    }
    
    /**
     * Given an <code>Object</code> that may be null or may be an Integer, this
     * method attempts to convert the value to an <code>Integer</code>. If successful,
     * the <code>Integer</code> value is returned; otherwise, <code>null</code> is returned.
     * NOTE: the [non-null] object is first converted to a string and is trimmed of whitespace.
     * @param o the object to try to convert
     * @return the converted value, if <em>o</em> is an <code>Integer</code>; null otherwise
     */
    public static Integer tryParseInt(Object o) {
        if (o == null)
            return null;
        
        Integer retVal = null;
        try {
            retVal = Integer.parseInt(o.toString().trim());
        }
        catch (NumberFormatException nfe ) { }
        
        return retVal;
    }
    
    /**
     * Given a date string, this method attempts to convert the date to a <code>
     * java.sql.Date</code> object and, if successful, returns the date. If
     * not successful, returns null.
     * 
     * @param dateString The date string against which to attempt conversion
     * @return the <code>java.sql.Date</code> date, if successful; null otherwise
     */
    public static java.sql.Date tryParseToDbDate(String dateString) {
        java.sql.Date retVal = null;
        
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = df.parse(dateString);
            retVal = new java.sql.Date(date.getTime());
        }
        catch(ParseException e) { }
        
        return retVal;
    }
    
    /**
     * Returns a string, wrapped in <code>wrapper</code>. If value was null,
     * an empty pair of quotes is returned.
     * @param value value to be wrapped
     * @param wrapper the wrapper
     * @return 
     */
    public static String wrap(String value, String wrapper) {
        String s = (value == null ? "" : value);
        return wrapper + s + wrapper;
    }
    
    /**
     * Given an object instance, a tablename to which the fields in the object are bound,
     * and a <code>org.springframework.validation.Errors.Errors</code> object,
     * this method performs maximum column width validation to insure the strings
     * in <code>object</code> do not exceed their maximum length as defined in
     * the database.
     * @param instance the instance whose String components are to be validated
     * @param tablename the table containing the columns to which <code>
     * instance</code> String fields are bound
     * @param errors a Spring validation Errors object updated with any validation
     * errors .
     */
    public static void validateMaxFieldLengths(Object instance, String tablename, Errors errors) {
        HashMap columnLengthsHash = DBUtils.getMaxColumnLengths(tablename);
        Field[] fields = instance.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getType().equals(String.class)) {
                try {
                    field.setAccessible(true);
                    String value = (String)field.get(instance);
                    if (value != null) {
                        logger.debug("validate: field: " + field.getName() + " in columnLengthsHash? " + (columnLengthsHash.containsKey(field.getName()) ? "true" : "false"));
                        if (columnLengthsHash.containsKey(field.getName())) {
                            int maxColumnLength = (int)columnLengthsHash.get(field.getName());
                            if (value.length() > maxColumnLength) {
                                String errMsg = "Expected at most " + maxColumnLength + " character(s) but found " + value.length() + " characters.";
                                errors.rejectValue(field.getName(), null, errMsg);
                                String logMsg = tablename + "." + field.getName() + ": expected at most " + maxColumnLength + " character(s) but found " + value.length() + " characters.";
                                logger.warn(logMsg);
                            }
                        }
                    }
                } catch (IllegalAccessException iae) {
                    logger.warn(iae.getMessage());
                }
            }
        }
    }
    
    /**
     * Given a comma-separated string of values, returns a string containing
     * all of the values, with whitespace removed (using trim()). If <b>intArray
     * </b> is null or empty, an empty string is returned. If <b>intArray</b>
     * contains only a single int, that value, less whitespace, is returned.
     * 
     * @param intArray string of comma-separated values to clean (trim)
     * @return   a string containing all of the values, with whitespace removed
     * (using trim()).
     */
    public static String cleanIntArray(String intArray) {
        if (intArray == null)
            return "";
        
        String retVal = "";
        String[] sA = intArray.split(",");
        if (sA.length == 0)
            return intArray.trim();
        
        for (String s : sA) {
            if ( ! retVal.isEmpty())
                retVal += ",";
            retVal += s.trim();
        }
        
        return retVal;
    }
    
    /**
     * Given a string containing 1 integer, or 2 or more comma-separated integers
     * (with optional trailing whitespace after each number), returns true if
     * all of the comma-separated values are integers; false if any of them is 
     * not, or if the string is null or empty.
     * 
     * @param intArray string of comma-separated integers to validate
     * @return true if all of the comma-separated values are integers; false if
     * any of them is  not, or if the string is null or empty.
     */
    public static boolean isValidIntArray(String intArray) {
        if (intArray == null)
            return false;
        
        boolean valueFound = false;

        String[] sA = intArray.split(",");
        for (String s : sA) {
            try {
                Integer.parseInt(s);
                valueFound = true;
            } catch (NumberFormatException nfe) {
                return false;
            }
        }
        
        return (valueFound);
    }
}
