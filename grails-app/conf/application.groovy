import grails.util.Environment
import grails.util.Holders

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
        filesDir = "/Users/semianchuk/ideaProject/first-webapp-react/"
    }
    production {
        dataSource {
            dbCreate = "update"
            username = 'postgres'
            password = 'admin'
            url = "jdbc:postgresql://localhost:5432/test"
        }
        filesDir = "/home/molot/zport/"
    }
}