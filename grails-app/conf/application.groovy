import grails.util.Environment
import grails.util.Holders

dataSource {
    pooled = true
    jmxExport = true
    driverClassName = "org.postgresql.Driver"
    username = "postgres"
    password = "admin"
}
/*
filesDir = "/Users/semianchuk/ideaProject/first-webapp-react/"
*/
filesDir = "/Users/semianchuk/ideaProject/first-webapp-react/"
environments {
    dev {
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