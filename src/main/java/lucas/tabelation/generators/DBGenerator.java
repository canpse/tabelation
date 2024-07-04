package lucas.tabelation.generators;

import lucas.tabelation.components.Schema;

/**
 *
 * @author lucas campestrini <lucas.campestrini@gmail.com>
 */
public interface DBGenerator {
    
    void generate();
    void setDB(Schema db);
    String getSql();
    
}
