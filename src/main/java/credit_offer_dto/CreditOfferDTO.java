package credit_offer_dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "CROFFERS")
@Table(name = "CROFFERS")
public class CreditOfferDTO {

	@Id
	@Column(name = "offer_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int offer_id;
	
	
	@Column(name = "client_id")
	private int client_id;
	
	
	@Column(name = "credit_id")
	private int credit_id;
	
	
	@Column(name = "credit_sum")
	private int credit_sum;
	
	
	@Column(name = "payment_date")
	private String payment_date;
	
	
	@Column(name = "payment_sum")
	private int payment_sum;
	
	
	@Column(name = "loan_body")
	private int loan_body;
	
	
	@Column(name = "loan_interest")
	private int loan_interest;
	
	public int getOfferId()
	{
		return this.offer_id;
	}
	
	
	public int getClientId()
	{
		return this.client_id;
	}
	
	
	public int getCreditId()
	{
		return this.credit_id;
	}
	
	
	public int getCreditSum()
	{
		return this.credit_sum;
	}
	
	
	public String getPaymentDate()
	{
		return this.payment_date;
	}
	
	
	public int getPaymentSum()
	{
		return this.payment_sum;
	}
	
	
	public int getLoanBody()
	{
		return this.loan_body;
	}
	
	
	public int getLoanInterest()
	{
		return this.loan_interest;
	}
	
	
	public void setOfferId(int offer_id)
	{
		this.offer_id = offer_id;
	}
	
	
	public void setClientId(int client_id)
	{
		this.client_id = client_id;
	}
	
	
	public void setCreditId(int credit_id)
	{
		this.credit_id = credit_id;
	}
	
	
	public void setCreditSum(int credit_sum)
	{
		this.credit_sum = credit_sum;
	}
	
	
	public void setPaymentDate(String payment_date)
	{
		this.payment_date = payment_date;
	}
	
	
	public void setPaymentSum(int payment_sum)
	{
		this.payment_sum = payment_sum;
	}
	
	
	public void setLoanBody(int loan_body)
	{
		this.loan_body = loan_body;
	}
	
	
	public void setLoanInterest(int loan_interest)
	{
		this.loan_interest = loan_interest;
	}
}
