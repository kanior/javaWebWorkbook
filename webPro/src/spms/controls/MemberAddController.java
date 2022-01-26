package spms.controls;

import java.util.Map;

import spms.dao.MySqlMemberDao;
import spms.vo.Member;

public class MemberAddController implements Controller {
	MySqlMemberDao memberDao;
	
	public MemberAddController setMemberDao(MySqlMemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		if (model.get("member") == null) {
			return "/member/MemberAddForm.jsp";
		}
		else {
			memberDao.insert((Member)model.get("member"));
			
			return "redirect:list.do";
		}
	}

}
