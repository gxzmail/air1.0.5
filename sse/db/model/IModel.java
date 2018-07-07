package sse.db.model;

public interface IModel {

	/*
	public   String[] getTitles();

	public   String getTitle(int index);

	public  int[] getColumnWidths();

	public  Object[] getColumnTypes();

	public int getColumnIndex(String colname);
	
	*/

	/**
	 * Gets the value.
	 * 
	 * @param index
	 *            the index
	 * @return the value
	 */
	public Object getValue(int index);

	/**
	 * Sets the value.
	 * 
	 * @param index
	 *            the index
	 * @param value
	 *            the value
	 * @return true, if successful
	 */
	public boolean setValue(int index, String value);
	
	/*
	public Object getColumnType(String title);

	public int getColumnWidth(String title);
	*/
}
