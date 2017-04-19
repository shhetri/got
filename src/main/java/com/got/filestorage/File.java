package com.got.filestorage;

import com.got.filestorage.contracts.IFile;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class File implements IFile {
    private static final String STORAGE_PATH = String.format("%s/storage/", System.getProperty("user.dir"));

    public void save(String filename, Object object) {

        Path directoryPath = FileSystems.getDefault().getPath(STORAGE_PATH, getDirectory(object.getClass()));
        directoryPath.toFile().mkdirs();

        ObjectOutputStream out = null;
        try {
            Path absolutePath = FileSystems.getDefault().getPath(STORAGE_PATH, getDirectory(object.getClass()), filename);
            out = new ObjectOutputStream(Files.newOutputStream(absolutePath));
            out.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception ignored) {
                }
            }
        }
    }

    public <T> T retrieve(String filename, Class<T> type) {
        ObjectInputStream in = null;
        T object = null;
        try {
            Path absolutePath = FileSystems.getDefault().getPath(STORAGE_PATH, getDirectory(type), filename);
            in = new ObjectInputStream(Files.newInputStream(absolutePath));
            object = type.cast(in.readObject());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception ignored) {
                }
            }
        }

        return object;
    }

    public <T> List<T> retrieveAll(Class<T> type) {
        List<T> collection = new ArrayList<>();
        java.io.File folder = new java.io.File(FileSystems.getDefault().getPath(STORAGE_PATH, getDirectory(type)).toString());
        java.io.File[] listOfFiles = folder.listFiles();

        for (java.io.File file : listOfFiles) {
            if (file.isFile()) {
                collection.add(retrieve(file.getName(), type));
            }
        }

        return collection;
    }

    private String getDirectory(Class<?> clazz) {
        return clazz.getPackage().getName().replaceAll("\\.", "")+clazz.getSimpleName().toLowerCase();
    }
}
