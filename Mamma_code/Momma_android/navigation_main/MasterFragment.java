package momma_beta.momma_bv.navigation_main;

import android.app.Dialog;
import android.app.Fragment;


public class MasterFragment extends Fragment {

	// returns the master activity
	public MasterActivity getMasterActivity() {
		MasterActivity masterActivity = (MasterActivity) getActivity();
		return masterActivity;
	}

	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
