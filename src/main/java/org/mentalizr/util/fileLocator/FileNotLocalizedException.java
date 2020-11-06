package org.mentalizr.util.fileLocator;

public class FileNotLocalizedException extends FileLocatorException {

    private String fileName;

    public FileNotLocalizedException(String fileName) {
        super("File not localized: " + fileName);
        this.fileName = fileName;
    }

    public FileNotLocalizedException(String fileName, Object object) {
        super("File not localized by classloader of class " + object.getClass().getCanonicalName() + ": " + fileName);
    }

    public FileNotLocalizedException(String fileName, Class clazz) {
        super("File not localized by classloader of class " + clazz.getCanonicalName() + ": " + fileName);
    }

    public String getFileName() {
        return this.fileName;
    }

}
