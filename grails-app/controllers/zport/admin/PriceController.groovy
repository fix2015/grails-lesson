package zport.admin

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class PriceController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Price.list(params), model:[priceCount: Price.count()]
    }

    def show(Price price) {
        respond price
    }

    def create() {
        respond new Price(params)
    }

    @Transactional
    def save(Price price) {
        if (price == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (price.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond price.errors, view:'create'
            return
        }

        price.save flush:true

        if(params.room.id){
            Room.get(params.room.id).addToPrice(price).save(flush: true)
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'price.label', default: 'Price'), price.id])
                redirect price
            }
            '*' { respond price, [status: CREATED] }
        }
    }

    def edit(Price price) {
        respond price
    }

    @Transactional
    def update(Price price) {
        if (price == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (price.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond price.errors, view:'edit'
            return
        }

        price.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'price.label', default: 'Price'), price.id])
                redirect price
            }
            '*'{ respond price, [status: OK] }
        }
    }

    @Transactional
    def delete(Price price) {

        if (price == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }
        if(params.room.id){
            def room = Room.get(params.room.id)
            def pic = room.price.find { it.id == params.id.toInteger() }
            room.removeFromPrice(pic)
            redirect(controller: "room", action: "edit", id: params.room.id)
        }
        price.delete flush:true

/*        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'price.label', default: 'Price'), price.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }*/
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'price.label', default: 'Price'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
