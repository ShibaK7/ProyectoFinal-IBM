package com.ibm.CardService.ServiceGetCard.constants;

public class Constant {
	
	private Constant() {
		throw new IllegalStateException(Constant.MESSAGE_CLASS);
	}

	public static final String QUERY = "SELECT C.CARDNAME  FROM PROFILES AS PF "
			+ "INNER JOIN CARDS AS C ON C.CARDID = PF.CARDID "
			+ "INNER JOIN SALARYS  AS S ON S.SALARYID = PF.MINSALARYID "
			+ "INNER JOIN SALARYS AS S2 ON S2.SALARYID = PF.MAXSALARYID "
			+ "INNER JOIN PASSIONS AS P ON P.PASSIONID = PF.PASSIONID "
			+ "INNER JOIN AGES AS A ON A.AGEID = PF.MINAGEID "
			+ "INNER JOIN AGES AS A2 ON A2.AGEID = PF.MAXAGEID "
			+ "WHERE A.AGE <= ? AND A2.AGE >= ? AND "
			+ "S.AMOUNT  <= ? AND (S2.AMOUNT >= ? OR S2.AMOUNT = 0) AND "
			+ "P.PASSIONNAME = ?";
	
	public static final String INFO_CARD = " types of credit card have been obtained"; 
	
	public static final String EMPTY_ERROR = "Empty values ​​cannot be processed";
	public static final String INVALID_ERROR = "Invalid values ​​cannot be processed";
	
	public static final String ERROR_CODE = "Error Code: ";
	public static final String ERROR_TYPE = " Error Type: ";
	
	public static final String MESSAGE_CLASS = "Utility class";
}
