package cn.com.dingduoduo.utils.common.okhttputil.test;

public class User {

	public String username ; 
	public String password  ;

	public User() {

	}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	public String toString()
	{
		return "User{" +
				"username='" + username + '\'' +
				", password='" + password + '\'' +
				'}';
	}
}
