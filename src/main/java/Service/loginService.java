package Service;

import Dao.loginDAO;

public class loginService {
	private loginDAO loginDAO=new loginDAO();
	
	public int foundUser(String user) {
		return loginDAO.foundUser(user);
	}
	
	public int foundMdp(String mdp) {
		return loginDAO.foundMdp(mdp);
	}
	
	public int found2(String user,String mdp) {
		return loginDAO.found2(user, mdp);
	}

}
