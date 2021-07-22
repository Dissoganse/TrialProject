package credit_offer_block;

import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CreditOfferBlock {
	
	
	public CreditOfferBlock()
	{
		last_name_label = new Label("Last Name");
		first_name_label = new Label("First Name");
		middle_name_label = new Label("Middle Name");
		phone_number_label = new Label("Phone Number");
		email_label = new Label("Email");
		passport_number_label = new Label("Passport Number");
		loan_limit_label = new Label("Loan Limit");
		interest_rate_label = new Label("Interest Rate");
		credit_sum_label = new Label("Credit Sum");
		payment_date_label = new Label("Payment Date");
		payment_sum_label = new Label("Payment Sum");
		loan_body_sum_label = new Label("Loan Body");
		loan_interest_sum_label = new Label("Loan Interest");
		last_name_text = new TextField();
		first_name_text = new TextField();
		middle_name_text = new TextField();
		phone_number_text = new TextField();
		email_text = new TextField();
		passport_number_text = new TextField();
		loan_limit_text = new TextField();
		interest_rate_text = new TextField();
		credit_sum_text = new TextField();
		payment_date_text = new TextField();
		payment_sum_text = new TextField();
		loan_body_sum_text = new TextField();
		loan_interest_sum_text = new TextField();
		last_name_vbox = new VBox();
		first_name_vbox = new VBox();
		middle_name_vbox = new VBox();
		phone_number_vbox = new VBox();
		email_vbox = new VBox();
		passport_number_vbox = new VBox();
		loan_limit_vbox = new VBox();
		interest_rate_vbox = new VBox();
		credit_sum_vbox = new VBox();
		payment_date_vbox = new VBox();
		payment_sum_vbox = new VBox();
		loan_body_sum_vbox = new VBox();
		loan_interest_sum_vbox = new VBox();
		client_hbox = new HBox();
		credit_hbox = new HBox();
		credit_details_hbox = new HBox();
		result_vbox = new VBox();
		
		
		result_vbox.setSpacing(10);
		last_name_vbox.setSpacing(10);
		first_name_vbox.setSpacing(10);
		middle_name_vbox.setSpacing(10);
		phone_number_vbox.setSpacing(10);
		email_vbox.setSpacing(10);
		passport_number_vbox.setSpacing(10);
		loan_limit_vbox.setSpacing(10);
		interest_rate_vbox.setSpacing(10);
		credit_sum_vbox.setSpacing(10);
		payment_date_vbox.setSpacing(10);
		payment_sum_vbox.setSpacing(10);
		loan_body_sum_vbox.setSpacing(10);
		loan_interest_sum_vbox.setSpacing(10);
		
		
		last_name_vbox.getChildren().addAll(last_name_label, last_name_text);
		first_name_vbox.getChildren().addAll(first_name_label, first_name_text);
		middle_name_vbox.getChildren().addAll(middle_name_label, middle_name_text);
		phone_number_vbox.getChildren().addAll(phone_number_label, phone_number_text);
		email_vbox.getChildren().addAll(email_label, email_text);
		passport_number_vbox.getChildren().addAll(passport_number_label, passport_number_text);
		loan_limit_vbox.getChildren().addAll(loan_limit_label, loan_limit_text);
		interest_rate_vbox.getChildren().addAll(interest_rate_label, interest_rate_text);
		credit_sum_vbox.getChildren().addAll(credit_sum_label, credit_sum_text);
		payment_date_vbox.getChildren().addAll(payment_date_label, payment_date_text);
		payment_sum_vbox.getChildren().addAll(payment_sum_label, payment_sum_text);
		loan_body_sum_vbox.getChildren().addAll(loan_body_sum_label, loan_body_sum_text);
		loan_interest_sum_vbox.getChildren().addAll(loan_interest_sum_label, loan_interest_sum_text);
		
		client_hbox.getChildren().addAll(last_name_vbox, first_name_vbox, middle_name_vbox,
				phone_number_vbox, email_vbox, passport_number_vbox);
		credit_hbox.getChildren().addAll(loan_limit_vbox, interest_rate_vbox);
		credit_details_hbox.getChildren().addAll(credit_sum_vbox, payment_date_vbox,
				payment_sum_vbox, loan_body_sum_vbox, loan_interest_sum_vbox);
		
	
		result_vbox.getChildren().addAll(client_hbox, credit_hbox, credit_details_hbox);
	}
	
	
	private Label last_name_label;
	private Label first_name_label;
	private Label middle_name_label;
	private Label phone_number_label;
	private Label email_label;
	private Label passport_number_label;
	private Label loan_limit_label;
	private Label interest_rate_label;
	private Label credit_sum_label;
	private Label payment_date_label;
	private Label payment_sum_label;
	private Label loan_body_sum_label;
	private Label loan_interest_sum_label;
	public TextField last_name_text;
	public TextField first_name_text;
	public TextField middle_name_text;
	public TextField phone_number_text;
	public TextField email_text;
	public TextField passport_number_text;
	public TextField loan_limit_text;
	public TextField interest_rate_text;
	public TextField credit_sum_text;
	public TextField payment_date_text;
	public TextField payment_sum_text;
	public TextField loan_body_sum_text;
	public TextField loan_interest_sum_text;
	private VBox last_name_vbox;
	private VBox first_name_vbox;
	private VBox middle_name_vbox;
	private VBox phone_number_vbox;
	private VBox email_vbox;
	private VBox passport_number_vbox;
	private VBox loan_limit_vbox;
	private VBox interest_rate_vbox;
	private VBox credit_sum_vbox;
	private VBox payment_date_vbox;
	private VBox payment_sum_vbox;
	private VBox loan_body_sum_vbox;
	private VBox loan_interest_sum_vbox;
	private HBox client_hbox;
	private HBox credit_hbox;
	private HBox credit_details_hbox;
	private VBox result_vbox;
	
	
	public VBox getResultVbox()
	{
		return this.result_vbox;
	}
}
