package clients_dao;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.Transactional;
import client_dto.*;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import org.hibernate.service.spi.ServiceException;

public class ClientsDAO {

	private EntityManagerFactory en_man_fac;
	private EntityManager en_man;
	
	public ClientsDAO()
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
	public void add_client(ClientDTO client)
	{
		if(!check_data(client)) return;
		
		
		en_man.getTransaction().begin();
		Query query = en_man.createNativeQuery("INSERT INTO CLIENTS (" + 
				"last_name, first_name, middle_name, phone_number, email, passport_number) " +
				"VALUES (" + "'" + client.getLastName().trim() + "', " +
				"'" + client.getFirstName().trim() + "', " + "'" + client.getMiddleName().trim() +
				"', '" + client.getPhoneNumber().trim() + "', '" + client.getEmail().trim() + "', '" +
				client.getPassportNumber().trim() + "')"
				);
		query.executeUpdate();
		en_man.getTransaction().commit();
	}
	
	public void remove_client(ClientDTO client)
	{	
		en_man.getTransaction().begin();
		en_man.remove(en_man.contains(client)? client : en_man.merge(client));
		en_man.getTransaction().commit();
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<Integer, ClientDTO> search_clients(ClientDTO client, boolean findById)
	{
		Query sql_query = en_man.createQuery(make_sql_query(client, findById));
		List<Object[]> result_dtos =  sql_query.getResultList();
		
		HashMap<Integer, ClientDTO> aim_array = new HashMap<Integer, ClientDTO>(); 
		for(int i = 0; i < result_dtos.size(); i++)
		{
			aim_array.put(i, new ClientDTO());
			aim_array.get(i).setClientId((int)result_dtos.get(i)[0]);
			aim_array.get(i).setLastName((String) result_dtos.get(i)[1]);
			aim_array.get(i).setFirstName((String) result_dtos.get(i)[2]);
			aim_array.get(i).setMiddleName((String) result_dtos.get(i)[3]);
			aim_array.get(i).setPhoneNumber((String) result_dtos.get(i)[4]);
			aim_array.get(i).setEmail((String) result_dtos.get(i)[5]);
			aim_array.get(i).setPassportNumber((String) result_dtos.get(i)[6]);
		}
		
		return aim_array;
	}
	
	public void save_changes(HashMap<Integer, ClientDTO> client, ArrayList<Integer> decode)
	{
		for(int i = 0; i < client.size(); i++)
		{	
			if(!check_data(client.get(decode.get(i)))) continue;
			
			
			en_man.getTransaction().begin();
			Query query = en_man.createNativeQuery("UPDATE CLIENTS SET " + 
					"last_name =" + "'" + client.get(decode.get(i)).getLastName().trim() + "'" + ", " +
					"first_name = " + "'" + client.get(decode.get(i)).getFirstName().trim() + "'" + ", " +
					"middle_name = " + "'" + client.get(decode.get(i)).getMiddleName().trim() + "'" + ", " +
					"phone_number = " + "'" + client.get(decode.get(i)).getPhoneNumber().trim() + "'" + ", " +
					"email = "+ "'" + client.get(decode.get(i)).getEmail() + "'" + ", " +
					"passport_number = " + "'" + client.get(decode.get(i)).getPassportNumber().trim() + "'" + " " +
					"WHERE client_id = " + client.get(decode.get(i)).getClientId()
					);
			query.executeUpdate();
			en_man.getTransaction().commit();
		}
	}
	
	
	private boolean check_data(ClientDTO client)
	{
		String test_string = new String();
		String[] email = null;
		String[] email_domain = null;
		
		
		if(client.getLastName().trim().length() == 0)
			return false;
		if(client.getFirstName().trim().length() == 0)
			return false;
		if(client.getMiddleName().trim().length() == 0)
			return false;
		if(client.getPhoneNumber().trim().length() == 0)
			return false;
		
		
		test_string = client.getPhoneNumber().trim();
		for(int i = 0; i < test_string.length(); i++)
		{
			if(i == 0)
			{
				if((test_string.charAt(i) != '+') && ((test_string.charAt(i) < '0') || 
						(test_string.charAt(i) > '9')))
					return false;
			}
			else 
			{
				if((test_string.charAt(i) < '0') || (test_string.charAt(i) > '9'))
					return false;
			}
		}
		if((test_string.length() > 15) || (test_string.length() < 6))
			return false;

		
		test_string = client.getEmail().trim();
		if(test_string.length() != 0) {
			if(test_string.length() > 50)
				return false;
			email = test_string.split("@");
			if(email.length != 2)
				return false;
			email_domain = email[1].trim().split("\\.");
			if(email_domain.length != 2)
				return false;
			if((email_domain[1].compareTo("com") != 0) &&
					(email_domain[1].compareTo("ua") != 0) &&
					(email_domain[1].compareTo("ru") != 0) &&
					(email_domain[1].compareTo("org") != 0))
				return false;
		}
		
		
		test_string = client.getPassportNumber();
		for(int i = 0; i < test_string.length(); i++)
		{
				if((test_string.charAt(i) < '0') || (test_string.charAt(i) > '9'))
					return false;
		}
		if((test_string.length() > 12) || (test_string.length() < 4))
			return false;

		
		return true;
	}
	
	
	private String make_sql_query(ClientDTO client, boolean findById)
	{
		String sql_query = new String();
		sql_query += "SELECT client_id, last_name, first_name, middle_name, phone_number, email, passport_number"
				+ " FROM CLIENTS";
		boolean was_string = false;
		
		if(!findById) {
			if((client.getLastName().trim().length() != 0))
			{
				sql_query += !was_string? " WHERE last_name = " : " AND last_name = ";
				sql_query += "'" + client.getLastName().trim() + "'";
				was_string = true;
			}
			if((client.getFirstName().trim().length() != 0))
			{
				sql_query += !was_string? " WHERE first_name = " : " AND first_name = ";
				sql_query += "'" + client.getFirstName().trim() + "'";
				was_string = true;
			}
			if((client.getMiddleName().trim().length() != 0))
			{
				sql_query += !was_string? " WHERE middle_name = " : " AND middle_name = ";
				sql_query += "'" + client.getMiddleName().trim() + "'";
				was_string = true;
			}
			if((client.getPhoneNumber().trim().length() != 0))
			{
				sql_query += !was_string? " WHERE phone_number = " : " AND phone_number = ";
				sql_query += "'" + client.getPhoneNumber().trim() + "'";
				was_string = true;
			}
			if((client.getEmail().trim().length() != 0))
			{
				sql_query += !was_string? " WHERE email = " : " AND email = ";
				sql_query += "'" + client.getEmail().trim() + "'";
				was_string = true;
			}
			if((client.getPassportNumber().trim().length() != 0))
			{
				sql_query += !was_string? " WHERE passport_number = " :
					" AND passport_number = ";
				sql_query += "'" + client.getPassportNumber().trim() + "'";
				was_string = true;
			}
		}
		else
			sql_query += " WHERE client_id = " + Integer.toString(client.getClientId());
		
		 return sql_query;
	}
	
}
