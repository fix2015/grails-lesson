package zport.admin

import grails.transaction.Transactional

@Transactional
class FileService {

    def validationFile(f) {
        String origFileType = f.getContentType();
        def acceptContentType = [
                'image/gif',
                'image/jpeg',
                'image/png',
                'image/jpg',
                'text/plain'
        ]
        if (!acceptContentType.contains(origFileType)){
            return false
        }else{
            return true
        }
    }
}
