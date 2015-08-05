LiveButton
==========

![ScreenShot](https://raw.github.com/dakatso/LiveButton/master/example.gif)

Including in your project
-------------------------
    
    dependencies {
        compile 'com.github.dakatso:livebutton:1.0.0'
    }


Usage
-------------------------


    <ru.katso.livebutton.LiveButton 
            xmlns:livebutton="http://schemas.android.com/apk/res-auto"
		    android:layout_height="wrap_content"
		    android:layout_width="wrap_content"
		    android:gravity="center"
		    android:text="Button"
		    android:textColor="#ffffff"
		    android:textStyle="bold"
		    android:textSize="26sp"
		    livebutton:corners="4dp"
		    livebutton:normalHeight="6dp"
		    livebutton:pressedHeight="2dp"
		    livebutton:backgroundColor="#F57F76"
		    livebutton:shadowColor="#b16262"/>

 or
 

	LiveButton button = (LiveButton) findViewById(R.id.button);
	button.setPressedHeight(2);
	button.setNormalHeight(8);
	button.setCorners(12);
	button.setBackgroundColor(0xffF57F76);
	button.setShadowColor(0xffb16262);

Sample
-------------------------

[![Get it on Google Play](http://www.android.com/images/brand/get_it_on_play_logo_small.png)](https://play.google.com/store/apps/details?id=ru.katso.livebuttonsample)