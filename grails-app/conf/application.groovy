dataSource {
    pooled = true
    jmxExport = true
    driverClassName = "org.postgresql.Driver"
    username = "postgres"
    password = "admin"
}
environments {
    development {
        dataSource {
            dbCreate = "update"
            username = 'postgres'
            password = 'admin'
            url = "jdbc:postgresql://localhost:5432/test"
        }
    }
    production {
        dataSource {
            dbCreate = 'validate'
            url = 'jdbc:postgresql://localhost:5432/mydb'
        }
    }
}