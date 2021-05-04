import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

object MemberRepository {
    var lastid=0
    val members = mutableListOf<Member>()
    fun joinMember() {
        val num = ++lastid
        print("이름 : ")
        val name = readLineTrim()
        print("비번 : ")
        val pass =readLineTrim()
        print("별명 : ")
        val nick =readLineTrim()
        members.add(Member(num,name,pass,nick))
    }

    fun loginMember(): Int {
        print("이름 : ")
        val id = readLineTrim()


        print("비번 : ")
        val pass =readLineTrim()
        for(i in members){
            if(i.id==id){
                if(i.pass==pass){
                    return i.num
                }else{
                    println("비번이 틀립니다")
                    return 0
                }
            }else{
                println("없는 아이디 입니다")
                return 0
            }
        }
        return 0
    }

    fun logoutMember(): Int {
        println("로그아웃합니다")
        return 0
    }

    fun getNick(num: Int): String {
        for(i in members){
            if(i.num==num)
                return i.nick
        }
        return ""
    }

    fun saveMember() {
        val mapper = jacksonObjectMapper()


        for(i in members){
            var file = File("src/main/json/member/${i}.json")
            if(!file.exists()){
                file.createNewFile()
            }
            mapper .writerWithDefaultPrettyPrinter() .writeValue( file,i)
        }


    }

    fun readMember() {

        val mapper= jacksonObjectMapper()
        var file = File("src/main/json/member")
        for(i in 1..file.list().size){
            members.add(mapper.readValue<Member>(File("src/main/json/member/${i}.json")))
        }


    }
}