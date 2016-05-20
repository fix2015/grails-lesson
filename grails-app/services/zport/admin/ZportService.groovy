package zport.admin

import grails.transaction.Transactional
import grails.converters.*

import javax.xml.soap.SOAPMessage

@Transactional
class ZportService {
    Zport createZport() {
        def obj = new LinkedHashMap();
        obj.a = '1'
        println obj.a
        return obj
    }
    def recDataToDb() {
        String fileContents = new File('restaurants.json').text
        def fileArray = JSON.parse(fileContents)
        fileArray.each {
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
            println 'zport'
            println it.title
            println zportSaveDataJSON.id

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
                println 'roomtData'
                def roomtSaveData = roomtData as JSON
                def roomtSaveDataJSON = JSON.parse(roomtSaveData.toString())
                println roomtSaveData
                println roomtSaveDataJSON.id
                Zport.get(zportSaveDataJSON.id).addToRoom(roomtData).save(flush: true)
                it.price.each {
                    println it
                    def arrayFirst = it.'0'
                    def arraySecond = it.'1'
                    println arrayFirst
                    println arraySecond
                    def priceData = new Price("mounth": arrayFirst, "price" :arraySecond ? arraySecond : 100).save(flush: true);
                    def priceSaveData = priceData as JSON
                    def priceSaveDataJSON = JSON.parse(priceSaveData.toString())
                    println priceSaveData
                    println priceSaveDataJSON.id
                    Room.get(roomtSaveDataJSON.id).addToPrice(priceData).save(flush: true)
                }
            }
        }
    }
}
