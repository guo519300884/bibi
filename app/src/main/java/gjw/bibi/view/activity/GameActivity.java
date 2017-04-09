package gjw.bibi.view.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.io.InputStream;
import java.util.UUID;

import gjw.bibi.R;
import gjw.bibi.view.base.BaseActivity;

public class GameActivity extends BaseActivity {

    private BluetoothAdapter mBluetoothAdapter;
    private AcceptThread acceptThread;
    private final UUID MY_UUID = UUID
            .fromString("abcd1234-ab12-ab12-ab12-abcdef123456");//和客户端相同的UUID
    private final String NAME = "Bluetooth_Socket";
    private BluetoothServerSocket serverSocket;
    private BluetoothSocket socket;
    private InputStream is;//输入流
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            //把接受的字符串Toast出来
            Toast.makeText(GameActivity.this, String.valueOf(msg.obj),
                    Toast.LENGTH_LONG).show();
            handler.obtainMessage().sendToTarget();
        }
    };

    @Override
    protected void initData() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        acceptThread = new AcceptThread();
        acceptThread.start();
    }

    @Override
    protected void initListener() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_game;
    }

    //服务端监听客户端的线程类
    private class AcceptThread extends Thread {
        public AcceptThread() {
            try {
                //得到蓝牙Socket
                serverSocket = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
            } catch (Exception e) {
            }
        }
        public void run() {
            try {
                //等待接受消息
                socket = serverSocket.accept();
                //得到流 开始读取消息
                is = socket.getInputStream();
                while(true) {
                    byte[] buffer =new byte[1024];
                    int count = is.read(buffer);
                    Message msg = new Message();
                    msg.obj = new String(buffer, 0, count, "utf-8");
                    //读取出消息 切换到主线程进行操作
                    handler.sendMessage(msg);
                }
            }
            catch (Exception e) {
            }
        }
    }
}
