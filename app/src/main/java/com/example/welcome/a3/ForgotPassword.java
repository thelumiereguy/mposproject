package com.example.welcome.a3;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ForgotPassword extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        TextView back=findViewById(R.id.textView);
        final Button fp=findViewById(R.id.button);
        final LinearLayout ll=findViewById(R.id.ll1);
        final TextView fp1=findViewById(R.id.fp1);
        final TextView fp2=findViewById(R.id.fp2);
        final LinearLayout fps=findViewById(R.id.fps);
        fp1.setAlpha(0);
        fp2.setAlpha(0);
        ll.setAlpha(1);
        fp.setVisibility(View.VISIBLE);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });

        fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ll.animate()
                        .alpha(0f)
                        .setDuration(4000)
                        .setListener(null);
                ll.setVisibility(View.GONE);

                fp1.animate()
                        .alpha(1f)
                        .setDuration(4000)
                        .setListener(null);
                fp1.setVisibility(View.VISIBLE);

                fp2.animate()
                        .alpha(1f)
                        .setDuration(4000)
                        .setListener(null);
                fp2.setVisibility(View.VISIBLE);



//                ObjectAnimator transA=ObjectAnimator.ofFloat(ll,View.TRANSLATION_Y,0,200);
//                transA.setDuration(4000);
////                ObjectAnimator alphaB=ObjectAnimator.ofFloat(fps,View.ALPHA,0,1);
////                alphaB.setDuration(4000);
//                AnimatorSet anim=new AnimatorSet();
//                anim.play(transA);
////                        .before(alphaB);
//                anim.start();
                fp.setVisibility(View.INVISIBLE);
            }
        });
    }
}
