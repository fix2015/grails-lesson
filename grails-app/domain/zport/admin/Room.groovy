package zport.admin

class Room {
    String title
    String folderImg
    Boolean conditioner
    Boolean dush
    Boolean toilet
    Boolean tv
    Boolean wifi
    Boolean refrigeter
    Boolean swiming

    static hasMany = [price:Price]

    static constraints = {
        title nullable: false
        folderImg nullable: false
        conditioner nullable: false
        dush nullable: false
        toilet nullable: false
        tv nullable: false
        wifi nullable: false
        refrigeter nullable: false
        wifi nullable: false
        refrigeter nullable: false
        swiming nullable: false
    }
}
