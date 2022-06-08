package Dao; // 창고관리자

import java.util.ArrayList;
import java.util.List;

import Article.Member;

public class MemberDao {

	public List<Member> members;

	public MemberDao() {
		members = new ArrayList<>();
	}
}
//Dao 창고관리자가 가지고 있는 members 창고 클래스를 만들고 코드 보완
//memberController 에서 사용했던 new ArrayList 를 여기에서 사용하도록 보완