package hr.fer.oprpp1.hw04.db;

/**
 * A class which is implemented as a strategy for the {@link IFieldValueGetter} interface
 * @author Vito Sabalic
 *
 */
public class FieldValueGetters {
	
	/**
	 * Returns the first name of the provided {@link StudentRecord} class
	 */
	public static final IFieldValueGetter FIRST_NAME = record -> record.getFirstName();
	
	/**
	 * Returns the last name of the provided {@link StudentRecord} class
	 */
	public static final IFieldValueGetter LAST_NAME = record -> record.getLastName();
	
	/**
	 * Returns the jmbag of the provided {@link StudentRecord} class
	 */
	public static final IFieldValueGetter JMBAG = record -> record.getJmbag();

}
