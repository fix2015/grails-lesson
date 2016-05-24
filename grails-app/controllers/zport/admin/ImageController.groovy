package zport.admin

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.*
import grails.util.Holders

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
        def f =  request.getFile("file")
        def nameFile = f.getOriginalFilename()
        image.name = nameFile
        image.save flush:true
        params.name = nameFile
        params.fotoFolderId = params.zport.id ? params.zport.id : params.room.id

        if(!FileService.validationFile(f) || f.isEmpty()){
            redirect(controller: "zport", action: "edit", id: params.zport.id)
        }else{
            FileService.createFolderForFile(params);
            f.transferTo(FileService.getDestination(params))
            ZportService.saveJSON();
            if(params.folder == 'zport'){
                Zport.get(params.zport.id).addToImage(image).save(flush: true)
                redirect(controller: "zport", action: "edit", id: params.zport.id)
            }else if(params.folder == 'room'){
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
        ZportService.saveJSON();
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
        prinln image as JSON
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
