package Dao;

public abstract class Dao {

	protected  int lastId;

	Dao() {
		lastId = 0;
	}

	public int getNewId() {
		return lastId + 1;

	}

	public int getLastId() {
		return lastId;
	}
}
