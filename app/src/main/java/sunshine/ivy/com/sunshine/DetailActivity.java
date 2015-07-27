package sunshine.ivy.com.sunshine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.ShareActionProvider;
import android.widget.TextView;


public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailsActivityFragment())
                    .commit();
        }

        String forecast = getIntent().getStringExtra(Intent.EXTRA_TEXT);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i=new Intent(this,SettingsActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class DetailsActivityFragment extends Fragment {

        private String LOG_TAG=DetailsActivityFragment.class.getSimpleName();
        private String FORECCAST_SHARE_HASH_TAG="#SunshineApp";
        private String mForecastStr;

        public DetailsActivityFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.menu_detail,menu);

            MenuItem share=menu.findItem(R.id.action_share);

            ShareActionProvider shareActionProvider=(ShareActionProvider) MenuItemCompat.getActionProvider(share);

            if(shareActionProvider!=null)
                shareActionProvider.setShareIntent(createShareForecastIntent());
            else
                Log.d(LOG_TAG,"Share Intent not available");

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            Intent intent = getActivity().getIntent();
            if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
                mForecastStr=intent.getStringExtra(Intent.EXTRA_TEXT);
                TextView detail_text = (TextView) rootView.findViewById(R.id.detail_text);
                detail_text.setText(mForecastStr);
            }
            return rootView;
        }

        private Intent createShareForecastIntent(){
            Intent shareIntent =new Intent(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT,mForecastStr+FORECCAST_SHARE_HASH_TAG);
            return shareIntent;
        }
    }
}
