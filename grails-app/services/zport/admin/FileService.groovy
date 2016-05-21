package zport.admin

import grails.transaction.Transactional
import grails.util.Holders

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
    def createFolderForFile(params) {
        new File(Holders.config.filesDir,"images/${params.folder}/${params.fotoFolderId}").mkdirs()
        return true
    }
    def getDestination(params) {
        return new File(Holders.config.filesDir,"images/${params.folder}/${params.fotoFolderId}/${params.name}")
    }
}
