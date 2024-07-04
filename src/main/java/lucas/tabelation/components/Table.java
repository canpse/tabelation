package lucas.tabelation.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lucas.tabelation.types.AbstractType;

/**
 *
 * @author lucas campestrini <lucas.campestrini@gmail.com>
 */
public class Table {
    
    private String name;
    private List<Field> fields;
    private Field primaryKey;
    private Set<Field> foreignKeys;
    private Schema schema;

    public Table(String name) {
        this.name = name;
        fields = new ArrayList<Field>();
        foreignKeys = new HashSet<Field>();
    }
    
    public String getName() {
        return name;
    }

    public List<Field> getFields() {
        return fields;
    }
    
    public Field addField(String name, AbstractType type) {
        Field f = ComponentFactory.field(name, type, this);
        this.fields.add(f);
        return f;
    }

    public Field getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(Field primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Set<Field> getForeignKeys() {
        return foreignKeys;
    }

    public void addForeignKey(Field field) {
        this.foreignKeys.add(field);
    }
    
    /**
     * Retorna a tabela associativa 
     * 
     * @return 
     */
    public Table addNtoNRelationship(Table relation) {
        Table ntonTable = ComponentFactory.table(this.getName() + relation.getName());
        ntonTable.addField(ntonTable.getName() + "codigo", AbstractType.INT).setIsPrimaryKey(true);
        
        Field f1 = ntonTable.addField(relation.getPrimaryKey().getName(), relation.getPrimaryKey().getType());
        f1.setforeignKeyTable(relation);
        
        Field f2 = ntonTable.addField(this.getPrimaryKey().getName(), this.getPrimaryKey().getType());
        f2.setforeignKeyTable(this);
        
        getSchema().addTable(ntonTable);
        
        return ntonTable;
    }

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }
    
}
