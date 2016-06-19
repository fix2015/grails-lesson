package zport.admin

class User {
    String first_name
    String last_name
    String email
    String network
    String profile
    String uid
    String identity
    String verified_email
    Number user_id
    Number ident
    String seed

    static constraints = {
        first_name nullable: false
        last_name nullable: false
        email email: true, blank: false
        network nullable: false
        profile nullable: false
        uid nullable: false
        identity nullable: false
        verified_email nullable: false
        user_id nullable: false
        ident nullable: false
        seed nullable: false
    }
}
