package cron.tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import cron.pojos.SearchResults;

public class Utils {

	//private static Logger log = LoggerFactory.getLogger(UtilsFiles.class);

	public ArrayList<String> verifyIfExistFilesOnFolder(String[] staticProjectList, String folderProjectStatic) throws Exception {
		ArrayList<String> ListProjectMiss = new ArrayList<String>();
		File folder = new File(folderProjectStatic);		
		if ( !folder.exists() && !folder.isDirectory() ) {
			if ( !folder.mkdirs() ) {
				 throw new Exception("Directoria ["+folderProjectStatic+"] esta com problemas! Ou nao exite Ou nao é uma pasta!");				
			}else {
				ListProjectMiss.addAll(new ArrayList<String>(Arrays.asList(staticProjectList)));
				return ListProjectMiss;
			}
		}		
		List<String> projectListExistInFolder = new ArrayList<String>(Arrays.asList(folder.list()));
		String namePjWithoutProto = null;
		for (String pjName : staticProjectList) {
			namePjWithoutProto = removeProtocol(pjName);
			if ( !projectListExistInFolder.contains(namePjWithoutProto) ) {
				ListProjectMiss.add(pjName);
			}
		}
		return ListProjectMiss;
	}
	
	public String removeProtocol(String url) {
		if (url.contains("https://")) {
			url = url.replaceAll("https://", "");
		}
		if (url.contains("http://")) {
			url = url.replaceAll("http://", "");
		}
		return url;
	}

	public TimeUnit getTimeUNIT(String property) {
		switch (property) {
			case "SECONDS":
			case "seconds":
				return TimeUnit.SECONDS;
			case "MINUTES":
			case "minutes":
				return TimeUnit.MINUTES;
			case "HOURS":
			case "hours":
				return TimeUnit.HOURS;
			case "DAYS":
			case "days":
				return TimeUnit.DAYS;
			case "MILLISECONDS":
			case "milliseconds":
				return TimeUnit.MILLISECONDS;
		}
		return null;
	}
	
	
	/* SAVE JSON JACKSONN*/
	protected static ObjectMapper objectMapper = new ObjectMapper();
	
	public synchronized static boolean saveJsonFile(Object map, String pathTarget) {
		try (FileWriter file = new FileWriter(pathTarget)) {
			objectMapper.writeValue(file, map);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	public synchronized SearchResults loadJsonModelInfoFile(String pathJsonFile ) {
		TypeReference<SearchResults> typeRef = new TypeReference<SearchResults>() {};
		SearchResults mp = null;
		try {
			byte[] jsonData = Files.readAllBytes(Paths.get(pathJsonFile));
			mp = objectMapper.readValue(jsonData, typeRef);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return mp;
	}
	
	private SimpleDateFormat formata;
	protected String getdataEhoraFormat(final Date date,String format) {
		formata = new SimpleDateFormat(format);
		return formata.format(date);
	}
}
