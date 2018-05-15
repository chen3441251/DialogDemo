package com.dialog.demo.dialogdemo;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean[] mBooleans;
    private EditText  mEt_name;
    private EditText  mEt_psd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_message = (Button) findViewById(R.id.btn_message);
        Button btn_items = (Button) findViewById(R.id.btn_items);
        Button btn_singleChoice = (Button) findViewById(R.id.btn_singleChoice);
        Button btn_multiChoice = (Button) findViewById(R.id.btn_multiChoice);
        Button btn_adapter = (Button) findViewById(R.id.btn_adapter);
        Button btn_view = (Button) findViewById(R.id.btn_view);
        Button btn_progress_show = (Button) findViewById(R.id.btn_progress_show);
        Button btn_progressdialog = (Button) findViewById(R.id.btn_progressdialog);
        btn_message.setOnClickListener(this);
        btn_items.setOnClickListener(this);
        btn_singleChoice.setOnClickListener(this);
        btn_multiChoice.setOnClickListener(this);
        btn_adapter.setOnClickListener(this);
        btn_view.setOnClickListener(this);
        btn_progress_show.setOnClickListener(this);
        btn_progressdialog.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_message:
                //文本对话框
                showDialogForMessage();
                break;
            case R.id.btn_items:
                //简单条目对话框
                showDialogItems();
                break;
            case R.id.btn_singleChoice:
                //单选对话框
                showDialogSingleChoice();
                break;
            case R.id.btn_multiChoice:
                //多选对话框
                showDialogMultiChoice();
                break;
            case R.id.btn_adapter:
                //adapter对话框
                showDialogAdapter();
                break;
            case R.id.btn_view:
                //view对话框
                showDialogView();
                break;
            case R.id.btn_progress_show:
                //progressDialog调用show
                showStaticProgressDialog();
                break;
            case R.id.btn_progressdialog:
                //progressDialog调用show
                showProgressDialog();
                break;
            default:
                break;
        }
    }

    private void showStaticProgressDialog() {
        ProgressDialog.show(this, "我是标题", "我是内容", false, true, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(getApplicationContext(),"ProgressDialog取消了",Toast.LENGTH_LONG).show();
            }
        });
    }
     int progress=0;
    private void showProgressDialog() {
        progress=0;
       //创建progressdialog对象
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("我是progress对象标题");
        dialog.setIcon(getResources().getDrawable(R.mipmap.ic_launcher));
        dialog.setMessage("我是progress对象的内容");
        dialog.setMax(100);
        dialog.setIndeterminate(false);//显示进度条进度
        dialog.setCanceledOnTouchOutside(true);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.show();
        //模拟进度数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progress<100){
                    progress+=5;
                    SystemClock.sleep(500);
                    dialog.setProgress(progress);
                }
                if(progress>=100){
                    progress=100;
                    dialog.dismiss();
                }
            }
        }).start();
    }


    private void showDialogForMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("简单Message对话框")
                .setIcon(getResources().getDrawable(R.mipmap.ic_launcher))
                .setMessage("我是对话框的内容");
        setCustomPositiveButton(builder);
        setCustomNegativeButton(builder);
        setCustomNeutralButton(builder)
                .create().show();


    }

    private void showDialogItems() {
        final String[] items = new String[]{"android", "ios", "java", "php", "Python", "javaScript", "html5"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("简单items对话框")
                .setIcon(getResources().getDrawable(R.mipmap.ic_launcher))
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), items[which] + "被点击了", Toast.LENGTH_LONG).show();
                    }
                });
        setCustomPositiveButton(builder);
        setCustomNegativeButton(builder);
        setCustomNeutralButton(builder)
                .create().show();

    }

    private void showDialogSingleChoice() {
        final String[] items = new String[]{"小学", "高中", "大学", "硕士", "博士"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("SingleChoice对话框")
                .setIcon(getResources().getDrawable(R.mipmap.ic_launcher))
                .setSingleChoiceItems(items, 1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "单选的条目是：" + items[which], Toast.LENGTH_LONG).show();
                    }
                });
        setCustomPositiveButton(builder);
        setCustomNegativeButton(builder);
        setCustomNeutralButton(builder)
                .create().show();
    }

    private void showDialogMultiChoice() {
        final String[] items = new String[]{"苹果", "香蕉", "樱桃", "芒果", "柠檬", "菠萝", "西瓜"};
        mBooleans = new boolean[]{true, true, false, false, false, false, true};
        ArrayList<String> objects = new ArrayList<>();
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("MultiChoice对话框")
                .setIcon(getResources().getDrawable(R.mipmap.ic_launcher))
                .setMultiChoiceItems(items, mBooleans, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        Toast.makeText(getApplicationContext(), (isChecked ? "选中了：" : "取消了：") + items[which], Toast.LENGTH_LONG).show();
                    }
                });
        setCustomPositiveButton(builder);
        setCustomNegativeButton(builder);
        setCustomNeutralButton(builder);
        builder.create().show();
    }

    private void showDialogAdapter() {
        final String[] items = new String[]{"上海", "北京", "深圳", "杭州", "南京", "武汉", "郑州", "哈尔滨", "拉萨", "岳阳", "南昌", "贵阳", "洛阳"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Adapter对话框")
                .setIcon(getResources().getDrawable(R.mipmap.ic_launcher))
                .setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "adapter选中：" + items[which], Toast.LENGTH_LONG).show();
                    }
                });
        setCustomPositiveButton(builder);
        setCustomNegativeButton(builder);
        setCustomNeutralButton(builder);
        builder.create().show();
    }

    private void showDialogView() {
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_login, (ViewGroup) findViewById(R.id.ll));
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("View弹窗")
                .setIcon(getResources().getDrawable(R.mipmap.ic_launcher))
                //                .setView(R.layout.layout_login);
                .setView(view);

        setCustomPositiveButton(builder);
        setCustomNegativeButton(builder);
        setCustomNeutralButton(builder);
        builder.create().show();
        mEt_name = view.findViewById(R.id.et_name);
        mEt_psd = view.findViewById(R.id.et_psd);
    }

    private AlertDialog.Builder setCustomPositiveButton(AlertDialog.Builder builder) {
        return builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "确认按钮被点击", Toast.LENGTH_LONG).show();

                Log.d("xxx", "et_name=" + mEt_name.getText().toString() + ",,et_psd=" + mEt_name.getText().toString());
            }
        });
    }

    private AlertDialog.Builder setCustomNegativeButton(AlertDialog.Builder builder) {

        return builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "取消按钮被点击", Toast.LENGTH_LONG).show();
            }
        });
    }

    private AlertDialog.Builder setCustomNeutralButton(AlertDialog.Builder builder) {
        return builder.setNeutralButton("扩展按钮", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "扩展按钮被点击", Toast.LENGTH_LONG).show();
            }
        });
    }
}
