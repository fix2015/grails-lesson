package zport.admin

class Zport {

    String title
    String type
    Integer distance
    Boolean children = false
    Boolean conditioner = false
    Boolean dush = false
    Boolean eat = false
    Boolean toilet = false
    Boolean tv = false
    Boolean wifi = false
    Boolean refrigeter = false
    Boolean swiming = false
    Boolean lat = false
    Boolean lng = false

    static hasMany = [room:Room]

    static constraints = {
        title nullable: false
        type inList: ['chast','pansionat','hotel','sanatoriy','otel','children','basi','room']
        distance nullable: false
        children nullable: false
        conditioner nullable: false
        dush nullable: false
        eat nullable: false
        toilet nullable: false
        tv nullable: false
        wifi nullable: false
        refrigeter nullable: false
        swiming nullable: false
        lat nullable: false
        lng nullable: false
        room display: true
    }
}