package zport.admin

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.*
import grails.util.Holders

@Transactional(readOnly = false)
class ZportController {
    def ZportService
    def FileService
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Zport.list(params), model:[zportCount: Zport.count()]
    }

    def test() {
        ZportService.recDataToDb();
        render {}
    }
    def savejson() {
        ZportService.saveJSON();
        render {}
    }

    def getjson() {
        JSON.use('deep')
        render Zport.getAll() as JSON
    }

    def show(Zport zport) {
        respond zport
    }

    def create() {
        respond new Room(params)
        respond new Zport(params), view:'create'
    }

    @Transactional
    def save(Zport zport) {
        if (zport == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (zport.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond zport.errors, view:'create'
            return
        }

        zport.save flush:true
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'zport.label', default: 'Zport'), zport.id])
                redirect zport
            }
            '*' { respond zport, [status: CREATED] }
        }
    }

    def edit(Zport zport) {
        respond zport
    }

    @Transactional
    def update(Zport zport) {
        if (zport == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }
        if (zport.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond zport.errors, view:'edit'
            return
        }

        zport.save flush:true
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'zport.label', default: 'Zport'), zport.id])
                redirect zport
            }
            '*'{ respond zport, [status: OK] }
        }
    }

    @Transactional
    def delete(Zport zport) {

        if (zport == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        zport.delete flush:true
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'zport.label', default: 'Zport'), zport.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'zport.label', default: 'Zport'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}