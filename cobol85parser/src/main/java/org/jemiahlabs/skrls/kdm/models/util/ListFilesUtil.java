package org.jemiahlabs.skrls.kdm.models.util;

import java.io.File;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ListFilesUtil {
	private Consumer<File> listener;
	
	public ListFilesUtil(Consumer<File> listener) {
		this.listener = listener;
	}
	
	public void listFiles(String directoryName) {
		listFiles(directoryName, file -> true);
    }
	
	public void listFiles(String directoryName, Predicate<File> fileValid) {
        File directory = new File(directoryName);
        File[] fList = directory.listFiles();

        for (File file : fList) {
            if ( file.isFile() && fileValid.test(file)) {
                listener.accept(file);
            } else if (file.isDirectory()){
            	listFiles(file.getAbsolutePath(), fileValid);
            }
        }
    }
	
    public Consumer<File> getListener() {
    	return listener;
    }
}