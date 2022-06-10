package Dao;

import java.util.ArrayList;
import java.util.List;

import Article.Member;
import Container.Container;
import YeomIT.Util;

public class MemberDao extends Dao {

	public List<Member> members;

	public MemberDao() {
		members = new ArrayList<>();
	}

	public void add(Member member) {
		members.add(member);
		lastId++;
	}

	public Member getMemberByLoginId(String loginId) {
		int index = getMemberIndexByLoginId(loginId);

		if (index == -1) {
			return null;
		}

		return members.get(index);
	}

	public boolean isJoinableLoginId(String loginId) {
		int index = getMemberIndexByLoginId(loginId);

		if (index == -1) {
			return true;
		}

		return false;
	}

	public int getMemberIndexByLoginId(String loginId) {
		int i = 0;
		for (Member member : members) {
			if (member.loginId.equals(loginId)) {
				return i;
			}
			i++;
		}

		return -1;
	}

}