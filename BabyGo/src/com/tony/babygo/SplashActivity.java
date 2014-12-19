package com.tony.babygo;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.tony.babygo.data.LocationData;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

public class SplashActivity extends BaseActivity implements
		AMapLocationListener {
	public static final String TAG = "SplashActivity";
	
	private LocationManagerProxy mLocationManagerProxy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		MobclickAgent.updateOnlineConfig( SplashActivity.this );
		MobclickAgent.setDebugMode( false );
		UmengUpdateAgent.update(this);
		init();  
		initView();
	}
	
	/**
	 * ��ʼ����λ  
	 */
	private void init() {
		// ��ʼ����λ��ֻ�������綨λ
		mLocationManagerProxy = LocationManagerProxy.getInstance(this);
		mLocationManagerProxy.setGpsEnable(false);
		// �˷���Ϊÿ���̶�ʱ��ᷢ��һ�ζ�λ����Ϊ�˼��ٵ������Ļ������������ģ�
		// ע�����ú��ʵĶ�λʱ��ļ������С���֧��Ϊ2000ms���������ں���ʱ�����removeUpdates()������ȡ����λ����
		// �ڶ�λ�������ں��ʵ��������ڵ���destroy()����
		// ����������ʱ��Ϊ-1����λֻ��һ��,
		//�ڵ��ζ�λ����£���λ���۳ɹ���񣬶��������removeUpdates()�����Ƴ����󣬶�λsdk�ڲ����Ƴ�
		mLocationManagerProxy.requestLocationData(
				LocationProviderProxy.AMapNetwork, 60*1000, 15, this);
		

	}
	
	private void initView() {
	}
	
	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLocationChanged(AMapLocation amapLocation) {

		if (amapLocation!=null&&amapLocation.getAMapException().getErrorCode() == 0) {
			// ��λ�ɹ��ص���Ϣ�����������Ϣ
			LocationData.city = amapLocation.getCity();
			LocationData.city_cur = amapLocation.getCity();
			LocationData.latitude = String.valueOf(amapLocation.getLatitude());
			LocationData.longitude = String.valueOf(amapLocation.getLongitude());
			LocationData.latitude_cur = String.valueOf(amapLocation.getLatitude());
			LocationData.longitude_cur = String.valueOf(amapLocation.getLongitude());
			LocationData.region = amapLocation.getDistrict();
			Intent goMainIntent = new Intent(SplashActivity.this, MainActivity.class);
			startActivity(goMainIntent);
			finish();
		} else {
			Intent goMainIntent = new Intent(SplashActivity.this, MainActivity.class);
			startActivity(goMainIntent);
			finish();
		}

	}
	
	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("SplashActivity");
		MobclickAgent.onPause(this);
		//�Ƴ���λ����
		mLocationManagerProxy.removeUpdates(this);
		// ���ٶ�λ
		mLocationManagerProxy.destroy();
	}

	protected void onDestroy() {
		super.onDestroy();
		
	}
	
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("SplashActivity");
		MobclickAgent.onResume(this);
	}

}
