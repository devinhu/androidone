package com.sd.one.activity.demo.draglistvew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.sd.one.activity.demo.drawermenu.DrawerMenuActivity;
import com.sd.one.activity.demo.pulltorefresh.PullToRefreshActivity;
import com.sd.one.activity.demo.waterfall.WaterfallActivity;
import com.sd.one.activity.demo.wheel.TimeWheelActivity;

public class DSLVFragmentClicks extends DSLVFragment {

    public static DSLVFragmentClicks newInstance(int headers, int footers) {
        DSLVFragmentClicks f = new DSLVFragmentClicks();

        Bundle args = new Bundle();
        args.putInt("headers", headers);
        args.putInt("footers", footers);
        f.setArguments(args);

        return f;
    }

    AdapterView.OnItemLongClickListener mLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,  long arg3) {
            String message = String.format("Long-clicked item %d", arg2);
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            return true;
        }
    };

    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);

        ListView lv = getListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,  long arg3) {
                String message = String.format("Clicked item %d", arg2);
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                switch(arg2){
                    case 0:
                        Intent intent = new Intent(getActivity(), PullToRefreshActivity.class);
                        getActivity().startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(getActivity(), DrawerMenuActivity.class);
                        getActivity().startActivity(intent);
                        break;
                        
                    case 2:
                        intent = new Intent(getActivity(), TimeWheelActivity.class);
                        getActivity().startActivity(intent);
                        break;
                        
                    case 3:
                        intent = new Intent(getActivity(), WaterfallActivity.class);
                        getActivity().startActivity(intent);
                        break;
                }
            }
        });
        
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,  long arg3) {
                String message = String.format("Long-clicked item %d", arg2);
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
}
