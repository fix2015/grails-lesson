package zport.admin

class Zport {

    String title
    String type
    String folder
    String distance
    String phone
    String address
    String description
    Boolean children = false
    Boolean conditioner = false
    Boolean dush = false
    Boolean eat = false
    Boolean toilet = false
    Boolean tv = false
    Boolean wifi = false
    Boolean refrigeter = false
    Boolean swiming = false
    String lat = false
    String lng = false

    static hasMany = [room:Room, image:Image, comments: Comments]

    static mapping = {
        phone type: 'text'
        address type: 'text'
        description type: 'text'
    }
    static constraints = {
        title nullable: false
        type inList: ['chast','pansionat','hotel','sanatoriy','otel','children','basi','room']
        phone nullable: false
        address nullable: false
        description nullable: false
        folder nullable: false
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
        room display: false
        image display: false
    }
}