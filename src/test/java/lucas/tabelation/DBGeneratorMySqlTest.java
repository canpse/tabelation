package lucas.tabelation;

import lucas.tabelation.generators.DBGeneratorMySql;
import lucas.tabelation.generators.DBGenerator;
import lucas.tabelation.components.Schema;
import lucas.tabelation.components.Table;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author lucas campestrini <lucas.campestrini@gmail.com>
 */
public class DBGeneratorMySqlTest {
    
    public DBGeneratorMySqlTest() {
    }

    /**
     * Test of generate method, of class DBGeneratorMySql.
     */
    @Test
    public void testGenerate() {
    }

    /**
     * Test of setDB method, of class DBGeneratorMySql.
     */
    @Test
    public void testSetDB() {
    }

    /**
     * Test of getSql method, of class DBGeneratorMySql.
     */
    @Test
    public void testGetSqlVerySimple() {
        
        Schema s = new Schema("teste");
        
        Table t1 = s.addTable("tabelateste");
        t1.addField("nome", "varchar");
        t1.addField("idade", "int");
        t1.addField("teste", "VARCHAR");
        
        Table t2 = s.addTable("tabelateste2");
        t2.addField("nome", "varchar");
        t2.addField("idade", "int");
        t2.addField("teste", "VARCHAR");
        
        DBGenerator dbg = new DBGeneratorMySql(s);
        assertEquals(getSimpleSqlExpected(), dbg.getSql());
        
    }
    
    private String getSimpleSqlExpected() {
        return ""
                + "CREATE DATABASE teste\n" +
                "\n" +
                "CREATE TABLE tabelateste2( \n" +
                " idade int,\n" +
                " nome varchar,\n" +
                " teste VARCHAR\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE tabelateste( \n" +
                " nome varchar,\n" +
                " idade int,\n" +
                " teste VARCHAR\n" +
                ");\n";
    }


    /**
     * Test of setConnection method, of class DBGeneratorMySql.
     */
    @Test
    public void testSetConnection() {
    }
    
}
