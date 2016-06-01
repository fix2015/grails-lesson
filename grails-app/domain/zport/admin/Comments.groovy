package zport.admin

class Comments {
    String username
    String email
    String message
    Date dateCreated = new Date()


    static belongsTo = [ zport : Zport ]

    static constraints = {
        username nullable: false,  size: 2..150
        email email: true, blank: false
        message nullable: false, size: 2..1000
    }
}
