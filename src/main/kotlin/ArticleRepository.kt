import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

object ArticleRepository {
    val articles = mutableListOf<Article>()
    var lastid=0

    fun getArticleById(id:Int): Article? {
        for (i in articles) {
            if (i.id == id)
                return i
        }
        return null
        }
        fun addArticle() {
            val regdate = Util.getNowDateStr()
            val update = Util.getNowDateStr()
            for (i in 1..100) {
                lastid++
                val temp = Article(lastid, "제목${i}", "내용${i}", regdate, update,1,0)
                articles.add(temp)
            }
        }

        fun wrtieArtice(num: Int) {
            val id = ++lastid
            print("제목 : ")
            val title = readLineTrim()
            print("내용 : ")
            val body = readLineTrim()
            print("게시판 : ")
            val board = readLineTrim().toInt()
            val regdate = Util.getNowDateStr()
            val update = Util.getNowDateStr()


            val temp = Article(id, title, body, regdate, update,num,board)
            articles.add(temp)
            println("${id}번째 게시물이 작성되었습니다")
        }

        fun filteredList(rq: Rq) {
            val id = rq.getIntParam("id",0)
            val page = rq.getIntParam("page",1)
            val boardId = rq.getIntParam("boardId",0)
            val keyword=rq.getStringParam("keyword","")
            if(id!=0){
                for(i in articles){
                    if(i.id==id) {
                        val boardId = BoardRepository.getBoard(i.boardId)
                        val member = MemberRepository.getNick(i.memberId)
                        println("${i.id} / ${i.title} / ${i.body} / ${i.regdate} / ${i.update} / $member / $boardId")
                    }
                    else{
                        println("없음")
                        return
                    }
                }
                return
            }
            val filteredArticle = filteredPage(page)
            val filteredArticle2=filteredBoardId(boardId,filteredArticle)
            val filteredArticle3 = filteredKeyWord(keyword,filteredArticle2)







            for (i in filteredArticle3) {
                if (i != null) {
                    val member = MemberRepository.getNick(i.memberId)

                    println("${i.id} / ${i.title} / ${i.body} / ${i.regdate} / ${i.update} / $member / ${i.boardId}")
                }
                else{
                    println("잘못된 법위입니다")
                    return
                }
            }
        }

    private fun filteredKeyWord(keyword: String, filteredArticle2:MutableList<Article>): MutableList<Article> {
        val arr = mutableListOf<Article>()
        for(i in filteredArticle2){
            if(i.title.contains(keyword) || i.body.contains(keyword))
                arr.add(i)

        }

        return arr
    }

    private fun filteredBoardId(boardId: Int, filteredArticle: MutableList<Article?>):  MutableList<Article> {
        val arr= mutableListOf<Article>()
            for(i in filteredArticle){
                if (i != null) {
                    if(i.boardId == boardId)
                        arr.add(i)
                }
            }
        return arr
    }

    fun filteredId(id:Int): Article? {
            val temp = getArticleById(id)
            return temp
        }
        fun filteredPage(page:Int): MutableList<Article?> {
            val offset = (page-1)*5
            val startIndex= articles.lastIndex-offset+1
            var lastIndex=startIndex-4
            if(lastIndex<0)
                lastIndex=0
            val arr = mutableListOf<Article?>()
            for(i in startIndex downTo lastIndex){
                arr.add(getArticleById(i))
            }
            return arr
        }

        fun detailArticle(rq: Rq) {
            val id = rq.getIntParam("id",0)
            val temp = getArticleById(id)
            if(temp==null){
                println("없는 게시물입니다")
                return
            }
            println("번호 : ${temp.id}")
            println("제목 : ${temp.title}")
            println("내용 : ${temp.body}")
            println("게시날짜 : ${temp.regdate}")
            println("갱신날짜 : ${temp.update}")
            println("작성자 : ${temp.memberId}")

        }

    fun modifyArticle(rq: Rq, num: Int) {
        val id = rq.getIntParam("id",0)
        var temp = getArticleById(id)
        if(temp==null){
            println("없는 게시물입니다")
            return
        }
        temp.id = temp.id
        print("제목 : ")
        temp.title=readLineTrim()
        print("내용 : ")
        temp.body =readLineTrim()
        temp.update = Util.getNowDateStr()
        temp.memberId=num

    }

    fun deleteArticle(rq: Rq) {
        val id = rq.getIntParam("id",0)
        val temp = getArticleById(id)
        if(temp==null){
            println("없는 게시물입니다")
            return
        }
        articles.remove(temp)
    }

    fun saveArticle() {
        val mapper = jacksonObjectMapper()


        for(i in articles){
            var file =File("src/main/json/article/${i}.json")
            if(!file.exists()){
                file.createNewFile()
            }
            mapper .writerWithDefaultPrettyPrinter() .writeValue( file,i)
        }


    }

    fun readArticle() {

        val mapper= jacksonObjectMapper()
        var file =File("src/main/json/article")

       for(i in 1..file.list().size){
            articles.add(mapper.readValue<Article>(File("src/main/json/article/${i}.json")))
        }


    }
}


