package bank_frontend;
	
import java.awt.Insets;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.fxml.FXMLLoader;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.NumberFormatException;

import clients_dao.*;
import credit_dao.*;
import credit_offer_dao.*;
import client_dto.*;
import credit_dto.*;
import credit_offer_dto.*;
import credit_offer_block.*;

public class Main extends Application {
	
	
	
	private TextField last_name_text;
	private TextField first_name_text;
	private TextField middle_name_text;
	private TextField phone_number_text;
	private TextField email_text;
	private TextField passport_number_text;
	private TextField loan_limit_text;
	private TextField interest_rate_text;
	private HashMap<Integer, TextField> last_name_text_result = new HashMap<Integer, TextField>();
	private HashMap<Integer, TextField> first_name_text_result = new HashMap<Integer, TextField>();
	private HashMap<Integer, TextField> middle_name_text_result = new HashMap<Integer, TextField>();
	private HashMap<Integer, TextField> phone_number_text_result = new HashMap<Integer, TextField>();
	private HashMap<Integer, TextField> email_text_result = new HashMap<Integer, TextField>();
	private HashMap<Integer, TextField> passport_number_text_result = new HashMap<Integer, TextField>();
	private HashMap<Integer, TextField> loan_limit_text_result = new HashMap<Integer, TextField>();
	private HashMap<Integer, TextField> interest_rate_text_result = new HashMap<Integer, TextField>();
	
	private Button get_clients;
	private Button save_changes_clients;
	private Button add_client;
	private Button get_credits;
	private Button save_changes_credits;
	private Button add_credit;
	private Button get_offers;
	private Button save_changes_offers;
	private Button add_offer;
	private Button clear_client_in_offer;
	private Button clear_credit_in_offer;
	private HashMap<Integer, Button> delete_client = new HashMap<Integer, Button>();
	private HashMap<Integer, Button> delete_credit = new HashMap<Integer, Button>();
	private HashMap<Integer, Button> delete_offer = new HashMap<Integer, Button>();
	private HashMap<Integer, ToggleButton> change_client = new HashMap<Integer, ToggleButton>();
	private HashMap<Integer, ToggleButton> change_credit = new HashMap<Integer, ToggleButton>();
	private HashMap<Integer, ToggleButton> make_offer_to_client = new HashMap<Integer, ToggleButton>();
	private HashMap<Integer, ToggleButton> offer_credit = new HashMap<Integer, ToggleButton>();
	
	private ToggleGroup offer_to_client_group;
	private ToggleGroup offer_credit_group;
	private ToggleGroup change_client_group;
	private ToggleGroup change_credit_group;
	
	private HashMap<Integer, HBox> clients_results_box = new HashMap<Integer, HBox>();
	private HashMap<Integer, HBox> credits_results_box = new HashMap<Integer, HBox>();
	private HashMap<Integer, HBox> offers_results_box = new HashMap<Integer, HBox>();
	private HashMap<Integer, CreditOfferBlock> results_block = new HashMap<Integer, CreditOfferBlock>();
	private HashMap<Integer, VBox> offers_buttons_edit_box = new HashMap<Integer, VBox>();
	
	private VBox clients_vbox;
	private VBox credits_vbox;
	private VBox offers_vbox;
	
	private TabPane root;
	
	private CreditOfferBlock search_add_offer;
	private CreditOfferDTO offer_main_dto = new CreditOfferDTO();
	private HashMap<Integer, CreditOfferBlock> results_offers_text = new HashMap<Integer, CreditOfferBlock>();
	private HashMap<Integer, CreditOfferDTO> results_offers = new HashMap<Integer,CreditOfferDTO>();
	private HashMap<Integer, ClientDTO> results_clients = new HashMap<Integer, ClientDTO>();
	private HashMap<Integer, CreditDTO> results_credits = new HashMap<Integer, CreditDTO>();
	private ClientsDAO manage_clients = new ClientsDAO();
	private CreditDAO manage_credits = new CreditDAO(); 
	private CreditOfferDAO manage_offers = new CreditOfferDAO();
	
	private EventHandler<ActionEvent> search_clients_handler;
	private EventHandler<ActionEvent> add_client_handler;
	private EventHandler<ActionEvent> save_changes_clients_handler;
	private EventHandler<ActionEvent> delete_client_handler;
	private EventHandler<ActionEvent> search_credits_handler;
	private EventHandler<ActionEvent> add_credit_handler;
	private EventHandler<ActionEvent> save_changes_credits_handler;
	private EventHandler<ActionEvent> delete_credit_handler;
	private EventHandler<ActionEvent> make_offer_to_client_handler;
	private EventHandler<ActionEvent> offer_credit_handler;
	private EventHandler<ActionEvent> search_offers_handler;
	private EventHandler<ActionEvent> add_offer_handler;
	private EventHandler<ActionEvent> save_changes_offers_handler;
	private EventHandler<ActionEvent> delete_offer_handler;
	private EventHandler<ActionEvent> clear_client_in_offer_handler;
	private EventHandler<ActionEvent> clear_credit_in_offer_handler;
	private EventHandler<ActionEvent> change_client_handler;
	private EventHandler<ActionEvent> change_credit_handler;
	
	private ArrayList<Integer> clients_decode = new ArrayList<Integer>();
	private ArrayList<Integer> credits_decode = new ArrayList<Integer>();
	private ArrayList<Integer> offers_decode = new ArrayList<Integer>();
	
	private int offer_client_id;
	private int offer_credit_id;
	private int change_client_num;
	private int change_credit_num;
	
	private EventHandler<ActionEvent> search_clients_handler_init()
	{
		return new EventHandler<ActionEvent>()
		{

					@Override
					public void handle(ActionEvent event)
					{						
						ClientDTO search_add_client = new ClientDTO();
						search_add_client.setLastName(last_name_text.getText());
						search_add_client.setFirstName(first_name_text.getText());
						search_add_client.setMiddleName(middle_name_text.getText());
						search_add_client.setPhoneNumber(phone_number_text.getText());
						search_add_client.setEmail(email_text.getText());
						search_add_client.setPassportNumber(passport_number_text.getText());
						
						if(results_clients.size() > 0)
						{
							clients_vbox.getChildren().remove(2, 2 + results_clients.size());
							clients_results_box.clear();
							last_name_text_result.clear();
							first_name_text_result.clear();
							middle_name_text_result.clear();
							phone_number_text_result.clear();
							email_text_result.clear();
							passport_number_text_result.clear();
							delete_client.clear();
							make_offer_to_client.clear();
							results_clients.clear();
							clients_decode.clear();
						}
							
						
						results_clients = manage_clients.search_clients(search_add_client, false);

						
						for(int i = 0; i < results_clients.size(); i++)
						{
							clients_results_box.put(i, new HBox());
							last_name_text_result.put(i, new TextField());
							first_name_text_result.put(i, new TextField());
							middle_name_text_result.put(i, new TextField());
							phone_number_text_result.put(i, new TextField());
							email_text_result.put(i, new TextField());
							passport_number_text_result.put(i, new TextField());
							delete_client.put(i, new Button());
							make_offer_to_client.put(i, new ToggleButton());
							clients_decode.add(i);
						}

						
						for(int i = 0; i < results_clients.size(); i++)
						{
							last_name_text_result.get(i).setText(results_clients.get(i).getLastName());
							first_name_text_result.get(i).setText(results_clients.get(i).getFirstName());
							middle_name_text_result.get(i).setText(results_clients.get(i).getMiddleName());
							phone_number_text_result.get(i).setText(results_clients.get(i).getPhoneNumber());
							email_text_result.get(i).setText(results_clients.get(i).getEmail());
							passport_number_text_result.get(i).setText(results_clients.get(i).getPassportNumber());
							delete_client.get(i).setText("Delete client");
							delete_client.get(i).setId(Integer.toString(i));
							delete_client.get(i).setOnAction(delete_client_handler);
							make_offer_to_client.get(i).setText("Offer credit");
							make_offer_to_client.get(i).setId(Integer.toString(i));
							make_offer_to_client.get(i).setOnAction(make_offer_to_client_handler);
							make_offer_to_client.get(i).setToggleGroup(offer_to_client_group);
							clients_results_box.get(i).getChildren().addAll(last_name_text_result.get(i), first_name_text_result.get(i),
									middle_name_text_result.get(i), phone_number_text_result.get(i), email_text_result.get(i), 
									passport_number_text_result.get(i), delete_client.get(i),make_offer_to_client.get(i));
							clients_vbox.getChildren().add(2 + i, clients_results_box.get(i));
						}		
					}
		};
	}

	
	
	private EventHandler<ActionEvent> add_client_handler_init()
	{
		return new EventHandler<ActionEvent>() 
				{
					@Override
					public void handle(ActionEvent event)
					{
						ClientDTO clientik = new ClientDTO();
						clientik.setLastName(last_name_text.getText());
						clientik.setFirstName(first_name_text.getText());
						clientik.setMiddleName(middle_name_text.getText());
						clientik.setPhoneNumber(phone_number_text.getText());
						clientik.setEmail(email_text.getText());
						clientik.setPassportNumber(passport_number_text.getText());
						
						
						manage_clients.add_client(clientik);
					}
				};
	}
	
	private EventHandler<ActionEvent> save_changes_clients_handler_init()
	{
		return new EventHandler<ActionEvent>() 
				{
					
					@Override
					public void handle(ActionEvent event)
					{
						for(int i = 0; i < results_clients.size(); i++)
						{
							results_clients.get(clients_decode.get(i)).setLastName(last_name_text_result.get(clients_decode.get(i)).getText());
							results_clients.get(clients_decode.get(i)).setFirstName(first_name_text_result.get(clients_decode.get(i)).getText());
							results_clients.get(clients_decode.get(i)).setMiddleName(middle_name_text_result.get(clients_decode.get(i)).getText());
							results_clients.get(clients_decode.get(i)).setPhoneNumber(phone_number_text_result.get(clients_decode.get(i)).getText());
							results_clients.get(clients_decode.get(i)).setEmail(email_text_result.get(clients_decode.get(i)).getText());
							results_clients.get(clients_decode.get(i)).setPassportNumber(passport_number_text_result.get(clients_decode.get(i)).getText());
						}
						
						
							manage_clients.save_changes(results_clients, clients_decode);
					}
				};
	}
	
	private EventHandler<ActionEvent> delete_client_handler_init()
	{
		return new EventHandler<ActionEvent>() 
				{
					@Override
					public void handle(ActionEvent event)
					{
						Button button = (Button) event.getSource();
						int index_hbox = Integer.parseInt(button.getId());
						
						if(results_clients.get(index_hbox).getClientId() == offer_client_id)
						{
							offer_client_id = 0;
							offer_main_dto.setClientId(offer_client_id);
						
						
							search_add_offer.last_name_text.setText("");
							search_add_offer.first_name_text.setText("");
							search_add_offer.middle_name_text.setText("");
							search_add_offer.phone_number_text.setText("");
							search_add_offer.email_text.setText("");
							search_add_offer.passport_number_text.setText("");
						}
						
						
						manage_clients.remove_client(
								results_clients.get(index_hbox));
						
						
						results_clients.remove(index_hbox);
						clients_vbox.getChildren().remove(2 + index_hbox);
						clients_results_box.remove(index_hbox);
						last_name_text_result.remove(index_hbox);
						first_name_text_result.remove(index_hbox);
						middle_name_text_result.remove(index_hbox);
						phone_number_text_result.remove(index_hbox);
						email_text_result.remove(index_hbox);
						passport_number_text_result.remove(index_hbox);
						delete_client.remove(index_hbox);
						clients_decode.remove(index_hbox);
					}
				};
	}
	
	private EventHandler<ActionEvent> search_credits_handler_init()
	{
		return new EventHandler<ActionEvent>() 
				{
					@Override
					public void handle(ActionEvent event)
					{
						CreditDTO search_add_credit = new CreditDTO();
						search_add_credit.setLoanLimit(loan_limit_text.getText());
						search_add_credit.setInterestRate(interest_rate_text.getText());
						
						
						credits_vbox.getChildren().remove(2, 2 + results_credits.size());
						credits_results_box.clear();
						loan_limit_text_result.clear();
						interest_rate_text_result.clear();
						delete_credit.clear();
						offer_credit.clear();
						results_credits.clear();
						credits_decode.clear();
						
						
						if(results_credits.size() > 0)
							credits_vbox.getChildren().remove(2, 1 + results_credits.size());
						
						
						results_credits = manage_credits.search_credits(search_add_credit, false);
						
						
						for(int i = 0; i < results_credits.size(); i++)
						{
							credits_results_box.put(i, new HBox());
							loan_limit_text_result.put(i, new TextField());
							interest_rate_text_result.put(i, new TextField());
							offer_credit.put(i, new ToggleButton());
							delete_credit.put(i, new Button());
							credits_decode.add(i);
						}
						
						
						for(int i = 0; i < results_credits.size(); i++)
						{
							loan_limit_text_result.get(i).setText(results_credits.get(i).getLoanLimit());
							interest_rate_text_result.get(i).setText(results_credits.get(i).getInterestRate());
							delete_credit.get(i).setText("Delete credit");
							delete_credit.get(i).setId(Integer.toString(i));
							delete_credit.get(i).setOnAction(delete_credit_handler);
							offer_credit.get(i).setText("Offer credit");
							offer_credit.get(i).setId(Integer.toString(i));
							offer_credit.get(i).setOnAction(offer_credit_handler);
							offer_credit.get(i).setToggleGroup(offer_credit_group);
							credits_results_box.get(i).getChildren().addAll(loan_limit_text_result.get(i), interest_rate_text_result.get(i), delete_credit.get(i), offer_credit.get(i));
							credits_results_box.get(i).setSpacing(20);
							credits_vbox.getChildren().add(2 + i, credits_results_box.get(i));
						}

					}
				};
	}
	
	private EventHandler<ActionEvent> add_credit_handler_init()
	{
		return new EventHandler<ActionEvent>() 
				{
					@Override
					public void handle(ActionEvent event)
					{
						CreditDTO creditik = new CreditDTO();
						creditik.setLoanLimit(loan_limit_text.getText());
						creditik.setInterestRate(interest_rate_text.getText());
						
						
						manage_credits.add_credit(creditik);
					}
				};
	}
	
	private EventHandler<ActionEvent> save_changes_credits_handler_init()
	{
		return new EventHandler<ActionEvent>() 
				{
					@Override
					public void handle(ActionEvent event)
					{
						for(int i = 0; i < results_credits.size(); i++)
						{
							results_credits.get(credits_decode.get(i)).setLoanLimit(loan_limit_text_result.get(credits_decode.get(i)).getText());
							results_credits.get(credits_decode.get(i)).setInterestRate(interest_rate_text_result.get(credits_decode.get(i)).getText());
						}
							
						
						manage_credits.save_changes(results_credits, credits_decode);
					}
				};
	}
	
	private EventHandler<ActionEvent> delete_credit_handler_init()
	{
		return new EventHandler<ActionEvent>() 
				{
					@Override
					public void handle(ActionEvent event)
					{
						Button button = (Button) event.getSource();
						int index_hbox = Integer.parseInt(button.getId());
						
						
						if(results_credits.get(index_hbox).getCreditId() == offer_credit_id)
						{
							offer_credit_id = 0;
							offer_main_dto.setCreditId(offer_credit_id);
						
						
							search_add_offer.loan_limit_text.setText("");
							search_add_offer.interest_rate_text.setText("");
						}
						
						
						manage_credits.remove_credit(results_credits.get(index_hbox));
						
						
						results_credits.remove(index_hbox);
						credits_vbox.getChildren().remove(2 + index_hbox);
						credits_results_box.remove(index_hbox);
						loan_limit_text_result.remove(index_hbox);
						interest_rate_text_result.remove(index_hbox);
						delete_credit.remove(index_hbox);
						credits_decode.remove(index_hbox);
					}
				};
	}

	private EventHandler<ActionEvent> make_offer_to_client_handler_init()
	{
		return new EventHandler<ActionEvent>() 
				{
					@Override
					public void handle(ActionEvent event)
					{
						ToggleButton button = (ToggleButton) event.getSource();
						int index_hbox = Integer.parseInt(button.getId());
						
						if(change_client_num == 0) {
							if(button.isSelected()) {
								offer_client_id = results_clients.get(index_hbox).getClientId();
							
							
								search_add_offer.last_name_text.setText(results_clients.get(index_hbox).getLastName());
								search_add_offer.first_name_text.setText(results_clients.get(index_hbox).getFirstName());
								search_add_offer.middle_name_text.setText(results_clients.get(index_hbox).getMiddleName());
								search_add_offer.phone_number_text.setText(results_clients.get(index_hbox).getPhoneNumber());
								search_add_offer.email_text.setText(results_clients.get(index_hbox).getEmail());
								search_add_offer.passport_number_text.setText(results_clients.get(index_hbox).getPassportNumber());
							}
							else
							{
								offer_client_id = 0;
							
							
								search_add_offer.last_name_text.setText("");
								search_add_offer.first_name_text.setText("");
								search_add_offer.middle_name_text.setText("");
								search_add_offer.phone_number_text.setText("");
								search_add_offer.email_text.setText("");
								search_add_offer.passport_number_text.setText("");
							}
						}
						else {
							results_offers.get(change_client_num).setClientId(results_clients.get(index_hbox).getClientId());
							
							
							results_offers_text.get(change_client_num).last_name_text.setText(results_clients.get(index_hbox).getLastName());
							results_offers_text.get(change_client_num).first_name_text.setText(results_clients.get(index_hbox).getFirstName());
							results_offers_text.get(change_client_num).middle_name_text.setText(results_clients.get(index_hbox).getMiddleName());
							results_offers_text.get(change_client_num).phone_number_text.setText(results_clients.get(index_hbox).getPhoneNumber());
							results_offers_text.get(change_client_num).email_text.setText(results_clients.get(index_hbox).getEmail());
							results_offers_text.get(change_client_num).passport_number_text.setText(results_clients.get(index_hbox).getPassportNumber());
							
							
							root.getSelectionModel().select(2);
						}
						
						
						offer_main_dto.setClientId(offer_client_id);
					}
				};
	}
	
	private EventHandler<ActionEvent> offer_credit_handler_init()
	{
		return new EventHandler<ActionEvent>() 
				{
					@Override
					public void handle(ActionEvent event)
					{
						ToggleButton button = (ToggleButton) event.getSource();
						int index_hbox = Integer.parseInt(button.getId());
						if(change_credit_num == 0) {
							if(button.isSelected())
							{
								offer_credit_id = results_credits.get(index_hbox).getCreditId();
							
							
								search_add_offer.loan_limit_text.setText(results_credits.get(index_hbox).getLoanLimit());
								search_add_offer.interest_rate_text.setText(results_credits.get(index_hbox).getInterestRate());
							}
							else
							{
								offer_credit_id = 0;
							
							
								search_add_offer.loan_limit_text.setText("");
								search_add_offer.interest_rate_text.setText("");
							}
						}
						else
						{
								results_offers.get(change_credit_num).setCreditId(results_credits.get(index_hbox).getCreditId());
								
								
								results_offers_text.get(change_credit_num).loan_limit_text.setText(results_credits.get(index_hbox).getLoanLimit());
								results_offers_text.get(change_credit_num).interest_rate_text.setText(results_credits.get(index_hbox).getInterestRate());
								
								
								root.getSelectionModel().select(2);
						}

						offer_main_dto.setCreditId(offer_credit_id);
					 }
				};
	}
	
	private EventHandler<ActionEvent> clear_client_in_offer_handler_init()
	{
		return new EventHandler<ActionEvent>() 
				{
					@Override
					public void handle(ActionEvent event)
					{
						offer_client_id = 0;
						
						
						search_add_offer.last_name_text.setText("");
						search_add_offer.first_name_text.setText("");
						search_add_offer.middle_name_text.setText("");
						search_add_offer.phone_number_text.setText("");
						search_add_offer.email_text.setText("");
						search_add_offer.passport_number_text.setText("");
						
						
						offer_main_dto.setClientId(offer_client_id);
					}
					 
				};
	}
	
	private EventHandler<ActionEvent> clear_credit_in_offer_handler_init()
	{
		return new EventHandler<ActionEvent>() 
				{
					@Override
					public void handle(ActionEvent event)
					{
						offer_credit_id = 0;
						
						
						search_add_offer.loan_limit_text.setText("");
						search_add_offer.interest_rate_text.setText("");
						
						
						offer_main_dto.setCreditId(offer_credit_id);
					}
					 
				};
	}
	
	private EventHandler<ActionEvent> search_offers_handler_init()
	{
		return new EventHandler<ActionEvent>()
		{

					@Override
					public void handle(ActionEvent event)
					{					
						ClientDTO client_local_dto = new ClientDTO();
						CreditDTO credit_local_dto = new CreditDTO();
						HashMap<Integer, ClientDTO> client_dto_result_set = new HashMap<Integer, ClientDTO>();
						HashMap<Integer, CreditDTO> credit_dto_result_set = new HashMap<Integer, CreditDTO>();
						
						
						change_client_num = 0;
						change_credit_num = 0;
						
						
						try {
							offer_main_dto.setCreditSum(
								search_add_offer.credit_sum_text.getText().trim().length() > 0?
								Integer.parseInt(search_add_offer.credit_sum_text.getText()) : 0);
							offer_main_dto.setPaymentDate(search_add_offer.payment_date_text.getText());
							offer_main_dto.setPaymentSum(
								search_add_offer.payment_sum_text.getText().trim().length() > 0?
								Integer.parseInt(
								search_add_offer.payment_sum_text.getText()) : 0);
							offer_main_dto.setLoanBody(
								search_add_offer.loan_body_sum_text.getText().trim().length() > 0?
								Integer.parseInt(
										search_add_offer.loan_body_sum_text.getText()) : 0);
							offer_main_dto.setLoanInterest(
								search_add_offer.loan_body_sum_text.getText().trim().length() > 0?
								Integer.parseInt(search_add_offer.loan_interest_sum_text.getText()) : 0);
						}
						catch(NumberFormatException e)
						{
							return;
						}
						
						
						if(results_offers.size() > 0)
						{
							offers_vbox.getChildren().remove(2, 2 + results_offers.size());
							offers_results_box.clear();
							results_offers_text.clear();
							offers_buttons_edit_box.clear();
							delete_offer.clear();
							change_client.clear();
							change_credit.clear();
							results_offers.clear();
							offers_decode.clear();
						}
							
						
						
						results_offers = manage_offers.search_offers(offer_main_dto);

						
						for(int i = 0; i < results_offers.size(); i++)
						{
							offers_results_box.put(i, new HBox());
							results_offers_text.put(i, new CreditOfferBlock());
							offers_buttons_edit_box.put(i, new VBox());
							delete_offer.put(i, new Button());
							change_client.put(i, new ToggleButton());
							change_credit.put(i, new ToggleButton());
							delete_offer.put(i, new Button("opa"));
							offers_decode.add(i);
						}

						
						for(int i = 0; i < results_offers.size(); i++)
						{
							client_local_dto.setClientId(results_offers.get(i).getClientId());
							credit_local_dto.setCreditId(results_offers.get(i).getCreditId());
							client_dto_result_set = manage_clients.search_clients(client_local_dto, true);
							credit_dto_result_set = manage_credits.search_credits(credit_local_dto, true);
							
							
							results_offers_text.get(i).last_name_text.setText(client_dto_result_set.get(0).getLastName());
							results_offers_text.get(i).first_name_text.setText(client_dto_result_set.get(0).getFirstName());
							results_offers_text.get(i).middle_name_text.setText(client_dto_result_set.get(0).getMiddleName());
							results_offers_text.get(i).phone_number_text.setText(client_dto_result_set.get(0).getPhoneNumber());
							results_offers_text.get(i).email_text.setText(client_dto_result_set.get(0).getEmail());
							results_offers_text.get(i).passport_number_text.setText(client_dto_result_set.get(0).getPassportNumber());
							results_offers_text.get(i).loan_limit_text.setText(credit_dto_result_set.get(0).getLoanLimit());
							results_offers_text.get(i).interest_rate_text.setText(credit_dto_result_set.get(0).getInterestRate());
							results_offers_text.get(i).credit_sum_text.setText(Integer.toString(results_offers.get(i).getCreditSum()));
							results_offers_text.get(i).payment_date_text.setText(results_offers.get(i).getPaymentDate());
							results_offers_text.get(i).payment_sum_text.setText(Integer.toString(results_offers.get(i).getPaymentSum()));
							results_offers_text.get(i).loan_body_sum_text.setText(Integer.toString(results_offers.get(i).getLoanBody()));
							results_offers_text.get(i).loan_interest_sum_text.setText(Integer.toString(results_offers.get(i).getLoanInterest()));
							
							
							delete_offer.get(i).setText("Delete offer");
							delete_offer.get(i).setId(Integer.toString(i));
							delete_offer.get(i).setOnAction(delete_offer_handler);
							change_client.get(i).setText("Change Client");
							change_client.get(i).setId(Integer.toString(i));
							change_client.get(i).setOnAction(change_client_handler);
							change_client.get(i).setToggleGroup(change_client_group);
							change_credit.get(i).setText("Change Credit");
							change_credit.get(i).setId(Integer.toString(i));
							change_credit.get(i).setOnAction(change_credit_handler);
							change_credit.get(i).setToggleGroup(change_credit_group);
							
							
							offers_buttons_edit_box.get(i).getChildren().addAll(delete_offer.get(i), 
									change_client.get(i), change_credit.get(i));
							offers_buttons_edit_box.get(i).setSpacing(40);
							offers_buttons_edit_box.get(i).setAlignment(javafx.geometry.Pos.CENTER);							
							
							offers_results_box.get(i).getChildren().addAll(results_offers_text.get(i).getResultVbox(), 
									offers_buttons_edit_box.get(i));
							offers_vbox.getChildren().add(2 + i, offers_results_box.get(i));
						}		
					}
		};
	}

	
	
	private EventHandler<ActionEvent> add_offer_handler_init()
	{
		return new EventHandler<ActionEvent>() 
				{
					@Override
					public void handle(ActionEvent event)
					{
						try {
							offer_main_dto.setCreditSum(Integer.parseInt(search_add_offer.credit_sum_text.getText()));
							offer_main_dto.setPaymentDate(search_add_offer.payment_date_text.getText());
							offer_main_dto.setPaymentSum(Integer.parseInt(search_add_offer.payment_sum_text.getText()));
							offer_main_dto.setLoanBody(Integer.parseInt(search_add_offer.loan_body_sum_text.getText()));
							offer_main_dto.setLoanInterest(Integer.parseInt(search_add_offer.loan_interest_sum_text.getText()));
						}
						catch(NumberFormatException e)
						{
							return;
						}
						
							
						manage_offers.add_offer(offer_main_dto);
					}
				};
	}
	
	private EventHandler<ActionEvent> save_changes_offers_handler_init()
	{
		return new EventHandler<ActionEvent>() 
				{
					
					@Override
					public void handle(ActionEvent event)
					{	
						CreditOfferDTO local_offer_dto = new CreditOfferDTO();
							for(int i = 0; i < results_offers.size(); i++)
							{	
								try {
									local_offer_dto.setCreditSum(Integer.parseInt(results_offers_text.get(offers_decode.get(i)).credit_sum_text.getText()));
									local_offer_dto.setPaymentDate(results_offers_text.get(offers_decode.get(i)).payment_date_text.getText());
									local_offer_dto.setPaymentSum(Integer.parseInt(results_offers_text.get(offers_decode.get(i)).payment_sum_text.getText()));
									local_offer_dto.setLoanBody(Integer.parseInt(results_offers_text.get(offers_decode.get(i)).loan_body_sum_text.getText()));
									local_offer_dto.setLoanInterest(Integer.parseInt(results_offers_text.get(offers_decode.get(i)).loan_interest_sum_text.getText()));
								}
								catch(NumberFormatException e)
								{
									continue;
								}
								
								
								results_offers.get(offers_decode.get(i)).setCreditSum(local_offer_dto.getCreditSum());
								results_offers.get(offers_decode.get(i)).setPaymentDate(local_offer_dto.getPaymentDate());
								results_offers.get(offers_decode.get(i)).setPaymentSum(local_offer_dto.getPaymentSum());
								results_offers.get(offers_decode.get(i)).setLoanBody(local_offer_dto.getLoanBody());
								results_offers.get(offers_decode.get(i)).setLoanInterest(local_offer_dto.getLoanInterest());
							}
						
						
							manage_offers.save_changes(results_offers, offers_decode);
							
							
							change_client_num = 0;
							change_credit_num = 0;
					}
				};
	}
	
	private EventHandler<ActionEvent> delete_offer_handler_init()
	{
		return new EventHandler<ActionEvent>() 
				{
					@Override
					public void handle(ActionEvent event)
					{
						Button button = (Button) event.getSource();
						int index_hbox = Integer.parseInt(button.getId());
						
						
						manage_offers.remove_offer(
								results_offers.get(index_hbox));
						
						
						results_offers.remove(index_hbox);
						offers_vbox.getChildren().remove(2 + index_hbox);
						offers_results_box.remove(index_hbox);
						results_offers_text.remove(index_hbox);
						offers_buttons_edit_box.remove(index_hbox);
						change_client.remove(index_hbox);
						change_credit.remove(index_hbox);
						delete_client.remove(index_hbox);
						offers_decode.remove(index_hbox);
					}
				};
	}
	
	private EventHandler<ActionEvent> change_client_handler_init()
	{
		return new EventHandler<ActionEvent>() 
				{
					
					@Override
					public void handle(ActionEvent event)
					{	
							ToggleButton button = (ToggleButton) event.getSource();
							int index_btn = Integer.parseInt(button.getId());
							
							if(button.isSelected()) {
								change_client_num = index_btn;
								root.getSelectionModel().select(0);
							}
							else
							{
								change_client_num = 0;
							}
					}
				};
	}
	
	private EventHandler<ActionEvent> change_credit_handler_init()
	{
		return new EventHandler<ActionEvent>() 
				{
					
					@Override
					public void handle(ActionEvent event)
					{	
							ToggleButton button = (ToggleButton) event.getSource();
							int index_btn = Integer.parseInt(button.getId());
							
							if(button.isSelected()) {
								change_credit_num = index_btn;
								root.getSelectionModel().select(1);
							}
							else
							{
								change_credit_num = 0;
							}
					}
				};
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Label last_name_label = new Label("Last Name");
			Label first_name_label = new Label("First name");
			Label middle_name_label = new Label("Middle name");
			Label phone_number_label = new Label("Phone number");
			Label email_label = new Label("email");
			Label passport_number_label = new Label("Passport number");
			Label loan_limit_label = new Label("Loan limit");
			Label interest_rate_label = new Label("Interest rate");

			
			VBox last_name_box = new VBox();
			VBox first_name_box = new VBox();
			VBox middle_name_box = new VBox();
			VBox phone_number_box = new VBox();
			VBox email_box = new VBox();
			VBox passport_number_box = new VBox();
			VBox loan_limit_box = new VBox();
			VBox interest_rate_box = new VBox();
			
			
			HBox clients_box = new HBox();
			HBox credits_box = new HBox();
			HBox clients_buttons_box = new HBox();
			HBox credits_buttons_box = new HBox();
			HBox offers_buttons_box = new HBox();
			HBox clients_results_box = new HBox();
			HBox credits_results_box = new HBox();
			
			
			ScrollPane clients_scroll = new ScrollPane();
			ScrollPane credits_scroll = new ScrollPane();
			ScrollPane offers_scroll = new ScrollPane();
			
			
			Tab clients_tab = new Tab("Clients", clients_scroll);
			Tab credits_tab = new Tab("Credits", credits_scroll);
			Tab offers_tab = new Tab("Credit Offers", offers_scroll);
			
			
			init_controls();
			
			
			Scene scene = new Scene(root, 1200, 800);
			primaryStage = new Stage();
			
			
			last_name_box.getChildren().addAll(last_name_label, last_name_text);
			first_name_box.getChildren().addAll(first_name_label, first_name_text);
			middle_name_box.getChildren().addAll(middle_name_label, middle_name_text);
			phone_number_box.getChildren().addAll(phone_number_label, phone_number_text);
			email_box.getChildren().addAll(email_label, email_text);
			passport_number_box.getChildren().addAll(passport_number_label,
			passport_number_text);
			loan_limit_box.getChildren().addAll(loan_limit_label, loan_limit_text);
			interest_rate_box.getChildren().addAll(interest_rate_label,
					interest_rate_text);
			
			
			last_name_box.setSpacing(20);
			first_name_box.setSpacing(20);
			middle_name_box.setSpacing(20);
			phone_number_box.setSpacing(20);
			email_box.setSpacing(20);
			passport_number_box.setSpacing(20);
			loan_limit_box.setSpacing(20);
			interest_rate_box.setSpacing(20);
			
			
			clients_box.getChildren().addAll(last_name_box, 
			first_name_box, middle_name_box, phone_number_box,
			email_box, passport_number_box);
			credits_box.getChildren().addAll(loan_limit_box, interest_rate_box);
			credits_box.setSpacing(20);
			
			
			get_clients.setText("Search");
			save_changes_clients.setText("Save changes");
			add_client.setText("Add client");
			get_credits.setText("Search");
			save_changes_credits.setText("Save changes");
			add_credit.setText("Add credit");
			get_offers.setText("Search");
			save_changes_offers.setText("Save changes");
			add_offer.setText("Add offer");
			clear_client_in_offer.setText("Clear client");
			clear_credit_in_offer.setText("Clear credit");
			
			
			clients_buttons_box.getChildren().addAll(get_clients, add_client);
			credits_buttons_box.getChildren().addAll(get_credits, add_credit);
			offers_buttons_box.getChildren().addAll(get_offers, add_offer, clear_client_in_offer, clear_credit_in_offer);
			clients_buttons_box.setSpacing(50);
			credits_buttons_box.setSpacing(50);
			offers_buttons_box.setSpacing(50);
			clients_buttons_box.setAlignment(javafx.geometry.Pos.TOP_CENTER);
			credits_buttons_box.setAlignment(javafx.geometry.Pos.TOP_CENTER);
			offers_buttons_box.setAlignment(javafx.geometry.Pos.TOP_CENTER);
			
			
			clients_vbox.getChildren().addAll(clients_box, clients_buttons_box, 
					save_changes_clients);
			credits_vbox.getChildren().addAll(credits_box, 
					credits_buttons_box, save_changes_credits);
			offers_vbox.getChildren().addAll(search_add_offer.getResultVbox(),
					offers_buttons_box, save_changes_offers);
			clients_vbox.setSpacing(20);
			credits_vbox.setSpacing(20);
			offers_vbox.setSpacing(80);
			clients_vbox.setAlignment(javafx.geometry.Pos.TOP_CENTER);
			credits_vbox.setAlignment(javafx.geometry.Pos.TOP_CENTER);
			offers_vbox.setAlignment(javafx.geometry.Pos.TOP_CENTER);
			

			clients_scroll.setContent(clients_vbox);
			credits_scroll.setContent(credits_vbox);
			offers_scroll.setContent(offers_vbox);
						
			
			root.getTabs().add(clients_tab);
			root.getTabs().add(credits_tab);
			root.getTabs().add(offers_tab);

			
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void init_controls()
	{
		last_name_text = new TextField();
		first_name_text = new TextField();
		middle_name_text = new TextField();
		phone_number_text = new TextField();
		email_text = new TextField();
		passport_number_text = new TextField();
		loan_limit_text = new TextField();
		interest_rate_text = new TextField();
		
		
		get_clients = new Button();
		save_changes_clients = new Button();
		add_client = new Button();
		get_credits = new Button();
		save_changes_credits = new Button();
		add_credit = new Button();
		get_offers = new Button();
		save_changes_offers = new Button();
		add_offer = new Button();
		clear_client_in_offer = new Button();
		clear_credit_in_offer = new Button();
		
		clients_vbox = new VBox();
		credits_vbox = new VBox();
		offers_vbox = new VBox();
		
		
		root = new TabPane();
		search_add_offer = new CreditOfferBlock();
		
		
		offer_to_client_group = new ToggleGroup();
		offer_credit_group = new ToggleGroup();
		change_client_group = new ToggleGroup();
		change_credit_group = new ToggleGroup();
		
		
		search_add_offer.last_name_text.setEditable(false);
		search_add_offer.first_name_text.setEditable(false);
		search_add_offer.middle_name_text.setEditable(false);
		search_add_offer.phone_number_text.setEditable(false);
		search_add_offer.email_text.setEditable(false);
		search_add_offer.passport_number_text.setEditable(false);
		search_add_offer.loan_limit_text.setEditable(false);
		search_add_offer.interest_rate_text.setEditable(false);
		
		
		search_clients_handler = search_clients_handler_init();
		add_client_handler = add_client_handler_init();
		save_changes_clients_handler = save_changes_clients_handler_init();
		delete_client_handler = delete_client_handler_init();
		search_credits_handler = search_credits_handler_init();
		add_credit_handler = add_credit_handler_init();
		save_changes_credits_handler = save_changes_credits_handler_init();
		delete_credit_handler = delete_credit_handler_init();
		search_offers_handler = search_offers_handler_init();
		add_offer_handler = add_offer_handler_init();
		save_changes_offers_handler = save_changes_offers_handler_init();
		delete_offer_handler = delete_offer_handler_init();
		make_offer_to_client_handler = make_offer_to_client_handler_init();
		offer_credit_handler = offer_credit_handler_init();
		clear_client_in_offer_handler = clear_client_in_offer_handler_init();
		clear_credit_in_offer_handler = clear_credit_in_offer_handler_init();
		change_client_handler = change_client_handler_init();
		change_credit_handler = change_credit_handler_init();
		
		
		get_clients.setOnAction(search_clients_handler);
		save_changes_clients.setOnAction(save_changes_clients_handler);
		add_client.setOnAction(add_client_handler);
		get_credits.setOnAction(search_credits_handler);
		save_changes_credits.setOnAction(save_changes_credits_handler);
		add_credit.setOnAction(add_credit_handler);
		get_offers.setOnAction(search_offers_handler);
		save_changes_offers.setOnAction(save_changes_offers_handler);
		add_offer.setOnAction(add_offer_handler);
		clear_client_in_offer.setOnAction(clear_client_in_offer_handler);
		clear_credit_in_offer.setOnAction(clear_credit_in_offer_handler);
		
		
		offer_client_id = 0;
		offer_credit_id = 0;
		change_client_num = 0;
		change_credit_num = 0;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
