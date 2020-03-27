package com.batch2.AutomationFrameworkFromScratch.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Constants
{
	//Operating System Enum
	public static enum OSType {Windows, MacOS, Unix, Other};
	
	//Common Regular Expressions
	public static final String REG_EX_WILD_CARD_CHAR = "(.)*";
	public static final String REG_EX_EXTRA_SPACE = "\\s+";
	
	//Common Values
	public static final String VALUE_TRUE = "TRUE";
	public static final String VALUE_FALSE = "FALSE";
	public static final String VALUE_YES = "Y";
	
	//Common Strings
	public static final String BLANK = "";
	public static final String WHITE_SPACE = " ";
	public static final String COMMA = ",";
	public static final String EMPTY = "empty";
	public static final String UPDATE = "update";
	public static final String UPDATED = "Updated";
	public static final int ZERO = 0;
	
	//Browser identifiers
	public static final String BROWSER = "BROWSER";
	public static final String BROWSER_IE = "ie";
	public static final String BROWSER_FIREFOX = "firefox";
	public static final String BROWSER_CHROME = "chrome";
	public static final String BROWSER_PHANTOM = "phantomjs";
	
	public static final String YES = "Yes";
	public static final String RANDOM = "random";
	
	
	public static final String ACTIVE = "active";
	public static final String XPATH = "xpath";
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String NO = "No";
	public static final String ADD_LOAN = "Add Loan";
	public static final String CURRENT_TIMESTAMP = "currentTimestamp";
	public static final String EARLIER_TIMESTAMP = "earlierTimestamp";
	public static final String NOT_NULL = "Not Null";
	public static final String ASCENDING = "asc";
	public static final String TODAY = "Today";
	
	public static String TIME_STAMP = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
	
	// Meta Data types
	public static final String RICH_HTML_TEXT = "Rich HTML Text";
	public static final String TEXT_INPUT = "Text Input";
	public static final String TEXT_AREA = "Text Area";
	public static final String DATE_INPUT = "Date Input";
	public static final String BOOLEAN = "Boolean";
	public static final String FILE = "File";
	
	// Authentication types
	public static final String OKTA_VERIFY = "Okta Verify";
	public static final String GOOGLE_AUTHENTICATOR = "Google Authenticator";
	
	// HCMS and QNP roles
	public static final String SYSTEM_ADMIN = "SystemAdmin";
	public static final String PROGRAM_ADMIN = "ProgramAdmin";
	public static final String CONTENT_SUBMITER = "ContentSubmitter";
	public static final String CONTENT_APPROVER = "ContentApprover";
	public static final String CONTENT_PUBLISHER = "ContentPublisher";
	
}
