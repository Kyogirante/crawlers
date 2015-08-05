package com.example.wang;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;


public class MainActivity extends ActionBarActivity {

    private MapView mMapView;
    private BaiduMap mBaiduMap;

    //定位相关
    private LocationClient mLocationClient;
    private BDLocationListener mLocationListener;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private BitmapDescriptor mCurrentMarker;//自定义位置图标，暂时未使用
    private LocationClientOption option;

    private boolean isFirstLocation = true;

    private SensorManager mSensorManager;
    private Sensor mMagneticSensor;//磁场感应器
    private Sensor mAccelerometerSensor;//重力感应器
    private SensorEventListener mSensorEventListner;

    private float[] accelerometerValues = new float[3];
    private float[] magneticValues = new float[3];

    private float orientation = 0;
    private float mCurrentAccuracy;
    private double mCurrentLatitude;
    private double mCurrentLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mSensorEventListner = new MySensorEventListener();
        //官方推荐用两个传感器判断偏向
        mMagneticSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mMapView = (MapView) findViewById(R.id.map_view);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);

        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        mCurrentMarker = BitmapDescriptorFactory.fromResource(R.mipmap.icon_geo);
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode,true,null));

        //初始化监听器
        mLocationListener = new MyLocationListener();
        //设置定位客户端参数
        option = new LocationClientOption();
        option.setOpenGps(true);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);

        //配置定位客户端
        mLocationClient = new LocationClient(this);
        mLocationClient.registerLocationListener(mLocationListener);
        mLocationClient.setLocOption(option);
        mLocationClient.start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = null;
        switch (id) {
            case R.id.go_web_view:
                intent = new Intent(this,WebViewActivity.class);
                break;
            case R.id.go_drag_demo_view:
                intent = new Intent(this,DragDemoActivity.class);
                break;
            case R.id.go_coordinator_demo_view:
                intent = new Intent(this,CoordinatorDemoActivity.class);
                break;
            default:
                break;
        }

        if( intent != null ){
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume(){
        mMapView.onResume();
        mSensorManager.registerListener(new MySensorEventListener(), mMagneticSensor, Sensor.TYPE_MAGNETIC_FIELD);
        mSensorManager.registerListener(new MySensorEventListener(), mAccelerometerSensor, Sensor.TYPE_ACCELEROMETER);
        super.onResume();
    }

    @Override
    protected void onPause(){
        //改变地图和传感器状态，一定要做
        mMapView.onPause();
        mSensorManager.unregisterListener(new MySensorEventListener());
        super.onPause();
    }

    @Override
    protected void onDestroy(){
        //注销
        if(mLocationClient != null){
            mLocationClient.stop();
        }
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView=null;
        super.onDestroy();
    }

    //官方推荐，计算偏向
    private void calculateOrientation(){
        float[] values = new float[3];
        float[] R = new float[9];
        SensorManager.getRotationMatrix(R, null, accelerometerValues, magneticValues);
        SensorManager.getOrientation(R, values);
        orientation = (float)Math.toDegrees(values[0]);
        setOrientation();
    }

    private void setOrientation(){
        MyLocationData locationData = new MyLocationData.Builder()
                .accuracy(mCurrentAccuracy)
                .direction(orientation)
                .latitude(mCurrentLatitude)
                .longitude(mCurrentLongitude)
                .build();
        mBaiduMap.setMyLocationData(locationData);
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if(bdLocation == null || mMapView == null){
                return;
            }
            mCurrentAccuracy = bdLocation.getRadius();//传感器改变方向会使用到该参数
            mCurrentLatitude = bdLocation.getLatitude();
            mCurrentLongitude = bdLocation.getLongitude();

            MyLocationData locationData = new MyLocationData.Builder()
                    .accuracy(mCurrentAccuracy)
                    .direction(orientation)
                    .latitude(mCurrentLatitude)
                    .longitude(mCurrentLongitude).build();
            mBaiduMap.setMyLocationData(locationData);



           if(isFirstLocation){
                isFirstLocation = false;
                LatLng ll = new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
                MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(mapStatusUpdate);
            }
        }
    }

    //建议将该类单独建立文件夹，calculateOrientation设置成回调比较好
    public class MySensorEventListener implements SensorEventListener{

        /**
         * 传感器方向改变调用
         */
        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                accelerometerValues = event.values;
            }
            if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
                magneticValues = event.values;
            }
            //当传感器方向变化，计算方向，然后改变图标
            calculateOrientation();
        }

        /**
         * 传感器精确度改变调用
         */
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }
}
