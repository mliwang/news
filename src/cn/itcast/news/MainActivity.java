package cn.itcast.news;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import com.loopj.android.image.SmartImageView;

import android.R.integer;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends ActionBarActivity {
	private List<News> newslists;
	private ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lv = (ListView) findViewById(R.id.lv);
		// 准备数据
		initListdata();

	}

	private void initListdata() {
		new Thread() {

			public void run() {
				try {
					URL url = new URL("http://192.168.42.51:8080/news.xml");
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(5000);
					int code = conn.getResponseCode();
					if (code == 200) {
						InputStream in = conn.getInputStream();
						// xml文件解析得到一个数据集合
						newslists = xmlprase.Parser(in);

						runOnUiThread(new Runnable() {
							public void run() {
								lv.setAdapter(new myadapter());
							}
						});

					} else {
						runOnUiThread(new Runnable() {
							public void run() {

								Toast.makeText(getApplicationContext(),
										"无法访问网络", 1).show();
							}
						});
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			};
		}.start();

	}

	private class myadapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return newslists.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v;
			if (convertView == null) {
				v = View.inflate(getApplicationContext(), R.layout.item, null);
			} else {
				v = convertView;
			}
			TextView tv_title = (TextView) v.findViewById(R.id.tv_title);
			TextView tv_description = (TextView) v
					.findViewById(R.id.tv_description);
			TextView tv_type = (TextView) v.findViewById(R.id.tv_type);
			SmartImageView iv_icon = (SmartImageView) v.findViewById(R.id.iv_icon);
			tv_title.setText(newslists.get(position).getTitle());
			tv_description.setText(newslists.get(position).getDescription());
			String stringtype = newslists.get(position).getType();
			int type = Integer.parseInt(stringtype);
			switch (type) {
			case 1:

				tv_type.setText("跟帖" + newslists.get(position).getComment());

				break;

			case 2:
				tv_type.setText("国外");

				break;
			case 3:
				tv_type.setText("国内");

				break;
			}
			String imageurl = newslists.get(position).getImage();
			iv_icon.setImageUrl(imageurl);

			return v;
		}

	}

}
