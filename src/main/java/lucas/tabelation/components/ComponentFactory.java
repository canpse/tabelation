package lucas.tabelation.components;

import lucas.tabelation.types.AbstractType;

/**
 * Component Factory
 * 
 *
 * @author lucas campestrini <lucas.campestrini@gmail.com>
 */
public class ComponentFactory {
    
    public static Field field(String name, AbstractType type,Table table) {
        return new Field(name, type, table);
    }
    
    public static Table table(String name) {
        return new Table(name);
    }
    
}
