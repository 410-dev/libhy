package me.hy.libhyextended.objects;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.Setter;
import me.hy.libhyextended.objects.exception.UndefinedSQLKeyException;
import me.hy.libhyextended.sql.SQLConnection;

import me.hy.libhyextended.sql.SQLConnectionFactory;
import me.hy.libhyextended.objects.exception.DataFieldMismatchException;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
public abstract class DatabaseObject extends DataObject {

    private Class<SQLConnectionFactory> sqlConnectionFactory;
    @Setter private String pkName;
    @Setter private String pkValue;
    @Setter private String pkType;
    @Setter private String tableName;

    public DatabaseObject(String tableName, Class<?> sqlConnectionFactoryClass) {
        this.sqlConnectionFactory = (Class<SQLConnectionFactory>) sqlConnectionFactoryClass;
        this.tableName = tableName;
    }

    public DatabaseObject(Class<?> sqlConnectionFactoryClass) {
        this(null, sqlConnectionFactoryClass);
    }

    private void checkKeys() throws UndefinedSQLKeyException {
        if (this.pkName == null) throw new UndefinedSQLKeyException("pkName");
        if (this.pkType == null) throw new UndefinedSQLKeyException("pkType");
        if (this.tableName == null) throw new UndefinedSQLKeyException("tableName");
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
        return false;
    }

    public void insert() throws SQLException, IllegalAccessException, UndefinedSQLKeyException {

        this.checkKeys();

        StringBuilder sql = new StringBuilder("INSERT INTO " + this.getTableName() + " (");
        StringBuilder values = new StringBuilder("VALUES (");

        for (Field field : this.getClass().getDeclaredFields()) {
            if (canSkip(field.getName())) continue;

            field.setAccessible(true);

            sql.append(field.getName()).append(", ");
            values.append("'").append(field.get(this)).append("', ");
        }

        sql.delete(sql.length() - 2, sql.length());
        values.delete(values.length() - 2, values.length());

        sql.append(") ").append(values).append(");");

        try {
            SQLConnection connection = sqlConnectionFactory.getDeclaredConstructor().newInstance().getConnection();
            ResultSet rs = connection.executeQuery(sql.toString());
            rs.close();
            connection.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete() throws SQLException, IllegalAccessException, UndefinedSQLKeyException {

        this.checkKeys();

        StringBuilder sql = new StringBuilder("DELETE FROM " + this.getTableName() + " WHERE " + this.getPkName() + " = '" + this.getPkValue() + "';");

        try {
            SQLConnection connection = sqlConnectionFactory.getDeclaredConstructor().newInstance().getConnection();
            ResultSet rs = connection.executeQuery(sql.toString());
            rs.close();
            connection.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() throws SQLException, IllegalAccessException, UndefinedSQLKeyException {

        this.checkKeys();

        StringBuilder sql = new StringBuilder("UPDATE " + this.getTableName() + " SET ");

        for (Field field : this.getClass().getDeclaredFields()) {
            if (canSkip(field.getName())) continue;

            field.setAccessible(true);

            sql.append(field.getName()).append(" = '").append(field.get(this)).append("', ");
        }

        sql.delete(sql.length() - 2, sql.length());

        sql.append(" WHERE ").append(this.getPkName()).append(" = '").append(this.getPkValue()).append("';");

        try {
            SQLConnection connection = sqlConnectionFactory.getDeclaredConstructor().newInstance().getConnection();
            ResultSet rs = connection.executeQuery(sql.toString());
            rs.close();
            connection.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void select() throws SQLException, IllegalAccessException, UndefinedSQLKeyException, DataFieldMismatchException {

        this.checkKeys();

        StringBuilder sql = new StringBuilder("SELECT * FROM " + this.getTableName() + " WHERE " + this.getPkName() + " = '" + this.getPkValue() + "' LIMIT 1;");


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

        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {

                // Get type
                Class<?> type = field.getType();
                String typeName = type.getName().toLowerCase();

                if (typeName.contains("int")) field.set(this, rs.getInt(field.getName()));
                else if (typeName.contains("string")) field.set(this, rs.getString(field.getName()));
                else if (typeName.contains("boolean")) field.set(this, rs.getBoolean(field.getName()));
                else if (typeName.contains("double")) field.set(this, rs.getDouble(field.getName()));
                else if (typeName.contains("float")) field.set(this, rs.getFloat(field.getName()));
                else if (typeName.contains("long")) field.set(this, rs.getLong(field.getName()));
                else if (typeName.contains("short")) field.set(this, rs.getShort(field.getName()));
                else if (typeName.contains("byte")) field.set(this, rs.getByte(field.getName()));
                else if (typeName.contains("char")) field.set(this, rs.getByte(field.getName()));
                else if (typeName.contains("date")) field.set(this, rs.getDate(field.getName()));
                else if (typeName.contains("time")) field.set(this, rs.getTime(field.getName()));
                else if (typeName.contains("timestamp")) field.set(this, rs.getTimestamp(field.getName()));
                else if (typeName.contains("object")) field.set(this, rs.getObject(field.getName()));
                else if (typeName.contains("array")) field.set(this, rs.getArray(field.getName()));
                else if (typeName.contains("blob")) field.set(this, rs.getBlob(field.getName()));
                else if (typeName.contains("clob")) field.set(this, rs.getClob(field.getName()));
                else if (typeName.contains("ref")) field.set(this, rs.getRef(field.getName()));
                else if (typeName.contains("rowid")) field.set(this, rs.getRowId(field.getName()));
                else if (typeName.contains("sqlxml")) field.set(this, rs.getSQLXML(field.getName()));
                else if (typeName.contains("nclob")) field.set(this, rs.getNClob(field.getName()));
                else if (typeName.contains("nstring")) field.set(this, rs.getNString(field.getName()));
                else if (typeName.contains("null")) field.set(this, rs.getNString(field.getName()));
                else if (typeName.contains("url")) field.set(this, rs.getURL(field.getName()));
                else if (type.getSuperclass().getName().equals(DataObject.class.getName())) {
                    JsonObject object = JsonParser.parseString(rs.getString(field.getName())).getAsJsonObject();
                    DataObject dataObject = (DataObject) type.getDeclaredConstructor().newInstance();
                    dataObject.fromJson(object);
                    field.set(this, dataObject);
                }
                else System.out.println("Unknown type: " + typeName + " with type " + type.isAssignableFrom(DataObject.class));

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Failed to import " + field.getName() + " from Json.");
                throw new DataFieldMismatchException(e);
            }
        }
    }

}
