package client_dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "CLIENTS")
@Table(name = "CLIENTS")
public class ClientDTO {

	@Id
	@Column(name = "client_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int client_id;
	
	@Column(name = "last_name")
	private String last_name = new String();
	
	@Column(name = "first_name")
	private String first_name = new String();
	
	@Column(name = "middle_name")
	private String middle_name = new String();
	
	@Column(name = "phone_number")
	private String phone_number = new String();
	
	@Column(name = "email")
	private String email = new String();
	
	@Column(name = "passport_number")
	private String passport_number = new String();
	
	public int getClientId ()
	{
		return client_id;
	}
	
	public String getLastName()
	{
		return last_name;
	}

	public String getFirstName()
	{
		return first_name;
	}
	
	public String getMiddleName()
	{
		return middle_name;
	}
	
	public String getPhoneNumber()
	{
		return phone_number;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public String getPassportNumber()
	{
		return passport_number;
	}
	
	public void setClientId (int client_id)
	{
		this.client_id = client_id;
	}
	
	public void setLastName(String last_name)
	{
		this.last_name = last_name.trim();
	}

	public void setFirstName(String first_name)
	{
		this.first_name = first_name.trim();
	}
	
	public void setMiddleName(String middle_name)
	{
		this.middle_name = middle_name.trim();
	}
	
	public void setPhoneNumber(String phone_number)
	{
		this.phone_number = phone_number.trim();
	}
	
	public void setEmail(String email)
	{
		this.email = email.trim();
	}
	
	public void setPassportNumber(String passport_number)
	{
		this.passport_number = passport_number.trim();
	}
	
}
