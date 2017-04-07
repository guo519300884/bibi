package gjw.bibi.view.fragment;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import gjw.bibi.R;
import gjw.bibi.view.base.BaseFragment01;


/**
 * Created by 皇上 on 2017/3/21.
 */

public class DynamicStateFragment extends BaseFragment01 {


    @InjectView(R.id.openbluetooth)
    Button openbluetooth;
    @InjectView(R.id.closebluetooth)
    Button closebluetooth;
    @InjectView(R.id.searchbluetooth)
    Button searchbluetooth;

    //    private static final int REQUEST_BLUETOOTH_PERMISSION = 10;
    @InjectView(R.id.lv_bluetooth)
    ListView lvBluetooth;
    private List<String> bluetoothDevicesList;
    private ArrayAdapter<String> adapter;

    private final UUID MY_UUID = UUID
            .fromString("abcd1234-ab12-ab12-ab12-abcdef123456");//类似端口号的作用
    //蓝牙连接双方必须匹配
    private BluetoothSocket clientSocket;
    private BluetoothDevice device;
    private OutputStream os;

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (bluetoothDevice.getBondState() != BluetoothDevice.BOND_BONDED) {
                    bluetoothDevicesList.add(bluetoothDevice.getName() + ":" + bluetoothDevice.getAddress());
                    adapter.notifyDataSetChanged();//更新适配器
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //已经搜索完成
                Toast.makeText(context, "搜索完成", Toast.LENGTH_SHORT).show();
            }
        }
    };
    private BluetoothAdapter bluetoothAdapter;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_dynamicstate;
    }

    @Override
    protected void initData(String json, String error) {

    }

    @Override
    protected void initView() {
        initBData();
    }

    private void initBData() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter != null && !bluetoothAdapter.isEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(intent);
        }

        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        bluetoothDevicesList = new ArrayList<>();

        if (pairedDevices != null && pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                bluetoothDevicesList.add(device.getName() + ":" + device.getAddress());
            }
        } else {
            Log.e("TAG", "DynamicStateFragment initBData()555555555555555555555555555");
        }
        adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, android.R.id.text1, bluetoothDevicesList);

        lvBluetooth.setAdapter(adapter);


        //每搜索到一个设备就会发送一个该广播
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        context.registerReceiver(broadcastReceiver, filter);
        //当全部搜索完后发送该广播
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        context.registerReceiver(broadcastReceiver, filter);

        //设置item监听 点击对应的设备 进行关联 关联成功后 发送文本
        lvBluetooth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = adapter.getItem(position);
                String address = s.substring(s.indexOf(":") + 1).trim();//把地址解析出来
                //主动连接蓝牙服务端
                try {
                    //判断当前是否正在搜索
                    if (bluetoothAdapter.isDiscovering()) {
                        bluetoothAdapter.cancelDiscovery();
                    }
                    try {
                        if (device == null) {
                            //获得远程设备
                            device = bluetoothAdapter.getRemoteDevice(address);
                        }
                        if (clientSocket == null) {
                            //创建客户端蓝牙Socket
                            clientSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
                            //开始连接蓝牙，如果没有配对则弹出对话框提示我们进行配对
                            clientSocket.connect();
                            //获得输出流（客户端指向服务端输出文本）
                            os = clientSocket.getOutputStream();
                        }
                    } catch (Exception e) {
                    }
                    if (os != null) {
                        //往服务端写信息
                        os.write("蓝牙信息来了".getBytes("utf-8"));
                        Toast.makeText(context, "发送成功", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                }
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public String setUrl() {
        return "";
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.openbluetooth, R.id.closebluetooth, R.id.searchbluetooth})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.openbluetooth:
                openBluetooth();
                break;
            case R.id.closebluetooth:
                closeBluetooth();
                break;
            case R.id.searchbluetooth:
                searchBluetooth();
                break;
        }
    }

    private void searchBluetooth() {
        //如果在搜索,先取消
        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        }
        //开始搜索
        bluetoothAdapter.startDiscovery();
    }

    private void closeBluetooth() {
        bluetoothAdapter.disable();
        Toast.makeText(context, "关闭蓝牙", Toast.LENGTH_SHORT).show();
    }

    private void openBluetooth() {
        bluetoothAdapter.enable();
        Toast.makeText(context, "打开蓝牙", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        if (broadcastReceiver != null) {
            context.unregisterReceiver(broadcastReceiver);
        }
        super.onDestroy();
    }

}
