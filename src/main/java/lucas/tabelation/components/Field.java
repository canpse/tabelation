package lucas.tabelation.components;

import java.util.HashMap;
import lucas.tabelation.types.AbstractType;

/**
 *
 * @author lucas campestrini <lucas.campestrini@gmail.com>
 */
public class Field {
    
    private Table table;
    private final String name;
    private final AbstractType typename;
    private String value;
    private Boolean isNotNull = false;
    private Boolean isUnique = false;
    private Table foreignKeyTable;
    private Boolean createIndex;

    public Field(String name, AbstractType typename, Table table) {
        this.name = name;
        this.typename = typename;
        this.table = table;
    }
    
    public String getName() {
        return name;
    }

    public AbstractType getType() {
        return typename;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Boolean getIsNotNull() {
        return isNotNull;
    }

    public Field setIsNotNull(Boolean isNotNull) {
        this.isNotNull = isNotNull;
        return this;
    }

    public Table getforeignKeyTable() {
        return foreignKeyTable;
    }

    /**
     * Chaves estrangeiras precisam ter o mesmo nome de colunas
     * 
     * @param foreignKeyTable 
     */
    public void setforeignKeyTable(Table foreignKeyTable) {
        Boolean fieldExists = false;
        for (Field field : foreignKeyTable.getFields()) {
            if (field.getName().equals(this.getName())) {
                fieldExists = true;
            }
        }
        if (!fieldExists) {
            throw new Error("O nome de uma chave estrangeira deve ser igual a da sua tabela de origem");
        }
        this.table.addForeignKey(this);
        this.foreignKeyTable = foreignKeyTable;
    }

    public Boolean getCreateIndex() {
        return createIndex;
    }

    public void setCreateIndex(Boolean createIndex) {
        this.createIndex = createIndex;
    }

    public Boolean getIsUnique() {
        return isUnique;
    }

    public Field setIsUnique(Boolean isUnique) {
        this.isUnique = isUnique;
        return this;
    }

    public Boolean getIsPrimaryKey() {
        return this.table.getPrimaryKey().equals(this);
    }

    public Field setIsPrimaryKey(Boolean isPrimaryKey) {
        this.table.setPrimaryKey(this);
        return this;
    }

    public boolean equals(Field field) {
        return this.name == field.getName();
    }
    
}
