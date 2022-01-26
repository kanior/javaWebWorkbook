package spms.controls;

import java.util.Map;

import spms.dao.MySqlMemberDao;
import spms.vo.Member;

public class MemberUpdateController implements Controller {
	MySqlMemberDao memberDao;
	
	public MemberUpdateController setMemberDao(MySqlMemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		if (model.get("member") == null) {
			model.put("member", memberDao.selectOne((Integer)model.get("no")));
			
			return "/member/MemberUpdateForm.jsp";
		}
		else {
			memberDao.update((Member)model.get("member"));
			
			return "redirect:list.do";
		}
	}

}
