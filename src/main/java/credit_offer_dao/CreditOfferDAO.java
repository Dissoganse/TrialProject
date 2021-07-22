package credit_offer_dao;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.Transactional;

import java.util.Date;
import java.lang.NumberFormatException;

import org.hibernate.service.spi.ServiceException;

import credit_offer_dto.*;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CreditOfferDAO {

	private EntityManagerFactory en_man_fac;
	private EntityManager en_man;
	
	public CreditOfferDAO()
	{
		try {
		en_man_fac = Persistence.createEntityManagerFactory("BankApp");}
		catch(ServiceException e)
		{
			System.out.println(e.getCause().getMessage());
		}
		en_man = en_man_fac.createEntityManager();
	}
	
	@Transactional
	public void add_offer(CreditOfferDTO offer)
	{	 
		if(!check_data(offer)) return;
		
		en_man.getTransaction().begin();
		Query query = en_man.createNativeQuery("INSERT INTO CROFFERS (" + 
				"client_id, credit_id, credit_sum, payment_date, payment_sum, loan_body, loan_interest) " +
				"VALUES (" + Integer.toString(offer.getClientId()) + ", " +
				Integer.toString(offer.getCreditId()) + ", " +
				Integer.toString(offer.getCreditSum()) +
				", '" + offer.getPaymentDate().trim() + "', " + 
				Integer.toString(offer.getPaymentSum()) + ", " +
				Integer.toString(offer.getLoanBody()) + ", " +
				Integer.toString(offer.getLoanInterest()) + ")"
				);
		query.executeUpdate();
		en_man.getTransaction().commit();
	}
	
	public void remove_offer(CreditOfferDTO offer)
	{	
		en_man.getTransaction().begin();
		en_man.remove(en_man.contains(offer)? offer : en_man.merge(offer));
		en_man.getTransaction().commit();
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<Integer, CreditOfferDTO> search_offers(CreditOfferDTO offer)
	{
		Query sql_query = en_man.createQuery(make_sql_query(offer));
		List<Object[]> result_dtos =  sql_query.getResultList();
		
		HashMap<Integer, CreditOfferDTO> aim_array = new HashMap<Integer, CreditOfferDTO>(); 
		for(int i = 0; i < result_dtos.size(); i++)
		{
			aim_array.put(i, new CreditOfferDTO());
			aim_array.get(i).setOfferId((int)result_dtos.get(i)[0]);
			aim_array.get(i).setClientId((int) result_dtos.get(i)[1]);
			aim_array.get(i).setCreditId((int) result_dtos.get(i)[2]);
			aim_array.get(i).setCreditSum((int) result_dtos.get(i)[3]);
			aim_array.get(i).setPaymentDate((String) result_dtos.get(i)[4]);
			aim_array.get(i).setPaymentSum((int) result_dtos.get(i)[5]);
			aim_array.get(i).setLoanBody((int) result_dtos.get(i)[6]);
			aim_array.get(i).setLoanInterest((int) result_dtos.get(i)[7]);
		}
		
		return aim_array;
	}
	
	public void save_changes(HashMap<Integer, CreditOfferDTO> offer, ArrayList<Integer> decode)
	{
		for(int i = 0; i < offer.size(); i++)
		{	
			if(!check_data(offer.get(decode.get(i)))) continue;
			
			
			en_man.getTransaction().begin();
			Query query = en_man.createNativeQuery("UPDATE CROFFERS SET " + 
					"client_id =" + Integer.toString(offer.get(decode.get(i)).getClientId()) + ", " +
					"credit_id = " + Integer.toString(offer.get(decode.get(i)).getCreditId()) + ", " +
					"credit_sum = " + Integer.toString(offer.get(decode.get(i)).getCreditSum()) + ", " +
					"payment_date = " + "'" + offer.get(decode.get(i)).getPaymentDate().trim() + "'" + ", " +
					"payment_sum = "+ Integer.toString(offer.get(decode.get(i)).getPaymentSum()) + ", " +
					"loan_body = " + Integer.toString(offer.get(decode.get(i)).getLoanBody()) + ", " +
					"loan_interest = " + Integer.toString(offer.get(decode.get(i)).getLoanInterest()) + " " +
					"WHERE offer_id = " + Integer.toString(offer.get(decode.get(i)).getOfferId())
					);
			query.executeUpdate();
			en_man.getTransaction().commit();
		}
	}
	
	private boolean check_data(CreditOfferDTO offer)
	{
		
		String[] test_strings = offer.getPaymentDate().trim().split("\\.");
		int day;
		int month;
		int year;
		Date current_date = new Date();
		Date harvested_date = new Date();
		
		
		if(offer.getPaymentDate().trim().length() > 15) return false;
		if(test_strings.length != 3) return false;
		
		
		try {
			day = Integer.parseInt(test_strings[0]);
			month = Integer.parseInt(test_strings[1]);
			year = Integer.parseInt(test_strings[2]);
		}
		catch(NumberFormatException e)
		{
			return false;
		}
		
		
		if((month > 12) || (month <= 0)) return false;
		if(day <= 0) return false;
		if(((month % 2) == 1) && (day > 31)) return false;
		if(((month % 2) == 0) && (day > 30)) return false;
		
		
		harvested_date.setDate(day);
		harvested_date.setMonth(month - 1);
		harvested_date.setYear(year - 1900);
		if(harvested_date.compareTo(current_date) < 0) return false;
		System.out.println(current_date.toString());
		System.out.println(harvested_date.toString());
		
		
		return true;
	}
	
	private String make_sql_query(CreditOfferDTO offer)
	{
		String sql_query = new String();
		sql_query += "SELECT offer_id, client_id, credit_id, credit_sum, payment_date, payment_sum, loan_body, loan_interest"
				+ " FROM CROFFERS";
		boolean was_string = false;
		
		if((offer.getClientId() != 0))
		{
			sql_query += !was_string? " WHERE client_id = " : " AND client_id = ";
			sql_query += Integer.toString(offer.getClientId());
			was_string = true;
		}
		if((offer.getCreditId() != 0))
		{
			sql_query += !was_string? " WHERE credit_id = " : " AND credit_id = ";
			sql_query += Integer.toString(offer.getCreditId());
			was_string = true;
		}
		if((offer.getCreditSum() != 0))
		{
			sql_query += !was_string? " WHERE credit_sum = " : " AND credit_sum = ";
			sql_query += Integer.toString(offer.getCreditSum());
			was_string = true;
		}
		if((offer.getPaymentDate().trim().length() != 0))
		{
			sql_query += !was_string? " WHERE payment_date = " : " AND payment_date = ";
			sql_query += "'" + offer.getPaymentDate() + "'";
			was_string = true;
		}
		if((offer.getPaymentSum() != 0))
		{
			sql_query += !was_string? " WHERE payment_sum = " : " AND payment_sum = ";
			sql_query += Integer.toString(offer.getPaymentSum());
			was_string = true;
		}
		if((offer.getLoanBody() != 0))
		{
			sql_query += !was_string? " WHERE loan_body = " :
				" AND loan_body = ";
			sql_query += Integer.toString(offer.getLoanBody());
			was_string = true;
		}
		if((offer.getLoanInterest() != 0))
		{
			sql_query += !was_string? " WHERE loan_interest = " :
				" AND loan_interest = ";
			sql_query += Integer.toString(offer.getLoanInterest());
			was_string = true;
		}
		
		 return sql_query;
	}
	
}
