package ly.generalassemb.drewmahrt.shoppinglistwithsearch;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import ly.generalassemb.drewmahrt.shoppinglistwithsearch.setup.DBAssetHelper;

public class MainActivity extends AppCompatActivity {
    private ListView mShoppingListView;
    private SimpleCursorAdapter mCursorAdapter;
    private ShoppingSQLiteOpenHelper mHelper;
    private Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ignore the two lines below, they are for setup
        DBAssetHelper dbSetup = new DBAssetHelper(MainActivity.this);
        dbSetup.getReadableDatabase();

        mShoppingListView = (ListView) findViewById(R.id.shopping_list_view);
        mHelper = new ShoppingSQLiteOpenHelper(MainActivity.this);

        mCursor = mHelper.getShoppingList();

        mCursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,
                mCursor, new String[]{ShoppingSQLiteOpenHelper.COL_ITEM_NAME},
                new int[]{android.R.id.text1}, 0);
        mShoppingListView.setAdapter(mCursorAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu,menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        ComponentName componentName = new ComponentName(this,SearchResultActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));
        return true;
    }

}
