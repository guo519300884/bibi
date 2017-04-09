package gjw.bibi.view.activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.search.share.OnGetShareUrlResultListener;
import com.baidu.mapapi.search.share.PoiDetailShareURLOption;
import com.baidu.mapapi.search.share.ShareUrlResult;
import com.baidu.mapapi.search.share.ShareUrlSearch;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import gjw.bibi.R;
import gjw.bibi.utils.map.overlayutil.BikingRouteOverlay;
import gjw.bibi.utils.map.overlayutil.DrivingRouteOverlay;
import gjw.bibi.utils.map.overlayutil.PoiOverlay;
import gjw.bibi.utils.map.overlayutil.WalkingRouteOverlay;
import gjw.bibi.view.base.BaseActivity01;

public class AttentionActivity extends BaseActivity01 {

    @InjectView(R.id.ib_back)
    ImageButton ibBack;
    @InjectView(R.id.btn_map1)
    Button btnMap1;
    @InjectView(R.id.btn_map2)
    Button btnMap2;
    @InjectView(R.id.btn_map3)
    Button btnMap3;
    @InjectView(R.id.btn_map4)
    Button btnMap4;
    @InjectView(R.id.btn_map5)
    Button btnMap5;
    @InjectView(R.id.btn_map6)
    Button btnMap6;
    @InjectView(R.id.btn_map7)
    Button btnMap7;
    @InjectView(R.id.btn_map8)
    Button btnMap8;
    @InjectView(R.id.btn_map9)
    Button btnMap9;
    @InjectView(R.id.mapView)
    MapView mapView;
    private BaiduMap map;
    private OverlayOptions options;
    private PoiSearch poiSearch;
    private ShareUrlSearch shareUrlSearch;
    private RoutePlanSearch routePlanSearch;
    private GeoCoder geoCoder;


    @Override
    public String setUrl() {
        return "";
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_attention;
    }

    @Override
    protected void initView() {
        //初始化地图
        map = mapView.getMap();
        //取得检索的实例
        poiSearch = PoiSearch.newInstance();
        //短串分享
        shareUrlSearch = ShareUrlSearch.newInstance();
        //路线实例
        routePlanSearch = RoutePlanSearch.newInstance();
        //地理编码
        geoCoder = GeoCoder.newInstance();

        initMapData();

    }

    private void initMapData() {

    }

    @Override
    protected void initListener() {
        //地图点击事件
        iniMapListener();
        //覆盖物的点击事件
        initMarkerListener();
        //搜索监听
        initPoiSearchListener();
        //短串分享的监听
        initShareUrlSearchListener();
        //路线选择监听
        initRoutePlanSearchListener();
        //地理编码的监听
        initGeoCoderListener();
    }

    private void initGeoCoderListener() {
        geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

                Log.e("TAG", "onGetGeoCodeResult: " + geoCodeResult.getLocation().toString());
            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                Log.e("TAG", "onGetGeoCodeResult: " + reverseGeoCodeResult.getAddress());
            }
        });
    }

    private void initRoutePlanSearchListener() {
        routePlanSearch.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {
            //步行
            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
                if (walkingRouteResult == null ||
                        walkingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    Log.e("TAG", "onGetWalkingRouteResult: " + " 没找到结果");
                    return;
                }
                map.clear();
                WalkingRouteOverlay overly = new MyWalk(map);
                overly.setData(walkingRouteResult.getRouteLines().get(0));
                overly.addToMap();
                overly.zoomToSpan();
            }

            //换乘路线
            @Override
            public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

            }

            //公交
            @Override
            public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

            }

            //驾车
            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
                if (drivingRouteResult == null ||
                        drivingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    Log.e("TAG", "onGetWalkingRouteResult: " + " 没找到结果");
                    return;
                }
                map.clear();
                DrivingRouteOverlay overlay = new MyDriver(map);
                overlay.setData(drivingRouteResult.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();
            }

            //室内
            @Override
            public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

            }

            //骑行
            @Override
            public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {
                if (bikingRouteResult == null ||
                        bikingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    Log.e("TAG", "onGetWalkingRouteResult: " + " 没找到结果");
                    return;
                }
                map.clear();
                BikingRouteOverlay overlay = new MyBinking(map);
                overlay.setData(bikingRouteResult.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();
            }
        });
    }

    private void initShareUrlSearchListener() {
        shareUrlSearch.setOnGetShareUrlResultListener(new OnGetShareUrlResultListener() {
            @Override
            public void onGetPoiDetailShareUrlResult(final ShareUrlResult shareUrlResult) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("TAG", "onGetPoiDetailShareUrlResult: " + shareUrlResult.getUrl());
                        Toast.makeText(AttentionActivity.this,
                                "分享内容:" + shareUrlResult.getUrl(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onGetLocationShareUrlResult(ShareUrlResult shareUrlResult) {

            }

            @Override
            public void onGetRouteShareUrlResult(ShareUrlResult shareUrlResult) {

            }
        });
    }

    private void initPoiSearchListener() {
        //检索监听
        poiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {

                if (poiResult == null || poiResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    Log.e("TAG", "没有找到相关搜索");
                    return;
                }

//                List<PoiInfo> list = poiResult.getAllPoi();
//
//                for (PoiInfo info : list) {
//
//                    Log.e("TAG", "onGetPoiResult: " + info.name);
//
//                    //添加标注物
//                    BitmapDescriptor bitmapDescriptor
//                            = BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher);
//
//                    options = new MarkerOptions().position(info.location)
//                            .icon(bitmapDescriptor);
//
//                    map.addOverlay(options);
//                }

                map.clear();
                MyOverly myOverly = new MyOverly(map);
                myOverly.setData(poiResult);
                myOverly.addToMap();
                myOverly.zoomToSpan();
                map.setOnMarkerClickListener(myOverly);
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

                shareUrlSearch.requestPoiDetailShareUrl(
                        new PoiDetailShareURLOption().poiUid(poiDetailResult.getUid()));

            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        });
    }

    private void initMarkerListener() {
        //覆盖物的点击事件
        map.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                BitmapDescriptor ll =
                        BitmapDescriptorFactory.fromResource(R.mipmap.ll);
                marker.setIcon(ll);

                Toast.makeText(AttentionActivity.this, "骚年,干嘛呢?", Toast.LENGTH_SHORT).show();

                return false;
            }
        });
    }

    private void iniMapListener() {
        //设置地图点击事件
        map.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                //添加覆盖物
                //创建一个坐标
                BitmapDescriptor bitmapDescriptor =
                        BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher);
                //创建一个覆盖物
                options =
                        new MarkerOptions().position(latLng).icon(bitmapDescriptor);
                //添加到地图中
                map.addOverlay(options);
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {

                return false;
            }
        });

    }

    @Override
    protected void initData(String json, String error) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @OnClick({R.id.ib_back, R.id.btn_map1, R.id.btn_map2, R.id.btn_map3, R.id.btn_map4, R.id.btn_map5, R.id.btn_map6, R.id.btn_map7, R.id.btn_map8, R.id.btn_map9})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.btn_map1:

                if (map.getMapType() == BaiduMap.MAP_TYPE_NORMAL) {
                    map.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                } else if (map.getMapType() == BaiduMap.MAP_TYPE_SATELLITE) {
                    map.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                    map.setTrafficEnabled(true);
                }

//                //普通地图
//                map.setMapType(BaiduMap.MAP_TYPE_NORMAL);
//
//                //卫星地图
//                map.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
//
//                //空白地图, 基础地图瓦片将不会被渲染。
//                // 在地图类型中设置为NONE，将不会使用流量下载基础地图瓦片图层。
//                // 使用场景：与瓦片图层一起使用，节省流量，提升自定义瓦片图下载速度。
//                map.setMapType(BaiduMap.MAP_TYPE_NONE);
//
//                //开启交通图   实时交通图
//                map.setTrafficEnabled(true);
//
//                //开启交通图   百度城市热力图
//                map.setBaiduHeatMapEnabled(true);

                break;
            case R.id.btn_map2:
                //经纬度
                LatLng l = new LatLng(39.963175, 116.400244);

                //创建一个覆盖物的图标
                BitmapDescriptor icon = BitmapDescriptorFactory
                        .fromResource(R.mipmap.ic_launcher);

                //创建一个覆盖物
                options = new MarkerOptions().position(l).icon(icon);

                //将覆盖物添加到地图中
                map.addOverlay(options);

                break;
            case R.id.btn_map3:
                //添加一个几何图形
                LatLng l1 = new LatLng(39.93925, 116.387428);

                LatLng l2 = new LatLng(39.93925, 116.407428);

                LatLng l3 = new LatLng(39.88925, 116.387428);

                LatLng l4 = new LatLng(39.88925, 116.407428);

                //将三个坐标添加到集合中
                List<LatLng> pts = new ArrayList<>();
                pts.add(l1);
                pts.add(l2);
                pts.add(l3);
                pts.add(l4);

                options = new PolygonOptions().points(pts)
                        //2 边宽  和边的颜色  ， fillColor(填充的颜色)
                        .stroke(new Stroke(2, 0xff00FF00)).fillColor(0x22ff0000);

                map.addOverlay(options);
                break;
            case R.id.btn_map4:

                /**
                 PoiCitySearchOption	city(java.lang.String city)
                 检索城市

                 PoiCitySearchOption	keyword(java.lang.String key)
                 搜索关键字

                 PoiCitySearchOption	pageCapacity(int pageCapacity)
                 设置每页容量，默认为每页10条

                 PoiCitySearchOption	pageNum(int pageNum)
                 分页编号
                 */
                map.clear();
                poiSearch.searchInCity(
                        new PoiCitySearchOption().city("北京")
                                .keyword("大保健").pageNum(1).pageCapacity(15));

                break;
            case R.id.btn_map5:

                //周边搜索
                /**
                 keyword(java.lang.String key)
                 检索关键字
                 PoiNearbySearchOption	location(LatLng location)
                 检索位置
                 PoiNearbySearchOption	pageCapacity(int pageCapacity)
                 设置每页容量，默认为每页10条
                 PoiNearbySearchOption	pageNum(int pageNum)
                 分页编号
                 PoiNearbySearchOption	radius(int radius)
                 设置检索的半径范围
                 PoiNearbySearchOption	sortType(PoiSortType sortType)
                 搜索结果排序规则，可选，默认
                 */
                map.clear();

                LatLng l11 = new LatLng(39.93925, 116.407428);


                poiSearch.searchNearby(new PoiNearbySearchOption()

                        .radius(10000).pageCapacity(10).pageNum(1).location(l11).keyword("ATM"));
                break;
            case R.id.btn_map6:
                //路线
                PlanNode stNode = PlanNode.withCityCodeAndPlaceName(132, "北京西站");
                PlanNode endNode = PlanNode.withCityCodeAndPlaceName(132, "平西府");

//                routePlanSearch.transitSearch(new TransitRoutePlanOption()
//                        .city("北京").from(stNode).to(endNode));
                routePlanSearch.drivingSearch(new DrivingRoutePlanOption().from(stNode).to(endNode));
//                routePlanSearch.bikingSearch(new BikingRoutePlanOption().from(stNode).to(endNode));
                break;
            case R.id.btn_map7:

                //将经纬度转找成地址
                geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(new LatLng(23.946758, 100.423134)));

                //将地址转换成经纬度
//                geoCoder.geocode(new GeoCodeOption().address("北京").city("北京"));
                break;
            case R.id.btn_map8:
                onCreate();
                break;
            case R.id.btn_map9:
                map.setMyLocationEnabled(true);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        poiSearch.destroy();
        map.setMyLocationEnabled(false);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onStop();
        mapView.onResume();
    }

    class MyOverly extends PoiOverlay {
        /**
         * 构造函数
         *
         * @param baiduMap 该 PoiOverlay 引用的 BaiduMap 对象
         */
        public MyOverly(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int i) {

            List<PoiInfo> allPoi = getPoiResult().getAllPoi();

            Toast.makeText(AttentionActivity.this,
                    "" + allPoi.get(i).name, Toast.LENGTH_SHORT).show();

//            if (allPoi.get(i).hasCaterDetails)
            //如果是有美食才会调用详情
            poiSearch.searchPoiDetail(
                    new PoiDetailSearchOption().poiUid(allPoi.get(i).uid));

            return super.onPoiClick(i);
        }
    }


    //创建步行路线覆盖物
    class MyWalk extends WalkingRouteOverlay {

        public MyWalk(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPolylineClick(Polyline polyline) {
            return super.onPolylineClick(polyline);
        }
    }


    //创建架车路线覆盖物
    class MyDriver extends DrivingRouteOverlay {

        public MyDriver(BaiduMap baiduMap) {
            super(baiduMap);
        }
    }


    //创建步行路线覆盖物
    class MyBinking extends BikingRouteOverlay {

        public MyBinking(BaiduMap baiduMap) {
            super(baiduMap);
        }
    }

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    public void onCreate() {
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数

        //設置option
        initLocation();
        //开启定位
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系

        int span = 1000;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要

        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps

        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果

        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集

        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

        mLocationClient.setLocOption(option);
    }


    public class MyLocationListener implements BDLocationListener {

        private boolean isFirst = true;

        @Override
        public void onReceiveLocation(BDLocation location) {

//            //获取定位结果
//            StringBuffer sb = new StringBuffer(256);
//
//            sb.append("time : ");
//            sb.append(location.getTime());    //获取定位时间
//
//            sb.append("\nerror code : ");
//            sb.append(location.getLocType());    //获取类型类型
//
//            sb.append("\nlatitude : ");
//            sb.append(location.getLatitude());    //获取纬度信息
//
//            sb.append("\nlontitude : ");
//            sb.append(location.getLongitude());    //获取经度信息
//
//            sb.append("\nradius : ");
//            sb.append(location.getRadius());    //获取定位精准度
//
//
//            Log.i("BaiduLocationApiDem", sb.toString());

            //拿到定位结果
            if (location == null) {
                return;
            }

            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    //设置精确度
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100)
                    //设置纬度
                    .latitude(location.getLatitude())
                    //设置经度
                    .longitude(location.getLongitude()).build();
            // 设置定位数据
            map.setMyLocationData(locData);


            if (isFirst) {

                isFirst = false; //只会在第一次更新的时候才更新
                LatLng lat = new LatLng(location.getLatitude(),
                        location.getLongitude());
                //第一个参数是坐标，第二个参数是缩放值
                MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLngZoom(lat, 13);

                map.animateMapStatus(mapStatusUpdate);

            }
        }

    }
}
