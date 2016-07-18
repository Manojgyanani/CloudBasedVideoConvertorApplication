package dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class User {
	

   private long id;
   private String lastName;
   private String firstName;
   private String city;
   private String country;
   private String email;
   private String password;
   private long usedSpace;
   
   @Id
   @GeneratedValue
   public long getId() {
    return id;
   }
   public void setId(long id) {
    this.id = id;
   }
   @Column(name="last_name")
   public String getLastName() {
      return lastName;
   }
   public void setLastName(String lastName) {
      this.lastName = lastName;
   }
   @Column(name="first_name")
   public String getFirstName() {
      return firstName;
   }
   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }
   @Column(name="city")
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	@Column(name="country")
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	@Column(name="email")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name="password")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Column(name="used_space")
	public long getUsedSpace() {
		return usedSpace;
	}
	public void setUsedSpace(long usedSpace) {
		this.usedSpace = usedSpace;
	}
	
}
