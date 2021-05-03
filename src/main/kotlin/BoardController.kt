class BoardController {
    val repository = BoardRepository
    fun write(){
        repository.writeBoard()
    }
    fun list(){
        repository.listBoard()
    }
    fun modify(rq: Rq){
        val id = rq.getIntParam("id",1)
        repository.modifyBoard(id)
    }
    fun delete(rq: Rq){
        val id=rq.getIntParam("id",0)
        repository.deleteBoard(id)
    }

    fun save(){
        repository.saveBoard()
    }
    fun read(){
        repository.readBoard()
    }
}