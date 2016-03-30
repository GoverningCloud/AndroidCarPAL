package bt.carplay.com.carplaymain;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MainActivity extends Activity {

    private static final int REQUEST_ENABLE_BT = 1;
    private Button onBtn;
    private Button offBtn;
    private Button listBtn;
    private Button findBtn;
    private TextView text;
    private BluetoothAdapter myBluetoothAdapter;
    private Set<BluetoothDevice> pairedDevices;
    private ListView myListView;
    private ArrayAdapter<String> BTArrayAdapter;
    private ImageButton buttonMusicPlay;
    private ImageButton buttonMapsOption;

    ListView listViewPaired;
    ListView listViewDetected;
    ArrayList<String> arrayListpaired;
    Button buttonSearch,buttonOn,buttonDesc,buttonOff;
    ArrayAdapter<String> adapter,detectedAdapter;
    static HandleSeacrh handleSeacrh;
    BluetoothDevice bdDevice;
    BluetoothClass bdClass;
    ArrayList<BluetoothDevice> arrayListPairedBluetoothDevices;
    private ButtonClicked clicked;
    ListItemClickedonPaired listItemClickedonPaired;
    BluetoothAdapter bluetoothAdapter = null;
    ArrayList<BluetoothDevice> arrayListBluetoothDevices = null;
    ListItemClicked listItemClicked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //======BLuetooth code====

        listViewDetected = (ListView) findViewById(R.id.listViewDetected);
        listViewPaired = (ListView) findViewById(R.id.listViewPaired);
        buttonSearch = (Button) findViewById(R.id.buttonSearch);
        buttonOn = (Button) findViewById(R.id.buttonOn);
        buttonDesc = (Button) findViewById(R.id.buttonDesc);
        buttonOff = (Button) findViewById(R.id.buttonOff);
        arrayListpaired = new ArrayList<String>();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        clicked = new ButtonClicked();
        handleSeacrh = new HandleSeacrh();
        arrayListPairedBluetoothDevices = new ArrayList<BluetoothDevice>();
        /*
         * the above declaration is just for getting the paired bluetooth devices;
         * this helps in the removing the bond between paired devices.
         */
        listItemClickedonPaired = new ListItemClickedonPaired();
        arrayListBluetoothDevices = new ArrayList<BluetoothDevice>();
        adapter= new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, arrayListpaired);
        detectedAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_single_choice);
        listViewDetected.setAdapter(detectedAdapter);
        listItemClicked = new ListItemClicked();
        detectedAdapter.notifyDataSetChanged();
        listViewPaired.setAdapter(adapter);


        //=====bluetooth code ends...



        // take an instance of BluetoothAdapter - Bluetooth radio
    //    myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        final MediaPlayer mp = MediaPlayer.create(this,R.raw.music);
        mp.setLooping(true);
          //create(MainActivity.this, R.raw.music);

//        if(myBluetoothAdapter == null) {
//            onBtn.setEnabled(false);
//            offBtn.setEnabled(false);
//            listBtn.setEnabled(false);
//            findBtn.setEnabled(false);
//            text.setText("Status: not supported");
//
//            Toast.makeText(getApplicationContext(),"Your device does not support Bluetooth",
//                    Toast.LENGTH_LONG).show();
//        } else {
//            text = (TextView) findViewById(R.id.text);
//            onBtn = (Button)findViewById(R.id.turnOn);
////            onBtn.setOnClickListener(new OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    // TODO Auto-generated method stub
//                   // on(v);
//                }
//            });



//            listBtn = (Button)findViewById(R.id.paired);
//            listBtn.setOnClickListener(new OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    // TODO Auto-generated method stub
//                    list(v);
//                }
//            });

//            findBtn = (Button)findViewById(R.id.search);
//            findBtn.setOnClickListener(new OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    // TODO Auto-generated method stub
//                    find(v);
//                }
//            });
//
//            myListView = (ListView)findViewById(R.id.listView1);
//
//            // create the arrayAdapter that contains the BTDevices, and set it to the ListView
//            BTArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
//            myListView.setAdapter(BTArrayAdapter);
//
//            myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                }
//            });

       // }

        buttonMapsOption=(ImageButton)findViewById(R.id.imageButton2);
        buttonMapsOption.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//
//                final CharSequence[] items = {
//                        "Option1", "Option2"
//                };
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                builder.setTitle("Make your selection");
//                builder.setItems(items, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int item) {
//                        // Do something with the selection
//
//                    }
//                });
//                AlertDialog alert = builder.create();
//                alert.show();

                Intent intent = new Intent(MainActivity.this, InAppBillingActivity.class);
                startActivity(intent);

            }
        });


       buttonMusicPlay=(ImageButton)findViewById(R.id.imageButton);
        buttonMusicPlay.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View v) {


                 try {

                     if (!mp.isPlaying()){
                         mp.start();
                     }
                     else {
                         mp.pause();
                     }

                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             }
         });

    }

//    public void on(View view){
//        if (!myBluetoothAdapter.isEnabled()) {
//            Intent turnOnIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(turnOnIntent, REQUEST_ENABLE_BT);
//
//            Toast.makeText(getApplicationContext(),"Bluetooth turned on" ,
//                    Toast.LENGTH_LONG).show();
//        }
//        else{
//            Toast.makeText(getApplicationContext(),"Bluetooth is already on",
//                    Toast.LENGTH_LONG).show();
//        }
//    }

//    private void pairDevice(BluetoothDevice device) {
//        try {
//            Log.d("pairDevice()", "Start Pairing...");
//            Method m = device.getClass().getMethod("createBond", (Class[]) null);
//            m.invoke(device, (Object[]) null);
//            Log.d("pairDevice()", "Pairing finished.");
//        } catch (Exception e) {
//            Log.e("pairDevice()", e.getMessage());
//        }
//    }



//    public void list(View view){
//        // get paired devices
//        pairedDevices = myBluetoothAdapter.getBondedDevices();
//
//        // put it's one to the adapter
//        for(BluetoothDevice device : pairedDevices)
//            BTArrayAdapter.add(device.getName()+ "\n" + device.getAddress());
//
//        Toast.makeText(getApplicationContext(),"Show Paired Devices",
//                Toast.LENGTH_SHORT).show();
//
//    }
////
//    final BroadcastReceiver bReceiver = new BroadcastReceiver() {
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            // When discovery finds a device
//            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
//                // Get the BluetoothDevice object from the Intent
//                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                // add the name and the MAC address of the object to the arrayAdapter
//                BTArrayAdapter.add(device.getName() + "\n" + device.getAddress());
//                BTArrayAdapter.notifyDataSetChanged();
//            }
//        }
//    };

//    public void find(View view) {
//        if (myBluetoothAdapter.isDiscovering()) {
//            // the button is pressed when it discovers, so cancel the discovery
//            myBluetoothAdapter.cancelDiscovery();
//        }
//        else {
//            BTArrayAdapter.clear();
//            myBluetoothAdapter.startDiscovery();
//
//            registerReceiver(bReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
//        }
//    }

//    public void off(View view){
//        myBluetoothAdapter.disable();
//        text.setText("Status: Disconnected");
//
//        Toast.makeText(getApplicationContext(),"Bluetooth turned off",
//                Toast.LENGTH_LONG).show();
//    }


    //======bt methods===
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        getPairedDevices();
        buttonOn.setOnClickListener(clicked);
        buttonSearch.setOnClickListener(clicked);
        buttonDesc.setOnClickListener(clicked);
        buttonOff.setOnClickListener(clicked);
        listViewDetected.setOnItemClickListener(listItemClicked);
        listViewPaired.setOnItemClickListener(listItemClickedonPaired);
    }
    private void getPairedDevices() {
        Set<BluetoothDevice> pairedDevice = bluetoothAdapter.getBondedDevices();
        if(pairedDevice.size()>0)
        {
            for(BluetoothDevice device : pairedDevice)
            {
                arrayListpaired.add(device.getName()+"\n"+device.getAddress());
                arrayListPairedBluetoothDevices.add(device);
            }
        }
        adapter.notifyDataSetChanged();
    }
    class ListItemClicked implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // TODO Auto-generated method stub
            bdDevice = arrayListBluetoothDevices.get(position);
            //bdClass = arrayListBluetoothDevices.get(position);
            Log.i("Log", "The dvice : "+bdDevice.toString());
            /*
             * here below we can do pairing without calling the callthread(), we can directly call the
             * connect(). but for the safer side we must usethe threading object.
             */
            //callThread();
            //connect(bdDevice);
            Boolean isBonded = false;
            try {
                isBonded = createBond(bdDevice);
                if(isBonded)
                {
                    //arrayListpaired.add(bdDevice.getName()+"\n"+bdDevice.getAddress());
                    //adapter.notifyDataSetChanged();
                    getPairedDevices();
                    adapter.notifyDataSetChanged();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }//connect(bdDevice);
            Log.i("Log", "The bond is created: "+isBonded);
        }
    }
    class ListItemClickedonPaired implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
            bdDevice = arrayListPairedBluetoothDevices.get(position);
            try {
                Boolean removeBonding = removeBond(bdDevice);
                if(removeBonding)
                {
                    arrayListpaired.remove(position);
                    adapter.notifyDataSetChanged();
                }


                Log.i("Log", "Removed"+removeBonding);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    /*private void callThread() {
        new Thread(){
            public void run() {
                Boolean isBonded = false;
                try {
                    isBonded = createBond(bdDevice);
                    if(isBonded)
                    {
                        arrayListpaired.add(bdDevice.getName()+"\n"+bdDevice.getAddress());
                        adapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }//connect(bdDevice);
                Log.i("Log", "The bond is created: "+isBonded);
            }
        }.start();
    }*/
    private Boolean connect(BluetoothDevice bdDevice) {
        Boolean bool = false;
        try {
            Log.i("Log", "service method is called ");
            Class cl = Class.forName("android.bluetooth.BluetoothDevice");
            Class[] par = {};
            Method method = cl.getMethod("createBond", par);
            Object[] args = {};
            bool = (Boolean) method.invoke(bdDevice);//, args);// this invoke creates the detected devices paired.
            //Log.i("Log", "This is: "+bool.booleanValue());
            //Log.i("Log", "devicesss: "+bdDevice.getName());
        } catch (Exception e) {
            Log.i("Log", "Inside catch of serviceFromDevice Method");
            e.printStackTrace();
        }
        return bool.booleanValue();
    };


    public boolean removeBond(BluetoothDevice btDevice)
            throws Exception
    {
        Class btClass = Class.forName("android.bluetooth.BluetoothDevice");
        Method removeBondMethod = btClass.getMethod("removeBond");
        Boolean returnValue = (Boolean) removeBondMethod.invoke(btDevice);
        return returnValue.booleanValue();
    }


    public boolean createBond(BluetoothDevice btDevice)
            throws Exception
    {
        Class class1 = Class.forName("android.bluetooth.BluetoothDevice");
        Method createBondMethod = class1.getMethod("createBond");
        Boolean returnValue = (Boolean) createBondMethod.invoke(btDevice);
        return returnValue.booleanValue();
    }


    class ButtonClicked implements View.OnClickListener
    {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.buttonOn:
                    onBluetooth();
                    break;
                case R.id.buttonSearch:
                    arrayListBluetoothDevices.clear();
                    startSearching();
                    break;
                case R.id.buttonDesc:
                    makeDiscoverable();
                    break;
                case R.id.buttonOff:
                    offBluetooth();
                    break;
                default:
                    break;
            }
        }
    }
    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Message msg = Message.obtain();
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                Toast.makeText(context, "ACTION_FOUND", Toast.LENGTH_SHORT).show();

                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                try
                {
                    //device.getClass().getMethod("setPairingConfirmation", boolean.class).invoke(device, true);
                    //device.getClass().getMethod("cancelPairingUserInput", boolean.class).invoke(device);
                }
                catch (Exception e) {
                    Log.i("Log", "Inside the exception: ");
                    e.printStackTrace();
                }

                if(arrayListBluetoothDevices.size()<1) // this checks if the size of bluetooth device is 0,then add the
                {                                           // device to the arraylist.
                    detectedAdapter.add(device.getName()+"\n"+device.getAddress());
                    arrayListBluetoothDevices.add(device);
                    detectedAdapter.notifyDataSetChanged();
                }
                else
                {
                    boolean flag = true;    // flag to indicate that particular device is already in the arlist or not
                    for(int i = 0; i<arrayListBluetoothDevices.size();i++)
                    {
                        if(device.getAddress().equals(arrayListBluetoothDevices.get(i).getAddress()))
                        {
                            flag = false;
                        }
                    }
                    if(flag == true)
                    {
                        detectedAdapter.add(device.getName()+"\n"+device.getAddress());
                        arrayListBluetoothDevices.add(device);
                        detectedAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
    };
    private void startSearching() {
        Log.i("Log", "in the start searching method");
        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(myReceiver, intentFilter);
        bluetoothAdapter.startDiscovery();
    }
    private void onBluetooth() {
        if(!bluetoothAdapter.isEnabled())
        {
            bluetoothAdapter.enable();
            Log.i("Log", "Bluetooth is Enabled");
        }
    }
    private void offBluetooth() {
        if(bluetoothAdapter.isEnabled())
        {
            bluetoothAdapter.disable();
        }
    }
    private void makeDiscoverable() {
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);
        Log.i("Log", "Discoverable ");
    }
    class HandleSeacrh extends Handler
    {
        @Override
        public void publish(LogRecord record) {

        }

        @Override
        public void close() {

        }

        @Override
        public void flush() {

        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 111:

                    break;

                default:
                    break;
            }
        }
    }
}

    //===paste ends====

//    @Override
//    protected void onDestroy() {
//        // TODO Auto-generated method stub
//        super.onDestroy();
//       // unregisterReceiver(bReceiver);
//    }

//}