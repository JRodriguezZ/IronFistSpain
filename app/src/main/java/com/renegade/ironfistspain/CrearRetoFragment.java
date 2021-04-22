package com.renegade.ironfistspain;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.renegade.ironfistspain.databinding.FragmentCrearRetoBinding;
import com.touchboarder.weekdaysbuttons.WeekdaysDataItem;
import com.touchboarder.weekdaysbuttons.WeekdaysDataSource;
import com.touchboarder.weekdaysbuttons.WeekdaysDrawableProvider;

import java.util.ArrayList;
import java.util.Calendar;

public class CrearRetoFragment extends Fragment {

    private FragmentCrearRetoBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = com.renegade.ironfistspain.databinding.FragmentCrearRetoBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        WeekdaysDataSource wds = new WeekdaysDataSource(this, binding.weekdaysStub)
//                .setDrawableType(WeekdaysDrawableProvider.MW_ROUND_RECT)
//                .setFirstDayOfWeek(Calendar.MONDAY)
//                .setSelectedColorRes(R.color.DarkRed)
//                .setUnselectedColor(Color.TRANSPARENT)
//                .setFontTypeFace(Typeface.MONOSPACE)
//                .setFontBaseSize(14)
//                .setNumberOfLetters(3)
//                .start(this);
//
//        new WeekdaysDataSource.Callback() {
//            @Override
//            public void onWeekdaysItemClicked(int i, WeekdaysDataItem weekdaysDataItem) {
//                // Do something if today is selected?
//                Calendar calendar = Calendar.getInstance();
//                if(item.getCalendarDayId()==calendar.get(Calendar.DAY_OF_WEEK)&&item.isSelected())
//                    Toast.makeText(MainActivity.this,"Carpe diem",Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onWeekdaysSelected(int i, ArrayList<WeekdaysDataItem> arrayList) {
//                //Filter on the attached id if there is multiple weekdays data sources
//                if(attachId==R.id.weekdays_stub_4){
//                    // Do something on week 4?
//                }
//            }
//
//        };
    }
}