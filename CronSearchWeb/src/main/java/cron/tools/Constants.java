package cron.tools;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.caudexorigo.Shutdown;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {

	private static Logger log = LoggerFactory.getLogger(Constants.class);

	
	public static final Properties properties = new Properties();
	static {
	
		try {
			//final String projectName = System.getProperty("project", null);
			String environment = System.getProperty("environment", "staging");
			//log.info("CONSTANS: project name... [" + projectName+"]");
			log.info("CONSTANS: loading settings... [" + environment+"]");
			
			List<String> loadingConfigsFiles = new ArrayList<String>();
			loadingConfigsFiles = loadParentConfigs(environment, loadingConfigsFiles);
			//LOAD ENV CONFIGS
			Map<String, ResourceBundle> _lstResourceBundle = new LinkedHashMap<String, ResourceBundle>();
			for(String configFiles :  loadingConfigsFiles) {
				_lstResourceBundle.put(configFiles, ResourceBundle.getBundle(configFiles));
			}
			_lstResourceBundle.put(environment, ResourceBundle.getBundle(environment));
			
			for (Map.Entry<String, ResourceBundle> entry : _lstResourceBundle.entrySet()) {
				ResourceBundle resBundle = entry.getValue();
				Enumeration<String> keys = resBundle.getKeys();
				while (keys.hasMoreElements()) {
					String key = keys.nextElement();
					if(resBundle.getString(key) == null || resBundle.getString(key).equals("null") || resBundle.getString(key).equals("")) {
						properties.remove(key);
					}else {
						properties.put(key, resBundle.getString(key));
					}
					log.debug("CONSTANS: got: \t'{}' \t=  '{}' ", key, resBundle.getString(key));
				}
			}
		} catch (Throwable t) {
			System.out.println("error " );
			Shutdown.now(t);
		}
	}
	private static List<String> loadParentConfigs(String configName, List<String> loadingConfigsFiles) {
		
		ResourceBundle initResorce = ResourceBundle.getBundle(configName);
		String loadingConfigs = null;
		try {
			loadingConfigs = initResorce.getString("loading.parents.configs");
		} catch (Exception e) {}
		if(loadingConfigs != null ) {
			for(String configFile : StringUtils.split(loadingConfigs, ";")) {
				loadingConfigsFiles = loadParentConfigs(configFile, loadingConfigsFiles);
				loadingConfigsFiles.add(configFile);
			}
		}
		return loadingConfigsFiles;	
	}
	
}
