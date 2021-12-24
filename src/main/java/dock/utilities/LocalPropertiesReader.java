package dock.utilities;

/**
 * @author Riffat Shahzad
 */

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalPropertiesReader {

    private static PropertiesConfiguration properties = null;
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    private static PropertiesConfiguration initializePropertiesFiles() {
        try {
            //TODO REMOVE USER DIR ACCESS
            String filePath = LocalPropertiesReader.class.getResource("/configfiles/local.properties").getPath();
            properties = new PropertiesConfiguration(filePath);
            FileChangedReloadingStrategy strategy = new FileChangedReloadingStrategy();
            strategy.setRefreshDelay(500);
            properties.setReloadingStrategy(strategy);
            properties.reload();
        }
        catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return properties;
    }

    private static String getProperty(String key) {
        properties = initializePropertiesFiles();
        String value = System.getProperty(key);
        if (value != null && !StringUtils.isEmpty(value)) {
            return value;
        }
        else {
            value = properties.getString(key);
        }
        return value;
    }

    public static String getGridHubName() {
        return getProperty("grid.hub");
    }

    public static String getRetryMode() {
        return getProperty("retry.mode");
    }

    public static Boolean getQuitDriverMode() {
        return Boolean.valueOf(getProperty("quit.driver"));
    }

    public static boolean getStatusdownloadApp() {
        return Boolean.valueOf(getProperty("downloadApp"));
    }

    public static String getAndroidPhoneName() {
        return getProperty("androidPhoneName");
    }

    public static String getAndroidOSVersion() {
        return getProperty("androidVersion");
    }

    public static String getAndroidUdid() {
        return getProperty("androidUdid");
    }

    public static String getExecutionMode() {
        return getProperty("execution.mode");
    }
}
