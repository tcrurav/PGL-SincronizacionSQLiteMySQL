package com.example.tiburcio.ejemploproveedorcontenido.ciclo;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.tiburcio.ejemploproveedorcontenido.R;
import com.example.tiburcio.ejemploproveedorcontenido.constantes.G;
import com.example.tiburcio.ejemploproveedorcontenido.pojos.Ciclo;
import com.example.tiburcio.ejemploproveedorcontenido.proveedor.CicloProveedor;
import com.example.tiburcio.ejemploproveedorcontenido.proveedor.Contrato;

public class CicloListFragment extends ListFragment
		implements LoaderManager.LoaderCallbacks<Cursor> {
	
	//private static final String LOGTAG = "Tiburcio - CicloListFragment";

	CicloCursorAdapter mAdapter;
	LoaderManager.LoaderCallbacks<Cursor> mCallbacks;

	ActionMode mActionMode;
	View viewSeleccionado;

	public static CicloListFragment newInstance() {
		CicloListFragment f = new CicloListFragment();

		return f;
	}

	/**
	 * When creating, retrieve this instance's number from its arguments.
	 */
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);

		MenuItem menuItem = menu.add(Menu.NONE, G.INSERTAR, Menu.NONE, "INSERTAR");
		menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		menuItem.setIcon(R.drawable.ic_nuevo_registro);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case G.INSERTAR:
				Intent intent = new Intent(getActivity(), CicloInsercionActivity.class);
				startActivity(intent);
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * The Fragment's UI is just a simple text view showing its
	 * instance number.
	 */

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		//Log.i(LOGTAG, "onCreateView");
		View v = inflater.inflate(R.layout.fragment_ciclo_list, container, false);

		mAdapter = new CicloCursorAdapter(getActivity());
		setListAdapter(mAdapter);

		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//Log.i(LOGTAG, "onActivityCreated");

		mCallbacks = this;

		getLoaderManager().initLoader(0, null, mCallbacks);

		getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				if(mActionMode!=null){
					return false;
				}
				mActionMode = getActivity().startActionMode(mActionModeCallback);
				view.setSelected(true);
				viewSeleccionado = view;
				return true;
			}
		});
	}

	ActionMode.Callback mActionModeCallback = new ActionMode.Callback(){

		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			MenuInflater inflater = mode.getMenuInflater();
			inflater.inflate(R.menu.menu_contextual, menu);
			return true;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false;
		}

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			switch(item.getItemId()){
				case R.id.menu_contextual_borrar:
					int cicloId = (Integer) viewSeleccionado.getTag();
					CicloProveedor.delete(getActivity().getContentResolver(), cicloId);
					break;
				case R.id.menu_contextual_editar:
					Intent intent = new Intent(getActivity(), CicloActualizacionActivity.class);
					cicloId = (Integer) viewSeleccionado.getTag();
					Log.i("El identificador 1", "kk"+cicloId);
					Ciclo ciclo = CicloProveedor.read(getActivity().getContentResolver(), cicloId);
					Log.i("El identificador", ciclo.getNombre());
					intent.putExtra("ID", ciclo.getID());
					intent.putExtra("Nombre", ciclo.getNombre());
					intent.putExtra("Abreviatura", ciclo.getAbreviatura());
					startActivity(intent);
					break;
				default:
					return false;
			}
			mode.finish();
			return true;
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
			mActionMode = null;
		}
	};

	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// This is called when a new Loader needs to be created.  This
		// sample only has one Loader, so we don't care about the ID.
		// First, pick the base URI to use depending on whether we are
		// currently filtering.
		String columns[] = new String[] { Contrato.Ciclo._ID,
										  Contrato.Ciclo.NOMBRE,
				                          Contrato.Ciclo.ABREVIATURA
										};

		Uri baseUri = Contrato.Ciclo.CONTENT_URI;

		// Now create and return a CursorLoader that will take care of
		// creating a Cursor for the data being displayed.

		String selection = null;

		return new CursorLoader(getActivity(), baseUri,
				columns, selection, null, null);
	}

	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		// Swap the new cursor in.  (The framework will take care of closing the
		// old cursor once we return.)

		Uri laUriBase = Uri.parse("content://"+Contrato.AUTHORITY+"/Ciclo");
		data.setNotificationUri(getActivity().getContentResolver(), laUriBase);
		
		mAdapter.swapCursor(data);
	}

	public void onLoaderReset(Loader<Cursor> loader) {
		// This is called when the last Cursor provided to onLoadFinished()
		// above is about to be closed.  We need to make sure we are no
		// longer using it.
		mAdapter.swapCursor(null);
	}

	public class CicloCursorAdapter extends CursorAdapter {
		public CicloCursorAdapter(Context context) {
			super(context, null, false);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			int ID = cursor.getInt(cursor.getColumnIndex(Contrato.Ciclo._ID));
			String nombre = cursor.getString(cursor.getColumnIndex(Contrato.Ciclo.NOMBRE));
			String abreviatura = cursor.getString(cursor.getColumnIndex(Contrato.Ciclo.ABREVIATURA));
	
			TextView textviewNombre = (TextView) view.findViewById(R.id.textview_ciclo_list_item_nombre);
			textviewNombre.setText(nombre);

			TextView textviewAbreviatura = (TextView) view.findViewById(R.id.textview_ciclo_list_item_abreviatura);
			textviewAbreviatura.setText(abreviatura);

			ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
			int color = generator.getColor(abreviatura); //Genera un color según el nombre
			TextDrawable drawable = TextDrawable.builder()
					.buildRound(abreviatura.substring(0,1), color);

			ImageView image = (ImageView) view.findViewById(R.id.image_view);
			image.setImageDrawable(drawable);

			view.setTag(ID);

		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			LayoutInflater inflater = LayoutInflater.from(context);
			View v = inflater.inflate(R.layout.ciclo_list_item, parent, false);
			bindView(v, context, cursor);
			return v;
		}
	}
}
