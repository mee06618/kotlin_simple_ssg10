class ArticleController {
    val repository = ArticleRepository
    fun add(){
        repository.addArticle()
    }
    fun wrtie(num:Int){
        if(num==0){
            println("로그인해주세요")
            return
        }
        repository.wrtieArtice(num)
    }
    fun list(rq: Rq){
        repository.filteredList(rq)
    }
    fun detail(rq: Rq){
        repository.detailArticle(rq)
    }
    fun modify(rq: Rq,num:Int){
        if(num==0){
            println("로그인해주세요")
            return
        }
        repository.modifyArticle(rq,num)
    }
    fun delete(rq: Rq,num: Int){
        if(num==0){
            println("로그인해주세요")
            return
        }
        repository.deleteArticle(rq)
    }

    fun save(){
        repository.saveArticle()
    }
    fun read(){
        repository.readArticle()
    }

}