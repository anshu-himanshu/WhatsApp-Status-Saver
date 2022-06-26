package com.ansh.whatsappstatussaver;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;


public class ViewpagerStatusAdapter extends FragmentStateAdapter {

    private final String[] tabTitles = new String[]{"Images","Videos","Saved"};

    public ViewpagerStatusAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 0:
                return new ImageFragment();
                case 1:
                    return new VideoFragment();
                    case 2:
                        return new SavedFragment();
        }
        return new ImageFragment();
    }

    @Override
    public int getItemCount() {
        return tabTitles.length;
    }
}
