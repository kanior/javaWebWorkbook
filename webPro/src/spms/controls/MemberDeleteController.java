package spms.controls;

import java.util.Map;

import spms.bind.DataBinding;
import spms.dao.MySqlMemberDao;

public class MemberDeleteController implements Controller, DataBinding {
	MySqlMemberDao memberDao;
	
	public MemberDeleteController setMemberDao(MySqlMemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	@Override
	public Object[] getDataBinders() {
		return new Object[] {
				"no", Integer.class
		};
	}

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		memberDao.delete((Integer)model.get("no"));
		
		return "redirect:list.do";
	}

}
