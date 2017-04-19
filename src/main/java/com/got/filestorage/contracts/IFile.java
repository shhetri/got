package com.got.filestorage.contracts;

import java.util.List;

public interface IFile {

    void save(String filename, Object object);
    <T> T retrieve(String filename, Class<T> type);
    <T> List<T> retrieveAll(Class<T> type);

}
