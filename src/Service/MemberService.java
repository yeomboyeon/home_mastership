package Service;

import Article.Member;
import Container.Container;
import Dao.MemberDao;

public class MemberService {
	private MemberDao memberDao;

	public MemberService() {
		this.memberDao = Container.memberDao;
	}

	public void add(Member member) {
		memberDao.add(member);
	}

	public boolean isJoinableLoginId(String loginId) {
		return memberDao.isJoinableLoginId(loginId);
	}

	public Member getMemberByLoginId(String loginId) {
		return memberDao.getMemberByLoginId(loginId);
	}

}