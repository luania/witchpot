package com.luania.witchpot.widget;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.luania.witchpot.R;
import com.luania.witchpot.activity.LogInActivity;
import com.luania.witchpot.service.DataService;
import com.wilddog.client.AuthData;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by luania on 16/7/4.
 */
public class UserDrawerLayout extends DrawerLayout {

    Context context;

    private TextView tvUserEmail;
    private CircleImageView civProfileImage;
    private Button btnLogout;
    private View navigationView;
    private Button btnHome;

    public UserDrawerLayout(final Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        navigationView = findViewById(R.id.ll_navigation);
        tvUserEmail = (TextView) findViewById(R.id.tv_user_email);

        civProfileImage = (CircleImageView) findViewById(R.id.civ_profile_image);
        civProfileImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthData authData = DataService.getAutDATA();
                if (authData != null) {
                    return;
                }
                Intent intent = new Intent(context, LogInActivity.class);
                context.startActivity(intent);
            }
        });

        btnLogout = (Button) findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DataService.logout();
                Toast.makeText(context, context.getString(R.string.user_logout_success), Toast.LENGTH_SHORT).show();
                setUserData();

                closeDrawer(GravityCompat.START);
            }
        });
        setUserData();
        btnHome = (Button) findViewById(R.id.btn_home);
        btnHome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDrawer(GravityCompat.START);
            }
        });
    }

    public void setUserData() {
        if(navigationView == null){
            return;
        }
        AuthData authData = DataService.getAutDATA();
        if (authData != null) {
            tvUserEmail.setText((CharSequence) authData.getProviderData().get("email"));
            btnLogout.setVisibility(VISIBLE);
        } else {
            tvUserEmail.setText(context.getString(R.string.user_email));
            btnLogout.setVisibility(GONE);
        }
    }

    public void close(){
        closeDrawer(navigationView);
    }

    public boolean isOpen(){
        return isDrawerOpen(navigationView);
    }
}
