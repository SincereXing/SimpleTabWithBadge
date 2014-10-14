package com.example.tabdemo;

import java.util.ArrayList;
import java.util.List;
import com.readystatesoftware.viewbadger.BadgeView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

/**
 * @author Allen Wang
 *
 */
public class MainActivity extends FragmentActivity {
	
	//三个tab页面的列表
	private List<Fragment> fragments = new ArrayList<Fragment>();
	
	private ViewPager viewPager;
	private PagerAdapter pagerAdapter;
	
	//tab布局
	private LinearLayout firstTab;
	private LinearLayout secondTab;
	private LinearLayout thirdTab;
	
	//tab的标题
	private TextView firstText;
	private TextView secondText;
	private TextView thirdText;
	
	private ImageView tabUnderLine;

	//当前页面
	private int currentIndex;
	//屏幕宽度
	private int screenWidth;
	//页面总个数
	private int fragSize;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//初始化屏幕宽度
		DisplayMetrics outMetrics = new DisplayMetrics();
		getWindow().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		screenWidth = outMetrics.widthPixels;
		
		//初始化三个页面
		FirstTabFragment firstTabFragment = new FirstTabFragment();
		SecondTabFragment secondTabFragment = new SecondTabFragment();
		ThirdTabFragment thirdTabFragment = new ThirdTabFragment();
		fragments.add(firstTabFragment);
		fragments.add(secondTabFragment);
		fragments.add(thirdTabFragment);
		fragSize = fragments.size();
		
		//初始化顶部三个tab
		firstTab = (LinearLayout) findViewById(R.id.tab_first);
		secondTab = (LinearLayout) findViewById(R.id.tab_second);
		thirdTab = (LinearLayout) findViewById(R.id.tab_third);
		//给tab设置点击事件
		firstTab.setOnClickListener(tabClickListener);
		secondTab.setOnClickListener(tabClickListener);
		thirdTab.setOnClickListener(tabClickListener);
		//初始化tab选中后的下划线
		initTabUnderLine();
		
		firstText = (TextView) findViewById(R.id.text_first);
		secondText = (TextView) findViewById(R.id.text_second);
		thirdText = (TextView) findViewById(R.id.text_third);
		
		viewPager = (ViewPager)findViewById(R.id.viewpager);
		pagerAdapter = new MyPageAdapter(getSupportFragmentManager());
		viewPager.setAdapter(pagerAdapter);
		viewPager.setOnPageChangeListener(pageChangeListener);
		
		//example 给第一个tab加上badge
		BadgeView badge;
		badge = new BadgeView(MainActivity.this, firstTab);
		badge.setText("1");
		badge.show();
	}
	
	private OnClickListener tabClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == firstTab){
				viewPager.setCurrentItem(0);
			}
			else if(v == secondTab){
				viewPager.setCurrentItem(1);
			}
			else if(v == thirdTab){
				viewPager.setCurrentItem(2);
			}
		}
	};
	
	private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {
		
		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			resetTabTextView();
			currentIndex = arg0;
			switch (arg0)
			{
			case 0:
				firstText.setTextColor(getResources().getColor(R.color.green));
				break;
			case 1:
				secondText.setTextColor(getResources().getColor(R.color.green));
				break;
			case 2:
				thirdText.setTextColor(getResources().getColor(R.color.green));
				break;
			default:
				break;
			}
			Toast.makeText(MainActivity.this, "第" + (arg0 + 1) + "个页面", Toast.LENGTH_SHORT);
		}
		
		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			// TODO Auto-generated method stub
			//从左到右
			if(currentIndex == position) {
				LinearLayout.LayoutParams layoutParam = (android.widget.LinearLayout.LayoutParams) tabUnderLine
						.getLayoutParams();
				layoutParam.leftMargin = (int) (positionOffset * (screenWidth * 1.0 / fragSize) + currentIndex * (screenWidth / fragSize));
				tabUnderLine.setLayoutParams(layoutParam);
			}
			//从右到左
			else if(currentIndex > position){
				LinearLayout.LayoutParams layoutParam = (android.widget.LinearLayout.LayoutParams) tabUnderLine
						.getLayoutParams();
				layoutParam.leftMargin = (int) (-(1-positionOffset) * (screenWidth * 1.0 / fragSize) + currentIndex * (screenWidth / fragSize));
				tabUnderLine.setLayoutParams(layoutParam);
			}
		}
		
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private class MyPageAdapter extends FragmentPagerAdapter {

		public MyPageAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return fragments.get(arg0);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return fragments.size();
		}
		
	}
	
	//初始化tab下划线
	private void initTabUnderLine()
	{
		tabUnderLine = (ImageView) findViewById(R.id.tab_under_line);
		LinearLayout.LayoutParams layoutParam = (android.widget.LinearLayout.LayoutParams) tabUnderLine.getLayoutParams();
		layoutParam.width = screenWidth / fragSize;
		tabUnderLine.setLayoutParams(layoutParam);
	}
	
	//重置tab标题颜色
	private void resetTabTextView()
	{
		firstText.setTextColor(getResources().getColor(R.color.black));
		secondText.setTextColor(getResources().getColor(R.color.black));
		thirdText.setTextColor(getResources().getColor(R.color.black));
	}
}
