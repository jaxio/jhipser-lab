$output.java("${Model.packageName}.util", "FixedH2Dialect")##

import java.sql.Types;

import org.hibernate.dialect.H2Dialect;

public class $output.currentClass extends H2Dialect {

    public ${output.currentClass}() {
        super();
        registerColumnType(Types.FLOAT, "real");
    }
}
