package com.ctech.reaction;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.ctech.reaction.ui.FeedAdapter;
import com.ctech.reaction.util.Constants;
import com.ctech.reaction.widget.ReactionView;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = "MainActivity";

  //@BindView(R.id.rvNewFeed)
  RecyclerView rvNewFeed;

  @BindView(R.id.btReaction)
  LinearLayout btReaction;

  @BindView(R.id.root)
  FrameLayout root;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

  }

  private boolean scaledDown;


  @OnClick(R.id.btReaction)
  public void showPopup(View v){
    ReactionView rvl = new ReactionView(this);
    root.addView(rvl);
  }



  private void setUpRecyclerView() {
    FeedAdapter adapter = new FeedAdapter(Constants.feeds);
    rvNewFeed.setLayoutManager(new LinearLayoutManager(this));
    rvNewFeed.setAdapter(adapter);
  }
}
