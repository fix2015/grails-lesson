package zport.admin

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.*

@Transactional(readOnly = false)
class ZportController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Zport.list(params), model:[zportCount: Zport.count()]
    }

    def getjson() {
        //params.max = Math.min(max ?: 10, 100)
        def results =  Zport.list(params) as JSON
        results = {ok: '1111'}
        render(contentType: 'text/json') {[
                'results': results,
                'status': results ? "OK" : "Nothing present"
        ]}
    }

    def show(Zport zport) {
        println 'show'
        println zport as JSON
        respond zport
    }

    def create() {
        respond new Room(params)
        respond new Zport(params), view:'create'
    }

    @Transactional
    def save(Zport zport) {
        println zport as JSON

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


/*
        String fileContents = new File('restaurants.json').text
        def fileArray = JSON.parse(fileContents)
        fileArray.each {
           // if(it.id == null){
                println it
                def zportData = new Zport(
                        "title":it.title,
                        "type":it.type,
                        "phone":it.phone ? it.phone : 'test',
                        "address":it.address ? it.address.toString() : 'test',
                        "description":it.description ? it.description.toString() : 'test',
                        "folder":it.folder ? it.folder : 'test',
                        "distance":it.distance,
                        "children":it.children.toBoolean(),
                        "conditioner":it.conditioner.toBoolean(),
                        "dush":it.dush.toBoolean(),
                        "eat":it.eat.toBoolean(),
                        "toilet":it.toilet.toBoolean(),
                        "tv": it.tv ? it.tv.toBoolean() : true ,
                        "wifi":it.wifi ? it.wifi.toBoolean() : true,
                        "refrigeter":it.refrigeter ? it.refrigeter.toBoolean() : true,
                        "swiming":it.swiming ? it.swiming.toBoolean() : true,
                        "lat":it.lat,
                        "lng":it.lng,
                        "room":[]).save(flush: true);
                def zportSaveData = zportData as JSON
                def zportSaveDataJSON = JSON.parse(zportSaveData.toString())
*//*                println 'zport'
                println it.title
                println zportSaveDataJSON.id*//*

                it.room.each {
                    def roomtData = new Room("title": it.title ?  it.title : 'test',
                            "dush": it.dush ?  it.dush.toBoolean() : false ,
                            "conditioner": it.conditioner ?  it.conditioner.toBoolean() : false,
                            "toilet": it.toilet ?  it.toilet.toBoolean() : false,
                            "tv": it.tv ?  it.tv.toBoolean() : false,
                            "wifi": it.wifi ?  it.wifi.toBoolean() : false,
                            "swiming": it.swiming ?  it.swiming.toBoolean() : false,
                            "refrigeter": it.refrigeter ?  it.refrigeter.toBoolean() : false,
                            "folderImg": it.folderImg ?  it.folderImg : 'test').save(flush: true);
*//*                    println 'roomtData'*//*
                    def roomtSaveData = roomtData as JSON
                    def roomtSaveDataJSON = JSON.parse(roomtSaveData.toString())
*//*                    println roomtSaveData
                    println roomtSaveDataJSON.id*//*
                    Zport.get(zportSaveDataJSON.id).addToRoom(roomtData).save(flush: true)
                    it.price.each {
*//*                        println it*//*
                        def arrayFirst = it.'0'
                        def arraySecond = it.'1'
*//*                        println arrayFirst
                        println arraySecond*//*
                        def priceData = new Price("mounth": arrayFirst, "price" :arraySecond ? arraySecond : 100).save(flush: true);
                        def priceSaveData = priceData as JSON
                        def priceSaveDataJSON = JSON.parse(priceSaveData.toString())
*//*                        println priceSaveData
                        println priceSaveDataJSON.id*//*
                        Room.get(roomtSaveDataJSON.id).addToPrice(priceData).save(flush: true)
                    }
                }
           // }

        }*/
