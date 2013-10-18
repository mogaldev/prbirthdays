package com.mogal.prbirthdays.lists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.mogal.prbirthdays.model.BaseModel;

/**
 * Generic model adapter for {@link BaseModel} entities. Used as list adapter
 * and manages the way the items will been represented.
 */
public abstract class BaseModelAdapter extends BaseAdapter {
	protected BaseModel[] data;
	protected Context context;
	protected LayoutInflater inflater;
	/**
	 * The resource id of the layout represents a single item. Should be set in
	 * the derived model's adapter C'tor.
	 */
	protected int resIdLayoutItem;

	/**
	 * Construct the adapter
	 * 
	 * @param data
	 *            - the records to be displayed
	 * @param context
	 *            - context for the getView method
	 */
	public BaseModelAdapter(BaseModel[] data, Context context) {
		this.data = data;
		this.context = context;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * Construct the adapter
	 * 
	 * @param response
	 *            - {@link GenericResponse} that represents the response from
	 *            the server.
	 * @param modelArrCls
	 *            - the class of the model as array. for example: User[].class
	 * @param context
	 *            - context for the getView method
	 */
	// public <T> BaseModelAdapter(GenericResponse response, Class<T>
	// modelArrCls,
	// Context context) {
	// this(response.getResults(modelArrCls), context);
	// }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getCount() {
		return data.length;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BaseModel getItem(int position) {
		return data[position];
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getItemId(int position) {
		return position; // TODO maybe return the id from the entity
	}

	/**
	 * Getting view representation of the model. every {@link BaseModel} should
	 * implement getView method for this purpose.
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getViewFromModel((BaseModel) getItem(position), convertView,
				parent);
	}

	/**
	 * Populate an item view with the model information
	 * 
	 * @param model
	 *            - the model to fill the view with its details
	 * @param convertView
	 *            - the resulted view
	 * @param parent
	 *            - parent container. used for passing to the inflate function
	 * @return view ready to be displayed in the list
	 */
	protected abstract View getViewFromModel(BaseModel model, View convertView,
			ViewGroup parent);

}
