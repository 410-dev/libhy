package me.hysong.libhyextended.objects;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.Setter;
import me.hysong.libhycore.CoreLogger;
import me.hysong.libhyextended.sql.SQLConnection;

import me.hysong.libhyextended.sql.SQLConnectionFactory;
import me.hysong.libhyextended.objects.exception.DataFieldMismatchException;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Getter
public abstract class DatabaseObject extends DataObject {

    private Class<SQLConnectionFactory> sqlConnectionFactory;
    private ArrayList<String> nonColumnFields = new ArrayList<>();
    @Setter private String pkName;
    @Setter private String pkValue;
    @Setter private String pkType;
    @Setter private String tableName;

    public static boolean doPrintQuery = false;

    public DatabaseObject(String tableName, Class<?> sqlConnectionFactoryClass) {
        this.sqlConnectionFactory = (Class<SQLConnectionFactory>) sqlConnectionFactoryClass;
        this.tableName = tableName;
    }


    public DatabaseObject(Class<?> sqlConnectionFactoryClass) {
        this(null, sqlConnectionFactoryClass);
    }

    public static <T> ArrayList<?> buildListFromResultSet(ResultSet rs, Class<T> clazz) throws SQLException, DataFieldMismatchException {
        ArrayList<T> list = new ArrayList<>();
        while (rs.next()) {
            try {
                DatabaseObject object = (DatabaseObject) clazz.getDeclaredConstructor().newInstance();
                object.mapFromResultSet(rs);
                list.add(clazz.cast(object));
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    public void setKeys(String pkName, String pkValue, String tableName) {
        this.pkName = pkName;
        this.pkValue = pkValue;
        this.tableName = tableName;
    }

    private void checkKeys() {
//        if (this.pkName == null) throw new UndefinedSQLKeyException("pkName");
//        if (this.pkType == null) throw new UndefinedSQLKeyException("pkType");
//        if (this.tabl`eName == null) throw new UndefinedSQLKeyException("tableName");
    }

    public void addNonColumnField(String fieldName) {
        this.nonColumnFields.add(fieldName);
    }

    private boolean canSkip(String fieldName) {

        String[] skippableFieldNames = new String[]{
            "pkName",
            "pkValue",
            "pkType",
            "tableName",
            "sqlConnectionFactory"
        };

        for (String skippableFieldName : skippableFieldNames) {
            if (skippableFieldName.equals(fieldName)) return true;
        }

        for (String nonColumnField : nonColumnFields) {
            if (nonColumnField.equals(fieldName)) return true;
        }

        return false;
    }

    public void insert() throws SQLException {

        this.checkKeys();

        StringBuilder sql = new StringBuilder("INSERT INTO " + this.getTableName() + " (");
        StringBuilder values = new StringBuilder("VALUES (");

        try {

            for (Field field : this.getClass().getDeclaredFields()) {
                if (canSkip(field.getName())) continue;

                field.setAccessible(true);

                sql.append(field.getName()).append(", ");
                values.append("'").append(field.get(this)).append("', ");
            }

            sql.delete(sql.length() - 2, sql.length());
            values.delete(values.length() - 2, values.length());

            sql.append(") ").append(values).append(");");
            SQLConnection connection = sqlConnectionFactory.getDeclaredConstructor().newInstance().getConnection();
            connection.executeUpdate(sql.toString());
            connection.close();
            if (doPrintQuery) System.out.println(sql.toString());
        }catch (SQLException e) {
            throw e;
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete() throws SQLException {

        this.checkKeys();

        StringBuilder sql = new StringBuilder("DELETE FROM " + this.getTableName() + " WHERE " + this.getPkName() + " = '" + this.getPkValue() + "';");

        try {
            SQLConnection connection = sqlConnectionFactory.getDeclaredConstructor().newInstance().getConnection();
            connection.executeUpdate(sql.toString());
            connection.close();
            if (doPrintQuery) System.out.println(sql.toString());
        }catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() throws SQLException {

        this.checkKeys();

        StringBuilder sql = new StringBuilder("UPDATE " + this.getTableName() + " SET ");

        try {
            for (Field field : this.getClass().getDeclaredFields()) {
                if (canSkip(field.getName())) continue;

                field.setAccessible(true);

                sql.append(field.getName()).append(" = '").append(field.get(this)).append("', ");
            }

            sql.delete(sql.length() - 2, sql.length());

            sql.append(" WHERE ").append(this.getPkName()).append(" = '").append(this.getPkValue()).append("';");


            SQLConnection connection = sqlConnectionFactory.getDeclaredConstructor().newInstance().getConnection();
            connection.executeUpdate(sql.toString());
            connection.close();
            if (doPrintQuery) System.out.println(sql.toString());
        }catch (SQLException e) {
            throw e;
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void select() throws SQLException, DataFieldMismatchException {

        this.checkKeys();

        StringBuilder sql = new StringBuilder("SELECT * FROM " + this.getTableName() + " WHERE " + this.getPkName() + " = '" + this.getPkValue() + "' LIMIT 1;");
        if (doPrintQuery) System.out.println(sql.toString());

        SQLConnection connection = null;
        try {
            connection = sqlConnectionFactory.getDeclaredConstructor().newInstance().getConnection();;
        }catch (Exception e) {
            e.printStackTrace();
            return;
        }

        connection.setUseAutoClose(true);

        ResultSet rs = connection.executeQuery(sql.toString());
        if (!rs.next()) {
            throw new SQLException("No results found for query: " + sql.toString());
        }

        mapFromResultSet(rs);
    }

    public void mapFromResultSet(ResultSet rs) throws DataFieldMismatchException, SQLException {
        if (rs.isClosed()) {
            CoreLogger.debug(CoreLogger.EventType.ERROR, "ResultSet is closed!");
        }
        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {

                // Get type
                Class<?> type = field.getType();
                String typeName = type.getName().toLowerCase();

                if (canSkip(field.getName())) continue;

                if (typeName.contains("int")) field.set(this, rs.getInt(field.getName()));
                else if (typeName.contains("string")) field.set(this, rs.getString(field.getName()));
                else if (typeName.contains("boolean")) field.set(this, rs.getBoolean(field.getName()));
                else if (typeName.contains("double")) field.set(this, rs.getDouble(field.getName()));
                else if (typeName.contains("float")) field.set(this, rs.getFloat(field.getName()));
                else if (typeName.contains("long")) field.set(this, rs.getLong(field.getName()));
                else if (typeName.contains("short")) field.set(this, rs.getShort(field.getName()));
                else if (typeName.contains("byte")) field.set(this, rs.getByte(field.getName()));
                else if (typeName.contains("char")) field.set(this, rs.getByte(field.getName()));
                else if (type.getSuperclass().getName().equals(DataObject.class.getName())) {
                    JsonObject object = JsonParser.parseString(rs.getString(field.getName())).getAsJsonObject();
                    DataObject dataObject = (DataObject) type.getDeclaredConstructor().newInstance();
                    dataObject.fromJson(object);
                    field.set(this, dataObject);
                }
                else CoreLogger.debug(CoreLogger.EventType.ERROR, "Unknown type: " + typeName + " with type " + type.getName().toLowerCase());

            } catch (Exception e) {
                CoreLogger.debug(CoreLogger.EventType.ERROR, "Failed to import " + field.getName() + " from Json.");
                throw new DataFieldMismatchException(e);
            }
        }
    }
}
