package cron.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CopyFilesOrDirectorys {

	private static Logger log = LoggerFactory.getLogger(CopyFilesOrDirectorys.class);
	
	public void copy(File sourceLocation, File targetLocation) throws IOException {
		if (sourceLocation.isDirectory()) {
			copyDirectory(sourceLocation, targetLocation);
		} else {
			copyFile(sourceLocation, targetLocation);
		}
	}

	private void copyDirectory(File source, File target) throws IOException {
		if (!target.exists()) {
			target.mkdir();
		}

		for (String f : source.list()) {
			copy(new File(source, f), new File(target, f));
		}
	}

	private void copyFile(File source, File target) throws IOException {
		try (InputStream in = new FileInputStream(source); OutputStream out = new FileOutputStream(target)) {
			byte[] buf = new byte[1024];
			int length;
			while ((length = in.read(buf)) > 0) {
				out.write(buf, 0, length);
			}
		}
	}
	
	public void createFolder(String folderName) {
		File folder = new File(folderName);
		if (!folder.exists()) {
			boolean success = (new File(folderName)).mkdirs();
			if (success) {
			    log.error("Successfully! Create folder... ["+folderName+"]");
			}else {
				log.error("Failed! not create folder... ["+folderName+"]");
			}
		}
	}
	
	public void createAndSaveStringInFile(String fileName,String content) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter(fileName, "UTF-8");
		writer.println(content);
		writer.close();
	}
	public boolean saveTextInFile(String fileName, String content) {
		try (PrintWriter out = new PrintWriter(fileName)) {
		    out.println(content);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
