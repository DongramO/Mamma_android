package momma_beta.momma_bv.Search;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import momma_beta.momma_bv.R;
import momma_beta.momma_bv.navigation_main.NavigationActivity;


public class SearchActivity extends Activity {

	private NavigationActivity mContext;
	private ImageButton search_back_btn, search_btn;
	private EditText search_edit;
	private FrameLayout search_fragment_container;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_main);


		search_back_btn = (ImageButton)findViewById(R.id.search_back_btn);
		search_btn = (ImageButton)findViewById(R.id.search_btn);

		search_edit = (EditText)findViewById(R.id.search_text);

		search_fragment_container = (FrameLayout)findViewById(R.id.search_fragment_container);


		if (savedInstanceState == null) {
			Fragment landingFragment = new SearchFragment();
			ReplaceFragement(landingFragment);
		}

		search_back_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

		search_btn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view) {
				Fragment search_result_fragment = new Search_Result_Fragment();
				ReplaceFragement(search_result_fragment);
				Bundle bundle = new Bundle(1); // 파라미터는 전달할 데이터 개수
				bundle.putString("milkname", search_edit.getText().toString()); // key , value
				search_result_fragment.setArguments(bundle);
				search_edit.setText("");
			}
		});
	}
	public void ReplaceFragement(Fragment fragment)
	{
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.search_fragment_container, fragment);
		transaction.commit();
	}


}
