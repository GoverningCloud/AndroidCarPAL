package bt.carplay.com.carplaymain;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import bt.carplay.com.carplaymain.util.IabHelper;
import bt.carplay.com.carplaymain.util.IabResult;
import bt.carplay.com.carplaymain.util.Inventory;
import bt.carplay.com.carplaymain.util.Purchase;


public class InAppBillingActivity extends AppCompatActivity {
    private static final String TAG =
            "bt.carplay.com.carplaymain";
    IabHelper mHelper;
    static final String ITEM_SKU = "android.test.purchased";
    // reference to buttons added
    private Button clickButton;
    private Button buyButton;
    private static final int PLACE_PICKER_REQUEST = 1;
    private TextView mName;
    private TextView mAddress;
    private TextView mAttributions;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_app_billing);


        buyButton = (Button)findViewById(R.id.buyButton);

        mName = (TextView) findViewById(R.id.textView);
        mAddress = (TextView) findViewById(R.id.textView2);
        mAttributions = (TextView) findViewById(R.id.textView3);
        Button pickerButton = (Button) findViewById(R.id.pickerButton);
        pickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PlacePicker.IntentBuilder intentBuilder =
                            new PlacePicker.IntentBuilder();
                    intentBuilder.setLatLngBounds(BOUNDS_MOUNTAIN_VIEW);
                    Intent intent = intentBuilder.build(InAppBillingActivity.this);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesRepairableException
                        | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        clickButton = (Button)findViewById(R.id.clickButton);
        clickButton.setEnabled(false);

        String base64EncodedPublicKey =
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAndu2PDHuC3xFCiwmQTe80S5curQ5noCxbAHIitaZRnolSJKCNaFnmsRmu40Zz2uEjqzElG9ROPg7b4kvbMNBQ0viPov1cFkyZSKIs978Ur3+x4ZiKbD50kKWYE78FuqDLz27GrhNoAB/xNsdwlkpd6kDU4HFCJIKIz6wM/IzAZHCGwE0xS2XVhzmUUTKq6vLbOox16j1mlWvQOIfKa9CZICZU1hRv9ExO+TNoHPOzKAO6PSaJv1zEz188YmG0ViMlbscbc36YOKPHQ+dujMA/RcsvCQLnAUNgYxajNBQWULU5nAnyY8S7ZWWI/Y4B7Bds1GU6wv8+Rhj0h0ya/UPLwIDAQAB";

        mHelper = new IabHelper(this, base64EncodedPublicKey);

        mHelper.startSetup(new
                                   IabHelper.OnIabSetupFinishedListener() {
                                       public void onIabSetupFinished(IabResult result) {
                                           if (!result.isSuccess()) {
                                               Log.d(TAG, "In-app Billing setup failed: " +
                                                       result);
                                           } else {
                                               Log.d(TAG, "In-app Billing is set up OK");
                                           }
                                       }
                                   });

                                      /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

                                       setSupportActionBar(toolbar);

                                       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); */
    }
    //implementing buttonClicked method
    public void buttonClicked (View view)
    {
        clickButton.setEnabled(false);
        buyButton.setEnabled(true);
    }
    //implementing buy click method
    public void buyClick(View view) {
        mHelper.launchPurchaseFlow(this, ITEM_SKU, 10001,
                mPurchaseFinishedListener, "mypurchasetoken");
    }
    // implementing onActivityResult method
//    protected void onActivityResult(int requestCode, int resultCode,
//                                    Intent data)
//    {
//        if (!mHelper.handleActivityResult(requestCode,
//                resultCode, data)) {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
//    }
    //implementing the purchase finished listener method
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener
            = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result,
                                          Purchase purchase)
        {
            if (result.isFailure()) {
                // Handle error
                return;
            }
            else if (purchase.getSku().equals(ITEM_SKU)) {
                consumeItem();
                buyButton.setEnabled(false);
            }

        }
    };
    //consuming the purchased item
    public void consumeItem() {
        mHelper.queryInventoryAsync(mReceivedInventoryListener);
    }

    IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener
            = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result,
                                             Inventory inventory) {

            if (result.isFailure()) {
                // Handle failure
            } else {
                mHelper.consumeAsync(inventory.getPurchase(ITEM_SKU),
                        mConsumeFinishedListener);
            }
        }
    };
    //implementing the listener such that the Click Me button is enabled after the item has been consumed in the billing system
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener =
            new IabHelper.OnConsumeFinishedListener() {
                public void onConsumeFinished(Purchase purchase,
                                              IabResult result) {

                    if (result.isSuccess()) {
                        clickButton.setEnabled(true);
                    } else {
                        // handle error
                    }
                }
            };
    //releasing the IabHelper Instance
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) mHelper.dispose();
        mHelper = null;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {

        if (!mHelper.handleActivityResult(requestCode,
                resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }

        if (requestCode == PLACE_PICKER_REQUEST
                && resultCode == Activity.RESULT_OK) {

            final Place place = PlacePicker.getPlace(this, data);
            final CharSequence name = place.getName();
            final CharSequence address = place.getAddress();
            String attributions = (String) place.getAttributions();
            if (attributions == null) {
                attributions = "";
            }

            mName.setText(name);
            mAddress.setText(address);
            mAttributions.setText(Html.fromHtml(attributions));

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}
