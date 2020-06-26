package ali.naseem.hachimichi.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

import ali.naseem.hachimichi.R;
import ali.naseem.hachimichi.adapters.SliderAdapter;
import ali.naseem.hachimichi.models.ImageModel;
import ali.naseem.hachimichi.viewmodels.SliderViewModel;
import me.relex.circleindicator.CircleIndicator;

public class SliderFragment extends Fragment {

    private SliderViewModel mViewModel;
    private ArrayList<ImageModel> items = new ArrayList<>();
    private SliderAdapter adapter;
    private ViewPager mPager;
    private View loader;

    public static SliderFragment instance() {
        return new SliderFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.slider_fragment, container, false);
        mPager = view.findViewById(R.id.viewPager);
        loader = view.findViewById(R.id.loader);
        adapter = new SliderAdapter(getContext(), items);
        mPager.setAdapter(adapter);
        CircleIndicator indicator = view.findViewById(R.id.indicator);
        indicator.setViewPager(mPager);
        adapter.registerDataSetObserver(indicator.getDataSetObserver());
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SliderViewModel.class);
        mViewModel.getImages().observe(getViewLifecycleOwner(), imageModels -> {
            items.addAll(imageModels);
            adapter.notifyDataSetChanged();
        });
        mViewModel.getLoading().observe(getViewLifecycleOwner(), loading -> {
            if (loading) loader.setVisibility(View.VISIBLE);
            else {
                loader.setVisibility(View.GONE);
                mPager.setVisibility(View.VISIBLE);
                mViewModel.startTimer(items.size());
            }
        });
        mViewModel.loadImages();
        mViewModel.getCurrentPage().observe(getViewLifecycleOwner(), integer -> {
            mPager.setCurrentItem(integer, true);
        });
    }

}