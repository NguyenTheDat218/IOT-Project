//package com.example.datn;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentStatePagerAdapter;
//
//public class ViewAutoManualAdapter extends FragmentStatePagerAdapter {
//    public ViewAutoManualAdapter(@NonNull FragmentManager fm, int behavior) {
//        super(fm, behavior);
//    }
//
//    @NonNull
//    @Override
//    public Fragment getItem(int position) {
//        switch (position){
//            case 0:
//                return new AutoFragment();
//            case 1:
//                return new ManualFragment();
//            default:
//                return new AutoFragment();
//        }
//    }
//
//    @Override
//    public int getCount() {
//        return 2;
//    }
//
//    @Nullable
//    @Override
//    public CharSequence getPageTitle(int position) {
//        String tittle="";
//        switch (position){
//            case 0:
//                tittle="Auto";
//                break;
//            case 1:
//                tittle="Manual";
//                break;
//        }
//        return tittle;
//    }
//}
