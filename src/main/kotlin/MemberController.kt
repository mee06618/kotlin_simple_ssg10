class MemberController {
    val repository = MemberRepository
    fun join(){
        repository.joinMember()
    }
    fun login(rq: Rq): Int {
        return repository.loginMember()
    }
    fun logout(): Int {
        return repository.logoutMember()
    }
    fun getNick(num:Int):String{
        return repository.getNick(num)
    }

    fun save(){
        repository.saveMember()
    }
    fun read(){
        repository.readMember()
    }
}