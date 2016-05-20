package zport.admin

import grails.transaction.Transactional

@Transactional
class RoomService {

    def appendToImage() {
        Room.get(params.id).addToImage(image).save(flush: true)
    }
}
