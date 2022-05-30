package Controller;
// 리모컨 버튼만 만들어논 추상클래스로 작성
public abstract class Controller {

	public abstract void doAction(String command, String actionMethodName);

}
