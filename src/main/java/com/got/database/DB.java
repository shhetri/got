package com.got.database;

import com.got.container.ContainerFactory;
import com.got.container.contracts.Container;
import com.got.helpers.StringHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class DB {
    public static DBInfo table(String tableName) {
        DBInfo.tableName = tableName;
        DBInfo.selectionQuery = "SELECT * FROM " + tableName;
        DBInfo.deletionQuery = "DELETE FROM " + tableName;
        DBInfo.insertionQuery = "INSERT INTO " + tableName;
        DBInfo.updationQuery = "UPDATE " + tableName;

        return new DBInfo();
    }

    public static class DBInfo {
        public static String tableName;
        public static String selectionQuery;
        public static String deletionQuery;
        public static String insertionQuery;
        public static String updationQuery;
        public static List<String> parameters = new ArrayList<>();

        public ResultSet rawQuery(String query) {
            try {
                PreparedStatement preparedStatement = DBConnector.connection.prepareStatement(query);

                return preparedStatement.executeQuery();

            } catch (SQLException e) {

                e.printStackTrace();

            }

            return null;
        }

        public ResultSet get() {
            try {
                PreparedStatement preparedStatement = DBConnector.connection.prepareStatement(selectionQuery);

                for (int i = 1; i <= parameters.size(); i++)
                    preparedStatement.setString(i, parameters.get(i - 1));
                parameters.clear();

                return preparedStatement.executeQuery();

            } catch (SQLException e) {

                e.printStackTrace();

            }

            return null;
        }

        public <T> List<T> get(Class<T> clazz) {
            ResultSet resultSet = get();

            List<T> resultList = new ArrayList<T>();

            try {
                while (resultSet.next()) {
                    Container container = ContainerFactory.getDefaultContainer();
                    T object = container.make(clazz);

                    ResultSetMetaData rsmd = resultSet.getMetaData();
                    int columnCount = rsmd.getColumnCount();

                    // The column count starts from 1
                    for (int i = 1; i <= columnCount; i++) {
                        String name = rsmd.getColumnName(i);
                        name = StringHelper.generateSetter(name);
                        try {
                            Method method = clazz.getMethod(name, String.class);
                            method.invoke(object, resultSet.getString(i));
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }

                    resultList.add(object);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return resultList;
        }

        public <T> T getFirst(Class<T> clazz) {
            List<T> result = get(clazz);
            if (result.size() > 0)
                return result.get(0);
            return null;
        }

        public DBInfo values(Map<String, String> insertionParameters) {
            insertionQuery += " (";
            String valuesQuery = ") VALUES (";
            for (Map.Entry<String, String> insertionParameter : insertionParameters.entrySet()) {
                insertionQuery += insertionParameter.getKey() + ",";
                valuesQuery += "?,";
                parameters.add(insertionParameter.getValue());
            }
            insertionQuery = insertionQuery.substring(0, insertionQuery.length() - 1);
            valuesQuery = valuesQuery.substring(0, valuesQuery.length() - 1);
            valuesQuery += ")";
            insertionQuery += valuesQuery;

            return this;
        }

        public int insert() {
            return execute(insertionQuery);
        }

        public DBInfo set(Map<String, String> updateParameters) {
            updationQuery += " SET ";
            for (Map.Entry<String, String> updateParameter : updateParameters.entrySet()) {
                updationQuery += updateParameter.getKey() + "=?,";
                parameters.add(updateParameter.getValue());
            }
            updationQuery = updationQuery.substring(0, updationQuery.length() - 1);

            return this;
        }

        public int update() {
            return execute(updationQuery);
        }

        public int delete() {
            return execute(deletionQuery);
        }

        private int execute(String query) {
            try (PreparedStatement preparedStatement = DBConnector.connection.prepareStatement(query)) {
                for (int i = 1; i <= parameters.size(); i++)
                    preparedStatement.setString(i, parameters.get(i - 1));
                parameters.clear();
                return preparedStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return 0;
        }

        public DBInfo select(String... args) {
            selectionQuery = selectionQuery.replace("*", String.join(",", args));

            return this;
        }

        public DBInfo where(String key, String value) {
            if (selectionQuery.contains("WHERE") || deletionQuery.contains("WHERE") || updationQuery.contains("WHERE")) {
                selectionQuery += key + "=?";
                deletionQuery += key + "=?";
                updationQuery += key + "=?";
            } else {
                selectionQuery += " WHERE " + key + "=?";
                deletionQuery += " WHERE " + key + "=?";
                updationQuery += " WHERE " + key + "=?";
            }
            parameters.add(value);

            return this;
        }

        public DBInfo where(Function<DBInfo, DBInfo> builder) {
            selectionQuery += " WHERE (";
            deletionQuery += " WHERE (";
            updationQuery += " WHERE (";
            builder.apply(this);
            selectionQuery += ")";
            deletionQuery += ")";
            updationQuery += ")";

            return this;
        }

        public DBInfo andWhere(String key, String value) {
            selectionQuery += " AND " + key + "=?";
            deletionQuery += " AND " + key + "=?";
            updationQuery += " AND " + key + "=?";
            parameters.add(value);

            return this;
        }

        public DBInfo orWhere(String key, String value) {
            selectionQuery += " OR " + key + "=?";
            deletionQuery += " OR " + key + "=?";
            updationQuery += " OR " + key + "=?";
            parameters.add(value);

            return this;
        }

        public DBInfo limit(int limit) {
            selectionQuery += " LIMIT " + limit;
            deletionQuery += " LIMIT " + limit;
            updationQuery += " LIMIT " + limit;

            return this;
        }

        public DBInfo orderBy(String column) {
            selectionQuery += " ORDER BY " + column;

            return this;
        }
    }
}
