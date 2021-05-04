import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

object BoardRepository {
    var lastid=0
    val boards = mutableListOf<Board>()
    fun writeBoard() {
        print("이름 : ")
        val name = readLineTrim()
        print("코드 : ")
        val code=readLineTrim().toInt()
        for(i in boards){
            if(i.name==name || i.code==code){
                println("중복입니다")
                return
            }

        }
        val temp = Board(++lastid,name,code)
        boards.add(temp)
        println("등록됨")
    }

    fun listBoard() {
        for(i in boards){
            println("${i.id}  / ${i.name}  / ${i.code}")
        }
    }

    fun modifyBoard(id: Int) {
        for(i in boards){
           if(i.id==id) {
               print("이름 : ")
               i.name = readLineTrim()
               print("코드 : ")
               i.code = readLineTrim().toInt()
           }
            else{
                println("없는 게시물입니다")
           }
        }
    }

    fun deleteBoard(id: Int) {
        for(i in boards){
            if(i.id==id)
                boards.remove(i)
            else{
                println("없는 게시물입니다")
            }
        }
    }

    fun getBoard(boardId: Int): String {
        for(i in boards){
            if(i.code==boardId)
                return i.name
        }
        return "오류"
    }

    fun saveBoard() {
        val mapper = jacksonObjectMapper()


        for(i in boards){
            var file = File("src/main/json/board/${i.id}.json")
            if(!file.exists()){
                file.createNewFile()
            }
            mapper .writerWithDefaultPrettyPrinter() .writeValue( file,i)
        }


    }

    fun readBoard() {

        val mapper= jacksonObjectMapper()
        var file = File("src/main/json/board")
        for(i in 1..file.list().size){
            boards.add(mapper.readValue<Board>(File("src/main/json/board/${i}.json")))
        }


    }
}