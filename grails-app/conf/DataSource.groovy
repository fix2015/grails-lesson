dataSource {
    pooled = false
    driverClassName = "org.hsqldb.jdbcDriver"
    username = "sa"
    password = ""
}
hibernate {
    cache.use_second_level_cache=true
    cache.use_query_cache=true
    cache.provider_class='org.hibernate.cache.EhCacheProvider'
}
// environment specific settings
environments {
    development {
        dataSource {
            factory = "org.apache.tomcat.jdbc.pool.DataSourceFactory"
            driverClassName = "org.postgresql.Driver"
            type = "com.mchange.v2.c3p0.ComboPooledDataSource"
            url = "jdbc:postgresql://localhost:5432/test"
            username = 'postgres'
            password = 'admin'
            dbCreate = 'update'
        }
    }
    test {
        dataSource {
            url = "jdbc:postgresql://localhost:5432/test"
            dbCreate = 'update'
        }
    }
    production {
        dataSource {
            factory = "org.apache.tomcat.jdbc.pool.DataSourceFactory"
            driverClassName = "org.postgresql.Driver"
            type = "com.mchange.v2.c3p0.ComboPooledDataSource"
            url = "jdbc:postgresql://localhost:5432/test"
            properties {
                defaultTransactionIsolation = java.sql.Connection.TRANSACTION_READ_COMMITTED
                maxActive = -1
                maxIdle = 50
                minIdle = 10
                minEvictableIdleTimeMillis = 60000
                timeBetweenEvictionRunsMillis = 5000
                numTestsPerEvictionRun = 3
                removeAbandonedTimeout = 60
                maxAge = 10 * 60000
                validationInterval = 15000
                jdbcInterceptors = "ResetAbandonedTimer"
                //removeAbandoned=true
                //logAbandoned=false
                testOnBorrow = true
                testWhileIdle = true
                testOnReturn = false
                validationQuery = "select 1"
            }
            username = 'postgres'
            password = 'admin'
            dbCreate = 'update'
        }
    }
}