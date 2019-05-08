package com.okcoin.house; /**
 *
 */

import java.sql.Types;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

/**
 * @author liupeng
 */
public class JavaTypeResolverImpl extends JavaTypeResolverDefaultImpl {

    /**
     *
     */
    public JavaTypeResolverImpl() {
        super();

        // 更改TINYINT 映射为int
        typeMap.put(Types.TINYINT, new JdbcTypeInformation("TINYINT",
                new FullyQualifiedJavaType(Integer.class.getName())));
        typeMap.put(Types.SMALLINT, new JdbcTypeInformation("SMALLINT",
                new FullyQualifiedJavaType(Integer.class.getName())));

        typeMap.put(Types.BLOB, new JdbcTypeInformation("BLOB",
                new FullyQualifiedJavaType(Object.class.getName())));
    }

}
