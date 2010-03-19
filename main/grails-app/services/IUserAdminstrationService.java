import java.util.List;


public interface IUserAdminstrationService {
	  void allUsers(); 

	  void blockedUsers();

	  Boolean listNameExists(String listName); 

	  void saveUsersList(String listName, Boolean force);

	
	  
	  

}
