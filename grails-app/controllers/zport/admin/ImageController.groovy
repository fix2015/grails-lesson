package zport.admin

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.*

@Transactional(readOnly = false)
class ImageController {
    def FileService
    def ZportService
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Image.list(params), model:[imageCount: Image.count()]
    }

    def show(Image image) {
        respond image
    }

    def create() {
        respond new Image(params)
    }

    @Transactional
    def save(Image image) {
        if (image == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (image.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond image.errors, view:'create'
            return
        }

        image.save flush:true

        def f =  request.getFile("file")
        def webrootDir = servletContext.getRealPath("/")
        def nameFile = f.getOriginalFilename()

        if(!FileService.validationFile(f) || f.isEmpty()){
            redirect(controller: "zport", action: "edit", id: params.zport.id)
        }else{
            if(params.folder == 'zport'){
                new File(webrootDir,"images/${params.folder}/${params.zport.id}").mkdirs()
                File fileDest = new File(webrootDir,"images/${params.folder}/${params.zport.id}/${nameFile}")
                f.transferTo(fileDest)
                params.name = nameFile
                Zport.get(params.zport.id).addToImage(image).save(flush: true)
                redirect(controller: "zport", action: "edit", id: params.zport.id)
/*
                ZportService.appendToImage(params.id, image);
*/
            }else if(params.folder == 'room'){
/*
                RoomService.appendToImage(params.id, image);
*/
                new File(webrootDir,"images/${params.folder}/${params.room.id}").mkdirs()
                File fileDest = new File(webrootDir,"images/${params.folder}/${params.room.id}/${nameFile}")
                f.transferTo(fileDest)
                params.name = nameFile
                Room.get(params.room.id).addToImage(image).save(flush: true)
                redirect(controller: "room", action: "edit", id: params.room.id)
            }else{
                redirect(controller: "zport", action: "index")
            }

        }


/*        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'image.label', default: 'Image'), image.id])
                redirect image
            }
            '*' { respond image, [status: CREATED] }
        }*/
    }

    def edit(Image image) {
        respond image
    }

    @Transactional
    def update(Image image) {
        if (image == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (image.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond image.errors, view:'edit'
            return
        }

        image.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'image.label', default: 'Image'), image.id])
                redirect image
            }
            '*'{ respond image, [status: OK] }
        }
    }

    @Transactional
    def delete(Image image) {

        if (image == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        image.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'image.label', default: 'Image'), image.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'image.label', default: 'Image'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
