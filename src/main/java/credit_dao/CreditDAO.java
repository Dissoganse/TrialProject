package credit_dao;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import java.lang.NumberFormatException;

import credit_dto.*;

import org.hibernate.service.spi.ServiceException;

public class CreditDAO {

	private EntityManagerFactory en_man_fac;
	private EntityManager en_man;
	
	public CreditDAO()
	{
		try {
		en_man_fac = Persistence.createEntityManagerFactory("BankApp");
		}
		catch(ServiceException e)
		{
			System.out.println(e.getCause().getMessage());
		}
		en_man = en_man_fac.createEntityManager();
	}
	
	public void add_credit(CreditDTO credit)
	{
		if(!check_data(credit)) return;
		
		en_man.getTransaction().begin();
		Query query = en_man.createNativeQuery("INSERT INTO CREDITS (" + 
				"loan_limit, interest_rate) " +
				"VALUES (" + "'" + credit.getLoanLimit().trim() + "', '" +
				credit.getInterestRate().trim() + "')"
				);
		query.executeUpdate();
		en_man.getTransaction().commit();
	}
	
	public void remove_credit(CreditDTO credit)
	{
		en_man.getTransaction().begin();
		en_man.remove(en_man.contains(credit)? credit : en_man.merge(credit));
		en_man.getTransaction().commit();
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<Integer, CreditDTO> search_credits(CreditDTO credit, boolean findById)
	{
		Query sql_query = en_man.createQuery(make_sql_query(credit, findById));
		List<Object[]> result_dtos = sql_query.getResultList();
		
		HashMap<Integer, CreditDTO> final_result = new HashMap<Integer, CreditDTO>();
		for(int i = 0; i < result_dtos.size();i++)
		{
			final_result.put(i, new CreditDTO());
			final_result.get(i).setCreditId((int)result_dtos.get(i)[0]);
			final_result.get(i).setLoanLimit((String)result_dtos.get(i)[1]);
			final_result.get(i).setInterestRate((String)result_dtos.get(i)[2]);
		}
		
		return final_result;
	}
	
	public void save_changes(HashMap<Integer, CreditDTO> credit, ArrayList<Integer> decode)
	{
		for(int i = 0; i < credit.size(); i++)
		{	
			if(!check_data(credit.get(decode.get(i)))) continue;
			
			
			en_man.getTransaction().begin();
			Query query = en_man.createNativeQuery("UPDATE CREDITS SET " + 
					"loan_limit =" + "'" + credit.get(decode.get(i)).getLoanLimit().trim() + "'" + ", " +
					"interest_rate = " + "'" + credit.get(decode.get(i)).getInterestRate().trim() + "'" +
					"WHERE credit_id = " + credit.get(decode.get(i)).getCreditId()
					);
			query.executeUpdate();
			en_man.getTransaction().commit();
		}
	}
	
	private boolean check_data(CreditDTO credit)
	{
		if(credit.getLoanLimit().trim().length() > 15) return false;
		if(credit.getInterestRate().trim().length() > 30) return false;
		
		try {
			int limitok = Integer.parseInt(credit.getLoanLimit().trim());
			int interestik = Integer.parseInt(credit.getInterestRate().trim());
		}
		catch(NumberFormatException e)
		{
			return false;
		}
		
		return true;
	}
	
	private String make_sql_query(CreditDTO credit, boolean findById)
	{
		String sql_query = new String();
		sql_query += "SELECT credit_id, loan_limit, interest_rate FROM CREDITS";
		boolean was_string = false;
		
		if(!findById) {
			if((credit.getLoanLimit().trim().length() != 0))
			{
				sql_query += !was_string? " WHERE loan_limit = " : " AND loan_limit = ";
				sql_query += "'" + credit.getLoanLimit().trim() + "'";
				was_string = true;
			}
			if((credit.getInterestRate().trim().length() != 0))
			{
				sql_query += !was_string? " WHERE interest_rate = " : 
					" AND interest_rate = ";
				sql_query += "'" + credit.getInterestRate().trim() + "'";
				was_string = true;
			}		
		}
		else
			sql_query += " WHERE credit_id = " + Integer.toString(credit.getCreditId());
		
		 return sql_query;
	}
	
}

