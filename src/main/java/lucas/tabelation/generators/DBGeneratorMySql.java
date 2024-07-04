package lucas.tabelation.generators;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import lucas.tabelation.components.Field;
import lucas.tabelation.components.Schema;
import lucas.tabelation.components.Table;

/**
 *
 * @author lucas campestrini <lucas.campestrini@gmail.com>
 */
public class DBGeneratorMySql implements DBGenerator{

    private Schema db;
    private Connection con;
    private Collection<String> tableStatements;

    public DBGeneratorMySql(Schema db, Connection con) {
        tableStatements = new ArrayList<String>();
        setDB(db);
        setConnection(con);
    }
    
    public DBGeneratorMySql(Schema db) {
        tableStatements = new ArrayList<String>();
        setDB(db);
    }
    
    @Override
    public void generate() {
        try {
            con.beginRequest();
        } catch (SQLException ex) {
            Logger.getLogger(DBGeneratorMySql.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("on Request: " + ex.getMessage());
        }
        try {
            PreparedStatement currentStatement = con.prepareStatement(getDropDbStatement());
            currentStatement.execute();
            currentStatement.execute(getCreateDbStatement());
            currentStatement.execute(getUseStatement());
            for (String tableStatement : tableStatements) {
                currentStatement.execute(tableStatement);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBGeneratorMySql.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("On execute: " + ex.getMessage() + "State: " + ex.getSQLState());
        }
    }

    @Override
    public void setDB(Schema db) {
       this.db = db;
    }

    @Override
    public String getSql() {
        StringBuilder sql = new StringBuilder();
        String create_db_sql = getDropDbStatement() + "; \n" + getCreateDbStatement() + "; \n" + getUseStatement() + "; \n";
        sql.append(create_db_sql);
        generateTableStatements();
        for (String table : tableStatements) {
            sql.append(table);
        }
        return sql.toString();
    }
    
    private String getDropDbStatement() {
        return "DROP DATABASE IF EXISTS " + db.getName();
    }
    
    private String getCreateDbStatement() {
        return "CREATE DATABASE " + db.getName();
    }
    
    private String getUseStatement() {
        return "USE " + db.getName();
    }
    

    public void setConnection(Connection con) {
        this.con = con;
    }
    
    private Collection<String> generateTableStatements() {
        List<Table> tables = this.db.getTables();
        
        for (Table table : tables) {
            StringBuilder sql = new StringBuilder("\n");
            sql.append("\nCREATE TABLE ").append(table.getName()).append("( \n");
            sql.append(getSqlFields(table));
            if (table.getPrimaryKey() != null) {
                sql.append(",\n");
                sql.append("PRIMARY KEY(" + table.getPrimaryKey().getName() +")");
            }
            if (!table.getForeignKeys().isEmpty()) {
                sql.append(",\n");
                for (Iterator<Field> keys = table.getForeignKeys().iterator(); keys.hasNext();) {
                    Field foreignField = keys.next();
                    sql.append("FOREIGN KEY (")
                       .append(foreignField.getName()).append(") REFERENCES ")
                       .append(foreignField.getforeignKeyTable().getName()).append("(")
                       .append(foreignField.getName()).append(")");
                    if (keys.hasNext()) {
                        sql.append(",\n");
                    }
                }
            }
            sql.append(");\n");
            this.tableStatements.add(sql.toString());
        }
        
        return this.tableStatements;
    }

    private String getSqlFields(Table table) {
        List<Field> fields = table.getFields();
        StringBuilder sb = new StringBuilder();
        for (Iterator<Field> iterator = fields.iterator(); iterator.hasNext();) {
            Field field = iterator.next();
            sb.append(" ");
            sb.append(field.getName() + " ");
            switch (field.getType()) {
                case INT:
                    sb.append("int");
                    break;
                case TEXT:
                    sb.append("varchar(45)");
                    break;
                default:
                    throw new AssertionError("Tipo não implementado");
            }
            if (field.getIsNotNull()) {
                sb.append(" NOT NULL");
            }
            if (field.getIsUnique()) {
                sb.append(" UNIQUE");
            }
            if (iterator.hasNext()) {
                sb.append(",\n");
            }
        }
        return sb.toString();
    }
    
}
