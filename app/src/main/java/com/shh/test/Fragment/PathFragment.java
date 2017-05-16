package com.shh.test.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.shh.test.R;
import com.shh.test.overlayutil.PoiOverlay;
import com.shh.test.overlayutil.WalkingRouteOverlay;

/**
 * Created by Administrator on 2017/5/16.
 */

public class PathFragment extends Fragment {
    private BaiduMap mBaiduMap = null;
    private MapView mMapView = null;
    private LocationClient mLocationClient;
    private LocationClientOption.LocationMode tempMode = LocationClientOption.LocationMode.Hight_Accuracy;
    private String tempcoor = "bd09ll";
    private RoutePlanSearch routSearch;
    private PoiSearch mPoiSearch;
    private EditText mEditText;
    private Button mButton;
    private static View v;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);



    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_path,container,false);
        setRetainInstance(true);
        mMapView = (MapView)v.findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        MapStatus mapStatus=new MapStatus.Builder().zoom(19).build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
        mBaiduMap.setMapStatus(mMapStatusUpdate);
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMyLocationEnabled(true);
        mEditText=(EditText)v.findViewById(R.id.key);
        mButton=(Button)v.findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption();
                nearbySearchOption.location(new LatLng(30.3264631626,120.3534762293));
                nearbySearchOption.keyword(mEditText.getText().toString());
                nearbySearchOption.radius(2000);
                mPoiSearch.searchNearby(nearbySearchOption);

            }
        });
        mLocationClient = new LocationClient(getContext());
        initLocation();
        mLocationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) throws NumberFormatException  {
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        .direction(100)
                        .latitude(location.getLatitude())
                        .longitude(location.getLongitude())
                        .build();
                mBaiduMap.setMyLocationData(locData);
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(u);
            }

            @Override
            public void onConnectHotSpotMessage(String s, int i) {

            }
        });

        mPoiSearch= PoiSearch.newInstance();

        OnGetPoiSearchResultListener poiListener=new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                if (poiResult == null || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
                    Toast.makeText(getContext(), "没有找到你想去的地方",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
                    //清空标识
                    mBaiduMap.clear();
                    MyPoiOverlay poiOverlay = new MyPoiOverlay(mBaiduMap);
                    //设置poi数据
                    poiOverlay.setData(poiResult);
                    mBaiduMap.setOnMarkerClickListener(poiOverlay);
                    //添加所有覆盖物
                    poiOverlay.addToMap();
                    poiOverlay.zoomToSpan();


                }

            }
            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
                if (poiDetailResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(getContext(), "没有找到你想去的地方",
                            Toast.LENGTH_SHORT).show();
                } else {
                    mBaiduMap.clear();
                    final double endlat=poiDetailResult.getLocation().latitude;
                    final double endlon=poiDetailResult.getLocation().longitude;
                    mMapView = (MapView)v.findViewById(R.id.bmapView);
                    mBaiduMap = mMapView.getMap();
                    MapStatus mapStatus=new MapStatus.Builder().zoom(19).build();
                    MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
                    mBaiduMap.setMapStatus(mMapStatusUpdate);
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                    mBaiduMap.setMyLocationEnabled(true);
                    mEditText=(EditText)v.findViewById(R.id.key);
                    mButton=(Button)v.findViewById(R.id.button);
                    mButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption();
                            nearbySearchOption.location(new LatLng(30.326912,120.35043));
                            nearbySearchOption.keyword(mEditText.getText().toString());
                            nearbySearchOption.radius(5000);
                            mPoiSearch.searchNearby(nearbySearchOption);

                        }
                    });
                    mLocationClient = new LocationClient(getContext());
                    initLocation();
                    mLocationClient.registerLocationListener(new BDLocationListener() {
                        @Override
                        public void onReceiveLocation(BDLocation location) throws NumberFormatException  {
                            MyLocationData locData = new MyLocationData.Builder()
                                    .accuracy(location.getRadius())
                                    .direction(100)
                                    .latitude(location.getLatitude())
                                    .longitude(location.getLongitude())
                                    .build();
                            mBaiduMap.setMyLocationData(locData);
                            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                            MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                            mBaiduMap.animateMapStatus(u);
                            routSearch = RoutePlanSearch.newInstance();
                            LatLng startCenter = new LatLng(location.getLatitude(), location.getLongitude());
                            LatLng endCenter = new LatLng(endlat,endlon);
                            OnGetRoutePlanResultListener routListener = new OnGetRoutePlanResultListener() {
                                @Override
                                public void onGetWalkingRouteResult(WalkingRouteResult result) {

                                    if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                                        Toast.makeText(getContext(), "没有找到你想去的地方",
                                                Toast.LENGTH_LONG).show();
                                    }
                                    if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                                        return;
                                    }
                                    if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                                        WalkingRouteOverlay overlay = new WalkingRouteOverlay(mBaiduMap);
                                        mBaiduMap.setOnMarkerClickListener(overlay);
                                        overlay.setData(result.getRouteLines().get(0));
                                        overlay.addToMap();
                                        overlay.zoomToSpan();
                                    }
                                }
                                @Override
                                public void onGetTransitRouteResult(TransitRouteResult arg0) {

                                }

                                @Override
                                public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

                                }

                                @Override
                                public void onGetDrivingRouteResult(DrivingRouteResult arg0) {

                                }

                                @Override
                                public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

                                }

                                @Override
                                public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

                                }
                            };
                            routSearch.setOnGetRoutePlanResultListener(routListener);
                            PlanNode endNode = PlanNode.withLocation(endCenter);
                            PlanNode startNode = PlanNode.withLocation(startCenter);
                            routSearch.walkingSearch((new WalkingRoutePlanOption()).from(startNode).to(endNode));

                        }

                        @Override
                        public void onConnectHotSpotMessage(String s, int i) {

                        }
                    });
                    mLocationClient.start();
                }

            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        };
        mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
        mLocationClient.start();
        return v;
    }

    class MyPoiOverlay extends PoiOverlay {
        public MyPoiOverlay(BaiduMap arg0) {
            super(arg0);
        }
        @Override
        public boolean onPoiClick(int arg0) {
            super.onPoiClick(arg0);
            PoiInfo poiInfo = getPoiResult().getAllPoi().get(arg0);
            mPoiSearch.searchPoiDetail(new PoiDetailSearchOption()
                    .poiUid(poiInfo.uid));
            return true;
        }
    }


    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        //设置定位模式：Hight_Accuracy为高精度模式
        option.setLocationMode(tempMode);
        //设置返回的结果坐标系：bd09ll为百度专用坐标系
        option.setCoorType(tempcoor);
        //定位间隔
        option.setScanSpan(0);
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setLocationNotify(true);
        option.setIgnoreKillProcess(true);
        option.setEnableSimulateGps(false);
        option.setIsNeedLocationDescribe(true);
        option.setIsNeedLocationPoiList(true);
        mLocationClient.setLocOption(option);
    }


}





