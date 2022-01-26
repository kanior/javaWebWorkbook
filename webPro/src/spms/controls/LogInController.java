package spms.controls;

import java.util.Map;

import javax.servlet.http.HttpSession;

import spms.dao.MySqlMemberDao;
import spms.vo.Member;

public class LogInController implements Controller{
	MySqlMemberDao memberDao;
	
	public LogInController setMemberDao(MySqlMemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		if (model.get("email") == null) {
			return "/auth/LogInForm.jsp";
		}
		else {
			Member member = memberDao.exist((String)model.get("email"), (String)model.get("password"));
			
			if (member != null) {
				HttpSession session = (HttpSession)model.get("session");
				session.setAttribute("member", member);
				
				return "redirect:../member/list.do";
			}
			else {
				return "/auth/LogInFail.jsp";
			}
		}
	}

}
