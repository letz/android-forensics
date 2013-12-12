package pt.ist.androidforensics.data;


/**
 * All objects that will be exported to .csv have to extend from this class
 *
 * @author letz
 *
 */
public abstract class Model {

	private int mId;

	public int getId() {
		return mId;
	}

	public void setId(int mId) {
		this.mId = mId;
	}

	/**
	 * Create a dot separated format to save the object
	 * @return
	 */
	public abstract String toCSV();

	/**
	 * Create a dot separated format to save the object header
	 * @return
	 */
	public abstract String toCSVHeader();

}
