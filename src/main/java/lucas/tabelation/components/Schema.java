package lucas.tabelation.components;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author lucas campestrini <lucas.campestrini@gmail.com>
 */
public class Schema {
    
    private String name;
    private List<Table> tables;

    public Schema(String name) {
        this.name = name;
        this.tables = new ArrayList<Table>();
    }
    
    public String getName() {
        return name;
    }

    public List<Table> getTables() {
        return tables;
    }
    
    public Table addTable(String name) {
        Table t = ComponentFactory.table(name); 
        this.tables.add(t);
        t.setSchema(this);
        return t;
    }
    
    public Table addTable(Table table) {
        this.tables.add(table);
        return table;
    }
    
    
}
