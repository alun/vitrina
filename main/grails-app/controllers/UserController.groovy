import com.katlex.vitrina.domain.User
import com.katlex.vitrina.domain.Role
public class UserController {


    def moderateUsers = {
		redirect(action:list)
	}
	   
	def list = {
		def userList = User.list() 
		log.debug "${userList}"
		[users: userList]
	}
	
}
