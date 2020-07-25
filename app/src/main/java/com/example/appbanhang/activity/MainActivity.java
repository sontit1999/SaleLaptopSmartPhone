package com.example.appbanhang.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.appbanhang.adapter.MenuAdapter;
import com.example.appbanhang.callback.Menucallback;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import android.support.design.widget.NavigationView;
//import android.support.v4.view.GravityCompat;
//import android.support.v4.widget.DrawerLayout;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhang.R;
import com.example.appbanhang.adapter.LoaispAdapter;
import com.example.appbanhang.adapter.SanphamAdapter;
import com.example.appbanhang.adapter.girdviewsanpham;
import com.example.appbanhang.model.Giohang;
import com.example.appbanhang.model.Loaisp;
import com.example.appbanhang.model.Sanpham;
import com.example.appbanhang.ultil.CheckConnection;
import com.example.appbanhang.ultil.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    RecyclerView rvMenu;
    MenuAdapter menuAdapter;
    private ViewFlipper viewFlipper;
    private GridView gridView;
    private NavigationView navigationView;
    private ListView listViewmanhinhchinh;
    private DrawerLayout drawerLayout;
    private ArrayList<Loaisp> mangloaisp;
    private LoaispAdapter loaispAdapter;
    private ArrayList<Sanpham> mangsanpham;
    private SanphamAdapter sanphamAdapter;
    private girdviewsanpham girdviewadapter;
    public static ArrayList<Giohang> manggiohang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            ActionBar();
            ActionViewFlipper();
            GetDuLieuLoaisp();
         //   GetDuLieuSanPhamNew();
            girdview();
            //CatchOnItemListView();
            ChangeIntent();
        }else{
            CheckConnection.ShowToast_short(getApplicationContext(),"Bạn Hãy Kiểm Tra Lại Kết Nối");
            finish();
        }
        setUpRecyclerviewMenu();
    }

    private void setUpRecyclerviewMenu() {
        ArrayList<Loaisp> arrayList = new ArrayList<>();
        arrayList.add(new Loaisp(0,"Trang chính",R.drawable.ic_baseline_home_24));
        arrayList.add(new Loaisp(1,"Điện thoại",R.drawable.ic_baseline_smartphone_24));
        arrayList.add(new Loaisp(2,"Laptop",R.drawable.ic_baseline_laptop_24));
        arrayList.add(new Loaisp(3,"Thông tin",R.drawable.ic_baseline_contact_phone_24));
        arrayList.add(new Loaisp(4,"Thanh toán",R.drawable.ic_baseline_monetization_on_24));
        arrayList.add(new Loaisp(5,"Đăng xuất",R.drawable.ic_baseline_exit_to_app_24));
        menuAdapter = new MenuAdapter();
        menuAdapter.setList(arrayList);
        menuAdapter.setMenucallback(new Menucallback() {
            @Override
            public void onItemMenuClick(int id) {
               // Toast.makeText(MainActivity.this, "Click id " + id, Toast.LENGTH_SHORT).show();
                switch (id){
                    case 0:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent=new Intent(MainActivity.this,MainActivity.class);
                        }else{
                            CheckConnection.ShowToast_short(getApplicationContext(),"Lỗi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent=new Intent(MainActivity.this,DienThoaiActivity.class);
                            intent.putExtra("idloaisanpham",id);
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_short(getApplicationContext(),"Lỗi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent=new Intent(MainActivity.this,LapTopActivity.class);
                            intent.putExtra("idloaisanpham",id);
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_short(getApplicationContext(),"Lỗi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent=new Intent(MainActivity.this,LienHeActivity.class);
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_short(getApplicationContext(),"Lỗi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
//
                    case 4:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent=new Intent(MainActivity.this,SanPhamDaDat.class);
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_short(getApplicationContext(),"Lỗi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 5:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            CheckConnection.ShowToast_short(getApplicationContext(),"Đăng Xuất thành công");
                            Intent intent=new Intent(getApplicationContext(),DangNhapActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            MainActivity.manggiohang.clear();
                            startActivity(intent);
                        }else {
                            CheckConnection.ShowToast_short(getApplicationContext(),"Lỗi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
        rvMenu.setHasFixedSize(true);
        rvMenu.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));

        rvMenu.setAdapter(menuAdapter);

    }

    private void ChangeIntent() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),ChiTiecSanPham.class);
                intent.putExtra("thongtinsanpham",mangsanpham.get(position));
                startActivity(intent);
            }
        });
    }

    private void CatchOnItemListView() {
        listViewmanhinhchinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent=new Intent(MainActivity.this,MainActivity.class);
                        }else{
                            CheckConnection.ShowToast_short(getApplicationContext(),"Lỗi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent=new Intent(MainActivity.this,DienThoaiActivity.class);
                            intent.putExtra("idloaisanpham",mangloaisp.get(position).getId());
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_short(getApplicationContext(),"Lỗi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent=new Intent(MainActivity.this,LapTopActivity.class);
                            intent.putExtra("idloaisanpham",mangloaisp.get(position).getId());
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_short(getApplicationContext(),"Lỗi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent=new Intent(MainActivity.this,LienHeActivity.class);
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_short(getApplicationContext(),"Lỗi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
//
                    case 4:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent=new Intent(MainActivity.this,SanPhamDaDat.class);
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_short(getApplicationContext(),"Lỗi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 5:
                    if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                        CheckConnection.ShowToast_short(getApplicationContext(),"Đăng Xuất thành công");
                        Intent intent=new Intent(getApplicationContext(),DangNhapActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        MainActivity.manggiohang.clear();
                        startActivity(intent);
                    }else {
                        CheckConnection.ShowToast_short(getApplicationContext(),"Lỗi");
                    }
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;

                }
            }
        });
    }

    private void girdview() {
        final RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Server.duongdansanpham, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                if(response !=null){
                    int ID=0;
                    String Tensanpham="";
                    Integer Giasanpham=0;
                    String Hinhanhsanpham="";
                    String Motasanpham="";
                    int IDsanpham=0;
                    for(int i = 0;i<response.length();i++){
                        try {
                            JSONObject jsonObject=response.getJSONObject(i);
                            ID=jsonObject.getInt("id");
                            Tensanpham=jsonObject.getString("tensp");
                            Giasanpham=jsonObject.getInt("giasp");
                            Hinhanhsanpham=jsonObject.getString("hinhanhsp");
                            Motasanpham=jsonObject.getString("motasp");
                            IDsanpham=jsonObject.getInt("idsanpham");
                            mangsanpham.add(new Sanpham(ID,Tensanpham,Giasanpham,Hinhanhsanpham,Motasanpham,IDsanpham));
                            girdviewadapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_short(getApplicationContext(),error.toString());
                finish();
            }
        });
        requestQueue.add(jsonArrayRequest);

    }

    private void GetDuLieuSanPhamNew() {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Server.duongdansanpham, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null){
                    int ID=0;
                    String Tensanpham="";
                    Integer Giasanpham=0;
                    String Hinhanhsanpham="";
                    String Motasanpham="";
                    int IDsanpham=0;
                    for(int i = 0;i<response.length();i++){
                        try {
                            JSONObject jsonObject=response.getJSONObject(i);
                            ID=jsonObject.getInt("id");
                            Tensanpham=jsonObject.getString("tensp");
                            Giasanpham=jsonObject.getInt("giasp");
                            Hinhanhsanpham=jsonObject.getString("hinhanhsp");
                            Motasanpham=jsonObject.getString("motasp");
                            IDsanpham=jsonObject.getInt("idsanpham");
                            mangsanpham.add(new Sanpham(ID,Tensanpham,Giasanpham,Hinhanhsanpham,Motasanpham,IDsanpham));
                            sanphamAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }


    private void GetDuLieuLoaisp() {
       //  mangloaisp.add(new Loaisp(0,"Liên Hệ",R.drawable.ic_baseline_home_24));

//        final RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
//        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Server.locahost, new Response.Listener<JSONArray>() {
//
//            @Override
//            public void onResponse(JSONArray response) {
//                if(response !=null){
//                    for(int i= 0 ;i<response.length();i++){
//                        try {
//                            JSONObject jsonObject=response.getJSONObject(i);
//                            id=jsonObject.getInt("id");
//                            tenloaisp=jsonObject.getString("tenLoaisp");
//                            hinhanhloaisp=jsonObject.getString("hinhanhloaisp");
//                            mangloaisp.add(new Loaisp(id,tenloaisp,hinhanhloaisp));
//                            loaispAdapter.notifyDataSetChanged();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    mangloaisp.add(3,new Loaisp(0,"Liên Hệ","https://cdn2.iconfinder.com/data/icons/helpdesk-support-business/128/business-25-128.png"));
////                    mangloaisp.add(4,new Loaisp(0,"Thông Tin","https://cdn0.iconfinder.com/data/icons/email-29/64/x-07-256.png"));
//                    mangloaisp.add(4,new Loaisp(0,"Thanh Toán","https://cdn0.iconfinder.com/data/icons/shopping-202/64/Buy_buyer_pay-128.png"));
//                    mangloaisp.add(5,new Loaisp(0,"Đăng xuất","https://cdn0.iconfinder.com/data/icons/office-83/32/exit-128.png"));
//                    loaispAdapter.notifyDataSetChanged();
//
//                }
//            }
//
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                    CheckConnection.ShowToast_short(getApplicationContext(),error.toString());
//                    finish();
//            }
//        });
//        requestQueue.add(jsonArrayRequest);

    }


    private void ActionViewFlipper() {
        ArrayList<String> mangquangcao=new ArrayList<>();
        mangquangcao.add("https://salt.tikicdn.com/ts/categoryblock/55/be/7d/1ac99e78bc71f8ffc61fe8b1649e3997.jpg");
        mangquangcao.add("https://cdn.tgdd.vn/2020/06/banner/800-170-800x170-52.png");
        mangquangcao.add("https://cdn.tgdd.vn/2020/05/banner/60E875CA-82A3-4D16-8F99-C75E5D09F299-800x170.png");
        mangquangcao.add("https://cdn.tgdd.vn/2020/05/banner/800-170-800x170-43.png");
        mangquangcao.add("https://cdn.tgdd.vn/2020/06/banner/dt-docquyen-800-170-800x170.png");
        mangquangcao.add("https://cdn.tgdd.vn/2020/05/banner/800-170-800x170-65.png");
        mangquangcao.add("https://cdn.tgdd.vn/2020/06/banner/800-170-800x170-50.png");
        mangquangcao.add("https://cdn.tgdd.vn/2020/05/banner/Hp-800-170-800x170-1.png");
//        mangquangcao.add("https://images.kienthuc.net.vn/zoom/500/Uploaded/nguyenvan/2016_12_06/phim/phim-0_JEMD.png");
//        mangquangcao.add("https://png.pngtree.com/thumb_back/fw800/20160820/pngtree-Laptop-Notebook-Portable-Computer-Personal-Computer-photo-111670.jpg");
//        mangquangcao.add("http://cafefcdn.com/2017/photo-1-1488674784324.png");
//        mangquangcao.add("https://znews-photo.zadn.vn/w660/Uploaded/fsmyy/2018_05_15/ea3ec008effd01a358ec.jpg");
        for(int i=0;i<mangquangcao.size();i++){
            ImageView imageView=new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
            viewFlipper.setAutoStart(true);
            Animation animation_slide_in=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
            Animation animation_slide_out=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
            viewFlipper.setInAnimation(animation_slide_in);
            viewFlipper.setOutAnimation(animation_slide_out);
        }
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_trangchinh,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang :
                Intent intent=new Intent(getApplicationContext(),GioHang.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void Anhxa(){
        rvMenu = (RecyclerView) findViewById(R.id.rvMenu);
        toolbar=(Toolbar)findViewById(R.id.toobarmanhinhchinh);
        viewFlipper=(ViewFlipper)findViewById(R.id.viewlipper);
        navigationView=(NavigationView)findViewById(R.id.navigationview);
        listViewmanhinhchinh=(ListView)findViewById(R.id.listviewmanhinhchinh);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawerlayout);
        mangloaisp=new ArrayList<>();
        loaispAdapter=new LoaispAdapter(mangloaisp,getApplicationContext());
        listViewmanhinhchinh.setAdapter(loaispAdapter);
        mangsanpham=new ArrayList<>();
        sanphamAdapter=new SanphamAdapter(getApplicationContext(),mangsanpham);
        girdviewadapter=new girdviewsanpham(getApplicationContext(),mangsanpham);
        gridView=(GridView)findViewById(R.id.girdviewsanpham);
        gridView.setAdapter(girdviewadapter);
        if(manggiohang !=null){

        }else{
            manggiohang=new ArrayList<>();
        }
    }
}
