package action;

//import utils.DownloadObject;
import utils.GetS3Object;
import utils.RDSConnection;
import utils.TranscoderService;
import utils.VerifyEmail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.Statement;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;


//import dao.UserDao;


public class BaseAction extends ActionSupport implements SessionAware{
    /**
	 * 
	 */
	private Map<String, Object> session;
	private static final long serialVersionUID = 3383755865214105969L;
    private static final String SUCCESS = "success";
    private static final String INPUT = "input";
    private Connection con = null;
    
    protected String email;
    protected String password;
    
//    protected String reFirstName;
//    protected String reLastName;
    protected String reEmail;
    protected String rePassword;
//    protected String reCity;
//    protected String reCountry;
    protected String searchString;
//    List list;
    protected String fileName;
    protected float fileSize;
    
    protected String updatePassword;
    protected String updateFirstName;
    protected String updateLastName;
    protected String updateCity;
    protected String updateCountry;
    protected String path;
    protected String guestEmail;
    protected String from;
    protected String to;
    protected String flag;
    protected String newFileName;
    
    private void getCon(){
    	con = RDSConnection.CreateConnection();
    }

    public String login(){
    	String ret = SUCCESS;
//    	try{
//	    	UserDao userDao = new UserDao();
//	    	User userDB = userDao.queryUser(email, password);
//	    	if(userDB == null){
//	    		ret = INPUT;
//	    	}
//    	}
//    	catch(Exception e){
//    		addActionError(e.getMessage());
//    		ret = INPUT;
//    	}
    	getCon();
    	try{
    		int pw = password.hashCode();
        	String getUser = "SELECT * FROM User WHERE email = ? AND password = ?";
        	PreparedStatement ps = con.prepareStatement(getUser);
        	ps.setString(1, email);
        	ps.setInt(2, pw);
        	ResultSet rs = ps.executeQuery();
        	if(!rs.next()){
        		ret = INPUT;
        		addActionError("Email or password is incorrect!");
        	}
        	else{
        		session.put("email", email);
        	}
    	}
    	catch(Exception e){
    		ret = INPUT;
    	}
    	
    	return ret;
    }

    public String register(){
    	String ret = SUCCESS;
//    	try{
//	    	UserDao userDao = new UserDao();
//	    	userDao.saveUser(user);
//    	}
//    	catch(Exception e){
//    		addActionMessage(e.getMessage());
//    		ret = INPUT;
//    	}
    	try{
    		getCon();
        	String getUser = "SELECT * FROM User WHERE email = ?";
        	PreparedStatement ps = con.prepareStatement(getUser);
        	ps.setString(1, reEmail);
        	ResultSet rs = ps.executeQuery();
        	if(rs.next()){
        		ret = INPUT;
        		addActionError("Email is already Exist!");
        	}
        	else{
        		int pw = rePassword.hashCode();
        	    String insertRow = "INSERT INTO User (email, password, used_space) VALUES (?, ?, ?);";
        	    PreparedStatement ps1 = con.prepareStatement(insertRow);
        	    ps1.setString(1, reEmail);
        	    ps1.setInt(2, pw);
        	    ps1.setInt(3, 0);
        	    ps1.executeUpdate();
            	session.put("email", reEmail);
            	setEmail(reEmail);
            	VerifyEmail.verifyEmail(reEmail);
            	addActionMessage("Please go to your email and activate your account before your conversions!");
        	}
    	}
    	catch(Exception e){
    		ret = INPUT;
    	}
    	
    	return ret;
    }
    
    public String logout(){
    	String ret = SUCCESS;
    	session.remove("email");
    	return ret;
    }
    
//    public String search(){
//    	String ret = SUCCESS;
//    	getCon();
//    	try{
//	    	String getFile = "SELECT * FROM User_file WHERE file LIKE ?";
//	    	PreparedStatement ps = con.prepareStatement(getFile);
//	    	ps.setString(1, "%"+ searchString + "%");
//	    	ResultSet rs = ps.executeQuery();
//	    	while(rs.next()){
//	    		List sublist = new ArrayList();
//	    		sublist.add(rs.getString("file_name"));
//	    		sublist.add(rs.getString("file_size"));
//	    		list.add(sublist);
//	    	}
//    	}
//    	catch(Exception e){
//    		ret = INPUT;
//    	}
//    	return ret;
//    }
    
    public String updateDB(){
    	String ret = SUCCESS;
    	getCon();
    	try{
    		if(session.containsKey("email")){
        		String getUser = "SELECT used_space FROM User WHERE email = ?";
        		PreparedStatement ps = con.prepareStatement(getUser);
        		ps.setString(1,email);
        		ResultSet rs = ps.executeQuery();
        		float size = 0;
        		if(rs.next()){
        			size = rs.getFloat(1);
        		}
        		String updateUser = "UPDATE User SET used_space = ? WHERE email = ?";
    	    	ps = con.prepareStatement(updateUser);
    	    	ps.setFloat(1, fileSize + size);
    	    	ps.setString(2, email);
    	    	ps.executeUpdate();
    	    	String insertFile = "INSERT INTO File (file_name, type, size, email) VALUES (?, ?, ?, ?)";
    	    	ps = con.prepareStatement(insertFile);
    	    	ps.setString(1, fileName);
    	    	ps.setString(2, "input");
    	    	ps.setFloat(3, fileSize);
    	    	ps.setString(4, email);
    	    	ps.executeUpdate();
    		}
    	}
    	catch(Exception e){
    		ret = INPUT;
    		addActionError(e.getMessage());
    	}
    	return ret;
    }
    
    public String updateUser(){
    	String ret = SUCCESS;
    	getCon();
    	try{
    		String updateUser = "UPDATE User SET password = ? WHERE email = ?";
    		PreparedStatement ps = con.prepareStatement(updateUser);
			int pwd = updatePassword.hashCode();
			updateUser += "password = " + ps;
			ps.setInt(1, pwd);
			ps.setString(2, (String) session.get("email"));
//    		if(updateFirstName != null && !updateFirstName.trim().equals(""))
//    			updateUser += " first_name = " + updateFirstName + ",";
//    		if(updateLastName != null && !updateLastName.trim().equals(""))
//    			updateUser += "last_name = " + updateLastName + ",";
//    		if(updateCity != null && !updateCity.trim().equals(""))
//    			
//    			updateUser += "city = " + updateCity + ",";
//    		if(updateCountry != null && !updateCountry.trim().equals(""))
//    			updateUser += "country = " + updateCountry;
    		ps.executeUpdate();
    	}
    	catch(Exception e){
    		ret = INPUT;
    		addActionError(e.getMessage());
    	}
    	return ret;
    }
    
    public String startConvert(){
    	System.out.println("In Start Convert Action===============");
    	String ret = SUCCESS;
    	getCon();
    	try {
			int f = TranscoderService.transcodeProcess(email, fileName, newFileName);
//			DownloadObject.download(path, fileName, to);
    		setFlag(Long.toString(f));
    		if(f != 0 && session.containsKey("email")){
    			float newSize = GetS3Object.getObjectSize(newFileName);
    			
    			String getUser = "SELECT used_space FROM User WHERE email = ?";
        		PreparedStatement ps = con.prepareStatement(getUser);
        		ps.setString(1,email);
        		ResultSet rs = ps.executeQuery();
        		float size = 0;
        		if(rs.next()){
        			size = rs.getFloat(1);	
        		}
        		String updateUser = "UPDATE User SET used_space = ? WHERE email = ?";
    	    	ps = con.prepareStatement(updateUser);
    	    	ps.setFloat(1, fileSize + size + newSize);
    	    	ps.setString(2, email);
    	    	ps.executeUpdate();
    	    	
    	    	String insertFile = "INSERT INTO File (file_name, type, size, email) VALUES (?, ?, ?, ?)";
    	    	ps = con.prepareStatement(insertFile);
    	    	ps.setString(1, newFileName);
    	    	ps.setString(2, "output");
    	    	ps.setFloat(3, newSize);
    	    	ps.setString(4, email);
    	    	ps.executeUpdate();
    		}
//    		setFlag("1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return ret;
    }
    
    public String verifyEmail(){
    	String ret = SUCCESS;
    	VerifyEmail.verifyEmail(guestEmail);
    	return ret;
    }
    
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

//	public String getReFirstName() {
//		return reFirstName;
//	}
//
//	public void setReFirstName(String reFirstName) {
//		this.reFirstName = reFirstName;
//	}
//
//	public String getReLastName() {
//		return reLastName;
//	}
//
//	public void setReLastName(String reLastName) {
//		this.reLastName = reLastName;
//	}

	public String getReEmail() {
		return reEmail;
	}

	public void setReEmail(String reEmail) {
		this.reEmail = reEmail;
	}

	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

//	public String getReCity() {
//		return reCity;
//	}
//
//	public void setReCity(String reCity) {
//		this.reCity = reCity;
//	}
//
//	public String getReCountry() {
//		return reCountry;
//	}
//
//	public void setReCountry(String reCountry) {
//		this.reCountry = reCountry;
//	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public float getFileSize() {
		return fileSize;
	}

	public void setFileSize(float fileSize) {
		this.fileSize = fileSize;
	}

	public String getUpdatePassword() {
		return updatePassword;
	}

	public void setUpdatePassword(String updatePassword) {
		this.updatePassword = updatePassword;
	}

	public String getUpdateFirstName() {
		return updateFirstName;
	}

	public void setUpdateFirstName(String updateFirstName) {
		this.updateFirstName = updateFirstName;
	}

	public String getUpdateCity() {
		return updateCity;
	}

	public void setUpdateCity(String updateCity) {
		this.updateCity = updateCity;
	}

	public String getUpdateCountry() {
		return updateCountry;
	}

	public void setUpdateCountry(String updateCountry) {
		this.updateCountry = updateCountry;
	}

	public String getUpdateLastName() {
		return updateLastName;
	}

	public void setUpdateLastName(String updateLastName) {
		this.updateLastName = updateLastName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getGuestEmail() {
		return guestEmail;
	}

	public void setGuestEmail(String guestEmail) {
		this.guestEmail = guestEmail;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
	
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}   
	
	public String getNewFileName() {
		return newFileName;
	}

	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
	} 
}
