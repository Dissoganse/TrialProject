package credit_dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "CREDITS")
@Table(name = "CREDITS")
public class CreditDTO {

	@Id
	@Column(name = "credit_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int credit_id;
	
	@Column(name = "loan_limit")
	private String loan_limit = new String();
	
	@Column(name = "interest_rate")
	private String interest_rate = new String();
	
	public int getCreditId ()
	{
		return credit_id;
	}
	
	public String getLoanLimit()
	{
		return loan_limit;
	}

	public String getInterestRate()
	{
		return interest_rate;
	}
	
	public void setCreditId (int credit_id)
	{
		this.credit_id = credit_id;
	}
	
	public void setLoanLimit(String loan_limit)
	{
		this.loan_limit = loan_limit.trim();
	}

	public void setInterestRate(String interest_rate)
	{
		this.interest_rate = interest_rate.trim();
	}
	
}
