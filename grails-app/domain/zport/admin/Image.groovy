package zport.admin

class Image {
    String name

    static belongsTo = [Zport, Room]

    static constraints = {
        name nullable: false

    }
}
