
package lucas.tabelation;

import lucas.tabelation.generators.DBGeneratorMySql;
import lucas.tabelation.generators.DBGenerator;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import lucas.tabelation.components.Field;
import lucas.tabelation.components.Schema;
import lucas.tabelation.components.Table;
import lucas.tabelation.types.AbstractType;

/**
 *
 * @author lucas campestrini <lucas.campestrini@gmail.com>
 */
public class Tabelation {

    public static void main(String[] args) {
        teste_com_primary_key();
        teste_com_foreign_key();
        teste_com_tabela_associativa();
    }
    
    public static void teste_com_primary_key() {
        System.out.println("Teste apenas com primary key");
        Schema teste = new Schema("testando");
        Table t1 = teste.addTable("pessoa");
        Field f1 = t1.addField("codigo", AbstractType.INT);
        Field f2 = t1.addField("nome", AbstractType.TEXT);
        Field f3 = t1.addField("filme", AbstractType.TEXT);
        f1.setIsPrimaryKey(true);
        
        DBGenerator dbg = new DBGeneratorMySql(teste);
        
        System.out.println(dbg.getSql());
                
    }
    
    public static void teste_com_foreign_key() {
        System.out.println("Teste apenas com foreign key");
        Schema teste = new Schema("testando");
        Table t1 = teste.addTable("diretor");
        Field t1f1 = t1.addField("diretor_codigo", AbstractType.INT);
        Field t1f2 = t1.addField("diretor_nome", AbstractType.TEXT);
        Field t1f3 = t1.addField("diretor_filme", AbstractType.TEXT);
        t1f1.setIsPrimaryKey(true);
        
        Table t2 = teste.addTable("filme");
        Field t2f1 = t2.addField("filme_codigo", AbstractType.INT);
        Field t2f2 = t2.addField("filme_nome", AbstractType.TEXT);
        Field t2f3 = t2.addField("diretor_codigo", AbstractType.INT);
        t2f1.setIsPrimaryKey(true);
        
        //para setar um campo como foreign ky basta setar de que tabela ele é
        //uma chave estrangeira. O framework obriga que os nomes sejam iguais
        t2f3.setforeignKeyTable(t1);
        
        DBGenerator dbg = new DBGeneratorMySql(teste);
        System.out.println(dbg.getSql());
    }
    
    public static void teste_com_tabela_associativa() {
        System.out.println("Teste com associativa");
        
        // utilizando a api para criar a estrutura das tabelas
        
        Schema dbeventos = new Schema("dbeventos");
        
        Table tbeventos = dbeventos.addTable("evento");
        tbeventos.addField("eventocodigo", AbstractType.INT).setIsPrimaryKey(true).setIsUnique(Boolean.TRUE).setIsNotNull(Boolean.TRUE);
        tbeventos.addField("eventonumeroconvidados", AbstractType.INT);
        
        Table tbconvidados = dbeventos.addTable("convidado");
        tbconvidados.addField("convidadocodigo", AbstractType.INT).setIsPrimaryKey(true);
        tbconvidados.addField("convidadonome", AbstractType.TEXT).setIsPrimaryKey(true);
        
        tbeventos.addNtoNRelationship(tbconvidados);

        //criando a conexao(a cargo do cliente) e passando ao objeto gerador 
        
        try (Connection conn = getMysqlConnetion()) {
            System.out.println("Database connected!");
            DBGenerator dbg = new DBGeneratorMySql(dbeventos, conn);
            System.out.println(dbg.getSql());
            dbg.generate();
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
        
    }
        
    public static Connection getMysqlConnetion() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/";
        String username = "lucas";
        String password = "1743";

        System.out.println("Connecting database ...");

        Connection conn = DriverManager.getConnection(url, username, password);
        return conn;
    }
    
}