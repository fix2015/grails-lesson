package zport.admin

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.*

@Transactional(readOnly = true)
class RoomController {
    def FileService
    def ZportService
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Room.list(params), model:[roomCount: Room.count()]
    }

    def show(Room room) {
        respond room
    }

    def create() {
        respond new Room(params)
    }

    @Transactional
    def save(Room room) {
        if (room == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (room.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond room.errors, view:'create'
            return
        }

        room.save flush:true
        if(params.zport.id) {
            Zport.get(params.zport.id).addToRoom(room).save(flush: true)
        }
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'room.label', default: 'Room'), room.id])
                redirect room
            }
            '*' { respond room, [status: CREATED] }
        }
    }

    def edit(Room room) {
        respond room
    }

    @Transactional
    def update(Room room) {
        if (room == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (room.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond room.errors, view:'edit'
            return
        }

        room.save flush:true
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'room.label', default: 'Room'), room.id])
                redirect room
            }
            '*'{ respond room, [status: OK] }
        }
    }

    @Transactional
    def delete(Room room) {

        if (room == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if(params.type == 'zport'){
            def zport = Zport.get(params.zport.id)
            def pic = zport.room.find { it.id == params.id.toInteger() }
            zport.removeFromRoom(pic)
            redirect(controller: "zport", action: "edit", id: params.zport.id)
        }

        room.delete flush:true
/*        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'room.label', default: 'Room'), room.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }*/
    }

    def upload (Room room) {
        def f =  request.getFile("file")
        def webrootDir = servletContext.getRealPath("/")
        def nameFile = f.getOriginalFilename()

        if(!FileService.validationFile(f) || f.isEmpty()){
            redirect(controller: "room", action: "edit", id: params.id)
        }else{
            new File(webrootDir,"images/${params.folder}/${params.id}").mkdirs()
            File fileDest = new File(webrootDir,"images/${params.folder}/${params.id}/${nameFile}")
            f.transferTo(fileDest)
            redirect(controller: "room", action: "edit", id: params.id)
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'room.label', default: 'Room'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
